package erp.billing.service;

import erp.billing.dto.BillDeductionMappingDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;

public interface BillDeductionMappingService {

	CustomResponse getDeductions(SearchDTO search);

	CustomResponse addOrUpdateBillDeductionMap(BillDeductionMappingDTO mapObj);

}
