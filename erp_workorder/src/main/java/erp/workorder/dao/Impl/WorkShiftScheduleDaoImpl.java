package erp.workorder.dao.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.workorder.dao.WorkShiftScheduleDao;
import erp.workorder.entity.WorkShiftSchedule;

@Repository
public class WorkShiftScheduleDaoImpl implements WorkShiftScheduleDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<WorkShiftSchedule> fetchWorkShiftSchedule() {
		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<WorkShiftSchedule> cr = cb.createQuery(WorkShiftSchedule.class);
		Root<WorkShiftSchedule> root = cr.from(WorkShiftSchedule.class);
		Query<WorkShiftSchedule> query = session.createQuery(cr.select(root));
		List<WorkShiftSchedule> results = query.getResultList();
		session.flush();
		session.clear();
		return results;
	}
}
