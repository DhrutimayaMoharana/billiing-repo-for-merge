package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;

public interface WorkorderFileService {

	CustomResponse getWorkorderFiles(SearchDTO search);

	CustomResponse addWorkorderFiles(SearchDTO search);

	CustomResponse deactivateWorkorderFiles(SearchDTO search);

}
