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

import erp.boq_mgmt.dao.BoqMasterLmpsMachineryDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMachineryDeactivateRequest;
import erp.boq_mgmt.dto.response.BoqMasterLmpsMachineryFetchResponseObj;
import erp.boq_mgmt.dto.response.BoqMasterLmpsMachineryResponse;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.entity.BoqMasterLmpsMachinery;
import erp.boq_mgmt.enums.MachineryTrip;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.BoqMasterLmpsMachineryService;
import erp.boq_mgmt.service.BoqMasterLmpsService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class BoqMasterLmpsMachineryServiceImpl implements BoqMasterLmpsMachineryService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BoqMasterLmpsMachineryDao boqMasterLmpsMachineryDao;

	@Autowired
	private BoqMasterLmpsService boqMasterLmpsService;

	@Override
	public CustomResponse addBoqMasterLmpsMachinery(BoqMasterLmpsMachineryAddUpdateRequest requestDTO) {
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

			BoqMasterLmpsMachinery boqMasterLmpsMachinery = setObject
					.boqMasterLmpsMachineryAddUpdateRequestDtoToEntity(requestDTO);

			Long boqMasterLmpsMachineryId = boqMasterLmpsMachineryDao
					.saveBoqMasterLmpsMachinery(boqMasterLmpsMachinery);

			BoqMasterLmpsMachineryResponse response = setObject
					.boqMasterLmpsMachineryEntityToDto(boqMasterLmpsMachinery);

			return new CustomResponse(
					((boqMasterLmpsMachineryId != null && boqMasterLmpsMachineryId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					response, ((boqMasterLmpsMachineryId != null && boqMasterLmpsMachineryId > 0) ? HttpStatus.OK.name()
							: " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateBoqMasterLmpsMachinery(BoqMasterLmpsMachineryAddUpdateRequest requestDTO) {
		try {

			BoqMasterLmpsMachinery boqMasterLmpsMachinery = setObject
					.boqMasterLmpsMachineryAddUpdateRequestDtoToEntity(requestDTO);

			BoqMasterLmpsMachinery dbObj = boqMasterLmpsMachineryDao
					.fetchBoqMasterLmpsMachineryById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = boqMasterLmpsMachineryDao.updateBoqMasterLmpsMachinery(boqMasterLmpsMachinery);

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
	public CustomResponse getBoqMasterLmpsMachineryByBoqMasterLmpsId(Long boqMasterLmpsId) {
		try {
			List<BoqMasterLmpsMachinery> boqMasterLmpsMachineryList = boqMasterLmpsMachineryDao
					.fetchBoqMasterLmpsMachineryByBoqMasterLmpsId(boqMasterLmpsId);
			List<BoqMasterLmpsMachineryResponse> results = new ArrayList<>();
			if (boqMasterLmpsMachineryList != null) {

				for (BoqMasterLmpsMachinery boqMasterLmpsMachinery : boqMasterLmpsMachineryList) {
					BoqMasterLmpsMachineryResponse obj = setObject
							.boqMasterLmpsMachineryEntityToDto(boqMasterLmpsMachinery);
					results.add(obj);

				}

			}

			BoqMasterLmpsMachineryFetchResponseObj responseObj = new BoqMasterLmpsMachineryFetchResponseObj(results);

			return new CustomResponse(Responses.SUCCESS.getCode(), responseObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateBoqMasterLmpsMachinery(BoqMasterLmpsMachineryDeactivateRequest requestDTO) {
		try {
			BoqMasterLmpsMachinery dbObj = boqMasterLmpsMachineryDao
					.fetchBoqMasterLmpsMachineryById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = boqMasterLmpsMachineryDao.deactivateBoqMasterLmpsMachinery(dbObj);
			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getMachineryTrip() {
		try {
			List<IdNameDTO> responseObj = new ArrayList<>();
			for (MachineryTrip machineryTrip : MachineryTrip.values()) {

				responseObj.add(new IdNameDTO(Long.valueOf(machineryTrip.getId()), machineryTrip.name()));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), responseObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
