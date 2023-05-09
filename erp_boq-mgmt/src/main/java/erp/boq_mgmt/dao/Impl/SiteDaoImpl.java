package erp.boq_mgmt.dao.Impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.boq_mgmt.dao.SiteDao;
import erp.boq_mgmt.entity.Site;
import erp.boq_mgmt.entity.SitesV2;

@Repository
public class SiteDaoImpl implements SiteDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Site fetchById(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		return session.get(Site.class, siteId);
	}

	@Override
	public void forceUpdateSite(Site site) {

		Session session = entityManager.unwrap(Session.class);
		session.update(site);
		session.flush();
		session.clear();
	}

	@Override
	public SitesV2 fetchSiteV2ById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		return session.get(SitesV2.class, id);
	}

}
