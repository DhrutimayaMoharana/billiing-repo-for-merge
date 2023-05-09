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

import erp.boq_mgmt.dao.ChainageBorewellBoqQuantityDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.ChainageBorewellBoqQuantityMapping;
import erp.boq_mgmt.entity.ChainageBorewellBoqQuantityTransacs;
import erp.boq_mgmt.entity.GenericChainageBoqQuantityMapping;

@Repository
public class ChainageBorewellBoqQuantityDaoImpl implements ChainageBorewellBoqQuantityDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long forceSaveChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj) {
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(cbqObj);
	}

	@Override
	public Long saveChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBorewellBoqQuantityMapping> cr = cb.createQuery(ChainageBorewellBoqQuantityMapping.class);
		Root<ChainageBorewellBoqQuantityMapping> root = cr.from(ChainageBorewellBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("borewellBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("chainage").get("id"),
				cbqObj.getChainage() != null ? cbqObj.getChainage().getId() : null));
		andPredicates
				.add(cb.equal(root.get("boq").get("id"), cbqObj.getBoq() != null ? cbqObj.getBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("borewellBoq").get("id"),
				cbqObj.getBorewellBoq() != null ? cbqObj.getBorewellBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("siteId"), cbqObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBorewellBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBorewellBoqQuantityMapping> results = query.getResultList();
		Long id = null;
		if (results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(cbqObj);
		}
		return id;
	}

	@Override
	public List<ChainageBorewellBoqQuantityMapping> fetchChainageBorewellBoqQuantityMappingBySearch(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBorewellBoqQuantityMapping> cr = cb.createQuery(ChainageBorewellBoqQuantityMapping.class);
		Root<ChainageBorewellBoqQuantityMapping> root = cr.from(ChainageBorewellBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("borewellBoq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getChainageId() != null)
			andPredicates.add(cb.equal(root.get("chainage").get("id"), search.getChainageId()));
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getHighwayBoqId() != null)
			andPredicates.add(cb.equal(root.get("borewellBoq").get("id"), search.getBorewellBoqId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBorewellBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBorewellBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveChainageBorewellBoqQuantityTransac(ChainageBorewellBoqQuantityTransacs cbqTransac) {
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(cbqTransac);
	}

	@Override
	public Boolean updateChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj) {
		if (cbqObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBorewellBoqQuantityMapping> cr = cb.createQuery(ChainageBorewellBoqQuantityMapping.class);
		Root<ChainageBorewellBoqQuantityMapping> root = cr.from(ChainageBorewellBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("borewellBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), cbqObj.getBoq().getId()));
		andPredicates.add(cb.equal(root.get("borewellBoq").get("id"), cbqObj.getBorewellBoq().getId()));
		andPredicates.add(cb.equal(root.get("chainage").get("id"), cbqObj.getChainage().getId()));
		andPredicates.add(cb.equal(root.get("siteId"), cbqObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), cbqObj.getCompanyId()));
		andPredicates.add(cb.notEqual(root.get("id"), cbqObj.getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBorewellBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBorewellBoqQuantityMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(cbqObj);
			return true;
		}
		return false;
	}

	@Override
	public Boolean forceUpdateChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj) {
		Session session = entityManager.unwrap(Session.class);
		session.update(cbqObj);
		return true;
	}

	@Override
	public Boolean forceUpdateAfterDetachChainageBorewellBoqQuantityMapping(ChainageBorewellBoqQuantityMapping cbqObj) {
		Session session = entityManager.unwrap(Session.class);
		session.detach(session.get(ChainageBorewellBoqQuantityMapping.class, cbqObj.getId()));
		session.update(cbqObj);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<ChainageBorewellBoqQuantityMapping> fetchBoqWiseCbq(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBorewellBoqQuantityMapping> cr = cb.createQuery(ChainageBorewellBoqQuantityMapping.class);
		Root<ChainageBorewellBoqQuantityMapping> root = cr.from(ChainageBorewellBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("borewellBoq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getHighwayBoqId() != null)
			andPredicates.add(cb.equal(root.get("borewellBoq").get("id"), search.getBorewellBoqId()));
		if (search.getChainageId() != null)
			andPredicates.add(cb.equal(root.get("chainage").get("id"), search.getChainageId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBorewellBoqQuantityMapping> query = session.createQuery(cr);
		if (search.getPageSize() != null && search.getPageNo() != null) {
			query.setMaxResults(search.getPageSize());
			query.setFirstResult((search.getPageNo() - 1) * search.getPageSize());
		}
		List<ChainageBorewellBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public ChainageBorewellBoqQuantityMapping fetchCbqById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		ChainageBorewellBoqQuantityMapping cbq = (ChainageBorewellBoqQuantityMapping) session
				.get(ChainageBorewellBoqQuantityMapping.class, id);
		return cbq;
	}

	@Override
	public List<ChainageBorewellBoqQuantityMapping> fetchChainageBorewellBoqQuantitiesByHbmId(Long bbmId) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBorewellBoqQuantityMapping> cr = cb.createQuery(ChainageBorewellBoqQuantityMapping.class);
		Root<ChainageBorewellBoqQuantityMapping> root = cr.from(ChainageBorewellBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("borewellBoq", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("borewellBoq").get("id"), bbmId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBorewellBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBorewellBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<ChainageBorewellBoqQuantityMapping> fetchChainageBorewellBoqQuantitiesByHbmIdWithOneCbqLess(Long hbmId,
			Long cbqId) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBorewellBoqQuantityMapping> cr = cb.createQuery(ChainageBorewellBoqQuantityMapping.class);
		Root<ChainageBorewellBoqQuantityMapping> root = cr.from(ChainageBorewellBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("borewellBoq", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("borewellBoq").get("id"), hbmId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), cbqId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBorewellBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBorewellBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<ChainageBorewellBoqQuantityMapping> fetchByRangeBoqAndSite(Long boqId,
			Integer fromChainageNameNumericValue, Integer toChainageNameNumericValue, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBorewellBoqQuantityMapping> cr = cb.createQuery(ChainageBorewellBoqQuantityMapping.class);
		Root<ChainageBorewellBoqQuantityMapping> root = cr.from(ChainageBorewellBoqQuantityMapping.class);
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
		Query<ChainageBorewellBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBorewellBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Long forceSaveChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj) {
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(cbqObj);
	}

	@Override
	public Long saveChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericChainageBoqQuantityMapping> cr = cb.createQuery(GenericChainageBoqQuantityMapping.class);
		Root<GenericChainageBoqQuantityMapping> root = cr.from(GenericChainageBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("genericBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("chainage").get("id"),
				cbqObj.getChainage() != null ? cbqObj.getChainage().getId() : null));
		andPredicates
				.add(cb.equal(root.get("boq").get("id"), cbqObj.getBoq() != null ? cbqObj.getBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("genericBoq").get("id"),
				cbqObj.getGenericBoq() != null ? cbqObj.getGenericBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("siteId"), cbqObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<GenericChainageBoqQuantityMapping> results = query.getResultList();
		Long id = null;
		if (results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(cbqObj);
		}
		return id;

	}

	@Override
	public List<GenericChainageBoqQuantityMapping> fetchChainageGenericBoqQuantityMappingBySearch(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericChainageBoqQuantityMapping> cr = cb.createQuery(GenericChainageBoqQuantityMapping.class);
		Root<GenericChainageBoqQuantityMapping> root = cr.from(GenericChainageBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("genericBoq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getChainageId() != null)
			andPredicates.add(cb.equal(root.get("chainage").get("id"), search.getChainageId()));
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getGenericBoqId() != null)
			andPredicates.add(cb.equal(root.get("genericBoq").get("id"), search.getGenericBoqId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<GenericChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Boolean updateChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj) {
		if (cbqObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericChainageBoqQuantityMapping> cr = cb.createQuery(GenericChainageBoqQuantityMapping.class);
		Root<GenericChainageBoqQuantityMapping> root = cr.from(GenericChainageBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("genericBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), cbqObj.getBoq().getId()));
		andPredicates.add(cb.equal(root.get("genericBoq").get("id"), cbqObj.getGenericBoq().getId()));
		andPredicates.add(cb.equal(root.get("chainage").get("id"), cbqObj.getChainage().getId()));
		andPredicates.add(cb.equal(root.get("siteId"), cbqObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), cbqObj.getCompanyId()));
		andPredicates.add(cb.notEqual(root.get("id"), cbqObj.getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<GenericChainageBoqQuantityMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(cbqObj);
			return true;
		}
		return false;
	}

	@Override
	public Boolean forceUpdateChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj) {
		Session session = entityManager.unwrap(Session.class);
		session.update(cbqObj);
		return true;
	}

	@Override
	public Boolean forceUpdateAfterDetachChainageGenericBoqQuantityMapping(GenericChainageBoqQuantityMapping cbqObj) {
		Session session = entityManager.unwrap(Session.class);
		session.detach(session.get(GenericChainageBoqQuantityMapping.class, cbqObj.getId()));
		session.update(cbqObj);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<GenericChainageBoqQuantityMapping> fetchBoqWiseCbqV1(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericChainageBoqQuantityMapping> cr = cb.createQuery(GenericChainageBoqQuantityMapping.class);
		Root<GenericChainageBoqQuantityMapping> root = cr.from(GenericChainageBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("genericBoq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getGenericBoqId() != null)
			andPredicates.add(cb.equal(root.get("genericBoq").get("id"), search.getGenericBoqId()));
		if (search.getChainageId() != null)
			andPredicates.add(cb.equal(root.get("chainage").get("id"), search.getChainageId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericChainageBoqQuantityMapping> query = session.createQuery(cr);
		if (search.getPageSize() != null && search.getPageNo() != null) {
			query.setMaxResults(search.getPageSize());
			query.setFirstResult((search.getPageNo() - 1) * search.getPageSize());
		}
		List<GenericChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public GenericChainageBoqQuantityMapping fetchCbqByIdV1(Long id, Integer woTypeId) {
//		Session session = entityManager.unwrap(Session.class);
//		GenericChainageBoqQuantityMapping cbq = (GenericChainageBoqQuantityMapping) session
//				.get(GenericChainageBoqQuantityMapping.class, id);
//		return cbq;

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericChainageBoqQuantityMapping> cr = cb.createQuery(GenericChainageBoqQuantityMapping.class);
		Root<GenericChainageBoqQuantityMapping> root = cr.from(GenericChainageBoqQuantityMapping.class);
		root.fetch("genericBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("id"), id));
		if (woTypeId != null) {
			andPredicates.add(cb.equal(root.get("genericBoq").get("workorderTypeId"), woTypeId));
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<GenericChainageBoqQuantityMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	@Override
	public List<GenericChainageBoqQuantityMapping> fetchChainageGenericBoqQuantitiesByHbmId(Long hbmId) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericChainageBoqQuantityMapping> cr = cb.createQuery(GenericChainageBoqQuantityMapping.class);
		Root<GenericChainageBoqQuantityMapping> root = cr.from(GenericChainageBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("genericBoq", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("genericBoq").get("id"), hbmId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<GenericChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<GenericChainageBoqQuantityMapping> fetchChainageGenericBoqQuantitiesByHbmIdWithOneCbqLess(Long hbmId,
			Long cbqId) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericChainageBoqQuantityMapping> cr = cb.createQuery(GenericChainageBoqQuantityMapping.class);
		Root<GenericChainageBoqQuantityMapping> root = cr.from(GenericChainageBoqQuantityMapping.class);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("genericBoq", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("genericBoq").get("id"), hbmId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), cbqId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("chainage").get("nameNumericValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<GenericChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<GenericChainageBoqQuantityMapping> fetchByRangeBoqAndSiteV1(Long boqId,
			Integer fromChainageNameNumericValue, Integer toChainageNameNumericValue, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericChainageBoqQuantityMapping> cr = cb.createQuery(GenericChainageBoqQuantityMapping.class);
		Root<GenericChainageBoqQuantityMapping> root = cr.from(GenericChainageBoqQuantityMapping.class);
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
		Query<GenericChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<GenericChainageBoqQuantityMapping> results = query.getResultList();
		return results;
	}

}
