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
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryDeactivateRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.BoqCostDefinitionMachineryService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/boq_cost_definition_machinery")
@RestController
@CrossOrigin
public class BoqCostDefinitionMachineryController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private BoqCostDefinitionMachineryService boqCostDefinitionMachineryService;

	@Autowired
	private CustomValidationUtil validate;

	@RequestMapping(value = "/v1/add", method = RequestMethod.POST)
	public ResponseEntity<?> addBoqCostDefinitionLabour(
			@RequestBody(required = true) BoqCostDefinitionMachineryAddUpdateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = validate.addBoqCostDefinitionMachinery(requestDTO);

		if (response.getStatus().equals(HttpStatus.OK.value())) {
			SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
			response = roleAuthorityUtil.checkAddAuthority(search);
		}

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionMachineryService.addBoqCostDefinitionMachinery(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBoqCostDefinitionMachinery(
			@RequestBody(required = true) BoqCostDefinitionMachineryAddUpdateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validate.updateBoqCostDefinitionMachinery(requestDTO);
		if (response.getStatus().equals(HttpStatus.OK.value())) {

			SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
			response = roleAuthorityUtil.checkUpdateAuthority(search);

		}
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionMachineryService.updateBoqCostDefinitionMachinery(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all/{boq_cost_definition_id}/{site_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBoqCostDefinitionMachineryByBoqCostDefinitionId(
			@PathVariable(value = "boq_cost_definition_id", required = true) Long boqCostDefinitionId,
			@PathVariable(value = "site_id", required = true) Long siteId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(siteId, user.getRoleId(), EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionMachineryService
					.getBoqCostDefinitionMachineryByBoqCostDefinitionId(boqCostDefinitionId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateBoqCostDefinitionLabour(
			@RequestBody(required = true) BoqCostDefinitionMachineryDeactivateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionMachineryService.deactivateBoqCostDefinitionMachinery(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/get/machinery-trip", method = RequestMethod.GET)
	public ResponseEntity<?> getMachineryTrip(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = boqCostDefinitionMachineryService.getMachineryTrip();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

}
