package erp.billing.dao;

import java.util.Date;
import java.util.List;

import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillDeductionMapTransac;
import erp.billing.entity.BillDeductionMapping;
import erp.billing.entity.BillDeductionMappingV2;

public interface BillDeductionMappingDao {

	List<BillDeductionMapping> fetchMappedBillDeductions(SearchDTO search);

	List<BillDeductionMapping> fetchMappedBillDeductionsByWorkorderId(Long workorderId);

	List<BillDeductionMapping> fetchUptoCurrentMappedBillDeductionsByWorkorderId(Long workorderId, Date toDate);

	BillDeductionMapping fetchBillDeductionMapById(Long id);

	Long saveBillDeductionMapping(BillDeductionMapping map);

	boolean updateBillDeductionMapping(BillDeductionMapping savedMap);

	void saveBillDeductionMapTransac(BillDeductionMapTransac mapTransac);

	List<BillDeductionMappingV2> fetchMappedBillDeductionsByWorkorderBillInfoId(SearchDTO search);

	List<BillDeductionMapping> fetchUptoCurrentMappedBillDeductionsByWorkorderBillInfoId(Integer workorderBillInfoId);

}
