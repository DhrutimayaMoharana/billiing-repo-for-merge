package erp.workorder.controller;

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

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.WorkorderBillInfoUpdateRequest;
import erp.workorder.dto.request.WorkorderRenewRequest;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;

@RequestMapping(value = "/workorder")
@RestController
@CrossOrigin
public class WorkorderController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private CustomValidationUtil validate;

	@Autowired
	private WorkorderService woService;

	@RequestMapping(value = "/workorders", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorders(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.getWorkorders(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/final/workorders", method = RequestMethod.POST)
	public ResponseEntity<?> getFinalApprovedWorkorders(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.getFinalApprovedWorkorders(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/finish_draft", method = RequestMethod.POST)
	public ResponseEntity<?> finishDraft(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.finishDraft(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/id_workorder", method = RequestMethod.POST)
	public ResponseEntity<?> printRenderWorkorderById(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.renderWorkorderById(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/id_workorder_equipment_material", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderMaterialEquipmentById(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_CONFIG.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.getWorkorderMaterialEquipmentById(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/wo_state", method = RequestMethod.PUT)
	public ResponseEntity<?> changeWorkorderState(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.changeWorkorderState(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderNextStates(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.getWorkorderNextStates(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/sites/workorders", method = RequestMethod.POST)
	public ResponseEntity<?> getSitesWorkorders(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = woService.getSitesWorkorders(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/get/workorder-bill-info/{workorder-id}/{site-id}", method = RequestMethod.GET)
	public ResponseEntity<?> getWorkorderBillInfoByWorkorderId(
			@PathVariable(value = "workorder-id", required = true) Long workorderId,
			@PathVariable(value = "site-id", required = true) Long siteId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		SearchDTO search = new SearchDTO();
		search.setSiteId(siteId);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.getWorkorderBillInfoByWorkorderId(workorderId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/update/workorder-bill-info", method = RequestMethod.PUT)
	public ResponseEntity<?> updateWorkorderBillInfo(
			@RequestBody(required = true) WorkorderBillInfoUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO();
		if (user != null && user.getId() != null) {
			requestDTO.setUserDetail(user);
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
			search.setSiteId(requestDTO.getSiteId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.workorderBillInfoUpdateRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.updateWorkorderBillInfo(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/renew", method = RequestMethod.PUT)
	public ResponseEntity<?> renewWorkorder(@RequestBody(required = true) WorkorderRenewRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO();
		if (user != null && user.getId() != null) {
			requestDTO.setUserDetail(user);
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
			search.setSiteId(requestDTO.getSiteId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woService.renewWorkorder(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/export_workorder", method = RequestMethod.POST)
	public ResponseEntity<?> exportWorkOrder(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateSearchRequestForWorkOrder(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode())) {
			response = woService.exportWorkOrderExcel(search);
		}

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}
}