package erp.billing.dao;

import java.util.List;

import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillFile;

public interface BillFileDao {

	List<BillFile> fetchBillFiles(SearchDTO search);

	Long addBillFile(BillFile file);

	Boolean deactivateBillFile(Long billFileId, Long billId, Long userId);

}
