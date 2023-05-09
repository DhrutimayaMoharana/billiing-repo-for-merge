package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Site;

public interface SiteDao {

	Site fetchSiteById(Long siteId);

	List<Site> fetchSites(SearchDTO search);

}
