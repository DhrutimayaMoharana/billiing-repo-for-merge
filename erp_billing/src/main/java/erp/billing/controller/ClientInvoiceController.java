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
import erp.billing.dto.request.ClientInvoiceAddUpdateRequest;
import erp.billing.dto.request.ClientInvoiceExportExcelRequest;
import erp.billing.dto.request.ClientInvoiceFetchRequest;
import erp.billing.dto.request.ClientInvoiceNextPossibleStatesFetchRequest;
import erp.billing.dto.request.ClientInvoicePdfRequest;
import erp.billing.dto.request.ClientIrnCancelRequest;
import erp.billing.dto.request.ClientIrnGeneratedRequest;
import erp.billing.enums.EntitiesEnum;
import erp.billing.enums.Responses;
import erp.billing.feignClient.service.AuthorityService;
import erp.billing.service.ClientInvoiceService;
import erp.billing.util.LogUtil;
import erp.billing.util.SetObject;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/client-invoice")
@RestController
@CrossOrigin
public class ClientInvoiceController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClientInvoiceService clientInvoiceService;

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@ApiOperation(value = "Add or Update Client Invoice")
	@RequestMapping(value = "v1/add_or_update", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateClientInvoice(
			@RequestBody(required = true) ClientInvoiceAddUpdateRequest requestObj, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			requestObj.setUpdatedBy(user.getId());
			requestObj.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(requestObj.getSiteId().longValue(), user.getRoleId(),
				EntitiesEnum.Client_Invoice.getEntityId());
		search.setCompanyId(user.getCompanyId());
		CustomResponse response = null;
		if (requestObj.getId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = clientInvoiceService.addOrUpdateClientInvoice(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Client Invoice List")
	@RequestMapping(value = "v1/get/all", method = RequestMethod.POST)
	public ResponseEntity<?> getClientInvoiceList(@RequestBody(required = true) ClientInvoiceFetchRequest requestObj,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(requestObj.getSiteId().longValue(), user.getRoleId(),
				EntitiesEnum.Client_Invoice.getEntityId());

		if (user != null && user.getId() != null) {
			requestObj.setUser(new UserDetail());
			requestObj.setUser(user);
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = clientInvoiceService.getClientInvoiceList(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Export Client Invoice Excel")
	@RequestMapping(value = "v1/export_excel", method = RequestMethod.POST)
	public ResponseEntity<?> exportClientInvoiceExcel(
			@RequestBody(required = true) ClientInvoiceExportExcelRequest requestObj, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(requestObj.getSiteId().longValue(), user.getRoleId(),
				EntitiesEnum.Client_Invoice.getEntityId());

		if (user != null && user.getId() != null) {
			requestObj.setUser(new UserDetail());
			requestObj.setUser(user);
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = clientInvoiceService.exportClientInvoiceExcel(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Client Invoice State Transition By Client Invoice Id")
	@RequestMapping(value = "v1/get/state_transition/{clientInvoiceId}/{siteId}", method = RequestMethod.GET)
	public ResponseEntity<?> getClientInvoiceStateTransitionByClientInvoiceId(
			@PathVariable(value = "clientInvoiceId", required = true) Long clientInvoiceId,
			@PathVariable(value = "siteId", required = true) Integer siteId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(siteId.longValue(), user.getRoleId(),
				EntitiesEnum.Client_Invoice.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = clientInvoiceService.getClientInvoiceTransitionByClientInvoiceId(clientInvoiceId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Get Client Invoice By Id")
	@RequestMapping(value = "v1/get/{id}/{siteId}", method = RequestMethod.GET)
	public ResponseEntity<?> getClientInvoiceById(@PathVariable(value = "id", required = true) Long clientInvoiceId,
			@PathVariable(value = "siteId", required = true) Integer siteId, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(siteId.longValue(), user.getRoleId(),
				EntitiesEnum.Client_Invoice.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = clientInvoiceService.getClientInvoiceById(clientInvoiceId);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Deactivate Client Invoice")
	@RequestMapping(value = "v1/deactivate/{id}/{siteId}", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateClientInvoiceById(@PathVariable(value = "id", required = true) Long id,
			@PathVariable(value = "siteId", required = true) Long siteId, HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(siteId, user.getRoleId(), EntitiesEnum.Client_Invoice.getEntityId());

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = clientInvoiceService.deactivateClientInvoiceById(id, user.getId());

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(
			@RequestBody(required = true) ClientInvoiceNextPossibleStatesFetchRequest requestObj,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		SearchDTO search = new SearchDTO(requestObj.getSiteId(), user.getRoleId(),
				EntitiesEnum.Client_Invoice.getEntityId());

		if (user != null && user.getId() != null) {
			requestObj.setUser(user);
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.Client_Invoice.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = clientInvoiceService.getNextPossibleStates(requestObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/generate_irn", method = RequestMethod.POST)
	public ResponseEntity<?> generateIrn(@RequestBody(required = true) ClientIrnGeneratedRequest requestObj,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		if (user != null && user.getId() != null) {
			requestObj.setUserDetail(user);
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = clientInvoiceService.generateIrn(requestObj.getClientInvoiceId(),
				requestObj.getUserDetail());

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Cancel Irn")
	@RequestMapping(value = "/v1/cancel", method = RequestMethod.POST)
	public ResponseEntity<?> cancelIrn(@RequestBody(required = true) ClientIrnCancelRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		if (user != null && user.getId() != null) {
			requestDTO.setUserDetail(user);
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = clientInvoiceService.cancelIrn(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@ApiOperation(value = "Export Client Invoice Pdf ")
	@RequestMapping(value = "/v1/export_pdf", method = RequestMethod.POST)
	public ResponseEntity<?> exportClientInvoicePdf(@RequestBody(required = true) ClientInvoicePdfRequest requestDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		if (user != null && user.getId() != null) {
			requestDTO.setUserDetail(user);
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = clientInvoiceService.exportClientInvoicePdf(requestDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}
}
