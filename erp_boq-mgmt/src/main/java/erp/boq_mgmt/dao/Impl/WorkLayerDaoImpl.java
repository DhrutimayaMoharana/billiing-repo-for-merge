package erp.boq_mgmt.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.boq_mgmt.dao.WorkLayerDao;
import erp.boq_mgmt.dto.request.WorkLayerBoqsFetchRequest;
import erp.boq_mgmt.entity.WorkLayer;
import erp.boq_mgmt.entity.WorkLayerStateTransition;

@Repository
public class WorkLayerDaoImpl implements WorkLayerDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer saveWorkLayer(WorkLayer workLayer) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkLayer> cr = cb.createQuery(WorkLayer.class);
		Root<WorkLayer> root = cr.from(WorkLayer.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if (workLayer.getCode() != null && !workLayer.getCode().isBlank())
			orPredicates.add(cb.like(cb.upper(root.get("code")), workLayer.getCode().toUpperCase()));
		orPredicates.add(cb.like(cb.upper(root.get("name")), workLayer.getName().toUpperCase()));
		andPredicates.add(cb.equal(root.get("companyId"), workLayer.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), workLayer.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<WorkLayer> query = session.createQuery(cr);
		List<WorkLayer> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Integer id = (Integer) session.save(workLayer);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public WorkLayer fetchWorkLayerById(Integer id) {

		Session session = entityManager.unwrap(Session.class);
		WorkLayer dbObj = (WorkLayer) session.get(WorkLayer.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateWorkLayer(WorkLayer workLayer) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkLayer> cr = cb.createQuery(WorkLayer.class);
		Root<WorkLayer> root = cr.from(WorkLayer.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), workLayer.getId()));
		if (workLayer.getCode() != null && !workLayer.getCode().isBlank())
			orPredicates.add(cb.like(cb.upper(root.get("code")), workLayer.getCode().toUpperCase()));
		orPredicates.add(cb.like(cb.upper(root.get("name")), workLayer.getName().toUpperCase()));
		andPredicates.add(cb.equal(root.get("companyId"), workLayer.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), workLayer.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<WorkLayer> query = session.createQuery(cr);
		List<WorkLayer> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(workLayer);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateWorkLayer(WorkLayer dbObj) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Long saveWorkLayerStateTransitionMapping(WorkLayerStateTransition stateTransition) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(stateTransition);
		session.clear();
		session.flush();
		return id;

	}

	@Override
	public List<WorkLayerStateTransition> fetchWorkLayerStateTransitionByWorkLayerId(Integer workLayerId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkLayerStateTransition> cr = cb.createQuery(WorkLayerStateTransition.class);
		Root<WorkLayerStateTransition> root = cr.from(WorkLayerStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workLayerId"), workLayerId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkLayerStateTransition> query = session.createQuery(cr);
		List<WorkLayerStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkLayerStateTransition> fetchWorkLayerStateTransitionList(WorkLayerBoqsFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkLayerStateTransition> cr = cb.createQuery(WorkLayerStateTransition.class);
		Root<WorkLayerStateTransition> root = cr.from(WorkLayerStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(cb.equal(root.get("workLayer").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("workLayer").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("workLayer").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("workLayer").get("code"), requestDTO.getSearchField() + "%"));
			orPredicates
					.add(cb.like(root.get("workLayer").get("state").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("workLayer").get("description"), requestDTO.getSearchField() + "%"));
		}

		roleOrStatePredicates
				.add(cb.equal(root.get("createdByUser").get("role").get("id"), requestDTO.getUserDetail().getRoleId()));
		for (Map.Entry<Integer, Set<Integer>> entry : roleStateMap.entrySet()) {
			Integer roleId = entry.getKey();
			for (Integer stateId : entry.getValue()) {
				List<Predicate> tempAndPredicates = new ArrayList<Predicate>();
				tempAndPredicates.add(cb.equal(root.get("createdByUser").get("role").get("id"), roleId));
				tempAndPredicates.add(cb.equal(root.get("state").get("id"), stateId));
				Predicate tempAndPredicate = cb.and(tempAndPredicates.toArray(new Predicate[] {}));
				roleOrStatePredicates.add(tempAndPredicate);
			}

		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("workLayerId")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, roleOrStatePredicate, orPredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<WorkLayerStateTransition> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<WorkLayerStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchWorkLayerStateTransitionListCount(WorkLayerBoqsFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<WorkLayerStateTransition> root = cr.from(WorkLayerStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(cb.equal(root.get("workLayer").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("workLayer").get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("workLayer").get("name"), requestDTO.getSearchField()));
			orPredicates.add(cb.like(root.get("workLayer").get("code"), requestDTO.getSearchField() + "%"));
			orPredicates
					.add(cb.like(root.get("workLayer").get("state").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("workLayer").get("description"), requestDTO.getSearchField() + "%"));

		}

		roleOrStatePredicates
				.add(cb.equal(root.get("createdByUser").get("role").get("id"), requestDTO.getUserDetail().getRoleId()));
		for (Map.Entry<Integer, Set<Integer>> entry : roleStateMap.entrySet()) {
			Integer roleId = entry.getKey();
			for (Integer stateId : entry.getValue()) {
				List<Predicate> tempAndPredicates = new ArrayList<Predicate>();
				tempAndPredicates.add(cb.equal(root.get("createdByUser").get("role").get("id"), roleId));
				tempAndPredicates.add(cb.equal(root.get("state").get("id"), stateId));
				Predicate tempAndPredicate = cb.and(tempAndPredicates.toArray(new Predicate[] {}));
				roleOrStatePredicates.add(tempAndPredicate);
			}

		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("workLayerId")).select(cb.count(root))
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, roleOrStatePredicate, orPredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

}
