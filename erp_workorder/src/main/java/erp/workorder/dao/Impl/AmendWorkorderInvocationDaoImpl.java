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

import erp.workorder.dao.AmendWorkorderInvocationDao;
import erp.workorder.dto.request.AmendWorkorderInvocationGetRequest;
import erp.workorder.entity.AmendWorkorderInvocation;
import erp.workorder.entity.AmendWorkorderInvocationTransitionMapping;

@Repository
public class AmendWorkorderInvocationDaoImpl implements AmendWorkorderInvocationDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<AmendWorkorderInvocation> fetchWorkorderAmendmentInvocations(
			AmendWorkorderInvocationGetRequest clientRequestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AmendWorkorderInvocation> cr = cb.createQuery(AmendWorkorderInvocation.class);
		Root<AmendWorkorderInvocation> root = cr.from(AmendWorkorderInvocation.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), clientRequestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<AmendWorkorderInvocation> query = session.createQuery(cr);
		List<AmendWorkorderInvocation> results = query.getResultList();
		return results;
	}

	@Override
	public List<AmendWorkorderInvocationTransitionMapping> fetchAmendWorkorderInvocationStateMappings(
			Long amendWorkorderInvocationId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AmendWorkorderInvocationTransitionMapping> cr = cb
				.createQuery(AmendWorkorderInvocationTransitionMapping.class);
		Root<AmendWorkorderInvocationTransitionMapping> root = cr.from(AmendWorkorderInvocationTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("amendWorkorderInvocationId"), amendWorkorderInvocationId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<AmendWorkorderInvocationTransitionMapping> query = session.createQuery(cr);
		List<AmendWorkorderInvocationTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveAmendWorkorderInvocation(AmendWorkorderInvocation invokeAmendment) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AmendWorkorderInvocation> cr = cb.createQuery(AmendWorkorderInvocation.class);
		Root<AmendWorkorderInvocation> root = cr.from(AmendWorkorderInvocation.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), invokeAmendment.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("siteId"), invokeAmendment.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<AmendWorkorderInvocation> query = session.createQuery(cr);
		List<AmendWorkorderInvocation> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(invokeAmendment);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Boolean updateAmendWorkorderInvocation(AmendWorkorderInvocation savedInvocation) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AmendWorkorderInvocation> cr = cb.createQuery(AmendWorkorderInvocation.class);
		Root<AmendWorkorderInvocation> root = cr.from(AmendWorkorderInvocation.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), savedInvocation.getId()));
		andPredicates.add(cb.equal(root.get("workorderId"), savedInvocation.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("siteId"), savedInvocation.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<AmendWorkorderInvocation> query = session.createQuery(cr);
		List<AmendWorkorderInvocation> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(savedInvocation);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public void mapAmendWorkorderInvocationTransition(AmendWorkorderInvocationTransitionMapping trasition) {

		Session session = entityManager.unwrap(Session.class);
		session.save(trasition);
		session.flush();
		session.clear();
	}

	@Override
	public AmendWorkorderInvocation fetchAmendWorkorderInvocationById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		AmendWorkorderInvocation obj = session.get(AmendWorkorderInvocation.class, id);
		return obj;
	}

	@Override
	public void forceUpdateAmendWorkorderInvocation(AmendWorkorderInvocation savedInvocation) {

		Session session = entityManager.unwrap(Session.class);
		session.update(savedInvocation);
		session.flush();
		session.clear();
	}

	@Override
	public List<AmendWorkorderInvocationTransitionMapping> fetchAmendWorkorderInvocationStateMappingsByAmendWorkorderInvocationIds(
			Set<Long> distinctInvocationIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AmendWorkorderInvocationTransitionMapping> cr = cb
				.createQuery(AmendWorkorderInvocationTransitionMapping.class);
		Root<AmendWorkorderInvocationTransitionMapping> root = cr.from(AmendWorkorderInvocationTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (distinctInvocationIds != null) {
			In<Long> inClause = cb.in(root.get("amendWorkorderInvocationId"));
			for (Long invId : distinctInvocationIds) {
				inClause.value(invId);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<AmendWorkorderInvocationTransitionMapping> query = session.createQuery(cr);
		List<AmendWorkorderInvocationTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<AmendWorkorderInvocationTransitionMapping> fetchWorkorderAmendmentInvocationStateTransitionList(
			AmendWorkorderInvocationGetRequest clientRequestDTO, Map<Integer, Set<Integer>> roleStateMap,
			Integer draftStateId, Set<Integer> stateVisibilityIds) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<AmendWorkorderInvocationTransitionMapping> cr = cb
				.createQuery(AmendWorkorderInvocationTransitionMapping.class);
		Root<AmendWorkorderInvocationTransitionMapping> root = cr.from(AmendWorkorderInvocationTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(cb.equal(root.get("amendWorkorderInvocation").get("siteId"), clientRequestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("amendWorkorderInvocation").get("isActive"), true));

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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("amendWorkorderInvocationId")).select(root)
				.where(cb.and(andPredicate, roleOrStatePredicate));
		Query<AmendWorkorderInvocationTransitionMapping> query = session.createQuery(cr);
		if (clientRequestDTO.getPageSize() != null && clientRequestDTO.getPageNo() != null
				&& clientRequestDTO.getPageNo() > 0 && clientRequestDTO.getPageSize() >= 0) {
			query.setMaxResults(clientRequestDTO.getPageSize());
			query.setFirstResult((clientRequestDTO.getPageNo() - 1) * clientRequestDTO.getPageSize());
		}
		List<AmendWorkorderInvocationTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchWorkorderAmendmentInvocationStateTransitionListCount(
			AmendWorkorderInvocationGetRequest clientRequestDTO, Map<Integer, Set<Integer>> roleStateMap,
			Integer draftStateId, Set<Integer> stateVisibilityIds) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<AmendWorkorderInvocationTransitionMapping> root = cr.from(AmendWorkorderInvocationTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(cb.equal(root.get("amendWorkorderInvocation").get("siteId"), clientRequestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("amendWorkorderInvocation").get("isActive"), true));

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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("amendWorkorderInvocationId"))
				.select(cb.count(root)).where(cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

}
