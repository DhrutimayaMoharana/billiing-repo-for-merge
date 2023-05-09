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

import erp.boq_mgmt.dao.BoqMasterLmpsLabourDao;
import erp.boq_mgmt.entity.BoqMasterLmpsLabour;

@Repository
public class BoqMasterLmpsLabourDaoImpl implements BoqMasterLmpsLabourDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBoqMasterLmpsLabour(BoqMasterLmpsLabour boqMasterLmpsLabour) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsLabour> cr = cb.createQuery(BoqMasterLmpsLabour.class);
		Root<BoqMasterLmpsLabour> root = cr.from(BoqMasterLmpsLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsLabour.getBoqMasterLmpsId()));
		andPredicates.add(cb.equal(root.get("labourTypeId"), boqMasterLmpsLabour.getLabourTypeId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqMasterLmpsLabour.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqMasterLmpsLabour> query = session.createQuery(cr);
		List<BoqMasterLmpsLabour> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(boqMasterLmpsLabour);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public BoqMasterLmpsLabour fetchBoqMasterLmpsLabourById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		BoqMasterLmpsLabour dbObj = (BoqMasterLmpsLabour) session.get(BoqMasterLmpsLabour.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateBoqMasterLmpsLabour(BoqMasterLmpsLabour boqMasterLmpsLabour) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsLabour> cr = cb.createQuery(BoqMasterLmpsLabour.class);
		Root<BoqMasterLmpsLabour> root = cr.from(BoqMasterLmpsLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), boqMasterLmpsLabour.getId()));
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsLabour.getBoqMasterLmpsId()));
		andPredicates.add(cb.equal(root.get("labourTypeId"), boqMasterLmpsLabour.getLabourTypeId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqMasterLmpsLabour.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqMasterLmpsLabour> query = session.createQuery(cr);
		List<BoqMasterLmpsLabour> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(boqMasterLmpsLabour);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateBoqMasterLmpsLabour(BoqMasterLmpsLabour dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public List<BoqMasterLmpsLabour> fetchBoqMasterLmpsLabourByBoqMasterLmpsId(Long boqMasterLmpsId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsLabour> cr = cb.createQuery(BoqMasterLmpsLabour.class);
		Root<BoqMasterLmpsLabour> root = cr.from(BoqMasterLmpsLabour.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqMasterLmpsLabour> query = session.createQuery(cr);
		List<BoqMasterLmpsLabour> results = query.getResultList();
		return results;
	}

}
