package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemDeactivateRequest;

public interface RfiChecklistItemBoqService {

	CustomResponse addUpdateRfiChecklistItemBoq(RfiChecklistItemBoqsAddUpdateRequest requestDTO);

	CustomResponse deactivateRfiChecklistItemBoqsByCheckListItemId(RfiChecklistItemDeactivateRequest requestDTO);

}
