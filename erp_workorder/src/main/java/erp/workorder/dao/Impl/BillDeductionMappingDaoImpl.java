package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
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

import erp.workorder.dao.BillDeductionMappingDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.BillDeductionMapTransac;
import erp.workorder.entity.BillDeductionMapping;

@Repository
public class BillDeductionMappingDaoImpl implements BillDeductionMappingDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<BillDeductionMapping> fetchMappedBillDeductions(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillDeductionMapping> cr = cb.createQuery(BillDeductionMapping.class);
		Root<BillDeductionMapping> root = cr.from(BillDeductionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderBillInfoId"), search.getWorkorderBillInfoId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillDeductionMapping> query = session.createQuery(cr);
		List<BillDeductionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<BillDeductionMapping> fetchMappedBillDeductionsByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillDeductionMapping> cr = cb.createQuery(BillDeductionMapping.class);
		Root<BillDeductionMapping> root = cr.from(BillDeductionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.join("bill").get("workorder").get("id"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillDeductionMapping> query = session.createQuery(cr);
		List<BillDeductionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<BillDeductionMapping> fetchUptoCurrentMappedBillDeductionsByWorkorderId(Long workorderId, Date toDate) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillDeductionMapping> cr = cb.createQuery(BillDeductionMapping.class);
		Root<BillDeductionMapping> root = cr.from(BillDeductionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.join("bill").join("workorder").get("id"), workorderId));
		andPredicates.add(cb.lessThan(root.join("bill").get("fromDate"), toDate));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillDeductionMapping> query = session.createQuery(cr);
		List<BillDeductionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public BillDeductionMapping fetchBillDeductionMapById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		BillDeductionMapping obj = session.get(BillDeductionMapping.class, id);
		return obj;
	}

	@Override
	public Long saveBillDeductionMapping(BillDeductionMapping obj) {

		Session session = entityManager.unwrap(Session.class);
//		CriteriaBuilder cb = session.getCriteriaBuilder();
//		CriteriaQuery<BillDeductionMapping> cr = cb.createQuery(BillDeductionMapping.class);
//		Root<BillDeductionMapping> root = cr.from(BillDeductionMapping.class);
//		List<Predicate> andPredicates = new ArrayList<Predicate>();
//		andPredicates.add(cb.equal(root.get("workorderBillInfoId"), obj.getWorkorderBillInfoId()));
//		andPredicates.add(cb.equal(root.get("billDeductionId").get("id"), obj.getBillDeductionId()));
//		andPredicates.add(cb.equal(root.get("isActive"), true));
//		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
//		cr.distinct(true).select(root).where(andPredicate);
//		Query<BillDeductionMapping> query = session.createQuery(cr);
//		List<BillDeductionMapping> results = query.getResultList();
//		if (results != null && results.size() > 0) {
//			return null;
//		}
		return (Long) session.save(obj);
	}

	@Override
	public boolean updateBillDeductionMapping(BillDeductionMapping billDeductionMappingObj) {

//		Session session = entityManager.unwrap(Session.class);
//		CriteriaBuilder cb = session.getCriteriaBuilder();
//		CriteriaQuery<BillDeductionMapping> cr = cb.createQuery(BillDeductionMapping.class);
//		Root<BillDeductionMapping> root = cr.from(BillDeductionMapping.class);
//		List<Predicate> andPredicates = new ArrayList<Predicate>();
//		andPredicates.add(cb.notEqual(root.get("id"), obj.getId()));
//		andPredicates.add(cb.equal(root.get("workorderBillInfoId"), obj.getWorkorderBillInfoId()));
//		andPredicates.add(cb.equal(root.get("billDeductionId").get("id"), obj.getBillDeductionId()));
//		andPredicates.add(cb.equal(root.get("isActive"), true));
//		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
//		cr.distinct(true).select(root).where(andPredicate);
//		Query<BillDeductionMapping> query = session.createQuery(cr);
//		List<BillDeductionMapping> results = query.getResultList();
//		if (results != null && results.size() > 0) {
//			return false;
//		}
//		session.update(obj);
		Session session = entityManager.unwrap(Session.class);
		session.merge(billDeductionMappingObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public void saveBillDeductionMapTransac(BillDeductionMapTransac mapTransac) {
		Session session = entityManager.unwrap(Session.class);
		session.save(mapTransac);
	}

}
