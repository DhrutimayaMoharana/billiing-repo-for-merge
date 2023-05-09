package erp.billing.dao;

import erp.billing.dto.SearchDTO;
import erp.billing.entity.WorkorderBillInfo;

public interface WorkorderBillInfoDao {

	Integer saveWorkorderBillInfo(WorkorderBillInfo workorderBillInfoObj);

	Boolean updateWorkorderBillInfo(WorkorderBillInfo workorderBillInfoObj);

	WorkorderBillInfo fetchWorkorderBillInfo(SearchDTO searchDTO);

}
