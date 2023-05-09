package erp.workorder.dao;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WorkorderBillInfo;

public interface WorkorderBillInfoDao {

	Integer saveWorkorderBillInfo(WorkorderBillInfo workorderBillInfoObj);

	Boolean updateWorkorderBillInfo(WorkorderBillInfo workorderBillInfoObj);

	WorkorderBillInfo fetchWorkorderBillInfo(SearchDTO searchDTO);

}
