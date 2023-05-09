package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryDeactivateRequest;

public interface BoqCostDefinitionMachineryService {

	CustomResponse addBoqCostDefinitionMachinery(BoqCostDefinitionMachineryAddUpdateRequest requestDTO);

	CustomResponse updateBoqCostDefinitionMachinery(BoqCostDefinitionMachineryAddUpdateRequest requestDTO);

	CustomResponse getBoqCostDefinitionMachineryByBoqCostDefinitionId(Long boqCostDefinitionId);

	CustomResponse deactivateBoqCostDefinitionMachinery(BoqCostDefinitionMachineryDeactivateRequest requestDTO);

	CustomResponse getMachineryTrip();

}
