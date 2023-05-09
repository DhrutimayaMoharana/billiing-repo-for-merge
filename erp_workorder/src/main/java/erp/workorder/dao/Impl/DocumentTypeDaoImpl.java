package erp.workorder.dao.Impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.DocumentTypeDao;
import erp.workorder.entity.DocumentType;

@Repository
public class DocumentTypeDaoImpl implements DocumentTypeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public DocumentType fetchById(Integer id) {

		Session session = entityManager.unwrap(Session.class);
		return session.get(DocumentType.class, id);
	}

}
