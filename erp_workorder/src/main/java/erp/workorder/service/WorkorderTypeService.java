package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderTypeDTO;

public interface WorkorderTypeService {

	CustomResponse getWorkorderTypes(SearchDTO search);

	CustomResponse addWorkorderType(WorkorderTypeDTO typeDTO);

	CustomResponse updateWorkorderType(WorkorderTypeDTO typeDTO);

	CustomResponse getWorkorderTypeById(SearchDTO search);

	CustomResponse removeWorkorderType(SearchDTO search);

}
