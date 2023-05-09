package erp.billing.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import erp.billing.entity.BillBoqItem;
import erp.billing.entity.BillBoqQuantityItem;
import erp.billing.entity.BillBoqQuantityItemTransac;

public interface BillBoqItemDao {

	Long saveBillBoqItem(BillBoqItem billBoq);

	Long saveBillBoqQuantityItem(BillBoqQuantityItem qtyItem);

	BillBoqItem fetchBillBoqItemByBillId(Long id, Long boqId, String vendorDescription, Long StructureTypeId);

	boolean forceUpdateBillBoqItem(BillBoqItem savedBillBoq);

	BillBoqQuantityItem fetchBillBoqQuantityItemById(Long id);

	boolean updateBillBoqQuantityItem(BillBoqQuantityItem savedQtyItem);

	void saveBillBoqQuantityItemTransac(BillBoqQuantityItemTransac qtyItemTransac);

	List<BillBoqItem> fetchBillBoqItemsByBillId(Long billId);

	List<BillBoqQuantityItem> fetchBillBoqQuantityItemsByBillId(Long id);

	List<BillBoqQuantityItem> fetchBillBoqQuantityItemsByWorkorderId(Long workorderId);

	List<BillBoqQuantityItem> fetchUptoCurrentBillBoqQuantityItemsByWorkorderId(Long workorderId, Date billToDate);

	void forceUpdateBillBoqQuantityItem(BillBoqQuantityItem billQtyItem);

	List<BillBoqQuantityItem> fetchBillBoqQuantityItemsByBillIds(Set<Long> distinctBillIds);

}
