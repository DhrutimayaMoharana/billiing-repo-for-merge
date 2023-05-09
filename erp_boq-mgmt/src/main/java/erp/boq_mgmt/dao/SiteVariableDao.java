package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Set;

import erp.boq_mgmt.entity.SiteVariableValue;

public interface SiteVariableDao {

	List<SiteVariableValue> fetchSiteVariableValuesBySiteVariableIdsAndSiteId(Set<Integer> distinctSiteVariableIds,
			Integer siteId);

}
