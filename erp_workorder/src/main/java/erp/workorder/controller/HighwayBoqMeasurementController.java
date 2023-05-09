package erp.workorder.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.service.HighwayBoqMeasurementService;
import erp.workorder.util.LogUtil;
import erp.workorder.util.SetObject;

@RequestMapping(value = "/highway_measurement")
@RestController
@CrossOrigin
public class HighwayBoqMeasurementController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HighwayBoqMeasurementService highwayMeasurementService;

	@RequestMapping(value = "/import_highway_measurement_excel", method = RequestMethod.POST)
	public ResponseEntity<?> importHighwayBoqMeasurementExcel(@RequestParam(name = "file") MultipartFile excelFile,
			@ModelAttribute SearchDTO search, HttpServletRequest request) throws Exception {

		UserDetail user = SetObject.getUserDetailFromHttpRequest(request);
		search.setUserId(user.getId());
		search.setCompanyId(user.getCompanyId());
		search.setRoleId(user.getRoleId());
		LOGGER.info(
				LogUtil.beforeExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		CustomResponse response = highwayMeasurementService.importHighwayBoqMeasurementExcel(excelFile, search);

		LOGGER.info(
				LogUtil.afterExecutionLogMessage(user, Thread.currentThread().getStackTrace()[1].getMethodName()));

		return ResponseEntity.ok(response);

	}
}
