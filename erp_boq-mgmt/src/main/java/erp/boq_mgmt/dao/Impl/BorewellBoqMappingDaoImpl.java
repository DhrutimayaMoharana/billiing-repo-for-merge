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

import erp.boq_mgmt.dao.BorewellBoqMappingDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.BorewellBoqMapping;
import erp.boq_mgmt.entity.BorewellBoqTransac;
import erp.boq_mgmt.entity.GenericBoqMappingHighway;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.WorkorderType;

@Repository
public class BorewellBoqMappingDaoImpl implements BorewellBoqMappingDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public BorewellBoqMapping forceSaveCategoryBoq(BorewellBoqMapping bcm) {
		Session session = entityManager.unwrap(Session.class);
		bcm.setId((Long) session.save(bcm));
		return bcm;
	}

	@Override
	public Long mapCategoryBoq(BorewellBoqMapping bcm) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BorewellBoqMapping> cr = cb.createQuery(BorewellBoqMapping.class);
		Root<BorewellBoqMapping> root = cr.from(BorewellBoqMapping.class);
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
		Query<BorewellBoqMapping> query = session.createQuery(cr);
		List<BorewellBoqMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			Long id = (Long) session.save(bcm);
			session.detach(bcm);
			return id;
		}
		return null;
	}

	@Override
	public void forceUpdateCategoryBoq(BorewellBoqMapping bcm) {
		Session session = entityManager.unwrap(Session.class);
		session.update(bcm);

	}

	@Override
	public void forceUpdateAfterDetachCategoryBoq(BorewellBoqMapping bcm) {
		Session session = entityManager.unwrap(Session.class);
		session.detach(session.get(BorewellBoqMapping.class, bcm.getId()));
		session.update(bcm);
		session.flush();
		session.clear();

	}

	@Override
	public void saveBoqCategoryTransac(BorewellBoqTransac bct) {
		Session session = entityManager.unwrap(Session.class);
		session.save(bct);

	}

	@Override
	public BorewellBoqMapping fetchCategoryBoqById(Long catBoqId) {
		Session session = entityManager.unwrap(Session.class);
		BorewellBoqMapping cbq = (BorewellBoqMapping) session.get(BorewellBoqMapping.class, catBoqId);
		return cbq;
	}

	@Override
	public List<BorewellBoqMapping> fetchCategoriesBoqs(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BorewellBoqMapping> cr = cb.createQuery(BorewellBoqMapping.class);
		Root<BorewellBoqMapping> root = cr.from(BorewellBoqMapping.class);
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
		Query<BorewellBoqMapping> query = session.createQuery(cr);
		List<BorewellBoqMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Boolean updateCategoryBoqMapping(BorewellBoqMapping bcmObj) {
		if (bcmObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BorewellBoqMapping> cr = cb.createQuery(BorewellBoqMapping.class);
		Root<BorewellBoqMapping> root = cr.from(BorewellBoqMapping.class);
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
		Query<BorewellBoqMapping> query = session.createQuery(cr);
		List<BorewellBoqMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.detach(session.get(HighwayBoqMapping.class, bcmObj.getId()));
			session.update(bcmObj);
			return true;
		}
		return false;
	}

	@Override
	public List<BorewellBoqMapping> fetchBorewellBoqByIdList(Set<Long> ids) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BorewellBoqMapping> cr = cb.createQuery(BorewellBoqMapping.class);
		Root<BorewellBoqMapping> root = cr.from(BorewellBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (ids != null) {
			In<Long> inClause = cb.in(root.get("id"));
			for (Long id : ids) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<BorewellBoqMapping> query = session.createQuery(cr);
		List<BorewellBoqMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<BorewellBoqMapping> fetchBorewellBoqMappingGroupByBoqs(Long siteId) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BorewellBoqMapping> cr = cb.createQuery(BorewellBoqMapping.class);
		Root<BorewellBoqMapping> root = cr.from(BorewellBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).orderBy(cb.asc(root.get("description"))).groupBy(root.get("boq").get("id")).select(root)
				.where(andPredicate);
		Query<BorewellBoqMapping> query = session.createQuery(cr);
		List<BorewellBoqMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<BorewellBoqMapping> fetchByBoqAndSite(Long boqId, Long siteId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BorewellBoqMapping> cr = cb.createQuery(BorewellBoqMapping.class);
		Root<BorewellBoqMapping> root = cr.from(BorewellBoqMapping.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BorewellBoqMapping> query = session.createQuery(cr);
		List<BorewellBoqMapping> results = query.getResultList();
		return results;
	}

	@Override
	public GenericBoqMappingHighway forceSaveCategoryBoq(GenericBoqMappingHighway bcm) {
		Session session = entityManager.unwrap(Session.class);
		bcm.setId((Long) session.save(bcm));
		return bcm;
	}

	@Override
	public Long mapCategoryBoq(GenericBoqMappingHighway bcm) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericBoqMappingHighway> cr = cb.createQuery(GenericBoqMappingHighway.class);
		Root<GenericBoqMappingHighway> root = cr.from(GenericBoqMappingHighway.class);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(
				cb.equal(root.get("category").get("id"), bcm.getCategory() != null ? bcm.getCategory().getId() : null));
		andPredicates.add(cb.equal(root.get("workorderTypeId"),
				bcm.getWorkorderTypeId() != null ? bcm.getWorkorderTypeId() : null));
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
		Query<GenericBoqMappingHighway> query = session.createQuery(cr);
		List<GenericBoqMappingHighway> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			Long id = (Long) session.save(bcm);
			session.detach(bcm);
			return id;
		}
		return null;
	}

	@Override
	public void forceUpdateCategoryBoq(GenericBoqMappingHighway bcm) {
		Session session = entityManager.unwrap(Session.class);
		session.update(bcm);

	}

	@Override
	public void forceUpdateAfterDetachCategoryBoq(GenericBoqMappingHighway bcm) {
		Session session = entityManager.unwrap(Session.class);
		session.detach(session.get(GenericBoqMappingHighway.class, bcm.getId()));
		session.update(bcm);
		session.flush();
		session.clear();

	}

	@Override
	public GenericBoqMappingHighway fetchGenricCategoryBoqById(Long catBoqId, Integer woTypeId) {
//		Session session = entityManager.unwrap(Session.class);
//		GenericBoqMappingHighway cbq = (GenericBoqMappingHighway) session.get(GenericBoqMappingHighway.class, catBoqId ,woTypeId );
//		return cbq;

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericBoqMappingHighway> cr = cb.createQuery(GenericBoqMappingHighway.class);
		Root<GenericBoqMappingHighway> root = cr.from(GenericBoqMappingHighway.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("id"), catBoqId));
		if (woTypeId != null) {
			andPredicates.add(cb.equal(root.get("workorderTypeId"), woTypeId));
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericBoqMappingHighway> query = session.createQuery(cr);
		List<GenericBoqMappingHighway> results = query.getResultList();

		if (results != null && results.size() > 0) {
			results.get(0);
		}
		return null;

	}

	@Override
	public List<GenericBoqMappingHighway> fetchGenricCategoriesBoqs(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericBoqMappingHighway> cr = cb.createQuery(GenericBoqMappingHighway.class);
		Root<GenericBoqMappingHighway> root = cr.from(GenericBoqMappingHighway.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getTypeId() != null)
			andPredicates.add(cb.equal(root.get("workorderTypeId"), search.getTypeId()));
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
		Query<GenericBoqMappingHighway> query = session.createQuery(cr);
		List<GenericBoqMappingHighway> results = query.getResultList();
		return results;
	}

	@Override
	public Boolean updateCategoryBoqMapping(GenericBoqMappingHighway bcmObj) {
		if (bcmObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericBoqMappingHighway> cr = cb.createQuery(GenericBoqMappingHighway.class);
		Root<GenericBoqMappingHighway> root = cr.from(GenericBoqMappingHighway.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates
				.add(cb.equal(root.get("boq").get("id"), bcmObj.getBoq() != null ? bcmObj.getBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("workorderTypeId"),
				bcmObj.getWorkorderTypeId() != null ? bcmObj.getWorkorderTypeId() : null));
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
		Query<GenericBoqMappingHighway> query = session.createQuery(cr);
		List<GenericBoqMappingHighway> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.detach(session.get(HighwayBoqMapping.class, bcmObj.getId()));
			session.update(bcmObj);
			return true;
		}
		return false;
	}

	@Override
	public List<GenericBoqMappingHighway> fetchGenricBoqByIdList(Set<Long> ids) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericBoqMappingHighway> cr = cb.createQuery(GenericBoqMappingHighway.class);
		Root<GenericBoqMappingHighway> root = cr.from(GenericBoqMappingHighway.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (ids != null) {
			In<Long> inClause = cb.in(root.get("id"));
			for (Long id : ids) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<GenericBoqMappingHighway> query = session.createQuery(cr);
		List<GenericBoqMappingHighway> results = query.getResultList();
		return results;
	}

	@Override
	public List<GenericBoqMappingHighway> fetchGenricBoqMappingGroupByBoqs(Long siteId) {
		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericBoqMappingHighway> cr = cb.createQuery(GenericBoqMappingHighway.class);
		Root<GenericBoqMappingHighway> root = cr.from(GenericBoqMappingHighway.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).orderBy(cb.asc(root.get("description"))).groupBy(root.get("boq").get("id")).select(root)
				.where(andPredicate);
		Query<GenericBoqMappingHighway> query = session.createQuery(cr);
		List<GenericBoqMappingHighway> results = query.getResultList();
		return results;
	}

	@Override
	public List<GenericBoqMappingHighway> fetchByBoqAndSiteV1(Long boqId, Long siteId, Integer workOrderTypeId) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<GenericBoqMappingHighway> cr = cb.createQuery(GenericBoqMappingHighway.class);
		Root<GenericBoqMappingHighway> root = cr.from(GenericBoqMappingHighway.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		andPredicates.add(cb.equal(root.get("workorderTypeId"), workOrderTypeId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<GenericBoqMappingHighway> query = session.createQuery(cr);
		List<GenericBoqMappingHighway> results = query.getResultList();
		return results;
	}

	@Override
	public List<WorkorderType> fetchWorkorderTypes(List<Integer> ids) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderType> cr = cb.createQuery(WorkorderType.class);
		Root<WorkorderType> root = cr.from(WorkorderType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		In<Integer> inClause = cb.in(root.get("id"));
		for (Integer uId : ids) {
			inClause.value(uId);
		}
		andPredicates.add(inClause);
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderType> query = session.createQuery(cr);
		List<WorkorderType> results = query.getResultList();
		return results;
	}

}
