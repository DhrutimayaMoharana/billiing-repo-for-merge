package erp.workorder.dao;

import java.util.List;
import java.util.Set;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Bill;
import erp.workorder.entity.BillBoqQuantityItem;
import erp.workorder.entity.BillMachineMapping;
import erp.workorder.entity.BillStateTransitionMapping;

public interface BillDao {

	List<Bill> fetchBills(SearchDTO search);

	List<BillStateTransitionMapping> fetchBillStateMappings(Long id);

	List<BillBoqQuantityItem> fetchWorkorderBillBoqQtyItems(Long siteId);

	List<BillStateTransitionMapping> fetchBillStateMappingsByBillIds(Set<Long> distinctBillIds);

	List<BillMachineMapping> fetchBillMachineMapping(Set<Long> distinctBillIds);

}
