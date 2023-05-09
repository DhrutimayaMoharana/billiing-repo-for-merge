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
import erp.boq_mgmt.dto.request.RfiCustomWorkItemAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemNextPossibleStatesFetchRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.RfiCustomWorkItemsService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/rfi_customwork_item")
@RestController
@CrossOrigin
public class RfiCustomWorkItemsController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private RfiCustomWorkItemsService customWorkItemService;

	@Autowired
	private CustomValidationUtil validate;

	@RequestMapping(value = "/v1/add", method = RequestMethod.POST)
	public ResponseEntity<?> addRfiCustomWorkItem(
			@RequestBody(required = true) RfiCustomWorkItemAddUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = validate.addRfiCustomWorkItem(requestDTO);

		if (response.getStatus().equals(HttpStatus.OK.value())) {
			SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId());
			response = roleAuthorityUtil.checkAddAuthority(search);
		}

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))

			response = customWorkItemService.addRfiCustomWorkItem(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRfiCustomWorkItem(
			@RequestBody(required = true) RfiCustomWorkItemAddUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validate.updateRfiCustomWorkItem(requestDTO);
		if (response.getStatus().equals(HttpStatus.OK.value())) {

			SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId());
			response = roleAuthorityUtil.checkUpdateAuthority(search);

		}
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = customWorkItemService.updateRfiCustomWorkItem(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiCustomWorkItemById(@PathVariable(value = "id", required = true) Long id,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = customWorkItemService.getRfiCustomWorkItemById(id);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all", method = RequestMethod.POST)
	public ResponseEntity<?> getRfiCustomWorkItem(
			@RequestBody(required = true) RfiCustomWorkItemFetchRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = customWorkItemService.getRfiCustomWorkItem(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all/final_success", method = RequestMethod.POST)
	public ResponseEntity<?> getRfiCustomWorkItemFinalSuccessList(
			@RequestBody(required = true) RfiCustomWorkItemFinalSuccessFetchRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = customWorkItemService.getRfiCustomWorkItemFinalSuccessList(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateRfiCustomWorkItem(
			@RequestBody(required = true) RfiCustomWorkItemDeactivateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = customWorkItemService.deactivateRfiCustomWorkItem(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(
			@RequestBody(required = true) RfiCustomWorkItemNextPossibleStatesFetchRequest requestObj,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId());

		if (user != null && user.getId() != null) {
			requestObj.setUser(user);
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = customWorkItemService.getNextPossibleStates(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "v1/get/state_transition/{rfiCustomWorkItemId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiChecklistItemStateTransitionByRfiChecklistItemId(
			@PathVariable(value = "rfiCustomWorkItemId", required = true) Long rfiCustomWorkItemId,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = customWorkItemService
					.getRfiCustomWorkItemStateTransitionByRfiCustomWorkItemId(rfiCustomWorkItemId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
