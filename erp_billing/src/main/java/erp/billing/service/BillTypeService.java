package erp.billing.service;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;

public interface BillTypeService {

	CustomResponse getBillTypes(SearchDTO search);

}
