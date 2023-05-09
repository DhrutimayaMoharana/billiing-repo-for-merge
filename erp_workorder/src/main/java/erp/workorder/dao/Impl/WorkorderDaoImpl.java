package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WorkorderDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.EngineState;
import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderBoqWork;
import erp.workorder.entity.WorkorderContractor;
import erp.workorder.entity.WorkorderStateTransitionMapping;
import erp.workorder.entity.WorkorderV2;
import erp.workorder.entity.WorkorderV3;
import erp.workorder.entity.WorkorderV4;
import erp.workorder.entity.WorkorderVersion;
import erp.workorder.enums.EngineStates;
import erp.workorder.enums.WorkorderTypes;

@Repository
public class WorkorderDaoImpl implements WorkorderDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Workorder> fetchWorkorders(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Workorder> cr = cb.createQuery(Workorder.class);
		Root<Workorder> root = cr.from(Workorder.class);
		root.fetch("type", JoinType.LEFT);
		root.fetch("woContractor", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getIsApproved() != null && search.getIsApproved()) {
			andPredicates.add(cb.equal(root.get("state").get("id"), EngineStates.Issued.getValue()));
			andPredicates.add(cb.notEqual(root.get("type").get("id"), WorkorderTypes.Transportation.getId()));
			andPredicates.add(cb.notEqual(root.get("type").get("id"), WorkorderTypes.Labour_Supply.getId()));
		}
		if (search.getWoTypeId() != null) {
			andPredicates.add(cb.equal(root.get("type").get("id"), search.getWoTypeId()));
		}
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("uniqueNo")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate contractorPredicate = cb.like(cb.lower(root.get("contractor").get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate statePredicate = cb.like(cb.lower(root.get("state").get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate userPredicate = cb.like(cb.lower(root.get("modifiedByUser").get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");

			Predicate subjectPredicate = cb.like(cb.lower(root.get("subject")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate, contractorPredicate, statePredicate, userPredicate,
					subjectPredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("modifiedOn")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Workorder> query = session.createQuery(cr);
		List<Workorder> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchCountBySearch(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Workorder> cr = cb.createQuery(Workorder.class);
		Root<Workorder> root = cr.from(Workorder.class);
		root.fetch("type", JoinType.LEFT);
		root.fetch("woContractor", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getIsApproved() != null && search.getIsApproved())
			andPredicates.add(cb.equal(root.get("state").get("id"), EngineStates.Issued.getValue()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("uniqueNo")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Workorder> query = session.createQuery(cr);
		List<Workorder> results = query.getResultList();
		return results.size();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Workorder fetchWorkorderById(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		Workorder workorder = (Workorder) session.get(Workorder.class, workorderId);
		return workorder;
	}

	@Override
	public Workorder fetchLastWorkorderUniqueNoByTypeSiteAndCompany(Long siteId, Integer typeId, Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Workorder> cr = cb.createQuery(Workorder.class);
		Root<Workorder> root = cr.from(Workorder.class);
		root.fetch("type", JoinType.LEFT);
		root.fetch("woContractor", JoinType.LEFT);
		root.fetch("boqWork", JoinType.LEFT);
		root.fetch("termsAndConditions", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("type").get("id"), typeId));
		andPredicates.add(cb.isNull(root.get("fromAmendmentId")));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("id")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Workorder> query = session.createQuery(cr);
		List<Workorder> results = query.getResultList();
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public Boolean forceUpdateWorkorder(Workorder workorder) {

		Session session = entityManager.unwrap(Session.class);
		session.merge(workorder);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public Boolean addWorkorderBoqWork(WorkorderBoqWork workorderBoqWork, Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		String hql = "UPDATE Workorder set boqWork = :workorderBoqWork " + "WHERE id = :id";
		Query<?> query = session.createQuery(hql);
		query.setParameter("id", workorderId);
		query.setParameter("workorderBoqWork", workorderBoqWork);
		int result = query.executeUpdate();
		return result > 0 ? true : false;
	}

	@Override
	public Boolean addWorkorderContractor(WorkorderContractor workorderContractor, Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		String hql = "UPDATE Workorder set woContractor = :workorderContractor " + "WHERE id = :id";
		Query<?> query = session.createQuery(hql);
		query.setParameter("id", workorderId);
		query.setParameter("workorderContractor", workorderContractor);
		int result = query.executeUpdate();
		session.clear();
		return result > 0 ? true : false;
	}

	@Override
	public Boolean finishDraft(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		String hql = "UPDATE Workorder set state = :state " + "WHERE id = :id";
		Query<?> query = session.createQuery(hql);
		query.setParameter("id", workorderId);
		query.setParameter("state", new EngineState(EngineStates.Wait_for_Approval.getValue()));
		int result = query.executeUpdate();
		return result > 0 ? true : false;
	}

	@Override
	public Long saveWorkorderContractor(WorkorderContractor woContractor) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(woContractor);
		return id;
	}

	@Override
	public Boolean updateWorkorderContractor(WorkorderContractor woContractor) {

		Session session = entityManager.unwrap(Session.class);
		session.update(woContractor);
		return true;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Workorder fetchDetachedWorkorderById(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		Workorder workorder = (Workorder) session.get(Workorder.class, workorderId);
		session.detach(workorder);
		return workorder;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<Workorder> fetchWorkordersByTypeId(Long typeId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Workorder> cr = cb.createQuery(Workorder.class);
		Root<Workorder> root = cr.from(Workorder.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("type", JoinType.LEFT);
		andPredicates.add(cb.equal(root.get("type").get("id"), typeId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Workorder> query = session.createQuery(cr);
		List<Workorder> results = query.getResultList();
		return results;
	}

	@Override
	public void mapWorkorderStateTransition(WorkorderStateTransitionMapping woStateTransitionMap) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderStateTransitionMapping> cr = cb.createQuery(WorkorderStateTransitionMapping.class);
		Root<WorkorderStateTransitionMapping> root = cr.from(WorkorderStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), woStateTransitionMap.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("createdOn")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderStateTransitionMapping> query = session.createQuery(cr);
		List<WorkorderStateTransitionMapping> results = query.getResultList();
		if (results != null && results.size() > 0
				&& results.get(0).getStateId().equals(woStateTransitionMap.getStateId())) {
			return;
		} else {
			session.save(woStateTransitionMap);
			session.flush();
			session.clear();
		}
	}

	@Override
	public List<WorkorderStateTransitionMapping> fetchWorkorderStateMappings(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderStateTransitionMapping> cr = cb.createQuery(WorkorderStateTransitionMapping.class);
		Root<WorkorderStateTransitionMapping> root = cr.from(WorkorderStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderStateTransitionMapping> query = session.createQuery(cr);
		List<WorkorderStateTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderV2> fetchWorkordersByStateId(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderV2> cr = cb.createQuery(WorkorderV2.class);
		Root<WorkorderV2> root = cr.from(WorkorderV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		if (search.getClosedWorkorder() != null && search.getClosedWorkorder()) {
			andPredicates.add(cb.isNotNull(root.get("closeType")));
		} else if (search.getClosedWorkorder() != null && !search.getClosedWorkorder()) {
			andPredicates.add(cb.isNull(root.get("closeType")));
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("state").get("id"), search.getStateId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderV2> query = session.createQuery(cr);
		List<WorkorderV2> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderStateTransitionMapping> fetchWorkorderStateMappingsByWorkorderIds(
			Set<Long> distinctWorkorderIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderStateTransitionMapping> cr = cb.createQuery(WorkorderStateTransitionMapping.class);
		Root<WorkorderStateTransitionMapping> root = cr.from(WorkorderStateTransitionMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (distinctWorkorderIds != null) {
			In<Long> inClause = cb.in(root.get("workorderId"));
			for (Long id : distinctWorkorderIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderStateTransitionMapping> query = session.createQuery(cr);
		List<WorkorderStateTransitionMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderV2> fetchWorkordersBySiteIds(Set<Long> distinctSiteIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderV2> cr = cb.createQuery(WorkorderV2.class);
		Root<WorkorderV2> root = cr.from(WorkorderV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (distinctSiteIds != null) {
			In<Long> inClause = cb.in(root.get("siteId"));
			for (Long id : distinctSiteIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderV2> query = session.createQuery(cr);
		List<WorkorderV2> results = query.getResultList();
		return results;
	}

	@Override
	public WorkorderV3 fetchWorkorderV3ByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderV3 workorder = session.get(WorkorderV3.class, workorderId);
		return workorder;
	}

	@Override
	public Boolean forceUpdateWorkorderBillInfo(WorkorderV3 workorder) {

		Session session = entityManager.unwrap(Session.class);
		session.update(workorder);
		return true;
	}

	@Override
	public Long saveWorkorderVersion(WorkorderVersion workorderVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long workorderVersionId = (Long) session.save(workorderVersion);
		return workorderVersionId;
	}

	@Override
	public List<Workorder> fetchWorkordersByEndDatesAndStateIds(Set<Integer> finalSuccessStateIds,
			Set<Date> distinctEndDates, Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Workorder> cr = cb.createQuery(Workorder.class);
		Root<Workorder> root = cr.from(Workorder.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(root.get("state").get("id").in(finalSuccessStateIds));
		andPredicates.add(root.get("endDate").in(distinctEndDates));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Workorder> query = session.createQuery(cr);
		List<Workorder> results = query.getResultList();
		return results;
	}

	@Override
	public Boolean forceUpdateWorkorderV3(WorkorderV3 workorder) {

		Session session = entityManager.unwrap(Session.class);
		session.update(workorder);
		return true;
	}

	@Override
	public WorkorderV4 fetchWorkorderV4ByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderV4 workorder = session.get(WorkorderV4.class, workorderId);
		return workorder;
	}

	@Override
	public Boolean forceUpdateWorkorderV4(WorkorderV4 workorder) {

		Session session = entityManager.unwrap(Session.class);
		session.update(workorder);
		return true;
	}

	@Override
	public List<WoTncMapping> fetchWorkorderTnCByWorkorderId(Long id) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncMapping> cr = cb.createQuery(WoTncMapping.class);
		Root<WoTncMapping> root = cr.from(WoTncMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorder").get("id"), id));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTncMapping> query = session.createQuery(cr);
		List<WoTncMapping> results = query.getResultList();
		return results;
	}

}
