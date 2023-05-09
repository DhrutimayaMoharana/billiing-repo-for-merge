package erp.billing.dao.Impl;

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

import erp.billing.dao.DebitNoteDao;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.DebitNoteItem;
import erp.billing.enums.DebitNoteItemDepartments;
import erp.billing.enums.DebitNotePartyTypes;
import erp.billing.util.DateUtil;

@Repository
public class DebitNoteDaoImpl implements DebitNoteDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<DebitNoteItem> fetchDebitNoteItems(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<DebitNoteItem> cr = cb.createQuery(DebitNoteItem.class);
		Root<DebitNoteItem> root = cr.from(DebitNoteItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getIsDiesel() != null && search.getIsDiesel()) {
			andPredicates.add(cb.equal(root.get("departmentId"), DebitNoteItemDepartments.Diesel.getDepartmentId()));
		} else if (search.getIsDiesel() != null && !search.getIsDiesel()) {
			andPredicates.add(cb.notEqual(root.get("departmentId"), DebitNoteItemDepartments.Diesel.getDepartmentId()));
		}
		andPredicates.add(cb.equal(root.get("workorderId"), search.getWorkorderId()));
		if (search.getFromDate() != null) {
			andPredicates
					.add(cb.greaterThanOrEqualTo(root.get("fromDate"), DateUtil.dateWithoutTime(search.getFromDate())));
		}
		andPredicates.add(cb.lessThan(root.get("toDate"), DateUtil.nextDateWithoutTime(search.getToDate())));
		andPredicates
				.add(cb.equal(root.get("debitNote").get("partyType"), DebitNotePartyTypes.Contractor.getPartyType()));
		andPredicates.add(cb.equal(root.get("debitNote").get("partyId"), search.getContractorId()));
		andPredicates.add(cb.equal(root.get("debitNote").get("siteId"), search.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<DebitNoteItem> query = session.createQuery(cr);
		List<DebitNoteItem> results = query.getResultList();
		return results;
	}

}
