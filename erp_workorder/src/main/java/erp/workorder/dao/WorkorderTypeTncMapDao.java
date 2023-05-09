package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WoTypeTncMapping;

public interface WorkorderTypeTncMapDao {

	List<WoTypeTncMapping> fetchWoTypesTncs(Integer companyId);

	List<WoTypeTncMapping> fetchWoTypesTncsByTncId(Long woTncId);

	List<WoTypeTncMapping> fetchWoTypeTncs(SearchDTO search);

	Long mapWorkorderTypeTnc(WoTypeTncMapping typeTncMap);

	WoTypeTncMapping fetchWoTypeTncById(Long id);

	void forceUpdateWoTypeTncMapping(WoTypeTncMapping typeTnc);

}
