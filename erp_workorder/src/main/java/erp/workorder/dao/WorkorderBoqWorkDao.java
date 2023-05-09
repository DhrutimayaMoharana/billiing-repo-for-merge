package erp.workorder.dao;

import java.util.List;
import java.util.Set;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.ChainageBoqQuantityMapping;
import erp.workorder.entity.HighwayBoqMapping;
import erp.workorder.entity.StructureBoqQuantityMapping;
import erp.workorder.entity.StructureType;
import erp.workorder.entity.WorkorderBoqWork;
import erp.workorder.entity.WorkorderBoqWorkLocation;
import erp.workorder.entity.WorkorderBoqWorkLocationVersion;
import erp.workorder.entity.WorkorderBoqWorkQtyMapping;
import erp.workorder.entity.WorkorderBoqWorkQtyMappingVersion;
import erp.workorder.entity.WorkorderBoqWorkVersion;

public interface WorkorderBoqWorkDao {

	List<ChainageBoqQuantityMapping> fetchActiveCbqsByChainageIdsAndSiteId(Set<Long> chainageIds, Long siteId);

	List<ChainageBoqQuantityMapping> fetchActiveCbqsBetweenChainageNumericValuesAndSiteIdAndWoCategories(
			Integer fromChainageValue, Integer toChainageValue, Set<Long> woCategoryIds, Long siteId);

	List<StructureBoqQuantityMapping> fetchActiveSbqsByStructureTypeIdAndStructureIdAndCategoryIds(Long structureTypeId,
			Long structureId, Set<Long> workorderCategoryIds, Long siteId);

	List<StructureBoqQuantityMapping> fetchActiveSbqsByStructureTypeIdAndStructureIdAndBoqId(Long structureTypeId,
			Long structureId, Long boqId, String boqDes, Long siteId);

	Long saveWorkorderBoqWork(WorkorderBoqWork boqWork);

	List<WorkorderBoqWorkQtyMapping> fetchStructureWoBoqWorkQtys(SearchDTO search);

	List<WorkorderBoqWorkQtyMapping> fetchChainageWoBoqWorkQtys(SearchDTO search, Set<Long> chainageIds);

	Boolean updateWorkorderBoqWork(WorkorderBoqWork savedBoqWork);

	void forceDeactivateBoqWorkQtys(List<WorkorderBoqWorkQtyMapping> boqQtysToDeactivate);

	List<HighwayBoqMapping> fetchHighwayBoqQtys(Long siteId, Set<Long> workorderCategoryIds);

	List<HighwayBoqMapping> fetchHighwayBoqQtysByBoqId(Long siteId, Long boqId);

	void forceDeactivateBoqWorkLocations(List<WorkorderBoqWorkLocation> boqLocsToDeactivate);

	List<WorkorderBoqWorkQtyMapping> fetchSiteChainagesWoBoqWorkQtys(SearchDTO search);

	StructureType fetchStructureTypeById(Long structureTypeId);

	WorkorderBoqWork fetchWorkorderBoqWorkByWorkorderId(Long workorderId);

	Long saveWorkorderBoqWorkVersion(WorkorderBoqWorkVersion boqWorkVersion);

	Long saveWorkorderBoqWorkQtyMapVersion(WorkorderBoqWorkQtyMappingVersion boqWorkQtyMappingVersionObj);

	Long saveWorkorderBoqWorkLocationVersion(WorkorderBoqWorkLocationVersion boqWorkLocationVersionObj);

	Long saveWorkorderBoqWorkQtyMap(WorkorderBoqWorkQtyMapping boqWorkQtyMappingObj);

	Long saveWorkorderBoqWorkLocation(WorkorderBoqWorkLocation boqWorkLocationObj);

}
