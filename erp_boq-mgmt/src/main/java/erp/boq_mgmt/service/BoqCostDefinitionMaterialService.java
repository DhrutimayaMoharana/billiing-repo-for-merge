package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialDeactivateRequest;
import erp.boq_mgmt.dto.request.MaterialFetchRequest;

public interface BoqCostDefinitionMaterialService {

	CustomResponse addBoqCostDefinitionMaterial(BoqCostDefinitionMaterialAddUpdateRequest requestDTO);

	CustomResponse updateBoqCostDefinitionMaterial(BoqCostDefinitionMaterialAddUpdateRequest requestDTO);

	CustomResponse getBoqCostDefinitionMaterialByBoqCostDefinitionId(Long boqCostDefinitionId);

	CustomResponse deactivateBoqCostDefinitionMaterial(BoqCostDefinitionMaterialDeactivateRequest requestDTO);

	CustomResponse getMaterialList(MaterialFetchRequest requestDTO);

}
