package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.request.MaterialFetchRequest;
import erp.boq_mgmt.entity.BoqCostDefinitionMaterial;
import erp.boq_mgmt.entity.Material;

public interface BoqCostDefinitionMaterialDao {

	Long saveBoqCostDefinitionMaterial(BoqCostDefinitionMaterial boqCostDefinitionMaterial);

	List<BoqCostDefinitionMaterial> fetchBoqCostDefinitionMaterialByBoqCostDefinitionId(Long boqCostDefinitionId);

	BoqCostDefinitionMaterial fetchBoqCostDefinitionMaterialById(Long id);

	Boolean deactivateBoqCostDefinitionMaterial(BoqCostDefinitionMaterial dbObj);

	Boolean updateBoqCostDefinitionMaterial(BoqCostDefinitionMaterial boqCostDefinitionMaterial);

	List<Material> fetchMaterialList(MaterialFetchRequest requestDTO);

}
