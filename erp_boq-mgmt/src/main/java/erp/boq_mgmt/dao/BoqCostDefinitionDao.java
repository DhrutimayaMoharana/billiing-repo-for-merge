package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.boq_mgmt.dto.request.BoqCostDefinitionFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.UndefinedCostDefinitionBoqsFetchRequest;
import erp.boq_mgmt.entity.BoqCostDefinition;
import erp.boq_mgmt.entity.BoqCostDefinitionStateTransition;
import erp.boq_mgmt.entity.BoqItem;

public interface BoqCostDefinitionDao {

	Long saveBoqCostDefinition(BoqCostDefinition boqCostDefinition);

	BoqCostDefinition fetchBoqCostDefinitionById(Long id);

	Boolean deactivateBoqCostDefinition(BoqCostDefinition dbObj);

	Boolean updateBoqCostDefinition(BoqCostDefinition boqCostDefinition);

	Long saveBoqCostDefinitionStateTransitionMapping(BoqCostDefinitionStateTransition stateTransition);

	List<BoqCostDefinitionStateTransition> fetchBoqCostDefinitionStateTransitionByBoqCostDefinitionId(
			Long boqCostDefinitionId);

	List<BoqCostDefinitionStateTransition> fetchBoqCostDefinitionStateTransitionList(
			BoqCostDefinitionFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap);

	Integer fetchBoqCostDefinitionStateTransitionListCount(BoqCostDefinitionFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap);

	List<BoqCostDefinition> fetchBoqCostDefinitionByStateIds(Set<Integer> finalSuccessStateIds,
			BoqCostDefinitionFinalSuccessFetchRequest requestDTO);

	Long fetchBoqCostDefinitionByStateIdsCount(Set<Integer> finalSuccessStateIds,
			BoqCostDefinitionFinalSuccessFetchRequest requestDTO);

	List<BoqCostDefinition> fetchBoqCostDefinitionList(UndefinedCostDefinitionBoqsFetchRequest requestDTO);

	List<BoqItem> fetchUndefinedCostDefinitionBoqs(UndefinedCostDefinitionBoqsFetchRequest requestDTO,
			Set<Long> distinctDefinedCostDefinitionBoqIds);

}
