package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.WorkorderBoqWorkQtyMapping;

public interface WorkorderBoqWorkDao {

	List<WorkorderBoqWorkQtyMapping> fetchWoBoqWorkQtysByBoqId(SearchDTO search);

	List<WorkorderBoqWorkQtyMapping> fetchWoBoqWorkQtys(SearchDTO search);

	List<WorkorderBoqWorkQtyMapping> fetchStructureWoBoqWorkQtys(SearchDTO search);

	List<WorkorderBoqWorkQtyMapping> fetchStructureWoBoqWorkQtysByBoqId(SearchDTO search);

}
