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

import erp.workorder.dao.WorkorderLabourWorkDao;
import erp.workorder.entity.WorkorderLabourType;
import erp.workorder.entity.WorkorderLabourWork;
import erp.workorder.entity.WorkorderLabourWorkItemMapping;
import erp.workorder.entity.WorkorderLabourWorkItemMappingVersion;
import erp.workorder.entity.WorkorderLabourWorkVersion;

@Repository
public class WorkorderLabourWorkDaoImpl implements WorkorderLabourWorkDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public WorkorderLabourWorkItemMapping fetchWorkorderLabourWorkItemMappingById(Long labourWorkItemId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderLabourWorkItemMapping labourWorkItem = session.get(WorkorderLabourWorkItemMapping.class,
				labourWorkItemId);
		return labourWorkItem;
	}

	@Override
	public List<WorkorderLabourWorkItemMapping> fetchWorkorderLabourWorkItemsByLabourWorkId(Long labourWorkId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourWorkItemMapping> cr = cb.createQuery(WorkorderLabourWorkItemMapping.class);
		Root<WorkorderLabourWorkItemMapping> root = cr.from(WorkorderLabourWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderLabourWorkId"), labourWorkId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderLabourWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderLabourWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public void forceUpdateWorkorderLabourWorkItem(WorkorderLabourWorkItemMapping labourWorkItem) {

		Session session = entityManager.unwrap(Session.class);
		session.update(labourWorkItem);
		session.flush();
		session.clear();
	}

	@Override
	public Long saveWorkorderLabourWork(WorkorderLabourWork labourWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourWork> cr = cb.createQuery(WorkorderLabourWork.class);
		Root<WorkorderLabourWork> root = cr.from(WorkorderLabourWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), labourWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderLabourWork> query = session.createQuery(cr);
		List<WorkorderLabourWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(labourWork);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderLabourWorkItemMap(WorkorderLabourWorkItemMapping workItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourWorkItemMapping> cr = cb.createQuery(WorkorderLabourWorkItemMapping.class);
		Root<WorkorderLabourWorkItemMapping> root = cr.from(WorkorderLabourWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderLabourWorkId"), workItem.getWorkorderLabourWorkId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), workItem.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderLabourWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderLabourWorkItemMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(workItem);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public WorkorderLabourWork fetchWorkorderLabourWorkById(Long labourWorkId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderLabourWork labourWork = session.get(WorkorderLabourWork.class, labourWorkId);
		return labourWork;
	}

	@Override
	public Boolean updateWorkorderLabourWork(WorkorderLabourWork labourWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourWork> cr = cb.createQuery(WorkorderLabourWork.class);
		Root<WorkorderLabourWork> root = cr.from(WorkorderLabourWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), labourWork.getId()));
		andPredicates.add(cb.equal(root.get("workorderId"), labourWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderLabourWork> query = session.createQuery(cr);
		List<WorkorderLabourWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(labourWork);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean updateWorkorderLabourWorkItem(WorkorderLabourWorkItemMapping workItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourWorkItemMapping> cr = cb.createQuery(WorkorderLabourWorkItemMapping.class);
		Root<WorkorderLabourWorkItemMapping> root = cr.from(WorkorderLabourWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), workItem.getId()));
		andPredicates.add(cb.equal(root.get("workorderLabourWorkId"), workItem.getWorkorderLabourWorkId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), workItem.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderLabourWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderLabourWorkItemMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(workItem);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<WorkorderLabourType> fetchWoLabourTypes(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourType> cr = cb.createQuery(WorkorderLabourType.class);
		Root<WorkorderLabourType> root = cr.from(WorkorderLabourType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderLabourType> query = session.createQuery(cr);
		List<WorkorderLabourType> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderLabourWork fetchWorkorderLabourWorkByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourWork> cr = cb.createQuery(WorkorderLabourWork.class);
		Root<WorkorderLabourWork> root = cr.from(WorkorderLabourWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderLabourWork> query = session.createQuery(cr);
		List<WorkorderLabourWork> results = query.getResultList();
		return results != null ? results.get(0) : null;
	}

	@Override
	public Long saveWorkorderLabourWorkVersion(WorkorderLabourWorkVersion labourWorkVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(labourWorkVersion);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderLabourWorkItemMapVersion(WorkorderLabourWorkItemMappingVersion workItemVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(workItemVersion);
		session.flush();
		session.clear();
		return id;
	}

}
