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

import erp.billing.dao.WorkorderBillInfoDao;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.WorkorderBillInfo;

@Repository
public class WorkorderBillInfoDaoImpl implements WorkorderBillInfoDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer saveWorkorderBillInfo(WorkorderBillInfo workorderBillInfoObj) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBillInfo> cr = cb.createQuery(WorkorderBillInfo.class);
		Root<WorkorderBillInfo> root = cr.from(WorkorderBillInfo.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderBillInfoObj.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("siteId"), workorderBillInfoObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<WorkorderBillInfo> query = session.createQuery(cr);
		List<WorkorderBillInfo> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			workorderBillInfoObj.setId(results.get(0).getId());
			session.merge(workorderBillInfoObj);
			return workorderBillInfoObj.getId();
		}
		Integer id = (Integer) session.save(workorderBillInfoObj);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Boolean updateWorkorderBillInfo(WorkorderBillInfo workorderBillInfoObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(workorderBillInfoObj);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public WorkorderBillInfo fetchWorkorderBillInfo(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBillInfo> cr = cb.createQuery(WorkorderBillInfo.class);
		Root<WorkorderBillInfo> root = cr.from(WorkorderBillInfo.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), search.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderBillInfo> query = session.createQuery(cr);
		WorkorderBillInfo results = null;
		try {
			results = query.getSingleResult();
		} catch (Exception e) {
		}
		session.flush();
		session.clear();
		return results;
	}

}
