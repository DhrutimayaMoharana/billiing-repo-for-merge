package erp.workorder.service.Impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import erp.workorder.dao.DprHireMachineDao;
import erp.workorder.dao.EquipmentSummaryDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.request.MachineDPRImportExcelRequest;
import erp.workorder.dto.request.MachineDprAddUpdateRequest;
import erp.workorder.dto.request.MachineDprDeactivateRequest;
import erp.workorder.dto.request.MachineDprGetRequest;
import erp.workorder.dto.response.MachineDPRImportErrorResponse;
import erp.workorder.dto.response.MachineDprGetResponse;
import erp.workorder.dto.response.MachineDprSummaryResponse;
import erp.workorder.dto.response.MachineryAttendanceStatusResponse;
import erp.workorder.dto.response.MachineryRunningModeResponse;
import erp.workorder.dto.response.MachineryShiftsResponse;
import erp.workorder.entity.Equipment;
import erp.workorder.entity.EquipmentSummary;
import erp.workorder.entity.MachineDPR;
import erp.workorder.enums.MachineType;
import erp.workorder.enums.MachineryAttendanceStatus;
import erp.workorder.enums.MachineryRunningMode;
import erp.workorder.enums.MachineryShifts;
import erp.workorder.enums.Responses;
import erp.workorder.service.DprHireMachineService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.DateUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class DprHireMachineServiceImpl implements DprHireMachineService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private DprHireMachineDao dprDao;

	@Autowired
	private EquipmentSummaryDao equipmentSummaryDao;

	@Autowired
	private SetObject setObject;

	@Override
	public CustomResponse addOrUpdateMachineDPR(MachineDprAddUpdateRequest requestDTO) {
		try {

			CustomResponse validationRes = validationUtil.validateAddOrUpdateMachineDPR(requestDTO);

			if (validationRes.getStatus().equals(Responses.BAD_REQUEST.getCode()))
				return validationRes;

			// override actuals readings
			requestDTO.setPrimaryOpeningActualReading(requestDTO.getPrimaryOpeningMeterReading());
			requestDTO.setPrimaryClosingActualReading(requestDTO.getPrimaryClosingMeterReading());
			requestDTO.setSecondaryOpeningActualReading(requestDTO.getSecondaryOpeningMeterReading());
			requestDTO.setSecondaryClosingActualReading(requestDTO.getSecondaryClosingMeterReading());

			DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}

			if (requestDTO.getMachineType().equals((byte) MachineType.Equipment.ordinal())) {
				Equipment equipment = dprDao.fetchEquipmentById(requestDTO.getMachineId());
				if (equipment == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid machineId.");
				}
				if (requestDTO.getRunningMode().equals(MachineryRunningMode.TRIP)) {
					if (equipment.getCategory().getIsMultiFuel()) {
						if (requestDTO.getSecondaryOpeningMeterReading() == null) {
							requestDTO.setSecondaryOpeningMeterReading(0D);
						}
						if (requestDTO.getSecondaryClosingMeterReading() == null) {
							requestDTO.setSecondaryClosingMeterReading(0D);
						}
						if (requestDTO.getSecondaryOpeningActualReading() == null) {
							requestDTO.setSecondaryOpeningActualReading(0D);
						}
						if (requestDTO.getSecondaryClosingActualReading() == null) {
							requestDTO.setSecondaryClosingActualReading(0D);
						}
					}

				}
				if (equipment.getCategory().getIsMultiFuel()) {
					if (requestDTO.getSecondaryOpeningMeterReading() == null
							|| requestDTO.getSecondaryOpeningActualReading() == null
							|| requestDTO.getSecondaryClosingMeterReading() == null
							|| requestDTO.getSecondaryClosingActualReading() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Please provide valid secondary meter reading.");
					}
				}
			}

			List<MachineDPR> sameDateDprList = dprDao.fetchSameDateMachineDprByDate(requestDTO.getDated(),
					requestDTO.getMachineType(), requestDTO.getMachineId(), requestDTO.getSiteId());

			if (sameDateDprList != null && !sameDateDprList.isEmpty()) {

				for (MachineDPR dpr : sameDateDprList) {

					// Validation for different running mode inputs
					if (!dpr.getRunningMode().equals(requestDTO.getRunningMode())
							&& (requestDTO.getId() == null || !requestDTO.getId().equals(dpr.getId()))) {

						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Different running mode found for dated : "
										+ dateFormatter.format(requestDTO.getDated()));
					}

				}

			}

//			update DPR
			if (requestDTO.getId() != null) {

				MachineDPR mDpr = dprDao.fetchMachineDprById(requestDTO.getId());
				if (mDpr == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid id.");
				}

				mDpr = setObject.updatedMachineDprEntityFromRequestDto(mDpr, requestDTO);
				Boolean isUpdated = dprDao.updateMachineDpr(mDpr);

				return new CustomResponse(isUpdated ? Responses.SUCCESS.getCode() : Responses.BAD_REQUEST.getCode(),
						isUpdated, isUpdated ? "Updated." : "Already exists.");
			}

