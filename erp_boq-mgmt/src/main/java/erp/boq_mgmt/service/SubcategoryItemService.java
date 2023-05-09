package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.SubcategoryItemDTO;

public interface SubcategoryItemService {

	CustomResponse addSubcategoryItem(SubcategoryItemDTO subcategoryDTO);

	CustomResponse updateSubcategoryItem(SubcategoryItemDTO subcategoryDTO);

	CustomResponse getSubcategoryItemByIdOrName(SearchDTO search);

	CustomResponse getSubcategoryItems(SearchDTO search);

}
