package erp.boq_mgmt.dao.Impl;

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

import erp.boq_mgmt.dao.RfiChecklistItemBoqDao;
import erp.boq_mgmt.dto.request.RfiChecklistItemFinalSuccessFetchRequest;
import erp.boq_mgmt.entity.RfiChecklistItemBoqs;
import erp.boq_mgmt.entity.RfiChecklistItemBoqsV2;

@Repository
public class RfiChecklistItemBoqDaoImpl implements RfiChecklistItemBoqDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<RfiChecklistItemBoqs> fetchRfiChecklistItemBoqsByCheckListItemIds(Set<Integer> checklistItemIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiChecklistItemBoqs> cr = cb.createQuery(RfiChecklistItemBoqs.class);
		Root<RfiChecklistItemBoqs> root = cr.from(RfiChecklistItemBoqs.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> checklistItemExp = root.get("checklistItemId");
		andPredicates.add(checklistItemExp.in(checklistItemIds));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiChecklistItemBoqs> query = session.createQuery(cr);
		List<RfiChecklistItemBoqs> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long saveRfiChecklistItemBoq(RfiChecklistItemBoqs clibObj) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiChecklistItemBoqs> cr = cb.createQuery(RfiChecklistItemBoqs.class);
		Root<RfiChecklistItemBoqs> root = cr.from(RfiChecklistItemBoqs.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("checklistItemId"), clibObj.getChecklistItemId()));
		andPredicates.add(cb.equal(root.get("boqItemId"), clibObj.getBoqItemId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<RfiChecklistItemBoqs> query = session.createQuery(cr);
		List<RfiChecklistItemBoqs> results = query.getResultList();
		if (results != null && results.size() > 0) {
			clibObj.setId(results.get(0).getId());
			session.merge(clibObj);
			session.flush();
			session.clear();
			return clibObj.getId();
		}
		Long id = (Long) session.save(clibObj);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Boolean deactivateRfiChecklistItemBoq(RfiChecklistItemBoqs clib) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(clib);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<RfiChecklistItemBoqsV2> fetchRfiChecklistItemBoqsByStateIds(Set<Integer> finalSuccessStateIds,
			RfiChecklistItemFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiChecklistItemBoqsV2> cr = cb.createQuery(RfiChecklistItemBoqsV2.class);
		Root<RfiChecklistItemBoqsV2> root = cr.from(RfiChecklistItemBoqsV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();

		Expression<Integer> stateExp = root.get("checklistItem").get("stateId");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getBoqIds() != null && !requestDTO.getBoqIds().isEmpty()) {
			Expression<Integer> boqItemExp = root.get("boqItemId");
			andPredicates.add(boqItemExp.in(requestDTO.getBoqIds()));
		}

		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("checklistItem").get("name"), requestDTO.getSearchField() + "%"));
		}

		andPredicates
				.add(cb.equal(root.get("checklistItem").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("checklistItem").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.groupBy(root.get("checklistItem").get("id")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<RfiChecklistItemBoqsV2> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<RfiChecklistItemBoqsV2> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long fetchRfiChecklistItemBoqsByStateIdsCount(Set<Integer> finalSuccessStateIds,
			RfiChecklistItemFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<RfiChecklistItemBoqsV2> root = cr.from(RfiChecklistItemBoqsV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();

		Expression<Integer> stateExp = root.get("checklistItem").get("stateId");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getBoqIds() != null && !requestDTO.getBoqIds().isEmpty()) {
			Expression<Integer> boqItemExp = root.get("boqItemId");
			andPredicates.add(boqItemExp.in(requestDTO.getBoqIds()));
		}

		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("checklistItem").get("name"), requestDTO.getSearchField() + "%"));
		}
		andPredicates
				.add(cb.equal(root.get("checklistItem").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("checklistItem").get("isActive"), true));
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
