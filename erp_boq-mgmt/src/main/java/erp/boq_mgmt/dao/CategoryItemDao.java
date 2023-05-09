package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.CategoryContractorMapping;
import erp.boq_mgmt.entity.CategoryItem;

public interface CategoryItemDao {

	Long saveCategoryItem(CategoryItem category);

	CategoryItem forceSaveCategoryItem(CategoryItem category);

	List<CategoryItem> fetchCategoryItems(SearchDTO search);

	Integer fetchCount(SearchDTO search);

	CategoryItem fetchCategoryItemById(Long id);
	
	Long getIdByNameOrSave(SearchDTO search);
	
	Long getIdByNameUpdateOrSave(SearchDTO search);

	Boolean updateCategoryItem(CategoryItem categoryObj);

	void forceUpdateCategoryItem(CategoryItem categoryObj);
	
	String fetchCodeByStandardBookCode(String standardBookCode, Integer companyId);

	List<CategoryContractorMapping> fetchCategoriesByContractorId(Long contractorId);

}
