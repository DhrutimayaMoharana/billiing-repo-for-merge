package erp.boq_mgmt.service.Impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dao.BoqItemDao;
import erp.boq_mgmt.dao.BorewellBoqMappingDao;
import erp.boq_mgmt.dao.CategoryItemDao;
import erp.boq_mgmt.dao.ChainageBorewellBoqQuantityDao;
import erp.boq_mgmt.dao.SubcategoryItemDao;
import erp.boq_mgmt.dao.UnitDao;
import erp.boq_mgmt.dao.WorkorderBoqWorkDao;
import erp.boq_mgmt.dto.BorewellBoqMappingDTO;
import erp.boq_mgmt.dto.CategoryItemDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.ExcelErrorDTO;
import erp.boq_mgmt.dto.GenericBoqMappingDTO;
import erp.boq_mgmt.dto.GenericBoqQtyImportMapDTO;
import erp.boq_mgmt.dto.GenericBoqsRenderDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.WorkorderTypeDTO;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.BorewellBoqMapping;
import erp.boq_mgmt.entity.CategoryItem;
import erp.boq_mgmt.entity.FileEntity;
import erp.boq_mgmt.entity.GenericBoqMappingHighway;
import erp.boq_mgmt.entity.GenericChainageBoqQuantityMapping;
import erp.boq_mgmt.entity.SubcategoryItem;
import erp.boq_mgmt.entity.Unit;
import erp.boq_mgmt.entity.WorkorderBoqWorkQtyMapping;
import erp.boq_mgmt.entity.WorkorderType;
import erp.boq_mgmt.enums.HighwayWorkOrderTypes;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.UnitTypes;
import erp.boq_mgmt.service.BorewellBoqMapService;
import erp.boq_mgmt.service.FileUtilService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class BorewellBoqMapServiceImpl implements BorewellBoqMapService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
//	@Autowired
//	private HighwayBoqMapDao bcmDao;
	@Autowired
	private BorewellBoqMappingDao bwbcmDao;
	@Autowired
	private UnitDao unitDao;
	@Autowired
	private BoqItemDao boqDao;
	@Autowired
	private CategoryItemDao categoryDao;
	@Autowired
	private SubcategoryItemDao subcategoryDao;
	@Autowired
	private FileUtilService fileUtilService;
