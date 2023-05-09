package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.DocumentSubType;

public interface DocumentSubTypeDao {

	List<DocumentSubType> fetchDocSubTypes(SearchDTO search);

	Integer fetchIdByNameOrSave(String name, Integer companyId, Long userId);

}