//			add DPR
			else {
				MachineDPR mDpr = setObject.machineDprAddRequestDtoToEntity(requestDTO);

				Long mDprId = dprDao.saveMachineDpr(mDpr);

				return new CustomResponse(
						mDprId != null && mDprId.longValue() > 0L ? Responses.SUCCESS.getCode()
								: Responses.BAD_REQUEST.getCode(),
						mDprId, mDprId != null && mDprId.longValue() > 0L ? "Added." : "Already exists.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateMachineDPR(MachineDprDeactivateRequest requestDTO) {
		try {
			CustomResponse validationRes = validationUtil.validateDeactivateMachineDprRequest(requestDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			MachineDPR mDpr = dprDao.fetchMachineDprById(requestDTO.getDprId());
			if (mDpr == null) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Provide valid dprId.");
			}
			mDpr.setModifiedOn(new Date());
			mDpr.setModifiedBy(requestDTO.getUserDetail().getId());
			mDpr.setIsActive(false);
			dprDao.forceUpdateMachineDpr(mDpr);
			return new CustomResponse(Responses.SUCCESS.getCode(), null, "Removed.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getMachineDPR(MachineDprGetRequest requestDTO) {

		try {
			CustomResponse validationRes = validationUtil.validateGetMachineDprRequest(requestDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}

			List<MachineDPR> machineDprList = dprDao.fetchMachineDprList(requestDTO.getMachineId(),
					requestDTO.getMachineType(), requestDTO.getFromDate(), requestDTO.getToDate(),
					requestDTO.getSiteId());
			List<MachineDprGetResponse> dprList = new ArrayList<>();
			Double netTotalPrimaryMeterReading = 0.0;
			Double netTotalPrimaryActualReading = 0.0;
			Double netTotalSecondaryMeterReading = 0.0;
			Double netTotalSecondaryActualReading = 0.0;
			if (machineDprList != null) {
				for (MachineDPR dpr : machineDprList) {

					Double primaryOpenMeterRead = (dpr.getPrimaryOpeningMeterReading() != null
							? dpr.getPrimaryOpeningMeterReading()
							: 0.0);
					Double primaryOpenActualRead = (dpr.getPrimaryOpeningActualReading() != null
							? dpr.getPrimaryOpeningActualReading()
							: 0.0);
					Double primaryCloseMeterRead = (dpr.getPrimaryClosingMeterReading() != null
							? dpr.getPrimaryClosingMeterReading()
							: 0.0);
					Double primaryCloseActualRead = (dpr.getPrimaryClosingActualReading() != null
							? dpr.getPrimaryClosingActualReading()
							: 0.0);
					Double secondaryOpenMeterRead = (dpr.getSecondaryOpeningMeterReading() != null
							? dpr.getSecondaryOpeningMeterReading()
							: 0.0);
					Double secondaryOpenActualRead = (dpr.getSecondaryOpeningActualReading() != null
							? dpr.getSecondaryOpeningActualReading()
							: 0.0);
					Double secondaryCloseMeterRead = (dpr.getSecondaryClosingMeterReading() != null
							? dpr.getSecondaryClosingMeterReading()
							: 0.0);
					Double secondaryCloseActualRead = (dpr.getSecondaryClosingActualReading() != null
							? dpr.getSecondaryClosingActualReading()
							: 0.0);

					Double netPrimaryMeterRead = primaryCloseMeterRead - primaryOpenMeterRead;
					netTotalPrimaryMeterReading += netPrimaryMeterRead;
					Double netPrimaryActualRead = primaryCloseActualRead - primaryOpenActualRead;
					netTotalPrimaryActualReading += netPrimaryActualRead;
					Double netSecondaryMeterRead = secondaryCloseMeterRead - secondaryOpenMeterRead;
					netTotalSecondaryMeterReading += netSecondaryMeterRead;
					Double netSecondaryActualRead = secondaryCloseActualRead - secondaryOpenActualRead;
					netTotalSecondaryActualReading += netSecondaryActualRead;

					if (dpr.getPrimaryOpeningMeterReading() == null || dpr.getPrimaryClosingMeterReading() == null)
						netPrimaryMeterRead = null;
					if (dpr.getPrimaryOpeningActualReading() == null || dpr.getPrimaryClosingActualReading() == null)
						netPrimaryActualRead = null;
					if (dpr.getSecondaryOpeningMeterReading() == null || dpr.getSecondaryClosingMeterReading() == null)
						netSecondaryMeterRead = null;
					if (dpr.getSecondaryOpeningActualReading() == null
							|| dpr.getSecondaryClosingActualReading() == null)
						netSecondaryActualRead = null;
					dprList.add(new MachineDprGetResponse(dpr.getId(), dpr.getDated(), dpr.getRunningMode(),
							dpr.getShift(), dpr.getPrimaryOpeningMeterReading(), dpr.getPrimaryClosingMeterReading(),
							dpr.getSecondaryOpeningMeterReading(), dpr.getSecondaryClosingMeterReading(),
							dpr.getPrimaryOpeningActualReading(), dpr.getPrimaryClosingActualReading(),
							dpr.getSecondaryOpeningActualReading(), dpr.getSecondaryClosingActualReading(),
							netPrimaryMeterRead, netPrimaryActualRead, netSecondaryMeterRead, netSecondaryActualRead,
							dpr.getTripCount(), dpr.getRemarks(), dpr.getAttendanceStatus(), dpr.getBreakdownHours()));
				}
			}
			MachineDprSummaryResponse resultResponse = new MachineDprSummaryResponse(dprList,
					netTotalPrimaryMeterReading, netTotalPrimaryActualReading, netTotalSecondaryMeterReading,
					netTotalSecondaryActualReading);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultResponse, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	public CustomResponse importMachineDPRExcelV2(MultipartFile excelFile,
			MachineDPRImportExcelRequest importExcelRequest) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {

			XSSFSheet worksheet = workbook.getSheetAt(0);

			Integer dprDateColumn = 1;
			Integer dprPrimaryOpeningMeterReadingColumn = 2;
			Integer dprPrimaryOpeningActualReadingColumn = 3;
			Integer dprPrimaryClosingMeterReadingColumn = 4;
			Integer dprPrimaryClosingActualReadingColumn = 5;
			Integer dprRemarksColumn = 6;
			Integer dprAttendanceStatus = 7;
			Integer dprSecondaryOpeningMeterReadingColumn = null;
			Integer dprSecondaryOpeningActualReadingColumn = null;
			Integer dprSecondaryClosingMeterReadingColumn = null;
			Integer dprSecondaryClosingActualReadingColumn = null;

			if (importExcelRequest.getMachineType().equals((byte) MachineType.Equipment.ordinal())) {
				Equipment equipment = dprDao.fetchEquipmentById(importExcelRequest.getMachineId());
				if (equipment == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid machineId.");
				}
				if (equipment.getCategory().getIsMultiFuel()) {
					dprSecondaryOpeningMeterReadingColumn = 6;
					dprSecondaryOpeningActualReadingColumn = 7;
					dprSecondaryClosingMeterReadingColumn = 8;
					dprSecondaryClosingActualReadingColumn = 9;
					dprRemarksColumn = 10;
					dprAttendanceStatus = 11;
				}

				String stringDateHeading = getCellValueFromCell(worksheet.getRow(0).getCell(dprDateColumn));
				String stringPrimaryOpeningMeterReadingHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprPrimaryOpeningMeterReadingColumn));
				String stringPrimaryOpeningActualReadingHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprPrimaryOpeningActualReadingColumn));
				String stringPrimaryClosingMeterReadingHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprPrimaryClosingMeterReadingColumn));
				String stringPrimaryClosingActualReadingHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprPrimaryClosingActualReadingColumn));
				String stringRemarksHeading = getCellValueFromCell(worksheet.getRow(0).getCell(dprRemarksColumn));
				String stringAttendanceStatusHeading = worksheet.getRow(0).getCell(dprAttendanceStatus) != null
						? getCellValueFromCell(worksheet.getRow(0).getCell(dprAttendanceStatus))
						: null;

				String stringSecondaryOpeningMeterReadingHeading = null;
				String stringSecondaryOpeningActualReadingHeading = null;
				String stringSecondaryClosingMeterReadingHeading = null;
				String stringSecondaryClosingActualReadingHeading = null;
				if (equipment.getCategory().getIsMultiFuel()) {
					stringSecondaryOpeningMeterReadingHeading = getCellValueFromCell(
							worksheet.getRow(0).getCell(dprSecondaryOpeningMeterReadingColumn));
					stringSecondaryOpeningActualReadingHeading = getCellValueFromCell(
							worksheet.getRow(0).getCell(dprSecondaryOpeningActualReadingColumn));
					stringSecondaryClosingMeterReadingHeading = getCellValueFromCell(
							worksheet.getRow(0).getCell(dprSecondaryClosingMeterReadingColumn));
					stringSecondaryClosingActualReadingHeading = getCellValueFromCell(
							worksheet.getRow(0).getCell(dprSecondaryClosingActualReadingColumn));

				}

				if (!(stringDateHeading != null && stringDateHeading.toLowerCase().contains("date")
						&& stringPrimaryOpeningMeterReadingHeading != null
						&& stringPrimaryOpeningMeterReadingHeading.toLowerCase()
								.contains("primary opening meter reading")
						&& stringPrimaryOpeningActualReadingHeading != null
						&& stringPrimaryOpeningActualReadingHeading.toLowerCase()
								.contains("primary opening actual reading")
						&& stringPrimaryClosingMeterReadingHeading != null
						&& stringPrimaryClosingMeterReadingHeading.toLowerCase()
								.contains("primary closing meter reading")
						&& stringPrimaryClosingActualReadingHeading != null
						&& stringPrimaryClosingActualReadingHeading.toLowerCase()
								.contains("primary closing actual reading")
						&& stringRemarksHeading != null && stringRemarksHeading.toLowerCase().contains("remarks"))) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid excel.");
				}
				if (equipment.getCategory().getIsMultiFuel() && !(stringSecondaryOpeningMeterReadingHeading != null
						&& stringSecondaryOpeningMeterReadingHeading.toLowerCase()
								.contains("secondary opening meter reading")
						&& stringSecondaryOpeningActualReadingHeading != null
						&& stringSecondaryOpeningActualReadingHeading.toLowerCase()
								.contains("secondary opening actual reading")
						&& stringSecondaryClosingMeterReadingHeading != null
						&& stringSecondaryClosingMeterReadingHeading.toLowerCase()
								.contains("secondary closing meter reading")
						&& stringSecondaryClosingActualReadingHeading != null
						&& stringSecondaryClosingActualReadingHeading.toLowerCase()
								.contains("secondary closing actual reading"))) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid excel.");

				}

				if (stringAttendanceStatusHeading != null
						&& !stringAttendanceStatusHeading.toLowerCase().contains("status")) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid excel.");
				}

			}
			Set<String> errorDateList = new HashSet<String>();
			List<MachineDPR> machineDprList = new ArrayList<MachineDPR>();

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

				XSSFRow row = worksheet.getRow(i);
				String stringDate = getCellValueFromCell(row.getCell(dprDateColumn));
				String stringPrimaryOpeningMeterReading = getCellValueFromCell(
						row.getCell(dprPrimaryOpeningMeterReadingColumn));
				String stringPrimaryOpeningActualReading = getCellValueFromCell(
						row.getCell(dprPrimaryOpeningActualReadingColumn));
				String stringPrimaryClosingMeterReading = getCellValueFromCell(
						row.getCell(dprPrimaryClosingMeterReadingColumn));
				String stringPrimaryClosingActualReading = getCellValueFromCell(
						row.getCell(dprPrimaryClosingActualReadingColumn));
				String stringAttendanceStatus = null;
				String stringSecondaryOpeningMeterReading = null;
				String stringSecondaryOpeningActualReading = null;
				String stringSecondaryClosingMeterReading = null;
				String stringSecondaryClosingActualReading = null;
				String stringRemarks = getCellValueFromCell(row.getCell(dprRemarksColumn));
				if (stringDate == null || stringDate.isEmpty() || stringPrimaryOpeningMeterReading == null
						|| stringPrimaryOpeningMeterReading.isEmpty() || stringPrimaryOpeningActualReading == null
						|| stringPrimaryOpeningActualReading.isEmpty() || stringPrimaryClosingMeterReading == null
						|| stringPrimaryClosingMeterReading.isEmpty() || stringPrimaryClosingActualReading == null
						|| stringPrimaryClosingActualReading.isEmpty()) {
					if (stringDate != null) {
						errorDateList.add(stringDate);
					}
					continue;
				}

				if (dprSecondaryOpeningMeterReadingColumn != null || dprSecondaryOpeningActualReadingColumn != null
						|| dprSecondaryClosingMeterReadingColumn != null
						|| dprSecondaryClosingActualReadingColumn != null) {

					stringSecondaryOpeningMeterReading = getCellValueFromCell(
							row.getCell(dprSecondaryOpeningMeterReadingColumn));
					stringSecondaryOpeningActualReading = getCellValueFromCell(
							row.getCell(dprSecondaryOpeningActualReadingColumn));
					stringSecondaryClosingMeterReading = getCellValueFromCell(
							row.getCell(dprSecondaryClosingMeterReadingColumn));
					stringSecondaryClosingActualReading = getCellValueFromCell(
							row.getCell(dprSecondaryClosingActualReadingColumn));
					if (stringSecondaryOpeningMeterReading == null || stringSecondaryOpeningMeterReading.isEmpty()
							|| stringSecondaryOpeningActualReading == null
							|| stringSecondaryOpeningActualReading.isEmpty()
							|| stringSecondaryClosingMeterReading == null
							|| stringSecondaryClosingMeterReading.isEmpty()
							|| stringSecondaryClosingActualReading == null
							|| stringSecondaryClosingActualReading.isEmpty()) {
						if (stringDate != null) {
							errorDateList.add(stringDate);
						}
						continue;
					}

				}

				Date date = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
				Double primaryOpeningMeterReading = Double.parseDouble(stringPrimaryOpeningMeterReading);
				Double primaryOpeningActualReading = Double.parseDouble(stringPrimaryOpeningActualReading);
				Double primaryClosingMeterReading = Double.parseDouble(stringPrimaryClosingMeterReading);
				Double primaryClosingActualReading = Double.parseDouble(stringPrimaryClosingActualReading);

				if (date == null || date.after(DateUtil.dateWithoutTime(new Date()))
						|| primaryOpeningMeterReading == null || primaryOpeningMeterReading.doubleValue() <= 0.0
						|| primaryOpeningActualReading == null || primaryOpeningActualReading.doubleValue() <= 0.0
						|| primaryClosingMeterReading == null || primaryClosingMeterReading.doubleValue() <= 0.0
						|| primaryClosingActualReading == null || primaryClosingActualReading.doubleValue() <= 0.0
						|| primaryOpeningActualReading.doubleValue() > primaryClosingActualReading.doubleValue()) {
					if (stringDate != null) {
						errorDateList.add(stringDate);
					}
					continue;
				}

				MachineDPR machineDPR = new MachineDPR(null, date, null, null, importExcelRequest.getMachineType(),
						importExcelRequest.getMachineId(), primaryOpeningMeterReading, primaryClosingMeterReading, null,
						null, primaryOpeningActualReading, primaryClosingActualReading, null, null, null, stringRemarks,
						null, importExcelRequest.getSiteId(), true, new Date(),
						importExcelRequest.getUserDetail().getId(), new Date(),
						importExcelRequest.getUserDetail().getId());

				// set machinery attendance status
				if (dprAttendanceStatus != null) {
					stringAttendanceStatus = getCellValueFromCell(row.getCell(dprAttendanceStatus));
					if (stringAttendanceStatus != null)
						for (MachineryAttendanceStatus status : MachineryAttendanceStatus.values()) {

							if (status.name().trim().equalsIgnoreCase(stringAttendanceStatus.trim())) {

								machineDPR.setAttendanceStatus(status);
							}

						}
				}

				if (stringSecondaryOpeningMeterReading != null) {
					Double secondaryOpeningMeterReading = Double.parseDouble(stringSecondaryOpeningMeterReading);
					if (secondaryOpeningMeterReading == null || secondaryOpeningMeterReading.doubleValue() <= 0.0) {
						if (stringDate != null) {
							errorDateList.add(stringDate);
						}
						continue;
					}
					machineDPR.setSecondaryOpeningMeterReading(secondaryOpeningMeterReading);
				}
				if (stringSecondaryOpeningActualReading != null) {
					Double secondaryOpeningActualReading = Double.parseDouble(stringSecondaryOpeningActualReading);
					if (secondaryOpeningActualReading == null || secondaryOpeningActualReading.doubleValue() <= 0.0) {
						if (stringDate != null) {
							errorDateList.add(stringDate);
						}
						continue;
					}
					machineDPR.setSecondaryOpeningMeterReading(secondaryOpeningActualReading);
				}
				if (stringSecondaryClosingMeterReading != null) {
					Double secondaryClosingMeterReading = Double.parseDouble(stringSecondaryClosingMeterReading);
					if (secondaryClosingMeterReading == null || secondaryClosingMeterReading.doubleValue() <= 0.0) {
						if (stringDate != null) {
							errorDateList.add(stringDate);
						}
						continue;
					}
					machineDPR.setSecondaryClosingMeterReading(secondaryClosingMeterReading);
				}
				if (stringSecondaryClosingActualReading != null) {
					Double secondaryClosingActualReading = Double.parseDouble(stringSecondaryClosingMeterReading);
					if (secondaryClosingActualReading == null || secondaryClosingActualReading.doubleValue() <= 0.0) {
						if (stringDate != null) {
							errorDateList.add(stringDate);
						}
						continue;
					}
					machineDPR.setSecondaryClosingMeterReading(secondaryClosingActualReading);
				}
				if (machineDPR.getSecondaryOpeningActualReading() != null
						&& machineDPR.getSecondaryClosingActualReading() != null && machineDPR
								.getSecondaryOpeningActualReading() > machineDPR.getSecondaryClosingActualReading()) {
					if (stringDate != null) {
						errorDateList.add(stringDate);
					}
					continue;
				}

				machineDprList.add(machineDPR);

			}

			if (machineDprList.size() > 0) {

				Date fromDate = DateUtil.dateWithoutTime(machineDprList.get(0).getDated());
				Date toDate = DateUtil.dateWithoutTime(machineDprList.get(machineDprList.size() - 1).getDated());

				List<MachineDPR> dbMachineDprList = dprDao.fetchMachineDprList(importExcelRequest.getMachineId(),
						importExcelRequest.getMachineType(), fromDate, toDate, importExcelRequest.getSiteId());

				MachineDPR nextDateDpr = dprDao.fetchNextDateMachineDprByDate(toDate,
						importExcelRequest.getMachineType(), importExcelRequest.getMachineId(),
						importExcelRequest.getSiteId());
				MachineDPR previousDateDpr = dprDao.fetchPreviousDateMachineDprByDate(fromDate,
						importExcelRequest.getMachineType(), importExcelRequest.getMachineId(),
						importExcelRequest.getSiteId());
				if (previousDateDpr != null)
					dbMachineDprList.add(0, previousDateDpr);
				if (nextDateDpr != null)
					dbMachineDprList.add(nextDateDpr);

				List<MachineDPR> listToUpdate = new ArrayList<MachineDPR>();
				List<MachineDPR> listToSave = new ArrayList<MachineDPR>();

				for (MachineDPR mDpr : machineDprList) {

					Boolean hasFound = false;
					for (MachineDPR dbMachineDpr : dbMachineDprList) {
						if (DateUtil.dateWithoutTime(mDpr.getDated())
								.equals(DateUtil.dateWithoutTime(dbMachineDpr.getDated()))) {
							mDpr.setId(dbMachineDpr.getId());

							dbMachineDpr.setPrimaryOpeningActualReading(mDpr.getPrimaryOpeningActualReading());
							dbMachineDpr.setPrimaryOpeningMeterReading(mDpr.getPrimaryOpeningMeterReading());
							dbMachineDpr.setPrimaryClosingActualReading(mDpr.getPrimaryClosingActualReading());
							dbMachineDpr.setPrimaryClosingMeterReading(mDpr.getPrimaryClosingMeterReading());
							dbMachineDpr.setSecondaryOpeningActualReading(mDpr.getSecondaryOpeningActualReading());
							dbMachineDpr.setSecondaryOpeningMeterReading(mDpr.getSecondaryOpeningMeterReading());
							dbMachineDpr.setSecondaryClosingActualReading(mDpr.getSecondaryClosingActualReading());
							dbMachineDpr.setSecondaryClosingMeterReading(mDpr.getSecondaryClosingMeterReading());
							hasFound = true;
						}
					}
					if (!hasFound) {
						dbMachineDprList.add(mDpr);
						listToSave.add(mDpr);
					} else {
						listToUpdate.add(mDpr);
					}

				}
				// sort machineDpr list
				Collections.sort(dbMachineDprList, new Comparator<MachineDPR>() {
					public int compare(MachineDPR e1, MachineDPR e2) {
						if (e1.getDated() == e2.getDated())
							return 0;
						return e1.getDated().getTime() < e2.getDated().getTime() ? -1 : 1;
					}
				});

				Date invalidFromDate = null;

				for (int i = 0; invalidFromDate == null && i < dbMachineDprList.size(); i++) {

					MachineDPR previousMachineDpr = null;
					MachineDPR currentMachineDpr = dbMachineDprList.get(i);
					MachineDPR nextMachineDpr = null;

					if (i - 1 >= 0) {
						previousMachineDpr = dbMachineDprList.get(i - 1);
					}
					if (i + 1 < dbMachineDprList.size()) {
						nextMachineDpr = dbMachineDprList.get(i + 1);
					}
					if (previousMachineDpr != null && (previousMachineDpr
							.getPrimaryClosingActualReading() > currentMachineDpr.getPrimaryOpeningActualReading()
							|| (previousMachineDpr.getSecondaryClosingActualReading() != null
									&& currentMachineDpr.getSecondaryOpeningActualReading() != null
									&& previousMachineDpr.getSecondaryClosingActualReading() > currentMachineDpr
											.getSecondaryOpeningActualReading()))) {
						invalidFromDate = DateUtil.dateWithoutTime(currentMachineDpr.getDated());

					}
					if (nextMachineDpr != null && (currentMachineDpr.getPrimaryClosingActualReading() > nextMachineDpr
							.getPrimaryOpeningActualReading()
							|| (currentMachineDpr.getSecondaryClosingActualReading() != null
									&& nextMachineDpr.getSecondaryOpeningActualReading() != null
									&& currentMachineDpr.getSecondaryClosingActualReading() > nextMachineDpr
											.getSecondaryOpeningActualReading()))) {
						invalidFromDate = DateUtil.dateWithoutTime(currentMachineDpr.getDated());
					}

				}

				for (MachineDPR mDprToSave : listToSave) {
					if (invalidFromDate != null
							&& invalidFromDate.after(DateUtil.dateWithoutTime(mDprToSave.getDated()))) {
						dprDao.saveMachineDpr(mDprToSave);
					} else {
						dprDao.saveMachineDpr(mDprToSave);
					}
				}
				for (MachineDPR mDprToUpdate : listToUpdate) {
					if (invalidFromDate != null
							&& invalidFromDate.after(DateUtil.dateWithoutTime(mDprToUpdate.getDated()))) {
						dprDao.mergeAndUpdateMachineDpr(mDprToUpdate);
					} else {
						dprDao.mergeAndUpdateMachineDpr(mDprToUpdate);
					}
				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(),
					!errorDateList.isEmpty()
							? new MachineDPRImportErrorResponse("DPR(s) not added for these following date.", null)
							: null,
					Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse importMachineDPRExcel(MultipartFile excelFile,
			MachineDPRImportExcelRequest importExcelRequest) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {

			XSSFSheet worksheet = workbook.getSheetAt(0);

			Integer dprDateColumn = 1;
			Integer dprRunningModeColumn = 2;
			Integer dprShiftOrTripCountColumn = 3;
			Integer dprPrimaryOpeningMeterReadingColumn = 4;
			Integer dprPrimaryClosingMeterReadingColumn = 5;
			Integer dprSecondaryOpeningMeterReadingColumn = 6;
			Integer dprSecondaryClosingMeterReadingColumn = 7;
			Integer dprRemarksColumn = 8;
			Integer dprAttendanceStatus = 9;
			Integer dprBreakdownHoursColumn = 10;
			Equipment equipment = dprDao.fetchEquipmentById(importExcelRequest.getMachineId());
			if (equipment == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid machineId.");
			}

			DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

			if (importExcelRequest.getMachineType().equals((byte) MachineType.Equipment.ordinal())) {

				String stringDateHeading = getCellValueFromCell(worksheet.getRow(0).getCell(dprDateColumn));
				String stringRunningModeHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprRunningModeColumn));
				String stringShiftOrTripCountHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprShiftOrTripCountColumn));
				String stringPrimaryOpeningMeterReadingHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprPrimaryOpeningMeterReadingColumn));
				String stringPrimaryClosingMeterReadingHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprPrimaryClosingMeterReadingColumn));
				String stringSecondaryOpeningMeterReadingHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprSecondaryOpeningMeterReadingColumn));
				String stringSecondaryClosingMeterReadingHeading = getCellValueFromCell(
						worksheet.getRow(0).getCell(dprSecondaryClosingMeterReadingColumn));
				String stringRemarksHeading = getCellValueFromCell(worksheet.getRow(0).getCell(dprRemarksColumn));
				String stringAttendanceStatusHeading = worksheet.getRow(0).getCell(dprAttendanceStatus) != null
						? getCellValueFromCell(worksheet.getRow(0).getCell(dprAttendanceStatus))
						: null;
				String stringBreakdownHoursColumn = worksheet.getRow(0).getCell(dprBreakdownHoursColumn) != null
						? getCellValueFromCell(worksheet.getRow(0).getCell(dprBreakdownHoursColumn))
						: null;

				if (!(stringDateHeading != null && stringDateHeading.toLowerCase().contains("date")
						&& stringRunningModeHeading != null
						&& stringRunningModeHeading.toLowerCase().contains("running mode")
						&& stringShiftOrTripCountHeading != null
						&& stringShiftOrTripCountHeading.toLowerCase().contains("shift/trip count")
						&& stringPrimaryOpeningMeterReadingHeading != null
						&& stringPrimaryOpeningMeterReadingHeading.toLowerCase()
								.contains("primary opening meter reading")
						&& stringPrimaryClosingMeterReadingHeading != null
						&& stringPrimaryClosingMeterReadingHeading.toLowerCase()
								.contains("primary closing meter reading")
						&& stringSecondaryOpeningMeterReadingHeading != null
						&& stringSecondaryOpeningMeterReadingHeading.toLowerCase()
								.contains("secondary opening meter reading")
						&& stringSecondaryClosingMeterReadingHeading != null
						&& stringSecondaryClosingMeterReadingHeading.toLowerCase()
								.contains("secondary closing meter reading")
						&& stringRemarksHeading != null && stringRemarksHeading.toLowerCase().contains("remarks")
						&& stringAttendanceStatusHeading != null
						&& stringAttendanceStatusHeading.toLowerCase().contains("status")
						&& stringBreakdownHoursColumn != null
						&& stringBreakdownHoursColumn.toLowerCase().contains("breakdown hours"))) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid excel.");
				}

			}

			Set<Integer> errorRows = new HashSet<Integer>();
			List<MachineDPR> machineDprList = new ArrayList<MachineDPR>();

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

				XSSFRow row = worksheet.getRow(i);

				if (checkIfRowIsEmpty(row)) {
					break;
				}
				String stringDate = getCellValueFromCell(row.getCell(dprDateColumn));
				String stringRunningMode = getCellValueFromCell(row.getCell(dprRunningModeColumn));
				String stringShiftOrTripCount = getCellValueFromCell(row.getCell(dprShiftOrTripCountColumn));
				String stringPrimaryOpeningMeterReading = getCellValueFromCell(
						row.getCell(dprPrimaryOpeningMeterReadingColumn));
				String stringPrimaryClosingMeterReading = getCellValueFromCell(
						row.getCell(dprPrimaryClosingMeterReadingColumn));
				String stringSecondaryOpeningMeterReading = getCellValueFromCell(
						row.getCell(dprSecondaryOpeningMeterReadingColumn));
				String stringSecondaryClosingMeterReading = getCellValueFromCell(
						row.getCell(dprSecondaryClosingMeterReadingColumn));
				String stringAttendanceStatus = getCellValueFromCell(row.getCell(dprAttendanceStatus));
				String stringRemarks = getCellValueFromCell(row.getCell(dprRemarksColumn));
				String stringBreakdownHours = getCellValueFromCell(row.getCell(dprBreakdownHoursColumn));

				if (stringDate == null || stringDate.isEmpty() || stringRunningMode == null
						|| stringRunningMode.isEmpty() || stringShiftOrTripCount == null
						|| stringShiftOrTripCount.isEmpty() || stringPrimaryOpeningMeterReading == null
						|| stringPrimaryOpeningMeterReading.isEmpty() || stringPrimaryClosingMeterReading == null
						|| stringPrimaryClosingMeterReading.isEmpty() || stringAttendanceStatus == null
						|| stringAttendanceStatus.isEmpty()) {
					errorRows.add(i);
					continue;
				}

				Date date = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
				MachineryRunningMode runningMode = null;
				MachineryShifts shift = null;
				Integer tripCounts = 0;
				Double primaryOpeningMeterReading = Double.parseDouble(stringPrimaryOpeningMeterReading);
				Double primaryClosingMeterReading = Double.parseDouble(stringPrimaryClosingMeterReading);
				Double secondaryOpeningMeterReading = null;
				Double secondaryClosingMeterReading = null;
				MachineryAttendanceStatus attendanceStatus = null;
				Double breakdownHours = null;

				// set machinery running mode
				for (MachineryRunningMode obj : MachineryRunningMode.values()) {
					if (obj.name().trim().equalsIgnoreCase(stringRunningMode.trim())) {
						runningMode = obj;
					}
				}

				if (runningMode == null) {
					errorRows.add(i);
					continue;
				}

				if (runningMode.equals(MachineryRunningMode.TRIP)) {
					try {
						tripCounts = Integer.parseInt(stringShiftOrTripCount);
					} catch (Exception e) {
						errorRows.add(i);
						continue;
					}
				}

				// set machinery running mode
				if (runningMode.equals(MachineryRunningMode.SHIFTS)) {
					for (MachineryShifts obj : MachineryShifts.values()) {
						if (obj.name().trim().equalsIgnoreCase(stringShiftOrTripCount.trim())) {
							shift = obj;
						}
					}
					if (shift == null) {
						errorRows.add(i);
						continue;
					}
				}

				// set machinery attendance status
				if (stringAttendanceStatus != null) {
					for (MachineryAttendanceStatus status : MachineryAttendanceStatus.values()) {
						if (status.name().trim().equalsIgnoreCase(stringAttendanceStatus.trim())) {
							attendanceStatus = status;
						}
					}
					if (attendanceStatus == null) {
						errorRows.add(i);
						continue;
					}
				}
