package erp.billing.dao.Impl;

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

import erp.billing.dao.WorkorderHireMachineryDao;
import erp.billing.entity.WorkorderHiringMachineRateDetails;
import erp.billing.entity.WorkorderHiringMachineWork;
import erp.billing.entity.WorkorderHiringMachineWorkItemMapping;

@Repository
public class WorkorderHireMachineryDaoImpl implements WorkorderHireMachineryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderHiringMachineWorkItemMapping> fetchWorkorderHiringMachineWorkItemMapping(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWorkItemMapping> cr = cb
				.createQuery(WorkorderHiringMachineWorkItemMapping.class);
		Root<WorkorderHiringMachineWorkItemMapping> root = cr.from(WorkorderHiringMachineWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderHmWork").get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderHiringMachineWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderHiringMachineWork fetchWorkorderHiringMachineWork(Long workorderId) {

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
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	@Override
	public List<WorkorderHiringMachineWorkItemMapping> fetchWorkorderHiringMachineWorkItemMappingByWorkId(
			Long hiringWorkId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderHiringMachineWorkItemMapping> cr = cb
				.createQuery(WorkorderHiringMachineWorkItemMapping.class);
		Root<WorkorderHiringMachineWorkItemMapping> root = cr.from(WorkorderHiringMachineWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderHmWorkId"), hiringWorkId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderHiringMachineWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderHiringMachineWorkItemMapping> results = query.getResultList();
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

}
