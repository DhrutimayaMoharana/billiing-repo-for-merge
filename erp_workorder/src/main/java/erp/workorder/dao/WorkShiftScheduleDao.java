package erp.workorder.dao;

import java.util.List;

import erp.workorder.entity.WorkShiftSchedule;

public interface WorkShiftScheduleDao {

	List<WorkShiftSchedule> fetchWorkShiftSchedule();
}
