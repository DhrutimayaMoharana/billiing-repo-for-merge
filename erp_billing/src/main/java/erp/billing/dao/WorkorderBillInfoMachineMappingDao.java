package erp.billing.dao;

import java.util.List;

import erp.billing.entity.WorkorderBillInfoMachineMapping;

public interface WorkorderBillInfoMachineMappingDao {

	List<WorkorderBillInfoMachineMapping> fetchWorkorderBillInfoMachineMapping(Integer workorderBillInfoId);

}
