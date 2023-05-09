package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.WorkLayerBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.WorkLayerDeactivateRequest;

public interface WorkLayerBoqService {

	CustomResponse addUpdateWorkLayerBoq(WorkLayerBoqsAddUpdateRequest requestDTO);

	CustomResponse deactivateWorkLayerBoqsByWorkLayerId(WorkLayerDeactivateRequest requestDTO);

}
