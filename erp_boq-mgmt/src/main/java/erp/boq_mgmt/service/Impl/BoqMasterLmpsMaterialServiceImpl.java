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

import erp.boq_mgmt.dao.BoqMasterLmpsMaterialDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMaterialDeactivateRequest;
import erp.boq_mgmt.dto.request.MaterialFetchRequest;
import erp.boq_mgmt.dto.response.BoqMasterLmpsMaterialFetchResponseObj;
import erp.boq_mgmt.dto.response.BoqMasterLmpsMaterialResponse;
import erp.boq_mgmt.dto.response.MaterialFetchResponse;
import erp.boq_mgmt.entity.BoqMasterLmpsMaterial;
import erp.boq_mgmt.entity.Material;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.BoqMasterLmpsMaterialService;
import erp.boq_mgmt.service.BoqMasterLmpsService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class BoqMasterLmpsMaterialServiceImpl implements BoqMasterLmpsMaterialService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BoqMasterLmpsMaterialDao boqMasterLmpsMaterialDao;

	@Autowired
	private BoqMasterLmpsService boqMasterLmpsService;

	@Override
	public CustomResponse addBoqMasterLmpsMaterial(BoqMasterLmpsMaterialAddUpdateRequest requestDTO) {
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

			BoqMasterLmpsMaterial boqMasterLmpsMaterial = setObject
					.boqMasterLmpsMaterialAddUpdateRequestDtoToEntity(requestDTO);

			Long boqMasterLmpsMaterialId = boqMasterLmpsMaterialDao.saveBoqMasterLmpsMaterial(boqMasterLmpsMaterial);

			BoqMasterLmpsMaterialResponse response = setObject.boqMasterLmpsMaterialEntityToDto(boqMasterLmpsMaterial);

			return new CustomResponse(
					((boqMasterLmpsMaterialId != null && boqMasterLmpsMaterialId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					response, ((boqMasterLmpsMaterialId != null && boqMasterLmpsMaterialId > 0) ? HttpStatus.OK.name()
							: " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateBoqMasterLmpsMaterial(BoqMasterLmpsMaterialAddUpdateRequest requestDTO) {
		try {

			BoqMasterLmpsMaterial boqMasterLmpsMaterial = setObject
					.boqMasterLmpsMaterialAddUpdateRequestDtoToEntity(requestDTO);

			BoqMasterLmpsMaterial dbObj = boqMasterLmpsMaterialDao.fetchBoqMasterLmpsMaterialById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = boqMasterLmpsMaterialDao.updateBoqMasterLmpsMaterial(boqMasterLmpsMaterial);

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
	public CustomResponse getBoqMasterLmpsMaterialByBoqMasterLmpsId(Long boqMasterLmpsId) {
		try {
			List<BoqMasterLmpsMaterial> boqMasterLmpsMaterialList = boqMasterLmpsMaterialDao
					.fetchBoqMasterLmpsMaterialByBoqMasterLmpsId(boqMasterLmpsId);
			List<BoqMasterLmpsMaterialResponse> results = new ArrayList<>();
			if (boqMasterLmpsMaterialList != null) {

				for (BoqMasterLmpsMaterial boqMasterLmpsMaterial : boqMasterLmpsMaterialList) {
					BoqMasterLmpsMaterialResponse obj = setObject
							.boqMasterLmpsMaterialEntityToDto(boqMasterLmpsMaterial);
					results.add(obj);

				}
			}

			BoqMasterLmpsMaterialFetchResponseObj responseObj = new BoqMasterLmpsMaterialFetchResponseObj(results);

			return new CustomResponse(Responses.SUCCESS.getCode(), responseObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateBoqMasterLmpsMaterial(BoqMasterLmpsMaterialDeactivateRequest requestDTO) {
		try {
			BoqMasterLmpsMaterial dbObj = boqMasterLmpsMaterialDao.fetchBoqMasterLmpsMaterialById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = boqMasterLmpsMaterialDao.deactivateBoqMasterLmpsMaterial(dbObj);
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
			List<Material> materialList = boqMasterLmpsMaterialDao.fetchMaterialList(requestDTO);
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