// 					BreakdownHours Check

				if (shift != null && stringBreakdownHours != null) {
					try {
						breakdownHours = Double.parseDouble(stringBreakdownHours);
					} catch (Exception e) {
						errorRows.add(i);
						continue;
					}
				}

				if (shift != null && stringBreakdownHours != null) {
					Boolean validBreakdownHours = false;
					if (breakdownHours >= 0 && breakdownHours <= 24) {
						validBreakdownHours = true;
					}
					if (!validBreakdownHours) {
						errorRows.add(i);
						continue;
					}

				}

				if (date == null || date.after(DateUtil.dateWithoutTime(new Date()))
						|| primaryOpeningMeterReading == null || primaryOpeningMeterReading.doubleValue() <= 0.0
						|| primaryClosingMeterReading == null || primaryClosingMeterReading.doubleValue() <= 0.0
						|| primaryOpeningMeterReading.doubleValue() > primaryClosingMeterReading.doubleValue()) {
					errorRows.add(i);
					continue;
				}

				if (equipment.getCategory() != null && equipment.getCategory().getIsMultiFuel() != null
						&& equipment.getCategory().getIsMultiFuel()) {
					if (stringSecondaryOpeningMeterReading == null || stringSecondaryClosingMeterReading == null
							|| stringSecondaryOpeningMeterReading.isEmpty()
							|| stringSecondaryClosingMeterReading.isEmpty()) {
						errorRows.add(i);
						continue;
					}

					try {
						secondaryOpeningMeterReading = Double.parseDouble(stringSecondaryOpeningMeterReading);
						secondaryClosingMeterReading = Double.parseDouble(stringSecondaryClosingMeterReading);
					} catch (Exception e) {
						errorRows.add(i);
						continue;
					}

					if (date == null || date.after(DateUtil.dateWithoutTime(new Date()))
							|| secondaryOpeningMeterReading == null || secondaryOpeningMeterReading.doubleValue() <= 0.0
							|| secondaryClosingMeterReading == null || secondaryClosingMeterReading.doubleValue() <= 0.0
							|| secondaryOpeningMeterReading.doubleValue() > secondaryClosingMeterReading
									.doubleValue()) {
						errorRows.add(i);
						continue;
					}

				}

				MachineDPR machineDPR = new MachineDPR(null, date, runningMode, shift,
						importExcelRequest.getMachineType(), importExcelRequest.getMachineId(),
						primaryOpeningMeterReading, primaryClosingMeterReading, secondaryOpeningMeterReading,
						secondaryClosingMeterReading, primaryOpeningMeterReading, primaryClosingMeterReading,
						secondaryOpeningMeterReading, secondaryClosingMeterReading, tripCounts, stringRemarks,
						breakdownHours, attendanceStatus, importExcelRequest.getSiteId(), true, new Date(),
						importExcelRequest.getUserDetail().getId(), new Date(),
						importExcelRequest.getUserDetail().getId());

				machineDprList.add(machineDPR);

			}

			if (!errorRows.isEmpty()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Error found for these following rows :" + errorRows);
			}

			if (machineDprList.size() > 0) {

				Collections.sort(machineDprList, new Comparator<MachineDPR>() {
					public int compare(MachineDPR e1, MachineDPR e2) {
						if (e1.getDated() == e2.getDated())
							return 0;
						return e1.getDated().getTime() < e2.getDated().getTime() ? -1 : 1;
					}
				});

				Set<Long> dbMachineryDprIds = new HashSet<>();

				for (MachineDPR mDpr : machineDprList) {
					Boolean tripEntryFound = false;
					Boolean dayTimeEntryFound = false;
					Boolean nightTimeEntryFound = false;
					for (MachineDPR nestedMDprObj : machineDprList) {
						if (mDpr.getDated().equals(nestedMDprObj.getDated())) {
							if (nestedMDprObj.getRunningMode().equals(MachineryRunningMode.TRIP)) {

								if (!tripEntryFound) {
									tripEntryFound = true;
								} else {
									return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
											"Multiple trip entries found for dated : "
													+ dateFormatter.format(nestedMDprObj.getDated()));
								}
							}

							if (nestedMDprObj.getRunningMode().equals(MachineryRunningMode.SHIFTS)
									&& nestedMDprObj.getShift().equals(MachineryShifts.DAYTIME)) {

								if (!dayTimeEntryFound) {
									dayTimeEntryFound = true;
								} else {
									return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
											"Multiple daytime entries found for dated : "
													+ dateFormatter.format(nestedMDprObj.getDated()));
								}
							}

							if (nestedMDprObj.getRunningMode().equals(MachineryRunningMode.SHIFTS)
									&& nestedMDprObj.getShift().equals(MachineryShifts.NIGHTTIME)) {

								if (!nightTimeEntryFound) {
									nightTimeEntryFound = true;
								} else {
									return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
											"Multiple daytime entries found for dated : "
													+ dateFormatter.format(nestedMDprObj.getDated()));
								}
							}

						}

					}

					if (tripEntryFound && (dayTimeEntryFound || nightTimeEntryFound)) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Multiple running mode entries found for dated : "
										+ dateFormatter.format(mDpr.getDated()));
					}
				}

				Date fromDate = DateUtil.dateWithoutTime(machineDprList.get(0).getDated());
				Date toDate = DateUtil.dateWithoutTime(machineDprList.get(machineDprList.size() - 1).getDated());

				List<MachineDPR> dbMachineDprList = dprDao.fetchMachineDprList(importExcelRequest.getMachineId(),
						importExcelRequest.getMachineType(), fromDate, toDate, importExcelRequest.getSiteId());

				List<MachineDPR> listToUpdate = new ArrayList<MachineDPR>();
				List<MachineDPR> listToSave = new ArrayList<MachineDPR>();

				for (MachineDPR mDpr : machineDprList) {

					// override actuals readings
					mDpr.setPrimaryOpeningActualReading(mDpr.getPrimaryOpeningActualReading());
					mDpr.setPrimaryClosingActualReading(mDpr.getPrimaryClosingActualReading());
					mDpr.setSecondaryOpeningActualReading(mDpr.getSecondaryOpeningActualReading());
					mDpr.setSecondaryClosingActualReading(mDpr.getSecondaryClosingActualReading());

					if (mDpr.getMachineType().equals((byte) MachineType.Equipment.ordinal())) {

						if (mDpr.getRunningMode().equals(MachineryRunningMode.TRIP)) {
							if (equipment.getCategory().getIsMultiFuel()) {
								if (mDpr.getSecondaryOpeningMeterReading() == null) {
									mDpr.setSecondaryOpeningMeterReading(0D);
								}
								if (mDpr.getSecondaryClosingMeterReading() == null) {
									mDpr.setSecondaryClosingMeterReading(0D);
								}
								if (mDpr.getSecondaryOpeningActualReading() == null) {
									mDpr.setSecondaryOpeningActualReading(0D);
								}
								if (mDpr.getSecondaryClosingActualReading() == null) {
									mDpr.setSecondaryClosingActualReading(0D);
								}
							}

						}
						if (equipment.getCategory().getIsMultiFuel()) {
							if (mDpr.getSecondaryOpeningMeterReading() == null
									|| mDpr.getSecondaryOpeningActualReading() == null
									|| mDpr.getSecondaryClosingMeterReading() == null
									|| mDpr.getSecondaryClosingActualReading() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Please provide valid secondary meter reading :"
												+ dateFormatter.format(mDpr.getDated()));
							}
						}
					}

					Boolean hasFound = false;
					for (MachineDPR dbMachineDpr : dbMachineDprList) {
						if (DateUtil.dateWithoutTime(mDpr.getDated())
								.equals(DateUtil.dateWithoutTime(dbMachineDpr.getDated()))) {
							if (dbMachineDpr.getRunningMode().equals(mDpr.getRunningMode())
									&& (mDpr.getRunningMode().equals(MachineryRunningMode.TRIP)
											|| (mDpr.getRunningMode().equals(MachineryRunningMode.SHIFTS)
													&& dbMachineDpr.getShift().equals(mDpr.getShift())))) {
								mDpr.setId(dbMachineDpr.getId());
								hasFound = true;
								dbMachineryDprIds.add(dbMachineDpr.getId());
							}
						}
					}
					if (!hasFound) {
						listToSave.add(mDpr);
					} else {
						listToUpdate.add(mDpr);
					}

				}
				for (MachineDPR mDpr : dbMachineDprList) {

					if (!dbMachineryDprIds.contains(mDpr.getId())) {
						mDpr.setIsActive(false);
						mDpr.setModifiedOn(new Date());
						mDpr.setModifiedBy(importExcelRequest.getUserDetail().getId());
						dprDao.mergeAndUpdateMachineDpr(mDpr);

					}

				}

				for (MachineDPR mDprToSave : listToSave) {
					dprDao.saveMachineDpr(mDprToSave);
				}

				for (MachineDPR mDprToUpdate : listToUpdate) {
					System.out.println(mDprToUpdate);
					dprDao.mergeAndUpdateMachineDpr(mDprToUpdate);
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	private boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellTypeEnum() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
				return false;
			}
		}
		return true;
	}

	public Boolean updateEquipmentSummary(List<MachineDPR> machineDprList) {

		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					EquipmentSummary dbObj = equipmentSummaryDao.fetchEquipmentSummary(
							machineDprList.get(0).getMachineType(), machineDprList.get(0).getMachineId());

					List<MachineDPR> machineDprDbObjList = dprDao.fetchMachineDprListTillCurrentDate(
							machineDprList.get(0).getMachineId(), machineDprList.get(0).getMachineType());

					if (machineDprDbObjList.size() > 0) {
						EquipmentSummary equipmentSummary = new EquipmentSummary();
						for (MachineDPR mDpr : machineDprDbObjList) {
							equipmentSummary
									.setPrimaryOpeningMeterReading(equipmentSummary.getPrimaryOpeningMeterReading()
											+ mDpr.getPrimaryOpeningMeterReading());
							equipmentSummary
									.setPrimaryOpeningActualReading(equipmentSummary.getPrimaryOpeningActualReading()
											+ mDpr.getPrimaryOpeningActualReading());
							equipmentSummary
									.setPrimaryClosingMeterReading(equipmentSummary.getPrimaryClosingMeterReading()
											+ mDpr.getPrimaryClosingMeterReading());
							equipmentSummary
									.setPrimaryClosingActualReading(equipmentSummary.getPrimaryClosingActualReading()
											+ mDpr.getPrimaryClosingActualReading());
							equipmentSummary
									.setSecondaryOpeningMeterReading(equipmentSummary.getSecondaryOpeningMeterReading()
											+ mDpr.getSecondaryOpeningMeterReading());
							equipmentSummary.setSecondaryOpeningActualReading(
									equipmentSummary.getSecondaryOpeningActualReading()
											+ mDpr.getSecondaryOpeningActualReading());
							equipmentSummary
									.setSecondaryClosingMeterReading(equipmentSummary.getSecondaryClosingMeterReading()
											+ mDpr.getSecondaryClosingMeterReading());
							equipmentSummary.setSecondaryClosingActualReading(
									equipmentSummary.getSecondaryClosingActualReading()
											+ mDpr.getSecondaryClosingActualReading());

						}
						equipmentSummary.setMachineType(machineDprList.get(0).getMachineType());
						equipmentSummary.setMachineId(machineDprList.get(0).getMachineId());
						equipmentSummary.setIsActive(true);
						equipmentSummary.setUpdatedOn(new Date());
						if (equipmentSummary.getId() == null) {
//							Boolean hasSaved = equipmentSummaryDao.saveEquipmentSummary(equipmentSummary);
							equipmentSummaryDao.saveEquipmentSummary(equipmentSummary);
//							return hasSaved;
						}

						if (equipmentSummary.getId() != null) {
							equipmentSummary.setId(dbObj.getId());
//							Boolean hasUpdated = equipmentSummaryDao.updateEquipmentSummary(equipmentSummary);
							equipmentSummaryDao.updateEquipmentSummary(equipmentSummary);
//							return hasUpdated;

						}

					}
//					return false;
				}
			}).start();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return null;
		}

	}

	public String getCellValueFromCell(XSSFCell cell) {

		String value = null;
		Double numericValue = null;

		if (cell != null) {
			switch (cell.getCellTypeEnum()) {
			case STRING:
				value = cell.getStringCellValue();
				break;
			case NUMERIC:

				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					value = dateFormat.format(cell.getDateCellValue());
				} else {
					numericValue = cell.getNumericCellValue();
					value = (new BigDecimal(numericValue)).toPlainString();
				}
				break;

			default:
				value = null;
				break;
			}
		}
		return value != null && !value.isEmpty() ? value.trim() : null;
	}

	@Override
	public CustomResponse getMachineryAttendnaceStatus() {
		try {
			List<MachineryAttendanceStatusResponse> machineryAttendanceStatusList = new ArrayList<>();

			for (MachineryAttendanceStatus status : MachineryAttendanceStatus.values()) {
				machineryAttendanceStatusList.add(new MachineryAttendanceStatusResponse(status.getId(), status.name()));

			}
			return new CustomResponse(Responses.SUCCESS.getCode(), machineryAttendanceStatusList,
					Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getMachineryRunningMode() {
		try {
			List<MachineryRunningModeResponse> machineryRunningModeList = new ArrayList<>();

			for (MachineryRunningMode mode : MachineryRunningMode.values()) {
				machineryRunningModeList.add(new MachineryRunningModeResponse(mode.getId(), mode.name()));

			}
			return new CustomResponse(Responses.SUCCESS.getCode(), machineryRunningModeList,
					Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getMachineryShifts() {
		try {
			List<MachineryShiftsResponse> machineryShiftList = new ArrayList<>();

			for (MachineryShifts shift : MachineryShifts.values()) {
				machineryShiftList.add(new MachineryShiftsResponse(shift.getId(), shift.name()));

			}
			return new CustomResponse(Responses.SUCCESS.getCode(), machineryShiftList, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

//	MachineDPR nextDateDpr = dprDao.fetchNextDateMachineDprByDate(requestDTO.getDated(),
//			requestDTO.getMachineType(), requestDTO.getMachineId(), requestDTO.getSiteId());
//	MachineDPR previousDateDpr = dprDao.fetchPreviousDateMachineDprByDate(requestDTO.getDated(),
//			requestDTO.getMachineType(), requestDTO.getMachineId(), requestDTO.getSiteId());

	// validate primary reading
//	if (nextDateDpr != null
//			&& requestDTO.getPrimaryClosingActualReading() > nextDateDpr.getPrimaryOpeningActualReading()) {
//		return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//				"Primary closing actual reading cannot be greater than next date DPR  primary opening actual reading.");
//	}
//	if (previousDateDpr != null
//			&& requestDTO.getPrimaryOpeningActualReading() < previousDateDpr.getPrimaryClosingActualReading()) {
//		return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//				"Primary opening actual reading cannot be less than previous date DPR  primary closing actual reading.");
//	}
//
//	// validate secondary reading
//	if (nextDateDpr != null && requestDTO.getSecondaryClosingActualReading() != null
//			&& nextDateDpr.getSecondaryOpeningActualReading() != null
//			&& requestDTO.getSecondaryClosingActualReading() > nextDateDpr.getSecondaryOpeningActualReading()) {
//		return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//				"Secondary closing actual reading cannot be greater than next date DPR  secondary opening actual reading.");
//	}
//	if (previousDateDpr != null && requestDTO.getSecondaryOpeningActualReading() != null
//			&& previousDateDpr.getSecondaryClosingActualReading() != null && requestDTO
//					.getSecondaryOpeningActualReading() < previousDateDpr.getSecondaryClosingActualReading()) {
//		return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//				"Secondary opening actual reading cannot be less than previous date DPR  Secondary closing actual reading.");
//	}

}
