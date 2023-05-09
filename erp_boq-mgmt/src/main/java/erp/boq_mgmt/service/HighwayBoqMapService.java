package erp.boq_mgmt.service;

import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.HighwayBoqMappingDTO;
import erp.boq_mgmt.dto.SearchDTO;

public interface HighwayBoqMapService {

	CustomResponse mapCategoryBoq(HighwayBoqMappingDTO bcmDTO);

	CustomResponse getCategoryBoqById(SearchDTO search);

	CustomResponse getCategoriesBoqs(SearchDTO search);

	CustomResponse getHighwayBoqs(SearchDTO search);

	CustomResponse updateCategoryBoq(HighwayBoqMappingDTO bcmDTO);

	CustomResponse importHighwayBoqQuantityMappingExcel(MultipartFile excelFile, SearchDTO search);
	
	CustomResponse deactivateHighwayBoqMapping(SearchDTO search);

}
