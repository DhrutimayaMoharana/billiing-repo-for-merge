package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.BoqMasterLmpsLabourDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqMasterLmpsLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsLabourDeactivateRequest;
import erp.boq_mgmt.dto.response.BoqMasterLmpsLabourFetchResponseObj;
import erp.boq_mgmt.dto.response.BoqMasterLmpsLabourResponse;
import erp.boq_mgmt.entity.BoqMasterLmpsLabour;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.BoqMasterLmpsLabourService;
import erp.boq_mgmt.service.BoqMasterLmpsService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class BoqMasterLmpsLabourServiceImpl implements BoqMasterLmpsLabourService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BoqMasterLmpsLabourDao boqMasterLmpsLabourDao;

	@Autowired
	private BoqMasterLmpsService boqMasterLmpsService;

	@Override
	public CustomResponse addBoqMasterLmpsLabour(BoqMasterLmpsLabourAddUpdateRequest requestDTO) {
		try {
			if (requestDTO.getBoqId() != null && requestDTO.getBoqMasterLmpsId() == null) {
				CustomResponse boqMasterLmpsResponse = boqMasterLmpsService
						.boqMasterLmpsSaveAsDraft(requestDTO.getBoqId(), requestDTO.getUserDetail());
				if (boqMasterLmpsResponse.getStatus().equals(HttpStatus.OK.value())) {

					Long boqMasterLmpsId = boqMasterLmpsResponse.getData() != null
							? (Long) boqMasterLmpsResponse.getData()
							: null;
					requestDTO.setBoqMasterLmpsId(boqMasterLmpsId);
				} else
					return boqMasterLmpsResponse;
			}

			if (requestDTO.getBoqMasterLmpsId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqMasterLmpsId.");
			}

			BoqMasterLmpsLabour boqMasterLmpsLabour = setObject
					.boqMasterLmpsLabourAddUpdateRequestDtoToEntity(requestDTO);

			Long boqMasterLmpsLabourId = boqMasterLmpsLabourDao.saveBoqMasterLmpsLabour(boqMasterLmpsLabour);

			BoqMasterLmpsLabourResponse response = setObject.boqMasterLmpsLabourEntityToDto(boqMasterLmpsLabour);

			return new CustomResponse(
					((boqMasterLmpsLabourId != null && boqMasterLmpsLabourId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					response, ((boqMasterLmpsLabourId != null && boqMasterLmpsLabourId > 0) ? HttpStatus.OK.name()
							: " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateBoqMasterLmpsLabour(BoqMasterLmpsLabourAddUpdateRequest requestDTO) {
		try {

			BoqMasterLmpsLabour boqMasterLmpsLabour = setObject
					.boqMasterLmpsLabourAddUpdateRequestDtoToEntity(requestDTO);

			BoqMasterLmpsLabour dbObj = boqMasterLmpsLabourDao.fetchBoqMasterLmpsLabourById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = boqMasterLmpsLabourDao.updateBoqMasterLmpsLabour(boqMasterLmpsLabour);

			if (!isUpdated)
				return new CustomResponse((HttpStatus.BAD_REQUEST.value()), isUpdated, "Already exist.");

			return new CustomResponse(HttpStatus.OK.value(), isUpdated, HttpStatus.OK.name());
		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getBoqMasterLmpsLabourByBoqMasterLmpsId(Long boqMasterLmpsId) {
		try {
			List<BoqMasterLmpsLabour> boqMasterLmpsLabourList = boqMasterLmpsLabourDao
					.fetchBoqMasterLmpsLabourByBoqMasterLmpsId(boqMasterLmpsId);
			List<BoqMasterLmpsLabourResponse> results = new ArrayList<>();
			if (boqMasterLmpsLabourList != null) {

				for (BoqMasterLmpsLabour boqMasterLmpsLabour : boqMasterLmpsLabourList) {
					BoqMasterLmpsLabourResponse obj = setObject.boqMasterLmpsLabourEntityToDto(boqMasterLmpsLabour);
					results.add(obj);

				}
			}
			BoqMasterLmpsLabourFetchResponseObj responseObj = new BoqMasterLmpsLabourFetchResponseObj(results);
			return new CustomResponse(Responses.SUCCESS.getCode(), responseObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateBoqMasterLmpsLabour(BoqMasterLmpsLabourDeactivateRequest requestDTO) {
		try {
			BoqMasterLmpsLabour dbObj = boqMasterLmpsLabourDao.fetchBoqMasterLmpsLabourById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = boqMasterLmpsLabourDao.deactivateBoqMasterLmpsLabour(dbObj);
			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

}
