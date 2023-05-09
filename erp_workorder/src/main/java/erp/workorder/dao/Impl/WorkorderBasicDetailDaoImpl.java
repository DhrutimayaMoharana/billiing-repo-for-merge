package erp.workorder.dao.Impl;

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

import erp.workorder.dao.WorkorderBasicDetailDao;
import erp.workorder.entity.CategoryItem;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderCategoryMapping;

@Repository
public class WorkorderBasicDetailDaoImpl implements WorkorderBasicDetailDao {
	
	@Autowired private EntityManager entityManager;

	@Override
	public Long saveBasicWorkorder(Workorder workorder) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Workorder> cr = cb.createQuery(Workorder.class);
		Root<Workorder> root = cr.from(Workorder.class);
		root.fetch("boqWork", JoinType.LEFT);
		root.fetch("type", JoinType.LEFT);
		root.fetch("woContractor", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), workorder.getCompanyId()));
		andPredicates.add(cb.equal(root.get("uniqueNo"), workorder.getUniqueNo()));
		andPredicates.add(cb.equal(root.get("siteId"), workorder.getSiteId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<Workorder> query = session.createQuery(cr);
		List<Workorder> results = query.getResultList();
		if(results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(workorder);
		session.clear();
		return id;
	}

	@Override
	public List<WorkorderCategoryMapping> fetchWorkorderCategoriesByWorkorderId(
			Long woId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderCategoryMapping> cr = cb.createQuery(WorkorderCategoryMapping.class);
		Root<WorkorderCategoryMapping> root = cr.from(WorkorderCategoryMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("workorderId"), woId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderCategoryMapping> query = session.createQuery(cr);
		List<WorkorderCategoryMapping> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveWorkorderContractorCategoryMapping(WorkorderCategoryMapping wcc) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkorderCategoryMapping> cr = cb.createQuery(WorkorderCategoryMapping.class);
		Root<WorkorderCategoryMapping> root = cr.from(WorkorderCategoryMapping.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("workorderId"), wcc.getWorkorderId()));
		andPredicates.add(cb.equal(root.get("categoryId"), wcc.getCategoryId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WorkorderCategoryMapping> query = session.createQuery(cr);
		List<WorkorderCategoryMapping> results = query.getResultList();
		if(results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(wcc);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public List<CategoryItem> fetchActiveCategoryItemsByCompanyId(Integer companyId) {
		
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<CategoryItem> cr = cb.createQuery(CategoryItem.class);
		Root<CategoryItem> root = cr.from(CategoryItem.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("companyId"), companyId));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<CategoryItem> query = session.createQuery(cr);
		List<CategoryItem> results = query.getResultList();
		return results;
	}
	
	@Override
	public Workorder fetchWorkorderById(Long id) {
		
		Session session = entityManager.unwrap(Session.class);
		Workorder wo = (Workorder) session.get(Workorder.class, id);
		return wo;
	}
	
	@Override
	public Boolean updateBasicWorkorder(Workorder workorder) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Workorder> cr = cb.createQuery(Workorder.class);
		Root<Workorder> root = cr.from(Workorder.class);
		root.fetch("boqWork", JoinType.LEFT);
		root.fetch("type", JoinType.LEFT);
		root.fetch("woContractor", JoinType.LEFT);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), workorder.getCompanyId()));
		andPredicates.add(cb.equal(root.get("uniqueNo"), workorder.getUniqueNo()));
		andPredicates.add(cb.equal(root.get("siteId"), workorder.getSiteId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.notEqual(root.get("id"), workorder.getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<Workorder> query = session.createQuery(cr);
		List<Workorder> results = query.getResultList();
		if(results == null || (results != null && results.size() == 0)) {
			 session.update(workorder);
			 return true;
		}
		return false;
	}


}
