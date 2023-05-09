package erp.boq_mgmt.dao.Impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.boq_mgmt.dao.UserDao;
import erp.boq_mgmt.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public User fetchUserById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		return (User) session.get(User.class, id);
	}

}
