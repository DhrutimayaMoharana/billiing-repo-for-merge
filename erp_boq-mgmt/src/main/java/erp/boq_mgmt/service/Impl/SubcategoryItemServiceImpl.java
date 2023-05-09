package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.SubcategoryItemDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.SubcategoryItemDTO;
import erp.boq_mgmt.entity.SubcategoryItem;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.SubcategoryItemService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class SubcategoryItemServiceImpl implements SubcategoryItemService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SubcategoryItemDao subcatDao;
	@Autowired
	private SetObject setObject;

	public Integer getCount(SearchDTO search) {
		return subcatDao.fetchCount(search);
	}

	@Override
	public CustomResponse addSubcategoryItem(SubcategoryItemDTO subcategoryDTO) {

		try {
			SubcategoryItem subcat = setObject.subcategoryItemDtoToEntity(subcategoryDTO);
			subcat.setCreatedOn(new Date());
			subcat.setModifiedOn(new Date());
			subcat.setModifiedBy(subcat.getModifiedBy());
			Long id = subcatDao.saveSubcategoryItem(subcat);
			return new CustomResponse(Responses.SUCCESS.getCode(),
					((id != null && id.longValue() > 0L) ? id : "Already exists..."), Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateSubcategoryItem(SubcategoryItemDTO subcategoryDTO) {

		try {
			SearchDTO search = new SearchDTO();
			search.setCompanyId(subcategoryDTO.getCompanyId());
			List<SubcategoryItem> subcats = subcatDao.fetchSubcategoryItems(search);
			if (subcats != null) {
				for (SubcategoryItem obj : subcats) {
					if (obj.getStandardBookCode() != null
							&& subcategoryDTO.getStandardBookCode() != null
									& obj.getStandardBookCode().equalsIgnoreCase(subcategoryDTO.getStandardBookCode())
							&& !obj.getId().equals(subcategoryDTO.getId())) {

						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"SDB Code attached with other Subcategory BOQ!");
					}
				}
			}

			SubcategoryItem subcat = setObject
					.updatedSubcategoryItem(
							subcatDao.fetchSubcategoryItemByIdOrName(subcategoryDTO.getId(), null,
									subcategoryDTO.getCompanyId()),
							setObject.subcategoryItemDtoToEntity(subcategoryDTO));

			Boolean updateItem = subcatDao.updateSubcategoryItem(subcat);
			return new CustomResponse(Responses.SUCCESS.getCode(), updateItem, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getSubcategoryItemByIdOrName(SearchDTO search) {

		try {
			SubcategoryItem subcat = subcatDao.fetchSubcategoryItemByIdOrName(search.getId(), search.getName(),
					search.getCompanyId());
			SubcategoryItemDTO result = setObject.subcategoryItemEntityToDto(subcat);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getSubcategoryItems(SearchDTO search) {

		try {
			List<SubcategoryItem> items = subcatDao.fetchSubcategoryItems(search);
			List<SubcategoryItemDTO> obj = new ArrayList<>();
			if (items != null)
				items.forEach(item -> obj.add(setObject.subcategoryItemEntityToDto(item)));
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

}
