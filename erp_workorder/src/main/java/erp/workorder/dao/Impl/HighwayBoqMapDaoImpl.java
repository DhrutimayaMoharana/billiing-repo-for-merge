package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.HighwayBoqMapDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.HighwayBoqMapping;

@Repository
public class HighwayBoqMapDaoImpl implements HighwayBoqMapDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<HighwayBoqMapping> fetchHbqsByCategories(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		if (search.getIdsArrSet() != null) {
			In<Long> inClause = cb.in(root.get("category").get("id"));
			for (Long id : search.getIdsArrSet()) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		return results;
	}

}
