package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Structure;

public interface StructureDao {

	List<Structure> fetchStructures(SearchDTO search);

	Structure fetchStructureById(Long id);

}