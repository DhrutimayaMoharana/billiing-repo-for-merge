package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.request.WorkorderCloseAddUpdateRequest;
import erp.workorder.dto.request.WorkorderCloseDeactivateRequest;
import erp.workorder.dto.request.WorkorderCloseGetRequest;
import erp.workorder.dto.request.WorkorderCloseNextActionsRequest;
import erp.workorder.dto.request.WorkorderCloseUpdateStateRequest;

public interface WorkorderCloseService {

	CustomResponse getWorkorderClose(WorkorderCloseGetRequest clientRequestDTO);

	CustomResponse addOrUpdateWorkorderClose(WorkorderCloseAddUpdateRequest clientRequestDTO);

	CustomResponse deactivateWorkorderClose(WorkorderCloseDeactivateRequest clientRequestDTO);

	CustomResponse updateWorkorderCloseState(WorkorderCloseUpdateStateRequest clientRequestDTO);

	CustomResponse getNextPossibleStates(WorkorderCloseNextActionsRequest clientRequestDTO);

	CustomResponse getWorkorderCloseType();

}
