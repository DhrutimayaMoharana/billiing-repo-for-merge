package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.ChainageBoqQuantityMapping;
import erp.boq_mgmt.entity.ChainageBoqQuantityTransacs;

public interface ChainageBoqQuantityDao {

	Long forceSaveChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj);

	Long saveChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj);

	List<ChainageBoqQuantityMapping> fetchChainageBoqQuantityMappingBySearch(SearchDTO search);

	Long saveChainageBoqQuantityTransac(ChainageBoqQuantityTransacs cbqTransac);

	Boolean updateChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj);

	Boolean forceUpdateChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj);

	Boolean forceUpdateAfterDetachChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj);

	List<ChainageBoqQuantityMapping> fetchBoqWiseCbq(SearchDTO search);

	ChainageBoqQuantityMapping fetchCbqById(Long id);

	List<ChainageBoqQuantityMapping> fetchChainageBoqQuantitiesByHbmId(Long hbmId);

	List<ChainageBoqQuantityMapping> fetchChainageBoqQuantitiesByHbmIdWithOneCbqLess(Long hbmId, Long cbqId);

	List<ChainageBoqQuantityMapping> fetchByRangeBoqAndSite(Long boqId, Integer fromChainageNameNumericValue,
			Integer toChainageNameNumericValue, Long siteId);

}
