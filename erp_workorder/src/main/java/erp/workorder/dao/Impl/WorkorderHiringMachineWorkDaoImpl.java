package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.WorkorderHiringMachineWorkDao;
import erp.workorder.entity.UnitMaster;
import erp.workorder.entity.WorkorderHiringMachineRateDetailVersion;
import erp.workorder.entity.WorkorderHiringMachineRateDetails;
import erp.workorder.entity.WorkorderHiringMachineWork;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMapping;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMappingVersion;
import erp.workorder.entity.WorkorderHiringMachineWorkVersion;

@Repository
public class WorkorderHiringMachineWorkDaoImpl implements WorkorderHiringMachineWorkDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveWorkorderHiringMachineWork(WorkorderHiringMachineWork hmWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWork> cr = cb.createQuery(WorkorderHiringMachineWork.class);
		Root<WorkorderHiringMachineWork> root = cr.from(WorkorderHiringMachineWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), hmWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWork> query = session.createQuery(cr);
		List<WorkorderHiringMachineWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(hmWork);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderHiringMachineWorkItemMap(WorkorderHiringMachineWorkItemMapping workItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWorkItemMapping> cr = cb
				.createQuery(WorkorderHiringMachineWorkItemMapping.class);
		Root<WorkorderHiringMachineWorkItemMapping> root = cr.from(WorkorderHiringMachineWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderHmWorkId"), workItem.getWorkorderHmWorkId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), workItem.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderHiringMachineWorkItemMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(workItem);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public WorkorderHiringMachineWork fetchWorkorderHiringMachineWorkById(Long hmWorkId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderHiringMachineWork consultantWork = session.get(WorkorderHiringMachineWork.class, hmWorkId);
		return consultantWork;
	}

	@Override
	public Boolean updateWorkorderHiringMachineWork(WorkorderHiringMachineWork hmWork) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWork> cr = cb.createQuery(WorkorderHiringMachineWork.class);
		Root<WorkorderHiringMachineWork> root = cr.from(WorkorderHiringMachineWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), hmWork.getId()));
		andPredicates.add(cb.equal(root.get("workorderId"), hmWork.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWork> query = session.createQuery(cr);
		List<WorkorderHiringMachineWork> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.merge(hmWork);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<WorkorderHiringMachineWorkItemMapping> fetchWorkorderHiringMachineWorkItemsByHmWorkId(Long hmWorkId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWorkItemMapping> cr = cb
				.createQuery(WorkorderHiringMachineWorkItemMapping.class);
		Root<WorkorderHiringMachineWorkItemMapping> root = cr.from(WorkorderHiringMachineWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderHmWorkId"), hmWorkId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderHiringMachineWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderHiringMachineWorkItemMapping fetchWorkorderHiringMachineWorkItemMappingById(Long hmWorkItemId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderHiringMachineWorkItemMapping hmWorkItem = session.get(WorkorderHiringMachineWorkItemMapping.class,
				hmWorkItemId);
		return hmWorkItem;
	}

	@Override
	public Boolean updateWorkorderHiringMachineWorkItem(WorkorderHiringMachineWorkItemMapping workItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWorkItemMapping> cr = cb
				.createQuery(WorkorderHiringMachineWorkItemMapping.class);
		Root<WorkorderHiringMachineWorkItemMapping> root = cr.from(WorkorderHiringMachineWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), workItem.getId()));
		andPredicates.add(cb.equal(root.get("workorderHmWorkId"), workItem.getWorkorderHmWorkId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), workItem.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderHiringMachineWorkItemMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.merge(workItem);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public void forceUpdateWorkorderHiringMachineWorkItem(WorkorderHiringMachineWorkItemMapping hmWorkItem) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(hmWorkItem);
		session.flush();
		session.clear();

	}

	@Override
	public List<WorkorderHiringMachineWorkItemMapping> fetchWorkorderHiringMachineWorkItemsBySiteId(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWorkItemMapping> cr = cb
				.createQuery(WorkorderHiringMachineWorkItemMapping.class);
		Root<WorkorderHiringMachineWorkItemMapping> root = cr.from(WorkorderHiringMachineWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderHmWork").get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderHiringMachineWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<UnitMaster> fetchHiringMachineUnits() {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UnitMaster> cr = cb.createQuery(UnitMaster.class);
		Root<UnitMaster> root = cr.from(UnitMaster.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<UnitMaster> query = session.createQuery(cr);
		List<UnitMaster> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderHiringMachineRateDetails> fetchHiringMachineItemRateDetailsByWorkItemIds(
			Set<Long> workItemIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineRateDetails> cr = cb.createQuery(WorkorderHiringMachineRateDetails.class);
		Root<WorkorderHiringMachineRateDetails> root = cr.from(WorkorderHiringMachineRateDetails.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(root.get("woHiringMachineItemId").in(workItemIds));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineRateDetails> query = session.createQuery(cr);
		List<WorkorderHiringMachineRateDetails> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderHiringMachineRateDetails> fetchHiringMachineItemRateDetailsByWorkItemIdsAndIsActive(
			Set<Long> workItemIds, Boolean isActive) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineRateDetails> cr = cb.createQuery(WorkorderHiringMachineRateDetails.class);
		Root<WorkorderHiringMachineRateDetails> root = cr.from(WorkorderHiringMachineRateDetails.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(root.get("woHiringMachineItemId").in(workItemIds));
		andPredicates.add(cb.equal(root.get("isActive"), isActive));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineRateDetails> query = session.createQuery(cr);
		List<WorkorderHiringMachineRateDetails> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderHiringMachineRateDetails saveOrUpdateHiringMachineItemRateDetail(
			WorkorderHiringMachineRateDetails itemRateDetail) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineRateDetails> cr = cb.createQuery(WorkorderHiringMachineRateDetails.class);
		Root<WorkorderHiringMachineRateDetails> root = cr.from(WorkorderHiringMachineRateDetails.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (itemRateDetail.getId() != null) {
			andPredicates.add(cb.notEqual(root.get("id"), itemRateDetail.getId()));
		}
		andPredicates.add(cb.equal(root.get("woHiringMachineItemId"), itemRateDetail.getWoHiringMachineItemId()));
		if (itemRateDetail.getShift() != null) {
			andPredicates.add(cb.equal(root.get("shift"), itemRateDetail.getShift()));
		} else {
			andPredicates.add(cb.isNull(root.get("shift")));
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineRateDetails> query = session.createQuery(cr);
		List<WorkorderHiringMachineRateDetails> results = query.getResultList();
		if (results != null && results.size() > 0) {
			if (itemRateDetail.getId() != null) {
				return null;
			}
			itemRateDetail.setId(results.get(0).getId());
			session.merge(itemRateDetail);
			session.flush();
			session.clear();
			return itemRateDetail;
		}
		session.merge(itemRateDetail);
		session.flush();
		session.clear();
		return itemRateDetail;
	}

	@Override
	public Boolean forceUpdateHiringMachineItemRateDetail(WorkorderHiringMachineRateDetails itemRateDetail) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(itemRateDetail);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public WorkorderHiringMachineWork fetchWorkorderHiringMachineWorkByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWork> cr = cb.createQuery(WorkorderHiringMachineWork.class);
		Root<WorkorderHiringMachineWork> root = cr.from(WorkorderHiringMachineWork.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWork> query = session.createQuery(cr);
		List<WorkorderHiringMachineWork> results = query.getResultList();
		return results != null ? results.get(0) : null;
	}

	@Override
	public Long saveWorkorderHiringMachineWorkVersion(WorkorderHiringMachineWorkVersion hiringMachineWorkVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(hiringMachineWorkVersion);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderHiringMachineWorkItemMapVersion(
			WorkorderHiringMachineWorkItemMappingVersion itemVersionObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(itemVersionObj);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveWorkorderHiringMachineRateDetailVersion(
			WorkorderHiringMachineRateDetailVersion rateDetailVersionObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(rateDetailVersionObj);
		session.flush();
		session.clear();
		return id;
	}
}
