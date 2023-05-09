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

import erp.workorder.dao.EquipmentSummaryDao;
import erp.workorder.entity.EquipmentSummary;

@Repository
public class EquipmentSummaryDaoImpl implements EquipmentSummaryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public EquipmentSummary fetchEquipmentSummary(Byte machineType, Long machineId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EquipmentSummary> cr = cb.createQuery(EquipmentSummary.class);
		Root<EquipmentSummary> root = cr.from(EquipmentSummary.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), machineId));
		andPredicates.add(cb.equal(root.get("machineType"), machineType));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<EquipmentSummary> query = session.createQuery(cr);
		List<EquipmentSummary> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	@Override
	public Boolean saveEquipmentSummary(EquipmentSummary equipmentSummary) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EquipmentSummary> cr = cb.createQuery(EquipmentSummary.class);
		Root<EquipmentSummary> root = cr.from(EquipmentSummary.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("machineId"), equipmentSummary.getMachineId()));
		andPredicates.add(cb.equal(root.get("machineType"), equipmentSummary.getMachineType()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<EquipmentSummary> query = session.createQuery(cr);
		List<EquipmentSummary> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.save(equipmentSummary);
			return true;
		}
		return null;
	}

	@Override
	public Boolean updateEquipmentSummary(EquipmentSummary equipmentSummary) {

		Session session = entityManager.unwrap(Session.class);
		session.update(equipmentSummary);
		return true;

	}

}
