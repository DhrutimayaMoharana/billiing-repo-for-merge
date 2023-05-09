package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.SiteDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Site;

@Repository
public class SiteDaoImpl implements SiteDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Site fetchSiteById(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		return session.get(Site.class, siteId);
	}

	@Override
	public List<Site> fetchSites(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Site> cr = cb.createQuery(Site.class);
		Root<Site> root = cr.from(Site.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Site> query = session.createQuery(cr);
		List<Site> results = query.getResultList();
		return results;
	}

}