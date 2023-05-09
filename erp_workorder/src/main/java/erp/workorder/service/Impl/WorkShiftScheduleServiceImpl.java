package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WorkShiftScheduleDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.response.WorkShiftScheduleResponse;
import erp.workorder.entity.WorkShiftSchedule;
import erp.workorder.enums.Responses;
import erp.workorder.service.WorkShiftScheduleService;

@Transactional
@Service
public class WorkShiftScheduleServiceImpl implements WorkShiftScheduleService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkShiftScheduleDao workShiftScheduleDao;

	@Override
	public CustomResponse getWorkShiftSchedule() {
		try {
			List<WorkShiftSchedule> schedules = workShiftScheduleDao.fetchWorkShiftSchedule();

			List<WorkShiftScheduleResponse> responseObj = new ArrayList<>();

			if (schedules != null && !schedules.isEmpty()) {
				schedules.forEach(
						obj -> responseObj.add(new WorkShiftScheduleResponse(obj.getId(), obj.getShiftHours())));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), responseObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
