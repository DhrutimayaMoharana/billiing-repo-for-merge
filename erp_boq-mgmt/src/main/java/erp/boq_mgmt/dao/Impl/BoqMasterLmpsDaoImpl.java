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

import erp.boq_mgmt.dao.BoqMasterLmpsDao;
import erp.boq_mgmt.dto.request.BoqMasterLmpsFetchRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.UndefinedMasterLmpsBoqsFetchRequest;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.BoqMasterLmps;
import erp.boq_mgmt.entity.BoqMasterLmpsStateTransition;

@Repository
public class BoqMasterLmpsDaoImpl implements BoqMasterLmpsDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveBoqMasterLmps(BoqMasterLmps boqMasterLmps) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmps> cr = cb.createQuery(BoqMasterLmps.class);
		Root<BoqMasterLmps> root = cr.from(BoqMasterLmps.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqId"), boqMasterLmps.getBoqId()));
		andPredicates.add(cb.equal(root.get("companyId"), boqMasterLmps.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqMasterLmps.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqMasterLmps> query = session.createQuery(cr);
		List<BoqMasterLmps> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(boqMasterLmps);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public BoqMasterLmps fetchBoqMasterLmpsById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		BoqMasterLmps dbObj = (BoqMasterLmps) session.get(BoqMasterLmps.class, id);
		session.flush();
		session.clear();
		return dbObj;

	}

	@Override
	public Boolean updateBoqMasterLmps(BoqMasterLmps boqMasterLmps) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmps> cr = cb.createQuery(BoqMasterLmps.class);
		Root<BoqMasterLmps> root = cr.from(BoqMasterLmps.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), boqMasterLmps.getId()));
		andPredicates.add(cb.equal(root.get("boqId"), boqMasterLmps.getBoqId()));
		andPredicates.add(cb.equal(root.get("companyId"), boqMasterLmps.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), boqMasterLmps.getIsActive()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<BoqMasterLmps> query = session.createQuery(cr);
		List<BoqMasterLmps> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(boqMasterLmps);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean deactivateBoqMasterLmps(BoqMasterLmps dbObj) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(dbObj);
		session.flush();
		session.clear();
		return true;

	}

	@Override
	public Long saveBoqMasterLmpsStateTransitionMapping(BoqMasterLmpsStateTransition stateTransition) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(stateTransition);
		session.clear();
		session.flush();
		return id;

	}

	@Override
	public List<BoqMasterLmpsStateTransition> fetchBoqMasterLmpsStateTransitionByBoqMasterLmpsId(Long boqMasterLmpsId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsStateTransition> cr = cb.createQuery(BoqMasterLmpsStateTransition.class);
		Root<BoqMasterLmpsStateTransition> root = cr.from(BoqMasterLmpsStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqMasterLmpsId"), boqMasterLmpsId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqMasterLmpsStateTransition> query = session.createQuery(cr);
		List<BoqMasterLmpsStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public List<BoqMasterLmpsStateTransition> fetchBoqMasterLmpsStateTransitionList(
			BoqMasterLmpsFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmpsStateTransition> cr = cb.createQuery(BoqMasterLmpsStateTransition.class);
		Root<BoqMasterLmpsStateTransition> root = cr.from(BoqMasterLmpsStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates
				.add(cb.equal(root.get("boqMasterLmps").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("boqMasterLmps").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates
					.add(cb.like(root.join("boqMasterLmps").get("boq").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates
					.add(cb.like(root.join("boqMasterLmps").get("boq").get("code"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.join("boqMasterLmps").get("boq").get("unit").get("name"),
					requestDTO.getSearchField() + "%"));

			orPredicates.add(cb.like(root.join("boqMasterLmps").get("updatedByUser").get("name"),
					requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.join("boqMasterLmps").get("state").get("name"), requestDTO.getSearchField() + "%"));
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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("boqMasterLmpsId")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate, roleOrStatePredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<BoqMasterLmpsStateTransition> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<BoqMasterLmpsStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchBoqMasterLmpsStateTransitionListCount(BoqMasterLmpsFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<BoqMasterLmpsStateTransition> root = cr.from(BoqMasterLmpsStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		andPredicates
				.add(cb.equal(root.get("boqMasterLmps").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("boqMasterLmps").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates
					.add(cb.like(root.join("boqMasterLmps").get("boq").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates
					.add(cb.like(root.join("boqMasterLmps").get("boq").get("code"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.join("boqMasterLmps").get("boq").get("unit").get("name"),
					requestDTO.getSearchField() + "%"));

			orPredicates.add(cb.like(root.join("boqMasterLmps").get("updatedByUser").get("name"),
					requestDTO.getSearchField() + "%"));
			orPredicates.add(
					cb.like(root.join("boqMasterLmps").get("state").get("name"), requestDTO.getSearchField() + "%"));
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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("boqMasterLmpsId")).select(cb.count(root))
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate, roleOrStatePredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

	@Override
	public List<BoqMasterLmps> fetchBoqMasterLmpsByStateIds(Set<Integer> finalSuccessStateIds,
			BoqMasterLmpsFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmps> cr = cb.createQuery(BoqMasterLmps.class);
		Root<BoqMasterLmps> root = cr.from(BoqMasterLmps.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> stateExp = root.get("state").get("id");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("boq").get("name"), requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.equal(root.get("boq").get("code"), requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<BoqMasterLmps> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<BoqMasterLmps> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long fetchBoqMasterLmpsByStateIdsCount(Set<Integer> finalSuccessStateIds,
			BoqMasterLmpsFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<BoqMasterLmps> root = cr.from(BoqMasterLmps.class);
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
	public List<BoqMasterLmps> fetchBoqMasterLmpsList(UndefinedMasterLmpsBoqsFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmps> cr = cb.createQuery(BoqMasterLmps.class);
		Root<BoqMasterLmps> root = cr.from(BoqMasterLmps.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<BoqMasterLmps> query = session.createQuery(cr);
		List<BoqMasterLmps> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public List<BoqItem> fetchUndefinedMasterLmpsBoqs(UndefinedMasterLmpsBoqsFetchRequest requestDTO,
			Set<Long> distinctDefinedMasterLmpsBoqIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if (distinctDefinedMasterLmpsBoqIds != null && !distinctDefinedMasterLmpsBoqIds.isEmpty()) {
			Expression<Long> boqIdsExp = root.get("id");
			andPredicates.add(cb.not(boqIdsExp.in(distinctDefinedMasterLmpsBoqIds)));
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

	@Override
	public BoqMasterLmps fetchBoqMasterLmpsByBoqId(Long boqId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqMasterLmps> cr = cb.createQuery(BoqMasterLmps.class);
		Root<BoqMasterLmps> root = cr.from(BoqMasterLmps.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boqId"), boqId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqMasterLmps> query = session.createQuery(cr);
		List<BoqMasterLmps> results = query.getResultList();
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

}
