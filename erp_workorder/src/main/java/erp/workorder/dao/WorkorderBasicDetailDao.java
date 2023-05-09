package erp.workorder.dao;

import java.util.List;

import erp.workorder.entity.CategoryItem;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderCategoryMapping;

public interface WorkorderBasicDetailDao {

	Long saveBasicWorkorder(Workorder workorder);
	
	List<WorkorderCategoryMapping> fetchWorkorderCategoriesByWorkorderId(Long woContractorId);

	Long saveWorkorderContractorCategoryMapping(WorkorderCategoryMapping wcc);

	List<CategoryItem> fetchActiveCategoryItemsByCompanyId(Integer companyId);
	
	Workorder fetchWorkorderById(Long id);
	
	Boolean updateBasicWorkorder(Workorder workorder);

}
