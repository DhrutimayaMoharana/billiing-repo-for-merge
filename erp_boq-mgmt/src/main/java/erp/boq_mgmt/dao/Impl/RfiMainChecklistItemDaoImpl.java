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

import erp.boq_mgmt.dao.RfiMainChecklistItemDao;
import erp.boq_mgmt.entity.RfiMainChecklist;

@Repository
public class RfiMainChecklistItemDaoImpl implements RfiMainChecklistItemDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<RfiMainChecklist> fetchRfiMainChecklistItems(Long rfiId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainChecklist> cr = cb.createQuery(RfiMainChecklist.class);
		Root<RfiMainChecklist> root = cr.from(RfiMainChecklist.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<RfiMainChecklist> query = session.createQuery(cr);
		List<RfiMainChecklist> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveRfiMainChecklistItem(RfiMainChecklist rfiMainChecklist) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainChecklist> cr = cb.createQuery(RfiMainChecklist.class);
		Root<RfiMainChecklist> root = cr.from(RfiMainChecklist.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
//		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiMainChecklist.getRfiMainId()));
		andPredicates.add(cb.equal(root.get("checklistItemId"), rfiMainChecklist.getChecklistItemId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<RfiMainChecklist> query = session.createQuery(cr);
		List<RfiMainChecklist> results = query.getResultList();
		if (results != null && results.size() > 0) {
			if (!results.get(0).getIsActive()) {
				rfiMainChecklist.setId(results.get(0).getId());
				RfiMainChecklist rfi = (RfiMainChecklist) session.merge(rfiMainChecklist);
				return rfi.getId();
			}
			return null;
		}
		Long id = (Long) session.save(rfiMainChecklist);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Boolean updateRfiMainChecklistItem(RfiMainChecklist rfiMainChecklist) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainChecklist> cr = cb.createQuery(RfiMainChecklist.class);
		Root<RfiMainChecklist> root = cr.from(RfiMainChecklist.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), rfiMainChecklist.getId()));
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiMainChecklist.getRfiMainId()));
		andPredicates.add(cb.equal(root.get("checklistItemId"), rfiMainChecklist.getChecklistItemId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<RfiMainChecklist> query = session.createQuery(cr);
		List<RfiMainChecklist> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		session.merge(rfiMainChecklist);
		session.flush();
		session.clear();
		return true;
	}

}
