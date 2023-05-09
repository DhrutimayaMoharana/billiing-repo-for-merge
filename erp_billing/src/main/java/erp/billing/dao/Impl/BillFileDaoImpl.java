package erp.billing.dao.Impl;

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

import erp.billing.dao.BillFileDao;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillFile;

@Repository
public class BillFileDaoImpl implements BillFileDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<BillFile> fetchBillFiles(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillFile> cr = cb.createQuery(BillFile.class);
		Root<BillFile> root = cr.from(BillFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getFileTypeId() != null)
			andPredicates.add(cb.equal(root.get("type").get("id"), search.getFileTypeId()));
		andPredicates.add(cb.equal(root.get("billId"), search.getBillId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillFile> query = session.createQuery(cr);
		List<BillFile> results = query.getResultList();
		return results;
	}

	@Override
	public Long addBillFile(BillFile file) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BillFile> cr = cb.createQuery(BillFile.class);
		Root<BillFile> root = cr.from(BillFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("type").get("id"), file.getType().getId()));
		andPredicates.add(cb.equal(root.get("billId"), file.getBillId()));
		andPredicates.add(cb.equal(root.get("file").get("id"), file.getFile().getId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<BillFile> query = session.createQuery(cr);
		List<BillFile> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		return (Long) session.save(file);

	}

	@Override
	public Boolean deactivateBillFile(Long billFileId, Long billId, Long userId) {

		Session session = entityManager.unwrap(Session.class);
		BillFile file = session.get(BillFile.class, billFileId);
		if (file != null && file.getBillId().equals(billId)) {
			file.setIsActive(false);
			file.setCreatedBy(userId);
			file.setCreatedOn(new Date());
			session.update(file);
			return true;
		}
		return false;
	}

}
