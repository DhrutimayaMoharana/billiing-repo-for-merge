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

import erp.boq_mgmt.dao.BoqCostDefinitionMachineryDao;
import erp.boq_mgmt.entity.BoqCostDefinitionMachinery;

@Repository
public class BoqCostDefinitionMachineryDaoImpl implements BoqCostDefinitionMachineryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBoqCostDefinitionMachinery(BoqCostDefinitionMachinery boqCostDefinitionMachinery) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionMachinery> cr = cb.createQuery(BoqCostDefinitionMachinery.class);
		Root<BoqCostDefinitionMachinery> root = cr.from(BoqCostDefinitionMachinery.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates
				.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionMachinery.getBoqCostDefinitionId()));
		andPredicates
				.add(cb.equal(root.get("machineryCategoryId"), boqCostDefinitionMachinery.getMachineryCategoryId()));
		if (boqCostDefinitionMachinery.getIsMachineryLoaded() != null) {
			orPredicates
					.add(cb.equal(root.get("isMachineryLoaded"), boqCostDefinitionMachinery.getIsMachineryLoaded()));
		}
		orPredicates.add(cb.isNull(root.get("isMachineryLoaded")));
		andPredicates.add(cb.equal(root.get("isActive"), boqCostDefinitionMachinery.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<BoqCostDefinitionMachinery> query = session.createQuery(cr);
		List<BoqCostDefinitionMachinery> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(boqCostDefinitionMachinery);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public BoqCostDefinitionMachinery fetchBoqCostDefinitionMachineryById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		BoqCostDefinitionMachinery dbObj = (BoqCostDefinitionMachinery) session.get(BoqCostDefinitionMachinery.class,
				id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateBoqCostDefinitionMachinery(BoqCostDefinitionMachinery boqCostDefinitionMachinery) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionMachinery> cr = cb.createQuery(BoqCostDefinitionMachinery.class);
		Root<BoqCostDefinitionMachinery> root = cr.from(BoqCostDefinitionMachinery.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), boqCostDefinitionMachinery.getId()));
		andPredicates
				.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionMachinery.getBoqCostDefinitionId()));
		andPredicates
				.add(cb.equal(root.get("machineryCategoryId"), boqCostDefinitionMachinery.getMachineryCategoryId()));
		if (boqCostDefinitionMachinery.getIsMachineryLoaded() != null) {
			orPredicates
					.add(cb.equal(root.get("isMachineryLoaded"), boqCostDefinitionMachinery.getIsMachineryLoaded()));
		}
		orPredicates.add(cb.isNull(root.get("isMachineryLoaded")));
		andPredicates.add(cb.equal(root.get("isActive"), boqCostDefinitionMachinery.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<BoqCostDefinitionMachinery> query = session.createQuery(cr);
		List<BoqCostDefinitionMachinery> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(boqCostDefinitionMachinery);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateBoqCostDefinitionMachinery(BoqCostDefinitionMachinery dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public List<BoqCostDefinitionMachinery> fetchBoqCostDefinitionMachineryByBoqCostDefinitionId(
			Long boqCostDefinitionId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionMachinery> cr = cb.createQuery(BoqCostDefinitionMachinery.class);
		Root<BoqCostDefinitionMachinery> root = cr.from(BoqCostDefinitionMachinery.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqCostDefinitionMachinery> query = session.createQuery(cr);
		List<BoqCostDefinitionMachinery> results = query.getResultList();
		return results;
	}

}
