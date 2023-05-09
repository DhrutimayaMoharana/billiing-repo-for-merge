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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dao.BoqItemDao;
import erp.boq_mgmt.dao.CategoryItemDao;
import erp.boq_mgmt.dao.StructureBoqQuantityDao;
import erp.boq_mgmt.dao.StructureDao;
import erp.boq_mgmt.dao.SubcategoryItemDao;
import erp.boq_mgmt.dao.UnitDao;
import erp.boq_mgmt.dao.WorkorderBoqWorkDao;
import erp.boq_mgmt.dto.CategoryItemDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.ExcelErrorDTO;
import erp.boq_mgmt.dto.SbqRenderDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.StructureBoqQtyImportDTO;
import erp.boq_mgmt.dto.StructureBoqQuantityMappingDTO;
import erp.boq_mgmt.dto.StructureDTO;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.CategoryItem;
import erp.boq_mgmt.entity.FileEntity;
import erp.boq_mgmt.entity.Structure;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.entity.StructureBoqQuantityTransacs;
import erp.boq_mgmt.entity.SubcategoryItem;
import erp.boq_mgmt.entity.Unit;
import erp.boq_mgmt.entity.WorkorderBoqWorkQtyMapping;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.FileUtilService;
import erp.boq_mgmt.service.StructureBoqQuantityService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class StructureBoqQuantityServiceImpl implements StructureBoqQuantityService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StructureBoqQuantityDao sbqDao;
	@Autowired
	private SetObject setObject;
	@Autowired
	private BoqItemDao boqDao;
	@Autowired
	private UnitDao unitDao;
	@Autowired
	private CategoryItemDao categoryDao;
	@Autowired
	private SubcategoryItemDao subcategoryDao;
	@Autowired
	private FileUtilService fileUtilService;
	@Autowired
	private StructureDao structureDao;
	@Autowired
	private WorkorderBoqWorkDao woBoqWorkDao;

	@Override
	public CustomResponse addStructureBoqQuantityMapping(StructureBoqQuantityMappingDTO sbqMapDTO) {

		try {
			StructureBoqQuantityMapping sbqObj = setObject.structureBoqQtyMapDtoToEntity(sbqMapDTO);
			sbqObj.setModifiedOn(new Date());
			sbqObj.setVersion(0);
			sbqObj.setQuantityVersion(0);
			Long id = sbqDao.saveStructureBoqQuantityMapping(sbqObj);
			if (id != null && id.longValue() > 0L) {
				StructureBoqQuantityTransacs sbqTransac = setObject.sbqEntityMappingToTransac(sbqObj);
				sbqTransac.setCreatedOn(new Date());
				sbqDao.saveStructureBoqQuantityTransac(sbqTransac);
			}
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
	public CustomResponse updateStructureBoqQuantityMapping(StructureBoqQuantityMappingDTO sbqMapDTO) {

		try {
			StructureBoqQuantityMapping dbObj = sbqDao.fetchSbqById(sbqMapDTO.getId());
			if (dbObj == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), "Does not exist.", Responses.SUCCESS.toString());
			boolean needTransac = false;

			if (!(dbObj.getSiteId().equals(sbqMapDTO.getSiteId())
					&& dbObj.getBoq().getId().equals(sbqMapDTO.getBoq().getId())
					&& ((dbObj.getCategory() == null && sbqMapDTO.getCategory() == null)
							|| (dbObj.getCategory() != null && sbqMapDTO.getCategory() != null
									&& dbObj.getCategory().getId().equals(sbqMapDTO.getCategory().getId())))
					&& ((dbObj.getSubcategory() == null && sbqMapDTO.getSubcategory() == null)
							|| (dbObj.getSubcategory() != null && sbqMapDTO.getSubcategory() != null
									&& dbObj.getSubcategory().getId().equals(sbqMapDTO.getSubcategory().getId())))
					&& dbObj.getIsActive().equals(sbqMapDTO.getIsActive())
					&& dbObj.getQuantity().equals(sbqMapDTO.getQuantity())
					&& dbObj.getUnit().getId().equals(sbqMapDTO.getUnit().getId())
					&& dbObj.getRate().equals(sbqMapDTO.getRate()) && dbObj.getMaxRate().equals(sbqMapDTO.getMaxRate())
					&& dbObj.getDescription().equals(sbqMapDTO.getDescription())
					&& dbObj.getRemark().equals(sbqMapDTO.getRemark())
					&& dbObj.getStructure().getId().equals(sbqMapDTO.getStructure().getId()))) {
				needTransac = true;
			}
			boolean qtyDiff = false;
			if (!dbObj.getQuantity().equals(sbqMapDTO.getQuantity()))
				qtyDiff = true;
			StructureBoqQuantityMapping sbqObj = setObject.updatedSbq(dbObj,
					setObject.structureBoqQtyMapDtoToEntity(sbqMapDTO));
			if (qtyDiff)
				sbqObj.setQuantityVersion(sbqObj.getQuantityVersion() + 1);
			if (needTransac)
				sbqObj.setVersion(sbqObj.getVersion() + 1);
			Boolean isSaved = sbqDao.updateStructureBoqQuantityMapping(sbqObj);
			if (needTransac) {
				StructureBoqQuantityTransacs sbqTransac = setObject.sbqEntityMappingToTransac(sbqObj);
				sbqDao.saveStructureBoqQuantityTransac(sbqTransac);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), isSaved, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public CustomResponse getStructureBoqQuantities(SearchDTO search) {

		try {
			List<StructureBoqQuantityMapping> sbqList = sbqDao.fetchStructureBoqQuantityMappingBySearch(search);
			List<StructureBoqQuantityMappingDTO> sbqListDTO = new ArrayList<StructureBoqQuantityMappingDTO>();
			if (sbqList != null) {
				for (StructureBoqQuantityMapping sbq : sbqList) {
					sbqListDTO.add(setObject.structureBoqQtyMapEntityToDto(sbq));
				}
			}
			if (sbqListDTO != null && sbqListDTO.size() > 0) {
				List<CategoryItem> categories = categoryDao.fetchCategoryItems(search);
				List<CategoryItemDTO> categoriesDTO = new ArrayList<>();
				for (CategoryItem category : categories)
					categoriesDTO.add(setObject.categoryItemEntityToDto(category));
				SbqRenderDTO result = setObject.renderChildParentSbqs(sbqListDTO, categoriesDTO);
				result.setStructure(sbqListDTO.get(0).getStructure());
				return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
			}
			StructureDTO structure = setObject
					.structureEntityToDto(structureDao.fetchStructureById(search.getSiteId()));
			SbqRenderDTO result = new SbqRenderDTO(sbqListDTO, structure, 0.0);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public CustomResponse getStructureWiseBoq(SearchDTO search) {

		try {
			List<StructureBoqQuantityMapping> sbqList = sbqDao.fetchStructureWiseBoq(search);
			List<StructureBoqQuantityMappingDTO> sbqListDTO = new ArrayList<StructureBoqQuantityMappingDTO>();
			if (sbqList != null) {
				for (StructureBoqQuantityMapping sbq : sbqList) {
					sbqListDTO.add(setObject.structureBoqQtyMapEntityToDto(sbq));
				}
			}
			if (sbqListDTO != null && sbqListDTO.size() > 0) {
				List<CategoryItem> categories = categoryDao.fetchCategoryItems(search);
				List<CategoryItemDTO> categoriesDTO = new ArrayList<>();
				for (CategoryItem category : categories)
					categoriesDTO.add(setObject.categoryItemEntityToDto(category));
				SbqRenderDTO result = setObject.renderChildParentSbqs(sbqListDTO, categoriesDTO);
				result.setStructure(sbqListDTO.get(0).getStructure());
				return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
			}
			StructureDTO structure = setObject
					.structureEntityToDto(structureDao.fetchStructureById(search.getStructureId()));
			SbqRenderDTO result = new SbqRenderDTO(sbqListDTO, structure, 0.0);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}

	}

	@Override
	public CustomResponse getSbqById(SearchDTO search) {

		try {
			StructureBoqQuantityMapping sbq = sbqDao.fetchSbqById(search.getCbqId());
			StructureBoqQuantityMappingDTO result = setObject.structureBoqQtyMapEntityToDto(sbq);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse importStructureBoqQuantityMappingExcel(MultipartFile excelFile, SearchDTO search) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {
			int freeTraversePotential = 20;
			Structure structure = structureDao.fetchStructureById(search.getStructureId());
			if (!(structure != null && structure.getSiteId().equals(search.getSiteId()))) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
						"Structure site does not match with provided site, please check.");
			}
			Integer sheetCount = workbook.getNumberOfSheets();
			MultipartFile[] file = { excelFile };
			search.setFiles(file);
			FileEntity uploadedFile = fileUtilService.addFile(search);
			List<StructureBoqQtyImportDTO> sbqObjList = new ArrayList<>();
			List<ExcelErrorDTO> excelErrors = new ArrayList<ExcelErrorDTO>();
			DataFormatter formatter = new DataFormatter();
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

								if (!(getCellValueFromCellWithEmptyStringOnNull(tempRow.getCell(k + 3)).toLowerCase()
										.trim().contains("unit")
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
								for (int m = initialRowIndex + 1; m < worksheet.getPhysicalNumberOfRows() + 1; m++) {
									XSSFRow row = worksheet.getRow(m);

									if (row != null && m > initialRowIndex && m < worksheet.getPhysicalNumberOfRows()
											&& (row.getCell(initialColumnIndex + 0) == null
													|| row.getCell(initialColumnIndex + 0).getCellTypeEnum()
															.equals(CellType.BLANK)
													|| formatter.formatCellValue(row.getCell(initialColumnIndex + 0))
															.toLowerCase().replaceAll(" ", "").trim().isEmpty())) {
										if (initialColumnIndex - 1 >= 0 && (row.getCell(initialColumnIndex - 1) == null
												|| row.getCell(initialColumnIndex - 1).getCellTypeEnum()
														.equals(CellType.BLANK)
												|| formatter.formatCellValue(row.getCell(initialColumnIndex - 1))
														.toLowerCase().replaceAll(" ", "").trim().isEmpty())) {
											continue;
										}
									}

									if (m == worksheet.getPhysicalNumberOfRows()) {
										if (sbqObjList != null) {

											List<Unit> units = unitDao.fetchUnits(search);
											Set<String> unitsNotPresent = new HashSet<>();
											for (StructureBoqQtyImportDTO obj : sbqObjList) {
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
													excelErrors.add(new ExcelErrorDTO(
															obj.getUnit() + " mentioned at row " + obj.getExcelRowNo(),
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
														new ExcelErrorDTO("Add the following units before importing."
																+ unitsNotPresentConcat, 0));
												return new CustomResponse(Responses.EXCEL_ERRORS.getCode(), excelErrors,
														Responses.EXCEL_ERRORS.toString());
											}

											List<StructureBoqQuantityMapping> toSaveObjList = new ArrayList<>();
											int sbqObjListSize = sbqObjList.size();
											search.setSearchField(null);
											List<CategoryItem> allCategories = categoryDao.fetchCategoryItems(search);
											List<SubcategoryItem> allSubcategories = subcategoryDao
													.fetchSubcategoryItems(search);
											List<BoqItem> allBoqs = boqDao.fetchBoqItems(search);

											for (int d = 0; d < sbqObjListSize; d++) {
												search.setCategoryId(null);
												search.setSubcategoryId(null);
												StructureBoqQtyImportDTO excelObj = sbqObjList.get(d);
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
														StructureBoqQuantityMapping obj = new StructureBoqQuantityMapping(
																null, structure, null, null, null, null, null, null,
																search.getSiteId(), null, null, null, null,
																uploadedFile, null, null, new Date(),
																search.getUserId(), true, search.getCompanyId());
														obj.setQuantity(
																excelObj.getQuantity() != null ? excelObj.getQuantity()
																		: 0.0);
														obj.setDescription(excelObj.getDescription());
														obj.setVendorDescription(excelObj.getVendorDescription());
														obj.setRate(
																excelObj.getRate() != null ? excelObj.getRate() : 0.0);
														obj.setMaxRate(
																excelObj.getMaxRate() != null ? excelObj.getMaxRate()
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
																		if (resultSdbCode
																				.equalsIgnoreCase(resultExcelSdbCode)) {
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
														search.setName(excelObj.getDescription());
														search.setVendorDescription(excelObj.getVendorDescription());
														search.setCode(excelObj.getCode());
														search.setStandardBookCode(excelObj.getStandardBookCode());
														Long boqId = null;
														if (allBoqs != null) {
															for (BoqItem item : allBoqs) {
																if (item.getStandardBookCode() != null
																		&& excelObj.getStandardBookCode() != null) {
																	String resultSdbCode = item.getStandardBookCode();
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
																	if (resultCode.equalsIgnoreCase(resultExcelCode)) {
																		boqId = item.getId();
																		break;
																	}
																}
															}
														}

														if (boqId != null) {
															obj.setBoq(new BoqItem(boqId));
															obj.setUnit(new Unit(unitId));
															toSaveObjList.add(obj);
														} else {
															excelErrors.add(new ExcelErrorDTO(
																	"Category is not provided for an unknown BOQ at row "
																			+ excelObj.getExcelRowNo(),
																	excelObj.getExcelRowNo()));
														}
													} else if (d < sbqObjListSize - 1) {
														int e = d;
														StructureBoqQtyImportDTO excelCatObj = sbqObjList.get(e);
														if ((excelCatObj.getStandardBookCode() != null
																&& !excelObj.getStandardBookCode().trim().isEmpty())
																|| (excelCatObj.getCode() != null
																		&& !excelObj.getCode().trim().isEmpty())) {

															if (excelCatObj.getStandardBookCode() != null
																	&& !excelCatObj.getStandardBookCode().isEmpty()) {
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
																		if (resultSdbCode
																				.equalsIgnoreCase(resultExcelSdbCode)) {
																			categoryId = item.getId();
																			break;
																		}
																	}
																	if (item.getCode() != null
																			&& excelCatObj.getCode() != null) {
																		String resultCode = item.getCode();

																		String resultExcelCode = excelCatObj.getCode();
																		if (resultCode
																				.equalsIgnoreCase(resultExcelCode)) {
																			categoryId = item.getId();
																			break;
																		}
																	}
																}
															}
															if (categoryId == null) {
																excelErrors.add(new ExcelErrorDTO(
																		"Add category with code " + search.getCode()
																				+ " before using at row "
																				+ excelCatObj.getExcelRowNo(),
																		excelCatObj.getExcelRowNo()));
																continue;
															}
															String categoryCodeAN = excelCatObj.getCode().toLowerCase()
																	.trim().replaceAll("[^A-Za-z0-9]", "");
															for (int f = e + 1; f < sbqObjListSize; f++) {
																search.setCategoryId(categoryId);
																search.setSubcategoryId(null);
																StructureBoqQtyImportDTO excelNestedObj = sbqObjList
																		.get(f);
																if (excelNestedObj.getStandardBookCode() != null
																		&& !excelNestedObj.getStandardBookCode()
																				.isEmpty()) {
																	String boqSdbCode = boqDao
																			.fetchCodeByStandardBookCode(
																					excelNestedObj
																							.getStandardBookCode(),
																					search.getCompanyId());
																	if (excelNestedObj.getCode() == null
																			&& boqSdbCode == null) {
																		if (excelNestedObj.getStandardBookCode() != null
																				&& !excelNestedObj.getStandardBookCode()
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
																				excelNestedObj
																						.setCode(subcategorySdbCode);
																			}
																		}
																	} else if (boqSdbCode != null) {
																		excelNestedObj.setCode(boqSdbCode);
																	}
																}
																if (excelNestedObj.getCode() != null && excelNestedObj
																		.getCode().replaceAll("[^A-Za-z0-9]", "")
																		.toLowerCase().trim()
																		.startsWith(categoryCodeAN)) {
																	search.setCategoryId(categoryId);
																	search.setSubcategoryId(null);
																	StructureBoqQuantityMapping obj = new StructureBoqQuantityMapping(
																			null, structure, null, null, null, null,
																			new CategoryItem(categoryId), null,
																			search.getSiteId(), null, null, null, null,
																			uploadedFile, null, null, new Date(),
																			search.getUserId(), true,
																			search.getCompanyId());
																	obj.setDescription(excelNestedObj.getDescription());
																	obj.setVendorDescription(
																			excelNestedObj.getVendorDescription());
																	obj.setRemark(excelNestedObj.getRemark());
																	if (excelNestedObj.getUnit() != null
																			&& !excelNestedObj.getUnit().trim()
																					.isEmpty()) {
																		obj.setQuantity(
																				excelNestedObj.getQuantity() != null
																						? excelNestedObj.getQuantity()
																						: 0.0);
																		obj.setRate(excelNestedObj.getRate() != null
																				? excelNestedObj.getRate()
																				: 0.0);
																		obj.setMaxRate(
																				excelNestedObj.getMaxRate() != null
																						? excelNestedObj.getMaxRate()
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
																						if (resultSdbCode
																								.equalsIgnoreCase(
																										resultExcelSdbCode)) {
																							boqSdbCode = item.getCode();
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
																		search.setName(excelNestedObj.getDescription());
																		search.setVendorDescription(
																				excelNestedObj.getVendorDescription());
																		search.setCode(excelNestedObj.getCode());
																		search.setStandardBookCode(
																				excelNestedObj.getStandardBookCode());
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
																					if (resultSdbCode.equalsIgnoreCase(
																							resultExcelSdbCode)) {
																						boqId = item.getId();
																						break;
																					}
																				}
																				if (item.getCode() != null
																						&& excelNestedObj
																								.getCode() != null) {
																					String resultCode = item.getCode();

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
																					new SubcategoryItem(
																							search.getSubcategoryId()),
																					true, new Date(),
																					search.getUserId(), new Date(),
																					search.getUserId(),
																					search.getCompanyId());
																			item = boqDao.forceSaveBoqItem(item);
																			boqId = item.getId();
																			allBoqs.add(item);
																		}

																		obj.setBoq(new BoqItem(boqId));
																		obj.setUnit(new Unit(unitId));
																		toSaveObjList.add(obj);
																	} else {

																		if (excelNestedObj.getStandardBookCode() != null
																				&& !excelNestedObj.getStandardBookCode()
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
																				excelNestedObj
																						.setCode(subcategorySdbCode);
																			}
																		}
																		search.setName(excelNestedObj.getDescription());
																		search.setVendorDescription(
																				excelNestedObj.getVendorDescription());
																		search.setStandardBookCode(
																				excelNestedObj.getStandardBookCode());
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
																					if (resultSdbCode.equalsIgnoreCase(
																							resultExcelSdbCode)) {
																						subcategoryId = item.getId();
																						break;
																					}
																				}
																				if (item.getCode() != null
																						&& excelNestedObj
																								.getCode() != null) {
																					String resultCode = item.getCode();

																					String resultExcelCode = excelNestedObj
																							.getCode();
																					if (resultCode.equalsIgnoreCase(
																							resultExcelCode)) {
																						subcategoryId = item.getId();
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
																		for (int g = f + 1; g < sbqObjListSize; g++) {
																			search.setCategoryId(categoryId);
																			search.setSubcategoryId(subcategoryId);
																			StructureBoqQtyImportDTO excelNestedBoqObj = sbqObjList
																					.get(g);
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
																				if (excelNestedBoqObj.getCode() == null
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
																							excelNestedBoqObj.setCode(
																									boqSdbCode);
																						}
																					}
																				} else if (subcategorySdbCode != null) {
																					excelNestedBoqObj.setCode(
																							subcategorySdbCode);
																				}
																			}
																			StructureBoqQuantityMapping nestedObj = new StructureBoqQuantityMapping();
																			BeanUtils.copyProperties(obj, nestedObj);
																			if (excelNestedBoqObj.getUnit() != null
																					&& !excelNestedBoqObj.getUnit()
																							.trim().isEmpty()
																					&& excelNestedBoqObj.getCode()
																							.trim().toLowerCase()
																							.replaceAll("[^A-Za-z0-9]",
																									"")
																							.startsWith(
																									subcategoryCodeAN)) {
																				nestedObj.setQuantity(excelNestedBoqObj
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
																						excelErrors
																								.add(new ExcelErrorDTO(
																										"System Code or SDB code is not available for BOQ at row "
																												+ excelNestedBoqObj
																														.getExcelRowNo(),
																										excelNestedBoqObj
																												.getExcelRowNo()));
																						continue;
																					} else if (boqSdbCode != null) {
																						excelNestedBoqObj
																								.setCode(boqSdbCode);
																					}
																				}
																				nestedObj.setRemark(
																						excelNestedBoqObj.getRemark());
																				nestedObj.setSubcategory(
																						obj.getSubcategory());
																				nestedObj.setRate(excelNestedBoqObj
																						.getRate() != null
																								? excelNestedBoqObj
																										.getRate()
																								: 0.0);
																				nestedObj.setMaxRate(excelNestedBoqObj
																						.getMaxRate() != null
																								? excelNestedBoqObj
																										.getMaxRate()
																								: 0.0);
																				search.setName(
																						excelNestedBoqObj.getUnit());
																				Long unitId = null;
																				for (Unit unit : units) {
																					if (unit.getName().equalsIgnoreCase(
																							search.getName())) {
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
																				search.setCode(
																						excelNestedBoqObj.getCode());
																				search.setStandardBookCode(
																						excelNestedBoqObj
																								.getStandardBookCode());
																				search.setCategoryId(categoryId);
																				search.setSubcategoryId(subcategoryId);

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
																								boqId = item.getId();
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
																							new Unit(
																									search.getUnitId()),
																							new CategoryItem(search
																									.getCategoryId()),
																							new SubcategoryItem(search
																									.getSubcategoryId()),
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
																				nestedObj.setBoq(new BoqItem(boqId));
																				nestedObj.setUnit(new Unit(unitId));
																				toSaveObjList.add(nestedObj);
																				if (g == sbqObjListSize - 1)
																					f = g - 1;
																				continue;
																			}
																			f = g - 1;
																			break;
																		}
																	}
																	if (f == sbqObjListSize - 1)
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
											List<StructureBoqQuantityMapping> sbqToReturn = new ArrayList<>();
											if (toSaveObjList != null) {
												search.setBoqId(null);
												search.setCategoryId(null);
												search.setSubcategoryId(null);
												search.setDescription(null);
												search.setHighwayBoqId(null);
												search.setStructureId(structure.getId());
												search.setStructureTypeId(structure.getType().getId());
												if (excelErrors != null && excelErrors.size() > 0) {
													workbook.close();
													TransactionAspectSupport.currentTransactionStatus()
															.setRollbackOnly();
													return new CustomResponse(Responses.EXCEL_ERRORS.getCode(),
															excelErrors, Responses.EXCEL_ERRORS.toString());
												}
												List<StructureBoqQuantityMapping> sbqs = sbqDao
														.fetchStructureBoqQuantityMappingBySearch(search);
												List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao
														.fetchStructureWoBoqWorkQtys(search);
												for (StructureBoqQuantityMapping sbqObj : toSaveObjList) {
													if (sbqObj.getBoq() == null || sbqObj.getDescription().isEmpty())
														continue;

													if (sbqObj.getBoq().getUnit() == null && allBoqs != null) {
														for (BoqItem item : allBoqs) {
															if (item.getId().equals(sbqObj.getBoq().getId())) {
																sbqObj.setBoq(item);
																if (sbqObj.getUnit() == null)
																	sbqObj.setUnit(item.getUnit());
																break;
															}
														}
													}
													Double workorderQuantity = 0.0;
													if (woBoqQtys != null) {
														for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
															if (obj.getBoq().getId().equals(sbqObj.getBoq().getId())
																	&& obj.getDescription().trim().equalsIgnoreCase(
																			sbqObj.getDescription().trim())) {
																workorderQuantity += obj.getQuantity();
															}
														}

													}

													if (sbqObj.getQuantity().doubleValue() < workorderQuantity
															.doubleValue()) {
														workbook.close();
														excelErrors.add(new ExcelErrorDTO(
																"Quantity provided in BOQ with code and description : "
																		+ sbqObj.getBoq().getCode() + " ("
																		+ sbqObj.getDescription()
																		+ ") is less than quantity issued in workorder for same BOQ.",
																0));
														continue;
													}

													boolean isSbqSaved = false;
													if (sbqs != null) {
														for (StructureBoqQuantityMapping sbq : sbqs) {
															if (sbq.getBoq().getId().equals(sbqObj.getBoq().getId())
																	&& sbq.getDescription().trim().equalsIgnoreCase(
																			sbqObj.getDescription().trim())) {

																sbq.setModifiedBy(sbqObj.getModifiedBy());
																sbq.setModifiedOn(new Date());
																sbq.setIsActive(true);
																boolean qtyDiff = false;
																if (!(sbq.getQuantity().equals(sbqObj.getQuantity())
																		&& sbq.getUnit().getId()
																				.equals(sbqObj.getUnit().getId())))
																	qtyDiff = true;
																if (!(sbq.getQuantity().equals(sbqObj.getQuantity())
																		&& sbq.getRate().equals(sbqObj.getRate()))) {
																	sbq.setQuantity(sbqObj.getQuantity() != null
																			? sbqObj.getQuantity()
																			: 0.0);
																	sbq.setVendorDescription(
																			sbqObj.getVendorDescription());
																	if (!sbqObj.getUnit().getId()
																			.equals(sbq.getUnit().getId()))
																		sbq.setUnit(sbqObj.getUnit());
																	sbq.setRate(
																			sbqObj.getRate() != null ? sbqObj.getRate()
																					: 0.0);
																	sbq.setMaxRate(sbqObj.getMaxRate() != null
																			? sbqObj.getMaxRate()
																			: 0.0);
																	sbq.setFile(uploadedFile);
																	sbq.setRemark(sbqObj.getRemark());
																	if (qtyDiff) {
																		int currentQuantityVersion = sbq
																				.getQuantityVersion().intValue();
																		sbq.setQuantityVersion(
																				currentQuantityVersion + 1);
																	}
																	int currentVersion = sbq.getVersion().intValue();
																	sbq.setVersion(currentVersion + 1);
																	sbqDao.forceUpdateStructureBoqQuantityMapping(sbq);
																	StructureBoqQuantityTransacs sbqTransac = setObject
																			.sbqEntityMappingToTransac(sbq);
																	sbqDao.saveStructureBoqQuantityTransac(sbqTransac);
																}
																sbqToReturn.add(sbq);
																isSbqSaved = true;
															}
														}
													}
													if (!isSbqSaved) {

														sbqObj.setVersion(0);
														sbqObj.setQuantityVersion(0);
														StructureBoqQuantityMapping sbq = sbqDao
																.forceSaveStructureBoqQuantityMapping(sbqObj);
														if (sbq != null) {
															sbqObj.setId(sbq.getId());
															sbqToReturn.add(sbq);
															StructureBoqQuantityTransacs sbqTransac = setObject
																	.sbqEntityMappingToTransac(sbqObj);
															sbqTransac.setCreatedOn(new Date());
															sbqDao.saveStructureBoqQuantityTransac(sbqTransac);
														}
													}
												}
												if (sbqs != null) {
													for (StructureBoqQuantityMapping hbq : sbqs) {
														boolean toRemove = true;
														for (StructureBoqQuantityMapping obj : sbqToReturn) {
															if (obj.getId().equals(hbq.getId())
																	&& obj.getDescription().trim().equalsIgnoreCase(
																			hbq.getDescription().trim())) {
																toRemove = false;
																break;
															}
														}
														if (toRemove) {
															boolean inWorkorder = false;
															if (woBoqQtys != null) {
																for (WorkorderBoqWorkQtyMapping obj : woBoqQtys) {
																	if (obj.getBoq().getId()
																			.equals(hbq.getBoq().getId())
																			&& obj.getDescription().trim()
																					.equalsIgnoreCase(hbq
																							.getDescription().trim())) {
																		inWorkorder = true;
																		break;
																	}
																}

															}
															if (inWorkorder) {
																continue;
															}
															int currentVersion = hbq.getVersion().intValue();
															hbq.setVersion(currentVersion + 1);
															hbq.setModifiedOn(new Date());
															hbq.setModifiedBy(search.getUserId());
															hbq.setIsActive(false);
															sbqDao.forceUpdateAfterDetachStructureBoqQuantityMapping(
																	hbq);
															StructureBoqQuantityTransacs sbqTransac = setObject
																	.sbqEntityMappingToTransac(hbq);
															sbqTransac.setCreatedOn(new Date());
															sbqDao.saveStructureBoqQuantityTransac(sbqTransac);
														}
													}
												}
											}
											workbook.close();
											if (excelErrors != null && excelErrors.size() > 0 && sbqToReturn != null
													&& sbqToReturn.size() > 0) {
												excelErrors.add(0, new ExcelErrorDTO(
														"Some of the BOQs have not imported successfully are mentioned below: ",
														0));
												return new CustomResponse(Responses.EXCEL_ERRORS.getCode(), excelErrors,
														Responses.EXCEL_ERRORS.toString());
											} else if (sbqToReturn != null && sbqToReturn.size() > 0) {
												return new CustomResponse(Responses.SUCCESS.getCode(),
														uploadedFile.getName() + " - Imported Successfully!",
														uploadedFile.getName() + " - Imported Successfully!");
											} else if (excelErrors != null && excelErrors.size() > 0) {
												TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
												return new CustomResponse(Responses.EXCEL_ERRORS.getCode(), excelErrors,
														Responses.EXCEL_ERRORS.toString());
											}
											return new CustomResponse(Responses.SUCCESS.getCode(), "No data imported.",
													"No data imported.");
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
										String sdbCode = formatter.formatCellValue(row.getCell(initialColumnIndex + 0))
												.trim();
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
										String rateString = getCellValueFromCell(row.getCell(initialColumnIndex + 4));
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
										String remark = formatter.formatCellValue(row.getCell(initialColumnIndex + 8))
												.trim();
										if (remark.isEmpty()) {
											remark = null;
										}
										if ((code == null || code.trim().isEmpty())
												&& (sdbCode == null || sdbCode.trim().isEmpty())) {
											continue;
										}
										StructureBoqQtyImportDTO sbqImport = new StructureBoqQtyImportDTO(code, sdbCode,
												description != null ? description.trim() : null, vendorDescription,
												unit, rate, maxRate, quantity, remark, m);
										sbqObjList.add(sbqImport);
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

			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					"Problem occurred while processing the file.");
		}
	}

	@Override
	public CustomResponse deactivateStructureBoqQuantity(SearchDTO search) {

		try {
			StructureBoqQuantityMapping sbq = sbqDao.fetchSbqById(search.getId());
			search.setStructureId(sbq.getStructure().getId());
			search.setBoqId(sbq.getBoq().getId());
			search.setDescription(sbq.getDescription());
			List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao.fetchStructureWoBoqWorkQtysByBoqId(search);
			if (woBoqQtys != null && woBoqQtys.size() > 0) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), false,
						"This structure BOQ is used in workorder.");
			}
			sbq.setIsActive(false);
			sbq.setModifiedBy(search.getUserId());
			sbq.setModifiedOn(new Date());
			sbqDao.forceUpdateAfterDetachStructureBoqQuantityMapping(sbq);
			StructureBoqQuantityTransacs sbqTransac = setObject.sbqEntityMappingToTransac(sbq);
			sbqTransac.setCreatedOn(new Date());
			sbqDao.saveStructureBoqQuantityTransac(sbqTransac);
			return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
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

}
