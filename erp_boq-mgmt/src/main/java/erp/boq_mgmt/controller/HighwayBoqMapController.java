//package erp.boq_mgmt.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import erp.boq_mgmt.dto.CustomResponse;
//import erp.boq_mgmt.dto.HighwayBoqMappingDTO;
//import erp.boq_mgmt.dto.SearchDTO;
//import erp.boq_mgmt.dto.UserDetail;
//import erp.boq_mgmt.enums.EntitiesEnum;
//import erp.boq_mgmt.enums.Responses;
//import erp.boq_mgmt.feignClient.service.AuthorityService;
//import erp.boq_mgmt.service.HighwayBoqMapService;
//import erp.boq_mgmt.util.LogUtil;
//import erp.boq_mgmt.util.SetObject;
//
//@RequestMapping(value = "/category_boq")
//@RestController
//@CrossOrigin
//public class HighwayBoqMapController {
//
//	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private AuthorityService roleAuthorityUtil;
//
//	@Autowired
//	private HighwayBoqMapService bcmService;
//
//	@RequestMapping(value = "/map_category_boq", method = RequestMethod.POST)
//	public ResponseEntity<?> mapCategoryBoq(@RequestBody(required = true) HighwayBoqMappingDTO bcmDTO,
//			HttpServletRequest request) throws Exception {
//
//		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
//		bcmDTO.setModifiedBy(user.getId());
//		bcmDTO.setCompanyId(user.getCompanyId());
//
//		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		SearchDTO search = new SearchDTO(bcmDTO.getSiteId(), user.getRoleId(), EntitiesEnum.HIGHWAY_BOQ.getEntityId());
//		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
//		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
//			response = bcmService.mapCategoryBoq(bcmDTO);
//
//		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		return ResponseEntity.ok(response);
//
//	}
//
//	@RequestMapping(value = "/id_category_boq", method = RequestMethod.POST)
//	public ResponseEntity<?> getCategoryBoqById(@RequestBody(required = true) SearchDTO search,
//			HttpServletRequest request) throws Exception {
//
//		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
//		search.setUserId(user.getId());
//		search.setCompanyId(user.getCompanyId());
//		search.setRoleId(user.getRoleId());
//
//		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		search.setEntityId(EntitiesEnum.HIGHWAY_BOQ.getEntityId());
//		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
//		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
//			response = bcmService.getCategoryBoqById(search);
//
//		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		return ResponseEntity.ok(response);
//
//	}
//
//	@RequestMapping(value = "/categories_boqs", method = RequestMethod.POST)
//	public ResponseEntity<?> getCategoriesBoqs(@RequestBody(required = true) SearchDTO search,
//			HttpServletRequest request) throws Exception {
//
//		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
//		search.setUserId(user.getId());
//		search.setCompanyId(user.getCompanyId());
//		search.setRoleId(user.getRoleId());
//
//		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		search.setEntityId(EntitiesEnum.HIGHWAY_BOQ.getEntityId());
//		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
//		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
//			response = bcmService.getCategoriesBoqs(search);
//
//		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		return ResponseEntity.ok(response);
//
//	}
//
//	@RequestMapping(value = "/highway_boqs", method = RequestMethod.POST)
//	public ResponseEntity<?> getHighwayBoqs(@RequestBody(required = true) SearchDTO search, HttpServletRequest request)
//			throws Exception {
//
//		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
//		search.setUserId(user.getId());
//		search.setCompanyId(user.getCompanyId());
//		search.setRoleId(user.getRoleId());
//
//		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		search.setEntityId(EntitiesEnum.HIGHWAY_BOQ.getEntityId());
//		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
//		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
//			response = bcmService.getHighwayBoqs(search);
//
//		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		return ResponseEntity.ok(response);
//
//	}
//
//	@RequestMapping(value = "/category_boq", method = RequestMethod.PUT)
//	public ResponseEntity<?> updateCategoryBoq(@RequestBody(required = true) HighwayBoqMappingDTO bcmDTO,
//			HttpServletRequest request) throws Exception {
//
//		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
//		bcmDTO.setModifiedBy(user.getId());
//		bcmDTO.setCompanyId(user.getCompanyId());
//
//		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		SearchDTO search = new SearchDTO(bcmDTO.getSiteId(), user.getRoleId(), EntitiesEnum.HIGHWAY_BOQ.getEntityId());
//		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
//		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
//			response = bcmService.updateCategoryBoq(bcmDTO);
//
//		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		return ResponseEntity.ok(response);
//
//	}
//
//	@RequestMapping(value = "/import_highway_boq_excel", method = RequestMethod.POST)
//	public ResponseEntity<?> importHighwayBoqQuantityMappingExcel(@RequestParam(name = "file") MultipartFile excelFile,
//			@ModelAttribute SearchDTO search, HttpServletRequest request) throws Exception {
//
//		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
//		search.setUserId(user.getId());
//		search.setCompanyId(user.getCompanyId());
//		search.setRoleId(user.getRoleId());
//
//		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		search.setEntityId(EntitiesEnum.HIGHWAY_BOQ.getEntityId());
//		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
//		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
//			response = bcmService.importHighwayBoqQuantityMappingExcel(excelFile, search);
//
//		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		return ResponseEntity.ok(response);
//
//	}
//
//	@RequestMapping(value = "/deactivate_highway_boq", method = RequestMethod.POST)
//	public ResponseEntity<?> deactivateHighwayBoqMapping(@RequestBody(required = true) SearchDTO search,
//			HttpServletRequest request) throws Exception {
//
//		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
//		search.setUserId(user.getId());
//		search.setCompanyId(user.getCompanyId());
//		search.setRoleId(user.getRoleId());
//
//		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		search.setEntityId(EntitiesEnum.HIGHWAY_BOQ.getEntityId());
//		CustomResponse response = roleAuthorityUtil.checkRemoveAuthority(search);
//		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
//			response = bcmService.deactivateHighwayBoqMapping(search);
//
//		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
//
//		return ResponseEntity.ok(response);
//
//	}
//
//}
