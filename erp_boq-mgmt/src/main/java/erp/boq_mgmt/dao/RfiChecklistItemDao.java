package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsFetchRequest;
import erp.boq_mgmt.entity.RfiChecklistItemStateTransition;
import erp.boq_mgmt.entity.RfiChecklistItems;

public interface RfiChecklistItemDao {

	Integer saveRfiChecklistItem(RfiChecklistItems rfiChecklistItem);

	RfiChecklistItems fetchRfiChecklistItemById(Integer id);

	Boolean updateRfiChecklistItem(RfiChecklistItems rfiChecklistItem);

	Boolean deactivateRfiChecklistItem(RfiChecklistItems dbObj);

	Long saveRfiChecklistItemStateTransitionMapping(RfiChecklistItemStateTransition stateTransition);

	List<RfiChecklistItemStateTransition> fetchRfiChecklistItemStateTransitionByRfiChecklistItemId(
			Integer rfiChecklistItemId);

	List<RfiChecklistItemStateTransition> fetchRfiChecklistItemStateTransitionList(
			RfiChecklistItemBoqsFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap);

	Integer fetchRfiChecklistItemStateTransitionListCount(RfiChecklistItemBoqsFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap);

}
