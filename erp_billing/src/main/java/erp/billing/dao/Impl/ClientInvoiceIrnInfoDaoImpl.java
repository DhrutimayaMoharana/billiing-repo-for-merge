package erp.billing.dao.Impl;

import java.util.ArrayList;
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

import erp.billing.dao.ClientInvoiceIrnInfoDao;
import erp.billing.entity.ClientInvoiceIrnInfo;

@Repository
public class ClientInvoiceIrnInfoDaoImpl implements ClientInvoiceIrnInfoDao {

	@Autowired
	private EntityManager entityManager;

	public Long saveClientInvoiceIrnInfo(ClientInvoiceIrnInfo clientInvoiceIrnInfoObj) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceIrnInfo> cr = cb.createQuery(ClientInvoiceIrnInfo.class);
		Root<ClientInvoiceIrnInfo> root = cr.from(ClientInvoiceIrnInfo.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("clientInvoiceId"), clientInvoiceIrnInfoObj.getClientInvoiceId()));
		andPredicates.add(cb.like(root.get("irn"), clientInvoiceIrnInfoObj.getIrn()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<ClientInvoiceIrnInfo> query = session.createQuery(cr);
		List<ClientInvoiceIrnInfo> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(clientInvoiceIrnInfoObj);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public ClientInvoiceIrnInfo fetchclientInvoiceIrnInfoByClientId(Long clientInvoiceId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceIrnInfo> cr = cb.createQuery(ClientInvoiceIrnInfo.class);
		Root<ClientInvoiceIrnInfo> root = cr.from(ClientInvoiceIrnInfo.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("clientInvoiceId"), clientInvoiceId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<ClientInvoiceIrnInfo> query = session.createQuery(cr);
		List<ClientInvoiceIrnInfo> results = query.getResultList();
		session.flush();
		session.clear();
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	public Boolean updateClientInvoiceIrnInfo(ClientInvoiceIrnInfo clientInvoiceIrnInfoObj) {
		if (clientInvoiceIrnInfoObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceIrnInfo> cr = cb.createQuery(ClientInvoiceIrnInfo.class);
		Root<ClientInvoiceIrnInfo> root = cr.from(ClientInvoiceIrnInfo.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("clientInvoiceId"), clientInvoiceIrnInfoObj.getClientInvoiceId()));
		andPredicates.add(cb.like(root.get("irn"), clientInvoiceIrnInfoObj.getIrn()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<ClientInvoiceIrnInfo> query = session.createQuery(cr);
		List<ClientInvoiceIrnInfo> results = query.getResultList();

		if (results == null || (results != null && results.size() == 0)) {
			session.merge(clientInvoiceIrnInfoObj);
			session.flush();
			session.clear();
			return true;
		}
		session.flush();
		session.clear();
		return false;
	}

	@Override
	public List<ClientInvoiceIrnInfo> fetchAllclientInvoiceIrnInfoByClientInvoiceId(Set<Long> clientInvoiceIdList) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceIrnInfo> cr = cb.createQuery(ClientInvoiceIrnInfo.class);
		Root<ClientInvoiceIrnInfo> root = cr.from(ClientInvoiceIrnInfo.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		Expression<Long> clientInvoiceIdsExp = root.get("clientInvoiceId");
		andPredicates.add(clientInvoiceIdsExp.in(clientInvoiceIdList));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<ClientInvoiceIrnInfo> query = session.createQuery(cr);
		List<ClientInvoiceIrnInfo> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}

}
