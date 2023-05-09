package erp.billing.dao;

import java.util.List;
import java.util.Set;

import erp.billing.dto.SearchDTO;
import erp.billing.entity.Bill;
import erp.billing.entity.BillStateTransitionMapping;
import erp.billing.entity.BillTransac;

public interface BillDao {

	List<Bill> fetchBills(SearchDTO search);

	List<Bill> fetchUptoCurrentWorkorderBills(SearchDTO search);

	Bill fetchBillById(Long billId);

	Long saveBill(Bill bill);

	Integer fetchLastBillNumberByWorkorderIdAndBillTypeId(Long workorderId, Short typeId);

	Bill fetchLastBillByWorkorderIdAndBillTypeId(Long workorderId, Short typeId);

	boolean updateBill(Bill savedBill);

	void saveBillTransac(BillTransac billTransac);

	List<BillStateTransitionMapping> fetchBillStateMappings(Long id);

	void mapBillStateTransition(BillStateTransitionMapping billStateTransitionMap);

	Boolean forceUpdateBill(Bill bill);

	List<BillStateTransitionMapping> fetchBillStateMappingsByBillIds(Set<Long> distinctBillIds);

}
