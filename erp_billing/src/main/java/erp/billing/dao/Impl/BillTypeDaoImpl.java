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

import erp.billing.dao.BillTypeDao;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillType;

@Repository
public class BillTypeDaoImpl implements BillTypeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<BillType> fetchBillTypes(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillType> cr = cb.createQuery(BillType.class);
		Root<BillType> root = cr.from(BillType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillType> query = session.createQuery(cr);
		List<BillType> results = query.getResultList();
		return results;
	}

}
