package erp.billing.dao;

import java.util.List;

import erp.billing.entity.WorkorderPayMilestone;

public interface WorkorderPayMilestoneDao {

	List<WorkorderPayMilestone> fetchWorkorderWorkorderPayMilestones(Long workorderId);

}
