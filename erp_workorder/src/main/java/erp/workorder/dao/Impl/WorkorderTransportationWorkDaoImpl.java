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

import erp.workorder.dao.WorkorderTransportationWorkDao;
import erp.workorder.entity.WorkorderTransportWork;
import erp.workorder.entity.WorkorderTransportWorkItemMapping;
import erp.workorder.entity.WorkorderTransportWorkItemMappingVersion;
import erp.workorder.entity.WorkorderTransportWorkVersion;

@Repository
public class WorkorderTransportationWorkDaoImpl implements WorkorderTransportationWorkDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveWorkorderTransportWork(WorkorderTransportWork transportWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderTransportWork> cr = cb.createQuery(WorkorderTransportWork.class);
		Root<WorkorderTransportWork> root = cr.from(WorkorderTransportWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), transportWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderTransportWork> query = session.createQuery(cr);
		List<WorkorderTransportWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(transportWork);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderTransportWorkItemMap(WorkorderTransportWorkItemMapping workItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderTransportWorkItemMapping> cr = cb.createQuery(WorkorderTransportWorkItemMapping.class);
		Root<WorkorderTransportWorkItemMapping> root = cr.from(WorkorderTransportWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderTransportWorkId"), workItem.getWorkorderTransportWorkId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), workItem.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderTransportWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderTransportWorkItemMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(workItem);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public WorkorderTransportWork fetchWorkorderTransportWorkById(Long transportWorkId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderTransportWork transportWork = session.get(WorkorderTransportWork.class, transportWorkId);
		return transportWork;
	}

	@Override
	public Boolean updateWorkorderTransportWork(WorkorderTransportWork transportWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderTransportWork> cr = cb.createQuery(WorkorderTransportWork.class);
		Root<WorkorderTransportWork> root = cr.from(WorkorderTransportWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), transportWork.getId()));
		andPredicates.add(cb.equal(root.get("workorderId"), transportWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderTransportWork> query = session.createQuery(cr);
		List<WorkorderTransportWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(transportWork);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<WorkorderTransportWorkItemMapping> fetchWorkorderTransportWorkItemsByTransportWorkId(
			Long transportWorkId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderTransportWorkItemMapping> cr = cb.createQuery(WorkorderTransportWorkItemMapping.class);
		Root<WorkorderTransportWorkItemMapping> root = cr.from(WorkorderTransportWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderTransportWorkId"), transportWorkId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderTransportWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderTransportWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderTransportWorkItemMapping fetchWorkorderTransportWorkItemMappingById(Long transportWorkItemId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderTransportWorkItemMapping transportWorkItem = session.get(WorkorderTransportWorkItemMapping.class,
				transportWorkItemId);
		return transportWorkItem;
	}

	@Override
	public Boolean updateWorkorderTransportWorkItem(WorkorderTransportWorkItemMapping workItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderTransportWorkItemMapping> cr = cb.createQuery(WorkorderTransportWorkItemMapping.class);
		Root<WorkorderTransportWorkItemMapping> root = cr.from(WorkorderTransportWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), workItem.getId()));
		andPredicates.add(cb.equal(root.get("workorderTransportWorkId"), workItem.getWorkorderTransportWorkId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), workItem.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderTransportWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderTransportWorkItemMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(workItem);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public void forceUpdateWorkorderTransportWorkItem(WorkorderTransportWorkItemMapping transportWorkItem) {

		Session session = entityManager.unwrap(Session.class);
		session.update(transportWorkItem);
		session.flush();
		session.clear();

	}

	@Override
	public List<WorkorderTransportWorkItemMapping> fetchWorkorderTransportWorkItemsBySiteId(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderTransportWorkItemMapping> cr = cb.createQuery(WorkorderTransportWorkItemMapping.class);
		Root<WorkorderTransportWorkItemMapping> root = cr.from(WorkorderTransportWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderTransportWork").get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderTransportWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderTransportWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderTransportWork fetchWorkorderTransportWorkByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderTransportWork> cr = cb.createQuery(WorkorderTransportWork.class);
		Root<WorkorderTransportWork> root = cr.from(WorkorderTransportWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderTransportWork> query = session.createQuery(cr);
		List<WorkorderTransportWork> results = query.getResultList();
		return results != null ? results.get(0) : null;
	}

	@Override
	public Long saveWorkorderTransportWorkVersion(WorkorderTransportWorkVersion transportWorkVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(transportWorkVersion);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderTransportWorkItemMapVersion(WorkorderTransportWorkItemMappingVersion itemVersionObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(itemVersionObj);
		session.flush();
		session.clear();
		return id;
	}

}
