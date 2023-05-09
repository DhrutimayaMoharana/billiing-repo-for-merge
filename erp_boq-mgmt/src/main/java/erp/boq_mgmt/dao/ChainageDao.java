package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Chainage;
import erp.boq_mgmt.entity.ChainageTraverse;

public interface ChainageDao {

	Long saveChainage(Chainage chainage);

	Long forceSaveChainage(Chainage chainage);

	Integer fetchCount(SearchDTO search);

	List<Chainage> fetchChainages(SearchDTO search);

	List<Chainage> fetchChainagesBySite(Long siteId);

	void updateChainage(Chainage chainage);

	Chainage fetchById(Long chainageId);

	ChainageTraverse fetchChainageTraverseById(Long chainageId);

}
