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

import erp.boq_mgmt.dao.BoqMasterLmpsMachineryDao;
import erp.boq_mgmt.entity.BoqMasterLmpsMachinery;

@Repository
public class BoqMasterLmpsMachineryDaoImpl implements BoqMasterLmpsMachineryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBoqMasterLmpsMachinery(BoqMasterLmpsMachinery boqMasterLmpsMachinery) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsMachinery> cr = cb.createQuery(BoqMasterLmpsMachinery.class);
		Root<BoqMasterLmpsMachinery> root = cr.from(BoqMasterLmpsMachinery.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsMachinery.getBoqMasterLmpsId()));
		andPredicates.add(cb.equal(root.get("machineryCategoryId"), boqMasterLmpsMachinery.getMachineryCategoryId()));
		if (boqMasterLmpsMachinery.getIsMachineryLoaded() != null) {
			orPredicates.add(cb.equal(root.get("isMachineryLoaded"), boqMasterLmpsMachinery.getIsMachineryLoaded()));
		}
		orPredicates.add(cb.isNull(root.get("isMachineryLoaded")));
		andPredicates.add(cb.equal(root.get("isActive"), boqMasterLmpsMachinery.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<BoqMasterLmpsMachinery> query = session.createQuery(cr);
		List<BoqMasterLmpsMachinery> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(boqMasterLmpsMachinery);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public BoqMasterLmpsMachinery fetchBoqMasterLmpsMachineryById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		BoqMasterLmpsMachinery dbObj = (BoqMasterLmpsMachinery) session.get(BoqMasterLmpsMachinery.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateBoqMasterLmpsMachinery(BoqMasterLmpsMachinery boqMasterLmpsMachinery) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsMachinery> cr = cb.createQuery(BoqMasterLmpsMachinery.class);
		Root<BoqMasterLmpsMachinery> root = cr.from(BoqMasterLmpsMachinery.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), boqMasterLmpsMachinery.getId()));
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsMachinery.getBoqMasterLmpsId()));
		andPredicates.add(cb.equal(root.get("machineryCategoryId"), boqMasterLmpsMachinery.getMachineryCategoryId()));
		if (boqMasterLmpsMachinery.getIsMachineryLoaded() != null) {
			orPredicates.add(cb.equal(root.get("isMachineryLoaded"), boqMasterLmpsMachinery.getIsMachineryLoaded()));
		}
		orPredicates.add(cb.isNull(root.get("isMachineryLoaded")));
		andPredicates.add(cb.equal(root.get("isActive"), boqMasterLmpsMachinery.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<BoqMasterLmpsMachinery> query = session.createQuery(cr);
		List<BoqMasterLmpsMachinery> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(boqMasterLmpsMachinery);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateBoqMasterLmpsMachinery(BoqMasterLmpsMachinery dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public List<BoqMasterLmpsMachinery> fetchBoqMasterLmpsMachineryByBoqMasterLmpsId(Long boqMasterLmpsId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsMachinery> cr = cb.createQuery(BoqMasterLmpsMachinery.class);
		Root<BoqMasterLmpsMachinery> root = cr.from(BoqMasterLmpsMachinery.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqMasterLmpsMachinery> query = session.createQuery(cr);
		List<BoqMasterLmpsMachinery> results = query.getResultList();
		return results;
	}

}
