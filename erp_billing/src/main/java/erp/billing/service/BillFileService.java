package erp.billing.service;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;

public interface BillFileService {

	CustomResponse getBillFiles(SearchDTO search);

	CustomResponse deactivateBillFiles(SearchDTO search);

	CustomResponse addBillFiles(SearchDTO search);

}
