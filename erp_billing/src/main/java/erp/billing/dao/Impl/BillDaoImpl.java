package erp.billing.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.billing.dao.BillDao;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.Bill;
import erp.billing.entity.BillStateTransitionMapping;
import erp.billing.entity.BillTransac;
import erp.billing.util.DateUtil;

@Repository
public class BillDaoImpl implements BillDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Bill> fetchBills(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
		Root<Bill> root = cr.from(Bill.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getWorkorderId() != null)
			andPredicates.add(cb.equal(root.get("workorder").get("id"), search.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("fromDate")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Bill> query = session.createQuery(cr);
		List<Bill> results = query.getResultList();
		return results;
	}

	@Override
	public List<Bill> fetchUptoCurrentWorkorderBills(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
		Root<Bill> root = cr.from(Bill.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorder").get("id"), search.getWorkorderId()));
		if (search.getToDate() != null)
			andPredicates.add(cb.lessThan(root.get("fromDate"), DateUtil.nextDateWithoutTime(search.getToDate())));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("fromDate")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Bill> query = session.createQuery(cr);
		List<Bill> results = query.getResultList();
		return results;
	}

	@Override
	public Bill fetchBillById(Long billId) {

		Session session = entityManager.unwrap(Session.class);
		Bill bill = session.get(Bill.class, billId);
		return bill;
	}

	@Override
	public Long saveBill(Bill bill) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
		Root<Bill> root = cr.from(Bill.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("type").get("id"), bill.getType().getId()));
		andPredicates.add(cb.equal(root.get("workorder").get("id"), bill.getWorkorder().getId()));
		Date[] fromDates = DateUtil.getSameDateTomorrowDateWithoutTime(bill.getFromDate());
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("toDate"), fromDates[0]));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Bill> query = session.createQuery(cr);
		List<Bill> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		return (Long) session.save(bill);
	}

	@Override
	public Integer fetchLastBillNumberByWorkorderIdAndBillTypeId(Long workorderId, Short typeId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
		Root<Bill> root = cr.from(Bill.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorder").get("id"), workorderId));
		andPredicates.add(cb.equal(root.get("type").get("id"), typeId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("billNo")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Bill> query = session.createQuery(cr);
		List<Bill> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0).getBillNo();
		}
		return 0;
	}

	@Override
	public Bill fetchLastBillByWorkorderIdAndBillTypeId(Long workorderId, Short typeId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
		Root<Bill> root = cr.from(Bill.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorder").get("id"), workorderId));
		andPredicates.add(cb.equal(root.get("type").get("id"), typeId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("billNo")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Bill> query = session.createQuery(cr);
		List<Bill> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	@Override
	public boolean updateBill(Bill bill) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
		Root<Bill> root = cr.from(Bill.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), bill.getId()));
		andPredicates.add(cb.equal(root.get("type").get("id"), bill.getType().getId()));
		andPredicates.add(cb.equal(root.get("workorder").get("id"), bill.getWorkorder().getId()));
		Date[] fromDates = DateUtil.getSameDateTomorrowDateWithoutTime(bill.getFromDate());
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("toDate"), fromDates[0]));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Bill> query = session.createQuery(cr);
		List<Bill> results = query.getResultList();
		if (results != null && results.size() > 0)
			return false;
		session.update(bill);
		return true;
	}

	@Override
	public void saveBillTransac(BillTransac billTransac) {

		Session session = entityManager.unwrap(Session.class);
		session.save(billTransac);
	}

	@Override
	public List<BillStateTransitionMapping> fetchBillStateMappings(Long id) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillStateTransitionMapping> cr = cb.createQuery(BillStateTransitionMapping.class);
		Root<BillStateTransitionMapping> root = cr.from(BillStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("billId"), id));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillStateTransitionMapping> query = session.createQuery(cr);
		List<BillStateTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public void mapBillStateTransition(BillStateTransitionMapping billStateTransitionMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillStateTransitionMapping> cr = cb.createQuery(BillStateTransitionMapping.class);
		Root<BillStateTransitionMapping> root = cr.from(BillStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("billId"), billStateTransitionMap.getBillId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn")));
		cr.select(root).where(andPredicate);
		Query<BillStateTransitionMapping> query = session.createQuery(cr);
		List<BillStateTransitionMapping> results = query.getResultList();
		if (results != null && results.size() > 0
				&& results.get(0).getStateId().equals(billStateTransitionMap.getStateId())) {
			return;
		} else {
			session.save(billStateTransitionMap);
			session.flush();
			session.clear();
		}
	}

	@Override
	public Boolean forceUpdateBill(Bill bill) {

		Session session = entityManager.unwrap(Session.class);
		session.update(bill);
		return true;
	}

	@Override
	public List<BillStateTransitionMapping> fetchBillStateMappingsByBillIds(Set<Long> distinctBillIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillStateTransitionMapping> cr = cb.createQuery(BillStateTransitionMapping.class);
		Root<BillStateTransitionMapping> root = cr.from(BillStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (distinctBillIds != null) {
			In<Long> inClause = cb.in(root.get("billId"));
			for (Long billId : distinctBillIds) {
				inClause.value(billId);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillStateTransitionMapping> query = session.createQuery(cr);
		List<BillStateTransitionMapping> results = query.getResultList();
		return results;
	}

}
