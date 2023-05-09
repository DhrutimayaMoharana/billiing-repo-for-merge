package erp.workorder.dao;

import erp.workorder.entity.User;

public interface UserDao {

	User fetchUserById(Long id);

}
