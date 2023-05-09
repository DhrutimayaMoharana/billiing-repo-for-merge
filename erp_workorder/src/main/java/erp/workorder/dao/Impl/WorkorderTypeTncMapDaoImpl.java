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

import erp.workorder.dao.WorkorderTypeTncMapDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WoTypeTncMapping;

@Repository
public class WorkorderTypeTncMapDaoImpl implements WorkorderTypeTncMapDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WoTypeTncMapping> fetchWoTypesTncs(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTypeTncMapping> cr = cb.createQuery(WoTypeTncMapping.class);
		Root<WoTypeTncMapping> root = cr.from(WoTypeTncMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTypeTncMapping> query = session.createQuery(cr);
		List<WoTypeTncMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<WoTypeTncMapping> fetchWoTypesTncsByTncId(Long woTncId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTypeTncMapping> cr = cb.createQuery(WoTypeTncMapping.class);
		Root<WoTypeTncMapping> root = cr.from(WoTypeTncMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("woTnc").get("id"), woTncId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTypeTncMapping> query = session.createQuery(cr);
		List<WoTypeTncMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<WoTypeTncMapping> fetchWoTypeTncs(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTypeTncMapping> cr = cb.createQuery(WoTypeTncMapping.class);
		Root<WoTypeTncMapping> root = cr.from(WoTypeTncMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("woTypeId"), search.getWoTypeId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTypeTncMapping> query = session.createQuery(cr);
		List<WoTypeTncMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Long mapWorkorderTypeTnc(WoTypeTncMapping typeTncMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTypeTncMapping> cr = cb.createQuery(WoTypeTncMapping.class);
		Root<WoTypeTncMapping> root = cr.from(WoTypeTncMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), typeTncMap.getCompanyId()));
		andPredicates.add(cb.equal(root.get("woTypeId"), typeTncMap.getWoTypeId()));
		andPredicates.add(cb.equal(root.get("woTnc").get("id"), typeTncMap.getWoTnc().getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTypeTncMapping> query = session.createQuery(cr);
		List<WoTypeTncMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		return (Long) session.save(typeTncMap);

	}

	@Override
	public WoTypeTncMapping fetchWoTypeTncById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		WoTypeTncMapping obj = session.get(WoTypeTncMapping.class, id);
		return obj;
	}

	@Override
	public void forceUpdateWoTypeTncMapping(WoTypeTncMapping typeTnc) {

		Session session = entityManager.unwrap(Session.class);
		session.update(typeTnc);
	}

}
