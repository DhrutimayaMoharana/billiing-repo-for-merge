package erp.billing.service;

import erp.billing.dto.BillDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;

public interface BillService {

	CustomResponse getBills(SearchDTO search);

	CustomResponse getBillById(SearchDTO search);

	CustomResponse addOrUpdateBill(BillDTO billObj);

	CustomResponse getBillPrintResponseData(SearchDTO search);

	CustomResponse getBillPriceEscalationData(SearchDTO search);

	CustomResponse updateBillState(SearchDTO search);

	CustomResponse getNextPossibleStates(SearchDTO search);

}
