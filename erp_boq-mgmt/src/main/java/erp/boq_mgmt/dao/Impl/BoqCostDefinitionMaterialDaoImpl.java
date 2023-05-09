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

import erp.boq_mgmt.dao.BoqCostDefinitionMaterialDao;
import erp.boq_mgmt.dto.request.MaterialFetchRequest;
import erp.boq_mgmt.entity.BoqCostDefinitionMaterial;
import erp.boq_mgmt.entity.Material;

@Repository
public class BoqCostDefinitionMaterialDaoImpl implements BoqCostDefinitionMaterialDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBoqCostDefinitionMaterial(BoqCostDefinitionMaterial boqCostDefinitionMaterial) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionMaterial> cr = cb.createQuery(BoqCostDefinitionMaterial.class);
		Root<BoqCostDefinitionMaterial> root = cr.from(BoqCostDefinitionMaterial.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates
				.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionMaterial.getBoqCostDefinitionId()));
		andPredicates.add(cb.equal(root.get("materialId"), boqCostDefinitionMaterial.getMaterialId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqCostDefinitionMaterial.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqCostDefinitionMaterial> query = session.createQuery(cr);
		List<BoqCostDefinitionMaterial> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(boqCostDefinitionMaterial);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public BoqCostDefinitionMaterial fetchBoqCostDefinitionMaterialById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		BoqCostDefinitionMaterial dbObj = (BoqCostDefinitionMaterial) session.get(BoqCostDefinitionMaterial.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateBoqCostDefinitionMaterial(BoqCostDefinitionMaterial boqCostDefinitionMaterial) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionMaterial> cr = cb.createQuery(BoqCostDefinitionMaterial.class);
		Root<BoqCostDefinitionMaterial> root = cr.from(BoqCostDefinitionMaterial.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), boqCostDefinitionMaterial.getId()));
		andPredicates
				.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionMaterial.getBoqCostDefinitionId()));
		andPredicates.add(cb.equal(root.get("materialId"), boqCostDefinitionMaterial.getMaterialId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqCostDefinitionMaterial.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqCostDefinitionMaterial> query = session.createQuery(cr);
		List<BoqCostDefinitionMaterial> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(boqCostDefinitionMaterial);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateBoqCostDefinitionMaterial(BoqCostDefinitionMaterial dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public List<BoqCostDefinitionMaterial> fetchBoqCostDefinitionMaterialByBoqCostDefinitionId(
			Long boqCostDefinitionId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionMaterial> cr = cb.createQuery(BoqCostDefinitionMaterial.class);
		Root<BoqCostDefinitionMaterial> root = cr.from(BoqCostDefinitionMaterial.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqCostDefinitionMaterial> query = session.createQuery(cr);
		List<BoqCostDefinitionMaterial> results = query.getResultList();
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
