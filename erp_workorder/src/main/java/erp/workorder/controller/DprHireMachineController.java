package erp.workorder.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.MachineDPRImportExcelRequest;
import erp.workorder.dto.request.MachineDprAddUpdateRequest;
import erp.workorder.dto.request.MachineDprDeactivateRequest;
import erp.workorder.dto.request.MachineDprGetRequest;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.AuthorityService;
import erp.workorder.service.DprHireMachineService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/dpr/machine")
@RestController
@CrossOrigin
public class DprHireMachineController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private DprHireMachineService dprService;

	@Autowired
	private CustomValidationUtil validate;

	@ApiOperation(value = "Add or Update Machine DPR")
	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateMachineDPR(@RequestBody(required = true) MachineDprAddUpdateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			requestDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId(), requestDTO.getUserDetail().getRoleId(),
				EntitiesEnum.Machine_Dpr.getEntityId());
		CustomResponse response;
		if (requestDTO.getId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = dprService.addOrUpdateMachineDPR(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Remove Machine DPR")
	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateMachineDPR(@RequestBody(required = true) MachineDprDeactivateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			requestDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId(), requestDTO.getUserDetail().getRoleId(),
				EntitiesEnum.Machine_Dpr.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = dprService.deactivateMachineDPR(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Machine DPR")
	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getMachineDPR(@RequestBody(required = true) MachineDprGetRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			requestDTO.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId(), requestDTO.getUserDetail().getRoleId(),
				EntitiesEnum.Machine_Dpr.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = dprService.getMachineDPR(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/import_excel", method = RequestMethod.POST)
	public ResponseEntity<?> importMachineDPRExcel(@RequestParam(name = "file") MultipartFile excelFile,
			@ModelAttribute MachineDPRImportExcelRequest importExcelRequest, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			importExcelRequest.setUserDetail(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validate.importMachineDPRExcelRequest(importExcelRequest);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = dprService.importMachineDPRExcel(excelFile, importExcelRequest);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Machinery Attendance Status")
	@RequestMapping(value = "/v1/get-machinery-attendance-status", method = RequestMethod.GET)
	public ResponseEntity<?> getMachineryAttendnaceStatus(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = dprService.getMachineryAttendnaceStatus();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Machinery Running Mode")
	@RequestMapping(value = "/v1/get-machinery-running-mode", method = RequestMethod.GET)
	public ResponseEntity<?> getMachineryRunningMode(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = dprService.getMachineryRunningMode();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Machinery Shifts")
	@RequestMapping(value = "/v1/get-machinery-shifts", method = RequestMethod.GET)
	public ResponseEntity<?> getMachineryShifts(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = dprService.getMachineryShifts();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
