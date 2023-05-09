package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WorkorderBoqWorkDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.ChainageBoqQuantityMapping;
import erp.workorder.entity.HighwayBoqMapping;
import erp.workorder.entity.StructureBoqQuantityMapping;
import erp.workorder.entity.StructureType;
import erp.workorder.entity.WorkorderBoqWork;
import erp.workorder.entity.WorkorderBoqWorkLocation;
import erp.workorder.entity.WorkorderBoqWorkLocationVersion;
import erp.workorder.entity.WorkorderBoqWorkQtyMapping;
import erp.workorder.entity.WorkorderBoqWorkQtyMappingVersion;
import erp.workorder.entity.WorkorderBoqWorkVersion;

@Repository
public class WorkorderBoqWorkDaoImpl implements WorkorderBoqWorkDao {

	@Autowired
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@Override
	public List<ChainageBoqQuantityMapping> fetchActiveCbqsByChainageIdsAndSiteId(Set<Long> chainageIds, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("highwayBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (chainageIds != null) {
			In<Long> inClause = cb.in(root.get("chainage").get("id"));
			for (Long id : chainageIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ChainageBoqQuantityMapping> fetchActiveCbqsBetweenChainageNumericValuesAndSiteIdAndWoCategories(
			Integer fromChainageValue, Integer toChainageValue, Set<Long> workorderCategoryIds, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ChainageBoqQuantityMapping> cr = cb.createQuery(ChainageBoqQuantityMapping.class);
		Root<ChainageBoqQuantityMapping> root = cr.from(ChainageBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("chainage", JoinType.LEFT);
		root.fetch("highwayBoq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (workorderCategoryIds != null) {
			In<Long> inClause = cb.in(root.join("boq").join("category").get("id"));
			for (Long id : workorderCategoryIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("chainage").get("numericChainageValue"), fromChainageValue));
		andPredicates.add(cb.lessThanOrEqualTo(root.get("chainage").get("numericChainageValue"), toChainageValue));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("boq").get("id")));
		cr.orderBy(cb.asc(root.get("chainage").get("numericChainageValue")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ChainageBoqQuantityMapping> query = session.createQuery(cr);
		List<ChainageBoqQuantityMapping> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}

	@Transactional(readOnly = true)
	@Override
	public List<HighwayBoqMapping> fetchHighwayBoqQtys(Long siteId, Set<Long> workorderCategoryIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (workorderCategoryIds != null) {
			In<Long> inClause = cb.in(root.get("category").get("id"));
			for (Long id : workorderCategoryIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}

	@Transactional(readOnly = true)
	@Override
	public List<HighwayBoqMapping> fetchHighwayBoqQtysByBoqId(Long siteId, Long boqId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}

	@Transactional(readOnly = true)
	@Override
	public List<StructureBoqQuantityMapping> fetchActiveSbqsByStructureTypeIdAndStructureIdAndCategoryIds(
			Long structureTypeId, Long structureId, Set<Long> workorderCategoryIds, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("structure", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (workorderCategoryIds != null) {
			In<Long> inClause = cb.in(root.get("category").get("id"));
			for (Long id : workorderCategoryIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.join("structure").join("type").get("id"), structureTypeId));
		if (structureId != null) {
			andPredicates.add(cb.equal(root.get("structure").get("id"), structureId));
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}

	@Transactional(readOnly = true)
	@Override
	public List<StructureBoqQuantityMapping> fetchActiveSbqsByStructureTypeIdAndStructureIdAndBoqId(
			Long structureTypeId, Long structureId, Long boqId, String boqDes, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("structure", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.join("structure").join("type").get("id"), structureTypeId));
		if (structureId != null) {
			andPredicates.add(cb.equal(root.get("structure").get("id"), structureId));
		}
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		if (boqDes != null)
			andPredicates.add(cb.equal(root.get("description"), boqDes));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}

	@Override
	public Long saveWorkorderBoqWork(WorkorderBoqWork boqWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWork> cr = cb.createQuery(WorkorderBoqWork.class);
		Root<WorkorderBoqWork> root = cr.from(WorkorderBoqWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), boqWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderBoqWork> query = session.createQuery(cr);
		List<WorkorderBoqWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(boqWork);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public List<WorkorderBoqWorkQtyMapping> fetchStructureWoBoqWorkQtys(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWorkQtyMapping> cr = cb.createQuery(WorkorderBoqWorkQtyMapping.class);
		Root<WorkorderBoqWorkQtyMapping> root = cr.from(WorkorderBoqWorkQtyMapping.class);
		root.fetch("boqWork", JoinType.LEFT);
		root.fetch("boqWork", JoinType.LEFT).fetch("locations", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("boqWork").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("boqWork").get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("structureTypeId"), search.getStructureTypeId()));
		if (search.getStructureId() != null) {
			andPredicates.add(cb.equal(root.get("structureId"), search.getStructureId()));
		} else {
			andPredicates.add(cb.isNull(root.get("structureId")));
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderBoqWorkQtyMapping> query = session.createQuery(cr);
		List<WorkorderBoqWorkQtyMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderBoqWorkQtyMapping> fetchChainageWoBoqWorkQtys(SearchDTO search, Set<Long> chainageIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWorkQtyMapping> cr = cb.createQuery(WorkorderBoqWorkQtyMapping.class);
		Root<WorkorderBoqWorkQtyMapping> root = cr.from(WorkorderBoqWorkQtyMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("boqWork", JoinType.LEFT);
		root.fetch("boqWork", JoinType.LEFT).fetch("locations", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("boqWork").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("boqWork").get("siteId"), search.getSiteId()));
		andPredicates.add(cb.isNull(root.join("boqWork").join("locations").get("structureTypeId")));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderBoqWorkQtyMapping> query = session.createQuery(cr);
		List<WorkorderBoqWorkQtyMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Boolean updateWorkorderBoqWork(WorkorderBoqWork savedBoqWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWork> cr = cb.createQuery(WorkorderBoqWork.class);
		Root<WorkorderBoqWork> root = cr.from(WorkorderBoqWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), savedBoqWork.getId()));
		andPredicates.add(cb.equal(root.get("workorderId"), savedBoqWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderBoqWork> query = session.createQuery(cr);
		List<WorkorderBoqWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		if (savedBoqWork.getId() != null && savedBoqWork.getId().longValue() > 0L) {
			session.detach(session.get(WorkorderBoqWork.class, savedBoqWork.getId()));
			session.clear();
		}
		session.merge(savedBoqWork);
		return true;
	}

	@Override
	public void forceDeactivateBoqWorkQtys(List<WorkorderBoqWorkQtyMapping> boqQtysToDeactivate) {

		Session session = entityManager.unwrap(Session.class);
		for (WorkorderBoqWorkQtyMapping obj : boqQtysToDeactivate) {
			obj.setIsActive(false);
			obj.setModifiedOn(new Date());
			session.merge(obj);
		}
	}

	@Override
	public void forceDeactivateBoqWorkLocations(List<WorkorderBoqWorkLocation> boqLocsToDeactivate) {

		Session session = entityManager.unwrap(Session.class);
		for (WorkorderBoqWorkLocation obj : boqLocsToDeactivate) {
			obj.setIsActive(false);
			obj.setModifiedOn(new Date());
			session.merge(obj);
		}
	}

	@Override
	public List<WorkorderBoqWorkQtyMapping> fetchSiteChainagesWoBoqWorkQtys(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWorkQtyMapping> cr = cb.createQuery(WorkorderBoqWorkQtyMapping.class);
		Root<WorkorderBoqWorkQtyMapping> root = cr.from(WorkorderBoqWorkQtyMapping.class);
		root.fetch("boqWork", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("boqWork").get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("boqWork").get("isActive"), true));
		andPredicates.add(cb.isNull(root.join("boqWork").join("locations").get("structureTypeId")));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderBoqWorkQtyMapping> query = session.createQuery(cr);
		List<WorkorderBoqWorkQtyMapping> results = query.getResultList();
		return results != null ? results : null;
	}

	@Override
	public StructureType fetchStructureTypeById(Long structureTypeId) {

		Session session = entityManager.unwrap(Session.class);
		StructureType type = session.get(StructureType.class, structureTypeId);
		return type;
	}

	@Override
	public WorkorderBoqWork fetchWorkorderBoqWorkByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWork> cr = cb.createQuery(WorkorderBoqWork.class);
		Root<WorkorderBoqWork> root = cr.from(WorkorderBoqWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderBoqWork> query = session.createQuery(cr);
		List<WorkorderBoqWork> results = query.getResultList();
		return results != null ? results.get(0) : null;
	}

	@Override
	public Long saveWorkorderBoqWorkVersion(WorkorderBoqWorkVersion boqWorkVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(boqWorkVersion);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderBoqWorkQtyMapVersion(WorkorderBoqWorkQtyMappingVersion boqWorkQtyMappingVersionObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(boqWorkQtyMappingVersionObj);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderBoqWorkLocationVersion(WorkorderBoqWorkLocationVersion boqWorkLocationVersionObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(boqWorkLocationVersionObj);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderBoqWorkQtyMap(WorkorderBoqWorkQtyMapping amendBoqWorkQtyMappingObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(amendBoqWorkQtyMappingObj);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderBoqWorkLocation(WorkorderBoqWorkLocation boqWorkLocationObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(boqWorkLocationObj);
		session.flush();
		session.clear();
		return id;
	}

}
