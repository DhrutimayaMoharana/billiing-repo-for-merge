package erp.boq_mgmt.dao.Impl;

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

import erp.boq_mgmt.dao.ChainageDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Chainage;
import erp.boq_mgmt.entity.ChainageTraverse;

@Repository
public class ChainageDaoImpl implements ChainageDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveChainage(Chainage chainage) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Chainage> cr = cb.createQuery(Chainage.class);
		Root<Chainage> root = cr.from(Chainage.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("name"), chainage.getName()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), chainage.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<Chainage> query = session.createQuery(cr);
		List<Chainage> results = query.getResultList();
		Long id = null;
		if (results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(chainage);
			session.detach(chainage);
			session.flush();
			session.clear();
		}
		return id;
	}

	@Override
	public Integer fetchCount(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Chainage> cr = cb.createQuery(Chainage.class);
		Root<Chainage> root = cr.from(Chainage.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getSearchField() != null)
			andPredicates.add(cb.like(cb.lower(root.get("name")), "%" + search.getSearchField().toLowerCase() + "%"));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("name")));
		cr.select(root).where(andPredicate);
		Query<Chainage> query = session.createQuery(cr);
		List<Chainage> results = query.getResultList();
		return results != null ? results.size() : 0;
	}

	@Override
	public List<Chainage> fetchChainages(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Chainage> cr = cb.createQuery(Chainage.class);
		Root<Chainage> root = cr.from(Chainage.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("nameNumericValue")));
		cr.select(root).where(andPredicate);
		Query<Chainage> query = session.createQuery(cr);
		List<Chainage> results = query.getResultList();
		return results;
	}

	@Override
	public List<Chainage> fetchChainagesBySite(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Chainage> cr = cb.createQuery(Chainage.class);
		Root<Chainage> root = cr.from(Chainage.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Chainage> query = session.createQuery(cr);
		List<Chainage> results = query.getResultList();
		return results;
	}

	@Override
	public void updateChainage(Chainage chainage) {

		Session session = entityManager.unwrap(Session.class);
		session.detach(session.get(Chainage.class, chainage.getId()));
		session.flush();
		session.update(chainage);
		session.clear();
	}

	@Override
	public Long forceSaveChainage(Chainage chainage) {

		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(chainage);
	}

	@Override
	public Chainage fetchById(Long chainageId) {

		Session session = entityManager.unwrap(Session.class);
		Chainage chainage = (Chainage) session.get(Chainage.class, chainageId);
		return chainage;
	}

	@Override
	public ChainageTraverse fetchChainageTraverseById(Long chainageId) {

		Session session = entityManager.unwrap(Session.class);
		ChainageTraverse chainage = (ChainageTraverse) session.get(ChainageTraverse.class, chainageId);
		return chainage;
	}

}
