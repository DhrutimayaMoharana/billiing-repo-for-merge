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

import erp.boq_mgmt.dao.UnitDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Unit;
import erp.boq_mgmt.entity.UnitType;

@Repository
public class UnitDaoImpl implements UnitDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveUnit(Unit unit) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Unit> cr = cb.createQuery(Unit.class);
		Root<Unit> root = cr.from(Unit.class);
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		if (unit != null && unit.getName() != null)
			orPredicates.add(cb.equal(cb.lower(root.get("name")), unit.getName().toLowerCase()));
		Predicate orPredicate = cb.or(orPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(orPredicate);
		Query<Unit> query = session.createQuery(cr);
		List<Unit> results = query.getResultList();
		if (results == null || (results != null && results.size() == 0)) {
			return (Long) session.save(unit);
		}
		return results.get(0).getId();
	}

	@Override
	public List<Unit> fetchUnits(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Unit> cr = cb.createQuery(Unit.class);
		Root<Unit> root = cr.from(Unit.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search != null && search.getSearchField() != null)
			andPredicates.add(cb.like(cb.lower(root.get("name")), "%" + search.getSearchField().toLowerCase() + "%"));
		if (search != null && search.getUnitTypeId() != null)
			andPredicates.add(cb.equal(root.get("type").get("id"), search.getUnitTypeId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<Unit> query = session.createQuery(cr);
		List<Unit> results = query.getResultList();
		return results;
	}

	@Override
	public Integer fetchCount(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Unit> cr = cb.createQuery(Unit.class);
		Root<Unit> root = cr.from(Unit.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getSearchField() != null)
			andPredicates.add(cb.like(cb.lower(root.get("name")), "%" + search.getSearchField().toLowerCase() + "%"));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<Unit> query = session.createQuery(cr);
		List<Unit> results = query.getResultList();
		return results != null ? results.size() : 0;
	}

	@Override
	public List<UnitType> fetchUnitTypes(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UnitType> cr = cb.createQuery(UnitType.class);
		Root<UnitType> root = cr.from(UnitType.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getSearchField() != null)
			andPredicates.add(cb.like(cb.lower(root.get("name")), "%" + search.getSearchField().toLowerCase() + "%"));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<UnitType> query = session.createQuery(cr);
		List<UnitType> results = query.getResultList();
		return results;
	}

	@Override
	public Long getIdByNameOrSave(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		session.clear();
		session.flush();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Unit> cr = cb.createQuery(Unit.class);
		Root<Unit> root = cr.from(Unit.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search.getName() != null)
			andPredicates.add(cb.like(cb.lower(root.get("name")), search.getName().toLowerCase()));
		if (search.getSearchField() != null)
			andPredicates.add(cb.like(cb.lower(root.get("name")), search.getSearchField().toLowerCase()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<Unit> query = session.createQuery(cr);
		List<Unit> results = query.getResultList();
		if (results != null && results.size() > 0)
			return results.get(0).getId();
		Unit item = new Unit(null, search.getName(), null, null, true, search.getCompanyId());
		return (Long) session.save(item);
	}

}
