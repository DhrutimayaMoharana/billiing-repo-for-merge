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

import erp.boq_mgmt.dao.StateTransitionDao;
import erp.boq_mgmt.entity.EntityStateMap;

@Repository
public class StateTransitionDaoImpl implements StateTransitionDao {

	@Autowired
	private EntityManager entityManager;

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

}
