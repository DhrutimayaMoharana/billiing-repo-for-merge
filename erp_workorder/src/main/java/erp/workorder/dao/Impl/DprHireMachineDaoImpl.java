package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.DprHireMachineDao;
import erp.workorder.entity.Equipment;
import erp.workorder.entity.MachineDPR;
import erp.workorder.enums.MachineryRunningMode;
import erp.workorder.util.DateUtil;

@Repository
public class DprHireMachineDaoImpl implements DprHireMachineDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public MachineDPR fetchMachineDprById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		MachineDPR obj = (MachineDPR) session.get(MachineDPR.class, id);
		return obj;
	}

	@Override
	public Boolean updateMachineDpr(MachineDPR mDpr) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), mDpr.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), mDpr.getMachineId()));
		andPredicates.add(cb.equal(root.get("machineType"), mDpr.getMachineType()));
		andPredicates.add(cb.notEqual(root.get("id"), mDpr.getId()));
		andPredicates.add(cb.equal(root.get("runningMode"), mDpr.getRunningMode()));
		if (mDpr.getRunningMode().equals(MachineryRunningMode.SHIFTS)) {
			andPredicates.add(cb.equal(root.get("shift"), mDpr.getShift()));
		}
		Date[] dates = DateUtil.getSameDateTomorrowDateWithoutTime(mDpr.getDated());
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("dated"), dates[0]));
		andPredicates.add(cb.lessThan(root.get("dated"), dates[1]));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<MachineDPR> query = session.createQuery(cr);
		List<MachineDPR> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.merge(mDpr);
			return true;
		}
		return false;
	}

	@Override
	public Boolean mergeAndUpdateMachineDpr(MachineDPR mDpr) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), mDpr.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), mDpr.getMachineId()));
		andPredicates.add(cb.equal(root.get("machineType"), mDpr.getMachineType()));
		andPredicates.add(cb.notEqual(root.get("id"), mDpr.getId()));
		Date[] dates = DateUtil.getSameDateTomorrowDateWithoutTime(mDpr.getDated());
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("dated"), dates[0]));
		andPredicates.add(cb.lessThan(root.get("dated"), dates[1]));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<MachineDPR> query = session.createQuery(cr);
		List<MachineDPR> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.merge(mDpr);
			return true;
		}
		return false;
	}

	@Override
	public Long saveMachineDpr(MachineDPR mDpr) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), mDpr.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), mDpr.getMachineId()));
		andPredicates.add(cb.equal(root.get("machineType"), mDpr.getMachineType()));
		andPredicates.add(cb.equal(root.get("runningMode"), mDpr.getRunningMode()));
		if (mDpr.getRunningMode().equals(MachineryRunningMode.SHIFTS)) {
			andPredicates.add(cb.equal(root.get("shift"), mDpr.getShift()));
		}
		Date[] dates = DateUtil.getSameDateTomorrowDateWithoutTime(mDpr.getDated());
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("dated"), dates[0]));
		andPredicates.add(cb.lessThan(root.get("dated"), dates[1]));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<MachineDPR> query = session.createQuery(cr);
		List<MachineDPR> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			Long id = (Long) session.save(mDpr);
			return id;
		}
		return null;
	}

	@Override
	public void forceUpdateMachineDpr(MachineDPR mDpr) {

		Session session = entityManager.unwrap(Session.class);
		session.update(mDpr);
	}

	@Override
	public List<MachineDPR> fetchMachineDprList(Long machineId, Byte machineType, Date fromDate, Date toDate,
			Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), machineId));
		andPredicates.add(cb.equal(root.get("machineType"), machineType));
		Date[] fromDates = DateUtil.getSameDateTomorrowDateWithoutTime(fromDate);
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("dated"), fromDates[0]));
		Date[] toDates = DateUtil.getSameDateTomorrowDateWithoutTime(toDate);
		andPredicates.add(cb.lessThan(root.get("dated"), toDates[1]));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));

		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get("dated")));
		orderList.add(cb.asc(root.get("shift")));
		cr.distinct(true).select(root).where(andPredicate).orderBy(orderList);
		Query<MachineDPR> query = session.createQuery(cr);
		List<MachineDPR> results = query.getResultList();
		return results;
	}

	@Override
	public Equipment fetchEquipmentById(Long machineId) {

		Session session = entityManager.unwrap(Session.class);
		Equipment obj = (Equipment) session.get(Equipment.class, machineId);
		return obj;
	}

	@Override
	public MachineDPR fetchPreviousDateMachineDprByDate(Date dated, Byte machineType, Long machineId, Long siteId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), machineId));
		andPredicates.add(cb.equal(root.get("machineType"), machineType));
		andPredicates.add(cb.lessThan(root.get("dated"), DateUtil.dateWithoutTime(dated)));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		cr.orderBy(cb.desc(root.get("dated")));
		Query<MachineDPR> query = session.createQuery(cr);
		query.setMaxResults(1);
		query.setFirstResult(0);
		List<MachineDPR> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	@Override
	public List<MachineDPR> fetchSameDateMachineDprByDate(Date dated, Byte machineType, Long machineId, Long siteId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), machineId));
		andPredicates.add(cb.equal(root.get("machineType"), machineType));
		andPredicates.add(cb.equal(root.get("dated"), DateUtil.dateWithoutTime(dated)));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<MachineDPR> query = session.createQuery(cr);
		List<MachineDPR> results = query.getResultList();
		return results;
	}

	@Override
	public MachineDPR fetchNextDateMachineDprByDate(Date dated, Byte machineType, Long machineId, Long siteId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), machineId));
		andPredicates.add(cb.equal(root.get("machineType"), machineType));
		andPredicates.add(cb.greaterThan(root.get("dated"), DateUtil.dateWithoutTime(dated)));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		cr.orderBy(cb.asc(root.get("dated")));
		Query<MachineDPR> query = session.createQuery(cr);
		query.setMaxResults(1);
		query.setFirstResult(0);
		List<MachineDPR> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	@Override
	public List<MachineDPR> fetchMachineDprListTillCurrentDate(Long machineId, Byte machineType) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<MachineDPR> cr = cb.createQuery(MachineDPR.class);
		Root<MachineDPR> root = cr.from(MachineDPR.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), machineId));
		andPredicates.add(cb.equal(root.get("machineType"), machineType));
		Date[] toDates = DateUtil.getSameDateTomorrowDateWithoutTime(new Date());
		andPredicates.add(cb.lessThan(root.get("dated"), toDates[1]));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<MachineDPR> query = session.createQuery(cr);
		List<MachineDPR> results = query.getResultList();
		return results;
	}

}
