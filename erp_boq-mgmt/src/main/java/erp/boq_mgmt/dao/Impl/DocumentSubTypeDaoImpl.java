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

import erp.boq_mgmt.dao.DocumentSubTypeDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.DocumentSubType;

@Repository
public class DocumentSubTypeDaoImpl implements DocumentSubTypeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<DocumentSubType> fetchDocSubTypes(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<DocumentSubType> cr = cb.createQuery(DocumentSubType.class);
		Root<DocumentSubType> root = cr.from(DocumentSubType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getDocumentSubtypeId() != null) {
			andPredicates.add(cb.equal(root.get("id"), search.getDocumentSubtypeId()));
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.asc(root.get("name")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<DocumentSubType> query = session.createQuery(cr);
		List<DocumentSubType> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchIdByNameOrSave(String name, Integer companyId, Long userId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<DocumentSubType> cr = cb.createQuery(DocumentSubType.class);
		Root<DocumentSubType> root = cr.from(DocumentSubType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(cb.upper(root.get("name")), name.toUpperCase()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<DocumentSubType> query = session.createQuery(cr);
		List<DocumentSubType> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0).getId();
		}
		DocumentSubType subtype = new DocumentSubType(null, name, true, new Date(), userId, companyId);
		Integer subtypeId = (Integer) session.save(subtype);
		session.flush();
		session.clear();
		return subtypeId;
	}

}
