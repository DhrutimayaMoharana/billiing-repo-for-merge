package erp.boq_mgmt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialDeactivateRequest;
import erp.boq_mgmt.dto.request.MaterialFetchRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.BoqCostDefinitionMaterialService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/boq_cost_definition_material")
@RestController
@CrossOrigin
public class BoqCostDefinitionMaterialController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private BoqCostDefinitionMaterialService boqCostDefinitionMaterialService;

	@Autowired
	private CustomValidationUtil validate;

	@RequestMapping(value = "/v1/add", method = RequestMethod.POST)
	public ResponseEntity<?> addBoqCostDefinitionMaterial(
			@RequestBody(required = true) BoqCostDefinitionMaterialAddUpdateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = validate.addBoqCostDefinitionMaterial(requestDTO);

		if (response.getStatus().equals(HttpStatus.OK.value())) {
			SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
			response = roleAuthorityUtil.checkAddAuthority(search);
		}

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))

			response = boqCostDefinitionMaterialService.addBoqCostDefinitionMaterial(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBoqCostDefinitionMaterial(
			@RequestBody(required = true) BoqCostDefinitionMaterialAddUpdateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validate.updateBoqCostDefinitionMaterial(requestDTO);
		if (response.getStatus().equals(HttpStatus.OK.value())) {

			SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
			response = roleAuthorityUtil.checkUpdateAuthority(search);

		}
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionMaterialService.updateBoqCostDefinitionMaterial(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all/{boq_cost_definition_id}/{site_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBoqCostDefinitionMaterialByBoqCostDefinitionId(
			@PathVariable(value = "boq_cost_definition_id", required = true) Long boqCostDefinitionId,
			@PathVariable(value = "site_id", required = true) Long siteId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(siteId, user.getRoleId(), EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionMaterialService
					.getBoqCostDefinitionMaterialByBoqCostDefinitionId(boqCostDefinitionId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateBoqCostDefinitionMaterial(
			@RequestBody(required = true) BoqCostDefinitionMaterialDeactivateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionMaterialService.deactivateBoqCostDefinitionMaterial(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/get/material_list", method = RequestMethod.POST)
	public ResponseEntity<?> getMaterialList(@RequestBody(required = true) MaterialFetchRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		SearchDTO search = new SearchDTO(requestDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionMaterialService.getMaterialList(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

}
