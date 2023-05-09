package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Set;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.entity.StructureBoqQuantityTransacs;

public interface StructureBoqQuantityDao {

	Long saveStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj);

	StructureBoqQuantityMapping forceSaveStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj);

	void forceUpdateStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj);

	void forceUpdateAfterDetachStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj);

	Long saveStructureBoqQuantityTransac(StructureBoqQuantityTransacs sbqTransac);

	StructureBoqQuantityMapping fetchSbqById(Long id);

	Boolean updateStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj);

	List<StructureBoqQuantityMapping> fetchStructureWiseBoq(SearchDTO search);

	List<StructureBoqQuantityMapping> fetchAllStructureBoqs(SearchDTO search);

	List<StructureBoqQuantityMapping> fetchStructureBoqQuantityMappingBySearch(SearchDTO search);

	List<StructureBoqQuantityMapping> fetchSbqByIdList(Set<Long> idsSaved);

	List<StructureBoqQuantityMapping> fetchStructureBoqMappingGroupByBoqs(Long siteId);

	List<StructureBoqQuantityMapping> fetchStructureBoqMappingByBoqIdStructureIdAndTypeId(Long boqId,
			Long structureId, Long structureTypeId, Long siteId);

	List<StructureBoqQuantityMapping> fetchStructureBoqMappingByBoqIdStructureIdAndTypeIdGroupByStructure(Long boqId,
			Long structureId, Long structureTypeId, Long siteId);

}
