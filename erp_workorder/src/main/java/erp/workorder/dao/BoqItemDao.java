package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.BoqItem;

public interface BoqItemDao {

	List<BoqItem> fetchBoqItems(SearchDTO search);

	BoqItem fetchBoqItemById(Long id);

	List<BoqItem> fetchBoqItemsByCompanyId(Integer companyId);

}
