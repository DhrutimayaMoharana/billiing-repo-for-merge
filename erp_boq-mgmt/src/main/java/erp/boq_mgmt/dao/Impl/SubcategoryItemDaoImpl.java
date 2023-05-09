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

import erp.boq_mgmt.dao.SubcategoryItemDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.SubcategoryItem;

@Repository
public class SubcategoryItemDaoImpl implements SubcategoryItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveSubcategoryItem(SubcategoryItem subcat) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SubcategoryItem> cr = cb.createQuery(SubcategoryItem.class);
		Root<SubcategoryItem> root = cr.from(SubcategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), subcat.getCompanyId()));
		if (subcat.getCode() != null && !subcat.getCode().trim().isEmpty())
			orPredicates.add(cb.like(cb.lower(root.get("code")), subcat.getCode().toLowerCase()));
		if (subcat.getStandardBookCode() != null && !subcat.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.like(cb.lower(root.get("standardBookCode")), subcat.getStandardBookCode().toLowerCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(andPredicate, orPredicate);
		cr.select(root).where(combinedPredicate);
		Query<SubcategoryItem> query = session.createQuery(cr);
		List<SubcategoryItem> results = query.getResultList();
		Long id = null;
		if (results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(subcat);
		}
		return id;
	}

	@Override
	public SubcategoryItem fetchSubcategoryItemByIdOrName(Long id, String name, Integer companyId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SubcategoryItem> cr = cb.createQuery(SubcategoryItem.class);
		Root<SubcategoryItem> root = cr.from(SubcategoryItem.class);
		List<Predicate> cnPredicates = new ArrayList<Predicate>();
		cnPredicates.add(cb.equal(root.get("isActive"), true));
		cnPredicates.add(cb.equal(root.get("companyId"), companyId));
		if (id != null)
			cnPredicates.add(cb.equal(root.get("id"), id));
		if (name != null)
			cnPredicates.add(cb.equal(root.get("name"), name));
		Predicate andPredicate = cb.and(cnPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<SubcategoryItem> query = session.createQuery(cr);
		List<SubcategoryItem> results = query.getResultList();
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	public Long getIdByNameOrSave(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SubcategoryItem> cr = cb.createQuery(SubcategoryItem.class);
		Root<SubcategoryItem> root = cr.from(SubcategoryItem.class);
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
		Query<SubcategoryItem> query = session.createQuery(cr);
		List<SubcategoryItem> results = query.getResultList();
		if (results != null && results.size() > 0)
			return results.get(0).getId();
		SubcategoryItem item = new SubcategoryItem(null, search.getCode(), search.getStandardBookCode(),
				search.getName(), new Date(), search.getUserId(), new Date(), search.getUserId(), true,
				search.getCompanyId());
		Long id = (Long) session.save(item);
		session.flush();
		return id;
	}

	public Long getIdByNameUpdateOrSave(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SubcategoryItem> cr = cb.createQuery(SubcategoryItem.class);
		Root<SubcategoryItem> root = cr.from(SubcategoryItem.class);
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
		Query<SubcategoryItem> query = session.createQuery(cr);
		List<SubcategoryItem> results = query.getResultList();
		if (results != null && results.size() > 0) {
			SubcategoryItem cat = results.get(0);
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
		SubcategoryItem item = new SubcategoryItem(null, search.getCode(), search.getStandardBookCode(),
				search.getName(), new Date(), search.getUserId(), new Date(), search.getUserId(), true,
				search.getCompanyId());
		Long id = (Long) session.save(item);
		session.flush();
		return id;
	}

	@Override
	public Integer fetchCount(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SubcategoryItem> cr = cb.createQuery(SubcategoryItem.class);
		Root<SubcategoryItem> root = cr.from(SubcategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate sdbCodePredicate = cb.like(cb.lower(root.get("standardBookCode")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate codePredicate = cb.like(cb.lower(root.get("code")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate, sdbCodePredicate, codePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<SubcategoryItem> query = session.createQuery(cr);
		List<SubcategoryItem> results = query.getResultList();
		return results != null ? results.size() : 0;
	}

	@Override
	public Boolean updateSubcategoryItem(SubcategoryItem subcat) {

		if (subcat == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SubcategoryItem> cr = cb.createQuery(SubcategoryItem.class);
		Root<SubcategoryItem> root = cr.from(SubcategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), subcat.getCompanyId()));
		if (subcat.getCode() != null && !subcat.getCode().trim().isEmpty())
			orPredicates.add(cb.like(cb.lower(root.get("code")), subcat.getCode().toLowerCase()));
		if (subcat.getStandardBookCode() != null && !subcat.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.like(cb.lower(root.get("standardBookCode")), subcat.getStandardBookCode().toLowerCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate idPredicate = cb.notEqual(root.get("id"), subcat.getId());
		Predicate combinedPredicate = cb.and(andPredicate, orPredicate, idPredicate);
		cr.select(root).where(combinedPredicate);
		Query<SubcategoryItem> query = session.createQuery(cr);
		List<SubcategoryItem> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(subcat);
			return true;
		}
		return false;
	}

	@Override
	public List<SubcategoryItem> fetchSubcategoryItems(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SubcategoryItem> cr = cb.createQuery(SubcategoryItem.class);
		Root<SubcategoryItem> root = cr.from(SubcategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate sdbCodePredicate = cb.like(cb.lower(root.get("standardBookCode")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate codePredicate = cb.like(cb.lower(root.get("code")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate, sdbCodePredicate, codePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<SubcategoryItem> query = session.createQuery(cr);
		if (search.getPageSize() != null && search.getPageNo() != null) {
			query.setMaxResults(search.getPageSize());
			query.setFirstResult((search.getPageNo() - 1) * search.getPageSize());
		}
		List<SubcategoryItem> results = query.getResultList();
		return results;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public String fetchCodeByStandardBookCode(String standardBookCode, Integer companyId) {

		if (standardBookCode == null || standardBookCode.trim().isEmpty())
			return null;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<SubcategoryItem> cr = cb.createQuery(SubcategoryItem.class);
		Root<SubcategoryItem> root = cr.from(SubcategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("standardBookCode"), standardBookCode));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<SubcategoryItem> query = session.createQuery(cr);
		SubcategoryItem result = query.getResultList() != null && query.getResultList().size() > 0
				? query.getResultList().get(0)
				: null;
		return result != null ? result.getCode() : null;
	}

	@Override
	public SubcategoryItem forceSaveSubcategoryItem(SubcategoryItem item) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		item.setId((Long) session.save(item));
		return item;
	}

	@Override
	public void forceUpdateSubcategoryItem(SubcategoryItem boq) {

		Session session = entityManager.unwrap(Session.class);
		session.update(boq);
	}

}
