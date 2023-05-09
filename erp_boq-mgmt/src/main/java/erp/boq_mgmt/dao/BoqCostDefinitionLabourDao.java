package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.entity.BoqCostDefinitionLabour;

public interface BoqCostDefinitionLabourDao {

	Long saveBoqCostDefinitionLabour(BoqCostDefinitionLabour boqCostDefinitionLabour);

	List<BoqCostDefinitionLabour> fetchBoqCostDefinitionLabourByBoqCostDefinitionId(Long boqCostDefinitionId);

	BoqCostDefinitionLabour fetchBoqCostDefinitionLabourById(Long id);

	Boolean deactivateBoqCostDefinitionLabour(BoqCostDefinitionLabour dbObj);

	Boolean updateBoqCostDefinitionLabour(BoqCostDefinitionLabour boqCostDefinitionLabour);

}
