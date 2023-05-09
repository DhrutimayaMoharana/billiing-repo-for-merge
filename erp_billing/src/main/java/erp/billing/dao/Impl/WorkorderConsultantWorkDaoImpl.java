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

import erp.billing.dao.WorkorderConsultantWorkDao;
import erp.billing.entity.WorkorderConsultantWorkItemMapping;

@Repository
public class WorkorderConsultantWorkDaoImpl implements WorkorderConsultantWorkDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderConsultantWorkItemMapping> fetchWorkorderConsultantWorkItemsByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWorkItemMapping> cr = cb.createQuery(WorkorderConsultantWorkItemMapping.class);
		Root<WorkorderConsultantWorkItemMapping> root = cr.from(WorkorderConsultantWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderConsultantWork").get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderConsultantWorkItemMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderConsultantWorkItemMapping> fetchWorkorderConsultantWorkItemsBySiteId(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderConsultantWorkItemMapping> cr = cb.createQuery(WorkorderConsultantWorkItemMapping.class);
		Root<WorkorderConsultantWorkItemMapping> root = cr.from(WorkorderConsultantWorkItemMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderConsultantWork").get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderConsultantWorkItemMapping> query = session.createQuery(cr);
		List<WorkorderConsultantWorkItemMapping> results = query.getResultList();
		return results;
	}

}
