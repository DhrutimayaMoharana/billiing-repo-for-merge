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
import erp.boq_mgmt.dto.request.ProjectPlanBoqAddUpdateRequest;
import erp.boq_mgmt.dto.request.ProjectPlanBoqFetchRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.ProjectPlanBoqService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/project_plan_boq")
@RestController
@CrossOrigin
public class ProjectPlanBoqController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private ProjectPlanBoqService projectPlanBoqService;

	@Autowired
	private CustomValidationUtil validate;

	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addUpdateProjectPlanBoq(
			@RequestBody(required = true) ProjectPlanBoqAddUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = validate.addUpdateProjectPlanBoq(requestDTO);

		if (response.getStatus().equals(HttpStatus.OK.value())) {
			SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());
			response = roleAuthorityUtil.checkAddAuthority(search);
		}

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))

			response = projectPlanBoqService.addUpdateProjectPlanBoq(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getProjectPlanBoq(@RequestBody(required = true) ProjectPlanBoqFetchRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = projectPlanBoqService.getProjectPlanBoq(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/structure-type/{boq_id}/{site_id}", method = RequestMethod.POST)
	public ResponseEntity<?> getStructureTypeAndStructureByBoqId(
			@PathVariable(value = "boq_id", required = true) Long boqId,
			@PathVariable(value = "site_id", required = true) Integer siteId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_COST_DEFINITION.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = projectPlanBoqService.getStructureTypeAndStructureByBoqId(boqId, siteId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

}
