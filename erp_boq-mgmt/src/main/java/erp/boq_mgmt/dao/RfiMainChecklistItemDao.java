package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.entity.RfiMainChecklist;

public interface RfiMainChecklistItemDao {

	List<RfiMainChecklist> fetchRfiMainChecklistItems(Long rfiId);

	Long saveRfiMainChecklistItem(RfiMainChecklist rfiMainChecklist);

	Boolean updateRfiMainChecklistItem(RfiMainChecklist rfiMainChecklist);

}
