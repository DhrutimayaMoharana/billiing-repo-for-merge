package erp.boq_mgmt.dao;

import erp.boq_mgmt.entity.User;

public interface UserDao {

	User fetchUserById(Long id);

}
