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

import erp.boq_mgmt.dao.BoqCostDefinitionDao;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.UndefinedCostDefinitionBoqsFetchRequest;
import erp.boq_mgmt.entity.BoqCostDefinition;
import erp.boq_mgmt.entity.BoqCostDefinitionStateTransition;
import erp.boq_mgmt.entity.BoqItem;

@Repository
public class BoqCostDefinitionDaoImpl implements BoqCostDefinitionDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBoqCostDefinition(BoqCostDefinition boqCostDefinition) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinition> cr = cb.createQuery(BoqCostDefinition.class);
		Root<BoqCostDefinition> root = cr.from(BoqCostDefinition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqId"), boqCostDefinition.getBoqId()));
		andPredicates.add(cb.equal(root.get("companyId"), boqCostDefinition.getCompanyId()));
		andPredicates.add(cb.equal(root.get("siteId"), boqCostDefinition.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqCostDefinition.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqCostDefinition> query = session.createQuery(cr);
		List<BoqCostDefinition> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(boqCostDefinition);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public BoqCostDefinition fetchBoqCostDefinitionById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		BoqCostDefinition dbObj = (BoqCostDefinition) session.get(BoqCostDefinition.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateBoqCostDefinition(BoqCostDefinition boqCostDefinition) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinition> cr = cb.createQuery(BoqCostDefinition.class);
		Root<BoqCostDefinition> root = cr.from(BoqCostDefinition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), boqCostDefinition.getId()));
		andPredicates.add(cb.equal(root.get("boqId"), boqCostDefinition.getBoqId()));
		andPredicates.add(cb.equal(root.get("siteId"), boqCostDefinition.getSiteId()));
		andPredicates.add(cb.equal(root.get("companyId"), boqCostDefinition.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqCostDefinition.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqCostDefinition> query = session.createQuery(cr);
		List<BoqCostDefinition> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(boqCostDefinition);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateBoqCostDefinition(BoqCostDefinition dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public Long saveBoqCostDefinitionStateTransitionMapping(BoqCostDefinitionStateTransition stateTransition) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(stateTransition);
		session.clear();
		session.flush();
		return id;

	}

	@Override
	public List<BoqCostDefinitionStateTransition> fetchBoqCostDefinitionStateTransitionByBoqCostDefinitionId(
			Long boqCostDefinitionId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionStateTransition> cr = cb.createQuery(BoqCostDefinitionStateTransition.class);
		Root<BoqCostDefinitionStateTransition> root = cr.from(BoqCostDefinitionStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqCostDefinitionId"), boqCostDefinitionId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqCostDefinitionStateTransition> query = session.createQuery(cr);
		List<BoqCostDefinitionStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public List<BoqCostDefinitionStateTransition> fetchBoqCostDefinitionStateTransitionList(
			BoqCostDefinitionFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinitionStateTransition> cr = cb.createQuery(BoqCostDefinitionStateTransition.class);
		Root<BoqCostDefinitionStateTransition> root = cr.from(BoqCostDefinitionStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(
				cb.equal(root.get("boqCostDefinition").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("boqCostDefinition").get("siteId"), requestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("boqCostDefinition").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(
					cb.like(root.join("boqCostDefinition").get("boq").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.join("boqCostDefinition").get("boq").get("code"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.join("boqCostDefinition").get("boq").get("unit").get("name"),
					requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.join("boqCostDefinition").get("updatedByUser").get("name"),
					requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.get("boqCostDefinition").get("state").get("name"), requestDTO.getSearchField() + "%"));
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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("boqCostDefinitionId")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate, roleOrStatePredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<BoqCostDefinitionStateTransition> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<BoqCostDefinitionStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchBoqCostDefinitionStateTransitionListCount(BoqCostDefinitionFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<BoqCostDefinitionStateTransition> root = cr.from(BoqCostDefinitionStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates.add(
				cb.equal(root.get("boqCostDefinition").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("boqCostDefinition").get("siteId"), requestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("boqCostDefinition").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(
					cb.like(root.get("boqCostDefinition").get("boq").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.get("boqCostDefinition").get("boq").get("code"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("boqCostDefinition").get("boq").get("unit").get("name"),
					requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("boqCostDefinition").get("updatedByUser").get("name"),
					requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.get("boqCostDefinition").get("state").get("name"), requestDTO.getSearchField() + "%"));
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
		Predicate orPredicate = cb.and(orPredicates.toArray(new Predicate[] {}));
		Predicate roleOrStatePredicate = cb.or(roleOrStatePredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("boqCostDefinitionId")).select(cb.count(root))
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate, roleOrStatePredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

	@Override
	public List<BoqCostDefinition> fetchBoqCostDefinitionByStateIds(Set<Integer> finalSuccessStateIds,
			BoqCostDefinitionFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinition> cr = cb.createQuery(BoqCostDefinition.class);
		Root<BoqCostDefinition> root = cr.from(BoqCostDefinition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> stateExp = root.get("state").get("id");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("boq").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.equal(root.get("boq").get("code"), requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("siteId"), requestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<BoqCostDefinition> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<BoqCostDefinition> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long fetchBoqCostDefinitionByStateIdsCount(Set<Integer> finalSuccessStateIds,
			BoqCostDefinitionFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<BoqCostDefinition> root = cr.from(BoqCostDefinition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> stateExp = root.get("stateId");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("boq").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.equal(root.get("boq").get("code"), requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(cb.count(root)).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<Long> query = session.createQuery(cr);
		Long result = query.getSingleResult();
		session.flush();
		session.clear();
		return result;

	}

	@Override
	public List<BoqCostDefinition> fetchBoqCostDefinitionList(UndefinedCostDefinitionBoqsFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqCostDefinition> cr = cb.createQuery(BoqCostDefinition.class);
		Root<BoqCostDefinition> root = cr.from(BoqCostDefinition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("siteId"), requestDTO.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<BoqCostDefinition> query = session.createQuery(cr);
		List<BoqCostDefinition> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public List<BoqItem> fetchUndefinedCostDefinitionBoqs(UndefinedCostDefinitionBoqsFetchRequest requestDTO,
			Set<Long> distinctDefinedCostDefinitionBoqIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if (distinctDefinedCostDefinitionBoqIds != null && !distinctDefinedCostDefinitionBoqIds.isEmpty()) {
			Expression<Long> boqIdsExp = root.get("id");
			andPredicates.add(cb.not(boqIdsExp.in(distinctDefinedCostDefinitionBoqIds)));
		}
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.equal(root.get("code"), requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

}
