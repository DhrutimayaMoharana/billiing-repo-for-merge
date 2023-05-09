package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTypeTncMapRequestDTO;

public interface WorkorderTypeTncMapService {

	CustomResponse getWorkorderTypesTncsCount(SearchDTO search);

	CustomResponse getWorkorderTypeTncs(SearchDTO search);

	CustomResponse mapWorkorderTypeTnc(WoTypeTncMapRequestDTO typeTncMapRequestDTO);

	CustomResponse removeWorkorderTypeTnc(SearchDTO search);

}
