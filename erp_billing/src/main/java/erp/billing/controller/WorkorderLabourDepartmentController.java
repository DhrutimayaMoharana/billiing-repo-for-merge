package erp.billing.controller;

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

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.UserDetail;
import erp.billing.dto.request.WorkorderLabourDepartmentAddUpdateRequest;
import erp.billing.enums.EntitiesEnum;
import erp.billing.enums.Responses;
import erp.billing.feignClient.service.AuthorityService;
import erp.billing.service.WorkorderLabourDepartmentService;
import erp.billing.util.LogUtil;
import erp.billing.util.SetObject;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/wo_labour_department")
@RestController
@CrossOrigin
public class WorkorderLabourDepartmentController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderLabourDepartmentService workorderLabourDepartmentService;
	
	@Autowired
	private AuthorityService roleAuthorityUtil;

	@ApiOperation(value = "Add Workorder Labour Department")
	@RequestMapping(value = "v1/add_or_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderLabourDepartment(
			@RequestBody(required = true) WorkorderLabourDepartmentAddUpdateRequest requestObj,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			requestObj.setUserId(user.getId().intValue());
			requestObj.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.WORKORDER_LABOUR_MASTER.getEntityId());
		search.setCompanyId(user.getCompanyId());
		CustomResponse response = null;
		if (requestObj.getId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderLabourDepartmentService.addOrUpdateWorkorderLabourDepartment(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workorder Labour Department List")
	@RequestMapping(value = "v1/get/all/{companyId}", method = RequestMethod.GET)
	public ResponseEntity<?> getWorkorderLabourDepartmentList(
			@PathVariable(value = "companyId", required = true) Integer companyId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.WORKORDER_LABOUR_MASTER.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderLabourDepartmentService.getWorkorderLabourDepartmentList(companyId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workflow Labour Department By Id")
	@RequestMapping(value = "v1/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getWorkflowLabourDepartmentById(@PathVariable(value = "id", required = true) Integer id,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.WORKORDER_LABOUR_MASTER.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderLabourDepartmentService.getWorkorderLabourDepartmentById(id);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Deactivate Workflow Labour Department")
	@RequestMapping(value = "v1/deactivate/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateWorkorderLabourDepartmentById(
			@PathVariable(value = "id", required = true) Integer id, HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.WORKORDER_LABOUR_MASTER.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_LABOUR_MASTER.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = workorderLabourDepartmentService.deactivateWorkorderLabourDepartment(id, user.getId());

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
