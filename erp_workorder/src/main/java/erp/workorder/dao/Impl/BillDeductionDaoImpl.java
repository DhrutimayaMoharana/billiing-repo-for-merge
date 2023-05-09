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

import erp.workorder.dao.BillDeductionDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.BillDeduction;

@Repository
public class BillDeductionDaoImpl implements BillDeductionDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer fetchBillDeductionIdByName(String deductionName, Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillDeduction> cr = cb.createQuery(BillDeduction.class);
		Root<BillDeduction> root = cr.from(BillDeduction.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(cb.upper(root.get("name")), deductionName.toUpperCase()));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillDeduction> query = session.createQuery(cr);
		List<BillDeduction> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0).getId();
		}
		return null;
	}

	@Override
	public Integer saveBillDeduction(BillDeduction deduction) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillDeduction> cr = cb.createQuery(BillDeduction.class);
		Root<BillDeduction> root = cr.from(BillDeduction.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(cb.upper(root.get("name")), deduction.getName().toUpperCase()));
		andPredicates.add(cb.equal(root.get("companyId"), deduction.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillDeduction> query = session.createQuery(cr);
		List<BillDeduction> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0).getId();
		}
		return (Integer) session.save(deduction);
	}

	@Override
	public List<BillDeduction> fetchBillDeductions(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillDeduction> cr = cb.createQuery(BillDeduction.class);
		Root<BillDeduction> root = cr.from(BillDeduction.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getSearchField() != null)
			andPredicates.add(cb.equal(cb.upper(root.get("name")), search.getSearchField().toUpperCase() + "%"));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillDeduction> query = session.createQuery(cr);
		List<BillDeduction> results = query.getResultList();
		return results;
	}
}
