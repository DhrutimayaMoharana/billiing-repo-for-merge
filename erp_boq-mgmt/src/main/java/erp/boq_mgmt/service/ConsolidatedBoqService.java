package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;

public interface ConsolidatedBoqService {

	CustomResponse getConsolidatedBoqs(SearchDTO search);

}
