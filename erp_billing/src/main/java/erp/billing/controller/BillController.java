
package erp.billing.controller;

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

import erp.billing.dto.BillDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.UserDetail;
import erp.billing.enums.EntitiesEnum;
import erp.billing.enums.Responses;
import erp.billing.feignClient.service.AuthorityService;
import erp.billing.service.BillService;
import erp.billing.util.LogUtil;
import erp.billing.util.SetObject;

@RequestMapping(value = "/bill")
@RestController
@CrossOrigin
public class BillController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BillService billService;

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@RequestMapping(value = "/bills", method = RequestMethod.POST)
	public ResponseEntity<?> getBills(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		if (user != null && user.getId() != null) {

			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billService.getBills(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/id_bill", method = RequestMethod.POST)
	public ResponseEntity<?> getBillById(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billService.getBillById(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/bill", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateBill(@RequestBody(required = true) BillDTO billObj, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			billObj.setCreatedBy(user.getId());
			billObj.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(billObj.getSiteId(), user.getRoleId(), EntitiesEnum.BILL.getEntityId());
		search.setCompanyId(user.getCompanyId());
		CustomResponse response = null;
		if (billObj.getId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billService.addOrUpdateBill(billObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/print_data", method = RequestMethod.POST)
	public ResponseEntity<?> getBillPrintResponseData(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);

		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billService.getBillPrintResponseData(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/state", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBillState(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {

			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.BILL.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billService.updateBillState(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.BILL.getEntityId());

		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billService.getNextPossibleStates(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/price_escalation", method = RequestMethod.POST)
	public ResponseEntity<?> getBillPriceEscalationData(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billService.getBillPriceEscalationData(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
