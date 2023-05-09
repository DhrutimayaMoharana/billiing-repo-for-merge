package erp.workorder.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.WorkorderTransportationWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderTransportationWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderTransportationWorkItemDeactivateRequestDTO;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderTransportationWorkService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/transportation")
@RestController
@CrossOrigin
public class WorkorderTransportationWorkController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderTransportationWorkService transportWorkService;

	@ApiOperation(value = "Add or Update Workorder Transportation Work", notes = "Do not send ID field in case of update.")
	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderTransportationWork(
			@RequestBody(required = true) WorkorderTransportationWorkAddUpdateRequestDTO transportWorkDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			transportWorkDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(transportWorkDTO.getSiteId(), transportWorkDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_TRANSPORT_WORK.getEntityId());
		CustomResponse response;
		if (transportWorkDTO.getTransportWorkId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = transportWorkService.addOrUpdateWorkorderTransportationWork(transportWorkDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Add or Update Workorder Transportation Work Item", notes = "Do not send ID field in case of update.")
	@RequestMapping(value = "/v1/item/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderTransportationWorkItem(
			@RequestBody(required = true) WorkorderTransportationWorkItemAddUpdateRequest trasportWorkItemDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			trasportWorkItemDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(trasportWorkItemDTO.getSiteId(),
				trasportWorkItemDTO.getUserDetail().getRoleId(), EntitiesEnum.WORKORDER_TRANSPORT_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = transportWorkService.addOrUpdateWorkorderTransportationWorkItem(trasportWorkItemDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Remove Workorder Transportation Work Item")
	@RequestMapping(value = "/v1/item/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateWorkorderTransportationWorkItem(
			@RequestBody(required = true) WorkorderTransportationWorkItemDeactivateRequestDTO transportWorkItemDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			transportWorkItemDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(transportWorkItemDTO.getSiteId(),
				transportWorkItemDTO.getUserDetail().getRoleId(), EntitiesEnum.WORKORDER_TRANSPORT_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = transportWorkService.deactivateWorkorderTransportationWorkItem(transportWorkItemDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workorder Transportation Work")
	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderTransportationWork(
			@RequestBody(required = true) WorkorderTransportationWorkGetRequestDTO transportWorkDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			transportWorkDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(transportWorkDTO.getSiteId(), transportWorkDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_TRANSPORT_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = transportWorkService.getWorkorderTransportationWork(transportWorkDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workorder Transportation BOQ Descriptions")
	@RequestMapping(value = "/boq", method = RequestMethod.GET)
	public ResponseEntity<?> getWorkorderTransportationBoqDescriptions(
			@RequestParam(required = false) Integer companyId, HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			companyId = user.getCompanyId();
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = transportWorkService.getWorkorderTransportationBoqDescriptions(companyId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}
}
