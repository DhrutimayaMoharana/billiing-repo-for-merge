package erp.billing.dao.Impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.billing.dao.WorkorderDao;
import erp.billing.entity.Workorder;

@Repository
public class WorkorderDaoImpl implements WorkorderDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Workorder fetchWorkorderById(Long workorderId) {

		Session session = entityManager.unwrap(Session.class);
		Workorder workorder = (Workorder) session.get(Workorder.class, workorderId);
		return workorder;
	}

}
