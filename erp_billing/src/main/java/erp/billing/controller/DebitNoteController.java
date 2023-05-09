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

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.UserDetail;
import erp.billing.enums.EntitiesEnum;
import erp.billing.enums.Responses;
import erp.billing.feignClient.service.AuthorityService;
import erp.billing.service.DebitNoteService;
import erp.billing.util.LogUtil;
import erp.billing.util.SetObject;

@RequestMapping(value = "/debit_note")
@RestController
@CrossOrigin
public class DebitNoteController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DebitNoteService debitNoteService;

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@RequestMapping(value = "/bill_note_items", method = RequestMethod.POST)
	public ResponseEntity<?> getBillDebitNoteItems(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = debitNoteService.getBillDebitNoteItems(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/workorder_note_items", method = RequestMethod.POST)
	public ResponseEntity<?> getWorkorderDebitNoteItems(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);

		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = debitNoteService.getWorkorderDebitNoteItems(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
