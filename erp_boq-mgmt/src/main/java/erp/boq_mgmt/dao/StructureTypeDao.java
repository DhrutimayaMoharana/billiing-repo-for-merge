package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.StructureType;

public interface StructureTypeDao {

	Integer fetchCount(SearchDTO search);

	Long saveStructureType(StructureType type);

	StructureType fetchStructureTypeByIdOrName(Long id, String name);

	Boolean updateStructureType(StructureType type);

	List<StructureType> fetchStructureTypes(SearchDTO search);

}
