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

import erp.billing.dao.WorkorderLabourDepartmentDao;
import erp.billing.entity.WorkorderLabourDepartment;

@Repository
public class WorkorderLabourDepartmentDaoImpl implements WorkorderLabourDepartmentDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer saveWorkorderLabourDepartment(WorkorderLabourDepartment workorderLabourDepartment) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourDepartment> cr = cb.createQuery(WorkorderLabourDepartment.class);
		Root<WorkorderLabourDepartment> root = cr.from(WorkorderLabourDepartment.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("name"), workorderLabourDepartment.getName().trim()));
		andPredicates.add(cb.equal(root.get("companyId"), workorderLabourDepartment.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourDepartment> query = session.createQuery(cr);
		List<WorkorderLabourDepartment> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		session.flush();
		session.clear();
		return (Integer) session.save(workorderLabourDepartment);
	}

	@Override
	public WorkorderLabourDepartment fetchworkorderLabourDepartmentById(Integer id) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderLabourDepartment workorderLabourDepartment = (WorkorderLabourDepartment) session
				.get(WorkorderLabourDepartment.class, id);
		session.flush();
		session.clear();
		return workorderLabourDepartment;

	}

	@Override
	public Boolean updateWorkorderLabourDepartment(WorkorderLabourDepartment workorderLabourDepartment) {
		if (workorderLabourDepartment == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourDepartment> cr = cb.createQuery(WorkorderLabourDepartment.class);
		Root<WorkorderLabourDepartment> root = cr.from(WorkorderLabourDepartment.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), workorderLabourDepartment.getId()));
		andPredicates.add(cb.equal(root.get("name"), workorderLabourDepartment.getName()));
		andPredicates.add(cb.equal(root.get("companyId"), workorderLabourDepartment.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourDepartment> query = session.createQuery(cr);
		List<WorkorderLabourDepartment> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.merge(workorderLabourDepartment);
			session.flush();
			session.clear();
			return true;
		}
		session.flush();
		session.clear();
		return false;

	}

	@Override
	public List<WorkorderLabourDepartment> fetchWorkorderLabourDepartmentDaoList(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourDepartment> cr = cb.createQuery(WorkorderLabourDepartment.class);
		Root<WorkorderLabourDepartment> root = cr.from(WorkorderLabourDepartment.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourDepartment> query = session.createQuery(cr);
		List<WorkorderLabourDepartment> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Boolean deactivateWorkorderLabourDepartment(WorkorderLabourDepartment workorderLabourDepartment) {

		Session session = entityManager.unwrap(Session.class);
		session.update(workorderLabourDepartment);
		session.flush();
		session.clear();
		return true;

	}

}
