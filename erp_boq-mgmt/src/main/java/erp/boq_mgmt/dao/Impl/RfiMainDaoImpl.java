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

import erp.boq_mgmt.dao.RfiMainDao;
import erp.boq_mgmt.dto.request.RfiMainByStateActionFetchRequest;
import erp.boq_mgmt.dto.request.RfiMainFetchRequest;
import erp.boq_mgmt.entity.RfiMain;
import erp.boq_mgmt.entity.RfiMainComments;
import erp.boq_mgmt.entity.RfiMainStateTransition;
import erp.boq_mgmt.enums.RfiMode;
import erp.boq_mgmt.enums.RfiWorkType;

@Repository
public class RfiMainDaoImpl implements RfiMainDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<RfiMain> fetchAllRfiMain(RfiMainFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMain> cr = cb.createQuery(RfiMain.class);
		Root<RfiMain> root = cr.from(RfiMain.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), requestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			andPredicates.add(cb.like(root.get("uniqueCode"), "%" + requestDTO.getSearchField() + "%"));
		}
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiMain> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<RfiMain> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveRfiMain(RfiMain rfi, Integer fromChainageNameNumericValue, Integer toChainageNameNumericValue) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMain> cr = cb.createQuery(RfiMain.class);
		Root<RfiMain> root = cr.from(RfiMain.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> fromChainageAndPredicates = new ArrayList<Predicate>();
		List<Predicate> toChainageAndPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), rfi.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (rfi.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boqId"), rfi.getBoqId()));
		if (rfi.getCustomItemId() != null)
			andPredicates.add(cb.equal(root.get("customItemId"), rfi.getCustomItemId()));
		if (rfi.getWorkLayerId() != null) {
			orPredicates.add(cb.equal(root.get("workLayerId"), rfi.getWorkLayerId()));
			orPredicates.add(cb.isNull(root.get("workLayerId")));
		}
		if (rfi.getMode().equals(RfiMode.New)) {
			if (rfi.getType().equals(RfiWorkType.Highway)) {
				orPredicates.add(cb.isNull(root.get("fromChainageId")));
				if (fromChainageNameNumericValue != null && fromChainageNameNumericValue > 0) {
					fromChainageAndPredicates.add(cb.greaterThanOrEqualTo(
							root.get("fromChainage").get("nameNumericValue"), fromChainageNameNumericValue));
					fromChainageAndPredicates.add(
							cb.lessThan(root.get("toChainage").get("nameNumericValue"), fromChainageNameNumericValue));
					toChainageAndPredicates.add(cb.greaterThan(root.get("fromChainage").get("nameNumericValue"),
							toChainageNameNumericValue));
					toChainageAndPredicates.add(cb.lessThanOrEqualTo(root.get("toChainage").get("nameNumericValue"),
							toChainageNameNumericValue));
					Predicate fromChainageAndPredicate = cb.and(fromChainageAndPredicates.toArray(new Predicate[] {}));
					Predicate toChainageAndPredicate = cb.and(toChainageAndPredicates.toArray(new Predicate[] {}));
					orPredicates.add(fromChainageAndPredicate);
					orPredicates.add(toChainageAndPredicate);
				}
			} else if (rfi.getType().equals(RfiWorkType.Structure)) {
				andPredicates.add(cb.equal(root.get("structureId"), rfi.getStructureId()));
			}

		} else if ((rfi.getMode().equals(RfiMode.Reinspection))) {
			orPredicates.add(cb.equal(root.get("uniqueCode"), rfi.getUniqueCode()));
			orPredicates.add(cb.equal(root.get("parentId"), rfi.getParentId()));
		}
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiMain> query = session.createQuery(cr);
		List<RfiMain> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		Long id = (Long) session.save(rfi);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Boolean updateRfiMain(RfiMain rfi, Integer fromChainageNameNumericValue,
			Integer toChainageNameNumericValue) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMain> cr = cb.createQuery(RfiMain.class);
		Root<RfiMain> root = cr.from(RfiMain.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> fromChainageAndPredicates = new ArrayList<Predicate>();
		List<Predicate> toChainageAndPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), rfi.getId()));
		andPredicates.add(cb.equal(root.get("siteId"), rfi.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (rfi.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boqId"), rfi.getBoqId()));
		if (rfi.getCustomItemId() != null)
			andPredicates.add(cb.equal(root.get("customItemId"), rfi.getCustomItemId()));
		if (rfi.getWorkLayerId() != null) {
			orPredicates.add(cb.equal(root.get("workLayerId"), rfi.getWorkLayerId()));
			orPredicates.add(cb.isNull(root.get("workLayerId")));
		}
		if (rfi.getMode().equals(RfiMode.New)) {
			if (rfi.getType().equals(RfiWorkType.Highway)) {
				orPredicates.add(cb.isNull(root.get("fromChainageId")));
				if (fromChainageNameNumericValue != null && fromChainageNameNumericValue > 0) {
					fromChainageAndPredicates.add(cb.greaterThanOrEqualTo(
							root.get("fromChainage").get("nameNumericValue"), fromChainageNameNumericValue));
					fromChainageAndPredicates.add(
							cb.lessThan(root.get("toChainage").get("nameNumericValue"), fromChainageNameNumericValue));
					toChainageAndPredicates.add(cb.greaterThan(root.get("fromChainage").get("nameNumericValue"),
							toChainageNameNumericValue));
					toChainageAndPredicates.add(cb.lessThanOrEqualTo(root.get("toChainage").get("nameNumericValue"),
							toChainageNameNumericValue));
					Predicate fromChainageAndPredicate = cb.and(fromChainageAndPredicates.toArray(new Predicate[] {}));
					Predicate toChainageAndPredicate = cb.and(toChainageAndPredicates.toArray(new Predicate[] {}));
					orPredicates.add(fromChainageAndPredicate);
					orPredicates.add(toChainageAndPredicate);
				}
			} else if (rfi.getType().equals(RfiWorkType.Structure)) {
				andPredicates.add(cb.equal(root.get("structureId"), rfi.getStructureId()));
			}

		} else if ((rfi.getMode().equals(RfiMode.Reinspection))) {
			orPredicates.add(cb.equal(root.get("uniqueCode"), rfi.getUniqueCode()));
			orPredicates.add(cb.equal(root.get("parentId"), rfi.getParentId()));
		}
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiMain> query = session.createQuery(cr);
		List<RfiMain> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return false;
		}
		session.merge(rfi);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public RfiMain fetchRfiById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		RfiMain obj = (RfiMain) session.get(RfiMain.class, id);
		return obj;
	}

	@Override
	public void forceUpdateRfi(RfiMain rfi) {

		Session session = entityManager.unwrap(Session.class);
		session.update(rfi);
		session.flush();
		session.clear();
	}

	@Override
	public List<RfiMain> fetchByBoqStructureAndSite(Long structureId, Long boqId, Integer workLayerId, Integer siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMain> cr = cb.createQuery(RfiMain.class);
		Root<RfiMain> root = cr.from(RfiMain.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("structureId"), structureId));
		andPredicates.add(cb.equal(root.get("boqId"), boqId));
		if (workLayerId != null) {
			orPredicates.add(cb.equal(root.get("workLayerId"), workLayerId));
			orPredicates.add(cb.isNull(root.get("workLayerId")));
		}
		andPredicates.add(cb.equal(root.get("mode"), RfiMode.New));
		andPredicates.add(cb.equal(root.get("type"), RfiWorkType.Structure));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiMain> query = session.createQuery(cr);
		List<RfiMain> results = query.getResultList();
		return results;
	}

	@Override
	public List<RfiMain> fetchByBoqChainageRangeAndSite(Long boqId, Integer workLayerId,
			Integer fromChainageNameNumericValue, Integer toChainageNameNumericValue, Integer siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMain> cr = cb.createQuery(RfiMain.class);
		Root<RfiMain> root = cr.from(RfiMain.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> fromChainageAndPredicates = new ArrayList<Predicate>();
		List<Predicate> toChainageAndPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("boqId"), boqId));
		andPredicates.add(cb.equal(root.get("mode"), RfiMode.New));
		if (workLayerId != null) {
			orPredicates.add(cb.equal(root.get("workLayerId"), workLayerId));
			orPredicates.add(cb.isNull(root.get("workLayerId")));
		}
		andPredicates.add(cb.equal(root.get("type"), RfiWorkType.Highway));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		orPredicates.add(cb.isNull(root.get("fromChainageId")));
		if (fromChainageNameNumericValue != null && fromChainageNameNumericValue > 0) {
			fromChainageAndPredicates.add(cb.greaterThanOrEqualTo(root.get("fromChainage").get("nameNumericValue"),
					fromChainageNameNumericValue));
			fromChainageAndPredicates
					.add(cb.lessThan(root.get("toChainage").get("nameNumericValue"), fromChainageNameNumericValue));
			toChainageAndPredicates
					.add(cb.greaterThan(root.get("fromChainage").get("nameNumericValue"), toChainageNameNumericValue));
			toChainageAndPredicates.add(
					cb.lessThanOrEqualTo(root.get("toChainage").get("nameNumericValue"), toChainageNameNumericValue));
			Predicate fromChainageAndPredicate = cb.and(fromChainageAndPredicates.toArray(new Predicate[] {}));
			Predicate toChainageAndPredicate = cb.and(toChainageAndPredicates.toArray(new Predicate[] {}));
			orPredicates.add(fromChainageAndPredicate);
			orPredicates.add(toChainageAndPredicate);
		}

		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiMain> query = session.createQuery(cr);
		List<RfiMain> results = query.getResultList();
		return results;
	}

	@Override
	public List<RfiMain> fetchByBoqHighwayAndSite(Long boqId, Integer workLayerId, Integer siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMain> cr = cb.createQuery(RfiMain.class);
		Root<RfiMain> root = cr.from(RfiMain.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("boqId"), boqId));
		if (workLayerId != null) {
			orPredicates.add(cb.equal(root.get("workLayerId"), workLayerId));
			orPredicates.add(cb.isNull(root.get("workLayerId")));
		}
		andPredicates.add(cb.equal(root.get("mode"), RfiMode.New));
		andPredicates.add(cb.equal(root.get("type"), RfiWorkType.Highway));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiMain> query = session.createQuery(cr);
		List<RfiMain> results = query.getResultList();
		return results;
	}

	@Override
	public List<RfiMainStateTransition> fetchRfiMainStateTransitionList(RfiMainFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainStateTransition> cr = cb.createQuery(RfiMainStateTransition.class);
		Root<RfiMainStateTransition> root = cr.from(RfiMainStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();
		if (requestDTO.getStateId() != null) {
			andPredicates.add(cb.equal(root.get("rfiMain").get("stateId"), requestDTO.getStateId()));
		}
		if (requestDTO.getWorkType() != null) {
			andPredicates.add(cb.equal(root.get("rfiMain").get("type"), requestDTO.getWorkType()));
		}
		andPredicates.add(cb.equal(root.get("rfiMain").get("siteId"), requestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("rfiMain").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("rfiMain").get("uniqueCode"), "%" + requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("rfiMain").get("workDescription"), "%" + requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("rfiMain").get("updatedByObj").get("name"),"%" + requestDTO.getSearchField() + "%"));
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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("rfiMainId")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate, roleOrStatePredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<RfiMainStateTransition> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<RfiMainStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchRfiMainStateTransitionListCount(RfiMainFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<RfiMainStateTransition> root = cr.from(RfiMainStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> roleOrStatePredicates = new ArrayList<Predicate>();

		if (requestDTO.getStateId() != null) {
			andPredicates.add(cb.equal(root.get("rfiMain").get("stateId"), requestDTO.getStateId()));
		}
		if (requestDTO.getWorkType() != null) {
			andPredicates.add(cb.equal(root.get("rfiMain").get("type"), requestDTO.getWorkType()));
		}
		andPredicates.add(cb.equal(root.get("rfiMain").get("siteId"), requestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("rfiMain").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("rfiMain").get("uniqueCode"), "%" + requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("rfiMain").get("workDescription"), "%" + requestDTO.getSearchField() + "%"));
			orPredicates.add(cb.like(root.get("rfiMain").get("updatedByObj").get("name"),"%" + requestDTO.getSearchField() + "%"));
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
		cr.orderBy(cb.desc(root.get("createdOn"))).groupBy(root.get("rfiMainId")).select(cb.count(root))
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate, roleOrStatePredicate)
						: cb.and(andPredicate, roleOrStatePredicate));
		Query<Long> query = session.createQuery(cr);
		List<Long> results = query.getResultList();
		return results.size();
	}

	@Override
	public Long saveRfiMainStateTransitionMapping(RfiMainStateTransition stateTransition) {

		Session session = entityManager.unwrap(Session.class);

		Long id = (Long) session.save(stateTransition);
		session.clear();
		session.flush();
		return id;

	}

	@Override
	public List<RfiMain> fetchRfiMainByStateIds(Set<Integer> stateIds, RfiMainByStateActionFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMain> cr = cb.createQuery(RfiMain.class);
		Root<RfiMain> root = cr.from(RfiMain.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> stateExp = root.get("stateId");
		andPredicates.add(stateExp.in(stateIds));
		if (requestDTO.getWorkType() != null) {
			andPredicates.add(cb.equal(root.get("type"), requestDTO.getWorkType()));
		}
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("uniqueCode"), "%" + requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("siteId"), requestDTO.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiMain> query = session.createQuery(cr);
		List<RfiMain> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long fetchRfiMainByStateIdsCount(Set<Integer> stateIds, RfiMainByStateActionFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<RfiMain> root = cr.from(RfiMain.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> stateExp = root.get("stateId");
		andPredicates.add(stateExp.in(stateIds));
		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.like(root.get("uniqueCode"), "%" + requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("siteId"), requestDTO.getSiteId()));
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

	@Override
	public List<RfiMainComments> getRfiMainCommentsByRfiId(Long id) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainComments> cr = cb.createQuery(RfiMainComments.class);
		Root<RfiMainComments> root = cr.from(RfiMainComments.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("rfiMainId"), id));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiMainComments> query = session.createQuery(cr);
		List<RfiMainComments> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long saveRfiMainComment(RfiMainComments rfiMainComment) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainComments> cr = cb.createQuery(RfiMainComments.class);
		Root<RfiMainComments> root = cr.from(RfiMainComments.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiMainComment.getRfiMainId()));
		andPredicates.add(cb.equal(root.get("commentType"), rfiMainComment.getCommentType()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<RfiMainComments> query = session.createQuery(cr);
		List<RfiMainComments> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.clear();
			session.flush();
			return null;
		}

		Long id = (Long) session.save(rfiMainComment);
		session.clear();
		session.flush();
		return id;

	}

	@Override
	public Boolean updateRfiMainComment(RfiMainComments rfiMainComment) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainComments> cr = cb.createQuery(RfiMainComments.class);
		Root<RfiMainComments> root = cr.from(RfiMainComments.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), rfiMainComment.getId()));
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiMainComment.getRfiMainId()));
		andPredicates.add(cb.equal(root.get("commentType"), rfiMainComment.getCommentType()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<RfiMainComments> query = session.createQuery(cr);
		List<RfiMainComments> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		session.merge(rfiMainComment);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<RfiMainComments> fetchRfiMainCommentByTypeAndRfiIds(Integer type, Set<Long> distinctRfiIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainComments> cr = cb.createQuery(RfiMainComments.class);
		Root<RfiMainComments> root = cr.from(RfiMainComments.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		Expression<Integer> rfiMainExp = root.get("rfiMainId");
		andPredicates.add(rfiMainExp.in(distinctRfiIds));
		if (type != null) {
			andPredicates.add(cb.equal(root.get("commentType"), type));
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<RfiMainComments> query = session.createQuery(cr);
		List<RfiMainComments> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public List<RfiMainStateTransition> fetchRfiMainStateTransitionByRfiMainId(Long rfiMainId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainStateTransition> cr = cb.createQuery(RfiMainStateTransition.class);
		Root<RfiMainStateTransition> root = cr.from(RfiMainStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiMainId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<RfiMainStateTransition> query = session.createQuery(cr);
		List<RfiMainStateTransition> results = query.getResultList();
		return results;
	}

}
