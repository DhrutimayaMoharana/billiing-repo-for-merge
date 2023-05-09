package erp.workorder.dao.Impl;

import java.util.ArrayList;
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

import erp.workorder.dao.BillDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Bill;
import erp.workorder.entity.BillBoqQuantityItem;
import erp.workorder.entity.BillMachineMapping;
import erp.workorder.entity.BillStateTransitionMapping;

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
			andPredicates.add(cb.equal(root.get("workorderId"), search.getWorkorderId()));
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
	public List<BillBoqQuantityItem> fetchWorkorderBillBoqQtyItems(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqQuantityItem> cr = cb.createQuery(BillBoqQuantityItem.class);
		Root<BillBoqQuantityItem> root = cr.from(BillBoqQuantityItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqQuantityItem> query = session.createQuery(cr);
		List<BillBoqQuantityItem> results = query.getResultList();
		return results;
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
			for (Long id : distinctBillIds) {
				inClause.value(id);
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

	@Override
	public List<BillMachineMapping> fetchBillMachineMapping(Set<Long> distinctBillIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillMachineMapping> cr = cb.createQuery(BillMachineMapping.class);
		Root<BillMachineMapping> root = cr.from(BillMachineMapping.class);
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
		Query<BillMachineMapping> query = session.createQuery(cr);
		List<BillMachineMapping> results = query.getResultList();
		return results;
	}
}
