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

import erp.billing.dao.BillBoqItemDao;
import erp.billing.entity.BillBoqItem;
import erp.billing.entity.BillBoqQuantityItem;
import erp.billing.entity.BillBoqQuantityItemTransac;

@Repository
public class BillBoqItemDaoImpl implements BillBoqItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBillBoqItem(BillBoqItem billBoq) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqItem> cr = cb.createQuery(BillBoqItem.class);
		Root<BillBoqItem> root = cr.from(BillBoqItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (billBoq.getStructureTypeId() != null) {
			andPredicates.add(cb.equal(root.get("structureTypeId"), billBoq.getStructureTypeId()));
		} else {
			andPredicates.add(cb.isNull(root.get("structureTypeId")));
		}
		andPredicates.add(cb.equal(root.get("billId"), billBoq.getBillId()));
		andPredicates.add(cb.equal(root.get("boq").get("id"), billBoq.getBoq().getId()));
		andPredicates.add(cb.equal(root.get("vendorDescription"), billBoq.getVendorDescription()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqItem> query = session.createQuery(cr);
		List<BillBoqItem> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		return (Long) session.save(billBoq);
	}

	@Override
	public Long saveBillBoqQuantityItem(BillBoqQuantityItem qtyItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqQuantityItem> cr = cb.createQuery(BillBoqQuantityItem.class);
		Root<BillBoqQuantityItem> root = cr.from(BillBoqQuantityItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("billBoqItemId"), qtyItem.getBillBoqItemId()));
		andPredicates.add(cb.equal(root.get("fromChainage").get("id"), qtyItem.getFromChainage().getId()));
		andPredicates.add(cb.equal(root.get("toChainage").get("id"), qtyItem.getToChainage().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqQuantityItem> query = session.createQuery(cr);
		List<BillBoqQuantityItem> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		return (Long) session.save(qtyItem);
	}

	@Override
	public BillBoqItem fetchBillBoqItemByBillId(Long billId, Long boqId, String vendorDescription,
			Long StructureTypeId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqItem> cr = cb.createQuery(BillBoqItem.class);
		Root<BillBoqItem> root = cr.from(BillBoqItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (StructureTypeId != null) {
			andPredicates.add(cb.equal(root.get("structureTypeId"), StructureTypeId));
		} else {
			andPredicates.add(cb.isNull(root.get("structureTypeId")));
		}
		andPredicates.add(cb.equal(root.get("billId"), billId));
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		andPredicates.add(cb.equal(root.get("vendorDescription"), vendorDescription));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqItem> query = session.createQuery(cr);
		List<BillBoqItem> results = query.getResultList();
		if (results != null && results.size() > 0)
			return results.get(0);
		return null;
	}

	@Override
	public BillBoqQuantityItem fetchBillBoqQuantityItemById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		if (id != null) {
			BillBoqQuantityItem obj = session.get(BillBoqQuantityItem.class, id);
			return obj;
		}
		return null;
	}

	@Override
	public boolean forceUpdateBillBoqItem(BillBoqItem savedBillBoq) {

		Session session = entityManager.unwrap(Session.class);
		session.update(savedBillBoq);
		return true;
	}

	@Override
	public boolean updateBillBoqQuantityItem(BillBoqQuantityItem savedQtyItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqQuantityItem> cr = cb.createQuery(BillBoqQuantityItem.class);
		Root<BillBoqQuantityItem> root = cr.from(BillBoqQuantityItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), savedQtyItem.getId()));
		andPredicates.add(cb.equal(root.get("billBoqItemId"), savedQtyItem.getBillBoqItemId()));
		andPredicates.add(cb.equal(root.get("fromChainage").get("id"), savedQtyItem.getFromChainage().getId()));
		andPredicates.add(cb.equal(root.get("toChainage").get("id"), savedQtyItem.getToChainage().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqQuantityItem> query = session.createQuery(cr);
		List<BillBoqQuantityItem> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(savedQtyItem);
		return true;
	}

	@Override
	public void saveBillBoqQuantityItemTransac(BillBoqQuantityItemTransac obj) {

		Session session = entityManager.unwrap(Session.class);
		session.save(obj);
	}

	@Override
	public List<BillBoqItem> fetchBillBoqItemsByBillId(Long billId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqItem> cr = cb.createQuery(BillBoqItem.class);
		Root<BillBoqItem> root = cr.from(BillBoqItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("billId"), billId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqItem> query = session.createQuery(cr);
		List<BillBoqItem> results = query.getResultList();
		return results;
	}

	@Override
	public List<BillBoqQuantityItem> fetchBillBoqQuantityItemsByBillId(Long billId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqQuantityItem> cr = cb.createQuery(BillBoqQuantityItem.class);
		Root<BillBoqQuantityItem> root = cr.from(BillBoqQuantityItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("billBoqItem").get("billId"), billId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqQuantityItem> query = session.createQuery(cr);
		List<BillBoqQuantityItem> results = query.getResultList();
		return results;
	}

	@Override
	public List<BillBoqQuantityItem> fetchBillBoqQuantityItemsByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqQuantityItem> cr = cb.createQuery(BillBoqQuantityItem.class);
		Root<BillBoqQuantityItem> root = cr.from(BillBoqQuantityItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqQuantityItem> query = session.createQuery(cr);
		List<BillBoqQuantityItem> results = query.getResultList();
		return results;
	}

	@Override
	public List<BillBoqQuantityItem> fetchUptoCurrentBillBoqQuantityItemsByWorkorderId(Long workorderId,
			Date billToDate) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqQuantityItem> cr = cb.createQuery(BillBoqQuantityItem.class);
		Root<BillBoqQuantityItem> root = cr.from(BillBoqQuantityItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.lessThan(root.join("billBoqItem").join("bill").get("fromDate"), billToDate));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqQuantityItem> query = session.createQuery(cr);
		List<BillBoqQuantityItem> results = query.getResultList();
		return results;
	}

	@Override
	public void forceUpdateBillBoqQuantityItem(BillBoqQuantityItem billQtyItem) {

		Session session = entityManager.unwrap(Session.class);
		session.update(billQtyItem);
	}

	@Override
	public List<BillBoqQuantityItem> fetchBillBoqQuantityItemsByBillIds(Set<Long> distinctBillIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillBoqQuantityItem> cr = cb.createQuery(BillBoqQuantityItem.class);
		Root<BillBoqQuantityItem> root = cr.from(BillBoqQuantityItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (distinctBillIds != null) {
			In<Long> inClause = cb.in(root.get("billBoqItem").get("billId"));
			for (Long id : distinctBillIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillBoqQuantityItem> query = session.createQuery(cr);
		List<BillBoqQuantityItem> results = query.getResultList();
		return results;
	}

}
