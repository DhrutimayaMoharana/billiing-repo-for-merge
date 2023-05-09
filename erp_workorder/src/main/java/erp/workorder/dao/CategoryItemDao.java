package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.CategoryItem;

public interface CategoryItemDao {

	List<CategoryItem> fetchCategoryItems(SearchDTO search);

	CategoryItem fetchCategoryItemById(Long categoryId);

}
