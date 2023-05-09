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

import erp.boq_mgmt.dao.WorkLayerBoqDao;
import erp.boq_mgmt.dto.request.WorkLayerFinalSuccessFetchRequest;
import erp.boq_mgmt.entity.WorkLayerBoqs;
import erp.boq_mgmt.entity.WorkLayerBoqsV2;

@Repository
public class WorkLayerBoqDaoImpl implements WorkLayerBoqDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkLayerBoqs> fetchWorkLayerBoqsByWorkLayerIds(Set<Integer> workLayerIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkLayerBoqs> cr = cb.createQuery(WorkLayerBoqs.class);
		Root<WorkLayerBoqs> root = cr.from(WorkLayerBoqs.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Integer> workLayerExp = root.get("workLayerId");
		andPredicates.add(workLayerExp.in(workLayerIds));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<WorkLayerBoqs> query = session.createQuery(cr);
		List<WorkLayerBoqs> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long saveWorkLayerBoq(WorkLayerBoqs clibObj) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkLayerBoqs> cr = cb.createQuery(WorkLayerBoqs.class);
		Root<WorkLayerBoqs> root = cr.from(WorkLayerBoqs.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workLayerId"), clibObj.getWorkLayerId()));
		andPredicates.add(cb.equal(root.get("boqItemId"), clibObj.getBoqItemId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<WorkLayerBoqs> query = session.createQuery(cr);
		List<WorkLayerBoqs> results = query.getResultList();
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
	public Boolean deactivateWorkLayerBoq(WorkLayerBoqs clib) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(clib);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public List<WorkLayerBoqsV2> fetchWorkLayerBoqsByStateIds(Set<Integer> finalSuccessStateIds,
			WorkLayerFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkLayerBoqsV2> cr = cb.createQuery(WorkLayerBoqsV2.class);
		Root<WorkLayerBoqsV2> root = cr.from(WorkLayerBoqsV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();

		Expression<Integer> stateExp = root.get("workLayer").get("stateId");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getBoqIds() != null && !requestDTO.getBoqIds().isEmpty()) {
			Expression<Integer> boqItemExp = root.get("boqItemId");
			andPredicates.add(boqItemExp.in(requestDTO.getBoqIds()));
		}

		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("workLayer").get("name"), requestDTO.getSearchField() + "%"));
		}

		andPredicates.add(cb.equal(root.get("workLayer").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("workLayer").get("isActive"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.groupBy(root.get("workLayer").get("id")).select(root)
				.where(!orPredicates.isEmpty() ? cb.and(andPredicate, orPredicate) : andPredicate);
		Query<WorkLayerBoqsV2> query = session.createQuery(cr);
		if (requestDTO.getPageSize() != null && requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0
				&& requestDTO.getPageSize() >= 0) {
			query.setMaxResults(requestDTO.getPageSize());
			query.setFirstResult((requestDTO.getPageNo() - 1) * requestDTO.getPageSize());
		}
		List<WorkLayerBoqsV2> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	@Override
	public Long fetchWorkLayerBoqsByStateIdsCount(Set<Integer> finalSuccessStateIds,
			WorkLayerFinalSuccessFetchRequest requestDTO) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cr = cb.createQuery(Long.class);
		Root<WorkLayerBoqsV2> root = cr.from(WorkLayerBoqsV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();

		Expression<Integer> stateExp = root.get("workLayer").get("stateId");
		andPredicates.add(stateExp.in(finalSuccessStateIds));
		if (requestDTO.getBoqIds() != null && !requestDTO.getBoqIds().isEmpty()) {
			Expression<Integer> boqItemExp = root.get("boqItemId");
			andPredicates.add(boqItemExp.in(requestDTO.getBoqIds()));
		}

		if (requestDTO.getSearchField() != null && !requestDTO.getSearchField().isBlank()) {
			orPredicates.add(cb.equal(root.get("workLayer").get("name"), requestDTO.getSearchField() + "%"));
		}
		andPredicates.add(cb.equal(root.get("workLayer").get("companyId"), requestDTO.getUserDetail().getCompanyId()));
		andPredicates.add(cb.equal(root.get("workLayer").get("isActive"), true));
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
