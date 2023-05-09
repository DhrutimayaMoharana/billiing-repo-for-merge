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

import erp.boq_mgmt.dao.RfiMainFileDao;
import erp.boq_mgmt.entity.RfiMainFile;

@Repository
public class RfiMainFileDaoImpl implements RfiMainFileDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<RfiMainFile> fetchRfiMainFiles(Long rfiId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainFile> cr = cb.createQuery(RfiMainFile.class);
		Root<RfiMainFile> root = cr.from(RfiMainFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<RfiMainFile> query = session.createQuery(cr);
		List<RfiMainFile> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveRfiMainFile(RfiMainFile rfiMainFile) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainFile> cr = cb.createQuery(RfiMainFile.class);
		Root<RfiMainFile> root = cr.from(RfiMainFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiMainFile.getRfiMainId()));
		andPredicates.add(cb.equal(root.get("fileId"), rfiMainFile.getFileId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<RfiMainFile> query = session.createQuery(cr);
		List<RfiMainFile> results = query.getResultList();
		if (results != null && results.size() > 0) {

			if (!results.get(0).getIsActive()) {
				rfiMainFile.setId(results.get(0).getId());
				RfiMainFile rfi = (RfiMainFile) session.merge(rfiMainFile);
				return rfi.getId();
			}

			return null;
		}
		Long id = (Long) session.save(rfiMainFile);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Boolean updateRfiMainFile(RfiMainFile rfiMainFile) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<RfiMainFile> cr = cb.createQuery(RfiMainFile.class);
		Root<RfiMainFile> root = cr.from(RfiMainFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), rfiMainFile.getId()));
		andPredicates.add(cb.equal(root.get("rfiMainId"), rfiMainFile.getRfiMainId()));
		andPredicates.add(cb.equal(root.get("fileId"), rfiMainFile.getFileId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(cb.and(andPredicate));
		Query<RfiMainFile> query = session.createQuery(cr);
		List<RfiMainFile> results = query.getResultList();
		if (results != null && results.size() > 0) {
			session.flush();
			session.clear();
			return null;
		}
		session.merge(rfiMainFile);
		session.flush();
		session.clear();
		return true;
	}

}
