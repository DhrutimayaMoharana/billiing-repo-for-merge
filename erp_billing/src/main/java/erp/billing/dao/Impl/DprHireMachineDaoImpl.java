package erp.billing.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.billing.dao.DprHireMachineDao;
import erp.billing.entity.MachineDPR;
import erp.billing.util.DateUtil;

@Repository
public class DprHireMachineDaoImpl implements DprHireMachineDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<MachineDPR> fetchMachineDprList(Set<Long> machineIds, Date fromDate, Date toDate, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Expression<Long> machineExp = root.get("machineId");
		andPredicates.add(machineExp.in(machineIds));
		Date[] fromDates = DateUtil.getSameDateTomorrowDateWithoutTime(fromDate);
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("dated"), fromDates[0]));
		Date[] toDates = DateUtil.getSameDateTomorrowDateWithoutTime(DateUtil.nextDateWithoutTime(toDate));
		andPredicates.add(cb.lessThan(root.get("dated"), toDates[1]));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<MachineDPR> query = session.createQuery(cr);
		List<MachineDPR> results = query.getResultList();
		return results;
	}

}
