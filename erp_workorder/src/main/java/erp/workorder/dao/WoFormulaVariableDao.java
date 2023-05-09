package erp.workorder.dao;

import erp.workorder.dto.SearchDTO;

public interface WoFormulaVariableDao {

	Long getIdByNameOrSave(SearchDTO search);

}
