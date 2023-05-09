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

import erp.workorder.dao.WorkorderConsultantWorkDao;
import erp.workorder.entity.WorkorderConsultantWork;
import erp.workorder.entity.WorkorderConsultantWorkItemMapping;
import erp.workorder.entity.WorkorderConsultantWorkItemMappingVersion;
import erp.workorder.entity.WorkorderConsultantWorkVersion;

@Repository
public class WorkorderConsultantWorkDaoImpl implements WorkorderConsultantWorkDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveWorkorderConsultantWork(WorkorderConsultantWork consultantWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWork> cr = cb.createQuery(WorkorderConsultantWork.class);
		Root<WorkorderConsultantWork> root = cr.from(WorkorderConsultantWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), consultantWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWork> query = session.createQuery(cr);
		List<WorkorderConsultantWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(consultantWork);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderConsultantWorkItemMap(WorkorderConsultantWorkItemMapping workItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWorkItemMapping> cr = cb.createQuery(WorkorderConsultantWorkItemMapping.class);
		Root<WorkorderConsultantWorkItemMapping> root = cr.from(WorkorderConsultantWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderConsultantWorkId"), workItem.getWorkorderConsultantWorkId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), workItem.getDescription().toUpperCase()));
		if (workItem.getChainage() != null)
			andPredicates.add(cb.equal(root.get("chainage").get("id"), workItem.getChainage().getId()));
		else
			andPredicates.add(cb.isNull(root.get("chainage")));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderConsultantWorkItemMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(workItem);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public WorkorderConsultantWork fetchWorkorderConsultantWorkById(Long consultantWorkId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderConsultantWork consultantWork = session.get(WorkorderConsultantWork.class, consultantWorkId);
		return consultantWork;
	}

	@Override
	public Boolean updateWorkorderConsultantWork(WorkorderConsultantWork consultantWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWork> cr = cb.createQuery(WorkorderConsultantWork.class);
		Root<WorkorderConsultantWork> root = cr.from(WorkorderConsultantWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), consultantWork.getId()));
		andPredicates.add(cb.equal(root.get("workorderId"), consultantWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWork> query = session.createQuery(cr);
		List<WorkorderConsultantWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(consultantWork);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<WorkorderConsultantWorkItemMapping> fetchWorkorderConsultantWorkItemsByConsultantWorkId(
			Long consultantWorkId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWorkItemMapping> cr = cb.createQuery(WorkorderConsultantWorkItemMapping.class);
		Root<WorkorderConsultantWorkItemMapping> root = cr.from(WorkorderConsultantWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderConsultantWorkId"), consultantWorkId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderConsultantWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderConsultantWorkItemMapping fetchWorkorderConsultantWorkItemMappingById(Long consultantWorkItemId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderConsultantWorkItemMapping consultantWorkItem = session.get(WorkorderConsultantWorkItemMapping.class,
				consultantWorkItemId);
		return consultantWorkItem;
	}

	@Override
	public Boolean updateWorkorderConsultantWorkItem(WorkorderConsultantWorkItemMapping workItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWorkItemMapping> cr = cb.createQuery(WorkorderConsultantWorkItemMapping.class);
		Root<WorkorderConsultantWorkItemMapping> root = cr.from(WorkorderConsultantWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), workItem.getId()));
		andPredicates.add(cb.equal(root.get("workorderConsultantWorkId"), workItem.getWorkorderConsultantWorkId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), workItem.getDescription().toUpperCase()));
		if (workItem.getChainage() != null)
			andPredicates.add(cb.equal(root.get("chainage").get("id"), workItem.getChainage().getId()));
		else
			andPredicates.add(cb.isNull(root.get("chainage")));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderConsultantWorkItemMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(workItem);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public void forceUpdateWorkorderConsultantWorkItem(WorkorderConsultantWorkItemMapping consultantWorkItem) {

		Session session = entityManager.unwrap(Session.class);
		session.update(consultantWorkItem);
		session.flush();
		session.clear();

	}

	@Override
	public List<WorkorderConsultantWorkItemMapping> fetchWorkorderConsultantWorkItemsBySiteId(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWorkItemMapping> cr = cb.createQuery(WorkorderConsultantWorkItemMapping.class);
		Root<WorkorderConsultantWorkItemMapping> root = cr.from(WorkorderConsultantWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderConsultantWork").get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderConsultantWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Object fetchWorkorderConsultantWorkItemCategoryDescriptions(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWorkItemMapping> cr = cb.createQuery(WorkorderConsultantWorkItemMapping.class);
		Root<WorkorderConsultantWorkItemMapping> root = cr.from(WorkorderConsultantWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.join("workorderConsultantWork").join("site").get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.isNotNull(root.get("categoryDescription")));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("categoryDescription")));
		cr.select(root.get("categoryDescription")).distinct(true).where(andPredicate);
		Query<WorkorderConsultantWorkItemMapping> query = session.createQuery(cr);
		Object results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderConsultantWork fetchWorkorderConsultantWorkByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWork> cr = cb.createQuery(WorkorderConsultantWork.class);
		Root<WorkorderConsultantWork> root = cr.from(WorkorderConsultantWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWork> query = session.createQuery(cr);
		List<WorkorderConsultantWork> results = query.getResultList();
		return results != null ? results.get(0) : null;
	}

	@Override
	public Long saveWorkorderConsultantWorkVersion(WorkorderConsultantWorkVersion consultantWorkVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(consultantWorkVersion);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderConsultantWorkItemMapVersion(WorkorderConsultantWorkItemMappingVersion itemVersionObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(itemVersionObj);
		session.flush();
		session.clear();
		return id;
	}

}
