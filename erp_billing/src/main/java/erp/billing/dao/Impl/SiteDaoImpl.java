package erp.billing.dao.Impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.billing.dao.SiteDao;
import erp.billing.entity.Site;

@Repository
public class SiteDaoImpl implements SiteDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Site fetchSiteById(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		return session.get(Site.class, siteId);
	}

}
