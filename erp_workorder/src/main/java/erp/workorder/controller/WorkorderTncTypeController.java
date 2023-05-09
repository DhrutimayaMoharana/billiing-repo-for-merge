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
import erp.workorder.dto.WoTncTypeDTO;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.WorkorderTncTypeService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;

@RequestMapping(value = "/wo_tnc_type")
@RestController
@CrossOrigin
public class WorkorderTncTypeController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private WorkorderTncTypeService woTncTypeService;

	@RequestMapping(value = "/wo_tnc_types", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderTncTypes(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_TNC_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTncTypeService.getWorkorderTncTypes(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/wo_tnc_type", method = RequestMethod.POST)
	public ResponseEntity<?> addWorkorderTncType(@RequestBody(required = true) WoTncTypeDTO tncTypeDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		tncTypeDTO.setCreatedBy(user.getId());
		tncTypeDTO.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(tncTypeDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.WORKORDER_TNC_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTncTypeService.addWorkorderTncType(tncTypeDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/wo_tnc_type", method = RequestMethod.PUT)
	public ResponseEntity<?> updateWorkorderTncType(@RequestBody(required = true) WoTncTypeDTO tncTypeDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		tncTypeDTO.setCreatedBy(user.getId());
		tncTypeDTO.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(tncTypeDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.WORKORDER_TNC_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTncTypeService.updateWorkorderTncType(tncTypeDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/id_wo_tnc_type", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderTncTypeById(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_TNC_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTncTypeService.getWorkorderTncTypeById(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/remove_tnc_type", method = RequestMethod.POST)
	public ResponseEntity<?> removeWorkorderTncType(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.WORKORDER_TNC_TYPE.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = woTncTypeService.removeWorkorderTncType(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
