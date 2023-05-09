package erp.billing.dao;

import java.util.List;

import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillDeduction;

public interface BillDeductionDao {

	Integer fetchBillDeductionIdByName(String deductionName, Integer companyId);

	Integer saveBillDeduction(BillDeduction deduction);

	List<BillDeduction> fetchBillDeductions(SearchDTO search);

}
