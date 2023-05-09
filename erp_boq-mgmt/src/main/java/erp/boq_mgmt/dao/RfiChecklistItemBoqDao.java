package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Set;

import erp.boq_mgmt.dto.request.RfiChecklistItemFinalSuccessFetchRequest;
import erp.boq_mgmt.entity.RfiChecklistItemBoqs;
import erp.boq_mgmt.entity.RfiChecklistItemBoqsV2;

public interface RfiChecklistItemBoqDao {

	List<RfiChecklistItemBoqs> fetchRfiChecklistItemBoqsByCheckListItemIds(Set<Integer> checklistItemIds);

	Boolean deactivateRfiChecklistItemBoq(RfiChecklistItemBoqs clib);

	Long saveRfiChecklistItemBoq(RfiChecklistItemBoqs clibObj);

	List<RfiChecklistItemBoqsV2> fetchRfiChecklistItemBoqsByStateIds(Set<Integer> finalSuccessStateIds,
			RfiChecklistItemFinalSuccessFetchRequest requestDTO);

	Long fetchRfiChecklistItemBoqsByStateIdsCount(Set<Integer> finalSuccessStateIds,
			RfiChecklistItemFinalSuccessFetchRequest requestDTO);

}
