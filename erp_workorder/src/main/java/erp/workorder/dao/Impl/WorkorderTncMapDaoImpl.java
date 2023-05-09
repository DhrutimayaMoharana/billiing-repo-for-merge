package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.WorkorderTncMapDao;
import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.WoTncMappingVersions;

@Repository
public class WorkorderTncMapDaoImpl implements WorkorderTncMapDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Boolean addWorkorderTncs(WoTncMapping woTnc) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncMapping> cr = cb.createQuery(WoTncMapping.class);
		Root<WoTncMapping> root = cr.from(WoTncMapping.class);
		root.fetch("workorder", JoinType.LEFT);
		root.fetch("termAndCondition", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorder").get("id"), woTnc.getWorkorder().getId()));
		andPredicates.add(cb.equal(root.get("termAndCondition").get("id"), woTnc.getTermAndCondition().getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("id")));
		cr.select(root).where(andPredicate);
		Query<WoTncMapping> query = session.createQuery(cr);
		List<WoTncMapping> results = query.getResultList();
		if (results != null && results.size() > 0)
			return false;
		session.save(woTnc);
		return true;
	}

	@Override
	public Boolean saveUpdateWorkorderTncs(WoTncMapping woTnc) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncMapping> cr = cb.createQuery(WoTncMapping.class);
		Root<WoTncMapping> root = cr.from(WoTncMapping.class);
		root.fetch("workorder", JoinType.LEFT);
		root.fetch("termAndCondition", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorder").get("id"), woTnc.getWorkorder().getId()));
		andPredicates.add(cb.equal(root.get("termAndCondition").get("id"), woTnc.getTermAndCondition().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("id")));
		cr.select(root).where(andPredicate);
		Query<WoTncMapping> query = session.createQuery(cr);
		List<WoTncMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			if (woTnc.getId() == null || !woTnc.getId().equals(results.get(0).getId())) {
				return false;
			}
			woTnc.setIsActive(true);
			session.update(woTnc);
			return true;
		}
		session.save(woTnc);
		return true;
	}

	@Override
	public WoTncMapping fetchWorkorderTncMapById(Long woTncId) {

		Session session = entityManager.unwrap(Session.class);
		WoTncMapping woTnc = (WoTncMapping) session.get(WoTncMapping.class, woTncId);
		return woTnc;
	}

	@Override
	public void forceUpdateWorkorderTncMap(WoTncMapping woTnc) {

		Session session = entityManager.unwrap(Session.class);
		session.update(woTnc);
	}

	@Override
	public Object executeSQLQuery(String referenceSql) {

		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createSQLQuery(referenceSql);
		return query.uniqueResult();
	}

	@Override
	public List<WoTncMapping> fetchWorkorderTncMapByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncMapping> cr = cb.createQuery(WoTncMapping.class);
		Root<WoTncMapping> root = cr.from(WoTncMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorder").get("id"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTncMapping> query = session.createQuery(cr);
		List<WoTncMapping> results = query.getResultList();
		session.clear();
		session.flush();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public Long saveWorkorderTermsAndConditionsVersion(WoTncMappingVersions woTncVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(woTncVersion);
		session.flush();
		session.clear();
		return id;
	}

}
