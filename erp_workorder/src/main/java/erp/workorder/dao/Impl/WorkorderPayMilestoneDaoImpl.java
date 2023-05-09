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

import erp.workorder.dao.WorkorderPayMilestoneDao;
import erp.workorder.entity.WorkorderPayMilestone;

@Repository
public class WorkorderPayMilestoneDaoImpl implements WorkorderPayMilestoneDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderPayMilestone> fetchWorkorderWorkorderPayMilestones(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderPayMilestone> cr = cb.createQuery(WorkorderPayMilestone.class);
		Root<WorkorderPayMilestone> root = cr.from(WorkorderPayMilestone.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderPayMilestone> query = session.createQuery(cr);
		List<WorkorderPayMilestone> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderPayMilestone fetchWorkorderWorkorderPayMilestoneById(Long workorderPayMilestoneId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderPayMilestone payMilestone = session.get(WorkorderPayMilestone.class, workorderPayMilestoneId);
		return payMilestone;

	}

	@Override
	public void forceUpdateWorkorderPayMilestone(WorkorderPayMilestone payMilestone) {

		Session session = entityManager.unwrap(Session.class);
		session.update(payMilestone);
		session.flush();
		session.clear();

	}

	@Override
	public Boolean updateWorkorderPayMilestone(WorkorderPayMilestone payMilestone) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderPayMilestone> cr = cb.createQuery(WorkorderPayMilestone.class);
		Root<WorkorderPayMilestone> root = cr.from(WorkorderPayMilestone.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), payMilestone.getId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), payMilestone.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("workorderId"), payMilestone.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderPayMilestone> query = session.createQuery(cr);
		List<WorkorderPayMilestone> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(payMilestone);
		return true;
	}

	@Override
	public Long saveWorkorderWorkorderPayMilestone(WorkorderPayMilestone payMilestone) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderPayMilestone> cr = cb.createQuery(WorkorderPayMilestone.class);
		Root<WorkorderPayMilestone> root = cr.from(WorkorderPayMilestone.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(cb.upper(root.get("description")), payMilestone.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("workorderId"), payMilestone.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderPayMilestone> query = session.createQuery(cr);
		List<WorkorderPayMilestone> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(payMilestone);
		return id;
	}

}
