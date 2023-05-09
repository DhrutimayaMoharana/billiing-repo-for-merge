package erp.boq_mgmt.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import erp.boq_mgmt.dao.RfiCustomWorkItemsDao;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFinalSuccessFetchRequest;
import erp.boq_mgmt.entity.RfiCustomWorkItemStateTransition;
import erp.boq_mgmt.entity.RfiCustomWorkItems;
import erp.boq_mgmt.entity.RfiCustomWorkItemsV2;

@Repository
public class RfiCustomWorkItemsDaoImpl implements RfiCustomWorkItemsDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveRfiCustomWorkItem(RfiCustomWorkItems rfiCustomWorkItem) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiCustomWorkItems> cr = cb.createQuery(RfiCustomWorkItems.class);
		Root<RfiCustomWorkItems> root = cr.from(RfiCustomWorkItems.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		orPredicates.add(cb.like(cb.upper(root.get("name")), rfiCustomWorkItem.getName().toUpperCase()));
		orPredicates.add(cb.like(root.get("code"), rfiCustomWorkItem.getCode()));
		andPredicates.add(cb.equal(root.get("companyId"), rfiCustomWorkItem.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), rfiCustomWorkItem.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<RfiCustomWorkItems> query = session.createQuery(cr);
		List<RfiCustomWorkItems> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(rfiCustomWorkItem);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public RfiCustomWorkItems fetchRfiCustomWorkItemById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		RfiCustomWorkItems dbObj = (RfiCustomWorkItems) session.get(RfiCustomWorkItems.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateRfiCustomWorkItem(RfiCustomWorkItems rfiCustomWorkItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiCustomWorkItems> cr = cb.createQuery(RfiCustomWorkItems.class);
		Root<RfiCustomWorkItems> root = cr.from(RfiCustomWorkItems.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), rfiCustomWorkItem.getId()));
		orPredicates.add(cb.like(cb.upper(root.get("name")), rfiCustomWorkItem.getName().toUpperCase()));
		orPredicates.add(cb.like(root.get("code"), rfiCustomWorkItem.getCode()));
		andPredicates.add(cb.equal(root.get("companyId"), rfiCustomWorkItem.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), rfiCustomWorkItem.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate, orPredicate));
		Query<RfiCustomWorkItems> query = session.createQuery(cr);
		List<RfiCustomWorkItems> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(rfiCustomWorkItem);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateRfiCustomWorkItem(RfiCustomWorkItems dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public Long saveRfiCustomWorkItemStateTransitionMapping(RfiCustomWorkItemStateTransition stateTransition) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(stateTransition);
		session.clear();
		session.flush();
		return id;

	}

	@Override
	public List<RfiCustomWorkItemStateTransition> fetchRfiCustomWorkItemStateTransitionByRfiCustomWorkItemId(
			Long rfiCustomWorkItemId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiCustomWorkItemStateTransition> cr = cb.createQuery(RfiCustomWorkItemStateTransition.class);
		Root<RfiCustomWorkItemStateTransition> root = cr.from(RfiCustomWorkItemStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("rfiCustomWorkItemId"), rfiCustomWorkItemId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<RfiCustomWorkItemStateTransition> query = session.createQuery(cr);
		List<RfiCustomWorkItemStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public List<RfiCustomWorkItemStateTransition> fetchRfiCustomWorkItemStateTransitionList(
			RfiCustomWorkItemFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiCustomWorkItemStateTransition> cr = cb.createQuery(RfiCustomWorkItemStateTransition.class);
		Root<RfiCustomWorkItemStateTransition> root = cr.from(RfiCustomWorkItemStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(
				cb.equal(root.get("rfiCustomWorkItem").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("rfiCustomWorkItem").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("rfiCustomWorkItem").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("rfiCustomWorkItem").get("code"), requestDTO.getSearchField() + "%"));
			orPredicates
					.add(cb.like(root.get("rfiCustomWorkItem").get("description"), requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.get("rfiCustomWorkItem").get("state").get("name"), requestDTO.getSearchField() + "%"));
		}

		roleOrStatePredicates
				.add(cb.equal(root.get("createdByUser").get("role").get("id"), requestDTO.getUserDetail().getRoleId()));
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
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("rfiCustomWorkItemId")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, roleOrStatePredicate, orPredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<RfiCustomWorkItemStateTransition> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<RfiCustomWorkItemStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchRfiCustomWorkItemStateTransitionListCount(RfiCustomWorkItemFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<RfiCustomWorkItemStateTransition> root = cr.from(RfiCustomWorkItemStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(
				cb.equal(root.get("rfiCustomWorkItem").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("rfiCustomWorkItem").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("rfiCustomWorkItem").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("rfiCustomWorkItem").get("code"), requestDTO.getSearchField() + "%"));
			orPredicates
					.add(cb.like(root.get("rfiCustomWorkItem").get("description"), requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.get("rfiCustomWorkItem").get("state").get("name"), requestDTO.getSearchField() + "%"));
		}

		roleOrStatePredicates
				.add(cb.equal(root.get("createdByUser").get("role").get("id"), requestDTO.getUserDetail().getRoleId()));
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
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("rfiCustomWorkItemId")).select(cb.count(root))
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, roleOrStatePredicate, orPredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

	@Override
	public List<RfiCustomWorkItemsV2> fetchRfiCustomWorkItemByStateIds(Set<Integer> finalSuccessStateIds,
			RfiCustomWorkItemFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiCustomWorkItemsV2> cr = cb.createQuery(RfiCustomWorkItemsV2.class);
		Root<RfiCustomWorkItemsV2> root = cr.from(RfiCustomWorkItemsV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> stateExp = root.get("stateId");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.equal(root.get("code"), requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiCustomWorkItemsV2> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<RfiCustomWorkItemsV2> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long fetchRfiCustomWorkItemByStateIdsCount(Set<Integer> finalSuccessStateIds,
			RfiCustomWorkItemFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<RfiCustomWorkItemsV2> root = cr.from(RfiCustomWorkItemsV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> stateExp = root.get("stateId");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.equal(root.get("code"), requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(cb.count(root)).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<Long> query = session.createQuery(cr);
		Long result = query.getSingleResult();
		session.flush();
		session.clear();
		return result;

	}

}
