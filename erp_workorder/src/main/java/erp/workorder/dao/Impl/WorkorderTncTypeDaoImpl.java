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

import erp.workorder.dao.WorkorderTncTypeDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WoTncType;

@Repository
public class WorkorderTncTypeDaoImpl implements WorkorderTncTypeDao {
	
	@Autowired private EntityManager entityManager;
	
	@Override
	public List<WoTncType> fetchActiveWoTncTypes(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncType> cr = cb.createQuery(WoTncType.class);
		Root<WoTncType> root = cr.from(WoTncType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTncType> query = session.createQuery(cr);
		List<WoTncType> results = query.getResultList();
		return results;
	}

	@Override
	public WoTncType fetchWoTncTypeById(Long id) {
		
		Session session = entityManager.unwrap(Session.class);
		WoTncType woTncType = (WoTncType) session.get(WoTncType.class, id);
		return woTncType;
	}

	@Override
	public Boolean updateWoTncType(WoTncType type) {

		if(type == null)
			return false;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncType> cr = cb.createQuery(WoTncType.class);
		Root<WoTncType> root = cr.from(WoTncType.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if(type.getName() != null)
			orPredicates.add(cb.equal(root.get("name"), type.getName()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate idPredicate = cb.notEqual(root.get("id"), type.getId());
		Predicate companyPredicate = cb.equal(root.get("companyId"), type.getCompanyId());
		Predicate activePredicate = cb.equal(root.get("isActive"), true);
		Predicate andPredicate = cb.and(orPredicate, idPredicate, companyPredicate, activePredicate);
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTncType> query = session.createQuery(cr);
		List<WoTncType> results = query.getResultList();
		if(results == null || (results != null && results.size() == 0)) {
			 session.update(type);
			 return true;
		}
		return false;
	}

	@Override
	public Long saveWoTncType(WoTncType type) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoTncType> cr = cb.createQuery(WoTncType.class);
		Root<WoTncType> root = cr.from(WoTncType.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if(type != null && type.getName() != null)
			orPredicates.add(cb.equal(root.get("name"), type.getName()));
		Predicate companyPredicate = cb.equal(root.get("companyId"), type.getCompanyId());
		Predicate activePredicate = cb.equal(root.get("isActive"), true);
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		Predicate andPredicate = cb.and(orPredicate, companyPredicate, activePredicate);
		cr.distinct(true).select(root).where(andPredicate);
		Query<WoTncType> query = session.createQuery(cr);
		List<WoTncType> results = query.getResultList();
		Long id = null;
		if(results == null || (results != null && results.size() == 0)) {
			id = (Long) session.save(type);
		}
		return id;
	}

}
