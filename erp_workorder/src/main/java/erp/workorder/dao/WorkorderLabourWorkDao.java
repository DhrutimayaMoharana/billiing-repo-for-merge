package erp.workorder.dao;

import java.util.List;

import erp.workorder.entity.WorkorderLabourType;
import erp.workorder.entity.WorkorderLabourWork;
import erp.workorder.entity.WorkorderLabourWorkItemMapping;
import erp.workorder.entity.WorkorderLabourWorkItemMappingVersion;
import erp.workorder.entity.WorkorderLabourWorkVersion;

public interface WorkorderLabourWorkDao {

	WorkorderLabourWorkItemMapping fetchWorkorderLabourWorkItemMappingById(Long labourWorkItemId);

	List<WorkorderLabourWorkItemMapping> fetchWorkorderLabourWorkItemsByLabourWorkId(Long workorderLabourWorkId);

	void forceUpdateWorkorderLabourWorkItem(WorkorderLabourWorkItemMapping labourWorkItem);

	Long saveWorkorderLabourWork(WorkorderLabourWork labourWork);

	Long saveWorkorderLabourWorkItemMap(WorkorderLabourWorkItemMapping workItem);

	Long saveWorkorderLabourWorkItemMapVersion(WorkorderLabourWorkItemMappingVersion workItemVerion);

	WorkorderLabourWork fetchWorkorderLabourWorkById(Long labourWorkId);

	Boolean updateWorkorderLabourWork(WorkorderLabourWork labourWork);

	Boolean updateWorkorderLabourWorkItem(WorkorderLabourWorkItemMapping labourWorkItem);

	List<WorkorderLabourType> fetchWoLabourTypes(Integer companyId);

	WorkorderLabourWork fetchWorkorderLabourWorkByWorkorderId(Long workorderId);

	Long saveWorkorderLabourWorkVersion(WorkorderLabourWorkVersion labourWorkVersion);

}
