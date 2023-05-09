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
import erp.workorder.dto.request.WorkorderConsultantWorkAddUpdateRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkGetRequestDTO;
import erp.workorder.dto.request.WorkorderConsultantWorkItemAddUpdateRequest;
import erp.workorder.dto.request.WorkorderConsultantWorkItemDeactivateRequestDTO;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderConsultantWorkService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/consultant_work")
@RestController
@CrossOrigin
public class WorkorderConsultantWorkController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderConsultantWorkService consultantWorkService;

	@ApiOperation(value = "Add or Update Workorder Consultant Work", notes = "Do not send ID field in case of update.")
	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderConsultantWork(
			@RequestBody(required = true) WorkorderConsultantWorkAddUpdateRequestDTO consultantWorkDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			consultantWorkDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(consultantWorkDTO.getSiteId(), consultantWorkDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_CONSULTANT_WORK.getEntityId());
		CustomResponse response;
		if (consultantWorkDTO.getConsultantWorkId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = consultantWorkService.addOrUpdateWorkorderConsultantWork(consultantWorkDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Add or Update Workorder Consultant Work Item", notes = "Do not send ID field in case of update.")
	@RequestMapping(value = "/v1/item/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateWorkorderConsultantWorkItem(
			@RequestBody(required = true) WorkorderConsultantWorkItemAddUpdateRequest consultantWorkItemDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			consultantWorkItemDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(consultantWorkItemDTO.getSiteId(),
				consultantWorkItemDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_CONSULTANT_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = consultantWorkService.addOrUpdateWorkorderConsultantWorkItem(consultantWorkItemDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Remove Workorder Consultant Work Item")
	@RequestMapping(value = "/v1/item/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateWorkorderConsultantWorkItem(
			@RequestBody(required = true) WorkorderConsultantWorkItemDeactivateRequestDTO consultantWorkItemDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			consultantWorkItemDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(consultantWorkItemDTO.getSiteId(),
				consultantWorkItemDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_CONSULTANT_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = consultantWorkService.deactivateWorkorderConsultantWorkItem(consultantWorkItemDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workorder Consultant Work")
	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderConsultantWork(
			@RequestBody(required = true) WorkorderConsultantWorkGetRequestDTO consultantWorkDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			consultantWorkDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(consultantWorkDTO.getSiteId(), consultantWorkDTO.getUserDetail().getRoleId(),
				EntitiesEnum.WORKORDER_CONSULTANT_WORK.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = consultantWorkService.getWorkorderConsultantWork(consultantWorkDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Workorder Consultant Work Item Category Descriptions")
	@RequestMapping(value = "/category_text/v1/get", method = RequestMethod.GET)
	public ResponseEntity<?> getWorkorderConsultantWorkItemCategoryDescriptions(
			@RequestParam(required = false) Integer companyId, HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			companyId = user.getCompanyId();
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = consultantWorkService.getWorkorderConsultantWorkItemCategoryDescriptions(companyId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
