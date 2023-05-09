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

import erp.workorder.dao.BoqItemDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.BoqItem;

@Repository
public class BoqItemDaoImpl implements BoqItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<BoqItem> fetchBoqItems(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search != null && search.getSearchField() != null) {
			Predicate codePredicate = cb.like(cb.lower(root.get("code")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate sdbCodePredicate = cb.like(cb.lower(root.get("standardBookCode")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(codePredicate, sdbCodePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		return results;
	}

	@Override
	public BoqItem fetchBoqItemById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		cr.select(root).where(cb.equal(root.get("id"), id));
		Query<BoqItem> query = session.createQuery(cr);
		BoqItem result = query.getSingleResult();
		return result;
	}

	@Override
	public List<BoqItem> fetchBoqItemsByCompanyId(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		return results;
	}

}
