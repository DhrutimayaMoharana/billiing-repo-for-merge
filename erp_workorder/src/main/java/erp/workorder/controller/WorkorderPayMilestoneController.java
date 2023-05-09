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
import erp.workorder.dto.request.WorkorderPayMilestoneAddUpdateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneDeactivateRequest;
import erp.workorder.dto.request.WorkorderPayMilestoneGetRequest;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderPayMilestoneService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/pay_milestone")
@RestController
@CrossOrigin
public class WorkorderPayMilestoneController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderPayMilestoneService payTermService;

	@ApiOperation(value = "Get Workorder Payment Milestones")
	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderWorkorderPayMilestones(
			@RequestBody(required = true) WorkorderPayMilestoneGetRequest payTermReq, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			payTermReq.setTokenDetails(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(payTermReq.getSiteId(), payTermReq.getTokenDetails().getRoleId(),
				EntitiesEnum.WORKORDER_PAY_MILESTONES.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = payTermService.getWorkorderWorkorderPayMilestones(payTermReq);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Add or Update Workorder Payment Milestone")
	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addUpdateWorkorderWorkorderPayMilestone(
			@RequestBody(required = true) WorkorderPayMilestoneAddUpdateRequest payTermReq, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			payTermReq.setTokenDetails(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(payTermReq.getSiteId(), payTermReq.getTokenDetails().getRoleId(),
				EntitiesEnum.WORKORDER_PAY_MILESTONES.getEntityId());
		CustomResponse response = null;
		if (payTermReq.getId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = payTermService.addUpdateWorkorderWorkorderPayMilestone(payTermReq);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Deactivate Workorder Payment Milestone")
	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateWorkorderWorkorderPayMilestone(
			@RequestBody(required = true) WorkorderPayMilestoneDeactivateRequest payTermReq, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			payTermReq.setTokenDetails(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(payTermReq.getSiteId(), payTermReq.getTokenDetails().getRoleId(),
				EntitiesEnum.WORKORDER_PAY_MILESTONES.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = payTermService.deactivateWorkorderWorkorderPayMilestone(payTermReq);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
