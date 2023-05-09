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
import erp.boq_mgmt.dto.request.BoqMasterLmpsMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsMachineryDeactivateRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.BoqMasterLmpsMachineryService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/boq_master_lmps_machinery")
@RestController
@CrossOrigin
public class BoqMasterLmpsMachineryController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private BoqMasterLmpsMachineryService boqMasterLmpsMachineryService;

	@Autowired
	private CustomValidationUtil validate;

	@RequestMapping(value = "/v1/add", method = RequestMethod.POST)
	public ResponseEntity<?> addBoqMasterLmpsLabour(
			@RequestBody(required = true) BoqMasterLmpsMachineryAddUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = validate.addBoqMasterLmpsMachinery(requestDTO);

		if (response.getStatus().equals(HttpStatus.OK.value())) {
			SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_MASTER_LMPS.getEntityId());
			response = roleAuthorityUtil.checkAddAuthority(search);
		}

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqMasterLmpsMachineryService.addBoqMasterLmpsMachinery(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBoqMasterLmpsMachinery(
			@RequestBody(required = true) BoqMasterLmpsMachineryAddUpdateRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validate.updateBoqMasterLmpsMachinery(requestDTO);
		if (response.getStatus().equals(HttpStatus.OK.value())) {

			SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_MASTER_LMPS.getEntityId());
			response = roleAuthorityUtil.checkUpdateAuthority(search);

		}
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqMasterLmpsMachineryService.updateBoqMasterLmpsMachinery(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all/{boq_cost_definition_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBoqMasterLmpsMachineryByBoqMasterLmpsId(
			@PathVariable(value = "boq_cost_definition_id", required = true) Long boqMasterLmpsId,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_MASTER_LMPS.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqMasterLmpsMachineryService.getBoqMasterLmpsMachineryByBoqMasterLmpsId(boqMasterLmpsId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateBoqMasterLmpsLabour(
			@RequestBody(required = true) BoqMasterLmpsMachineryDeactivateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_MASTER_LMPS.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqMasterLmpsMachineryService.deactivateBoqMasterLmpsMachinery(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/get/machinery-trip", method = RequestMethod.GET)
	public ResponseEntity<?> getMachineryTrip(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.BOQ_MASTER_LMPS.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = boqMasterLmpsMachineryService.getMachineryTrip();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

}
