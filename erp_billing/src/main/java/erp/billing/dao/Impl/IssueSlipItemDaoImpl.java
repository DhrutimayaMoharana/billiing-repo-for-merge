package erp.billing.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import erp.billing.dao.IssueSlipItemDao;
import erp.billing.entity.IssueSlipItem;
import erp.billing.util.DateUtil;
import erp.billing.util.SetObject;

@Repository
public class IssueSlipItemDaoImpl implements IssueSlipItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<IssueSlipItem> fetchIssueSlipItems(Date fromDate, Date toDate, Set<Long> machineIds) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<IssueSlipItem> cr = cb.createQuery(IssueSlipItem.class);
		Root<IssueSlipItem> root = cr.from(IssueSlipItem.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		In<Long> equipmentInClause = cb.in(root.get("equipmentId"));
		for (Long machineId : machineIds) {
			equipmentInClause.value(machineId);
		}
		orPredicates.add(equipmentInClause);
		In<Long> plantInClause = cb.in(root.get("plantId"));
		for (Long machineId : machineIds) {
			equipmentInClause.value(machineId);
		}
		orPredicates.add(plantInClause);
		andPredicates.add(cb.equal(root.get("issueSlip").get("isActive"), true));
		andPredicates
				.add(cb.equal(root.get("issueSlip").get("materialDepartmentId"), SetObject.storeDieselDepartmentId));
		andPredicates
				.add(cb.greaterThanOrEqualTo(root.get("issueSlip").get("dateOn"), DateUtil.dateWithoutTime(fromDate)));
		andPredicates.add(cb.lessThan(root.get("issueSlip").get("dateOn"), DateUtil.nextDateWithoutTime(toDate)));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(orPredicate, andPredicate);
		cr.distinct(true).select(root).where(combinedPredicate);
		Query<IssueSlipItem> query = session.createQuery(cr);
		List<IssueSlipItem> results = query.getResultList();
		return results;
	}

}
