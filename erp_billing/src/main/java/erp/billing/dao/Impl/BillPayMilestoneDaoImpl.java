package erp.billing.dao.Impl;

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

import erp.billing.dao.BillPayMilestoneDao;
import erp.billing.entity.BillPayMilestones;

@Repository
public class BillPayMilestoneDaoImpl implements BillPayMilestoneDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBillPayMilestone(BillPayMilestones payMilestone) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillPayMilestones> cr = cb.createQuery(BillPayMilestones.class);
		Root<BillPayMilestones> root = cr.from(BillPayMilestones.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorderMilestoneId"), payMilestone.getWorkorderMilestoneId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillPayMilestones> query = session.createQuery(cr);
		List<BillPayMilestones> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(payMilestone);
		return id;
	}

	@Override
	public List<BillPayMilestones> fetchBillPayMilestones(Long billId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillPayMilestones> cr = cb.createQuery(BillPayMilestones.class);
		Root<BillPayMilestones> root = cr.from(BillPayMilestones.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("billId"), billId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillPayMilestones> query = session.createQuery(cr);
		List<BillPayMilestones> results = query.getResultList();
		return results;
	}

	@Override
	public void forceUpdateBillPayMilestone(BillPayMilestones billPM) {

		Session session = entityManager.unwrap(Session.class);
		session.update(billPM);

	}

	@Override
	public List<BillPayMilestones> fetchBillPayMilestonesByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillPayMilestones> cr = cb.createQuery(BillPayMilestones.class);
		Root<BillPayMilestones> root = cr.from(BillPayMilestones.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.join("bill").join("workorder").get("id"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillPayMilestones> query = session.createQuery(cr);
		List<BillPayMilestones> results = query.getResultList();
		return results;
	}

}
