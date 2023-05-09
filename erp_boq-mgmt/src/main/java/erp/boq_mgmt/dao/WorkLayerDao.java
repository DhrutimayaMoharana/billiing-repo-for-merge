package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.boq_mgmt.dto.request.WorkLayerBoqsFetchRequest;
import erp.boq_mgmt.entity.WorkLayerStateTransition;
import erp.boq_mgmt.entity.WorkLayer;

public interface WorkLayerDao {

	Integer saveWorkLayer(WorkLayer workLayer);

	WorkLayer fetchWorkLayerById(Integer id);

	Boolean updateWorkLayer(WorkLayer workLayer);

	Boolean deactivateWorkLayer(WorkLayer dbObj);

	Long saveWorkLayerStateTransitionMapping(WorkLayerStateTransition stateTransition);

	List<WorkLayerStateTransition> fetchWorkLayerStateTransitionByWorkLayerId(
			Integer workLayerId);

	List<WorkLayerStateTransition> fetchWorkLayerStateTransitionList(
			WorkLayerBoqsFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap);

	Integer fetchWorkLayerStateTransitionListCount(WorkLayerBoqsFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap);

}
