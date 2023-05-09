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

import erp.workorder.dao.WoFormulaVariableDao;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WoFormulaVariable;

@Repository
public class WoFormulaVariableDaoImpl implements WoFormulaVariableDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long getIdByNameOrSave(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WoFormulaVariable> cr = cb.createQuery(WoFormulaVariable.class);
		Root<WoFormulaVariable> root = cr.from(WoFormulaVariable.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("companyId"), search.getCompanyId()));
		andPredicates.add(cb.equal(root.get("name"), search.getName()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.select(root).where(andPredicate);
		Query<WoFormulaVariable> query = session.createQuery(cr);
		List<WoFormulaVariable> results = query.getResultList();
		if (results != null && results.size() > 0)
			return results.get(0).getId();
		WoFormulaVariable fv = new WoFormulaVariable(null, search.getName(), new Date(), search.getUserId(),
				search.getCompanyId());
		return (Long) session.save(fv);
	}

}
