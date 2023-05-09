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

import erp.boq_mgmt.dao.BoqItemDao;
import erp.boq_mgmt.dao.CategoryItemDao;
import erp.boq_mgmt.dao.HighwayBoqMapDao;
import erp.boq_mgmt.dao.StructureBoqQuantityDao;
import erp.boq_mgmt.dto.BoqItemDTO;
import erp.boq_mgmt.dto.CategoryBoqsRenderDTO;
import erp.boq_mgmt.dto.CategoryItemDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.response.WorkTypeBoqResponse;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.CategoryItem;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.RfiWorkType;
import erp.boq_mgmt.service.BoqItemService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class BoqItemServiceImpl implements BoqItemService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoqItemDao itemDao;
	@Autowired
	private SetObject setObject;
	@Autowired
	private CategoryItemDao categoryDao;
	@Autowired
	private HighwayBoqMapDao hbmDao;
	@Autowired
	private StructureBoqQuantityDao sbqDao;

	public Integer getCount(SearchDTO search) {
		return itemDao.fetchCount(search);
	}

	@Override
	public CustomResponse getCategoryBoqs(SearchDTO search) {

		try {
			List<BoqItem> boqList = itemDao.fetchBoqItems(search);
			List<BoqItemDTO> boqListDTO = new ArrayList<>();
			if (boqList != null) {
				for (BoqItem boq : boqList) {
					boqListDTO.add(setObject.boqItemEntityToDto(boq));
				}
			}
			if (boqListDTO != null && boqListDTO.size() > 0) {
				List<CategoryItem> categories = categoryDao.fetchCategoryItems(search);
				List<CategoryItemDTO> categoriesDTO = new ArrayList<>();
				for (CategoryItem category : categories)
					categoriesDTO.add(setObject.categoryItemEntityToDto(category));
				CategoryBoqsRenderDTO result = setObject.renderChildParentCategoryBoqs(boqListDTO, categoriesDTO);
				return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), "No data exists...", Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBoqItems(SearchDTO search) {

		try {
			List<BoqItem> items = itemDao.fetchBoqItems(search);
			List<BoqItemDTO> obj = new ArrayList<>();
			if (items != null)
				items.forEach(item -> obj.add(setObject.boqItemEntityToDto(item)));
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

	@Override
	public CustomResponse addBoqItem(BoqItemDTO boqDTO) {

		try {
			BoqItem boq = setObject.boqItemDtoToEntity(boqDTO);
			boq.setCreatedOn(new Date());
			boq.setModifiedOn(new Date());
			boq.setModifiedBy(boq.getCreatedBy());
			Long id = itemDao.saveBoqItem(boq);
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
	public CustomResponse updateBoqItem(BoqItemDTO boqDTO) {

		try {
			SearchDTO search = new SearchDTO();
			search.setCompanyId(boqDTO.getCompanyId());
			List<BoqItem> boqs = itemDao.fetchBoqItems(search);
			if (boqs != null) {
				for (BoqItem obj : boqs) {
					if (obj.getStandardBookCode() != null
							&& boqDTO.getStandardBookCode() != null
									& obj.getStandardBookCode().equalsIgnoreCase(boqDTO.getStandardBookCode())
							&& !obj.getId().equals(boqDTO.getId())) {

						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"SDB Code attached with other BOQ!");
					}
				}
			}
			BoqItem boq = setObject.updatedBoqItem(itemDao.fetchBoqItemById(boqDTO.getId()), boqDTO);
			Boolean updateItem = itemDao.updateBoqItem(boq);
			return new CustomResponse(Responses.SUCCESS.getCode(), updateItem, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBoqItemById(SearchDTO search) {

		try {
			BoqItem boq = itemDao.fetchBoqItemById(search.getId());
			BoqItemDTO result = setObject.boqItemEntityToDto(boq);
			itemDao.fetchBoqItems(search);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}

	}

	@Override
	public CustomResponse getAllByWorkType(Long siteId, Integer workType) {

		try {

			List<WorkTypeBoqResponse> resultList = new ArrayList<>();
			WorkTypeBoqResponse workTypeObj = null;
			BoqItem boq = null;

			if (RfiWorkType.Highway.ordinal() == workType) {

				List<HighwayBoqMapping> hbqs = hbmDao.fetchHighwayBoqMappingGroupByBoqs(siteId);
				if (hbqs != null) {
					for (HighwayBoqMapping hbq : hbqs) {
						boq = hbq.getBoq();
						workTypeObj = setObject.boqItemEntityToWorkTypeBoqResponse(boq);
						workTypeObj.setBoqDescription(hbq.getDescription());
						resultList.add(workTypeObj);
					}
				}

			} else if (RfiWorkType.Structure.ordinal() == workType) {

				List<StructureBoqQuantityMapping> sbqs = sbqDao.fetchStructureBoqMappingGroupByBoqs(siteId);
				if (sbqs != null) {
					for (StructureBoqQuantityMapping sbq : sbqs) {
						boq = sbq.getBoq();
						workTypeObj = setObject.boqItemEntityToWorkTypeBoqResponse(boq);
						workTypeObj.setBoqDescription(sbq.getDescription());
						resultList.add(workTypeObj);
					}
				}
			} else {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid workType.");
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultList, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}
}
