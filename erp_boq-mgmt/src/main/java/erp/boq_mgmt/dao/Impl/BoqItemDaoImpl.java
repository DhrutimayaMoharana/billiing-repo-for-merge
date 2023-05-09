package erp.boq_mgmt.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.BoqItemDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.CategoryItem;
import erp.boq_mgmt.entity.SubcategoryItem;
import erp.boq_mgmt.entity.Unit;

@Repository
public class BoqItemDaoImpl implements BoqItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Integer fetchCount(SearchDTO search) {

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
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(codePredicate, namePredicate, sdbCodePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		return results != null ? results.size() : 0;
	}

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
		if (search.getCategoryId() != null)
			andPredicates.add(cb.equal(root.get("category").get("id"), search.getCategoryId()));
		if (search != null && search.getSearchField() != null) {
			Predicate codePredicate = cb.like(cb.lower(root.get("code")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate sdbCodePredicate = cb.like(cb.lower(root.get("standardBookCode")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(codePredicate, namePredicate, sdbCodePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveBoqItem(BoqItem boq) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), boq.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (boq.getStandardBookCode() != null && !boq.getStandardBookCode().trim().isEmpty())
			orPredicates.add(cb.equal(cb.upper(root.get("standardBookCode")), boq.getStandardBookCode().toUpperCase()));
		if (boq.getCode() != null && !boq.getCode().trim().isEmpty())
			orPredicates.add(cb.equal(cb.upper(root.get("code")), boq.getCode().toUpperCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(orPredicate, andPredicate);
		cr.select(root).where(combinedPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			Long id = (Long) session.save(boq);
			session.flush();
			return id;
		}
		return results.get(0).getId();
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
	public Boolean updateBoqItem(BoqItem boq) {

		if (boq == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), boq.getId()));
		andPredicates.add(cb.equal(root.get("companyId"), boq.getCompanyId()));
		if (boq.getStandardBookCode() != null && !boq.getStandardBookCode().trim().isEmpty())
			orPredicates.add(cb.equal(cb.upper(root.get("standardBookCode")), boq.getStandardBookCode().toUpperCase()));
		if (boq.getCode() != null && !boq.getCode().trim().isEmpty())
			orPredicates.add(cb.equal(cb.upper(root.get("code")), boq.getCode().toUpperCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(orPredicate, andPredicate);
		cr.select(root).where(combinedPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(boq);
			return true;
		}
		return false;
	}

	@Override
	public void forceUpdateBoqItem(BoqItem boq) {

		Session session = entityManager.unwrap(Session.class);
		session.update(boq);
	}

	@Override
	public Long getIdByCodeOrSave(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getStandardBookCode() != null && !search.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.equal(cb.upper(root.get("standardBookCode")), search.getStandardBookCode().toUpperCase()));
		if (search.getCode() != null && !search.getCode().trim().isEmpty())
			orPredicates.add(cb.equal(cb.upper(root.get("code")), search.getCode().toUpperCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(orPredicate, andPredicate);
		cr.select(root).where(combinedPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		if (results != null && results.size() > 0)
			return results.get(0).getId();
		BoqItem item = new BoqItem(null, search.getCode(), search.getStandardBookCode(), search.getName(),
				new Unit(search.getUnitId()), new CategoryItem(search.getCategoryId()),
				new SubcategoryItem(search.getSubcategoryId()), true, new Date(), search.getUserId(), new Date(),
				search.getUserId(), search.getCompanyId());
		Long id = (Long) session.save(item);
		session.flush();
		return id;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public String fetchCodeByStandardBookCode(String standardBookCode, Integer companyId) {

		if (standardBookCode == null || standardBookCode.isEmpty())
			return null;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("standardBookCode"), standardBookCode));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		BoqItem result = query.getResultList() != null && query.getResultList().size() > 0
				? query.getResultList().get(0)
				: null;
		return result != null ? result.getCode() : null;
	}

	@Override
	public Long getIdByCode(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getStandardBookCode() != null && !search.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.equal(cb.upper(root.get("standardBookCode")), search.getStandardBookCode().toUpperCase()));
		if (search.getCode() != null && !search.getCode().trim().isEmpty())
			orPredicates.add(cb.equal(cb.upper(root.get("code")), search.getCode().toUpperCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(orPredicate, andPredicate);
		cr.select(root).where(combinedPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		if (results != null && results.size() > 0)
			return results.get(0).getId();
		return null;
	}

	@Override
	public Long getIdByCodeUpdateOrSave(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getStandardBookCode() != null && !search.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.equal(cb.upper(root.get("standardBookCode")), search.getStandardBookCode().toUpperCase()));
		if (search.getCode() != null && !search.getCode().trim().isEmpty())
			orPredicates.add(cb.equal(cb.upper(root.get("code")), search.getCode().toUpperCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(orPredicate, andPredicate);
		cr.select(root).where(combinedPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		if (results != null && results.size() > 0) {
			BoqItem boq = results.get(0);
			boolean hasChanges = false;
			if (search.getName() != null && !search.getName().equals(boq.getName())) {
				hasChanges = true;
				boq.setName(search.getName());
			}
			if (hasChanges) {
				boq.setModifiedOn(new Date());
				boq.setModifiedBy(search.getUserId());
				session.update(boq);
			}
			return boq.getId();
		}
		BoqItem item = new BoqItem(null, search.getCode(), search.getStandardBookCode(), search.getName(),
				new Unit(search.getUnitId()), new CategoryItem(search.getCategoryId()),
				new SubcategoryItem(search.getSubcategoryId()), true, new Date(), search.getUserId(), new Date(),
				search.getUserId(), search.getCompanyId());
		return (Long) session.save(item);
	}

	@Override
	public Long getIdByCodeUpdate(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BoqItem> cr = cb.createQuery(BoqItem.class);
		Root<BoqItem> root = cr.from(BoqItem.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getStandardBookCode() != null && !search.getStandardBookCode().trim().isEmpty())
			orPredicates
					.add(cb.equal(cb.upper(root.get("standardBookCode")), search.getStandardBookCode().toUpperCase()));
		if (search.getCode() != null && !search.getCode().trim().isEmpty())
			orPredicates.add(cb.equal(cb.upper(root.get("code")), search.getCode().toUpperCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		Predicate combinedPredicate = cb.and(orPredicate, andPredicate);
		cr.select(root).where(combinedPredicate);
		Query<BoqItem> query = session.createQuery(cr);
		List<BoqItem> results = query.getResultList();
		if (results != null && results.size() > 0) {
			BoqItem boq = results.get(0);
			boolean hasChanges = false;
			if (search.getName() != null && !search.getName().equals(boq.getName())) {
				hasChanges = true;
				boq.setName(search.getName());
			}
			if (hasChanges) {
				boq.setModifiedOn(new Date());
				boq.setModifiedBy(search.getUserId());
				session.update(boq);
			}
			return boq.getId();
		}
		return null;
	}

	@Override
	public BoqItem forceSaveBoqItem(BoqItem item) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		item.setId((Long) session.save(item));
		return item;
	}

}
