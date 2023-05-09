package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.request.BillDeductionMappingAddUpdateRequest;

public interface BillDeductionMappingService {

	CustomResponse getDeductions(SearchDTO search);

	CustomResponse addOrUpdateBillDeductionMap(BillDeductionMappingAddUpdateRequest mapObj);

}
