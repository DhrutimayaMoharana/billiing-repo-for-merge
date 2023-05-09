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
import erp.boq_mgmt.dto.request.BoqCostDefinitionUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionAddRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionDeactivateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.request.UndefinedCostDefinitionBoqsFetchRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.BoqCostDefinitionService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/boq_cost_definition")
@RestController
@CrossOrigin
public class BoqCostDefinitionController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private BoqCostDefinitionService boqCostDefinitionService;

	@Autowired
	private CustomValidationUtil validate;

	@RequestMapping(value = "/v1/add", method = RequestMethod.POST)
	public ResponseEntity<?> addBoqCostDefinition(@RequestBody(required = true) BoqCostDefinitionAddRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validate.addBoqCostDefinition(requestDTO);
		if (response.getStatus().equals(HttpStatus.OK.value())) {

			SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
			response = roleAuthorityUtil.checkAddAuthority(search);

		}
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService.addBoqCostDefinition(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBoqCostDefinition(
			@RequestBody(required = true) BoqCostDefinitionUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validate.updateBoqCostDefinition(requestDTO);
		if (response.getStatus().equals(HttpStatus.OK.value())) {

			SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
			response = roleAuthorityUtil.checkUpdateAuthority(search);

		}
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService.updateBoqCostDefinition(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/{id}/{site_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBoqCostDefinitionById(@PathVariable(value = "id", required = true) Long id,
			@PathVariable(value = "site_id", required = true) Integer siteId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(siteId.longValue(), user.getRoleId(),
				EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService.getBoqCostDefinitionById(id);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all", method = RequestMethod.POST)
	public ResponseEntity<?> getBoqCostDefinition(
			@RequestBody(required = true) BoqCostDefinitionFetchRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
				EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService.getBoqCostDefinition(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all/final_success", method = RequestMethod.POST)
	public ResponseEntity<?> getBoqCostDefinitionFinalSuccessList(
			@RequestBody(required = true) BoqCostDefinitionFinalSuccessFetchRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
				EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService.getBoqCostDefinitionFinalSuccessList(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateBoqCostDefinition(
			@RequestBody(required = true) BoqCostDefinitionDeactivateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
				EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService.deactivateBoqCostDefinition(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(
			@RequestBody(required = true) BoqCostDefinitionNextPossibleStatesFetchRequest requestObj,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());

		if (user != null && user.getId() != null) {
			requestObj.setUser(user);
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService.getNextPossibleStates(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "v1/get/state_transition/{boqCostDefinitionId}/{siteId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBoqCostDefinitionStateTransitionByBoqCostDefinitionId(
			@PathVariable(value = "boqCostDefinitionId", required = true) Long boqCostDefinitionId,
			@PathVariable(value = "siteId", required = true) Long siteId, HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(siteId, user.getRoleId(), EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService
					.getBoqCostDefinitionStateTransitionByBoqCostDefinitionId(boqCostDefinitionId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/get/undefined_boqs", method = RequestMethod.POST)
	public ResponseEntity<?> getUndefinedCostDefinitionBoqs(
			@RequestBody(required = true) UndefinedCostDefinitionBoqsFetchRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		SearchDTO search = new SearchDTO(requestDTO.getSiteId().longValue(), user.getRoleId(),
				EntitiesEnum.BOQ.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqCostDefinitionService.getUndefinedCostDefinitionBoqs(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

}
