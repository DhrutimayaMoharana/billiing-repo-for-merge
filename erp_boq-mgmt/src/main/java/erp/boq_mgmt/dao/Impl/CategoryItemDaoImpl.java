
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.CategoryItemDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.CategoryContractorMapping;
import erp.boq_mgmt.entity.CategoryItem;

@Repository
public class CategoryItemDaoImpl implements CategoryItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveCategoryItem(CategoryItem category) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), category.getCompanyId()));
		andPredicates.add(cb.equal(root.get("name"), category.getName()));
		if (category.getCode() != null && !category.getCode().trim().isEmpty())
			orPredicates.add(cb.like(cb.lower(root.get("code")), category.getCode().toLowerCase()));
		if (category.getStandardBookCode() != null && !category.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.like(cb.lower(root.get("standardBookCode")), category.getStandardBookCode().toLowerCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(andPredicate, orPredicate);
		cr.select(root).where(combinedPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		List<CategoryItem> results = query.getResultList();
		Long id = null;
		if (results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(category);
			session.flush();
		}
		return results.size() > 0 ? results.get(0).getId() : id;
	}

	@Override
	public CategoryItem forceSaveCategoryItem(CategoryItem category) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		category.setId((Long) session.save(category));
		return category;
	}

	@Override
	public List<CategoryItem> fetchCategoryItems(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate sdbCodePredicate = cb.like(cb.lower(root.get("standardBookCode")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate, sdbCodePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		if (search.getPageSize() != null && search.getPageNo() != null && search.getPageNo() > 0) {
			query.setMaxResults(search.getPageSize());
			query.setFirstResult((search.getPageNo() - 1) * search.getPageSize());
		}
		List<CategoryItem> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchCount(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		List<CategoryItem> results = query.getResultList();
		return results != null ? results.size() : 0;
	}

	@Override
	public CategoryItem fetchCategoryItemById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		cr.select(root).where(cb.equal(root.get("id"), id));
		Query<CategoryItem> query = session.createQuery(cr);
		CategoryItem result = query.getSingleResult();
		return result;
	}

	@Override
	public Long getIdByNameOrSave(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getCode() != null && !search.getCode().trim().isEmpty())
			orPredicates.add(cb.like(cb.lower(root.get("code")), search.getCode().toLowerCase()));
		if (search.getStandardBookCode() != null && !search.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.like(cb.lower(root.get("standardBookCode")), search.getStandardBookCode().toLowerCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(andPredicate, orPredicate);
		cr.select(root).where(combinedPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		List<CategoryItem> results = query.getResultList();
		if (results != null && results.size() > 0)
			return results.get(0).getId();
		CategoryItem item = new CategoryItem(null, search.getCode(), search.getStandardBookCode(), search.getName(),
				false, true, new Date(), search.getUserId(), new Date(), search.getUserId(), 1);
		Long id = (Long) session.save(item);
		session.flush();
		return id;
	}

	@Override
	public Long getIdByNameUpdateOrSave(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getCode() != null && !search.getCode().trim().isEmpty())
			orPredicates.add(cb.like(cb.lower(root.get("code")), search.getCode().toLowerCase()));
		if (search.getStandardBookCode() != null && !search.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.like(cb.lower(root.get("standardBookCode")), search.getStandardBookCode().toLowerCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(andPredicate, orPredicate);
		cr.select(root).where(combinedPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		List<CategoryItem> results = query.getResultList();
		if (results != null && results.size() > 0) {
			CategoryItem cat = results.get(0);
			boolean hasChanges = false;
			if (search.getName() != null && !search.getName().equals(cat.getName())) {
				hasChanges = true;
				cat.setName(search.getName());
			}
			if (hasChanges) {
				cat.setModifiedOn(new Date());
				cat.setModifiedBy(search.getUserId());
				session.update(cat);
			}
			return cat.getId();
		}
		CategoryItem item = new CategoryItem(null, search.getCode(), search.getStandardBookCode(), search.getName(),
				false, true, new Date(), search.getUserId(), new Date(), search.getUserId(), 1);
		Long id = (Long) session.save(item);
		session.flush();
		return id;
	}

	@Override
	public Boolean updateCategoryItem(CategoryItem categoryObj) {

		if (categoryObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), categoryObj.getCompanyId()));
		andPredicates.add(cb.equal(root.get("name"), categoryObj.getName()));
		if (categoryObj.getCode() != null && !categoryObj.getCode().trim().isEmpty())
			orPredicates.add(cb.like(cb.lower(root.get("code")), categoryObj.getCode().toLowerCase()));
		if (categoryObj.getStandardBookCode() != null && !categoryObj.getStandardBookCode().trim().isEmpty())
			orPredicates.add(
					cb.like(cb.lower(root.get("standardBookCode")), categoryObj.getStandardBookCode().toLowerCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate idPredicate = cb.notEqual(root.get("id"), categoryObj.getId());
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(andPredicate, orPredicate, idPredicate);
		cr.select(root).where(combinedPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		List<CategoryItem> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(categoryObj);
			return true;
		}
		return false;
	}

	@Override
	public void forceUpdateCategoryItem(CategoryItem categoryObj) {

		Session session = entityManager.unwrap(Session.class);
		session.update(categoryObj);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public String fetchCodeByStandardBookCode(String standardBookCode, Integer companyId) {

		if (standardBookCode == null || standardBookCode.isEmpty())
			return null;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("standardBookCode"), standardBookCode));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		CategoryItem result = query.getResultList() != null && query.getResultList().size() > 0
				? query.getResultList().get(0)
				: null;
		return result != null ? result.getCode() : null;
	}

	@Override
	public List<CategoryContractorMapping> fetchCategoriesByContractorId(Long contractorId) {

		if (contractorId == null)
			return null;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryContractorMapping> cr = cb.createQuery(CategoryContractorMapping.class);
		Root<CategoryContractorMapping> root = cr.from(CategoryContractorMapping.class);
		cr.select(root).where(cb.equal(root.get("contractorId"), contractorId));
		cr.select(root).where(cb.equal(root.get("isActive"), true));
		Query<CategoryContractorMapping> query = session.createQuery(cr);
		return query.getResultList() != null ? query.getResultList() : null;
	}

}
