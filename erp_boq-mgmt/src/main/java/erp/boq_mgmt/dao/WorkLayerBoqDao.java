package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Set;

import erp.boq_mgmt.dto.request.WorkLayerFinalSuccessFetchRequest;
import erp.boq_mgmt.entity.WorkLayerBoqs;
import erp.boq_mgmt.entity.WorkLayerBoqsV2;

public interface WorkLayerBoqDao {

	List<WorkLayerBoqs> fetchWorkLayerBoqsByWorkLayerIds(Set<Integer> workLayerIds);

	Boolean deactivateWorkLayerBoq(WorkLayerBoqs wlb);

	Long saveWorkLayerBoq(WorkLayerBoqs wlbObj);

	List<WorkLayerBoqsV2> fetchWorkLayerBoqsByStateIds(Set<Integer> finalSuccessStateIds,
			WorkLayerFinalSuccessFetchRequest requestDTO);

	Long fetchWorkLayerBoqsByStateIdsCount(Set<Integer> finalSuccessStateIds,
			WorkLayerFinalSuccessFetchRequest requestDTO);

}
