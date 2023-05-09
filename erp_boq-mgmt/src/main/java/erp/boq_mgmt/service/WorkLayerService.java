package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.WorkLayerBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.WorkLayerBoqsFetchRequest;
import erp.boq_mgmt.dto.request.WorkLayerDeactivateRequest;
import erp.boq_mgmt.dto.request.WorkLayerFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.WorkLayerNextPossibleStatesFetchRequest;

public interface WorkLayerService {

	CustomResponse addWorkLayer(WorkLayerBoqsAddUpdateRequest requestDTO);

	CustomResponse updateWorkLayer(WorkLayerBoqsAddUpdateRequest requestDTO);

	CustomResponse getWorkLayerList(WorkLayerBoqsFetchRequest requestDTO);

	CustomResponse getWorkLayerById(Integer id);

	CustomResponse deactivateWorkLayer(WorkLayerDeactivateRequest requestDTO);

	CustomResponse getNextPossibleStates(WorkLayerNextPossibleStatesFetchRequest requestObj);

	CustomResponse getWorkLayerStateTransitionByWorkLayerId(Integer rfiChecklistItemId);

	CustomResponse getWorkLayerFinalSuccessList(WorkLayerFinalSuccessFetchRequest requestDTO);

}
