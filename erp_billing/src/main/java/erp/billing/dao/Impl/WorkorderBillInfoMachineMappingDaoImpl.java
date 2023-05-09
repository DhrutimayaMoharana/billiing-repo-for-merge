package erp.billing.dao.Impl;

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

import erp.billing.dao.WorkorderBillInfoMachineMappingDao;
import erp.billing.entity.WorkorderBillInfoMachineMapping;

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

}
