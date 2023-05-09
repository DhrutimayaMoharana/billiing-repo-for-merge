package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WorkorderType;

public interface WorkorderTypeDao {

	List<WorkorderType> fetchWorkorderTypes(SearchDTO search);

	WorkorderType fetchWorkorderTypeById(Integer typeId);

	Long saveWorkorderType(WorkorderType type);

	Boolean updateWorkorderType(WorkorderType type);

}
