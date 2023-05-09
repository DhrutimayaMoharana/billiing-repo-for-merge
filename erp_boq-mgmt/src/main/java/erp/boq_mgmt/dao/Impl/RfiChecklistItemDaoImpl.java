package erp.boq_mgmt.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.boq_mgmt.dao.RfiChecklistItemDao;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsFetchRequest;
import erp.boq_mgmt.entity.RfiChecklistItemStateTransition;
import erp.boq_mgmt.entity.RfiChecklistItems;

@Repository
public class RfiChecklistItemDaoImpl implements RfiChecklistItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer saveRfiChecklistItem(RfiChecklistItems rfiChecklistItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiChecklistItems> cr = cb.createQuery(RfiChecklistItems.class);
		Root<RfiChecklistItems> root = cr.from(RfiChecklistItems.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.like(cb.upper(root.get("name")), rfiChecklistItem.getName().toUpperCase()));
		andPredicates.add(cb.equal(root.get("companyId"), rfiChecklistItem.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), rfiChecklistItem.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<RfiChecklistItems> query = session.createQuery(cr);
		List<RfiChecklistItems> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Integer id = (Integer) session.save(rfiChecklistItem);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public RfiChecklistItems fetchRfiChecklistItemById(Integer id) {

		Session session = entityManager.unwrap(Session.class);
		RfiChecklistItems dbObj = (RfiChecklistItems) session.get(RfiChecklistItems.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateRfiChecklistItem(RfiChecklistItems rfiChecklistItem) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiChecklistItems> cr = cb.createQuery(RfiChecklistItems.class);
		Root<RfiChecklistItems> root = cr.from(RfiChecklistItems.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), rfiChecklistItem.getId()));
		andPredicates.add(cb.like(cb.upper(root.get("name")), rfiChecklistItem.getName().toUpperCase()));
		andPredicates.add(cb.equal(root.get("companyId"), rfiChecklistItem.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), rfiChecklistItem.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<RfiChecklistItems> query = session.createQuery(cr);
		List<RfiChecklistItems> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(rfiChecklistItem);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateRfiChecklistItem(RfiChecklistItems dbObj) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Long saveRfiChecklistItemStateTransitionMapping(RfiChecklistItemStateTransition stateTransition) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(stateTransition);
		session.clear();
		session.flush();
		return id;

	}

	@Override
	public List<RfiChecklistItemStateTransition> fetchRfiChecklistItemStateTransitionByRfiChecklistItemId(
			Integer rfiChecklistItemId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiChecklistItemStateTransition> cr = cb.createQuery(RfiChecklistItemStateTransition.class);
		Root<RfiChecklistItemStateTransition> root = cr.from(RfiChecklistItemStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("rfiChecklistItemId"), rfiChecklistItemId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<RfiChecklistItemStateTransition> query = session.createQuery(cr);
		List<RfiChecklistItemStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public List<RfiChecklistItemStateTransition> fetchRfiChecklistItemStateTransitionList(
			RfiChecklistItemBoqsFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiChecklistItemStateTransition> cr = cb.createQuery(RfiChecklistItemStateTransition.class);
		Root<RfiChecklistItemStateTransition> root = cr.from(RfiChecklistItemStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(
				cb.equal(root.get("rfiChecklistItem").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("rfiChecklistItem").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("rfiChecklistItem").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.get("rfiChecklistItem").get("state").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates
					.add(cb.like(root.get("rfiChecklistItem").get("description"), requestDTO.getSearchField() + "%"));
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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("rfiChecklistItemId")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, roleOrStatePredicate, orPredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<RfiChecklistItemStateTransition> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<RfiChecklistItemStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchRfiChecklistItemStateTransitionListCount(RfiChecklistItemBoqsFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<RfiChecklistItemStateTransition> root = cr.from(RfiChecklistItemStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(
				cb.equal(root.get("rfiChecklistItem").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("rfiChecklistItem").get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("rfiChecklistItem").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.get("rfiChecklistItem").get("state").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates
					.add(cb.like(root.get("rfiChecklistItem").get("description"), requestDTO.getSearchField() + "%"));
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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("rfiChecklistItemId")).select(cb.count(root))
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, roleOrStatePredicate, orPredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

}
