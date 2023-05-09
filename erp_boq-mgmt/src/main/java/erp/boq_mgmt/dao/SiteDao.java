package erp.boq_mgmt.dao;

import erp.boq_mgmt.entity.Site;
import erp.boq_mgmt.entity.SitesV2;

public interface SiteDao {

	Site fetchById(Long siteId);

	void forceUpdateSite(Site site);

	SitesV2 fetchSiteV2ById(Long id);

}
