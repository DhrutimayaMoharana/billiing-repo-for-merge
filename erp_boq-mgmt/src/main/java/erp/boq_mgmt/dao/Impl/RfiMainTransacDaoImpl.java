package erp.boq_mgmt.dao.Impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.boq_mgmt.dao.RfiMainTransacDao;
import erp.boq_mgmt.entity.RfiMainExecutedQuantityTransaction;

@Repository
public class RfiMainTransacDaoImpl implements RfiMainTransacDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long saveRfiMainExecutedQuantityTransaction(RfiMainExecutedQuantityTransaction transacObj) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(transacObj);
		session.flush();
		session.clear();
		return id;
	}

}
