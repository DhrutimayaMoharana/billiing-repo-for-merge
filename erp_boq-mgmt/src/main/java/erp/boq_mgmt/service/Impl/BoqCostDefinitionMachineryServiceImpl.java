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

import erp.boq_mgmt.dao.BoqCostDefinitionMachineryDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryDeactivateRequest;
import erp.boq_mgmt.dto.response.BoqCostDefinitionMachineryFetchResponseObj;
import erp.boq_mgmt.dto.response.BoqCostDefinitionMachineryResponse;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.entity.BoqCostDefinitionMachinery;
import erp.boq_mgmt.enums.MachineryTrip;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.BoqCostDefinitionMachineryService;
import erp.boq_mgmt.service.BoqCostDefinitionService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class BoqCostDefinitionMachineryServiceImpl implements BoqCostDefinitionMachineryService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BoqCostDefinitionMachineryDao boqCostDefinitionMachineryDao;

	@Autowired
	private BoqCostDefinitionService boqCostDefinitionService;

	@Override
	public CustomResponse addBoqCostDefinitionMachinery(BoqCostDefinitionMachineryAddUpdateRequest requestDTO) {
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

			BoqCostDefinitionMachinery boqCostDefinitionMachinery = setObject
					.boqCostDefinitionMachineryAddUpdateRequestDtoToEntity(requestDTO);

			Long boqCostDefinitionMachineryId = boqCostDefinitionMachineryDao
					.saveBoqCostDefinitionMachinery(boqCostDefinitionMachinery);

			BoqCostDefinitionMachineryResponse response = setObject
					.boqCostDefinitionMachineryEntityToDto(boqCostDefinitionMachinery);

			return new CustomResponse(
					((boqCostDefinitionMachineryId != null && boqCostDefinitionMachineryId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					response,
					((boqCostDefinitionMachineryId != null && boqCostDefinitionMachineryId > 0) ? HttpStatus.OK.name()
							: " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateBoqCostDefinitionMachinery(BoqCostDefinitionMachineryAddUpdateRequest requestDTO) {
		try {

			BoqCostDefinitionMachinery boqCostDefinitionMachinery = setObject
					.boqCostDefinitionMachineryAddUpdateRequestDtoToEntity(requestDTO);

			BoqCostDefinitionMachinery dbObj = boqCostDefinitionMachineryDao
					.fetchBoqCostDefinitionMachineryById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = boqCostDefinitionMachineryDao
					.updateBoqCostDefinitionMachinery(boqCostDefinitionMachinery);

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
	public CustomResponse getBoqCostDefinitionMachineryByBoqCostDefinitionId(Long boqCostDefinitionId) {
		try {
			List<BoqCostDefinitionMachinery> boqCostDefinitionMachineryList = boqCostDefinitionMachineryDao
					.fetchBoqCostDefinitionMachineryByBoqCostDefinitionId(boqCostDefinitionId);
			List<BoqCostDefinitionMachineryResponse> results = new ArrayList<>();
			Double totalAmount = 0.0;
			if (boqCostDefinitionMachineryList != null) {

				for (BoqCostDefinitionMachinery boqCostDefinitionMachinery : boqCostDefinitionMachineryList) {
					BoqCostDefinitionMachineryResponse obj = setObject
							.boqCostDefinitionMachineryEntityToDto(boqCostDefinitionMachinery);
					totalAmount = totalAmount + obj.getAmount();
					results.add(obj);

				}

			}

			BoqCostDefinitionMachineryFetchResponseObj responseObj = new BoqCostDefinitionMachineryFetchResponseObj(
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
	public CustomResponse deactivateBoqCostDefinitionMachinery(BoqCostDefinitionMachineryDeactivateRequest requestDTO) {
		try {
			BoqCostDefinitionMachinery dbObj = boqCostDefinitionMachineryDao
					.fetchBoqCostDefinitionMachineryById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = boqCostDefinitionMachineryDao.deactivateBoqCostDefinitionMachinery(dbObj);
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
