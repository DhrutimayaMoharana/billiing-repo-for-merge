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

import erp.boq_mgmt.dao.WorkorderBoqWorkDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.WorkorderBoqWorkQtyMapping;

@Repository
public class WorkorderBoqWorkDaoImpl implements WorkorderBoqWorkDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderBoqWorkQtyMapping> fetchWoBoqWorkQtysByBoqId(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWorkQtyMapping> cr = cb.createQuery(WorkorderBoqWorkQtyMapping.class);
		Root<WorkorderBoqWorkQtyMapping> root = cr.from(WorkorderBoqWorkQtyMapping.class);
		root.fetch("boqWork", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		andPredicates.add(cb.equal(root.get("boqWork").get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("boqWork").get("isActive"), true));
		andPredicates.add(cb.isNull(root.get("structureTypeId")));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderBoqWorkQtyMapping> query = session.createQuery(cr);
		List<WorkorderBoqWorkQtyMapping> results = query.getResultList();
		return results != null ? results : null;
	}

	@Override
	public List<WorkorderBoqWorkQtyMapping> fetchWoBoqWorkQtys(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWorkQtyMapping> cr = cb.createQuery(WorkorderBoqWorkQtyMapping.class);
		Root<WorkorderBoqWorkQtyMapping> root = cr.from(WorkorderBoqWorkQtyMapping.class);
		root.fetch("boqWork", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("boqWork").get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("boqWork").get("isActive"), true));
		andPredicates.add(cb.isNull(root.get("structureTypeId")));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderBoqWorkQtyMapping> query = session.createQuery(cr);
		List<WorkorderBoqWorkQtyMapping> results = query.getResultList();
		return results != null ? results : null;
	}

	@Override
	public List<WorkorderBoqWorkQtyMapping> fetchStructureWoBoqWorkQtys(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWorkQtyMapping> cr = cb.createQuery(WorkorderBoqWorkQtyMapping.class);
		Root<WorkorderBoqWorkQtyMapping> root = cr.from(WorkorderBoqWorkQtyMapping.class);
		root.fetch("boqWork", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("boqWork").get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("boqWork").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("structureTypeId"), search.getStructureTypeId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderBoqWorkQtyMapping> query = session.createQuery(cr);
		List<WorkorderBoqWorkQtyMapping> results = query.getResultList();
		return results != null ? results : null;
	}

	@Override
	public List<WorkorderBoqWorkQtyMapping> fetchStructureWoBoqWorkQtysByBoqId(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWorkQtyMapping> cr = cb.createQuery(WorkorderBoqWorkQtyMapping.class);
		Root<WorkorderBoqWorkQtyMapping> root = cr.from(WorkorderBoqWorkQtyMapping.class);
		root.fetch("boqWork", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), search.getDescription().trim().toUpperCase()));
		andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		andPredicates.add(cb.equal(root.get("boqWork").get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("boqWork").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("structureTypeId"), search.getStructureTypeId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderBoqWorkQtyMapping> query = session.createQuery(cr);
		List<WorkorderBoqWorkQtyMapping> results = query.getResultList();
		return results != null ? results : null;
	}
}
