package erp.boq_mgmt.dao.Impl;

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

import erp.boq_mgmt.dao.ChainageBoqQuantityDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.ChainageBoqQuantityMapping;
import erp.boq_mgmt.entity.ChainageBoqQuantityTransacs;

@Repository
public class ChainageBoqQuantityDaoImpl implements ChainageBoqQuantityDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("highwayBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("chainage").get("id"),
				cbqObj.getChainage() != null ? cbqObj.getChainage().getId() : null));
		andPredicates
				.add(cb.equal(root.get("boq").get("id"), cbqObj.getBoq() != null ? cbqObj.getBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("highwayBoq").get("id"),
				cbqObj.getHighwayBoq() != null ? cbqObj.getHighwayBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("siteId"), cbqObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		Long id = null;
		if (results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(cbqObj);
		}
		return id;
	}

	@Override
	public List<ChainageBoqQuantityMapping> fetchChainageBoqQuantityMappingBySearch(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("highwayBoq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getChainageId() != null)
			andPredicates.add(cb.equal(root.get("chainage").get("id"), search.getChainageId()));
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getHighwayBoqId() != null)
			andPredicates.add(cb.equal(root.get("highwayBoq").get("id"), search.getHighwayBoqId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveChainageBoqQuantityTransac(ChainageBoqQuantityTransacs cbqTransac) {

		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(cbqTransac);
	}

	@Override
	public Boolean updateChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj) {

		if (cbqObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("highwayBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), cbqObj.getBoq().getId()));
		andPredicates.add(cb.equal(root.get("highwayBoq").get("id"), cbqObj.getHighwayBoq().getId()));
		andPredicates.add(cb.equal(root.get("chainage").get("id"), cbqObj.getChainage().getId()));
		andPredicates.add(cb.equal(root.get("siteId"), cbqObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), cbqObj.getCompanyId()));
		andPredicates.add(cb.notEqual(root.get("id"), cbqObj.getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(cbqObj);
			return true;
		}
		return false;
	}

	@Override
	public List<ChainageBoqQuantityMapping> fetchBoqWiseCbq(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("highwayBoq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getHighwayBoqId() != null)
			andPredicates.add(cb.equal(root.get("highwayBoq").get("id"), search.getHighwayBoqId()));
		if (search.getChainageId() != null)
			andPredicates.add(cb.equal(root.get("chainage").get("id"), search.getChainageId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		if (search.getPageSize() != null && search.getPageNo() != null) {
			query.setMaxResults(search.getPageSize());
			query.setFirstResult((search.getPageNo() - 1) * search.getPageSize());
		}
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public ChainageBoqQuantityMapping fetchCbqById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		ChainageBoqQuantityMapping cbq = (ChainageBoqQuantityMapping) session.get(ChainageBoqQuantityMapping.class, id);
		return cbq;
	}

	@Override
	public List<ChainageBoqQuantityMapping> fetchChainageBoqQuantitiesByHbmId(Long hbmId) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("highwayBoq", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("highwayBoq").get("id"), hbmId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<ChainageBoqQuantityMapping> fetchChainageBoqQuantitiesByHbmIdWithOneCbqLess(Long hbmId, Long cbqId) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("highwayBoq", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("highwayBoq").get("id"), hbmId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), cbqId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Long forceSaveChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj) {

		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(cbqObj);
	}

	@Override
	public Boolean forceUpdateChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj) {

		Session session = entityManager.unwrap(Session.class);
		session.update(cbqObj);
		return true;
	}

	@Override
	public Boolean forceUpdateAfterDetachChainageBoqQuantityMapping(ChainageBoqQuantityMapping cbqObj) {

		Session session = entityManager.unwrap(Session.class);
		session.detach(session.get(ChainageBoqQuantityMapping.class, cbqObj.getId()));
		session.update(cbqObj);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<ChainageBoqQuantityMapping> fetchByRangeBoqAndSite(Long boqId, Integer fromChainageNameNumericValue,
			Integer toChainageNameNumericValue, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(
				cb.greaterThanOrEqualTo(root.get("chainage").get("nameNumericValue"), fromChainageNameNumericValue));
		andPredicates
				.add(cb.lessThanOrEqualTo(root.get("chainage").get("nameNumericValue"), toChainageNameNumericValue));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

}
