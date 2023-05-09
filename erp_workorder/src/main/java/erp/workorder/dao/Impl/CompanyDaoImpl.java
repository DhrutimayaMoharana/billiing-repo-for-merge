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

import erp.workorder.dao.CompanyDao;
import erp.workorder.entity.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Company> fetchCompanyList() {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Company> cr = cb.createQuery(Company.class);
		Root<Company> root = cr.from(Company.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Company> query = session.createQuery(cr);
		List<Company> results = query.getResultList();
		return results;
	}

}
