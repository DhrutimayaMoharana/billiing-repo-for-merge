package erp.boq_mgmt.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import erp.boq_mgmt.dao.HighwayBoqMapDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.HighwayBoqTransacs;

@Repository
public class HighwayBoqMapDaoImpl implements HighwayBoqMapDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long mapCategoryBoq(HighwayBoqMapping bcm) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(
				cb.equal(root.get("category").get("id"), bcm.getCategory() != null ? bcm.getCategory().getId() : null));
		andPredicates.add(cb.equal(root.get("boq").get("id"), bcm.getBoq() != null ? bcm.getBoq().getId() : null));
		if (bcm.getSubcategory() != null)
			andPredicates.add(cb.equal(root.get("subcategory").get("id"),
					bcm.getSubcategory() != null ? bcm.getSubcategory().getId() : null));
		andPredicates.add(cb.equal(root.get("siteId"), bcm.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (bcm.getDescription() != null)
			andPredicates.add(cb.equal(root.get("description"), bcm.getDescription()));
		andPredicates.add(cb.equal(root.get("companyId"), bcm.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			Long id = (Long) session.save(bcm);
			session.detach(bcm);
			return id;
		}
		return null;
	}

	@Override
	public void saveBoqCategoryTransac(HighwayBoqTransacs bct) {

		Session session = entityManager.unwrap(Session.class);
		session.save(bct);
	}

	@Override
	public HighwayBoqMapping fetchCategoryBoqById(Long catBoqId) {

		Session session = entityManager.unwrap(Session.class);
		HighwayBoqMapping cbq = (HighwayBoqMapping) session.get(HighwayBoqMapping.class, catBoqId);
		return cbq;
	}

	@Override
	public List<HighwayBoqMapping> fetchCategoriesBoqs(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getCategoryId() != null)
			andPredicates.add(cb.equal(root.get("category").get("id"), search.getCategoryId()));
		if (search.getSubcategoryId() != null)
			andPredicates.add(cb.equal(root.get("subcategory").get("id"), search.getSubcategoryId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search.getDescription() != null)
			andPredicates.add(cb.equal(root.get("description"), search.getDescription()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		cr.orderBy(cb.desc(root.get("modifiedOn")));
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Boolean updateCategoryBoqMapping(HighwayBoqMapping bcmObj) {

		if (bcmObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates
				.add(cb.equal(root.get("boq").get("id"), bcmObj.getBoq() != null ? bcmObj.getBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("category").get("id"),
				bcmObj.getCategory() != null ? bcmObj.getCategory().getId() : null));
		if (bcmObj.getSubcategory() != null)
			andPredicates.add(cb.equal(root.get("subcategory").get("id"),
					bcmObj.getSubcategory() != null ? bcmObj.getSubcategory().getId() : null));
		andPredicates.add(cb.equal(root.get("siteId"), bcmObj.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), bcmObj.getCompanyId()));
		andPredicates.add(cb.notEqual(root.get("id"), bcmObj.getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.detach(session.get(HighwayBoqMapping.class, bcmObj.getId()));
			session.update(bcmObj);
			return true;
		}
		return false;
	}

	@Override
	public List<HighwayBoqMapping> fetchHighwayBoqByIdList(Set<Long> idsArr) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (idsArr != null) {
			In<Long> inClause = cb.in(root.get("id"));
			for (Long id : idsArr) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		return results;
	}

	@Override
	public void forceUpdateCategoryBoq(HighwayBoqMapping bcm) {

		Session session = entityManager.unwrap(Session.class);
		session.update(bcm);

	}

	@Override
	public void forceUpdateAfterDetachCategoryBoq(HighwayBoqMapping bcm) {

		Session session = entityManager.unwrap(Session.class);
		session.detach(session.get(HighwayBoqMapping.class, bcm.getId()));
		session.update(bcm);
		session.flush();
		session.clear();

	}

	@Override
	public HighwayBoqMapping forceSaveCategoryBoq(HighwayBoqMapping bcm) {

		Session session = entityManager.unwrap(Session.class);
		bcm.setId((Long) session.save(bcm));
		return bcm;
	}

	@Override
	public List<HighwayBoqMapping> fetchHighwayBoqMappingGroupByBoqs(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).orderBy(cb.asc(root.get("description"))).groupBy(root.get("boq").get("id")).select(root)
				.where(andPredicate);
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<HighwayBoqMapping> fetchByBoqAndSite(Long boqId, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<HighwayBoqMapping> cr = cb.createQuery(HighwayBoqMapping.class);
		Root<HighwayBoqMapping> root = cr.from(HighwayBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<HighwayBoqMapping> query = session.createQuery(cr);
		List<HighwayBoqMapping> results = query.getResultList();
		return results;
	}

}
