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
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.DocumentSubTypeService;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/doc_subtype")
@RestController
@CrossOrigin
public class DocumentSubTypeController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private DocumentSubTypeService docSubtypeService;

	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getDocSubTypes(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
			search.setRoleId(user.getRoleId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId());
		CustomResponse response = authorityService.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = docSubtypeService.getDocSubTypes(search);
		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

}
