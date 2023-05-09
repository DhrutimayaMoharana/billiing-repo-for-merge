package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.WorkorderTncDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WoTnc;
import erp.workorder.entity.WoTncFormulaVariable;
import erp.workorder.entity.WoTypeTncMapping;
import erp.workorder.entity.WorkorderMasterVariable;

@Repository
public class WorkorderTncDaoImpl implements WorkorderTncDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WoTypeTncMapping> fetchWoTypeTermsConditions(Long typeId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTypeTncMapping> cr = cb.createQuery(WoTypeTncMapping.class);
		Root<WoTypeTncMapping> root = cr.from(WoTypeTncMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("woTypeId"), typeId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTypeTncMapping> query = session.createQuery(cr);
		List<WoTypeTncMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<WoTnc> fetchActiveTermsConditionsBySearch(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTnc> cr = cb.createQuery(WoTnc.class);
		Root<WoTnc> root = cr.from(WoTnc.class);
		root.fetch("formulaVariables", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTnc> query = session.createQuery(cr);
		List<WoTnc> results = query.getResultList();
		return results;
	}

	@Override
	public List<WoTnc> fetchWoTncsByTncTypeId(Long typeId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTnc> cr = cb.createQuery(WoTnc.class);
		Root<WoTnc> root = cr.from(WoTnc.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("type", JoinType.LEFT);
		andPredicates.add(cb.equal(root.get("type").get("id"), typeId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).distinct(true).select(root).where(andPredicate);
		Query<WoTnc> query = session.createQuery(cr);
		List<WoTnc> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveTermCondition(WoTnc tnc) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTnc> cr = cb.createQuery(WoTnc.class);
		Root<WoTnc> root = cr.from(WoTnc.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("type", JoinType.LEFT);
		if (tnc.getCode() != null && !tnc.getCode().isEmpty()) {
			andPredicates.add(cb.equal(root.get("code"), tnc.getCode()));
		}
		andPredicates.add(cb.equal(root.get("type").get("id"), tnc.getType().getId()));
		andPredicates.add(cb.equal(root.get("description"), tnc.getDescription()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), tnc.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).distinct(true).select(root).where(andPredicate);
		Query<WoTnc> query = session.createQuery(cr);
		List<WoTnc> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		} else {
			return (Long) session.save(tnc);
		}
	}

	@Override
	public Boolean updateTermCondition(WoTnc savedTnc) {

		if (savedTnc == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTnc> cr = cb.createQuery(WoTnc.class);
		Root<WoTnc> root = cr.from(WoTnc.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("type", JoinType.LEFT);
		if (savedTnc.getName() != null)
			andPredicates.add(cb.equal(root.get("name"), savedTnc.getName()));
		else
			andPredicates.add(cb.isNull(root.get("name")));
		if (savedTnc.getCode() != null && !savedTnc.getCode().isEmpty()) {
			andPredicates.add(cb.equal(root.get("code"), savedTnc.getCode()));
		}
		andPredicates.add(cb.equal(root.get("description"), savedTnc.getDescription()));
		andPredicates.add(cb.notEqual(root.get("id"), savedTnc.getId()));
		andPredicates.add(cb.equal(root.get("type").get("id"), savedTnc.getType().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), savedTnc.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTnc> query = session.createQuery(cr);
		List<WoTnc> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(savedTnc);
			return true;
		}
		return false;
	}

	@Override
	public WoTnc fetchWoTncById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		WoTnc tnc = session.get(WoTnc.class, id);
		return tnc;
	}

	@Override
	public void forceUpdateWoTnc(WoTnc woTnc) {

		Session session = entityManager.unwrap(Session.class);
		session.update(woTnc);
	}

	@Override
	public WoTncFormulaVariable fetchWoTncFormulaVariableByTncAndVar(Long woTncId, Long variableId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncFormulaVariable> cr = cb.createQuery(WoTncFormulaVariable.class);
		Root<WoTncFormulaVariable> root = cr.from(WoTncFormulaVariable.class);
		root.fetch("termCondition", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("termCondition").get("id"), woTncId));
		andPredicates.add(cb.equal(root.get("variable").get("id"), variableId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTncFormulaVariable> query = session.createQuery(cr);
		List<WoTncFormulaVariable> results = query.getResultList();
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public Long saveOrUpdateWoTncFormulaVariable(WoTncFormulaVariable fv) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncFormulaVariable> cr = cb.createQuery(WoTncFormulaVariable.class);
		Root<WoTncFormulaVariable> root = cr.from(WoTncFormulaVariable.class);
		root.fetch("termCondition", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("termCondition").get("id"), fv.getTermCondition().getId()));
		andPredicates.add(cb.equal(root.get("variable").get("id"), fv.getVariable().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTncFormulaVariable> query = session.createQuery(cr);
		List<WoTncFormulaVariable> results = query.getResultList();
		if (results != null && results.size() > 0) {
			WoTncFormulaVariable obj = results.get(0);
			session.detach(obj);
			session.clear();
			fv.setId(obj.getId());
			session.update(fv);
			return fv.getId();
		}
		return (Long) session.save(fv);
	}

	@Override
	public void forceDeactivateWoTncFormulaVariable(WoTncFormulaVariable savedWoTncFv) {

		Session session = entityManager.unwrap(Session.class);
		savedWoTncFv.setIsActive(false);
		savedWoTncFv.setCreatedOn(new Date());
		session.update(savedWoTncFv);
		session.flush();
		session.clear();
	}

	@Override
	public List<WorkorderMasterVariable> fetchWorkorderMasterVariables() {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderMasterVariable> cr = cb.createQuery(WorkorderMasterVariable.class);
		Root<WorkorderMasterVariable> root = cr.from(WorkorderMasterVariable.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderMasterVariable> query = session.createQuery(cr);
		List<WorkorderMasterVariable> results = query.getResultList();
		return results;
	}

}
