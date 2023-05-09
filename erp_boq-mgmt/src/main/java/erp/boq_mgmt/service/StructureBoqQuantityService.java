package erp.boq_mgmt.service;

import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.StructureBoqQuantityMappingDTO;

public interface StructureBoqQuantityService {

	CustomResponse addStructureBoqQuantityMapping(StructureBoqQuantityMappingDTO sbqMapDTO);

	CustomResponse getSbqById(SearchDTO search);

	CustomResponse updateStructureBoqQuantityMapping(StructureBoqQuantityMappingDTO sbqMapDTO);

	CustomResponse getStructureBoqQuantities(SearchDTO search);

	CustomResponse getStructureWiseBoq(SearchDTO search);

	CustomResponse importStructureBoqQuantityMappingExcel(MultipartFile excelFile, SearchDTO search);

	CustomResponse deactivateStructureBoqQuantity(SearchDTO search);
}
