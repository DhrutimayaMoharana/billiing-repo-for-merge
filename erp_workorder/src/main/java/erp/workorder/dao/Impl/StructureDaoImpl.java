package erp.workorder.dao.Impl;

import java.util.ArrayList;
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

import erp.workorder.dao.StructureDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Structure;

@Repository
public class StructureDaoImpl implements StructureDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Structure> fetchStructures(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("startChainage", JoinType.LEFT);
		root.fetch("endChainage", JoinType.LEFT);
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search != null && search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		if (search != null && search.getFromChainageId() != null)
			andPredicates.add(cb.equal(root.get("startChainage").get("id"), search.getFromChainageId()));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Structure> query = session.createQuery(cr);
		List<Structure> results = query.getResultList();
		return results;
	}

	@Override
	public Structure fetchStructureById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		cr.select(root).where(cb.equal(root.get("id"), id));
		Query<Structure> query = session.createQuery(cr);
		Structure result = query.uniqueResult();
		return result;
	}

}
