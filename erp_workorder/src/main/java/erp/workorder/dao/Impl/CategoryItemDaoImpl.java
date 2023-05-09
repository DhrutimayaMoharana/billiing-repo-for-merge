
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

import erp.workorder.dao.CategoryItemDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.CategoryItem;

@Repository
public class CategoryItemDaoImpl implements CategoryItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<CategoryItem> fetchCategoryItems(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		List<CategoryItem> results = query.getResultList();
		return results;
	}

	@Override
	public CategoryItem fetchCategoryItemById(Long categoryId) {

		Session session = entityManager.unwrap(Session.class);
		if (categoryId == null)
			return null;
		CategoryItem item = session.get(CategoryItem.class, categoryId);
		return item;
	}

}
