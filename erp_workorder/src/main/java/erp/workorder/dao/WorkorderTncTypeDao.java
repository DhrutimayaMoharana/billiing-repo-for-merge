package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WoTncType;

public interface WorkorderTncTypeDao {

	List<WoTncType> fetchActiveWoTncTypes(SearchDTO search);

	WoTncType fetchWoTncTypeById(Long id);

	Boolean updateWoTncType(WoTncType type);

	Long saveWoTncType(WoTncType type);
	
}
