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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WorkorderMaterialConfigDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.MaterialGroup;
import erp.workorder.entity.WorkorderMaterialConfig;

@Repository
public class WorkorderMaterialConfigDaoImpl implements WorkorderMaterialConfigDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<MaterialGroup> fetchMaterialGroups(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MaterialGroup> cr = cb.createQuery(MaterialGroup.class);
		Root<MaterialGroup> root = cr.from(MaterialGroup.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<MaterialGroup> query = session.createQuery(cr);
		List<MaterialGroup> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public Long issueMaterial(WorkorderMaterialConfig woMaterialIssue) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderMaterialConfig> cr = cb.createQuery(WorkorderMaterialConfig.class);
		Root<WorkorderMaterialConfig> root = cr.from(WorkorderMaterialConfig.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorderId"), woMaterialIssue.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("materialGroup").get("id"), woMaterialIssue.getMaterialGroup().getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderMaterialConfig> query = session.createQuery(cr);
		List<WorkorderMaterialConfig> results = query.getResultList();
		return results != null && results.size() > 0 ? null : (Long) session.save(woMaterialIssue);
	}

	@Override
	public List<WorkorderMaterialConfig> fetchWorkorderMaterialConfig(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderMaterialConfig> cr = cb.createQuery(WorkorderMaterialConfig.class);
		Root<WorkorderMaterialConfig> root = cr.from(WorkorderMaterialConfig.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), search.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderMaterialConfig> query = session.createQuery(cr);
		List<WorkorderMaterialConfig> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public Boolean updateMaterialConfig(WorkorderMaterialConfig issuedMaterial) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderMaterialConfig> cr = cb.createQuery(WorkorderMaterialConfig.class);
		Root<WorkorderMaterialConfig> root = cr.from(WorkorderMaterialConfig.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), issuedMaterial.getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorderId"), issuedMaterial.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("materialGroup").get("id"), issuedMaterial.getMaterialGroup().getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderMaterialConfig> query = session.createQuery(cr);
		List<WorkorderMaterialConfig> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(issuedMaterial);
		return true;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public WorkorderMaterialConfig fetchMaterialConfigById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderMaterialConfig materialConfig = (WorkorderMaterialConfig) session.get(WorkorderMaterialConfig.class,
				id);
		return materialConfig;
	}

}
