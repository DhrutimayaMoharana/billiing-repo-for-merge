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

import erp.boq_mgmt.dao.ProjectPlanBoqDao;
import erp.boq_mgmt.entity.ProjectPlanBoq;
import erp.boq_mgmt.entity.ProjectPlanBoqDistribution;
import erp.boq_mgmt.enums.WorkType;

@Repository
public class ProjectPlanBoqDaoImpl implements ProjectPlanBoqDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveProjectPlanBoq(ProjectPlanBoq projectPlanBoq) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ProjectPlanBoq> cr = cb.createQuery(ProjectPlanBoq.class);
		Root<ProjectPlanBoq> root = cr.from(ProjectPlanBoq.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqId"), projectPlanBoq.getBoqId()));
		if (projectPlanBoq.getStructureId() != null) {
			andPredicates.add(cb.equal(root.get("structureId"), projectPlanBoq.getStructureId()));
		} else {
			andPredicates.add(cb.isNull(root.get("structureId")));
		}
		if (projectPlanBoq.getStructureTypeId() != null) {
			andPredicates.add(cb.equal(root.get("structureTypeId"), projectPlanBoq.getStructureTypeId()));
		} else {
			andPredicates.add(cb.isNull(root.get("structureTypeId")));
		}
		andPredicates.add(cb.equal(root.get("siteId"), projectPlanBoq.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), projectPlanBoq.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<ProjectPlanBoq> query = session.createQuery(cr);
		List<ProjectPlanBoq> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(projectPlanBoq);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public ProjectPlanBoq fetchProjectPlanBoqByWorkTypeAndBoqId(WorkType workType, Long boqId, Long structureTypeId,
			Long structureId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ProjectPlanBoq> cr = cb.createQuery(ProjectPlanBoq.class);
		Root<ProjectPlanBoq> root = cr.from(ProjectPlanBoq.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workType"), workType));
		andPredicates.add(cb.equal(root.get("boqId"), boqId));
		if (structureId != null) {
			andPredicates.add(cb.equal(root.get("structureId"), structureId));
		}
		else {
			andPredicates.add(cb.isNull(root.get("structureId")));
		}
		if (structureTypeId != null) {
			andPredicates.add(cb.equal(root.get("structureTypeId"), structureTypeId));
		}
		else {
			andPredicates.add(cb.isNull(root.get("structureTypeId")));
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ProjectPlanBoq> query = session.createQuery(cr);
		List<ProjectPlanBoq> results = query.getResultList();
		return results != null && !results.isEmpty() ? results.get(0) : null;
	}

	@Override
	public List<ProjectPlanBoqDistribution> fetchProjectPlanBoqDistributionByProjectPlanBoqId(Long id) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ProjectPlanBoqDistribution> cr = cb.createQuery(ProjectPlanBoqDistribution.class);
		Root<ProjectPlanBoqDistribution> root = cr.from(ProjectPlanBoqDistribution.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("projectPlanBoqId"), id));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ProjectPlanBoqDistribution> query = session.createQuery(cr);
		List<ProjectPlanBoqDistribution> results = query.getResultList();
		return results;
	}

	@Override
	public Boolean updateProjectPlantBoqDistribution(ProjectPlanBoqDistribution projectPlanBoqDistribution) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(projectPlanBoqDistribution);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Long saveProjectPlantBoqDistribution(ProjectPlanBoqDistribution projectPlanBoqDistribution) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ProjectPlanBoqDistribution> cr = cb.createQuery(ProjectPlanBoqDistribution.class);
		Root<ProjectPlanBoqDistribution> root = cr.from(ProjectPlanBoqDistribution.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("projectPlanBoqId"), projectPlanBoqDistribution.getProjectPlanBoqId()));
		andPredicates.add(cb.equal(root.get("month"), projectPlanBoqDistribution.getMonth()));
		andPredicates.add(cb.equal(root.get("year"), projectPlanBoqDistribution.getYear()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<ProjectPlanBoqDistribution> query = session.createQuery(cr);
		List<ProjectPlanBoqDistribution> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(projectPlanBoqDistribution);
		session.flush();
		session.clear();
		return id;
	}

}
