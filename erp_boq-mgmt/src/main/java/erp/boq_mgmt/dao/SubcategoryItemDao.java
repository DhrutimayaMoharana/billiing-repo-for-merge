package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.SubcategoryItem;

public interface SubcategoryItemDao {

	Long saveSubcategoryItem(SubcategoryItem subcat);

	SubcategoryItem fetchSubcategoryItemByIdOrName(Long id, String name, Integer companyId);
	
	Long getIdByNameOrSave(SearchDTO search);
	
	Long getIdByNameUpdateOrSave(SearchDTO search);

	Integer fetchCount(SearchDTO search);

	Boolean updateSubcategoryItem(SubcategoryItem subcat);

	List<SubcategoryItem> fetchSubcategoryItems(SearchDTO search);
	
	String fetchCodeByStandardBookCode(String standardBookCode, Integer companyId);

	SubcategoryItem forceSaveSubcategoryItem(SubcategoryItem item);

	void forceUpdateSubcategoryItem(SubcategoryItem boq);

}
