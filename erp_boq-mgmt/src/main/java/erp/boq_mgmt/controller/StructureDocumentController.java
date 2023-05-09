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
import erp.boq_mgmt.dto.request.StructureDocumentDeactivateRequest;
import erp.boq_mgmt.dto.request.StructureDocumentRequest;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.StructureDocumentService;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/structure_doc")
@RestController
@CrossOrigin
public class StructureDocumentController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private StructureDocumentService documentService;

	@RequestMapping(value = "/v1/get", method = RequestMethod.POST)
	public ResponseEntity<?> getStructureDocuments(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

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
			response = documentService.getStructureDocuments(search);
		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/v1/add_update", method = RequestMethod.POST)
	public ResponseEntity<?> addUpdateStructureDocument(
			@RequestBody(required = true) StructureDocumentRequest docRequest, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user.getId() != null) {
			docRequest.setUser(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(docRequest.getSiteId(), docRequest.getUser().getRoleId(),
				EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId());
		CustomResponse response = null;
		if (docRequest.getId() == null)
			response = authorityService.checkAddAuthority(search);
		else
			response = authorityService.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = documentService.addUpdateStructureDocument(docRequest);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/v1/add_files", method = RequestMethod.POST)
	public ResponseEntity<?> addStructureDocumentFiles(
			@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
			search.setRoleId(user.getRoleId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId());
		CustomResponse response = authorityService.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = documentService.addStructureDocumentFiles(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/v1/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deactivateStructureDocumentFiles(
			@RequestBody(required = true) StructureDocumentDeactivateRequest docRequest, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user.getId() != null) {
			docRequest.setUser(user);
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(docRequest.getSiteId(), docRequest.getUser().getRoleId(),
				EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId());
		CustomResponse response = authorityService.checkRemoveAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = documentService.deactivateStructureDocumentFiles(docRequest);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/v1/get/revisions", method = RequestMethod.POST)
	public ResponseEntity<?> getStructureDocumentRevisions(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
			search.setRoleId(user.getRoleId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.STRUCTURE_DOCUMENT_VERSION.getEntityId());
		CustomResponse response = authorityService.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = documentService.getStructureDocumentRevisions(search);
		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/v1/get/revision", method = RequestMethod.POST)
	public ResponseEntity<?> getStructureDocumentRevision(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user.getId() != null) {
			search.setUserId(user.getId());
			search.setCompanyId(user.getCompanyId());
			search.setRoleId(user.getRoleId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.STRUCTURE_DOCUMENT_VERSION.getEntityId());
		CustomResponse response = authorityService.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = documentService.getStructureDocumentRevision(search);
		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/v1/state", method = RequestMethod.PUT)
	public ResponseEntity<?> updateDocState(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
			throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId());

		CustomResponse response = authorityService.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = documentService.updateDocState(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/v1/next_action", method = RequestMethod.POST)
	public ResponseEntity<?> getNextPossibleStates(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		if (user != null && user.getId() != null) {
			search.setUserId(user.getId());
			search.setRoleId(user.getRoleId());
			search.setCompanyId(user.getCompanyId());
		}
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		search.setEntityId(EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId());

		CustomResponse response = authorityService.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = documentService.getNextPossibleStates(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
