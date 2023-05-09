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

import erp.workorder.dao.ChainageDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Chainage;

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
		andPredicates.add(cb.equal(root.get("siteId"), chainage.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
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
		if (search.getSearchField() != null)
			andPredicates.add(cb.like(cb.lower(root.get("name")), "%" + search.getSearchField().toLowerCase() + "%"));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("name")));
		cr.distinct(true).select(root).where(andPredicate);
		cr.select(root).where(andPredicate);
		Query<Chainage> query = session.createQuery(cr);
		if (search.getPageSize() != null && search.getPageNo() != null) {
			query.setMaxResults(search.getPageSize());
			query.setFirstResult((search.getPageNo() - 1) * search.getPageSize());
		}
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
		cr.orderBy(cb.asc(root.get("name")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Chainage> query = session.createQuery(cr);
		List<Chainage> results = query.getResultList();
		return results;
	}

	@Override
	public void updateChainage(Chainage chainage) {

		Session session = entityManager.unwrap(Session.class);
		session.update(chainage);
		session.flush();
		session.clear();
	}

	@Override
	public Chainage fetchById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		return (Chainage) session.get(Chainage.class, id);
	}

}
