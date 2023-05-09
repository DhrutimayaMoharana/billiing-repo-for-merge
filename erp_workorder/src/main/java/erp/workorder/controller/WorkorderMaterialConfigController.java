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
import erp.workorder.dto.WorkorderMaterialConfigDTO;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderMaterialConfigService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;

@RequestMapping(value = "/material_config")
@RestController
@CrossOrigin
public class WorkorderMaterialConfigController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderMaterialConfigService woMaterialConfigService;

	@RequestMapping(value = "/groups", method = RequestMethod.POST)
	public ResponseEntity<?> getMaterialGroups(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_CONFIG.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woMaterialConfigService.getMaterialGroups(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/issue_material", method = RequestMethod.POST)
	public ResponseEntity<?> issueMaterial(@RequestBody(required = true) WorkorderMaterialConfigDTO woMaterialIssue,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		woMaterialIssue.setModifiedBy(user.getId());
		woMaterialIssue.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(woMaterialIssue.getSiteId(), user.getRoleId(),
				EntitiesEnum.WORKORDER_CONFIG.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woMaterialConfigService.issueMaterial(woMaterialIssue);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/deactivate_issued_material", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateIssuedMaterial(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_CONFIG.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woMaterialConfigService.deactivateIssuedMaterial(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/issue_material", method = RequestMethod.PUT)
	public ResponseEntity<?> updateIssuedMaterial(
			@RequestBody(required = true) WorkorderMaterialConfigDTO materialConfig, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		materialConfig.setModifiedBy(user.getId());
		materialConfig.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(materialConfig.getSiteId(), user.getRoleId(),
				EntitiesEnum.WORKORDER_CONFIG.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woMaterialConfigService.updateIssuedMaterial(materialConfig);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
