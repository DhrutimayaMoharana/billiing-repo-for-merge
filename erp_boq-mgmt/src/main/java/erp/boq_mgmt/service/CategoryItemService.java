package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CategoryItemDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;

public interface CategoryItemService {

	CustomResponse addCategoryItem(CategoryItemDTO categoryDTO);

	CustomResponse getCategoryItems(SearchDTO search);

	CustomResponse getCategoryItemById(SearchDTO search);

	CustomResponse updateCategoryItem(CategoryItemDTO categoryDTO);

	CustomResponse getTypewiseCategoryItems(SearchDTO search);

	CustomResponse deactivateCategoryItemById(SearchDTO search);

}
