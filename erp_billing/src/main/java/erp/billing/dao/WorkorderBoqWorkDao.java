package erp.billing.dao;

import java.util.List;

import erp.billing.entity.WorkorderBoqWorkQtyMapping;

public interface WorkorderBoqWorkDao {

	List<WorkorderBoqWorkQtyMapping> fetchWoBoqWorkQtys(Long workorderId);

}
