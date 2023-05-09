package erp.billing.dao;

import erp.billing.entity.Workorder;

public interface WorkorderDao {

	Workorder fetchWorkorderById(Long workorderId);
}
