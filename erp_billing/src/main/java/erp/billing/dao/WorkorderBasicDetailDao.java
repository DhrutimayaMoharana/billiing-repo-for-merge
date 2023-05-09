package erp.billing.dao;

import java.util.List;

import erp.billing.entity.CategoryItem;
import erp.billing.entity.WorkorderCategoryMapping;

public interface WorkorderBasicDetailDao {
	
	List<WorkorderCategoryMapping> fetchWorkorderCategoriesByWorkorderId(Long woContractorId);

	List<CategoryItem> fetchActiveCategoryItemsByCompanyId(Integer companyId);

}