//	@Autowired
//	private ChainageBoqQuantityDao cbqDao;

	@Autowired
	private ChainageBorewellBoqQuantityDao cbqDao;
	@Autowired
	private WorkorderBoqWorkDao woBoqWorkDao;

	@Override
	public CustomResponse importGenericBoqQuantityMappingExcel(MultipartFile excelFile, SearchDTO search) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {
			List<Integer> highwayWoTypeIds = new ArrayList<>();
			for (HighwayWorkOrderTypes woType : HighwayWorkOrderTypes.values()) {
				highwayWoTypeIds.add(woType.getTypeId());
			}
//			if(highwayWoTypeIds.contains(search.getTypeId())) {
			int freeTraversePotential = 20;
			Integer sheetCount = workbook.getNumberOfSheets();
			MultipartFile[] file = { excelFile };
			search.setFiles(file);
			FileEntity uploadedFile = fileUtilService.addFile(search);
			List<GenericBoqQtyImportMapDTO> gbqObjList = new ArrayList<>();
			List<ExcelErrorDTO> excelErrors = new ArrayList<ExcelErrorDTO>();
			DataFormatter formatter = new DataFormatter();
			if (highwayWoTypeIds.contains(search.getTypeId().intValue())) {
				HighwayWorkOrderTypes name = HighwayWorkOrderTypes.valueOf(search.getWorkOrderTypeId());
				if (sheetCount != null && sheetCount > 0) {
					for (int i = 0; i < sheetCount; i++) {
						XSSFSheet worksheet = workbook.getSheetAt(i);
						int initialRowIndex = 0, initialColumnIndex = 0;
						for (int j = 0; j < worksheet.getPhysicalNumberOfRows(); j++) {

							XSSFRow tempRow = worksheet.getRow(j);
							if (tempRow == null)
								continue;
							for (int k = 0; k < tempRow.getPhysicalNumberOfCells(); k++) {
								if (tempRow.getCell(k) != null
										&& (getCellValueFromCellWithEmptyStringOnNull(tempRow.getCell(k)).toLowerCase()
												.trim().contains("sdb code")
												|| getCellValueFromCellWithEmptyStringOnNull(tempRow.getCell(k))
														.toLowerCase().trim().contains("sdb no"))) {

									if (!(getCellValueFromCellWithEmptyStringOnNull(tempRow.getCell(k + 3))
											.toLowerCase().trim().contains("unit")
											&& getCellValueFromCellWithEmptyStringOnNull(tempRow.getCell(k + 5))
													.toLowerCase().trim().contains("rate")
											&& (getCellValueFromCellWithEmptyStringOnNull(tempRow.getCell(k + 6))
													.toLowerCase().trim().contains("quantity")
													|| getCellValueFromCellWithEmptyStringOnNull(tempRow.getCell(k + 6))
															.toLowerCase().trim().contains("qty"))
											&& getCellValueFromCellWithEmptyStringOnNull(tempRow.getCell(k + 8))
													.toLowerCase().trim().contains("remark"))) {
										workbook.close();
										return new CustomResponse(Responses.INVALID_EXCEL.getCode(), null,
												"Invalid excel format.");
									}
									initialRowIndex = j;
									initialColumnIndex = k;
									for (int m = initialRowIndex + 1; m < worksheet.getPhysicalNumberOfRows()
											+ 1; m++) {
										XSSFRow row = worksheet.getRow(m);
										if (row != null && m > initialRowIndex
												&& m < worksheet.getPhysicalNumberOfRows()
												&& (row.getCell(initialColumnIndex + 0) == null
														|| row.getCell(initialColumnIndex + 0).getCellTypeEnum()
																.equals(CellType.BLANK)
														|| formatter
																.formatCellValue(row.getCell(initialColumnIndex + 0))
																.toLowerCase().replaceAll(" ", "").trim().isEmpty())) {
											if (initialColumnIndex - 1 >= 0
													&& (row.getCell(initialColumnIndex - 1) == null || row
															.getCell(initialColumnIndex - 1).getCellTypeEnum()
															.equals(CellType.BLANK)
															|| formatter
																	.formatCellValue(
																			row.getCell(initialColumnIndex - 1))
																	.toLowerCase().replaceAll(" ", "").trim()
																	.isEmpty())) {
												continue;
											}
										}
										if (m == worksheet.getPhysicalNumberOfRows()) {
											if (gbqObjList != null) {

												List<Unit> units = unitDao.fetchUnits(search);
												Set<String> unitsNotPresent = new HashSet<>();
												for (GenericBoqQtyImportMapDTO obj : gbqObjList) {
													boolean isPresent = false;
													for (Unit unit : units) {
														if (obj.getUnit() != null && !obj.getUnit().trim().isEmpty()) {
															if (unit.getName().equalsIgnoreCase(obj.getUnit().trim())) {
																isPresent = true;
																break;
															}
														}
													}
													if (!isPresent && obj.getUnit() != null) {
														unitsNotPresent.add(obj.getUnit());
														excelErrors
																.add(new ExcelErrorDTO(
																		obj.getUnit() + " mentioned at row "
																				+ obj.getExcelRowNo(),
																		obj.getExcelRowNo()));
													}
												}
												if (unitsNotPresent.size() > 0) {
													workbook.close();
													String unitsNotPresentConcat = "| ";
													for (String unitName : unitsNotPresent) {
														unitsNotPresentConcat += unitName + " | ";
													}
													excelErrors.add(0,
															new ExcelErrorDTO(
																	"Add the following units before importing."
																			+ unitsNotPresentConcat,
																	0));
													return new CustomResponse(Responses.EXCEL_ERRORS.getCode(),
															excelErrors, Responses.EXCEL_ERRORS.toString());
												}

												List<GenericBoqMappingHighway> toSaveObjList = new ArrayList<>();
												int bbqObjListSize = gbqObjList.size();
												search.setSearchField(null);
												List<CategoryItem> allCategories = categoryDao
														.fetchCategoryItems(search);
												List<SubcategoryItem> allSubcategories = subcategoryDao
														.fetchSubcategoryItems(search);
												List<BoqItem> allBoqs = boqDao.fetchBoqItems(search);

												for (int d = 0; d < bbqObjListSize; d++) {
													search.setCategoryId(null);
													search.setSubcategoryId(null);
													GenericBoqQtyImportMapDTO excelObj = gbqObjList.get(d);
													if (excelObj.getStandardBookCode() != null
															&& !excelObj.getStandardBookCode().isEmpty()) {
														String categorySdbCode = null;
														if (allCategories != null) {
															for (CategoryItem item : allCategories) {
																if (item.getStandardBookCode() != null) {
																	String resultSdbCode = item.getStandardBookCode();

																	String resultExcelSdbCode = excelObj
																			.getStandardBookCode();
																	if (resultSdbCode
																			.equalsIgnoreCase(resultExcelSdbCode)) {
																		categorySdbCode = item.getCode();
																		break;
																	}
																}
															}
														}

														if (excelObj.getCode() == null && categorySdbCode == null) {
															excelErrors.add(new ExcelErrorDTO(
																	"System Code or SDB code is not available for category at row "
																			+ excelObj.getExcelRowNo(),
																	excelObj.getExcelRowNo()));
															continue;
														} else if (categorySdbCode != null) {
															excelObj.setCode(categorySdbCode);
														}
													}
													if ((excelObj.getStandardBookCode() != null
															&& !excelObj.getStandardBookCode().trim().isEmpty())
															|| (excelObj.getCode() != null
																	&& !excelObj.getCode().trim().isEmpty())) {
														if (excelObj.getUnit() != null
																&& !excelObj.getUnit().trim().isEmpty()) {
															GenericBoqMappingHighway obj = new GenericBoqMappingHighway(
																	null, search.getTypeId().intValue(),
																	name.toString(), null, null, null, null, null, null,
																	null, null, null, search.getSiteId(), uploadedFile,
																	null, null, true, new Date(), search.getUserId(),
																	search.getCompanyId());
															obj.setQuantity(excelObj.getQuantity() != null
																	? excelObj.getQuantity()
																	: 0.0);
															obj.setDescription(excelObj.getDescription());
															obj.setVendorDescription(excelObj.getVendorDescription());
															obj.setRate(excelObj.getRate() != null ? excelObj.getRate()
																	: 0.0);
															obj.setMaxRate(excelObj.getMaxRate() != null
																	? excelObj.getMaxRate()
																	: 0.0);
															if (excelObj.getStandardBookCode() != null
																	&& !excelObj.getStandardBookCode().isEmpty()) {
																String boqSdbCode = null;
																if (allBoqs != null) {
																	for (BoqItem item : allBoqs) {
																		if (item.getStandardBookCode() != null) {
																			String resultSdbCode = item
																					.getStandardBookCode();

																			String resultExcelSdbCode = excelObj
																					.getStandardBookCode();
																			if (resultSdbCode.equalsIgnoreCase(
																					resultExcelSdbCode)) {
																				boqSdbCode = item.getCode();
																				break;
																			}
																		}
																	}
																}

																if (excelObj.getCode() == null && boqSdbCode == null) {
																	excelErrors.add(new ExcelErrorDTO(
																			"System Code or SDB code is not available for BOQ at row "
																					+ excelObj.getExcelRowNo(),
																			excelObj.getExcelRowNo()));
																	continue;
																} else if (boqSdbCode != null) {
																	excelObj.setCode(boqSdbCode);
																}
															}
															obj.setRemark(excelObj.getRemark());
															search.setName(excelObj.getUnit());
															Long unitId = null;
															for (Unit unit : units) {
																if (unit.getName().equalsIgnoreCase(search.getName())) {
																	unitId = unit.getId();
																	break;
																}
															}
															search.setUnitId(unitId);
															search.setVendorDescription(
																	excelObj.getVendorDescription());
															search.setName(excelObj.getDescription());
															search.setCode(excelObj.getCode());
															search.setStandardBookCode(excelObj.getStandardBookCode());
															Long boqId = null;
															if (allBoqs != null) {
																for (BoqItem item : allBoqs) {
																	if (item.getStandardBookCode() != null
																			&& excelObj.getStandardBookCode() != null) {
																		String resultSdbCode = item
																				.getStandardBookCode();
																		String resultExcelSdbCode = excelObj
																				.getStandardBookCode();
																		if (resultSdbCode
																				.equalsIgnoreCase(resultExcelSdbCode)) {
																			boqId = item.getId();
																			break;
																		}
																	}
																	if (item.getCode() != null
																			&& excelObj.getCode() != null) {
																		String resultCode = item.getCode();
																		String resultExcelCode = excelObj.getCode();
																		if (resultCode
																				.equalsIgnoreCase(resultExcelCode)) {
																			boqId = item.getId();
																			break;
																		}
																	}
																}
															}
															if (boqId != null) {
																obj.setBoq(new BoqItem(boqId));
																toSaveObjList.add(obj);
															} else {
																excelErrors.add(new ExcelErrorDTO(
																		"Category is not provided for an unknown BOQ at row "
																				+ excelObj.getExcelRowNo(),
																		excelObj.getExcelRowNo()));
															}
														} else if (d < bbqObjListSize - 1) {
															int e = d;
															GenericBoqQtyImportMapDTO excelCatObj = gbqObjList.get(e);
															if ((excelCatObj.getStandardBookCode() != null
																	&& !excelObj.getStandardBookCode().trim().isEmpty())
																	|| (excelCatObj.getCode() != null
																			&& !excelObj.getCode().trim().isEmpty())) {

																if (excelCatObj.getStandardBookCode() != null
																		&& !excelCatObj.getStandardBookCode()
																				.isEmpty()) {
																	String categorySdbCode = null;
																	if (allCategories != null) {
																		for (CategoryItem item : allCategories) {
																			if (item.getStandardBookCode() != null) {
																				String resultSdbCode = item
																						.getStandardBookCode();

																				String resultExcelSdbCode = excelCatObj
																						.getStandardBookCode();
																				if (resultSdbCode.equalsIgnoreCase(
																						resultExcelSdbCode)) {
																					categorySdbCode = item.getCode();
																					break;
																				}
																			}
																		}
																	}

																	if (excelCatObj.getCode() == null
																			&& categorySdbCode == null) {
																		excelErrors.add(new ExcelErrorDTO(
																				"System Code or SDB code is not available for category at row "
																						+ excelCatObj.getExcelRowNo(),
																				excelCatObj.getExcelRowNo()));
																		continue;
																	} else if (categorySdbCode != null) {
																		excelCatObj.setCode(categorySdbCode);
																	}
																}
																search.setName(excelCatObj.getDescription());
																search.setVendorDescription(
																		excelCatObj.getVendorDescription());
																search.setCode(excelCatObj.getCode());
																search.setStandardBookCode(
																		excelCatObj.getStandardBookCode());
																Long categoryId = null;

																if (allCategories != null) {
																	for (CategoryItem item : allCategories) {
																		if (item.getStandardBookCode() != null
																				&& excelCatObj
																						.getStandardBookCode() != null) {
																			String resultSdbCode = item
																					.getStandardBookCode();
																			String resultExcelSdbCode = excelCatObj
																					.getStandardBookCode();
																			if (resultSdbCode.equalsIgnoreCase(
																					resultExcelSdbCode)) {
																				categoryId = item.getId();
																				break;
																			}
																		}
																		if (item.getCode() != null
																				&& excelCatObj.getCode() != null) {
																			String resultCode = item.getCode();

																			String resultExcelCode = excelCatObj
																					.getCode();
																			if (resultCode.equalsIgnoreCase(
																					resultExcelCode)) {
																				categoryId = item.getId();
																				break;
																			}
																		}
																	}
																}
																if (categoryId == null) {
																	excelErrors
																			.add(new ExcelErrorDTO(
																					"Add category with code "
																							+ search.getCode()
																							+ " before using at row "
																							+ excelCatObj
																									.getExcelRowNo(),
																					excelCatObj.getExcelRowNo()));
																	continue;
																}
																String categoryCodeAN = excelCatObj.getCode()
																		.toLowerCase().trim()
																		.replaceAll("[^A-Za-z0-9]", "");
																for (int f = e + 1; f < bbqObjListSize; f++) {
																	search.setCategoryId(categoryId);
																	search.setSubcategoryId(null);
																	GenericBoqQtyImportMapDTO excelNestedObj = gbqObjList
																			.get(f);
																	if (excelNestedObj.getStandardBookCode() != null
																			&& !excelNestedObj.getStandardBookCode()
																					.isEmpty()) {

																		String boqSdbCode = null;
																		if (allBoqs != null) {
																			for (BoqItem item : allBoqs) {
																				if (item.getStandardBookCode() != null) {
																					String resultSdbCode = item
																							.getStandardBookCode();

																					String resultExcelSdbCode = excelNestedObj
																							.getStandardBookCode();
																					if (resultSdbCode.equalsIgnoreCase(
																							resultExcelSdbCode)) {
																						boqSdbCode = item.getCode();
																						break;
																					}
																				}
																			}
																		}

																		if (excelNestedObj.getCode() == null
																				&& boqSdbCode == null) {
																			if (excelNestedObj
																					.getStandardBookCode() != null
																					&& !excelNestedObj
																							.getStandardBookCode()
																							.isEmpty()) {

																				String subcategorySdbCode = null;
																				if (allSubcategories != null) {
																					for (SubcategoryItem item : allSubcategories) {
																						if (item.getStandardBookCode() != null) {
																							String resultSdbCode = item
																									.getStandardBookCode();

																							String resultExcelSdbCode = excelNestedObj
																									.getStandardBookCode();
																							if (resultSdbCode
																									.equalsIgnoreCase(
																											resultExcelSdbCode)) {
																								subcategorySdbCode = item
																										.getCode();
																								break;
																							}
																						}
																					}
																				}

																				if (excelNestedObj.getCode() == null
																						&& subcategorySdbCode == null) {
																					excelErrors.add(new ExcelErrorDTO(
																							"System Code or SDB code is not available for sub-category at row "
																									+ excelNestedObj
																											.getExcelRowNo(),
																							excelNestedObj
																									.getExcelRowNo()));
																					continue;
																				} else if (subcategorySdbCode != null) {
																					excelNestedObj.setCode(
																							subcategorySdbCode);
																				}
																			}
																		} else if (boqSdbCode != null) {
																			excelNestedObj.setCode(boqSdbCode);
																		}
																	}
																	if (excelNestedObj.getCode() != null
																			&& excelNestedObj.getCode()
																					.replaceAll("[^A-Za-z0-9]", "")
																					.toLowerCase().trim()
																					.startsWith(categoryCodeAN)) {
																		search.setCategoryId(categoryId);
																		search.setSubcategoryId(null);
																		GenericBoqMappingHighway obj = new GenericBoqMappingHighway(
																				null, search.getTypeId().intValue(),
																				name.toString(), null, null, null,
																				new CategoryItem(categoryId), null,
																				null, null, null, null,
																				search.getSiteId(), uploadedFile, null,
																				null, true, new Date(),
																				search.getUserId(),
																				search.getCompanyId());
																		obj.setDescription(
																				excelNestedObj.getDescription());
																		obj.setVendorDescription(
																				excelNestedObj.getVendorDescription());
																		obj.setRemark(excelNestedObj.getRemark());
																		if (excelNestedObj.getUnit() != null
																				&& !excelNestedObj.getUnit().trim()
																						.isEmpty()) {
																			obj.setQuantity(
																					excelNestedObj.getQuantity() != null
																							? excelNestedObj
																									.getQuantity()
																							: 0.0);
																			obj.setRate(excelNestedObj.getRate() != null
																					? excelNestedObj.getRate()
																					: 0.0);
																			obj.setMaxRate(
																					excelNestedObj.getMaxRate() != null
																							? excelNestedObj
																									.getMaxRate()
																							: 0.0);
																			search.setName(excelNestedObj.getUnit());
																			Long unitId = null;
																			for (Unit unit : units) {
																				if (unit.getName().equalsIgnoreCase(
																						search.getName())) {
																					unitId = unit.getId();
																					break;
																				}
																			}
																			search.setUnitId(unitId);
																			if (excelNestedObj
																					.getStandardBookCode() != null
																					&& !excelNestedObj
																							.getStandardBookCode()
																							.isEmpty()) {
																				String boqSdbCode = null;
																				if (allBoqs != null) {
																					for (BoqItem item : allBoqs) {
																						if (item.getStandardBookCode() != null) {
																							String resultSdbCode = item
																									.getStandardBookCode();

																							String resultExcelSdbCode = excelNestedObj
																									.getStandardBookCode();
																							if (resultSdbCode
																									.equalsIgnoreCase(
																											resultExcelSdbCode)) {
																								boqSdbCode = item
																										.getCode();
																								break;
																							}
																						}
																					}
																				}

																				if (excelNestedObj.getCode() == null
																						&& boqSdbCode == null) {
																					excelErrors.add(new ExcelErrorDTO(
																							"System Code or SDB code is not available for BOQ at row "
																									+ excelNestedObj
																											.getExcelRowNo(),
																							excelNestedObj
																									.getExcelRowNo()));
																					continue;
																				} else if (boqSdbCode != null) {
																					excelNestedObj.setCode(boqSdbCode);
																				}
																			}
																			search.setName(
																					excelNestedObj.getDescription());
																			search.setVendorDescription(excelNestedObj
																					.getVendorDescription());
																			search.setCode(excelNestedObj.getCode());
																			search.setStandardBookCode(excelNestedObj
																					.getStandardBookCode());
																			search.setCategoryId(categoryId);
																			Long boqId = null;

																			if (allBoqs != null) {
																				for (BoqItem item : allBoqs) {
																					if (item.getStandardBookCode() != null
																							&& excelNestedObj
																									.getStandardBookCode() != null) {
																						String resultSdbCode = item
																								.getStandardBookCode();
																						String resultExcelSdbCode = excelNestedObj
																								.getStandardBookCode();
																						if (resultSdbCode
																								.equalsIgnoreCase(
																										resultExcelSdbCode)) {
																							boqId = item.getId();
																							break;
																						}
																					}
																					if (item.getCode() != null
																							&& excelNestedObj
																									.getCode() != null) {
																						String resultCode = item
																								.getCode();

																						String resultExcelCode = excelNestedObj
																								.getCode();
																						if (resultCode.equalsIgnoreCase(
																								resultExcelCode)) {
																							boqId = item.getId();
																							break;
																						}
																					}
																				}
																			}
																			if (boqId == null) {

																				BoqItem item = new BoqItem(null,
																						search.getCode(),
																						search.getStandardBookCode(),
																						search.getName(),
																						new Unit(search.getUnitId()),
																						new CategoryItem(
																								search.getCategoryId()),
																						new SubcategoryItem(search
																								.getSubcategoryId()),
																						true, new Date(),
																						search.getUserId(), new Date(),
																						search.getUserId(),
																						search.getCompanyId());
																				item = boqDao.forceSaveBoqItem(item);
																				boqId = item.getId();
																				allBoqs.add(item);
																			}

																			obj.setBoq(new BoqItem(boqId));
																			toSaveObjList.add(obj);
																		} else {
																			if (excelNestedObj
																					.getStandardBookCode() != null
																					&& !excelNestedObj
																							.getStandardBookCode()
																							.isEmpty()) {
																				String subcategorySdbCode = null;
																				if (allSubcategories != null) {
																					for (SubcategoryItem item : allSubcategories) {
																						if (item.getStandardBookCode() != null) {
																							String resultSdbCode = item
																									.getStandardBookCode();

																							String resultExcelSdbCode = excelNestedObj
																									.getStandardBookCode();
																							if (resultSdbCode
																									.equalsIgnoreCase(
																											resultExcelSdbCode)) {
																								subcategorySdbCode = item
																										.getCode();
																								break;
																							}
																						}
																					}
																				}

																				if (excelNestedObj.getCode() == null
																						&& subcategorySdbCode == null) {
																					excelErrors.add(new ExcelErrorDTO(
																							"System Code or SDB code is not available for sub-category at row "
																									+ excelNestedObj
																											.getExcelRowNo(),
																							excelNestedObj
																									.getExcelRowNo()));
																					continue;
																				} else if (subcategorySdbCode != null) {
																					excelNestedObj.setCode(
																							subcategorySdbCode);
																				}
																			}
																			search.setVendorDescription(excelNestedObj
																					.getVendorDescription());
																			search.setName(
																					excelNestedObj.getDescription());
																			search.setStandardBookCode(excelNestedObj
																					.getStandardBookCode());
																			search.setCode(excelNestedObj.getCode());
																			Long subcategoryId = null;

																			if (allSubcategories != null) {
																				for (SubcategoryItem item : allSubcategories) {
																					if (item.getStandardBookCode() != null
																							&& excelNestedObj
																									.getStandardBookCode() != null) {
																						String resultSdbCode = item
																								.getStandardBookCode();
																						String resultExcelSdbCode = excelNestedObj
																								.getStandardBookCode();
																						if (resultSdbCode
																								.equalsIgnoreCase(
																										resultExcelSdbCode)) {
																							subcategoryId = item
																									.getId();
																							break;
																						}
																					}
																					if (item.getCode() != null
																							&& excelNestedObj
																									.getCode() != null) {
																						String resultCode = item
																								.getCode();

																						String resultExcelCode = excelNestedObj
																								.getCode();
																						if (resultCode.equalsIgnoreCase(
																								resultExcelCode)) {
																							subcategoryId = item
																									.getId();
																							break;
																						}
																					}
																				}
																			}
																			if (subcategoryId == null) {

																				SubcategoryItem item = new SubcategoryItem(
																						null, search.getCode(),
																						search.getStandardBookCode(),
																						search.getName(), new Date(),
																						search.getUserId(), new Date(),
																						search.getUserId(), true,
																						search.getCompanyId());
																				item = subcategoryDao
																						.forceSaveSubcategoryItem(item);
																				subcategoryId = item.getId();
																				allSubcategories.add(item);
																			}

																			String subcategoryCodeAN = excelNestedObj
																					.getCode().toLowerCase().trim()
																					.replaceAll("[^A-Za-z0-9]", "");
																			;
																			obj.setSubcategory(
																					new SubcategoryItem(subcategoryId));
																			for (int g = f
																					+ 1; g < bbqObjListSize; g++) {
																				GenericBoqQtyImportMapDTO excelNestedBoqObj = gbqObjList
																						.get(g);
																				search.setCategoryId(categoryId);
																				search.setSubcategoryId(subcategoryId);
																				if (excelNestedBoqObj
																						.getStandardBookCode() != null
																						&& !excelNestedBoqObj
																								.getStandardBookCode()
																								.isEmpty()) {
																					String subcategorySdbCode = null;
																					if (allSubcategories != null) {
																						for (SubcategoryItem item : allSubcategories) {
																							if (item.getStandardBookCode() != null) {
																								String resultSdbCode = item
																										.getStandardBookCode();

																								String resultExcelSdbCode = excelNestedBoqObj
																										.getStandardBookCode();
																								if (resultSdbCode
																										.equalsIgnoreCase(
																												resultExcelSdbCode)) {
																									subcategorySdbCode = item
																											.getCode();
																									break;
																								}
																							}
																						}
																					}

																					if (excelNestedBoqObj
																							.getCode() == null
																							&& subcategorySdbCode == null) {
																						if (excelNestedBoqObj
																								.getStandardBookCode() != null
																								&& !excelNestedBoqObj
																										.getStandardBookCode()
																										.isEmpty()) {

																							String boqSdbCode = null;
																							if (allBoqs != null) {
																								for (BoqItem item : allBoqs) {
																									if (item.getStandardBookCode() != null) {
																										String resultSdbCode = item
																												.getStandardBookCode();

																										String resultExcelSdbCode = excelNestedBoqObj
																												.getStandardBookCode();
																										if (resultSdbCode
																												.equalsIgnoreCase(
																														resultExcelSdbCode)) {
																											boqSdbCode = item
																													.getCode();
																											break;
																										}
																									}
																								}
																							}

																							if (excelNestedBoqObj
																									.getCode() == null
																									&& boqSdbCode == null) {
																								if (excelNestedBoqObj
																										.getStandardBookCode() != null
																										&& !excelNestedBoqObj
																												.getStandardBookCode()
																												.isEmpty()) {
																									String categorySdbCode = null;
																									if (allCategories != null) {
																										for (CategoryItem item : allCategories) {
																											if (item.getStandardBookCode() != null) {
																												String resultSdbCode = item
																														.getStandardBookCode();

																												String resultExcelSdbCode = excelNestedBoqObj
																														.getStandardBookCode();
																												if (resultSdbCode
																														.equalsIgnoreCase(
																																resultExcelSdbCode)) {
																													categorySdbCode = item
																															.getCode();
																													break;
																												}
																											}
																										}
																									}

																									if (excelNestedBoqObj
																											.getCode() == null
																											&& categorySdbCode == null) {
																										excelErrors.add(
																												new ExcelErrorDTO(
																														"System Code or SDB code is not available for category at row "
																																+ excelNestedBoqObj
																																		.getExcelRowNo(),
																														excelNestedBoqObj
																																.getExcelRowNo()));
																										continue;
																									} else if (categorySdbCode != null) {
																										excelNestedBoqObj
																												.setCode(
																														categorySdbCode);
																									}
																								}
																							} else if (boqSdbCode != null) {
																								excelNestedBoqObj
																										.setCode(
																												boqSdbCode);
																							}
																						}
																					} else if (subcategorySdbCode != null) {
																						excelNestedBoqObj.setCode(
																								subcategorySdbCode);
																					}
																				}
																				GenericBoqMappingHighway nestedObj = new GenericBoqMappingHighway();
																				BeanUtils.copyProperties(obj,
																						nestedObj);
																				if (excelNestedBoqObj.getUnit() != null
																						&& !excelNestedBoqObj.getUnit()
																								.trim().isEmpty()
																						&& excelNestedBoqObj.getCode()
																								.trim().toLowerCase()
																								.replaceAll(
																										"[^A-Za-z0-9]",
																										"")
																								.startsWith(
																										subcategoryCodeAN)) {
																					nestedObj.setQuantity(
																							excelNestedBoqObj
																									.getQuantity() != null
																											? excelNestedBoqObj
																													.getQuantity()
																											: 0.0);
																					nestedObj.setDescription(
																							excelNestedBoqObj
																									.getDescription());
																					nestedObj.setVendorDescription(
																							excelNestedBoqObj
																									.getVendorDescription());
																					if (excelNestedBoqObj
																							.getStandardBookCode() != null
																							&& !excelNestedBoqObj
																									.getStandardBookCode()
																									.isEmpty()) {
																						String boqSdbCode = null;
																						if (allBoqs != null) {
																							for (BoqItem item : allBoqs) {
																								if (item.getStandardBookCode() != null) {
																									String resultSdbCode = item
																											.getStandardBookCode();

																									String resultExcelSdbCode = excelNestedBoqObj
																											.getStandardBookCode();
																									if (resultSdbCode
																											.equalsIgnoreCase(
																													resultExcelSdbCode)) {
																										boqSdbCode = item
																												.getCode();
																										break;
																									}
																								}
																							}
																						}

																						if (excelNestedBoqObj
																								.getCode() == null
																								&& boqSdbCode == null) {
																							excelErrors.add(
																									new ExcelErrorDTO(
																											"System Code or SDB code is not available for BOQ at row "
																													+ excelNestedBoqObj
																															.getExcelRowNo(),
																											excelNestedBoqObj
																													.getExcelRowNo()));
																							continue;
																						} else if (boqSdbCode != null) {
																							excelNestedBoqObj.setCode(
																									boqSdbCode);
																						}
																					}
																					nestedObj
																							.setRemark(excelNestedBoqObj
																									.getRemark());
																					nestedObj.setSubcategory(
																							obj.getSubcategory());
																					nestedObj.setRate(excelNestedBoqObj
																							.getRate() != null
																									? excelNestedBoqObj
																											.getRate()
																									: 0.0);
																					nestedObj.setMaxRate(
																							excelNestedBoqObj
																									.getMaxRate() != null
																											? excelNestedBoqObj
																													.getMaxRate()
																											: 0.0);
																					search.setName(excelNestedBoqObj
																							.getUnit());

																					Long unitId = null;
																					for (Unit unit : units) {
																						if (unit.getName()
																								.equalsIgnoreCase(search
																										.getName())) {
																							unitId = unit.getId();
																							break;
																						}
																					}
																					search.setUnitId(unitId);
																					search.setName(excelNestedBoqObj
																							.getDescription());
																					search.setVendorDescription(
																							excelNestedBoqObj
																									.getVendorDescription());
																					search.setCode(excelNestedBoqObj
																							.getCode());
																					search.setStandardBookCode(
																							excelNestedBoqObj
																									.getStandardBookCode());
																					search.setCategoryId(categoryId);
																					search.setSubcategoryId(
																							subcategoryId);

																					Long boqId = null;

																					if (allBoqs != null) {
																						for (BoqItem item : allBoqs) {
																							if (item.getStandardBookCode() != null
																									&& excelNestedBoqObj
																											.getStandardBookCode() != null) {
																								String resultSdbCode = item
																										.getStandardBookCode();
																								String resultExcelSdbCode = excelNestedBoqObj
																										.getStandardBookCode();
																								if (resultSdbCode
																										.equalsIgnoreCase(
																												resultExcelSdbCode)) {
																									boqId = item
																											.getId();
																									break;
																								}
																							}
																							if (item.getCode() != null
																									&& excelNestedBoqObj
																											.getCode() != null) {
																								String resultCode = item
																										.getCode();

																								String resultExcelCode = excelNestedBoqObj
																										.getCode();
																								if (resultCode
																										.equalsIgnoreCase(
																												resultExcelCode)) {
																									boqId = item
																											.getId();
																									break;
																								}
																							}
																						}
																					}
																					if (boqId == null) {

																						BoqItem item = new BoqItem(null,
																								search.getCode(),
																								search.getStandardBookCode(),
																								search.getName(),
																								new Unit(search
																										.getUnitId()),
																								new CategoryItem(search
																										.getCategoryId()),
																								new SubcategoryItem(
																										search.getSubcategoryId()),
																								true, new Date(),
																								search.getUserId(),
																								new Date(),
																								search.getUserId(),
																								search.getCompanyId());
																						item = boqDao
																								.forceSaveBoqItem(item);
																						boqId = item.getId();
																						allBoqs.add(item);
																					}
																					nestedObj.setWorkorderTypeId(search
																							.getTypeId().intValue());
																					nestedObj
																							.setWorkorderTypeName(null);
																					nestedObj
																							.setBoq(new BoqItem(boqId));
																					toSaveObjList.add(nestedObj);
																					if (g == bbqObjListSize - 1)
																						f = g - 1;
																					continue;
																				}
																				f = g - 1;
																				break;
																			}
																		}
																		if (f == bbqObjListSize - 1)
																			d = f;
																		continue;
																	}
																	d = f - 1;
																	break;
																}
															}
														}
													}
												}
												List<GenericBoqMappingHighway> hbqToReturn = new ArrayList<>();
												if (toSaveObjList != null) {
													search.setBoqId(null);
													search.setCategoryId(null);
													search.setSubcategoryId(null);
													search.setDescription(null);
													search.setHighwayBoqId(null);
													search.setWorkType(search.getTypeId().intValue());
													if (excelErrors != null && excelErrors.size() > 0) {
														workbook.close();
														TransactionAspectSupport.currentTransactionStatus()
																.setRollbackOnly();
														return new CustomResponse(Responses.EXCEL_ERRORS.getCode(),
																excelErrors, Responses.EXCEL_ERRORS.toString());
													}
													List<GenericBoqMappingHighway> hbqs = bwbcmDao
															.fetchGenricCategoriesBoqs(search);
													List<GenericChainageBoqQuantityMapping> cbqs = cbqDao
															.fetchBoqWiseCbqV1(search);
													List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao
															.fetchWoBoqWorkQtys(search);
													for (GenericBoqMappingHighway hbqObj : toSaveObjList) {
														if (hbqObj.getBoq() == null)
															continue;

														List<GenericChainageBoqQuantityMapping> hbmCbqs = new ArrayList<GenericChainageBoqQuantityMapping>();
														if (cbqs != null) {
															for (GenericChainageBoqQuantityMapping obj : cbqs) {
																if (obj.getBoq().getId()
																		.equals(hbqObj.getBoq().getId())) {
																	hbmCbqs.add(obj);
																}
															}
														}

														Double totalArea = 0.0;
														Double totalVolume = 0.0;
														for (int cbqIdx = 0; cbqIdx < hbmCbqs.size(); cbqIdx++) {
															GenericChainageBoqQuantityMapping toSaveObj = hbmCbqs
																	.get(cbqIdx);
															if (cbqIdx == 0) {
																totalArea += (toSaveObj.getLhsQuantity()
																		+ toSaveObj.getRhsQuantity());
																continue;
															}
															GenericChainageBoqQuantityMapping previousToSaveObj = hbmCbqs
																	.get(cbqIdx - 1);
															String numericChainage = toSaveObj.getChainage().getName()
																	.replaceAll("[^0-9]", "");
															String previousChainage = previousToSaveObj.getChainage()
																	.getName().replaceAll("[^0-9]", "");
															Double length = Double.valueOf(numericChainage)
																	- Double.valueOf(previousChainage);
															Double previousArea = previousToSaveObj.getLhsQuantity()
																	+ previousToSaveObj.getRhsQuantity();
															Double currentArea = toSaveObj.getLhsQuantity()
																	+ toSaveObj.getRhsQuantity();
															totalArea += currentArea;
															if (toSaveObj.getStructureRemark() == null
																	|| toSaveObj.getStructureRemark().isBlank()) {
																totalVolume += ((currentArea + previousArea) / 2)
																		* length;
															}
														}

														Double totalBoqQuantity = totalArea;
														if (hbqObj.getBoq().getUnit() == null && allBoqs != null) {
															for (BoqItem item : allBoqs) {
																if (item.getId().equals(hbqObj.getBoq().getId())) {

																	hbqObj.setBoq(item);
																	break;
																}
															}
														}
														if (hbqObj.getBoq().getUnit().getType().getId()
																.equals(UnitTypes.VOLUME.getTypeId())) {
															totalBoqQuantity = totalVolume;
														}
														Double workorderQuantity = 0.0;
														if (woBoqQtys != null) {
															for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
																if (obj.getBoq().getId()
																		.equals(hbqObj.getBoq().getId())) {

																	workorderQuantity += obj.getQuantity();
																}
															}

														}
														if (hbqObj.getQuantity().doubleValue() < totalBoqQuantity
																.doubleValue()) {
															workbook.close();
															excelErrors.add(new ExcelErrorDTO(
																	"Quantity provided in BOQ with code "
																			+ hbqObj.getBoq().getCode()
																			+ " is less than quantity distributed in chainages for same BOQ.",
																	0));
															continue;

														}
														if (hbqObj.getQuantity().doubleValue() < workorderQuantity
																.doubleValue()) {
															workbook.close();
															excelErrors.add(new ExcelErrorDTO(
																	"Quantity provided in BOQ with code "
																			+ hbqObj.getBoq().getCode()
																			+ " is less than quantity issued in workorder for same BOQ.",
																	0));
															continue;
														}
														boolean isHbqSaved = false;
														if (hbqs != null) {
															for (GenericBoqMappingHighway hbq : hbqs) {
																if (hbq.getBoq().getId()
																		.equals(hbqObj.getBoq().getId())) {
																	hbq.setModifiedBy(hbqObj.getModifiedBy());
																	hbq.setModifiedOn(new Date());
																	hbq.setIsActive(true);
																	boolean qtyDiff = false;
																	if (!hbq.getQuantity().equals(hbqObj.getQuantity()))
																		qtyDiff = true;
																	if (!(hbq.getQuantity().equals(hbqObj.getQuantity())
																			&& hbq.getRate().equals(hbqObj.getRate())
																			&& hbq.getDescription()
																					.equals(hbqObj.getDescription()))) {
																		hbq.setQuantity(hbqObj.getQuantity() != null
																				? hbqObj.getQuantity()
																				: 0.0);
																		hbq.setDescription(hbqObj.getDescription());
																		hbq.setVendorDescription(
																				hbqObj.getVendorDescription());
																		hbq.setRate(hbqObj.getRate() != null
																				? hbqObj.getRate()
																				: 0.0);
																		hbq.setMaxRate(hbqObj.getMaxRate() != null
																				? hbqObj.getMaxRate()
																				: 0.0);
																		hbq.setFile(uploadedFile);
																		hbq.setRemark(hbqObj.getRemark());
																		if (qtyDiff) {
																			int currentQuantityVersion = hbq
																					.getQuantityVersion().intValue();
																			hbq.setQuantityVersion(
																					currentQuantityVersion + 1);
																		}
																		int currentVersion = hbq.getVersion()
																				.intValue();
																		hbq.setVersion(currentVersion + 1);
																		bwbcmDao.forceUpdateCategoryBoq(hbq);
//																	BorewellBoqTransac hbqTransac = setObject
//																			.BorewellBoqEntityMappingToTransac(hbq);
//																	hbqTransac.setCreatedOn(new Date());
//																	bwbcmDao.saveBoqCategoryTransac(hbqTransac);
																	}
																	hbqToReturn.add(hbq);
																	isHbqSaved = true;
																	break;
																}
															}
														}
														if (!isHbqSaved) {
															hbqObj.setVersion(0);
															hbqObj.setQuantityVersion(0);
															bwbcmDao.forceSaveCategoryBoq(hbqObj);
															GenericBoqMappingHighway hbm = bwbcmDao
																	.forceSaveCategoryBoq(hbqObj);
															if (hbm != null) {
																hbqObj.setId(hbm.getId());
																hbqToReturn.add(hbm);
																hbqs.add(hbm);
//															BorewellBoqTransac hbqTransac = setObject
//																	.BorewellBoqEntityMappingToTransac(hbqObj);
//															hbqTransac.setCreatedOn(new Date());
//															bwbcmDao.saveBoqCategoryTransac(hbqTransac);
															}
														}
													}
													if (hbqs != null) {
														for (GenericBoqMappingHighway hbq : hbqs) {
															boolean toRemove = true;
															for (GenericBoqMappingHighway obj : hbqToReturn) {
																if (obj.getId().equals(hbq.getId())) {
																	toRemove = false;
																	break;
																}
															}
															if (toRemove) {
																boolean inWorkorder = false;
																if (woBoqQtys != null) {
																	for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
																		if (obj.getBoq().getId()
																				.equals(hbq.getBoq().getId())) {
																			inWorkorder = true;
																			break;
																		}
																	}

																}
																if (inWorkorder) {
																	continue;
																}
																if (cbqs != null) {
																	for (GenericChainageBoqQuantityMapping obj : cbqs) {
																		if (obj.getBoq().getId()
																				.equals(hbq.getBoq().getId())) {
																			obj.setIsActive(false);
																			obj.setModifiedBy(search.getUserId());
																			obj.setModifiedOn(new Date());
																			Boolean cbqUpdate = cbqDao
																					.updateChainageGenericBoqQuantityMapping(
																							obj);
																			if (cbqUpdate) {
//																				ChainageBorewellBoqQuantityTransacs cbqTransac = setObject
//																						.cbqEntityMappingToTransac(obj);
//																				cbqTransac.setCreatedOn(new Date());
//																				cbqDao.saveChainageBorewellBoqQuantityTransac(
//																						cbqTransac);
																			}
																		}
																	}
																}

																int currentVersion = hbq.getVersion().intValue();
																hbq.setVersion(currentVersion + 1);
																hbq.setModifiedOn(new Date());
																hbq.setModifiedBy(search.getUserId());
																hbq.setIsActive(false);
																bwbcmDao.forceUpdateAfterDetachCategoryBoq(hbq);
//															BorewellBoqTransac hbqTransac = setObject
//																	.BorewellBoqEntityMappingToTransac(hbq);
//															hbqTransac.setCreatedOn(new Date());
//															bwbcmDao.saveBoqCategoryTransac(hbqTransac);
															}
														}
													}
												}

												workbook.close();
												if (excelErrors != null && excelErrors.size() > 0 && hbqToReturn != null
														&& hbqToReturn.size() > 0) {
													excelErrors.add(0, new ExcelErrorDTO(
															"Some of the BOQs have not imported successfully are mentioned below: ",
															0));
													return new CustomResponse(Responses.EXCEL_ERRORS.getCode(),
															excelErrors, Responses.EXCEL_ERRORS.toString());
												} else if (hbqToReturn != null && hbqToReturn.size() > 0) {
													return new CustomResponse(Responses.SUCCESS.getCode(),
															uploadedFile.getName() + " - Imported Successfully!",
															uploadedFile.getName() + " - Imported Successfully!");
												} else if (excelErrors != null && excelErrors.size() > 0) {
													TransactionAspectSupport.currentTransactionStatus()
															.setRollbackOnly();
													return new CustomResponse(Responses.EXCEL_ERRORS.getCode(),
															excelErrors, Responses.EXCEL_ERRORS.toString());
												}
												return new CustomResponse(Responses.SUCCESS.getCode(),
														"No data imported.", "No data imported.");
											}
										} else if (row != null) {

											String code = null;
											if (initialColumnIndex - 1 >= 0) {
												code = formatter.formatCellValue(row.getCell(initialColumnIndex - 1))
														.trim();
												if (code.isEmpty()) {
													code = null;
												}
											}
											String sdbCode = formatter
													.formatCellValue(row.getCell(initialColumnIndex + 0)).trim();
											if (sdbCode.isEmpty()) {
												sdbCode = null;
											}

											String description = formatter
													.formatCellValue(row.getCell(initialColumnIndex + 1)).trim();
											if (description.isEmpty()) {
												description = null;
											}

											String vendorDescription = formatter
													.formatCellValue(row.getCell(initialColumnIndex + 2)).trim();
											if (vendorDescription.isEmpty()) {
												vendorDescription = null;
											}

											String unit = formatter.formatCellValue(row.getCell(initialColumnIndex + 3))
													.trim();
											if (unit.isEmpty()) {
												unit = null;
											}
											Double rate = 0.0;
											String rateString = getCellValueFromCell(
													row.getCell(initialColumnIndex + 4));
											if (rateString != null) {
												rate = Double.valueOf(rateString);
											}
											Double maxRate = 0.0;
											String maxRateString = getCellValueFromCell(
													row.getCell(initialColumnIndex + 5));
											if (maxRateString != null) {
												maxRate = Double.valueOf(maxRateString);
											}
											Double quantity = 0.0;
											String quantityString = getCellValueFromCell(
													row.getCell(initialColumnIndex + 6));
											if (quantityString != null) {
												quantity = Double.valueOf(quantityString);
											}
											String remark = formatter
													.formatCellValue(row.getCell(initialColumnIndex + 8)).trim();
											if (remark.isEmpty()) {
												remark = null;
											}
											if ((code == null || code.trim().isEmpty())
													&& (sdbCode == null || sdbCode.trim().isEmpty())) {
												continue;
											}
											GenericBoqQtyImportMapDTO hbqImport = new GenericBoqQtyImportMapDTO(code,
													sdbCode, description, vendorDescription, unit, rate, maxRate,
													quantity, remark, m);
											gbqObjList.add(hbqImport);
										}
									}
								}
								if (k > freeTraversePotential)
									break;
							}
							if (j > freeTraversePotential)
								break;
						}
					}
				}
				workbook.close();
			} else {
				return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), "Invalid typeid to process the file.",
						"Invalid workordertypeId to process the file.");
			}

			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), e.getMessage(),
					"Problem occurred while processing the file.");
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
		return value != null && !value.trim().isEmpty() ? value.trim() : null;
	}

	public String getCellValueFromCellWithEmptyStringOnNull(XSSFCell cell) {

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
		return value != null && !value.trim().isEmpty() ? value.trim() : "";
	}

	@Override
	public CustomResponse deactivateGenericBoqMapping(SearchDTO search) {

		try {
			GenericBoqMappingHighway hbq = bwbcmDao.fetchGenricCategoryBoqById(search.getGenericBoqId(),
					search.getWorkOrderTypeId());
			search.setGenericBoqId(hbq.getId());
			search.setBoqId(hbq.getBoq().getId());
			List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao.fetchWoBoqWorkQtysByBoqId(search);
			boolean inWorkorder = false;
			if (woBoqQtys != null && woBoqQtys.size() > 0) {
				inWorkorder = true;

			}
			if (inWorkorder) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), false,
						"This borewell BOQ is used in workorder.");
			}
			List<GenericChainageBoqQuantityMapping> cbqs = cbqDao.fetchBoqWiseCbqV1(search);
			if (cbqs != null) {
				for (GenericChainageBoqQuantityMapping obj : cbqs) {
					if (obj.getBoq().getId().equals(hbq.getBoq().getId())) {
						obj.setIsActive(false);
						obj.setModifiedBy(search.getUserId());
						obj.setModifiedOn(new Date());
						Boolean cbqUpdate = cbqDao.updateChainageGenericBoqQuantityMapping(obj);
//						if (cbqUpdate) {
//							ChainageBorewellBoqQuantityTransacs cbqTransac = setObject.cbqEntityMappingToTransac(obj);
//							cbqTransac.setCreatedOn(new Date());
//							cbqDao.saveChainageBorewellBoqQuantityTransac(cbqTransac);
//						}
					}
				}
			}

			int currentVersion = hbq.getVersion().intValue();
			hbq.setVersion(currentVersion + 1);
			hbq.setModifiedOn(new Date());
			hbq.setModifiedBy(search.getUserId());
			hbq.setIsActive(false);
			bwbcmDao.forceUpdateAfterDetachCategoryBoq(hbq);
