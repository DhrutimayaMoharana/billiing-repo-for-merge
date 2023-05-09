package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Set;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.HighwayBoqTransacs;

public interface HighwayBoqMapDao {

	Long mapCategoryBoq(HighwayBoqMapping bcm);

	void forceUpdateCategoryBoq(HighwayBoqMapping bcm);

	void forceUpdateAfterDetachCategoryBoq(HighwayBoqMapping bcm);

	HighwayBoqMapping forceSaveCategoryBoq(HighwayBoqMapping bcm);

	void saveBoqCategoryTransac(HighwayBoqTransacs bct);

	HighwayBoqMapping fetchCategoryBoqById(Long catBoqId);

	List<HighwayBoqMapping> fetchCategoriesBoqs(SearchDTO search);

	Boolean updateCategoryBoqMapping(HighwayBoqMapping bcmObj);

	List<HighwayBoqMapping> fetchHighwayBoqByIdList(Set<Long> ids);

	List<HighwayBoqMapping> fetchHighwayBoqMappingGroupByBoqs(Long siteId);

	List<HighwayBoqMapping> fetchByBoqAndSite(Long boqId, Long siteId);

}
