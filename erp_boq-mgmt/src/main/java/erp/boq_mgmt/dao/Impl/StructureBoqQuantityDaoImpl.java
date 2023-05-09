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

import erp.boq_mgmt.dao.StructureBoqQuantityDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.entity.StructureBoqQuantityTransacs;

@Repository
public class StructureBoqQuantityDaoImpl implements StructureBoqQuantityDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("structure", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("structure").get("id"),
				sbqObj.getStructure() != null ? sbqObj.getStructure().getId() : null));
		andPredicates
				.add(cb.equal(root.get("boq").get("id"), sbqObj.getBoq() != null ? sbqObj.getBoq().getId() : null));
		if (sbqObj.getCategory() != null)
			andPredicates.add(cb.equal(root.get("category").get("id"),
					sbqObj.getCategory() != null ? sbqObj.getCategory().getId() : null));
		if (sbqObj.getSubcategory() != null)
			andPredicates.add(cb.equal(root.get("subcategory").get("id"),
					sbqObj.getSubcategory() != null ? sbqObj.getSubcategory().getId() : null));
		andPredicates.add(cb.equal(root.get("siteId"), sbqObj.getSiteId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), sbqObj.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		Long id = null;
		if (results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(sbqObj);
			session.detach(sbqObj);
		}
		return id;
	}

	@Override
	public Long saveStructureBoqQuantityTransac(StructureBoqQuantityTransacs sbqTransac) {

		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(sbqTransac);

	}

	@Override
	public StructureBoqQuantityMapping fetchSbqById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		StructureBoqQuantityMapping cbq = (StructureBoqQuantityMapping) session.get(StructureBoqQuantityMapping.class,
				id);
		return cbq;
	}

	@Override
	public Boolean updateStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj) {

		if (sbqObj == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("structure", JoinType.LEFT);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("structure").get("id"),
				sbqObj.getStructure() != null ? sbqObj.getStructure().getId() : null));
		andPredicates
				.add(cb.equal(root.get("boq").get("id"), sbqObj.getBoq() != null ? sbqObj.getBoq().getId() : null));
		andPredicates.add(cb.equal(root.get("category").get("id"),
				sbqObj.getCategory() != null ? sbqObj.getCategory().getId() : null));
		andPredicates.add(cb.equal(root.get("subcategory").get("id"),
				sbqObj.getSubcategory() != null ? sbqObj.getSubcategory().getId() : null));
		andPredicates.add(cb.equal(root.get("siteId"), sbqObj.getSiteId()));
		andPredicates.add(cb.equal(cb.upper(root.get("description")), sbqObj.getDescription().toUpperCase()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), sbqObj.getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(sbqObj);
			return true;
		}
		return false;
	}

	@Override
	public List<StructureBoqQuantityMapping> fetchStructureWiseBoq(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("structure", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("structure").get("id"), search.getStructureId()));
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureBoqQuantityMapping> fetchAllStructureBoqs(SearchDTO search) {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("structure", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureBoqQuantityMapping> fetchStructureBoqQuantityMappingBySearch(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("structure", JoinType.LEFT);
		root.fetch("category", JoinType.LEFT);
		root.fetch("subcategory", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		if (search.getStructureId() != null)
			andPredicates.add(cb.equal(root.get("structure").get("id"), search.getStructureId()));
		if (search.getBoqId() != null)
			andPredicates.add(cb.equal(root.get("boq").get("id"), search.getBoqId()));
		if (search.getDescription() != null)
			andPredicates.add(cb.equal(root.get("description"), search.getDescription()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureBoqQuantityMapping> fetchSbqByIdList(Set<Long> idsArr) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		root.fetch("structure", JoinType.LEFT);
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
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public StructureBoqQuantityMapping forceSaveStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj) {

		Session session = entityManager.unwrap(Session.class);
		sbqObj.setId((Long) session.save(sbqObj));
		return sbqObj;
	}

	@Override
	public void forceUpdateStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj) {

		Session session = entityManager.unwrap(Session.class);
		session.update(sbqObj);

	}

	@Override
	public void forceUpdateAfterDetachStructureBoqQuantityMapping(StructureBoqQuantityMapping sbqObj) {

		Session session = entityManager.unwrap(Session.class);
		session.detach(session.get(StructureBoqQuantityMapping.class, sbqObj.getId()));
		session.update(sbqObj);
		session.flush();
		session.clear();

	}

	@Override
	public List<StructureBoqQuantityMapping> fetchStructureBoqMappingGroupByBoqs(Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).orderBy(cb.asc(root.get("description"))).groupBy(root.get("boq").get("id")).select(root)
				.where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		return results;
	}
	
	@Override
	public List<StructureBoqQuantityMapping> fetchStructureBoqMappingByBoqIdStructureIdAndTypeId(Long boqId,
			Long structureId, Long structureTypeId, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		if (structureId != null)
			andPredicates.add(cb.equal(root.get("structure").get("id"), structureId));
		if (structureTypeId != null)
			andPredicates.add(cb.equal(root.get("structure").get("type").get("id"), structureTypeId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureBoqQuantityMapping> fetchStructureBoqMappingByBoqIdStructureIdAndTypeIdGroupByStructure(Long boqId,
			Long structureId, Long structureTypeId, Long siteId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureBoqQuantityMapping> cr = cb.createQuery(StructureBoqQuantityMapping.class);
		Root<StructureBoqQuantityMapping> root = cr.from(StructureBoqQuantityMapping.class);
		root.fetch("boq", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("boq").get("id"), boqId));
		if (structureId != null)
			andPredicates.add(cb.equal(root.get("structure").get("id"), structureId));
		if (structureTypeId != null)
			andPredicates.add(cb.equal(root.get("structure").get("type").get("id"), structureTypeId));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate).groupBy(root.get("structure").get("id"));
		Query<StructureBoqQuantityMapping> query = session.createQuery(cr);
		List<StructureBoqQuantityMapping> results = query.getResultList();
		return results;
	}

}
