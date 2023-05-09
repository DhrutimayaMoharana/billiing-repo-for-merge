package erp.billing.dao;

import java.util.List;
import java.util.Set;

import erp.billing.entity.WorkorderHiringMachineRateDetails;
import erp.billing.entity.WorkorderHiringMachineWork;
import erp.billing.entity.WorkorderHiringMachineWorkItemMapping;

public interface WorkorderHireMachineryDao {

	List<WorkorderHiringMachineWorkItemMapping> fetchWorkorderHiringMachineWorkItemMapping(Long workorderId);

	List<WorkorderHiringMachineWorkItemMapping> fetchWorkorderHiringMachineWorkItemMappingByWorkId(Long hiringWorkId);

	WorkorderHiringMachineWork fetchWorkorderHiringMachineWork(Long workorderId);

	List<WorkorderHiringMachineRateDetails> fetchHiringMachineItemRateDetailsByWorkItemIds(
			Set<Long> distinctHmWorkItemIds);

}
