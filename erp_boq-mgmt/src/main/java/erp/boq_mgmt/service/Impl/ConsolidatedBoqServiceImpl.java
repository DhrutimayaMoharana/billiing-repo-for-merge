package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.CategoryItemDao;
import erp.boq_mgmt.dao.HighwayBoqMapDao;
import erp.boq_mgmt.dao.StructureBoqQuantityDao;
import erp.boq_mgmt.dto.CategoryItemDTO;
import erp.boq_mgmt.dto.ConsolidatedBoqsDTO;
import erp.boq_mgmt.dto.ConsolidatedBoqsRenderDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.CategoryItem;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.WorkType;
import erp.boq_mgmt.service.ConsolidatedBoqService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class ConsolidatedBoqServiceImpl implements ConsolidatedBoqService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private HighwayBoqMapDao bcmDao;

	@Autowired
	private CategoryItemDao categoryDao;

	@Autowired
	private StructureBoqQuantityDao sbqDao;

	@Override
	public CustomResponse getConsolidatedBoqs(SearchDTO search) {

		try {
			List<CategoryItem> categories = categoryDao.fetchCategoryItems(search);
			List<CategoryItemDTO> categoriesDTO = new ArrayList<>();
			List<ConsolidatedBoqsDTO> consolidatedBoqs = new ArrayList<>();
			for (CategoryItem category : categories)
				categoriesDTO.add(setObject.categoryItemEntityToDto(category));
			if (!(search.getWorkType() != null && search.getWorkType().equals(WorkType.STRUCTURE.ordinal()))) {
				List<HighwayBoqMapping> bcmList = bcmDao.fetchCategoriesBoqs(search);
				if (bcmList != null)
					for (HighwayBoqMapping bcm : bcmList)
						consolidatedBoqs.add(setObject.highwayBoqEntityToConsolidatedBoqDTO(bcm));
			}

			if (!(search.getWorkType() != null && search.getWorkType().equals(WorkType.HIGHWAY.ordinal()))) {
				List<StructureBoqQuantityMapping> sbqList = sbqDao.fetchAllStructureBoqs(search);
				if (sbqList != null)
					for (StructureBoqQuantityMapping sbq : sbqList)
						consolidatedBoqs.add(setObject.structureBoqEntityToConsolidatedBoqDTO(sbq));

			}

			if (consolidatedBoqs != null && consolidatedBoqs.size() > 0) {

				List<ConsolidatedBoqsDTO> filteredConsolidatedBoqs = new ArrayList<>();
				for (ConsolidatedBoqsDTO obj : consolidatedBoqs) {
					boolean hasFound = false;
					for (ConsolidatedBoqsDTO newObj : filteredConsolidatedBoqs) {
						if (newObj.getBoq().getId().equals(obj.getBoq().getId())) {
							hasFound = true;
							break;
						}
					}
					if (!hasFound) {
						Long boqId = obj.getBoq().getId();
						Double quantitySum = 0.0;
						Double maxRateSum = 0.0;
						Double rateSum = 0.0;
						int count = 0;
						for (ConsolidatedBoqsDTO boq : consolidatedBoqs) {
							if (boqId.equals(boq.getBoq().getId())) {
								if (boq.getQuantity() != null)
									quantitySum += boq.getQuantity();
								if (boq.getMaxRate() != null)
									maxRateSum += boq.getMaxRate();
								if (boq.getRate() != null)
									rateSum += boq.getRate();
								count++;
							}
						}
						if (count > 0) {
							Double weightedMaxRate = maxRateSum / count;
							Double weightedRate = rateSum / count;
							ConsolidatedBoqsDTO cBoqSum = new ConsolidatedBoqsDTO(obj.getBoq(), obj.getCategory(),
									obj.getSubcategory(), quantitySum, weightedRate, weightedMaxRate);
							filteredConsolidatedBoqs.add(cBoqSum);
						}
					}
				}
				ConsolidatedBoqsRenderDTO result = setObject.renderChildParentConsolidatedBoqs(filteredConsolidatedBoqs,
						categoriesDTO);
				return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), null, "No data exists...");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
