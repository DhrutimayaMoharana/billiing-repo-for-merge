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

import erp.boq_mgmt.dao.BoqCostDefinitionMaterialDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialDeactivateRequest;
import erp.boq_mgmt.dto.request.MaterialFetchRequest;
import erp.boq_mgmt.dto.response.BoqCostDefinitionMaterialFetchResponseObj;
import erp.boq_mgmt.dto.response.BoqCostDefinitionMaterialResponse;
import erp.boq_mgmt.dto.response.MaterialFetchResponse;
import erp.boq_mgmt.entity.BoqCostDefinitionMaterial;
import erp.boq_mgmt.entity.Material;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.BoqCostDefinitionMaterialService;
import erp.boq_mgmt.service.BoqCostDefinitionService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class BoqCostDefinitionMaterialServiceImpl implements BoqCostDefinitionMaterialService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BoqCostDefinitionMaterialDao boqCostDefinitionMaterialDao;

	@Autowired
	private BoqCostDefinitionService boqCostDefinitionService;

	@Override
	public CustomResponse addBoqCostDefinitionMaterial(BoqCostDefinitionMaterialAddUpdateRequest requestDTO) {
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

			BoqCostDefinitionMaterial boqCostDefinitionMaterial = setObject
					.boqCostDefinitionMaterialAddUpdateRequestDtoToEntity(requestDTO);

			Long boqCostDefinitionMaterialId = boqCostDefinitionMaterialDao
					.saveBoqCostDefinitionMaterial(boqCostDefinitionMaterial);

			BoqCostDefinitionMaterialResponse response = setObject
					.boqCostDefinitionMaterialEntityToDto(boqCostDefinitionMaterial);

			return new CustomResponse(
					((boqCostDefinitionMaterialId != null && boqCostDefinitionMaterialId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					response,
					((boqCostDefinitionMaterialId != null && boqCostDefinitionMaterialId > 0) ? HttpStatus.OK.name()
							: " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateBoqCostDefinitionMaterial(BoqCostDefinitionMaterialAddUpdateRequest requestDTO) {
		try {

			BoqCostDefinitionMaterial boqCostDefinitionMaterial = setObject
					.boqCostDefinitionMaterialAddUpdateRequestDtoToEntity(requestDTO);

			BoqCostDefinitionMaterial dbObj = boqCostDefinitionMaterialDao
					.fetchBoqCostDefinitionMaterialById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = boqCostDefinitionMaterialDao.updateBoqCostDefinitionMaterial(boqCostDefinitionMaterial);

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
	public CustomResponse getBoqCostDefinitionMaterialByBoqCostDefinitionId(Long boqCostDefinitionId) {
		try {
			List<BoqCostDefinitionMaterial> boqCostDefinitionMaterialList = boqCostDefinitionMaterialDao
					.fetchBoqCostDefinitionMaterialByBoqCostDefinitionId(boqCostDefinitionId);
			List<BoqCostDefinitionMaterialResponse> results = new ArrayList<>();
			Double totalAmount = 0.0;
			if (boqCostDefinitionMaterialList != null) {

				for (BoqCostDefinitionMaterial boqCostDefinitionMaterial : boqCostDefinitionMaterialList) {
					BoqCostDefinitionMaterialResponse obj = setObject
							.boqCostDefinitionMaterialEntityToDto(boqCostDefinitionMaterial);
					totalAmount = totalAmount + obj.getAmount();
					results.add(obj);

				}
			}

			BoqCostDefinitionMaterialFetchResponseObj responseObj = new BoqCostDefinitionMaterialFetchResponseObj(
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
	public CustomResponse deactivateBoqCostDefinitionMaterial(BoqCostDefinitionMaterialDeactivateRequest requestDTO) {
		try {
			BoqCostDefinitionMaterial dbObj = boqCostDefinitionMaterialDao
					.fetchBoqCostDefinitionMaterialById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = boqCostDefinitionMaterialDao.deactivateBoqCostDefinitionMaterial(dbObj);
			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getMaterialList(MaterialFetchRequest requestDTO) {
		try {
			List<Material> materialList = boqCostDefinitionMaterialDao.fetchMaterialList(requestDTO);
			List<MaterialFetchResponse> results = new ArrayList<>();
			if (materialList != null) {
				materialList.forEach(obj -> results.add(setObject.materialEntityToDto(obj)));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), results, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
