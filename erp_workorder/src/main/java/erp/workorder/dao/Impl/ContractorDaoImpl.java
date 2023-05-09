package erp.workorder.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.ContractorDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.CategoryContractorMapping;
import erp.workorder.entity.Contractor;
import erp.workorder.entity.ContractorBankDetail;
import erp.workorder.entity.ContractorBillingAddress;

@Repository
public class ContractorDaoImpl implements ContractorDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Contractor> fetchContractors(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Contractor> cr = cb.createQuery(Contractor.class);
		Root<Contractor> root = cr.from(Contractor.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		if (search != null && search.getSearchField() != null) {
			Predicate namePredicate = cb.like(cb.lower(root.get("name")),
					"%" + search.getSearchField().toLowerCase() + "%");
			Predicate searchFieldPredicate = cb.or(namePredicate);
			andPredicates.add(searchFieldPredicate);
		}
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<Contractor> query = session.createQuery(cr);
		List<Contractor> results = query.getResultList();
		return results;
	}

	@Override
	public Contractor fetchContractorById(Long contractorId) {

		Session session = entityManager.unwrap(Session.class);
		Contractor contractor = (Contractor) session.get(Contractor.class, contractorId);
		return contractor;
	}

	@Override
	public List<ContractorBankDetail> fetchBankDetailsByContractorId(Long contractorId) {

		if (contractorId == null)
			return null;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ContractorBankDetail> cr = cb.createQuery(ContractorBankDetail.class);
		Root<ContractorBankDetail> root = cr.from(ContractorBankDetail.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("contractorId"), contractorId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<ContractorBankDetail> query = session.createQuery(cr);
		return query.getResultList() != null ? query.getResultList() : null;
	}

	@Override
	public List<ContractorBillingAddress> fetchBillingAddressesByContractorId(Long contractorId) {

		if (contractorId == null)
			return null;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ContractorBillingAddress> cr = cb.createQuery(ContractorBillingAddress.class);
		Root<ContractorBillingAddress> root = cr.from(ContractorBillingAddress.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("contractorId"), contractorId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<ContractorBillingAddress> query = session.createQuery(cr);
		return query.getResultList() != null ? query.getResultList() : null;
	}

	@Override
	public List<CategoryContractorMapping> fetchContractorsByCategoryId(Long categoryId) {

		if (categoryId == null)
			return null;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryContractorMapping> cr = cb.createQuery(CategoryContractorMapping.class);
		Root<CategoryContractorMapping> root = cr.from(CategoryContractorMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("categoryId"), categoryId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<CategoryContractorMapping> query = session.createQuery(cr);
		List<CategoryContractorMapping> result = query.getResultList();
		return result != null ? result : null;
	}

	@Override
	public List<CategoryContractorMapping> fetchContractorsByCategoryIdsArr(Set<Long> categoryIds) {

		if (categoryIds == null || categoryIds.size() < 1)
			return null;
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryContractorMapping> cr = cb.createQuery(CategoryContractorMapping.class);
		Root<CategoryContractorMapping> root = cr.from(CategoryContractorMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (categoryIds != null) {
			In<Long> inClause = cb.in(root.get("categoryId"));
			for (Long id : categoryIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<CategoryContractorMapping> query = session.createQuery(cr);
		List<CategoryContractorMapping> result = query.getResultList();
		return result != null ? result : null;
	}

}
