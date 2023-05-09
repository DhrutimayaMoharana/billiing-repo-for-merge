package erp.billing.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.billing.dao.ClientInvoiceDao;
import erp.billing.dto.request.ClientInvoiceFetchRequest;
import erp.billing.entity.ClientInvoice;
import erp.billing.entity.ClientInvoiceProduct;
import erp.billing.entity.ClientInvoiceStateTransitionMapping;
import erp.billing.entity.ClientInvoiceStateTransitionMappingV2;

@Repository
public class ClientInvoiceDaoImpl implements ClientInvoiceDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveClientInvoice(ClientInvoice clientInvoice) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoice> cr = cb.createQuery(ClientInvoice.class);
		Root<ClientInvoice> root = cr.from(ClientInvoice.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), clientInvoice.getSiteId()));
		andPredicates.add(cb.equal(root.get("invoiceNo"), clientInvoice.getInvoiceNo()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ClientInvoice> query = session.createQuery(cr);
		List<ClientInvoice> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(clientInvoice);
		session.clear();
		session.flush();
		return id;

	}

	@Override
	public Long saveClientInvoiceProduct(ClientInvoiceProduct productObj) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceProduct> cr = cb.createQuery(ClientInvoiceProduct.class);
		Root<ClientInvoiceProduct> root = cr.from(ClientInvoiceProduct.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("clientInvoiceId"), productObj.getClientInvoiceId()));
		andPredicates.add(cb.equal(root.get("gstManagement").get("id"), productObj.getGstManagement().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ClientInvoiceProduct> query = session.createQuery(cr);
		List<ClientInvoiceProduct> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(productObj);
		session.flush();
		session.clear();
		return id;

	}

	@Override
	public List<ClientInvoice> fetchClientInvoiceList(ClientInvoiceFetchRequest requestObj) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoice> cr = cb.createQuery(ClientInvoice.class);
		Root<ClientInvoice> root = cr.from(ClientInvoice.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();

		if (requestObj.getSearch() != null && !requestObj.getSearch().isEmpty()) {
			orPredicates.add(
					cb.like(cb.upper(root.get("invoiceNo")), "%" + requestObj.getSearch().trim().toUpperCase() + "%"));
			orPredicates.add(cb.like(cb.upper(root.get("ciu").get("officeName")),
					"%" + requestObj.getSearch().trim().toUpperCase() + "%"));

		}
		andPredicates.add(cb.equal(root.get("siteId"), requestObj.getSiteId()));

		if (requestObj.getStateId() != null)
			andPredicates.add(cb.equal(root.get("state").get("id"), requestObj.getStateId()));

		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		if (requestObj.getSortByInvoiceDateInAsc() != null && requestObj.getSortByInvoiceDateInAsc())
			cr.orderBy(cb.asc(root.get("updatedOn")));
		else {
			cr.orderBy(cb.desc(root.get("updatedOn")));
		}
		cr.distinct(true).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<ClientInvoice> query = session.createQuery(cr);
		List<ClientInvoice> results = query.getResultList();
		return results;
	}

	@Override
	public List<ClientInvoiceStateTransitionMappingV2> fetchClientInvoiceStateTransitionList(
			ClientInvoiceFetchRequest requestObj, Map<Integer, Set<Integer>> roleStateMap, Integer draftStateId,
			Set<Integer> stateVisibilityIds) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceStateTransitionMappingV2> cr = cb
				.createQuery(ClientInvoiceStateTransitionMappingV2.class);
		Root<ClientInvoiceStateTransitionMappingV2> root = cr.from(ClientInvoiceStateTransitionMappingV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();

		if (requestObj.getSearch() != null && !requestObj.getSearch().isEmpty()) {
			orPredicates.add(cb.like(cb.upper(root.get("clientInvoice").get("invoiceNo")),
					"%" + requestObj.getSearch().trim().toUpperCase() + "%"));
			orPredicates.add(cb.like(cb.upper(root.get("clientInvoice").get("ciu").get("officeName")),
					"%" + requestObj.getSearch().trim().toUpperCase() + "%"));

		}

		andPredicates.add(cb.equal(root.get("clientInvoice").get("siteId"), requestObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("clientInvoice").get("isActive"), true));
		if (requestObj.getStateId() != null)
			andPredicates.add(cb.equal(root.get("clientInvoice").get("state").get("id"), requestObj.getStateId()));

		for (Map.Entry<Integer, Set<Integer>> entry : roleStateMap.entrySet()) {
			Integer roleId = entry.getKey();
			for (Integer stateId : entry.getValue()) {
				List<Predicate> tempAndPredicates = new ArrayList<Predicate>();
				tempAndPredicates.add(cb.equal(root.get("createdByUser").get("role").get("id"), roleId));
				tempAndPredicates.add(cb.equal(root.get("state").get("id"), stateId));
				Predicate tempAndPredicate = cb.and(tempAndPredicates.toArray(new Predicate[] {}));
				roleOrStatePredicates.add(tempAndPredicate);
			}

		}
		roleOrStatePredicates.add(root.get("state").get("id").in(stateVisibilityIds));
		if (draftStateId != null) {
			roleOrStatePredicates.add(cb.and(new Predicate[] {
					cb.equal(root.get("createdByUser").get("role").get("id"), requestObj.getUser().getRoleId()),
					cb.notEqual(root.get("state").get("id"), draftStateId) }));
			roleOrStatePredicates
					.add(cb.and(new Predicate[] { cb.equal(root.get("createdBy"), requestObj.getUser().getId()),
							cb.equal(root.get("state").get("id"), draftStateId) }));
		} else {
			roleOrStatePredicates
					.add(cb.equal(root.get("createdByUser").get("role").get("id"), requestObj.getUser().getRoleId()));
		}

		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));

		if (requestObj.getSortByInvoiceDateInAsc() != null && requestObj.getSortByInvoiceDateInAsc())
			cr.orderBy(cb.asc(root.get("clientInvoice").get("updatedOn")));
		else {
			cr.orderBy(cb.desc(root.get("clientInvoice").get("updatedOn")));
		}

		cr.groupBy(root.get("clientInvoiceId")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate, roleOrStatePredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<ClientInvoiceStateTransitionMappingV2> query = session.createQuery(cr);
		if (requestObj.getPageSize() != null && requestObj.getPageNo() != null && requestObj.getPageNo() > 0
				&& requestObj.getPageSize() >= 0) {
			query.setMaxResults(requestObj.getPageSize());
			query.setFirstResult((requestObj.getPageNo() - 1) * requestObj.getPageSize());
		}
		List<ClientInvoiceStateTransitionMappingV2> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchClientInvoiceStateTransitionCount(ClientInvoiceFetchRequest requestObj,
			Map<Integer, Set<Integer>> roleStateMap, Integer draftStateId, Set<Integer> stateVisibilityIds) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<ClientInvoiceStateTransitionMappingV2> root = cr.from(ClientInvoiceStateTransitionMappingV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();

		if (requestObj.getSearch() != null && !requestObj.getSearch().isEmpty()) {
			orPredicates.add(cb.like(cb.upper(root.get("clientInvoice").get("invoiceNo")),
					"%" + requestObj.getSearch().trim().toUpperCase() + "%"));
			orPredicates.add(cb.like(cb.upper(root.get("clientInvoice").get("ciu").get("officeName")),
					"%" + requestObj.getSearch().trim().toUpperCase() + "%"));

		}

		andPredicates.add(cb.equal(root.get("clientInvoice").get("siteId"), requestObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("clientInvoice").get("isActive"), true));
		if (requestObj.getStateId() != null)
			andPredicates.add(cb.equal(root.get("clientInvoice").get("state").get("id"), requestObj.getStateId()));

		for (Map.Entry<Integer, Set<Integer>> entry : roleStateMap.entrySet()) {
			Integer roleId = entry.getKey();
			for (Integer stateId : entry.getValue()) {
				List<Predicate> tempAndPredicates = new ArrayList<Predicate>();
				tempAndPredicates.add(cb.equal(root.get("createdByUser").get("role").get("id"), roleId));
				tempAndPredicates.add(cb.equal(root.get("state").get("id"), stateId));
				Predicate tempAndPredicate = cb.and(tempAndPredicates.toArray(new Predicate[] {}));
				roleOrStatePredicates.add(tempAndPredicate);
			}

		}
		roleOrStatePredicates.add(root.get("state").get("id").in(stateVisibilityIds));
		if (draftStateId != null) {
			roleOrStatePredicates.add(cb.and(new Predicate[] {
					cb.equal(root.get("createdByUser").get("role").get("id"), requestObj.getUser().getRoleId()),
					cb.notEqual(root.get("state").get("id"), draftStateId) }));
			roleOrStatePredicates
					.add(cb.and(new Predicate[] { cb.equal(root.get("createdBy"), requestObj.getUser().getId()),
							cb.equal(root.get("state").get("id"), draftStateId) }));
		} else {
			roleOrStatePredicates
					.add(cb.equal(root.get("createdByUser").get("role").get("id"), requestObj.getUser().getRoleId()));
		}

		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));

		cr.groupBy(root.get("clientInvoiceId")).select(cb.count(root))
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate, roleOrStatePredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

	@Override
	public List<ClientInvoiceProduct> fetchClientInvoiceProductsByInvoiceIds(Set<Long> distinctClientInvoiceIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceProduct> cr = cb.createQuery(ClientInvoiceProduct.class);
		Root<ClientInvoiceProduct> root = cr.from(ClientInvoiceProduct.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		Expression<Long> clientInvoiceIdsExp = root.get("clientInvoiceId");
		andPredicates.add(clientInvoiceIdsExp.in(distinctClientInvoiceIds));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<ClientInvoiceProduct> query = session.createQuery(cr);
		List<ClientInvoiceProduct> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public ClientInvoice fetchClientInvoiceById(Long clientInvoiceId) {

		Session session = entityManager.unwrap(Session.class);
		ClientInvoice clientInvoice = (ClientInvoice) session.get(ClientInvoice.class, clientInvoiceId);
		session.flush();
		session.clear();
		return clientInvoice;

	}

	@Override
	public Boolean deactivateClientInvoice(ClientInvoice clientInvoice) {

		Session session = entityManager.unwrap(Session.class);
		session.update(clientInvoice);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public void deactivateClientInvoiceProduct(ClientInvoiceProduct ciProduct) {

		Session session = entityManager.unwrap(Session.class);
		session.update(ciProduct);
		session.flush();
		session.clear();
	}

	@Override
	public ClientInvoice mergeClientInvoice(ClientInvoice clientInvoice) {

		if (clientInvoice == null)
			return null;

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoice> cr = cb.createQuery(ClientInvoice.class);
		Root<ClientInvoice> root = cr.from(ClientInvoice.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), clientInvoice.getId()));
		andPredicates.add(cb.equal(root.get("siteId"), clientInvoice.getSiteId()));
		andPredicates.add(cb.equal(root.get("invoiceNo"), clientInvoice.getInvoiceNo()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<ClientInvoice> query = session.createQuery(cr);
		List<ClientInvoice> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			ClientInvoice ci = (ClientInvoice) session.merge(clientInvoice);
			session.flush();
			session.clear();
			return ci;
		}
		session.flush();
		session.clear();
		return null;

	}

	@Override
	public ClientInvoiceProduct mergeClientInvoiceProduct(ClientInvoiceProduct productObj) {

		if (productObj == null)
			return null;

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceProduct> cr = cb.createQuery(ClientInvoiceProduct.class);
		Root<ClientInvoiceProduct> root = cr.from(ClientInvoiceProduct.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), productObj.getId()));
		andPredicates.add(cb.equal(root.get("clientInvoiceId"), productObj.getClientInvoiceId()));
		andPredicates.add(cb.equal(root.get("gstManagement").get("id"), productObj.getGstManagement().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<ClientInvoiceProduct> query = session.createQuery(cr);
		List<ClientInvoiceProduct> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			ClientInvoiceProduct cip = (ClientInvoiceProduct) session.merge(productObj);
			session.flush();
			session.clear();
			return cip;
		}
		session.flush();
		session.clear();
		return null;

	}

	@Override
	public List<ClientInvoiceStateTransitionMapping> fetchClientInvoiceStateTransitionByClientInvoiceIds(
			Set<Long> distinctIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceStateTransitionMapping> cr = cb
				.createQuery(ClientInvoiceStateTransitionMapping.class);
		Root<ClientInvoiceStateTransitionMapping> root = cr.from(ClientInvoiceStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (distinctIds != null) {
			In<Long> inClause = cb.in(root.get("clientInvoiceId"));
			for (Long id : distinctIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ClientInvoiceStateTransitionMapping> query = session.createQuery(cr);
		List<ClientInvoiceStateTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public void saveClientInvoiceStateTransitionMapping(
			ClientInvoiceStateTransitionMapping clientInvoiceStateTransitionMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceStateTransitionMapping> cr = cb
				.createQuery(ClientInvoiceStateTransitionMapping.class);
		Root<ClientInvoiceStateTransitionMapping> root = cr.from(ClientInvoiceStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("clientInvoiceId"), clientInvoiceStateTransitionMap.getClientInvoiceId()));
		andPredicates.add(cb.equal(root.get("stateId"), clientInvoiceStateTransitionMap.getStateId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ClientInvoiceStateTransitionMapping> query = session.createQuery(cr);
		List<ClientInvoiceStateTransitionMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			ClientInvoiceStateTransitionMapping cstm = results.get(0);
			cstm.setCreatedOn(new Date());
			session.merge(cstm);
			session.flush();
			session.clear();
			return;
		}

		session.save(clientInvoiceStateTransitionMap);
		session.clear();
		session.flush();
		return;

	}

	@Override
	public List<ClientInvoiceStateTransitionMappingV2> fetchClientInvoiceStateTransitionListByClientInvoiceId(
			Long clientInvoiceId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientInvoiceStateTransitionMappingV2> cr = cb
				.createQuery(ClientInvoiceStateTransitionMappingV2.class);
		Root<ClientInvoiceStateTransitionMappingV2> root = cr.from(ClientInvoiceStateTransitionMappingV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("clientInvoiceId"), clientInvoiceId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<ClientInvoiceStateTransitionMappingV2> query = session.createQuery(cr);
		List<ClientInvoiceStateTransitionMappingV2> results = query.getResultList();

		return results;
	}

}
