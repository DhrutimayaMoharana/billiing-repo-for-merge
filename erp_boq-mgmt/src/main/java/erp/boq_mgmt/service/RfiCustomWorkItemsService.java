package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemNextPossibleStatesFetchRequest;

public interface RfiCustomWorkItemsService {

	CustomResponse addRfiCustomWorkItem(RfiCustomWorkItemAddUpdateRequest requestDTO);

	CustomResponse updateRfiCustomWorkItem(RfiCustomWorkItemAddUpdateRequest requestDTO);

	CustomResponse getRfiCustomWorkItemById(Long id);

	CustomResponse getRfiCustomWorkItem(RfiCustomWorkItemFetchRequest requestDTO);

	CustomResponse deactivateRfiCustomWorkItem(RfiCustomWorkItemDeactivateRequest requestDTO);

	CustomResponse getNextPossibleStates(RfiCustomWorkItemNextPossibleStatesFetchRequest requestObj);

	CustomResponse getRfiCustomWorkItemStateTransitionByRfiCustomWorkItemId(Long rfiCustomWorkItemId);

	CustomResponse getRfiCustomWorkItemFinalSuccessList(RfiCustomWorkItemFinalSuccessFetchRequest requestDTO);

}
