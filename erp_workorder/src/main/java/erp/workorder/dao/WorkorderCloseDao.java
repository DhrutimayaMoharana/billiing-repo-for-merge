package erp.workorder.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.workorder.dto.request.WorkorderCloseGetRequest;
import erp.workorder.entity.WorkorderClose;
import erp.workorder.entity.WorkorderCloseStateTransitionMapping;

public interface WorkorderCloseDao {

	List<WorkorderClose> fetchWorkorderCloseList(WorkorderCloseGetRequest clientRequestDTO);

	List<WorkorderCloseStateTransitionMapping> fetchWorkorderCloseStateMappings(Long workorderCloseId);

	Long saveWorkorderClose(WorkorderClose workorderClose);

	void mapWorkorderCloseStateTransition(WorkorderCloseStateTransitionMapping trasition);

	WorkorderClose fetchWorkorderCloseById(Long id);

	Boolean updateWorkorderClose(WorkorderClose workorderClose);

	void forceUpdateWorkorderClose(WorkorderClose workorderClose);

	List<WorkorderCloseStateTransitionMapping> fetchWorkorderCloseStateMappingsByWorkorderCloseIds(
			Set<Long> distinctWorkorderCloseIds);

	List<WorkorderCloseStateTransitionMapping> fetchWorkorderCloseStateTransitionList(
			WorkorderCloseGetRequest clientRequestDTO, Map<Integer, Set<Integer>> roleStateMap, Integer draftStateId,
			Set<Integer> stateVisibilityIds);

	Integer fetchWorkorderCloseStateTransitionListCount(WorkorderCloseGetRequest clientRequestDTO,
			Map<Integer, Set<Integer>> roleStateMap, Integer draftStateId, Set<Integer> stateVisibilityIds);

	List<WorkorderClose> fetchWorkorderCloseListByWorkorderIdsAndStateIds(Set<Long> workorderIds,
			Set<Integer> stateIds);

}
