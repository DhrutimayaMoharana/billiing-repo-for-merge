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
import erp.billing.service.BillFileService;
import erp.billing.util.LogUtil;
import erp.billing.util.SetObject;

@RequestMapping(value = "/file")
@RestController
@CrossOrigin
public class BillFileController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BillFileService billFileService;

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getBillFiles(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billFileService.getBillFiles(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/add", method = RequestMethod.POST)
	public ResponseEntity<?> addBillFiles(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billFileService.addBillFiles(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateBillFiles(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setRoleId(user.getRoleId());
		search.setCompanyId(user.getCompanyId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.BILL.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = billFileService.deactivateBillFiles(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