//			BorewellBoqTransac hbqTransac = setObject.BorewellBoqEntityMappingToTransac(hbq);
//			hbqTransac.setCreatedOn(new Date());
//			bwbcmDao.saveBoqCategoryTransac(hbqTransac);
			return new CustomResponse(Responses.SUCCESS.getCode(), true, "Removed!");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

//	@Override
//	public CustomResponse updateCategoryBoq(BorewellBoqMappingDTO bcmDTO) {
//		try {
//			BorewellBoqMapping dbObj = bwbcmDao.fetchCategoryBoqById(bcmDTO.getId());
//			if (dbObj == null)
//				return new CustomResponse(Responses.SUCCESS.getCode(), "Does not exist...",
//						Responses.SUCCESS.toString());
//			boolean needTransac = false;
//			if (!(dbObj.getSiteId().equals(bcmDTO.getSiteId()) && dbObj.getBoq().getId().equals(bcmDTO.getBoq().getId())
//					&& ((dbObj.getCategory() == null && bcmDTO.getCategory() == null)
//							|| (dbObj.getCategory() != null && bcmDTO.getCategory() != null
//									&& dbObj.getCategory().getId().equals(bcmDTO.getCategory().getId())))
//					&& ((dbObj.getSubcategory() == null && bcmDTO.getSubcategory() == null)
//							|| (dbObj.getSubcategory() != null && bcmDTO.getSubcategory() != null
//									&& dbObj.getSubcategory().getId().equals(bcmDTO.getSubcategory().getId())))
//					&& dbObj.getQuantity().equals(bcmDTO.getQuantity()) && dbObj.getRate().equals(bcmDTO.getRate())
//					&& dbObj.getMaxRate().equals(bcmDTO.getMaxRate())
//					&& dbObj.getDescription().equals(bcmDTO.getDescription())
//					&& dbObj.getRemark().equals(bcmDTO.getRemark())
//					&& dbObj.getIsActive().equals(bcmDTO.getIsActive()))) {
//				needTransac = true;
//			}
//			boolean qtyDiff = false;
//			if (!dbObj.getQuantity().equals(bcmDTO.getQuantity()))
//				qtyDiff = true;
//			BorewellBoqMapping bcmObj = setObject.updatedBoqCategoryMapping(dbObj,
//					setObject.boqCategoryMapDtoToEntity(bcmDTO));
//			if (qtyDiff)
//				bcmObj.setQuantityVersion(bcmObj.getQuantityVersion() + 1);
//			if (needTransac)
//				bcmObj.setVersion(bcmObj.getVersion() + 1);
//			Boolean isSaved = bwbcmDao.updateCategoryBoqMapping(bcmObj);
//			if (needTransac) {
//				BorewellBoqTransac bcmTransac = setObject.BorewellBoqEntityMappingToTransac(bcmObj);
//				bcmTransac.setCreatedOn(new Date());
//				bwbcmDao.saveBoqCategoryTransac(bcmTransac);
//			}
//			return new CustomResponse(Responses.SUCCESS.getCode(), isSaved, Responses.SUCCESS.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
//			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
//					Responses.PROBLEM_OCCURRED.toString());
//		}
//	}

	@Override
	public CustomResponse getGenericBoqs(SearchDTO search) {

		try {
			List<GenericBoqMappingHighway> bcmList = bwbcmDao.fetchGenricCategoriesBoqs(search);
			List<GenericBoqMappingDTO> bcmListDTO = new ArrayList<GenericBoqMappingDTO>();
			if (bcmList != null) {
				for (GenericBoqMappingHighway bcm : bcmList) {
					bcmListDTO.add(setObject.boqCategoryMapEntityToDto(bcm));
				}
			}
			if (bcmListDTO != null && bcmListDTO.size() > 0) {
				List<CategoryItem> categories = categoryDao.fetchCategoryItems(search);
				List<CategoryItemDTO> categoriesDTO = new ArrayList<>();
				for (CategoryItem category : categories)
					categoriesDTO.add(setObject.categoryItemEntityToDto(category));
				GenericBoqsRenderDTO result = setObject.renderChildParentGenericBoqs(bcmListDTO, categoriesDTO);
				return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), "No data exists...", Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getCategoriesBoqs(SearchDTO search) {

		try {
			List<GenericBoqMappingHighway> bcmList = bwbcmDao.fetchGenricCategoriesBoqs(search);
			List<GenericBoqMappingDTO> result = new ArrayList<GenericBoqMappingDTO>();
			if (bcmList != null) {
				for (GenericBoqMappingHighway bcm : bcmList) {
					result.add(setObject.boqCategoryMapEntityToDto(bcm));
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getCategoryBoqById(SearchDTO search) {

		try {
			GenericBoqMappingHighway bcm = bwbcmDao.fetchGenricCategoryBoqById(search.getCatBoqId(),
					search.getWorkOrderTypeId());
			GenericBoqMappingDTO result = setObject.boqCategoryMapEntityToDto(bcm);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse mapCategoryBoq(BorewellBoqMappingDTO bcmDTO) {

		try {
			BorewellBoqMapping bcm = setObject.boqCategoryMapDtoToEntity(bcmDTO);
			bcm.setVersion(0);
			bcm.setQuantityVersion(0);
			Long id = bwbcmDao.mapCategoryBoq(bcm);
//			if (id != null && id.longValue() > 0L) {
//				BorewellBoqTransac bct = setObject.BorewellBoqEntityMappingToTransac(bcm);
//				bwbcmDao.saveBoqCategoryTransac(bct);
//			}

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
	public CustomResponse getHighwayTypes(SearchDTO search) {
		try {

			List<Integer> highwayWoTypeIds = new ArrayList<>();
			for (HighwayWorkOrderTypes woType : HighwayWorkOrderTypes.values()) {
				highwayWoTypeIds.add(woType.getTypeId());
			}
			List<WorkorderType> types = bwbcmDao.fetchWorkorderTypes(highwayWoTypeIds);
			List<WorkorderTypeDTO> obj = new ArrayList<>();
			if (types != null)
				types.forEach(type -> obj.add(setObject.workorderTypeEntityToDto(type)));
			return new CustomResponse(Responses.SUCCESS.getCode(), types, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse mapCategoryBoq(GenericBoqMappingDTO bcmDTO) {

		try {
			GenericBoqMappingHighway bcm = setObject.boqCategoryMapDtoToEntity(bcmDTO);
			bcm.setVersion(0);
			bcm.setQuantityVersion(0);
			Long id = bwbcmDao.mapCategoryBoq(bcm);
//			if (id != null && id.longValue() > 0L) {
//				BorewellBoqTransac bct = setObject.BorewellBoqEntityMappingToTransac(bcm);
//				bwbcmDao.saveBoqCategoryTransac(bct);
//			}

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
	public CustomResponse updateCategoryBoq(GenericBoqMappingDTO bcmDTO) {
		try {
			GenericBoqMappingHighway dbObj = bwbcmDao.fetchGenricCategoryBoqById(bcmDTO.getId(),
					bcmDTO.getWorkorderTypeId());
			if (dbObj == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), "Does not exist...",
						Responses.SUCCESS.toString());
			boolean needTransac = false;
			if (!(dbObj.getSiteId().equals(bcmDTO.getSiteId()) && dbObj.getBoq().getId().equals(bcmDTO.getBoq().getId())
					&& ((dbObj.getCategory() == null && bcmDTO.getCategory() == null)
							|| (dbObj.getCategory() != null && bcmDTO.getCategory() != null
									&& dbObj.getCategory().getId().equals(bcmDTO.getCategory().getId())))
					&& ((dbObj.getSubcategory() == null && bcmDTO.getSubcategory() == null)
							|| (dbObj.getSubcategory() != null && bcmDTO.getSubcategory() != null
									&& dbObj.getSubcategory().getId().equals(bcmDTO.getSubcategory().getId())))
					&& dbObj.getQuantity().equals(bcmDTO.getQuantity()) && dbObj.getRate().equals(bcmDTO.getRate())
					&& dbObj.getMaxRate().equals(bcmDTO.getMaxRate())
					&& dbObj.getDescription().equals(bcmDTO.getDescription())
					&& dbObj.getRemark().equals(bcmDTO.getRemark())
					&& dbObj.getIsActive().equals(bcmDTO.getIsActive()))) {
				needTransac = true;
			}
			boolean qtyDiff = false;
			if (!dbObj.getQuantity().equals(bcmDTO.getQuantity()))
				qtyDiff = true;
			GenericBoqMappingHighway bcmObj = setObject.updatedBoqCategoryMapping(dbObj,
					setObject.boqCategoryMapDtoToEntity(bcmDTO));
			if (qtyDiff)
				bcmObj.setQuantityVersion(bcmObj.getQuantityVersion() + 1);
			if (needTransac)
				bcmObj.setVersion(bcmObj.getVersion() + 1);
			Boolean isSaved = bwbcmDao.updateCategoryBoqMapping(bcmObj);
//			if (needTransac) {
//				BorewellBoqTransac bcmTransac = setObject.BorewellBoqEntityMappingToTransac(bcmObj);
//				bcmTransac.setCreatedOn(new Date());
//				bwbcmDao.saveBoqCategoryTransac(bcmTransac);
//			}
			return new CustomResponse(Responses.SUCCESS.getCode(), isSaved, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
