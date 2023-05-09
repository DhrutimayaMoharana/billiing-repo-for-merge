package erp.workorder.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.UserDetail;
import erp.workorder.service.WorkShiftScheduleService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;

@RequestMapping(value = "/work_shift_schedule")
@RestController
@CrossOrigin
public class WorkShiftScheduleController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkShiftScheduleService shiftScheduleService;

	@RequestMapping(value = "/v1/get", method = RequestMethod.GET)
	public ResponseEntity<?> getWorkShiftSchedule(HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		;
		LOGGER.info(LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = shiftScheduleService.getWorkShiftSchedule();

		LOGGER.info(LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(response);

	}

}
