package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsFetchRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemNextPossibleStatesFetchRequest;

public interface RfiChecklistItemService {

	CustomResponse addRfiChecklistItem(RfiChecklistItemBoqsAddUpdateRequest requestDTO);

	CustomResponse updateRfiChecklistItem(RfiChecklistItemBoqsAddUpdateRequest requestDTO);

	CustomResponse getRfiChecklistItemList(RfiChecklistItemBoqsFetchRequest requestDTO);

	CustomResponse getRfiChecklistItemById(Integer id);

	CustomResponse deactivateRfiChecklistItem(RfiChecklistItemDeactivateRequest requestDTO);

	CustomResponse getNextPossibleStates(RfiChecklistItemNextPossibleStatesFetchRequest requestObj);

	CustomResponse getRfiChecklistItemStateTransitionByRfiChecklistItemId(Integer rfiChecklistItemId);

	CustomResponse getRfiChecklistItemFinalSuccessList(RfiChecklistItemFinalSuccessFetchRequest requestDTO);

}
