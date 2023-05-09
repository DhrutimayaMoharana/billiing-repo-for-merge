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
import erp.workorder.dto.request.WorkorderHiringMachineWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderHiringMachineWorkItemDeactivateRequestDTO;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderHiringMachineWorkService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/hire/machine")
@RestController
@CrossOrigin
public class WorkorderHiringMachineWorkController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderHiringMachineWorkService hmWorkService;

	@ApiOperation(value = "Add or Update Workorder Hiring Machine")
	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderHiringMachineWork(
			@RequestBody(required = true) WorkorderHiringMachineWorkAddUpdateRequestDTO hmWorkDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			hmWorkDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(hmWorkDTO.getSiteId(), hmWorkDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_HiringMachine_WORK.getEntityId());
		CustomResponse response;
		if (hmWorkDTO.getHmWorkId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = hmWorkService.addOrUpdateWorkorderHiringMachineWork(hmWorkDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Add or Update Workorder Hiring Machine Work Item")
	@RequestMapping(value = "/v1/item/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderHiringMachineWorkItem(
			@RequestBody(required = true) WorkorderHiringMachineWorkItemAddUpdateRequest hmWorkItemDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			hmWorkItemDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(hmWorkItemDTO.getSiteId(), hmWorkItemDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_HiringMachine_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = hmWorkService.addOrUpdateWorkorderHiringMachineWorkItem(hmWorkItemDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Remove Workorder Hiring Machine Work Item")
	@RequestMapping(value = "/v1/item/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateWorkorderHiringMachineWorkItem(
			@RequestBody(required = true) WorkorderHiringMachineWorkItemDeactivateRequestDTO hmWorkItemDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			hmWorkItemDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(hmWorkItemDTO.getSiteId(), hmWorkItemDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_HiringMachine_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = hmWorkService.deactivateWorkorderHiringMachineWorkItem(hmWorkItemDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workorder Hiring Machine Work")
	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderHiringMachineWork(
			@RequestBody(required = true) WorkorderHiringMachineWorkGetRequestDTO hmWorkDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			hmWorkDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(hmWorkDTO.getSiteId(), hmWorkDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_HiringMachine_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = hmWorkService.getWorkorderHiringMachineWork(hmWorkDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workorder Hiring Machine Units")
	@RequestMapping(value = "unit/v1/get", method = RequestMethod.GET)
	public ResponseEntity<?> getWorkorderHiringMachineUnits(HttpServletRequest request) throws Exception {

		CustomResponse response = hmWorkService.getWorkorderHiringMachineUnits();

		return ResponseEntity.ok(response);

	}

}
