package erp.boq_mgmt.dao.Impl;

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

import erp.boq_mgmt.dao.StructureGroupDao;
import erp.boq_mgmt.entity.StructureGroup;

@Repository
public class StructureGroupDaoImpl implements StructureGroupDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer saveStructureGroup(StructureGroup group) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureGroup> cr = cb.createQuery(StructureGroup.class);
		Root<StructureGroup> root = cr.from(StructureGroup.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), group.getCompanyId()));
		andPredicates.add(cb.equal(cb.upper(root.get("name")), group.getName().toUpperCase()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureGroup> query = session.createQuery(cr);
		List<StructureGroup> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Integer structureGroupId = (Integer) session.save(group);
		return structureGroupId;
	}

	@Override
	public StructureGroup fetchGroupById(Integer id) {

		Session session = entityManager.unwrap(Session.class);
		StructureGroup group = session.get(StructureGroup.class, id);
		return group;
	}

	@Override
	public Boolean updateStructureGroup(StructureGroup group) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureGroup> cr = cb.createQuery(StructureGroup.class);
		Root<StructureGroup> root = cr.from(StructureGroup.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), group.getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), group.getCompanyId()));
		andPredicates.add(cb.equal(cb.upper(root.get("name")), group.getName().toUpperCase()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureGroup> query = session.createQuery(cr);
		List<StructureGroup> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(group);
		return true;
	}

	@Override
	public List<StructureGroup> fetchGroups(Integer companyId, Long structureTypeId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureGroup> cr = cb.createQuery(StructureGroup.class);
		Root<StructureGroup> root = cr.from(StructureGroup.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		if (structureTypeId != null)
			andPredicates.add(cb.equal(root.get("structureTypeId"), structureTypeId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureGroup> query = session.createQuery(cr);
		List<StructureGroup> results = query.getResultList();
		return results;
	}

}
