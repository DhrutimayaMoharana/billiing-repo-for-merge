package erp.boq_mgmt.dao.Impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.boq_mgmt.dao.FileUtilDao;
import erp.boq_mgmt.entity.FileEntity;

@Repository
public class FileUtilDaoImpl implements FileUtilDao {
	
	@Autowired private EntityManager entityManager;

	@Override
	public Long saveFile(FileEntity uploadFile) {
		
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(uploadFile);
	}

}
