package erp.boq_mgmt.controller;

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

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.UnitDTO;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.UnitService;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/unit")
@RestController
@CrossOrigin
public class UnitController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private UnitService unitService;

	@RequestMapping(value = "/unit", method = RequestMethod.POST)
	public ResponseEntity<?> addUnit(@RequestBody(required = true) UnitDTO unitDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		unitDTO.setCreatedBy(user.getId());
		unitDTO.setCompanyId(user.getCompanyId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(unitDTO.getSiteId(), user.getRoleId(), EntitiesEnum.UNIT.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = unitService.addUnit(unitDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/units", method = RequestMethod.POST)
	public ResponseEntity<?> getUnits(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
			search.setRoleId(user.getRoleId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.UNIT.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = unitService.getUnits(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/unit_types", method = RequestMethod.POST)
	public ResponseEntity<?> getUnitTypes(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.UNIT.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = unitService.getUnitTypes(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

}
