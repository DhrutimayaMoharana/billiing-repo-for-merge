package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTncTypeDTO;

public interface WorkorderTncTypeService {

	CustomResponse getWorkorderTncTypes(SearchDTO search);

	CustomResponse addWorkorderTncType(WoTncTypeDTO tncTypeDTO);

	CustomResponse updateWorkorderTncType(WoTncTypeDTO tncTypeDTO);

	CustomResponse getWorkorderTncTypeById(SearchDTO search);

	CustomResponse removeWorkorderTncType(SearchDTO search);

}
