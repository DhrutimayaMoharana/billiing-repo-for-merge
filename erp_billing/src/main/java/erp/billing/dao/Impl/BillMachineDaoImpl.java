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

import erp.billing.dao.BillMachineDao;
import erp.billing.entity.BillMachineMapping;

@Repository
public class BillMachineDaoImpl implements BillMachineDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBillMachineMapping(BillMachineMapping bcmToSave) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillMachineMapping> cr = cb.createQuery(BillMachineMapping.class);
		Root<BillMachineMapping> root = cr.from(BillMachineMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("machineType"), bcmToSave.getMachineType()));
		andPredicates.add(cb.equal(root.get("machineId"), bcmToSave.getMachineId()));
		andPredicates.add(cb.equal(root.get("billId"), bcmToSave.getBillId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillMachineMapping> query = session.createQuery(cr);
		List<BillMachineMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			if (!results.get(0).getIsActive()) {
				bcmToSave.setId(results.get(0).getId());
				session.merge(bcmToSave);
				return bcmToSave.getId();
			}
		}

		return (Long) session.save(bcmToSave);
	}

	@Override
	public List<BillMachineMapping> fetchBillMachineMapping(Long billId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillMachineMapping> cr = cb.createQuery(BillMachineMapping.class);
		Root<BillMachineMapping> root = cr.from(BillMachineMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("billId"), billId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillMachineMapping> query = session.createQuery(cr);
		List<BillMachineMapping> results = query.getResultList();
		return results;
	}

	@Override
	public void forceUpdateBillMachineMapping(BillMachineMapping bcm) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(bcm);

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

	@Override
	public List<BillMachineMapping> fetchBillMachineMappingByBillIds(Set<Long> billIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillMachineMapping> cr = cb.createQuery(BillMachineMapping.class);
		Root<BillMachineMapping> root = cr.from(BillMachineMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (billIds != null) {
			In<Long> inClause = cb.in(root.get("billId"));
			for (Long billId : billIds) {
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

	@Override
	public Boolean fetchBillMachineList(Long machineId, Date fromDate, Date toDate, Long billId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillMachineMapping> cr = cb.createQuery(BillMachineMapping.class);
		Root<BillMachineMapping> root = cr.from(BillMachineMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("machineId"), machineId));
		if (billId != null)
			andPredicates.add(cb.not(root.get("billId").in(billId)));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.or(cb.between(root.get("fromDate"), fromDate, toDate),
				cb.between(root.get("toDate"), fromDate, toDate)));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<BillMachineMapping> query = session.createQuery(cr);
		List<BillMachineMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return true;
		}
		return false;
	}

}
