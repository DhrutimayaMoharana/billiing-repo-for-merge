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
import erp.workorder.dto.WorkorderTypeDTO;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderTypeService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;

@RequestMapping(value = "/workorder_type")
@RestController
@CrossOrigin
public class WorkorderTypeController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderTypeService woTypeService;

	@RequestMapping(value = "/workorder_types", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderTypes(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTypeService.getWorkorderTypes(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/workorder_type", method = RequestMethod.POST)
	public ResponseEntity<?> addWorkorderType(@RequestBody(required = true) WorkorderTypeDTO typeDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		typeDTO.setModifiedBy(user.getId());
		typeDTO.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(typeDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.WORKORDER_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTypeService.addWorkorderType(typeDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/workorder_type", method = RequestMethod.PUT)
	public ResponseEntity<?> updateWorkorderType(@RequestBody(required = true) WorkorderTypeDTO typeDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		typeDTO.setModifiedBy(user.getId());
		typeDTO.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(typeDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.WORKORDER_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTypeService.updateWorkorderType(typeDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/id_workorder_type", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderTypeById(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTypeService.getWorkorderTypeById(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/remove_type", method = RequestMethod.POST)
	public ResponseEntity<?> removeWorkorderType(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTypeService.removeWorkorderType(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
