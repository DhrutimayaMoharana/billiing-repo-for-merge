package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.StructureDocumentStatus;

public interface StructureDocStatusDao {

	List<StructureDocumentStatus> fetchStructureDocStatus(SearchDTO search);

	Integer fetchIdByNameOrSave(String name, Integer companyId, Long userId);

}
