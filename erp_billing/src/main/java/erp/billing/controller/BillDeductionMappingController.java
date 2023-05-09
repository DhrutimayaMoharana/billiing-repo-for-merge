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

import erp.billing.dto.BillDeductionMappingDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.UserDetail;
import erp.billing.enums.EntitiesEnum;
import erp.billing.enums.Responses;
import erp.billing.feignClient.service.AuthorityService;
import erp.billing.service.BillDeductionMappingService;
import erp.billing.util.LogUtil;
import erp.billing.util.SetObject;

@RequestMapping(value = "/bill_deduction_map")
@RestController
@CrossOrigin
public class BillDeductionMappingController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BillDeductionMappingService deductionMapService;

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@RequestMapping(value = "/deductions", method = RequestMethod.POST)
	public ResponseEntity<?> getMappedBillDeductions(@RequestBody(required = true) SearchDTO search,
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
			response = deductionMapService.getDeductions(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/deduction", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateBillDeductionMap(@RequestBody(required = true) BillDeductionMappingDTO mapObj,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		mapObj.setCreatedBy(user.getId());
		mapObj.setCompanyId(user.getCompanyId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(mapObj.getSiteId(), user.getRoleId(), EntitiesEnum.BILL.getEntityId());
		search.setCompanyId(user.getCompanyId());
		CustomResponse response = null;
		if (mapObj.getId() == null)
			response = roleAuthorityUtil.checkAddAuthority(search);
		else
			response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = deductionMapService.addOrUpdateBillDeductionMap(mapObj);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);
	}

}
