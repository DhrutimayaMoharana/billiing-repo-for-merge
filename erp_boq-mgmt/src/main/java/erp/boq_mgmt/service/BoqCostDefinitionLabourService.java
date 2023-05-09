package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqCostDefinitionLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionLabourDeactivateRequest;

public interface BoqCostDefinitionLabourService {

	CustomResponse addBoqCostDefinitionLabour(BoqCostDefinitionLabourAddUpdateRequest requestDTO);

	CustomResponse updateBoqCostDefinitionLabour(BoqCostDefinitionLabourAddUpdateRequest requestDTO);

	CustomResponse getBoqCostDefinitionLabourByBoqCostDefinitionId(Long boqCostDefinitionId);

	CustomResponse deactivateBoqCostDefinitionLabour(BoqCostDefinitionLabourDeactivateRequest requestDTO);

}
