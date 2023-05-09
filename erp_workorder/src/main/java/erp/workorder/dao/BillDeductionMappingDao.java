package erp.workorder.dao;

import java.util.Date;
import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.BillDeductionMapTransac;
import erp.workorder.entity.BillDeductionMapping;

public interface BillDeductionMappingDao {

	List<BillDeductionMapping> fetchMappedBillDeductions(SearchDTO search);

	List<BillDeductionMapping> fetchMappedBillDeductionsByWorkorderId(Long workorderId);

	List<BillDeductionMapping> fetchUptoCurrentMappedBillDeductionsByWorkorderId(Long workorderId, Date toDate);

	BillDeductionMapping fetchBillDeductionMapById(Long id);

	Long saveBillDeductionMapping(BillDeductionMapping map);

	boolean updateBillDeductionMapping(BillDeductionMapping savedMap);

	void saveBillDeductionMapTransac(BillDeductionMapTransac mapTransac);

}
