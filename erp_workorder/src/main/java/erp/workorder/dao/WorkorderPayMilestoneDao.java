package erp.workorder.dao;

import java.util.List;

import erp.workorder.entity.WorkorderPayMilestone;

public interface WorkorderPayMilestoneDao {

	List<WorkorderPayMilestone> fetchWorkorderWorkorderPayMilestones(Long workorderId);

	WorkorderPayMilestone fetchWorkorderWorkorderPayMilestoneById(Long workorderPayMilestoneId);

	void forceUpdateWorkorderPayMilestone(WorkorderPayMilestone payMilestone);

	Boolean updateWorkorderPayMilestone(WorkorderPayMilestone payMilestone);

	Long saveWorkorderWorkorderPayMilestone(WorkorderPayMilestone payMilestone);

}
