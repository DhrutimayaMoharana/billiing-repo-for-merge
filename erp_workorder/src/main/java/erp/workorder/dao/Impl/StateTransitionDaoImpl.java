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

import erp.workorder.dao.StateTransitionDao;
import erp.workorder.entity.EngineState;
import erp.workorder.entity.EntityStateMap;
import erp.workorder.entity.StateTransition;
import erp.workorder.entity.UserRole;

@Repository
public class StateTransitionDaoImpl implements StateTransitionDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public StateTransition fetchStateTransition(Integer entityId, Integer siteId, Integer stateId, Integer roleId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StateTransition> cr = cb.createQuery(StateTransition.class);
		Root<StateTransition> root = cr.from(StateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("entityId"), entityId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("stateId"), stateId));
		andPredicates.add(cb.equal(root.get("roleId"), roleId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StateTransition> query = session.createQuery(cr);
		List<StateTransition> results = query.getResultList();
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public List<StateTransition> fetchRoleStateTransitions(Integer entityId, Integer siteId, Integer stateId, Integer roleId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StateTransition> cr = cb.createQuery(StateTransition.class);
		Root<StateTransition> root = cr.from(StateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("entityId"), entityId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("stateId"), stateId));
		andPredicates.add(cb.equal(root.get("roleId"), roleId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StateTransition> query = session.createQuery(cr);
		List<StateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public List<StateTransition> fetchStateTransitions(Integer entityId, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StateTransition> cr = cb.createQuery(StateTransition.class);
		Root<StateTransition> root = cr.from(StateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("entityId"), entityId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StateTransition> query = session.createQuery(cr);
		List<StateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public EntityStateMap fetchEntityFinalState(Integer entityId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EntityStateMap> cr = cb.createQuery(EntityStateMap.class);
		Root<EntityStateMap> root = cr.from(EntityStateMap.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("entityId"), entityId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isFinal"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<EntityStateMap> query = session.createQuery(cr);
		List<EntityStateMap> results = query.getResultList();
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public List<UserRole> fetchUserRoles(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UserRole> cr = cb.createQuery(UserRole.class);
		Root<UserRole> root = cr.from(UserRole.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<UserRole> query = session.createQuery(cr);
		List<UserRole> results = query.getResultList();
		return results;
	}

	@Override
	public List<EngineState> fetchEntityStates(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EngineState> cr = cb.createQuery(EngineState.class);
		Root<EngineState> root = cr.from(EngineState.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<EngineState> query = session.createQuery(cr);
		List<EngineState> results = query.getResultList();
		return results;
	}

}
