package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WorkorderEquipmentIssueDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Equipment;
import erp.workorder.entity.EquipmentCategory;
import erp.workorder.entity.WorkorderEquipmentIssue;
import erp.workorder.entity.WorkorderEquipmentIssueVersion;

@Repository
public class WorkorderEquipmentIssueDaoImpl implements WorkorderEquipmentIssueDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<EquipmentCategory> fetchEquipmentCategories(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EquipmentCategory> cr = cb.createQuery(EquipmentCategory.class);
		Root<EquipmentCategory> root = cr.from(EquipmentCategory.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("name")));
		cr.select(root).where(andPredicate);
		Query<EquipmentCategory> query = session.createQuery(cr);
		List<EquipmentCategory> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public List<Equipment> fetchEquipments(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Equipment> cr = cb.createQuery(Equipment.class);
		Root<Equipment> root = cr.from(Equipment.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getCategoryId() != null) {
			andPredicates.add(cb.equal(root.get("category").get("id"), search.getCategoryId()));
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("assetCode")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Equipment> query = session.createQuery(cr);
		List<Equipment> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public List<Equipment> fetchEquipmentsByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Equipment> cr = cb.createQuery(Equipment.class);
		Root<Equipment> root = cr.from(Equipment.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("billingWorkorderId"), workorderId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Equipment> query = session.createQuery(cr);
		List<Equipment> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public Long issueEquipment(WorkorderEquipmentIssue woEquipmentIssue) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderEquipmentIssue> cr = cb.createQuery(WorkorderEquipmentIssue.class);
		Root<WorkorderEquipmentIssue> root = cr.from(WorkorderEquipmentIssue.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorderId"), woEquipmentIssue.getWorkorderId()));
		andPredicates.add(
				cb.equal(root.get("equipmentCategory").get("id"), woEquipmentIssue.getEquipmentCategory().getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderEquipmentIssue> query = session.createQuery(cr);
		List<WorkorderEquipmentIssue> results = query.getResultList();
		return results != null && results.size() > 0 ? null : (Long) session.save(woEquipmentIssue);
	}

	@Override
	public List<WorkorderEquipmentIssue> fetchWorkorderIssuedEquipments(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderEquipmentIssue> cr = cb.createQuery(WorkorderEquipmentIssue.class);
		Root<WorkorderEquipmentIssue> root = cr.from(WorkorderEquipmentIssue.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), search.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderEquipmentIssue> query = session.createQuery(cr);
		List<WorkorderEquipmentIssue> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public Boolean updateIssuedEquipment(WorkorderEquipmentIssue issuedEquipment) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderEquipmentIssue> cr = cb.createQuery(WorkorderEquipmentIssue.class);
		Root<WorkorderEquipmentIssue> root = cr.from(WorkorderEquipmentIssue.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), issuedEquipment.getId()));
		andPredicates.add(cb.equal(root.get("workorderId"), issuedEquipment.getWorkorderId()));
		andPredicates
				.add(cb.equal(root.get("equipmentCategory").get("id"), issuedEquipment.getEquipmentCategory().getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderEquipmentIssue> query = session.createQuery(cr);
		List<WorkorderEquipmentIssue> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(issuedEquipment);
		return true;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public WorkorderEquipmentIssue fetchIssuedEquipmentById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderEquipmentIssue issuedEquipment = (WorkorderEquipmentIssue) session.get(WorkorderEquipmentIssue.class,
				id);
		return issuedEquipment;
	}

	@Override
	public List<Equipment> fetchHiredEquipments(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Equipment> cr = cb.createQuery(Equipment.class);
		Root<Equipment> root = cr.from(Equipment.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.or(cb.equal(root.get("isActive"), true), cb.equal(root.get("isDehired"), true)));
		andPredicates.add(cb.equal(root.get("isOwned"), false));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("assetCode")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Equipment> query = session.createQuery(cr);
		List<Equipment> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public List<Equipment> fetchHiredEquipments(Long siteId, Long categoryId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Equipment> cr = cb.createQuery(Equipment.class);
		Root<Equipment> root = cr.from(Equipment.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("category").get("id"), categoryId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.or(cb.equal(root.get("isActive"), true), cb.equal(root.get("isDehired"), true)));
		andPredicates.add(cb.equal(root.get("isOwned"), false));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("assetCode")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Equipment> query = session.createQuery(cr);
		List<Equipment> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public List<WorkorderEquipmentIssue> fetchWorkorderIssuedEquipmentsByWorkorderId(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderEquipmentIssue> cr = cb.createQuery(WorkorderEquipmentIssue.class);
		Root<WorkorderEquipmentIssue> root = cr.from(WorkorderEquipmentIssue.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), workorderId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderEquipmentIssue> query = session.createQuery(cr);
		List<WorkorderEquipmentIssue> results = query.getResultList();
		return results != null && results.size() > 0 ? results : null;
	}

	@Override
	public Long saveWorkorderEquipmentIssueVersion(WorkorderEquipmentIssueVersion woEquipmentIssueVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(woEquipmentIssueVersion);
		session.flush();
		session.clear();
		return id;
	}

}
