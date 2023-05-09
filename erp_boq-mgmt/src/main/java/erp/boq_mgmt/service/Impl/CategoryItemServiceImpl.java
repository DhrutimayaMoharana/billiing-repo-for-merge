package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.BoqItemDao;
import erp.boq_mgmt.dao.BorewellBoqMappingDao;
import erp.boq_mgmt.dao.CategoryItemDao;
//import erp.boq_mgmt.dao.HighwayBoqMapDao;
import erp.boq_mgmt.dao.StructureBoqQuantityDao;
import erp.boq_mgmt.dto.CategoryItemDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.response.CategoryItemResponse;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.CategoryItem;
import erp.boq_mgmt.entity.GenericBoqMappingHighway;
//import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.enums.HighwayWorkOrderTypes;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.WorkorderTypes;
import erp.boq_mgmt.service.CategoryItemService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class CategoryItemServiceImpl implements CategoryItemService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private CategoryItemDao categoryDao;
//	@Autowired
//	private HighwayBoqMapDao hbmDao;
	@Autowired
	private BorewellBoqMappingDao bbmDao;
	@Autowired
	private StructureBoqQuantityDao sbqDao;
	@Autowired
	private CustomValidationUtil validationUtil;
	@Autowired
	private BoqItemDao boqDao;

	@Override
	public CustomResponse addCategoryItem(CategoryItemDTO categoryDTO) {

		try {
			CustomResponse response = validationUtil.validateAddCategoryItem(categoryDTO);
			if (!response.getStatus().equals(Responses.SUCCESS.getCode())) {
				return response;
			}
			SearchDTO search = new SearchDTO();
			search.setCompanyId(categoryDTO.getCompanyId());
			List<CategoryItem> categories = categoryDao.fetchCategoryItems(search);
			if (categories != null) {
				for (CategoryItem item : categories) {
					if (item.getCode().replaceAll("[^A-Za-z0-9]", "").trim().toLowerCase()
							.startsWith(categoryDTO.getCode().replaceAll("[^A-Za-z0-9]", "").trim().toLowerCase())
							|| categoryDTO.getCode().replaceAll("[^A-Za-z0-9]", "").trim().toLowerCase()
									.startsWith(item.getCode().replaceAll("[^A-Za-z0-9]", "").trim().toLowerCase())) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Category code in conflict with category having code : " + item.getCode());
					}
					if (item.getStandardBookCode() != null && item.getStandardBookCode().toLowerCase()
							.equals(categoryDTO.getStandardBookCode().toLowerCase())) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Category SDB code in conflict with category having SDB code : "
										+ item.getStandardBookCode());
					}
				}
			}
			CategoryItem category = setObject.categoryItemDtoToEntity(categoryDTO);
			category.setOfStructure(false);
			category.setIsActive(true);
			category.setCreatedOn(new Date());
			category.setModifiedOn(new Date());
			category.setModifiedBy(category.getCreatedBy());

			Long id = categoryDao.saveCategoryItem(category);

			return new CustomResponse(Responses.SUCCESS.getCode(), null,
					((id != null && id.longValue() > 0L) ? "Added." : "Already exists."));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateCategoryItem(CategoryItemDTO categoryDTO) {

		try {
			CustomResponse response = validationUtil.validateUpdateCategoryItem(categoryDTO);
			if (!response.getStatus().equals(Responses.SUCCESS.getCode())) {
				return response;
			}
			SearchDTO search = new SearchDTO();
			search.setCompanyId(categoryDTO.getCompanyId());
			CategoryItem dbObj = categoryDao.fetchCategoryItemById(categoryDTO.getId());
			if (dbObj == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), "Provide valid category Id.",
						Responses.SUCCESS.toString());
			CategoryItem categoryObj = setObject.categoryItemDtoToEntity(categoryDTO);
			CategoryItem category = setObject.updatedCategoryItem(dbObj, categoryObj);

			Boolean isUpdated = categoryDao.updateCategoryItem(category);

			return new CustomResponse(Responses.SUCCESS.getCode(), null,
					isUpdated != null && isUpdated ? "Updated." : "Could not update.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getCategoryItems(SearchDTO search) {

		try {
			if (search.getCompanyId() == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Assign company to user.");
			List<CategoryItem> items = categoryDao.fetchCategoryItems(search);
			List<CategoryItemResponse> obj = new ArrayList<>();
			if (items != null)
				items.forEach(item -> obj.add(new CategoryItemResponse(item.getId(), item.getCode(),
						item.getStandardBookCode(), item.getName())));
			Integer count = getCount(search);
			PaginationDTO resultObj = new PaginationDTO(count, obj);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	private Integer getCount(SearchDTO search) {

		return categoryDao.fetchCount(search);
	}

	@Override
	public CustomResponse getCategoryItemById(SearchDTO search) {

		try {
			if (search.getCompanyId() == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Assign company to user.");
			if (search.getCategoryId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide categoryId.");
			}
			CategoryItem category = categoryDao.fetchCategoryItemById(search.getCategoryId());
			CategoryItemResponse result = new CategoryItemResponse(category.getId(), category.getCode(),
					category.getStandardBookCode(), category.getName());
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getTypewiseCategoryItems(SearchDTO search) {

		try {
			List<Integer> highwayWoTypeIds = new ArrayList<>();
			for (HighwayWorkOrderTypes woType : HighwayWorkOrderTypes.values()) {
				highwayWoTypeIds.add(woType.getTypeId());
			}
			List<CategoryItem> items = categoryDao.fetchCategoryItems(search);
			Set<Long> keepCategoriesId = new HashSet<>();
			if (highwayWoTypeIds.contains(search.getTypeId().intValue())) {
				List<GenericBoqMappingHighway> bbms = bbmDao.fetchGenricCategoriesBoqs(search);
				if (bbms != null) {
					for (GenericBoqMappingHighway bbm : bbms) {
						if (bbm.getCategory() != null)
							keepCategoriesId.add(bbm.getCategory().getId());
					}
				}

			} else if (search.getTypeId().intValue() == WorkorderTypes.Structure.getTypeId()) {
				List<StructureBoqQuantityMapping> sbqs = sbqDao.fetchStructureBoqQuantityMappingBySearch(search);
				if (sbqs != null) {
					for (StructureBoqQuantityMapping sbq : sbqs) {
						if (sbq.getCategory() != null)
							keepCategoriesId.add(sbq.getCategory().getId());
					}
				}
			} else if (search.getTypeId().intValue() == WorkorderTypes.Consultant.getTypeId()) {
				if (items != null) {
					for (CategoryItem category : items) {
						keepCategoriesId.add(category.getId());
					}
				}
			}
			List<CategoryItemDTO> obj = new ArrayList<>();
			if (items != null) {
				for (Long catId : keepCategoriesId) {
					for (CategoryItem category : items) {
						if (catId.equals(category.getId())) {
							obj.add(setObject.categoryItemEntityToDto(category));
						}
					}
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateCategoryItemById(SearchDTO search) {

		try {
			if (search.getCompanyId() == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Assign company to user.");
			if (search.getCategoryId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide categoryId.");
			}
			CategoryItem category = categoryDao.fetchCategoryItemById(search.getCategoryId());
			if (category == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid categoryId.");
			search.setSearchField(null);
			List<BoqItem> boqs = boqDao.fetchBoqItems(search);
			if (boqs != null && boqs.size() > 0) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
						"BOQ exists with this category, cannot remove.");
			}
			category.setIsActive(false);
			category.setModifiedOn(new Date());
			category.setModifiedBy(search.getUserId());
			categoryDao.forceUpdateCategoryItem(category);
			return new CustomResponse(Responses.SUCCESS.getCode(), true, "Removed.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
