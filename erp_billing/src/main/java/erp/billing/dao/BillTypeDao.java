package erp.billing.dao;

import java.util.List;

import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillType;

public interface BillTypeDao {

	List<BillType> fetchBillTypes(SearchDTO search);

}
