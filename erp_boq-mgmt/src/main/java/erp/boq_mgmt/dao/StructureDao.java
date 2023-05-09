package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Structure;
import erp.boq_mgmt.entity.StructureV2;

public interface StructureDao {

	Integer fetchCount(SearchDTO search);

	List<Structure> fetchStructures(SearchDTO search);

	Long saveStructure(Structure structure);

	Structure fetchStructureById(Long id);

	Boolean updateStructure(Structure structure);

	List<Structure> fetchStructuresByTypeId(Long id);

	List<StructureV2> fetchTypeWiseStructureIds(SearchDTO search);

	List<Structure> fetchStructuresByGroupId(Integer groupId);

	Integer fetchMaxExistedGroupStructurePatternIndex(Long siteId, String groupStructureMultiplesPattern,
			String separator);

}
