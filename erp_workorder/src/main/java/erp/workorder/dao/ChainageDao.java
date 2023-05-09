package erp.workorder.dao;
import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Chainage;

public interface ChainageDao {

	Long saveChainage(Chainage chainage);

	Integer fetchCount(SearchDTO search);

	List<Chainage> fetchChainages(SearchDTO search);
	
	List<Chainage> fetchChainagesBySite(Long siteId);

	void updateChainage(Chainage chainage);
	
	Chainage fetchById(Long id);

}
