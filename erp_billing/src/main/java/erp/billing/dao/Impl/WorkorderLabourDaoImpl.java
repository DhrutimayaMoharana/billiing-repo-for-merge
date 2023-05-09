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

import erp.billing.dao.WorkorderLabourDao;
import erp.billing.dto.request.WorkorderLabourFetchRequest;
import erp.billing.entity.WorkorderLabour;

@Repository
public class WorkorderLabourDaoImpl implements WorkorderLabourDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer saveWorkorderLabour(WorkorderLabour workorderLabour) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabour> cr = cb.createQuery(WorkorderLabour.class);
		Root<WorkorderLabour> root = cr.from(WorkorderLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("code"), workorderLabour.getCode().trim()));
		andPredicates.add(cb.equal(root.get("siteId"), workorderLabour.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabour> query = session.createQuery(cr);
		List<WorkorderLabour> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		session.flush();
		session.clear();
		return (Integer) session.save(workorderLabour);
	}

	@Override
	public WorkorderLabour fetchworkorderLabourById(Integer id) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderLabour workorderLabourDepartment = (WorkorderLabour) session.get(WorkorderLabour.class, id);
		session.flush();
		session.clear();
		return workorderLabourDepartment;

	}

	@Override
	public Boolean updateWorkorderLabourDepartment(WorkorderLabour workorderLabour) {
		if (workorderLabour == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabour> cr = cb.createQuery(WorkorderLabour.class);
		Root<WorkorderLabour> root = cr.from(WorkorderLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), workorderLabour.getId()));
		andPredicates.add(cb.equal(root.get("code"), workorderLabour.getCode()));
		andPredicates.add(cb.equal(root.get("siteId"), workorderLabour.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabour> query = session.createQuery(cr);
		List<WorkorderLabour> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.merge(workorderLabour);
			session.flush();
			session.clear();
			return true;
		}
		session.flush();
		session.clear();
		return false;

	}

	@Override
	public List<WorkorderLabour> fetchWorkorderLabourDaoList(WorkorderLabourFetchRequest requestObj) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabour> cr = cb.createQuery(WorkorderLabour.class);
		Root<WorkorderLabour> root = cr.from(WorkorderLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if (requestObj.getDepartmentId() != null)
			andPredicates.add(cb.equal(root.get("department").get("id"), requestObj.getDepartmentId()));
		if (requestObj.getTypeId() != null)
			andPredicates.add(cb.equal(root.get("type").get("id"), requestObj.getTypeId()));
		if (requestObj.getSearch() != null && !requestObj.getSearch().isEmpty()) {
			orPredicates.add(cb.like(cb.upper(root.get("name")), requestObj.getSearch().toUpperCase() + "%"));
			orPredicates.add(cb.like(cb.upper(root.get("code")), requestObj.getSearch().toUpperCase() + "%"));
		}

		andPredicates.add(cb.equal(root.get("siteId"), requestObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<WorkorderLabour> query = session.createQuery(cr);
		List<WorkorderLabour> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Boolean deactivateWorkorderLabourDepartment(WorkorderLabour workorderLabour) {

		Session session = entityManager.unwrap(Session.class);
		session.update(workorderLabour);
		session.flush();
		session.clear();
		return true;

	}

}
