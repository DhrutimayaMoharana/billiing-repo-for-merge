package erp.boq_mgmt.controller;

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

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.dto.request.RfiBoqGetExecutableQuantityRequest;
import erp.boq_mgmt.dto.request.RfiMainAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainByStateActionFetchRequest;
import erp.boq_mgmt.dto.request.RfiMainDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiMainExportSummaryRequest;
import erp.boq_mgmt.dto.request.RfiMainFetchRequest;
import erp.boq_mgmt.dto.request.RfiMainNextPossibleStatesFetchRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.RfiMainService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/rfi_main")
@RestController
@CrossOrigin
public class RfiMainController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RfiMainService rfiService;

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private CustomValidationUtil validate;

	@RequestMapping(value = "/v1/add", method = RequestMethod.POST)
	public ResponseEntity<?> addRfiMain(@RequestBody(required = true) RfiMainAddUpdateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId() != null ? requestDTO.getSiteId().longValue() : null,
				user.getRoleId(), EntitiesEnum.RFI_MAIN.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateAddRfiMainRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.addRfiMain(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRfiMain(@RequestBody(required = true) RfiMainAddUpdateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId() != null ? requestDTO.getSiteId().longValue() : null,
				user.getRoleId(), EntitiesEnum.RFI_MAIN.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateUpdateRfiMainRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.updateRfiMain(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/{siteId}/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiMainById(@PathVariable(value = "siteId", required = true) Integer siteId,
			@PathVariable(value = "id", required = true) Long id,

			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(siteId != null ? siteId.longValue() : null, user.getRoleId(),
				EntitiesEnum.RFI_MAIN.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.getRfiMainById(id, user.getCompanyId());

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all", method = RequestMethod.POST)
	public ResponseEntity<?> getAllRfiMain(@RequestBody(required = true) RfiMainFetchRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId() != null ? requestDTO.getSiteId().longValue() : null,
				user.getRoleId(), EntitiesEnum.RFI_MAIN.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateFetchRfiMainRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.getAllRfiMain(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/export_rfi_summary", method = RequestMethod.POST)
	public ResponseEntity<?> exportRfiMainSummary(@RequestBody(required = true) RfiMainExportSummaryRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId() != null ? requestDTO.getSiteId().longValue() : null,
				user.getRoleId(), EntitiesEnum.RFI_MAIN.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = validate.validateExportRfiSummary(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.exportRfiMainSummary(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/all/state-action", method = RequestMethod.POST)
	public ResponseEntity<?> getRfiMainListByStateAction(
			@RequestBody(required = true) RfiMainByStateActionFetchRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId() != null ? requestDTO.getSiteId().longValue() : null,
				user.getRoleId(), EntitiesEnum.RFI_MAIN.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.getRfiMainListByStateAction(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/get/print/{siteId}/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPrintRfiMainById(@PathVariable(value = "siteId", required = true) Integer siteId,
			@PathVariable(value = "id", required = true) Long id,

			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(siteId != null ? siteId.longValue() : null, user.getRoleId(),
				EntitiesEnum.RFI_MAIN.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.getPrintRfiMainById(id, user.getCompanyId());

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateRfiMain(@RequestBody(required = true) RfiMainDeactivateRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		requestDTO.setUserDetail(user);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestDTO.getSiteId() != null ? requestDTO.getSiteId().longValue() : null,
				user.getRoleId(), EntitiesEnum.RFI_MAIN.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.deactivateRfiMain(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/executable-work", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkItemExecutableQuantity(
			@RequestBody(required = true) RfiBoqGetExecutableQuantityRequest requestDTO, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = validate.validateRfiBoqGetExecutableQuantityRequest(requestDTO);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.getWorkItemExecutableQuantity(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/modes", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiModes(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = rfiService.getRfiModes();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/work-types", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiWorkTypes(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = rfiService.getRfiWorkTypes();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/comment-types", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiCommentTypes(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = rfiService.getRfiCommentTypes();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(
			@RequestBody(required = true) RfiMainNextPossibleStatesFetchRequest requestObj, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(requestObj.getSiteId().longValue(), user.getRoleId(),
				EntitiesEnum.RFI_MAIN.getEntityId());

		if (user != null && user.getId() != null) {
			requestObj.setUser(user);
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.getNextPossibleStates(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "v1/get/state_transition/{rfiMainId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRfiMainStateTransitionByRfiMainId(
			@PathVariable(value = "rfiMainId", required = true) Long rfiMainId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(null, user.getRoleId(), EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = rfiService.getRfiMainStateTransitionByRfiMainId(rfiMainId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
