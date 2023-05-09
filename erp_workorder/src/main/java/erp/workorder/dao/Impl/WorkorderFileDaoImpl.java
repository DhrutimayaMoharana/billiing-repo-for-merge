package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
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

import erp.workorder.dao.WorkorderFileDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WorkorderFile;

@Repository
public class WorkorderFileDaoImpl implements WorkorderFileDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkorderFile> fetchWorkorderFiles(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderFile> cr = cb.createQuery(WorkorderFile.class);
		Root<WorkorderFile> root = cr.from(WorkorderFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getFileTypeId() != null)
			andPredicates.add(cb.equal(root.get("type").get("id"), search.getFileTypeId()));
		andPredicates.add(cb.equal(root.get("workorderId"), search.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderFile> query = session.createQuery(cr);
		List<WorkorderFile> results = query.getResultList();
		return results;
	}

	@Override
	public Long addWorkorderFile(WorkorderFile file) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderFile> cr = cb.createQuery(WorkorderFile.class);
		Root<WorkorderFile> root = cr.from(WorkorderFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("type").get("id"), file.getType().getId()));
		andPredicates.add(cb.equal(root.get("workorderId"), file.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("file").get("id"), file.getFile().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<WorkorderFile> query = session.createQuery(cr);
		List<WorkorderFile> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		return (Long) session.save(file);
	}

	@Override
	public boolean deactivateWorkorderFile(Long woFileId, Long workorderId, Long userId) {

		Session session = entityManager.unwrap(Session.class);
		WorkorderFile file = session.get(WorkorderFile.class, woFileId);
		if (file != null && file.getWorkorderId().equals(workorderId)) {
			file.setIsActive(false);
			file.setCreatedBy(userId);
			file.setCreatedOn(new Date());
			session.update(file);
			return true;
		}
		return false;
	}

}
