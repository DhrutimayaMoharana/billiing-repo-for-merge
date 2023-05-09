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

import erp.workorder.dao.EngineStatesDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.EngineState;

@Repository
public class EngineStateDaoImpl implements EngineStatesDao {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<EngineState> fetchEngineStates(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EngineState> cr = cb.createQuery(EngineState.class);
		Root<EngineState> root = cr.from(EngineState.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<EngineState> query = session.createQuery(cr);
		List<EngineState> results = query.getResultList();
		return results;
	}

}
