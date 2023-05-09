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
import org.springframework.web.bind.annotation.RestController;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.WorkorderLabourWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderLabourWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderLabourWorkItemDeactivateRequest;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderLabourWorkService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/labour")
@RestController
@CrossOrigin
public class WorkorderLabourWorkController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderLabourWorkService labourWorkService;

	@ApiOperation(value = "Add or Update Workorder Labour Work", notes = "Do not send ID field in case of update.")
	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderLabourWork(
			@RequestBody(required = true) WorkorderLabourWorkAddUpdateRequestDTO labourWorkDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			labourWorkDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(labourWorkDTO.getSiteId(), labourWorkDTO.getUserDetail().getRoleId(),
				EntitiesEnum.Workorder_Labour_Work.getEntityId());
		CustomResponse response;
		if (labourWorkDTO.getLabourWorkId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = labourWorkService.addOrUpdateWorkorderLabourWork(labourWorkDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Add or Update Workorder Labour Work Item", notes = "Do not send ID field in case of update.")
	@RequestMapping(value = "/v1/item/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderLabourWorkItem(
			@RequestBody(required = true) WorkorderLabourWorkItemAddUpdateRequest labourWorkItemDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			labourWorkItemDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(labourWorkItemDTO.getSiteId(), labourWorkItemDTO.getUserDetail().getRoleId(),
				EntitiesEnum.Workorder_Labour_Work.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = labourWorkService.addOrUpdateWorkorderLabourWorkItem(labourWorkItemDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Remove Workorder Labour Work Item")
	@RequestMapping(value = "/v1/item/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateWorkorderLabourWorkItem(
			@RequestBody(required = true) WorkorderLabourWorkItemDeactivateRequest labourWorkItemDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			labourWorkItemDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(labourWorkItemDTO.getSiteId(), labourWorkItemDTO.getUserDetail().getRoleId(),
				EntitiesEnum.Workorder_Labour_Work.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = labourWorkService.deactivateWorkorderLabourWorkItem(labourWorkItemDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workorder Labour Work")
	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderLabourWork(
			@RequestBody(required = true) WorkorderLabourWorkGetRequestDTO labourWorkDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			labourWorkDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(labourWorkDTO.getSiteId(), labourWorkDTO.getUserDetail().getRoleId(),
				EntitiesEnum.Workorder_Labour_Work.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = labourWorkService.getWorkorderLabourWork(labourWorkDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
