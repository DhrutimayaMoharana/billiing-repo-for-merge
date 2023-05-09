package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.WorkorderCloseDao;
import erp.workorder.dto.request.WorkorderCloseGetRequest;
import erp.workorder.entity.WorkorderClose;
import erp.workorder.entity.WorkorderCloseStateTransitionMapping;

@Repository
public class WorkorderCloseDaoImpl implements WorkorderCloseDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderClose> fetchWorkorderCloseList(WorkorderCloseGetRequest clientRequestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderClose> cr = cb.createQuery(WorkorderClose.class);
		Root<WorkorderClose> root = cr.from(WorkorderClose.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), clientRequestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderClose> query = session.createQuery(cr);
		List<WorkorderClose> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderCloseStateTransitionMapping> fetchWorkorderCloseStateMappings(Long WorkorderCloseId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderCloseStateTransitionMapping> cr = cb
				.createQuery(WorkorderCloseStateTransitionMapping.class);
		Root<WorkorderCloseStateTransitionMapping> root = cr.from(WorkorderCloseStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderCloseId"), WorkorderCloseId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderCloseStateTransitionMapping> query = session.createQuery(cr);
		List<WorkorderCloseStateTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveWorkorderClose(WorkorderClose workorderClose) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(workorderClose);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Boolean updateWorkorderClose(WorkorderClose savedWorkorderClose) {

		Session session = entityManager.unwrap(Session.class);
		session.update(savedWorkorderClose);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public void mapWorkorderCloseStateTransition(WorkorderCloseStateTransitionMapping trasition) {

		Session session = entityManager.unwrap(Session.class);
		session.save(trasition);
		session.flush();
		session.clear();
	}

	@Override
	public WorkorderClose fetchWorkorderCloseById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderClose obj = session.get(WorkorderClose.class, id);
		return obj;
	}

	@Override
	public void forceUpdateWorkorderClose(WorkorderClose savedWorkorderClose) {

		Session session = entityManager.unwrap(Session.class);
		session.update(savedWorkorderClose);
		session.flush();
		session.clear();
	}

	@Override
	public List<WorkorderCloseStateTransitionMapping> fetchWorkorderCloseStateMappingsByWorkorderCloseIds(
			Set<Long> distinctInvocationIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderCloseStateTransitionMapping> cr = cb
				.createQuery(WorkorderCloseStateTransitionMapping.class);
		Root<WorkorderCloseStateTransitionMapping> root = cr.from(WorkorderCloseStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (distinctInvocationIds != null) {
			In<Long> inClause = cb.in(root.get("workorderCloseId"));
			for (Long invId : distinctInvocationIds) {
				inClause.value(invId);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderCloseStateTransitionMapping> query = session.createQuery(cr);
		List<WorkorderCloseStateTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderCloseStateTransitionMapping> fetchWorkorderCloseStateTransitionList(
			WorkorderCloseGetRequest clientRequestDTO, Map<Integer, Set<Integer>> roleStateMap, Integer draftStateId,
			Set<Integer> stateVisibilityIds) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderCloseStateTransitionMapping> cr = cb
				.createQuery(WorkorderCloseStateTransitionMapping.class);
		Root<WorkorderCloseStateTransitionMapping> root = cr.from(WorkorderCloseStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(cb.equal(root.get("workorderClose").get("siteId"), clientRequestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorderClose").get("isActive"), true));
		if (clientRequestDTO.getCloseType() != null) {
			andPredicates.add(cb.equal(root.get("workorderClose").get("closeType"), clientRequestDTO.getCloseType()));
		}

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
		roleOrStatePredicates.add(root.get("state").get("id").in(stateVisibilityIds));
		if (draftStateId != null) {
			roleOrStatePredicates.add(cb.and(new Predicate[] {
					cb.equal(root.get("createdByUser").get("role").get("id"),
							clientRequestDTO.getUserDetail().getRoleId()),
					cb.notEqual(root.get("state").get("id"), draftStateId) }));
			roleOrStatePredicates.add(
					cb.and(new Predicate[] { cb.equal(root.get("createdBy"), clientRequestDTO.getUserDetail().getId()),
							cb.equal(root.get("state").get("id"), draftStateId) }));
		} else {
			roleOrStatePredicates.add(cb.equal(root.get("createdByUser").get("role").get("id"),
					clientRequestDTO.getUserDetail().getRoleId()));
		}

		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("workorderCloseId")).select(root)
				.where(cb.and(andPredicate, roleOrStatePredicate));
		Query<WorkorderCloseStateTransitionMapping> query = session.createQuery(cr);
		if (clientRequestDTO.getPageSize() != null && clientRequestDTO.getPageNo() != null
				&& clientRequestDTO.getPageNo() > 0 && clientRequestDTO.getPageSize() >= 0) {
			query.setMaxResults(clientRequestDTO.getPageSize());
			query.setFirstResult((clientRequestDTO.getPageNo() - 1) * clientRequestDTO.getPageSize());
		}
		List<WorkorderCloseStateTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchWorkorderCloseStateTransitionListCount(WorkorderCloseGetRequest clientRequestDTO,
			Map<Integer, Set<Integer>> roleStateMap, Integer draftStateId, Set<Integer> stateVisibilityIds) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<WorkorderCloseStateTransitionMapping> root = cr.from(WorkorderCloseStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(cb.equal(root.get("workorderClose").get("siteId"), clientRequestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorderClose").get("isActive"), true));

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
		roleOrStatePredicates.add(root.get("state").get("id").in(stateVisibilityIds));
		if (draftStateId != null) {
			roleOrStatePredicates.add(cb.and(new Predicate[] {
					cb.equal(root.get("createdByUser").get("role").get("id"),
							clientRequestDTO.getUserDetail().getRoleId()),
					cb.notEqual(root.get("state").get("id"), draftStateId) }));
			roleOrStatePredicates.add(
					cb.and(new Predicate[] { cb.equal(root.get("createdBy"), clientRequestDTO.getUserDetail().getId()),
							cb.equal(root.get("state").get("id"), draftStateId) }));
		} else {
			roleOrStatePredicates.add(cb.equal(root.get("createdByUser").get("role").get("id"),
					clientRequestDTO.getUserDetail().getRoleId()));
		}

		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("workorderCloseId")).select(cb.count(root))
				.where(cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

	@Override
	public List<WorkorderClose> fetchWorkorderCloseListByWorkorderIdsAndStateIds(Set<Long> workorderIds,
			Set<Integer> stateIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderClose> cr = cb.createQuery(WorkorderClose.class);
		Root<WorkorderClose> root = cr.from(WorkorderClose.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add((root.get("workorderId")).in(workorderIds));
		andPredicates.add((root.get("engineStateId")).in(stateIds));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderClose> query = session.createQuery(cr);
		List<WorkorderClose> results = query.getResultList();
		return results;
	}

}
