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

import erp.billing.dao.WorkorderBasicDetailDao;
import erp.billing.entity.CategoryItem;
import erp.billing.entity.WorkorderCategoryMapping;

@Repository
public class WorkorderBasicDetailDaoImpl implements WorkorderBasicDetailDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderCategoryMapping> fetchWorkorderCategoriesByWorkorderId(Long woId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderCategoryMapping> cr = cb.createQuery(WorkorderCategoryMapping.class);
		Root<WorkorderCategoryMapping> root = cr.from(WorkorderCategoryMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorderId"), woId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderCategoryMapping> query = session.createQuery(cr);
		List<WorkorderCategoryMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<CategoryItem> fetchActiveCategoryItemsByCompanyId(Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		List<CategoryItem> results = query.getResultList();
		return results;
	}

}
