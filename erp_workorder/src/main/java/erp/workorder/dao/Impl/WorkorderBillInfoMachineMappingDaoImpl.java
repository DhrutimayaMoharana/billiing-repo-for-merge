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

import erp.workorder.dao.WorkorderBillInfoMachineMappingDao;
import erp.workorder.entity.WorkorderBillInfoMachineMapping;

@Repository
public class WorkorderBillInfoMachineMappingDaoImpl implements WorkorderBillInfoMachineMappingDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderBillInfoMachineMapping> fetchWorkorderBillInfoMachineMapping(Integer workorderBillInfoId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBillInfoMachineMapping> cr = cb.createQuery(WorkorderBillInfoMachineMapping.class);
		Root<WorkorderBillInfoMachineMapping> root = cr.from(WorkorderBillInfoMachineMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderBillInfoId"), workorderBillInfoId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderBillInfoMachineMapping> query = session.createQuery(cr);
		List<WorkorderBillInfoMachineMapping> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}

	@Override
	public Long saveWorkorderBillInfoMachineMapping(WorkorderBillInfoMachineMapping bcmToSave) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderBillInfoMachineMapping> cr = cb.createQuery(WorkorderBillInfoMachineMapping.class);
		Root<WorkorderBillInfoMachineMapping> root = cr.from(WorkorderBillInfoMachineMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("machineType"), bcmToSave.getMachineType()));
		andPredicates.add(cb.equal(root.get("machineId"), bcmToSave.getMachineId()));
		andPredicates.add(cb.equal(root.get("workorderBillInfoId"), bcmToSave.getWorkorderBillInfoId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderBillInfoMachineMapping> query = session.createQuery(cr);
		List<WorkorderBillInfoMachineMapping> results = query.getResultList();
		if (results != null && results.size() > 0) {
			if (!results.get(0).getIsActive()) {
				bcmToSave.setId(results.get(0).getId());
				session.merge(bcmToSave);
				return bcmToSave.getId();
			}
		}
		return (Long) session.save(bcmToSave);
	}

	@Override
	public void forceUpdateWorkorderBillInfoMachineMapping(WorkorderBillInfoMachineMapping bcm) {
		Session session = entityManager.unwrap(Session.class);
		session.merge(bcm);
	}

}
