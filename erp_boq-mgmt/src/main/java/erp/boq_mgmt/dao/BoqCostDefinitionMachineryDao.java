package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.entity.BoqCostDefinitionMachinery;

public interface BoqCostDefinitionMachineryDao {

	Long saveBoqCostDefinitionMachinery(BoqCostDefinitionMachinery boqCostDefinitionMachinery);

	List<BoqCostDefinitionMachinery> fetchBoqCostDefinitionMachineryByBoqCostDefinitionId(Long boqCostDefinitionId);

	BoqCostDefinitionMachinery fetchBoqCostDefinitionMachineryById(Long id);

	Boolean deactivateBoqCostDefinitionMachinery(BoqCostDefinitionMachinery dbObj);

	Boolean updateBoqCostDefinitionMachinery(BoqCostDefinitionMachinery boqCostDefinitionMachinery);

}
