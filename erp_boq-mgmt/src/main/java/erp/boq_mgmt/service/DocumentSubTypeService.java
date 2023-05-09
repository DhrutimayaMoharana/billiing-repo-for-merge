package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;

public interface DocumentSubTypeService {

	CustomResponse getDocSubTypes(SearchDTO search);

}
