package erp.billing.dao;

import java.util.List;

import erp.billing.entity.WorkorderConsultantWorkItemMapping;

public interface WorkorderConsultantWorkDao {

	List<WorkorderConsultantWorkItemMapping> fetchWorkorderConsultantWorkItemsByWorkorderId(Long workorderId);

	List<WorkorderConsultantWorkItemMapping> fetchWorkorderConsultantWorkItemsBySiteId(Long siteId);

}
