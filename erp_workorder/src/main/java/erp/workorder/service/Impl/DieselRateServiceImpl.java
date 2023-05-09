package erp.workorder.service.Impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
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

import erp.workorder.dao.DieselRateDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.DieselRateMappingDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.request.DieselRateFetchRequest;
import erp.workorder.entity.DieselRateMapping;
import erp.workorder.entity.DieselRateTransacs;
import erp.workorder.enums.Responses;
import erp.workorder.service.DieselRateService;
import erp.workorder.util.DateUtil;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class DieselRateServiceImpl implements DieselRateService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private DieselRateDao dieselRateDao;

	@Override
	public CustomResponse saveDieselRate(DieselRateMappingDTO dieselRateMappingDTO) {
		try {
			DieselRateMapping dieselRateMapping = setObject.dieselRateMappingDtoToEntity(dieselRateMappingDTO);

			dieselRateMapping.setModifiedOn(new Date());
			Long id = dieselRateDao.saveDieselRate(dieselRateMapping);

			return new CustomResponse(Responses.SUCCESS.getCode(),
					((id != null && id.longValue() > 0L) ? id : "Already exists..."), Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getDieselRates(SearchDTO search) {
		try {
			List<DieselRateMapping> dieselRateMappings = dieselRateDao.fetchDieselRates(search);
			List<DieselRateMappingDTO> obj = new ArrayList<>();
			Double weightedRate = 0.0;
			if (dieselRateMappings != null && dieselRateMappings.size() > 0) {
				for (DieselRateMapping drm : dieselRateMappings) {
					weightedRate = weightedRate + drm.getRate();
					obj.add(setObject.dieselRateMappingEntityToDto(drm));
				}
				weightedRate = weightedRate / dieselRateMappings.size();
			}
			DieselRateFetchRequest resultObj = new DieselRateFetchRequest(obj, weightedRate);

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getDieselRateById(SearchDTO search) {
		try {
			DieselRateMapping dieselRate = dieselRateDao.fetchDieselRateById(search.getId());
			DieselRateMappingDTO result = setObject.dieselRateMappingEntityToDto(dieselRate);
			if (result == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), "Does not exist...",
						Responses.SUCCESS.toString());

			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateDieselRate(DieselRateMappingDTO dieselRateDTO) {
		try {
			DieselRateMapping dbObj = dieselRateDao.fetchDieselRateById(dieselRateDTO.getId());
			if (dbObj == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), "Does not exist...",
						Responses.SUCCESS.toString());
			boolean needTransac = false;
			if (!(dbObj.getSiteId().equals(dieselRateDTO.getSiteId()))
					&& dbObj.getRate().equals(dieselRateDTO.getRate())
					&& dbObj.getDate().equals(dieselRateDTO.getDate())) {
				needTransac = true;
			}

			dbObj.setDate(dieselRateDTO.getDate());
			dbObj.setRate(dieselRateDTO.getRate());
			dbObj.setModifiedOn(new Date());
			dbObj.setModifiedBy(dieselRateDTO.getModifiedBy());
			Boolean isSaved = dieselRateDao.updateDieselRateMapping(dbObj);
			if (isSaved && needTransac) {
				DieselRateTransacs dieselRateTransac = setObject.dieselRateEntityMappingToTransac(dbObj);
				dieselRateTransac.setRate(dbObj.getRate());
				dieselRateTransac.setCreatedOn(new Date());
				dieselRateTransac.setCreatedBy(dbObj.getModifiedBy());
				dieselRateTransac.setDate(dbObj.getDate());
				dieselRateTransac.setIsActive(dbObj.getIsActive());
				dieselRateDao.saveDieselRateTransac(dieselRateTransac);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), isSaved, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateDieselRate(SearchDTO search) {

		try {
			DieselRateMapping dieselRate = dieselRateDao.fetchDieselRateById(search.getId());

			DieselRateTransacs dieselRateTransac = setObject.dieselRateEntityMappingToTransac(dieselRate);
			dieselRate.setIsActive(false);
			dieselRate.setModifiedOn(new Date());
			dieselRate.setModifiedBy(search.getUserId());
			Boolean update = dieselRateDao.updateDieselRateMapping(dieselRate);
			if (update) {

				dieselRateTransac.setCreatedOn(new Date());
				dieselRateDao.saveDieselRateTransac(dieselRateTransac);
				return new CustomResponse(Responses.SUCCESS.getCode(), update, Responses.SUCCESS.toString());
			}
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), false, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse importDieselRateMapping(MultipartFile excelFile, SearchDTO search) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {

			XSSFSheet worksheet = workbook.getSheetAt(0);
			List<DieselRateMapping> dieselRates = new ArrayList<>();
			if (search.getSiteId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide siteId.");
			}
			String stringDateHeading = getCellValueFromCell(worksheet.getRow(0).getCell(1));
			String stringRateHeading = getCellValueFromCell(worksheet.getRow(0).getCell(2));

			if (!(stringRateHeading != null && stringRateHeading.toLowerCase().contains("rate")
					&& stringDateHeading != null && stringDateHeading.toLowerCase().contains("date"))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid excel.");
			}

			int drDateColumn = 1;
			int drRateColumn = 2;

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

				XSSFRow row = worksheet.getRow(i);
				String stringRate = getCellValueFromCell(row.getCell(drRateColumn));
				String stringDate = getCellValueFromCell(row.getCell(drDateColumn));
				if (stringDate == null || stringDate.isEmpty() || stringRate == null || stringRate.isEmpty()) {
					continue;
				}
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
				Double rate = Double.parseDouble(stringRate);
				if (date == null || rate == null || rate.doubleValue() <= 0.0) {
					continue;
				}
				date = DateUtil.dateMidDayTime(date);
				DieselRateMapping dieselRate = new DieselRateMapping(null, date, rate, search.getSiteId(), true,
						new Date(), search.getUserId());
				dieselRates.add(dieselRate);
				Long id = dieselRateDao.saveDieselRate(dieselRate);
				if (id == null) {
					DieselRateMapping savedDRMap = dieselRateDao.fetchDieselRateByDateAndSite(search.getSiteId(),
							dieselRate.getDate());
					DieselRateTransacs transac = setObject.dieselRateEntityMappingToTransac(dieselRate);
					if (!savedDRMap.getRate().equals(dieselRate.getRate())) {
						savedDRMap.setRate(dieselRate.getRate());
						savedDRMap.setModifiedBy(search.getUserId());
						savedDRMap.setModifiedOn(new Date());
						dieselRateDao.saveDieselRateTransac(transac);
					}
				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
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
				numericValue = cell.getNumericCellValue();
				value = (new BigDecimal(numericValue)).toPlainString();
				break;

			default:
				value = null;
				break;
			}
		}
		return value != null && !value.isEmpty() ? value.trim() : null;
	}

}
