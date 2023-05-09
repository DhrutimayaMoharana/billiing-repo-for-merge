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

import erp.billing.dao.DieselRateDao;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.DieselRateMapping;
import erp.billing.util.DateUtil;

@Repository
public class DieselRateDaoImpl implements DieselRateDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<DieselRateMapping> fetchDieselRates(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<DieselRateMapping> cr = cb.createQuery(DieselRateMapping.class);
		Root<DieselRateMapping> root = cr.from(DieselRateMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.greaterThanOrEqualTo(root.get("date"), DateUtil.dateWithoutTime(search.getFromDate())));
		andPredicates.add(cb.lessThan(root.get("date"), DateUtil.nextDateWithoutTime(search.getToDate())));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<DieselRateMapping> query = session.createQuery(cr);
		List<DieselRateMapping> results = query.getResultList();
		return results;
	}

}
