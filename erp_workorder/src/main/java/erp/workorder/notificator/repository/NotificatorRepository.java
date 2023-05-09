package erp.workorder.notificator.repository;

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
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.notificator.entity.ClientNotificationCredential;
import erp.workorder.notificator.entity.EmpDepartmentGroupMapping;
import erp.workorder.notificator.entity.EmployeeEmailInfo;
import erp.workorder.notificator.entity.TemplateVariable;
import erp.workorder.notificator.entity.UserNotificationOptOut;

@Repository
@Transactional
public class NotificatorRepository {

	@Autowired
	private EntityManager entityManager;

	public List<EmployeeEmailInfo> fetchEmployeeEmailList(Set<Integer> roleIds, Integer companyId, Integer siteId) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EmployeeEmailInfo> cr = cb.createQuery(EmployeeEmailInfo.class);
		Root<EmployeeEmailInfo> root = cr.from(EmployeeEmailInfo.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Expression<Long> roleExp = root.get("roleId");
		orPredicates.add(roleExp.in(roleIds));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate, orPredicate);
		Query<EmployeeEmailInfo> query = session.createQuery(cr);
		List<EmployeeEmailInfo> results = query.getResultList();
		return results;
	}

	public ClientNotificationCredential getClientCredential(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ClientNotificationCredential> cr = cb.createQuery(ClientNotificationCredential.class);
		Root<ClientNotificationCredential> root = cr.from(ClientNotificationCredential.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<ClientNotificationCredential> query = session.createQuery(cr);
		ClientNotificationCredential results = query.getSingleResult();
		return results;
	}

	public List<TemplateVariable> fetchTemplateVariableList(Set<String> variableList) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TemplateVariable> cr = cb.createQuery(TemplateVariable.class);
		Root<TemplateVariable> root = cr.from(TemplateVariable.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Long> variableExp = root.get("name");
		orPredicates.add(variableExp.in(variableList));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate, orPredicate);
		Query<TemplateVariable> query = session.createQuery(cr);
		List<TemplateVariable> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	public Object executeSQLQuery(String referenceSql) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		Query<?> query = session.createSQLQuery(referenceSql);
		return query.uniqueResult();

	}

	public List<EmpDepartmentGroupMapping> fetchDepartmentGroupListByIds(Set<Integer> departmentGroupIds) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EmpDepartmentGroupMapping> cr = cb.createQuery(EmpDepartmentGroupMapping.class);
		Root<EmpDepartmentGroupMapping> root = cr.from(EmpDepartmentGroupMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Long> departmentGroupExp = root.get("departmentGroupId");
		orPredicates.add(departmentGroupExp.in(departmentGroupIds));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate, orPredicate);
		Query<EmpDepartmentGroupMapping> query = session.createQuery(cr);
		List<EmpDepartmentGroupMapping> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

	public List<UserNotificationOptOut> fetchUserNotificationOptOutList(Set<Long> workflowRuleActionIds) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UserNotificationOptOut> cr = cb.createQuery(UserNotificationOptOut.class);
		Root<UserNotificationOptOut> root = cr.from(UserNotificationOptOut.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		Expression<Long> ruleActionExp = root.get("ruleActionId");
		orPredicates.add(ruleActionExp.in(workflowRuleActionIds));
		andPredicates.add(cb.equal(root.get("status"), true));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate, orPredicate);
		Query<UserNotificationOptOut> query = session.createQuery(cr);
		List<UserNotificationOptOut> results = query.getResultList();
		session.flush();
		session.clear();
		return results;

	}

}
