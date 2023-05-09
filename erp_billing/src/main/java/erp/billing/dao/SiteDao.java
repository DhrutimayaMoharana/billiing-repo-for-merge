package erp.billing.dao;

import erp.billing.entity.Site;

public interface SiteDao {

	Site fetchSiteById(Long siteId);

}
