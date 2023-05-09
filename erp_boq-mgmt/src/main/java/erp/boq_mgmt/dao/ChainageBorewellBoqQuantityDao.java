package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.ChainageBorewellBoqQuantityMapping;
import erp.boq_mgmt.entity.ChainageBorewellBoqQuantityTransacs;
import erp.boq_mgmt.entity.GenericChainageBoqQuantityMapping;

public interface ChainageBorewellBoqQuantityDao {

	Long forceSaveChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj);

	Long saveChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj);

	List<ChainageBorewellBoqQuantityMapping> fetchChainageBorewellBoqQuantityMappingBySearch(SearchDTO search);

	Long saveChainageBorewellBoqQuantityTransac(ChainageBorewellBoqQuantityTransacs cbqTransac);

	Boolean updateChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj);

	Boolean forceUpdateChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj);

	Boolean forceUpdateAfterDetachChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj);

	List<ChainageBorewellBoqQuantityMapping> fetchBoqWiseCbq(SearchDTO search);

	ChainageBorewellBoqQuantityMapping fetchCbqById(Long id);

	List<ChainageBorewellBoqQuantityMapping> fetchChainageBorewellBoqQuantitiesByHbmId(Long hbmId);

	List<ChainageBorewellBoqQuantityMapping> fetchChainageBorewellBoqQuantitiesByHbmIdWithOneCbqLess(Long hbmId,
			Long cbqId);

	List<ChainageBorewellBoqQuantityMapping> fetchByRangeBoqAndSite(Long boqId, Integer fromChainageNameNumericValue,
			Integer toChainageNameNumericValue, Long siteId);

	Long forceSaveChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj);

	Long saveChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj);

	List<GenericChainageBoqQuantityMapping> fetchChainageGenericBoqQuantityMappingBySearch(SearchDTO search);

//	Long saveChainageBorewellBoqQuantityTransac(ChainageBorewellBoqQuantityTransacs cbqTransac);

	Boolean updateChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj);

	Boolean forceUpdateChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj);

	Boolean forceUpdateAfterDetachChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj);

	List<GenericChainageBoqQuantityMapping> fetchBoqWiseCbqV1(SearchDTO search);

	GenericChainageBoqQuantityMapping fetchCbqByIdV1(Long id, Integer woTypeId);

	List<GenericChainageBoqQuantityMapping> fetchChainageGenericBoqQuantitiesByHbmId(Long hbmId);

	List<GenericChainageBoqQuantityMapping> fetchChainageGenericBoqQuantitiesByHbmIdWithOneCbqLess(Long hbmId,
			Long cbqId);

	List<GenericChainageBoqQuantityMapping> fetchByRangeBoqAndSiteV1(Long boqId, Integer fromChainageNameNumericValue,
			Integer toChainageNameNumericValue, Long siteId);

}
