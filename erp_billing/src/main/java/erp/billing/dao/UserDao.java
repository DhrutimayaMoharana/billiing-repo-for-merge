package erp.billing.dao;

import erp.billing.entity.User;

public interface UserDao {

	User fetchUserById(Long id);

}
