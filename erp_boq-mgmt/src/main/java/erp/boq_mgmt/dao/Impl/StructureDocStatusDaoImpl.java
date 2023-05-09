package erp.boq_mgmt.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
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

import erp.boq_mgmt.dao.StructureDocStatusDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.StructureDocumentStatus;

@Repository
public class StructureDocStatusDaoImpl implements StructureDocStatusDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<StructureDocumentStatus> fetchStructureDocStatus(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentStatus> cr = cb.createQuery(StructureDocumentStatus.class);
		Root<StructureDocumentStatus> root = cr.from(StructureDocumentStatus.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getDocStatusId() != null) {
			andPredicates.add(cb.equal(root.get("id"), search.getDocStatusId()));
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("name")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentStatus> query = session.createQuery(cr);
		List<StructureDocumentStatus> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchIdByNameOrSave(String name, Integer companyId, Long userId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentStatus> cr = cb.createQuery(StructureDocumentStatus.class);
		Root<StructureDocumentStatus> root = cr.from(StructureDocumentStatus.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(cb.upper(root.get("name")), name.toUpperCase()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentStatus> query = session.createQuery(cr);
		List<StructureDocumentStatus> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0).getId();
		}
		StructureDocumentStatus status = new StructureDocumentStatus(null, name, true, new Date(), userId, companyId);
		Integer statusId = (Integer) session.save(status);
		session.flush();
		session.clear();
		return statusId;
	}

}
