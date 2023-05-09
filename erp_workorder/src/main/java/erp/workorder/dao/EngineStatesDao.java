package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.EngineState;

public interface EngineStatesDao {

	List<EngineState> fetchEngineStates(SearchDTO search);

}
