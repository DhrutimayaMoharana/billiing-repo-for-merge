package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderDTO;

public interface WorkorderBasicDetailService {

	CustomResponse addBasicWorkorder(WorkorderDTO workorder);

	CustomResponse getBasicWorkorderById(SearchDTO search);

	CustomResponse updateBasicWorkorder(WorkorderDTO workorder);

}
