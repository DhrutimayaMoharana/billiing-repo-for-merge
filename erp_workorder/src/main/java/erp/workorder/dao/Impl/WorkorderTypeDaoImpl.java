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

import erp.workorder.dao.WorkorderTypeDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WorkorderType;

@Repository
public class WorkorderTypeDaoImpl implements WorkorderTypeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderType> fetchWorkorderTypes(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderType> cr = cb.createQuery(WorkorderType.class);
		Root<WorkorderType> root = cr.from(WorkorderType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search != null && search.getSearchField() != null)
			andPredicates.add(cb.equal(cb.upper(root.get("name")), search.getSearchField().toUpperCase()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderType> query = session.createQuery(cr);
		List<WorkorderType> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderType fetchWorkorderTypeById(Integer typeId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderType woType = (WorkorderType) session.get(WorkorderType.class, typeId);
		return woType;
	}

	@Override
	public Long saveWorkorderType(WorkorderType type) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderType> cr = cb.createQuery(WorkorderType.class);
		Root<WorkorderType> root = cr.from(WorkorderType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (type != null && type.getName() != null)
			andPredicates.add(cb.equal(cb.upper(root.get("name")), type.getName().toUpperCase()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderType> query = session.createQuery(cr);
		List<WorkorderType> results = query.getResultList();
		Long id = null;
		if (results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(type);
		}
		return id;
	}

	@Override
	public Boolean updateWorkorderType(WorkorderType type) {

		if (type == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderType> cr = cb.createQuery(WorkorderType.class);
		Root<WorkorderType> root = cr.from(WorkorderType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (type != null && type.getName() != null)
			andPredicates.add(cb.equal(cb.upper(root.get("name")), type.getName().toUpperCase()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderType> query = session.createQuery(cr);
		List<WorkorderType> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(type);
			return true;
		}
		return false;
	}

}
