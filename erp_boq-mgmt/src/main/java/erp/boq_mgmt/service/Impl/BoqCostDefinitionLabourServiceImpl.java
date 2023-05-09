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

import erp.boq_mgmt.dao.BoqCostDefinitionLabourDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqCostDefinitionLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionLabourDeactivateRequest;
import erp.boq_mgmt.dto.response.BoqCostDefinitionLabourFetchResponseObj;
import erp.boq_mgmt.dto.response.BoqCostDefinitionLabourResponse;
import erp.boq_mgmt.entity.BoqCostDefinitionLabour;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.BoqCostDefinitionLabourService;
import erp.boq_mgmt.service.BoqCostDefinitionService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class BoqCostDefinitionLabourServiceImpl implements BoqCostDefinitionLabourService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BoqCostDefinitionLabourDao boqCostDefinitionLabourDao;

	@Autowired
	private BoqCostDefinitionService boqCostDefinitionService;

	@Override
	public CustomResponse addBoqCostDefinitionLabour(BoqCostDefinitionLabourAddUpdateRequest requestDTO) {
		try {
			if (requestDTO.getBoqId() != null && requestDTO.getBoqCostDefinitionId() == null) {
				CustomResponse boqCostDefinitionResponse = boqCostDefinitionService.boqCostDefinitionSaveAsDraft(
						requestDTO.getBoqId(), requestDTO.getSiteId(), requestDTO.getUserDetail());
				if (boqCostDefinitionResponse.getStatus().equals(HttpStatus.OK.value())) {

					Long boqCostDefinitionId = boqCostDefinitionResponse.getData() != null
							? (Long) boqCostDefinitionResponse.getData()
							: null;
					requestDTO.setBoqCostDefinitionId(boqCostDefinitionId);
				} else
					return boqCostDefinitionResponse;
			}

			if (requestDTO.getBoqCostDefinitionId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide boqCostDefinitionId.");
			}

			BoqCostDefinitionLabour boqCostDefinitionLabour = setObject
					.boqCostDefinitionLabourAddUpdateRequestDtoToEntity(requestDTO);

			Long boqCostDefinitionLabourId = boqCostDefinitionLabourDao
					.saveBoqCostDefinitionLabour(boqCostDefinitionLabour);

			BoqCostDefinitionLabourResponse response = setObject
					.boqCostDefinitionLabourEntityToDto(boqCostDefinitionLabour);

			return new CustomResponse(
					((boqCostDefinitionLabourId != null && boqCostDefinitionLabourId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					response,
					((boqCostDefinitionLabourId != null && boqCostDefinitionLabourId > 0) ? HttpStatus.OK.name()
							: " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateBoqCostDefinitionLabour(BoqCostDefinitionLabourAddUpdateRequest requestDTO) {
		try {

			BoqCostDefinitionLabour boqCostDefinitionLabour = setObject
					.boqCostDefinitionLabourAddUpdateRequestDtoToEntity(requestDTO);

			BoqCostDefinitionLabour dbObj = boqCostDefinitionLabourDao
					.fetchBoqCostDefinitionLabourById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = boqCostDefinitionLabourDao.updateBoqCostDefinitionLabour(boqCostDefinitionLabour);

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
	public CustomResponse getBoqCostDefinitionLabourByBoqCostDefinitionId(Long boqCostDefinitionId) {
		try {
			List<BoqCostDefinitionLabour> boqCostDefinitionLabourList = boqCostDefinitionLabourDao
					.fetchBoqCostDefinitionLabourByBoqCostDefinitionId(boqCostDefinitionId);
			List<BoqCostDefinitionLabourResponse> results = new ArrayList<>();
			Double totalAmount = 0.0;
			if (boqCostDefinitionLabourList != null) {

				for (BoqCostDefinitionLabour boqCostDefinitionLabour : boqCostDefinitionLabourList) {
					BoqCostDefinitionLabourResponse obj = setObject
							.boqCostDefinitionLabourEntityToDto(boqCostDefinitionLabour);
					totalAmount = totalAmount + obj.getAmount();
					results.add(obj);

				}
			}
			BoqCostDefinitionLabourFetchResponseObj responseObj = new BoqCostDefinitionLabourFetchResponseObj(
					totalAmount, results);
			return new CustomResponse(Responses.SUCCESS.getCode(), responseObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateBoqCostDefinitionLabour(BoqCostDefinitionLabourDeactivateRequest requestDTO) {
		try {
			BoqCostDefinitionLabour dbObj = boqCostDefinitionLabourDao
					.fetchBoqCostDefinitionLabourById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = boqCostDefinitionLabourDao.deactivateBoqCostDefinitionLabour(dbObj);
			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

}
