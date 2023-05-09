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

import erp.boq_mgmt.dao.BoqMasterLmpsMaterialDao;
import erp.boq_mgmt.dto.request.MaterialFetchRequest;
import erp.boq_mgmt.entity.BoqMasterLmpsMaterial;
import erp.boq_mgmt.entity.Material;

@Repository
public class BoqMasterLmpsMaterialDaoImpl implements BoqMasterLmpsMaterialDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBoqMasterLmpsMaterial(BoqMasterLmpsMaterial boqMasterLmpsMaterial) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsMaterial> cr = cb.createQuery(BoqMasterLmpsMaterial.class);
		Root<BoqMasterLmpsMaterial> root = cr.from(BoqMasterLmpsMaterial.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsMaterial.getBoqMasterLmpsId()));
		andPredicates.add(cb.equal(root.get("materialId"), boqMasterLmpsMaterial.getMaterialId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqMasterLmpsMaterial.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqMasterLmpsMaterial> query = session.createQuery(cr);
		List<BoqMasterLmpsMaterial> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(boqMasterLmpsMaterial);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public BoqMasterLmpsMaterial fetchBoqMasterLmpsMaterialById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		BoqMasterLmpsMaterial dbObj = (BoqMasterLmpsMaterial) session.get(BoqMasterLmpsMaterial.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateBoqMasterLmpsMaterial(BoqMasterLmpsMaterial boqMasterLmpsMaterial) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsMaterial> cr = cb.createQuery(BoqMasterLmpsMaterial.class);
		Root<BoqMasterLmpsMaterial> root = cr.from(BoqMasterLmpsMaterial.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), boqMasterLmpsMaterial.getId()));
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsMaterial.getBoqMasterLmpsId()));
		andPredicates.add(cb.equal(root.get("materialId"), boqMasterLmpsMaterial.getMaterialId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqMasterLmpsMaterial.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqMasterLmpsMaterial> query = session.createQuery(cr);
		List<BoqMasterLmpsMaterial> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(boqMasterLmpsMaterial);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateBoqMasterLmpsMaterial(BoqMasterLmpsMaterial dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public List<BoqMasterLmpsMaterial> fetchBoqMasterLmpsMaterialByBoqMasterLmpsId(Long boqMasterLmpsId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsMaterial> cr = cb.createQuery(BoqMasterLmpsMaterial.class);
		Root<BoqMasterLmpsMaterial> root = cr.from(BoqMasterLmpsMaterial.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqMasterLmpsMaterial> query = session.createQuery(cr);
		List<BoqMasterLmpsMaterial> results = query.getResultList();
		return results;
	}

	@Override
	public List<Material> fetchMaterialList(MaterialFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Material> cr = cb.createQuery(Material.class);
		Root<Material> root = cr.from(Material.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			andPredicates.add(cb.like(root.get("name"), requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Material> query = session.createQuery(cr);
		List<Material> results = query.getResultList();
		return results;
	}

}
