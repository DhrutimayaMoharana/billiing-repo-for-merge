package erp.workorder.dao;

import java.util.List;

import erp.workorder.entity.WorkorderBillInfoMachineMapping;

public interface WorkorderBillInfoMachineMappingDao {

	List<WorkorderBillInfoMachineMapping> fetchWorkorderBillInfoMachineMapping(Integer workorderBillInfoId);

	Long saveWorkorderBillInfoMachineMapping(WorkorderBillInfoMachineMapping bcmToSave);

	void forceUpdateWorkorderBillInfoMachineMapping(WorkorderBillInfoMachineMapping bcm);

}
