package erp.boq_mgmt.service;

import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dto.ChainageGenericBoqQuantityMappingDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;

public interface ChainageBorewellBoqQuantityService {

	CustomResponse addChainageBoqQuantityMapping(ChainageGenericBoqQuantityMappingDTO cbqMapDTO);

	CustomResponse updateChainageBoqQuantityMapping(ChainageGenericBoqQuantityMappingDTO cbqMapDTO);

	CustomResponse importChainageBoqQuantityMappingExcel(MultipartFile excelFile, SearchDTO search);

	CustomResponse getChainageBoqQuantities(SearchDTO search);

	CustomResponse getBoqWiseCbq(SearchDTO search);

	CustomResponse getCbqById(SearchDTO search);

	CustomResponse getChainageQuantitiesPerBoq(SearchDTO search);

	CustomResponse removeChainageBoqQuantityMapping(SearchDTO search);

}
