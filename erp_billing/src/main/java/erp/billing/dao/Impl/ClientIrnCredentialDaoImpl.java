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

import erp.billing.dao.ClientIrnCredentialDao;
import erp.billing.entity.ClientIrnCredential;

@Repository
public class ClientIrnCredentialDaoImpl implements ClientIrnCredentialDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public ClientIrnCredential fetchClientByCompanyIdAndSiteId(Integer companyId, Integer siteId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientIrnCredential> cr = cb.createQuery(ClientIrnCredential.class);
		Root<ClientIrnCredential> root = cr.from(ClientIrnCredential.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (siteId != null)
			andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<ClientIrnCredential> query = session.createQuery(cr);
		ClientIrnCredential results = query.getSingleResult();
		session.flush();
		session.clear();
		return results;
	}

}
