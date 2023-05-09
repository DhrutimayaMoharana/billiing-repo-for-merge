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

import erp.billing.dao.WorkorderLabourTypeDao;
import erp.billing.entity.WorkorderLabourType;

@Repository
public class WorkorderLabourTypeDaoImpl implements WorkorderLabourTypeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer saveWorkorderLabourType(WorkorderLabourType workorderLabourType) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourType> cr = cb.createQuery(WorkorderLabourType.class);
		Root<WorkorderLabourType> root = cr.from(WorkorderLabourType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("name"), workorderLabourType.getName().trim()));
		andPredicates.add(cb.equal(root.get("companyId"), workorderLabourType.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourType> query = session.createQuery(cr);
		List<WorkorderLabourType> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		session.flush();
		session.clear();
		return (Integer) session.save(workorderLabourType);
	}

	@Override
	public WorkorderLabourType fetchworkorderLabourTypeById(Integer id) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderLabourType workorderLabourType = (WorkorderLabourType) session.get(WorkorderLabourType.class, id);
		session.flush();
		session.clear();
		return workorderLabourType;

	}

	@Override
	public Boolean updateWorkorderLabourType(WorkorderLabourType workorderLabourType) {
		if (workorderLabourType == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourType> cr = cb.createQuery(WorkorderLabourType.class);
		Root<WorkorderLabourType> root = cr.from(WorkorderLabourType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), workorderLabourType.getId()));
		andPredicates.add(cb.equal(root.get("name"), workorderLabourType.getName()));
		andPredicates.add(cb.equal(root.get("companyId"), workorderLabourType.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourType> query = session.createQuery(cr);
		List<WorkorderLabourType> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.merge(workorderLabourType);
			session.flush();
			session.clear();
			return true;
		}
		session.flush();
		session.clear();
		return false;

	}

	@Override
	public List<WorkorderLabourType> fetchWorkorderLabourTypeDaoList(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderLabourType> cr = cb.createQuery(WorkorderLabourType.class);
		Root<WorkorderLabourType> root = cr.from(WorkorderLabourType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderLabourType> query = session.createQuery(cr);
		List<WorkorderLabourType> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Boolean deactivateWorkorderLabourType(WorkorderLabourType workorderLabourType) {

		Session session = entityManager.unwrap(Session.class);
		session.update(workorderLabourType);
		session.flush();
		session.clear();
		return true;

	}

}
