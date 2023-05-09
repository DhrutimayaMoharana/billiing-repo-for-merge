package erp.boq_mgmt.service;

import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dto.BorewellBoqMappingDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.GenericBoqMappingDTO;
import erp.boq_mgmt.dto.SearchDTO;

public interface BorewellBoqMapService {

	CustomResponse importGenericBoqQuantityMappingExcel(MultipartFile excelFile, SearchDTO search);

	CustomResponse deactivateGenericBoqMapping(SearchDTO search);

	CustomResponse getHighwayTypes(SearchDTO search);

//	CustomResponse updateCategoryBoq(BorewellBoqMappingDTO bcmDTO);

	CustomResponse updateCategoryBoq(GenericBoqMappingDTO bcmDTO);

	CustomResponse getGenericBoqs(SearchDTO search);

	CustomResponse getCategoriesBoqs(SearchDTO search);

	CustomResponse getCategoryBoqById(SearchDTO search);

	CustomResponse mapCategoryBoq(BorewellBoqMappingDTO bcmDTO);

	CustomResponse mapCategoryBoq(GenericBoqMappingDTO bcmDTO);

}
