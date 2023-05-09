package erp.billing.dao.Impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.billing.dao.MachineDao;
import erp.billing.entity.Equipment;

@Repository
public class MachineDaoImpl implements MachineDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Equipment fetchEquipment(Long equipmentId) {

		Session session = entityManager.unwrap(Session.class);
		return session.get(Equipment.class, equipmentId);
	}

}
