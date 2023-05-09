package erp.boq_mgmt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsFetchRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemNextPossibleStatesFetchRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.RfiChecklistItemService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/rfi_checklist_item")
@RestController
@CrossOrigin
public class RfiChecklistItemController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private RfiChecklistItemService checklistItemService;

	@Autowired
	private CustomValidationUtil validate;

	@RequestMapping(value = "/v1/add", method = RequestMethod.POST)
	public ResponseEntity<?> addRfiChecklistItem(
			@RequestBody(required = true) RfiChecklistItemBoqsAddUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateAddRfiChecklistItemRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = checklistItemService.addRfiChecklistItem(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRfiChecklistItem(
			@RequestBody(required = true) RfiChecklistItemBoqsAddUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateUpdateRfiChecklistItemRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = checklistItemService.updateRfiChecklistItem(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiChecklistItemById(@PathVariable(value = "id", required = true) Integer id,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = checklistItemService.getRfiChecklistItemById(id);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all", method = RequestMethod.POST)
	public ResponseEntity<?> getRfiChecklistItem(
			@RequestBody(required = true) RfiChecklistItemBoqsFetchRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateFetchRfiChecklistItemRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = checklistItemService.getRfiChecklistItemList(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all/final_success", method = RequestMethod.POST)
	public ResponseEntity<?> getRfiChecklistItemFinalSuccessList(
			@RequestBody(required = true) RfiChecklistItemFinalSuccessFetchRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateFetchRfiChecklistItemFinalSuccessRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = checklistItemService.getRfiChecklistItemFinalSuccessList(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateRfiChecklistItem(
			@RequestBody(required = true) RfiChecklistItemDeactivateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = checklistItemService.deactivateRfiChecklistItem(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(
			@RequestBody(required = true) RfiChecklistItemNextPossibleStatesFetchRequest requestObj,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());

		if (user != null && user.getId() != null) {
			requestObj.setUser(user);
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = checklistItemService.getNextPossibleStates(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "v1/get/state_transition/{rfiChecklistItemId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiChecklistItemStateTransitionByRfiChecklistItemId(
			@PathVariable(value = "rfiChecklistItemId", required = true) Integer rfiChecklistItemId,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = checklistItemService.getRfiChecklistItemStateTransitionByRfiChecklistItemId(rfiChecklistItemId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
