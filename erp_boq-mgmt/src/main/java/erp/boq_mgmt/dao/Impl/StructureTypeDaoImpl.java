package erp.boq_mgmt.dao.Impl;

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

import erp.boq_mgmt.dao.StructureTypeDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.StructureType;

@Repository
public class StructureTypeDaoImpl implements StructureTypeDao {

	@Autowired private EntityManager entityManager;
	
	@Override
	public Integer fetchCount(SearchDTO search) {
		
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureType> cr = cb.createQuery(StructureType.class);
		Root<StructureType> root = cr.from(StructureType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if(search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")), "%"+ search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StructureType> query = session.createQuery(cr);
		List<StructureType> results = query.getResultList();
		return results != null ? results.size() : 0;
	}

	@Override
	public Long saveStructureType(StructureType type) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureType> cr = cb.createQuery(StructureType.class);
		Root<StructureType> root = cr.from(StructureType.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if(type != null && type.getName() != null)
			orPredicates.add(cb.equal(root.get("name"), type.getName()));
		if(type.getCode() != null)
			orPredicates.add(cb.equal(root.get("code"), type.getCode()));
		Predicate companyPredicate = cb.equal(root.get("companyId"), type.getCompanyId());
		Predicate activePredicate = cb.equal(root.get("isActive"), true);
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(orPredicate, companyPredicate, activePredicate);
		cr.select(root).where(andPredicate);
		Query<StructureType> query = session.createQuery(cr);
		List<StructureType> results = query.getResultList();
		Long id = null;
		if(results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(type);
		}
		return id;
	}

	@Override
	public StructureType fetchStructureTypeByIdOrName(Long id, String name) {
		
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureType> cr = cb.createQuery(StructureType.class);
		Root<StructureType> root = cr.from(StructureType.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if(id != null)
			orPredicates.add(cb.equal(root.get("id"), id));
		if(name != null)
			orPredicates.add(cb.equal(root.get("name"), name));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(orPredicate);
		Query<StructureType> query = session.createQuery(cr);
		List<StructureType> results = query.getResultList();
		return results != null && results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public Boolean updateStructureType(StructureType type) {

		if(type == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureType> cr = cb.createQuery(StructureType.class);
		Root<StructureType> root = cr.from(StructureType.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if(type.getName() != null)
			orPredicates.add(cb.equal(root.get("name"), type.getName()));
		if(type.getCode() != null)
			orPredicates.add(cb.equal(root.get("code"), type.getCode()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate idPredicate = cb.notEqual(root.get("id"), type.getId());
		Predicate companyPredicate = cb.equal(root.get("companyId"), type.getCompanyId());
		Predicate activePredicate = cb.equal(root.get("isActive"), true);
		Predicate andPredicate = cb.and(orPredicate, idPredicate, companyPredicate, activePredicate);
		cr.select(root).where(andPredicate);
		Query<StructureType> query = session.createQuery(cr);
		List<StructureType> results = query.getResultList();
		if(results == null || (results != null && results.size() == 0)) {
			 session.update(type);
			 return true;
		}
		return false;
	}

	@Override
	public List<StructureType> fetchStructureTypes(SearchDTO search) {
		
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureType> cr = cb.createQuery(StructureType.class);
		Root<StructureType> root = cr.from(StructureType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		if(search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")), "%"+ search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<StructureType> query = session.createQuery(cr);
		List<StructureType> results = query.getResultList();
		return results;
	}

}
