package erp.boq_mgmt.dao.Impl;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.StructureDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Structure;
import erp.boq_mgmt.entity.StructureV2;

@Repository
public class StructureDaoImpl implements StructureDao {

	@Autowired
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Integer fetchCount(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("startChainage", JoinType.LEFT);
		root.fetch("endChainage", JoinType.LEFT);
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search != null && search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if (search != null && search.getSearchField() != null) {
			Predicate codePredicate = cb.like(cb.lower(root.get("code")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(codePredicate, namePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Structure> query = session.createQuery(cr);
		List<Structure> results = query.getResultList();
		return results != null ? results.size() : 0;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<Structure> fetchStructures(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("startChainage", JoinType.LEFT);
		root.fetch("endChainage", JoinType.LEFT);
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search != null && search.getTypeId() != null) {
			andPredicates.add(cb.equal(root.get("type").get("id"), search.getTypeId()));
		}
		if (search != null && search.getCompanyId() != null)
			andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		if (search != null && search.getFromChainageId() != null)
			andPredicates.add(cb.equal(root.get("startChainage").get("id"), search.getFromChainageId()));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Structure> query = session.createQuery(cr);
		List<Structure> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveStructure(Structure structure) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if (structure != null && structure.getName() != null)
			orPredicates.add(cb.equal(cb.lower(root.get("name")), structure.getName().toLowerCase()));
		Predicate activePredicate = cb.equal(root.get("isActive"), true);
		Predicate sitePredicate = cb.equal(root.get("siteId"), structure.getSiteId());
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(activePredicate, sitePredicate, orPredicate);
		cr.select(root).where(andPredicate);
		Query<Structure> query = session.createQuery(cr);
		List<Structure> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			return (Long) session.save(structure);
		}
		return null;
	}

	@Override
	public Structure fetchStructureById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		cr.select(root).where(cb.equal(root.get("id"), id));
		Query<Structure> query = session.createQuery(cr);
		Structure result = query.uniqueResult();
		return result;
	}

	@Override
	public Boolean updateStructure(Structure structure) {

		if (structure == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if (structure != null && structure.getName() != null)
			orPredicates.add(cb.equal(root.get("name"), structure.getName()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate idPredicate = cb.notEqual(root.get("id"), structure.getId());
		Predicate activePredicate = cb.equal(root.get("isActive"), true);
		Predicate sitePredicate = cb.equal(root.get("siteId"), structure.getSiteId());
		Predicate andPredicate = cb.and(orPredicate, idPredicate, activePredicate, sitePredicate);
		cr.select(root).where(andPredicate);
		Query<Structure> query = session.createQuery(cr);
		List<Structure> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			session.update(structure);
			return true;
		}
		return false;
	}

	@Override
	public List<Structure> fetchStructuresByTypeId(Long typeId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("type", JoinType.LEFT);
		andPredicates.add(cb.equal(root.get("type").get("id"), typeId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Structure> query = session.createQuery(cr);
		List<Structure> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureV2> fetchTypeWiseStructureIds(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureV2> cr = cb.createQuery(StructureV2.class);
		Root<StructureV2> root = cr.from(StructureV2.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		root.fetch("type", JoinType.LEFT);
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), search.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureV2> query = session.createQuery(cr);
		List<StructureV2> results = query.getResultList();
		return results;
	}

	@Override
	public List<Structure> fetchStructuresByGroupId(Integer groupId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("groupId"), groupId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Structure> query = session.createQuery(cr);
		List<Structure> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchMaxExistedGroupStructurePatternIndex(Long siteId, String groupStructureMultiplesPattern,
			String separator) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Structure> cr = cb.createQuery(Structure.class);
		Root<Structure> root = cr.from(Structure.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(cb.lower(root.get("name")), groupStructureMultiplesPattern.toLowerCase() + "%"));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("siteId"), siteId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("name")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<Structure> query = session.createQuery(cr);
		query.setMaxResults(1);
		query.setFirstResult(0);
		Structure result = query.uniqueResult();
		if (result != null) {
			String[] separatedName = result.getName().split(separator);
			String lastIdxString = separatedName[separatedName.length - 1];
			if (lastIdxString.isBlank()) {
				return 0;
			} else {
				return Integer.parseInt(lastIdxString);
			}
		}
		return 0;
	}

}
