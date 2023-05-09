package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.BillDeduction;

public interface BillDeductionDao {

	Integer fetchBillDeductionIdByName(String deductionName, Integer companyId);

	Integer saveBillDeduction(BillDeduction deduction);

	List<BillDeduction> fetchBillDeductions(SearchDTO search);

}
