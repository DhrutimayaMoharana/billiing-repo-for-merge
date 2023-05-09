package erp.billing.dao.Impl;

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

import erp.billing.dao.WorkorderBoqWorkDao;
import erp.billing.entity.WorkorderBoqWorkQtyMapping;

@Repository
public class WorkorderBoqWorkDaoImpl implements WorkorderBoqWorkDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderBoqWorkQtyMapping> fetchWoBoqWorkQtys(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBoqWorkQtyMapping> cr = cb.createQuery(WorkorderBoqWorkQtyMapping.class);
		Root<WorkorderBoqWorkQtyMapping> root = cr.from(WorkorderBoqWorkQtyMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqWork").get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderBoqWorkQtyMapping> query = session.createQuery(cr);
		List<WorkorderBoqWorkQtyMapping> results = query.getResultList();
		return results;
	}

}
