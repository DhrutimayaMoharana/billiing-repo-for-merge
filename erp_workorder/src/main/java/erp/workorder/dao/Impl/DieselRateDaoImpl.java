package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
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

import erp.workorder.dao.DieselRateDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.DieselRateMapping;
import erp.workorder.entity.DieselRateTransacs;
import erp.workorder.util.DateUtil;

@Repository
public class DieselRateDaoImpl implements DieselRateDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveDieselRate(DieselRateMapping dieselRateMapping) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<DieselRateMapping> cr = cb.createQuery(DieselRateMapping.class);
		Root<DieselRateMapping> root = cr.from(DieselRateMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), dieselRateMapping.getSiteId()));
		Date[] dates = DateUtil.getSameDateTomorrowDateWithoutTime(dieselRateMapping.getDate());
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("date"), dates[0]));
		andPredicates.add(cb.lessThan(root.get("date"), dates[1]));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<DieselRateMapping> query = session.createQuery(cr);
		List<DieselRateMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		return (Long) session.save(dieselRateMapping);
	}

	@Override
	public DieselRateMapping fetchDieselRateByDateAndSite(Long siteId, Date date) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<DieselRateMapping> cr = cb.createQuery(DieselRateMapping.class);
		Root<DieselRateMapping> root = cr.from(DieselRateMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		Date[] dates = DateUtil.getSameDateTomorrowDateWithoutTime(date);
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("date"), dates[0]));
		andPredicates.add(cb.lessThan(root.get("date"), dates[1]));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<DieselRateMapping> query = session.createQuery(cr);
		List<DieselRateMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	@Override
	public List<DieselRateMapping> fetchDieselRates(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<DieselRateMapping> cr = cb.createQuery(DieselRateMapping.class);
		Root<DieselRateMapping> root = cr.from(DieselRateMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("date"), DateUtil.dateWithoutTime(search.getFromDate())));
		andPredicates.add(cb.lessThanOrEqualTo(root.get("date"), DateUtil.dateWithoutTime(search.getToDate())));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);

		Query<DieselRateMapping> query = session.createQuery(cr);
		List<DieselRateMapping> results = query.getResultList();
		return results;
	}

	@Override
	public DieselRateMapping fetchDieselRateById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		DieselRateMapping dieselRate = (DieselRateMapping) session.get(DieselRateMapping.class, id);
		return dieselRate;
	}

	@Override
	public Boolean updateDieselRateMapping(DieselRateMapping drObj) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<DieselRateMapping> cr = cb.createQuery(DieselRateMapping.class);
		Root<DieselRateMapping> root = cr.from(DieselRateMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), drObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), drObj.getId()));
		Date[] dates = DateUtil.getSameDateTomorrowDateWithoutTime(drObj.getDate());
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("date"), dates[0]));
		andPredicates.add(cb.lessThan(root.get("date"), dates[1]));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<DieselRateMapping> query = session.createQuery(cr);
		List<DieselRateMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(drObj);
			return true;
		}
		return false;
	}

	@Override
	public void saveDieselRateTransac(DieselRateTransacs dieselRateTransac) {

		Session session = entityManager.unwrap(Session.class);
		session.save(dieselRateTransac);
	}

}
