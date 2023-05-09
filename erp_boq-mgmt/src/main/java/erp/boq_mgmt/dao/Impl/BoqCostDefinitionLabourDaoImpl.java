package erp.boq_mgmt.dao.Impl;

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

import erp.boq_mgmt.dao.BoqCostDefinitionLabourDao;
import erp.boq_mgmt.entity.BoqCostDefinitionLabour;

@Repository
public class BoqCostDefinitionLabourDaoImpl implements BoqCostDefinitionLabourDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBoqCostDefinitionLabour(BoqCostDefinitionLabour boqCostDefinitionLabour) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionLabour> cr = cb.createQuery(BoqCostDefinitionLabour.class);
		Root<BoqCostDefinitionLabour> root = cr.from(BoqCostDefinitionLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionLabour.getBoqCostDefinitionId()));
		andPredicates.add(cb.equal(root.get("labourTypeId"), boqCostDefinitionLabour.getLabourTypeId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqCostDefinitionLabour.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqCostDefinitionLabour> query = session.createQuery(cr);
		List<BoqCostDefinitionLabour> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(boqCostDefinitionLabour);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public BoqCostDefinitionLabour fetchBoqCostDefinitionLabourById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		BoqCostDefinitionLabour dbObj = (BoqCostDefinitionLabour) session.get(BoqCostDefinitionLabour.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateBoqCostDefinitionLabour(BoqCostDefinitionLabour boqCostDefinitionLabour) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionLabour> cr = cb.createQuery(BoqCostDefinitionLabour.class);
		Root<BoqCostDefinitionLabour> root = cr.from(BoqCostDefinitionLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), boqCostDefinitionLabour.getId()));
		andPredicates.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionLabour.getBoqCostDefinitionId()));
		andPredicates.add(cb.equal(root.get("labourTypeId"), boqCostDefinitionLabour.getLabourTypeId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqCostDefinitionLabour.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqCostDefinitionLabour> query = session.createQuery(cr);
		List<BoqCostDefinitionLabour> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(boqCostDefinitionLabour);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateBoqCostDefinitionLabour(BoqCostDefinitionLabour dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public List<BoqCostDefinitionLabour> fetchBoqCostDefinitionLabourByBoqCostDefinitionId(Long boqCostDefinitionId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionLabour> cr = cb.createQuery(BoqCostDefinitionLabour.class);
		Root<BoqCostDefinitionLabour> root = cr.from(BoqCostDefinitionLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqCostDefinitionLabour> query = session.createQuery(cr);
		List<BoqCostDefinitionLabour> results = query.getResultList();
		return results;
	}

}
