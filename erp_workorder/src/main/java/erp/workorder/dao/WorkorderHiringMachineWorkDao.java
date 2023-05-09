package erp.workorder.dao;

import java.util.List;
import java.util.Set;

import erp.workorder.entity.UnitMaster;
import erp.workorder.entity.WorkorderHiringMachineRateDetailVersion;
import erp.workorder.entity.WorkorderHiringMachineRateDetails;
import erp.workorder.entity.WorkorderHiringMachineWork;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMapping;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMappingVersion;
import erp.workorder.entity.WorkorderHiringMachineWorkVersion;

public interface WorkorderHiringMachineWorkDao {

	Long saveWorkorderHiringMachineWork(WorkorderHiringMachineWork hmWork);

	Long saveWorkorderHiringMachineWorkItemMap(WorkorderHiringMachineWorkItemMapping workItem);

	WorkorderHiringMachineWork fetchWorkorderHiringMachineWorkById(Long hmWorkId);

	Boolean updateWorkorderHiringMachineWork(WorkorderHiringMachineWork hmWork);

	List<WorkorderHiringMachineWorkItemMapping> fetchWorkorderHiringMachineWorkItemsByHmWorkId(Long id);

	WorkorderHiringMachineWorkItemMapping fetchWorkorderHiringMachineWorkItemMappingById(Long hmWorkItemId);

	void forceUpdateWorkorderHiringMachineWorkItem(WorkorderHiringMachineWorkItemMapping hmWorkItem);

	Boolean updateWorkorderHiringMachineWorkItem(WorkorderHiringMachineWorkItemMapping hmWorkItem);

	List<WorkorderHiringMachineWorkItemMapping> fetchWorkorderHiringMachineWorkItemsBySiteId(Long siteId);

	List<UnitMaster> fetchHiringMachineUnits();

	WorkorderHiringMachineRateDetails saveOrUpdateHiringMachineItemRateDetail(
			WorkorderHiringMachineRateDetails itemRateDetail);

	Boolean forceUpdateHiringMachineItemRateDetail(WorkorderHiringMachineRateDetails itemRateDetail);

	List<WorkorderHiringMachineRateDetails> fetchHiringMachineItemRateDetailsByWorkItemIds(
			Set<Long> distinctHmWorkItemIds);

	List<WorkorderHiringMachineRateDetails> fetchHiringMachineItemRateDetailsByWorkItemIdsAndIsActive(
			Set<Long> workItemIds, Boolean isActive);

	WorkorderHiringMachineWork fetchWorkorderHiringMachineWorkByWorkorderId(Long workorderId);

	Long saveWorkorderHiringMachineWorkVersion(WorkorderHiringMachineWorkVersion hiringMachineWorkVersion);

	Long saveWorkorderHiringMachineWorkItemMapVersion(WorkorderHiringMachineWorkItemMappingVersion itemVersionObj);

	Long saveWorkorderHiringMachineRateDetailVersion(WorkorderHiringMachineRateDetailVersion rateDetailVersionObj);

}
