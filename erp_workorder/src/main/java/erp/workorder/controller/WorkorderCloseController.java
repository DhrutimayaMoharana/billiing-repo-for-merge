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
import erp.workorder.dto.request.WorkorderCloseAddUpdateRequest;
import erp.workorder.dto.request.WorkorderCloseDeactivateRequest;
import erp.workorder.dto.request.WorkorderCloseGetRequest;
import erp.workorder.dto.request.WorkorderCloseNextActionsRequest;
import erp.workorder.dto.request.WorkorderCloseUpdateStateRequest;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderCloseService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;

@RequestMapping(value = "/workorder-close")
@RestController
@CrossOrigin
public class WorkorderCloseController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderCloseService workorderCloseService;

	@Autowired
	private CustomValidationUtil validationUtil;

	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderClose(@RequestBody(required = true) WorkorderCloseGetRequest clientRequestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.WORKORDER_CLOSE.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validationUtil.validateGetWorkorderCloseRequest(clientRequestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = roleAuthorityUtil.checkViewAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderCloseService.getWorkorderClose(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderClose(
			@RequestBody(required = true) WorkorderCloseAddUpdateRequest clientRequestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.WORKORDER_CLOSE.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validationUtil.validateAddOrUpdateWorkorderClose(clientRequestDTO);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			if (clientRequestDTO.getId() == null)
				response = roleAuthorityUtil.checkAddAuthority(authoritySearchObj);
			else
				response = roleAuthorityUtil.checkUpdateAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderCloseService.addOrUpdateWorkorderClose(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateWorkorderClose(
			@RequestBody(required = true) WorkorderCloseDeactivateRequest clientRequestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.WORKORDER_CLOSE.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validationUtil.validateDeactivateWorkorderClose(clientRequestDTO);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = roleAuthorityUtil.checkRemoveAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderCloseService.deactivateWorkorderClose(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/state", method = RequestMethod.PUT)
	public ResponseEntity<?> updateWorkorderClose(
			@RequestBody(required = true) WorkorderCloseUpdateStateRequest clientRequestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.WORKORDER_CLOSE.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validationUtil.validateUpdateWorkorderCloseState(clientRequestDTO);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = roleAuthorityUtil.checkUpdateAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderCloseService.updateWorkorderCloseState(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(
			@RequestBody(required = true) WorkorderCloseNextActionsRequest clientRequestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.WORKORDER_CLOSE.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderCloseService.getNextPossibleStates(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/close_type", method = RequestMethod.GET)
	public ResponseEntity<?> getWorkorderCloseType(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			LOGGER.info(
					LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = workorderCloseService.getWorkorderCloseType();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
