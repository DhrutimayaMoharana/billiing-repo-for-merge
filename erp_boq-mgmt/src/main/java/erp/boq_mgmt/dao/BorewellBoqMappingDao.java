package erp.boq_mgmt.dao;

import java.util.List;

import java.util.Set;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.BorewellBoqMapping;
import erp.boq_mgmt.entity.BorewellBoqTransac;
import erp.boq_mgmt.entity.GenericBoqMappingHighway;
import erp.boq_mgmt.entity.WorkorderType;

public interface BorewellBoqMappingDao {

	BorewellBoqMapping forceSaveCategoryBoq(BorewellBoqMapping bcm);

	Long mapCategoryBoq(BorewellBoqMapping bcm);

	void forceUpdateCategoryBoq(BorewellBoqMapping bcm);

	void forceUpdateAfterDetachCategoryBoq(BorewellBoqMapping bcm);

	void saveBoqCategoryTransac(BorewellBoqTransac bct);

	BorewellBoqMapping fetchCategoryBoqById(Long catBoqId);

	List<BorewellBoqMapping> fetchCategoriesBoqs(SearchDTO search);

	Boolean updateCategoryBoqMapping(BorewellBoqMapping bcmObj);

	List<BorewellBoqMapping> fetchBorewellBoqByIdList(Set<Long> ids);

	List<BorewellBoqMapping> fetchBorewellBoqMappingGroupByBoqs(Long siteId);

	List<BorewellBoqMapping> fetchByBoqAndSite(Long boqId, Long siteId);

	GenericBoqMappingHighway forceSaveCategoryBoq(GenericBoqMappingHighway bcm);

	Long mapCategoryBoq(GenericBoqMappingHighway bcm);

	void forceUpdateCategoryBoq(GenericBoqMappingHighway bcm);

	void forceUpdateAfterDetachCategoryBoq(GenericBoqMappingHighway bcm);

//	void saveBoqCategoryTransac(BorewellBoqTransac bct);

	GenericBoqMappingHighway fetchGenricCategoryBoqById(Long catBoqId, Integer woTypeId);

	List<GenericBoqMappingHighway> fetchGenricCategoriesBoqs(SearchDTO search);

	Boolean updateCategoryBoqMapping(GenericBoqMappingHighway bcmObj);

	List<GenericBoqMappingHighway> fetchGenricBoqByIdList(Set<Long> ids);

	List<GenericBoqMappingHighway> fetchGenricBoqMappingGroupByBoqs(Long siteId);

	List<GenericBoqMappingHighway> fetchByBoqAndSiteV1(Long boqId, Long siteId, Integer workOrderTypeId);

	List<WorkorderType> fetchWorkorderTypes(List<Integer> ids);

}
