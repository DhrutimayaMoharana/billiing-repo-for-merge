package erp.boq_mgmt.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.boq_mgmt.dao.SiteVariableDao;
import erp.boq_mgmt.entity.SiteVariableValue;

@Repository
public class SiteVariableDaoImpl implements SiteVariableDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<SiteVariableValue> fetchSiteVariableValuesBySiteVariableIdsAndSiteId(
			Set<Integer> distinctSiteVariableIds, Integer siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SiteVariableValue> cr = cb.createQuery(SiteVariableValue.class);
		Root<SiteVariableValue> root = cr.from(SiteVariableValue.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		Expression<Integer> siteVariableIdsExp = root.get("variableId");
		andPredicates.add(siteVariableIdsExp.in(distinctSiteVariableIds));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<SiteVariableValue> query = session.createQuery(cr);
		List<SiteVariableValue> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

}
