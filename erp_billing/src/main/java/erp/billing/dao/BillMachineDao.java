package erp.billing.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import erp.billing.entity.BillMachineMapping;

public interface BillMachineDao {

	Long saveBillMachineMapping(BillMachineMapping bcmToSave);

	List<BillMachineMapping> fetchBillMachineMapping(Long billId);

	List<BillMachineMapping> fetchBillMachineMapping(Set<Long> billIds);

	void forceUpdateBillMachineMapping(BillMachineMapping bcm);

	List<BillMachineMapping> fetchBillMachineMappingByBillIds(Set<Long> billIds);

	Boolean fetchBillMachineList(Long machineId, Date fromDate, Date toDate, Long billId);

}
