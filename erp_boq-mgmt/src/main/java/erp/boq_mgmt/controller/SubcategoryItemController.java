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
import erp.boq_mgmt.dto.SubcategoryItemDTO;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.AuthorityService;
import erp.boq_mgmt.service.SubcategoryItemService;
import erp.boq_mgmt.util.LogUtil;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/subcategory_item")
@RestController
@CrossOrigin
public class SubcategoryItemController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityService roleAuthorityUtil;

	@Autowired
	private SubcategoryItemService subcatService;

	@RequestMapping(value = "/subcategory", method = RequestMethod.POST)
	public ResponseEntity<?> addSubcategoryItem(@RequestBody(required = true) SubcategoryItemDTO subcategoryDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		subcategoryDTO.setCreatedBy(user.getId());
		subcategoryDTO.setCompanyId(user.getCompanyId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(subcategoryDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.CATEGORY.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkAddAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = subcatService.addSubcategoryItem(subcategoryDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/subcategory", method = RequestMethod.PUT)
	public ResponseEntity<?> updateSubcategoryItem(@RequestBody(required = true) SubcategoryItemDTO subcategoryDTO,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		subcategoryDTO.setModifiedBy(user.getId());
		subcategoryDTO.setCompanyId(user.getCompanyId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		SearchDTO search = new SearchDTO(subcategoryDTO.getSiteId(), user.getRoleId(),
				EntitiesEnum.CATEGORY.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkUpdateAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = subcatService.updateSubcategoryItem(subcategoryDTO);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/id_name_subcategory", method = RequestMethod.POST)
	public ResponseEntity<?> getSubcategoryItemByIdOrName(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.CATEGORY.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = subcatService.getSubcategoryItemByIdOrName(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/subcategories", method = RequestMethod.POST)
	public ResponseEntity<?> getSubcategoryItems(@RequestBody(required = true) SearchDTO search,
			HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());

		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		search.setEntityId(EntitiesEnum.CATEGORY.getEntityId());
		CustomResponse response = roleAuthorityUtil.checkViewAuthority(search);
		if (response.getStatus().equals(Responses.SUCCESS.getCode()))
			response = subcatService.getSubcategoryItems(search);

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}

}
