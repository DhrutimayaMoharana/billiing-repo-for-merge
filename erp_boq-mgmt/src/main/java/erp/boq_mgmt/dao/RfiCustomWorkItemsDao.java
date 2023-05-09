package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.boq_mgmt.dto.request.RfiCustomWorkItemFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFinalSuccessFetchRequest;
import erp.boq_mgmt.entity.RfiCustomWorkItemStateTransition;
import erp.boq_mgmt.entity.RfiCustomWorkItems;
import erp.boq_mgmt.entity.RfiCustomWorkItemsV2;

public interface RfiCustomWorkItemsDao {

	Long saveRfiCustomWorkItem(RfiCustomWorkItems rfiCustomWorkItem);

	RfiCustomWorkItems fetchRfiCustomWorkItemById(Long id);

	Boolean deactivateRfiCustomWorkItem(RfiCustomWorkItems dbObj);

	Boolean updateRfiCustomWorkItem(RfiCustomWorkItems rfiCustomWorkItem);

	Long saveRfiCustomWorkItemStateTransitionMapping(RfiCustomWorkItemStateTransition stateTransition);

	List<RfiCustomWorkItemStateTransition> fetchRfiCustomWorkItemStateTransitionByRfiCustomWorkItemId(
			Long rfiCustomWorkItemId);

	List<RfiCustomWorkItemStateTransition> fetchRfiCustomWorkItemStateTransitionList(
			RfiCustomWorkItemFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap);

	Integer fetchRfiCustomWorkItemStateTransitionListCount(RfiCustomWorkItemFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap);

	List<RfiCustomWorkItemsV2> fetchRfiCustomWorkItemByStateIds(Set<Integer> finalSuccessStateIds,
			RfiCustomWorkItemFinalSuccessFetchRequest requestDTO);

	Long fetchRfiCustomWorkItemByStateIdsCount(Set<Integer> finalSuccessStateIds,
			RfiCustomWorkItemFinalSuccessFetchRequest requestDTO);

}
