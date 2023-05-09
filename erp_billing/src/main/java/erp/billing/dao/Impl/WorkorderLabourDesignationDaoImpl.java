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

import erp.billing.dao.WorkorderLabourDesignationDao;
import erp.billing.entity.WorkorderLabourDesignation;

@Repository
public class WorkorderLabourDesignationDaoImpl implements WorkorderLabourDesignationDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer saveWorkorderLabourDesignation(WorkorderLabourDesignation workorderLabourDesignation) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourDesignation> cr = cb.createQuery(WorkorderLabourDesignation.class);
		Root<WorkorderLabourDesignation> root = cr.from(WorkorderLabourDesignation.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("name"), workorderLabourDesignation.getName().trim()));
		andPredicates.add(cb.equal(root.get("companyId"), workorderLabourDesignation.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourDesignation> query = session.createQuery(cr);
		List<WorkorderLabourDesignation> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		session.flush();
		session.clear();
		return (Integer) session.save(workorderLabourDesignation);
	}

	@Override
	public WorkorderLabourDesignation fetchworkorderLabourDesignationById(Integer id) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderLabourDesignation workorderLabourDesignation = (WorkorderLabourDesignation) session
				.get(WorkorderLabourDesignation.class, id);
		session.flush();
		session.clear();
		return workorderLabourDesignation;

	}

	@Override
	public Boolean updateWorkorderLabourDesignation(WorkorderLabourDesignation workorderLabourDesignation) {
		if (workorderLabourDesignation == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourDesignation> cr = cb.createQuery(WorkorderLabourDesignation.class);
		Root<WorkorderLabourDesignation> root = cr.from(WorkorderLabourDesignation.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), workorderLabourDesignation.getId()));
		andPredicates.add(cb.equal(root.get("name"), workorderLabourDesignation.getName()));
		andPredicates.add(cb.equal(root.get("companyId"), workorderLabourDesignation.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourDesignation> query = session.createQuery(cr);
		List<WorkorderLabourDesignation> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.merge(workorderLabourDesignation);
			session.flush();
			session.clear();
			return true;
		}
		session.flush();
		session.clear();
		return false;

	}

	@Override
	public List<WorkorderLabourDesignation> fetchWorkorderLabourDesignationDaoList(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourDesignation> cr = cb.createQuery(WorkorderLabourDesignation.class);
		Root<WorkorderLabourDesignation> root = cr.from(WorkorderLabourDesignation.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourDesignation> query = session.createQuery(cr);
		List<WorkorderLabourDesignation> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Boolean deactivateWorkorderLabourDesignation(WorkorderLabourDesignation workorderLabourDesignation) {

		Session session = entityManager.unwrap(Session.class);
		session.update(workorderLabourDesignation);
		session.flush();
		session.clear();
		return true;

	}

}
