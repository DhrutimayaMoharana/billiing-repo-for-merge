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
import erp.workorder.dto.request.AmendWorkorderInvocationAddUpdateRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationDeactivateRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationGetRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationNextActionsRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationUpdateStateRequest;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.AmendWorkorderInvocationService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;

@RequestMapping(value = "/invoke_amendment")
@RestController
@CrossOrigin
public class AmendWorkorderInvocationController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private AmendWorkorderInvocationService invokeAmendmentService;

	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderAmendmentInvocation(
			@RequestBody(required = true) AmendWorkorderInvocationGetRequest clientRequestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = invokeAmendmentService.getWorkorderAmendmentInvocation(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderAmendmentInvocation(
			@RequestBody(required = true) AmendWorkorderInvocationAddUpdateRequest clientRequestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = null;
		if (clientRequestDTO.getId() == null)
			response = roleAuthorityUtil.checkAddAuthority(authoritySearchObj);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = invokeAmendmentService.addOrUpdateWorkorderAmendmentInvocation(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateWorkorderAmendmentInvocation(
			@RequestBody(required = true) AmendWorkorderInvocationDeactivateRequest clientRequestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = invokeAmendmentService.deactivateWorkorderAmendmentInvocation(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/state", method = RequestMethod.PUT)
	public ResponseEntity<?> updateWorkorderAmendmentInvocationState(
			@RequestBody(required = true) AmendWorkorderInvocationUpdateStateRequest clientRequestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = invokeAmendmentService.updateWorkorderAmendmentInvocationState(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(
			@RequestBody(required = true) AmendWorkorderInvocationNextActionsRequest clientRequestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null)
			clientRequestDTO.setUserDetail(user);
		SearchDTO authoritySearchObj = new SearchDTO(clientRequestDTO.getUserDetail().getCompanyId(),
				clientRequestDTO.getUserDetail().getId(), clientRequestDTO.getSiteId(),
				clientRequestDTO.getUserDetail().getRoleId(), EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(authoritySearchObj);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = invokeAmendmentService.getNextPossibleStates(clientRequestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}
}
