package erp.workorder.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.service.HighwayBoqMeasurementService;

@Service
@Transactional
public class HighwayBoqMeasurementServiceImpl implements HighwayBoqMeasurementService {

//	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public CustomResponse importHighwayBoqMeasurementExcel(MultipartFile excelFile, SearchDTO search) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Autowired
//	private AuthorityClient permitClient;
//
//	@Autowired
//	private SetObject setObject;
//
//	@Autowired
//	private FileUtilService fileUtilService;
//
//	@Autowired
//	private BoqItemDao boqDao;
//
//	@Override
//	public CustomResponse importHighwayBoqMeasurementExcel(MultipartFile excelFile, SearchDTO search) {
//
//		try (XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {
//			search.setPageId(PageType.HIGHWAY_BOQ_MEASUREMENT.getPageId());
//			ObjectMapper mapper = new ObjectMapper();
//			CustomResponse authResponse = permitClient.getUserPagePermissions(search);
//			if (authResponse != null && authResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
//				AuthorityDTO permissions = mapper.convertValue(authResponse.getData(), AuthorityDTO.class);
//				if (!(permissions.getAdd() != null && permissions.getAdd().equals(true)))
//					return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), null,
//							Responses.PERMISSION_DENIED.toString());
//			} else
//				return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), null,
//						Responses.PERMISSION_DENIED.toString());
//
//			int freeTraversePotential = 10;
//			Integer sheetCount = workbook.getNumberOfSheets();
//			MultipartFile[] file = { excelFile };
//			search.setFiles(file);
//			FileEntity uploadedFile = fileUtilService.addFile(search);
//			List<HighwayMeasurementImportDTO> hbmObjList = new ArrayList<>();
//			DataFormatter formatter = new DataFormatter();
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
//			if (sheetCount != null && sheetCount > 0) {
//				for (int i = 0; i < sheetCount; i++) {
//					XSSFSheet worksheet = workbook.getSheetAt(i);
//					int initialRowIndex = 0, initialColumnIndex = 0;
//					for (int j = 0; j < worksheet.getPhysicalNumberOfRows(); j++) {
//
//						XSSFRow tempRow = worksheet.getRow(j);
//						if (tempRow == null)
//							continue;
//						for (int k = 0; k < tempRow.getPhysicalNumberOfCells(); k++) {
//							if (tempRow.getCell(k) != null
//									&& tempRow.getCell(k).getStringCellValue().toLowerCase().trim().contains("date")) {
//
//								if (!(tempRow.getCell(k + 3).getStringCellValue().toLowerCase().trim()
//										.contains("description")
//										&& tempRow.getCell(k + 5).getStringCellValue().toLowerCase().trim()
//												.contains("chainage")
//										&& tempRow.getCell(k + 6).getStringCellValue().toLowerCase().trim()
//												.contains("side")
//										&& tempRow.getCell(k + 8).getStringCellValue().toLowerCase().trim()
//												.contains("remark")
//										&& tempRow.getCell(k + 9).getStringCellValue().toLowerCase().trim()
//												.contains("workorder"))) {
//									workbook.close();
//									return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//											Responses.BAD_REQUEST.toString());
//								}
//								initialRowIndex = j;
//								initialColumnIndex = k;
//								for (int m = initialRowIndex + 1; m < worksheet.getPhysicalNumberOfRows() + 1; m++) {
//									XSSFRow row = worksheet.getRow(m);
//									if (m > initialRowIndex && m < worksheet.getPhysicalNumberOfRows()
//											&& (row.getCell(initialColumnIndex + 0) == null
//													|| row.getCell(initialColumnIndex + 0).getCellTypeEnum()
//															.equals(CellType.BLANK)
//													|| formatter.formatCellValue(row.getCell(initialColumnIndex + 0))
//															.toLowerCase().replaceAll(" ", "").trim().isEmpty())) {
//										continue;
//									}
//									if (m == worksheet.getPhysicalNumberOfRows()) {
//										if (hbmObjList != null) {
//
//											List<HighwayBoqMeasurement> toSaveObjList = new ArrayList<>();
//											int hbmObjListSize = hbmObjList.size();
//
//											for (int d = 0; d < hbmObjListSize; d++) {
//												HighwayMeasurementImportDTO excelObj = hbmObjList.get(d);
//												if (excelObj.getDate() == null
//														|| (excelObj.getCode() == null
//																&& excelObj.getStandardBookCode() == null)
//														|| excelObj.getFromChainage() == null
//														|| excelObj.getToChainage() == null
//														|| excelObj.getQuantity() == null) {
//													continue;
//												}
//
//											}
//											List<HighwayBoqMapping> hbqToReturn = new ArrayList<>();
//											if (toSaveObjList != null) {
//												Set<Long> idsSaved = new HashSet<>();
//												for (HighwayBoqMeasurement hbqObj : toSaveObjList) {
//													if (hbqObj.getBoq() == null)
//														continue;
//													hbqObj.setVersion(0);
//													hbqObj.setQuantityVersion(0);
//													Long id = bcmDao.mapCategoryBoq(hbqObj);
//													if (id != null && id.longValue() > 0) {
//														hbqObj.setId(id);
//														idsSaved.add(id);
//														hbqToReturn.add(bcmDao.fetchCategoryBoqById(id));
//														HighwayBoqTransacs hbqTransac = setObject
//																.highwayBoqEntityMappingToTransac(hbqObj);
//														hbqTransac.setCreatedOn(new Date());
//														bcmDao.saveBoqCategoryTransac(hbqTransac);
//													} else {
//														search.setBoqId(hbqObj.getBoq().getId());
//														search.setCategoryId(hbqObj.getCategory() != null
//																? hbqObj.getCategory().getId()
//																: null);
//														search.setSubcategoryId(hbqObj.getSubcategory() != null
//																? hbqObj.getSubcategory().getId()
//																: null);
//														search.setDescription(null);
//														List<HighwayBoqMapping> hbqs = bcmDao
//																.fetchCategoriesBoqs(search);
//														if (hbqs != null && hbqs.size() > 0) {
//															HighwayBoqMapping hbq = hbqs.get(0);
//															hbq.setModifiedBy(hbqObj.getModifiedBy());
//															hbq.setModifiedOn(new Date());
//															hbq.setIsActive(true);
//															boolean qtyDiff = false;
//															if (!hbq.getQuantity().equals(hbqObj.getQuantity()))
//																qtyDiff = true;
//															if (!(hbq.getQuantity().equals(hbqObj.getQuantity())
//																	&& hbq.getRate().equals(hbqObj.getRate())
//																	&& hbq.getDescription()
//																			.equals(hbqObj.getDescription()))) {
//																hbq.setQuantity(hbqObj.getQuantity() != null
//																		? hbqObj.getQuantity()
//																		: 0.0);
//																hbq.setDescription(hbqObj.getDescription());
//																hbq.setRate(hbqObj.getRate() != null ? hbqObj.getRate()
//																		: 0.0);
//																hbq.setMaxRate(hbqObj.getMaxRate() != null
//																		? hbqObj.getMaxRate()
//																		: 0.0);
//																hbq.setFile(uploadedFile);
//																hbq.setRemark(hbqObj.getRemark());
//																if (qtyDiff) {
//																	int currentQuantityVersion = hbq
//																			.getQuantityVersion().intValue();
//																	hbq.setQuantityVersion(currentQuantityVersion + 1);
//																}
//																int currentVersion = hbq.getVersion().intValue();
//																hbq.setVersion(currentVersion + 1);
//																bcmDao.updateCategoryBoqMapping(hbq);
//																HighwayBoqTransacs hbqTransac = setObject
//																		.highwayBoqEntityMappingToTransac(hbq);
//																hbqTransac.setCreatedOn(new Date());
//																bcmDao.saveBoqCategoryTransac(hbqTransac);
//															}
//															hbqToReturn.add(hbq);
//														}
//													}
//												}
//												if (idsSaved.size() > 0) {
//													List<HighwayBoqMapping> hbmSaved = bcmDao
//															.fetchHighwayBoqByIdList(idsSaved);
//													hbqToReturn.addAll(hbmSaved);
//												}
//												HashSet<Long> seen = new HashSet<>();
//												hbqToReturn.removeIf(e -> !seen.add(e.getId()));
//											}
//											workbook.close();
//											List<HighwayBoqMappingDTO> hbqListDTOToReturn = new ArrayList<HighwayBoqMappingDTO>();
//											if (hbqToReturn != null) {
//												for (HighwayBoqMapping bcm : hbqToReturn) {
//													hbqListDTOToReturn.add(setObject.boqCategoryMapEntityToDto(bcm));
//												}
//											}
//											if (hbqListDTOToReturn != null && hbqListDTOToReturn.size() > 0) {
//
//												List<CategoryItem> categories = categoryDao.fetchCategoryItems(search);
//												List<CategoryItemDTO> categoriesDTO = new ArrayList<>();
//												for (CategoryItem category : categories)
//													categoriesDTO.add(setObject.categoryItemEntityToDto(category));
////				        						HighwayBoqsRenderDTO result = setObject.renderChildParentHighwayBoqs(hbqListDTOToReturn, categoriesDTO);
////				        						return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
////				        						UNCOMMENT ABOVE TWO LINEs TO RETURN HBQs in Parent Child Render Format
//												return new CustomResponse(Responses.SUCCESS.getCode(),
//														uploadedFile.getName() + " - Imported Successfully!",
//														Responses.SUCCESS.toString());
//											}
//											return new CustomResponse(Responses.SUCCESS.getCode(), "No data exists...",
//													Responses.SUCCESS.toString());
//										}
//									} else {
//
//										String dateString = null;
//										Date date = null;
//										dateString = formatter.formatCellValue(row.getCell(initialColumnIndex + 0))
//												.trim();
//										if (dateString.isEmpty()) {
//											dateString = null;
//										}
//										if (dateString != null) {
//											date = dateFormatter.parse(dateString);
//										}
//										String code = null;
//										code = formatter.formatCellValue(row.getCell(initialColumnIndex + 1)).trim();
//										if (code.isEmpty()) {
//											code = null;
//										}
//
//										String sdbCode = formatter.formatCellValue(row.getCell(initialColumnIndex + 2))
//												.trim();
//										if (sdbCode.isEmpty()) {
//											sdbCode = null;
//										}
//
//										String description = formatter
//												.formatCellValue(row.getCell(initialColumnIndex + 3)).trim();
//										if (description.isEmpty()) {
//											description = null;
//										}
//
//										String fromChainage = formatter
//												.formatCellValue(row.getCell(initialColumnIndex + 4)).trim();
//										if (fromChainage.isEmpty()) {
//											fromChainage = null;
//										}
//
//										String toChainage = formatter
//												.formatCellValue(row.getCell(initialColumnIndex + 5)).trim();
//										if (toChainage.isEmpty()) {
//											toChainage = null;
//										}
//
//										String chainageSide = formatter
//												.formatCellValue(row.getCell(initialColumnIndex + 6)).trim();
//										if (chainageSide.isEmpty()) {
//											chainageSide = null;
//										}
//
//										Double quantity = 0.0;
//										String quantityString = getCellValueFromCell(
//												row.getCell(initialColumnIndex + 7));
//										if (quantityString != null) {
//											quantity = Double.valueOf(quantityString);
//										}
//
//										String remark = formatter.formatCellValue(row.getCell(initialColumnIndex + 8))
//												.trim();
//										if (remark.isEmpty()) {
//											remark = null;
//										}
//
//										String workorderNo = formatter
//												.formatCellValue(row.getCell(initialColumnIndex + 9)).trim();
//										if (workorderNo.isEmpty()) {
//											workorderNo = null;
//										}
//										HighwayMeasurementImportDTO hbmImport = new HighwayMeasurementImportDTO(date,
//												code, sdbCode, description, fromChainage, toChainage, chainageSide,
//												quantity, remark, workorderNo);
//										hbmObjList.add(hbmImport);
//									}
//								}
//							}
//							if (k > freeTraversePotential)
//								break;
//						}
//						if (j > freeTraversePotential)
//							break;
//					}
//				}
//			}
//			workbook.close();
//
//			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
//		}
//	}
//
//	public String getCellValueFromCell(XSSFCell cell) {
//
//		String value = null;
//		Double numericValue = null;
//
//		if (cell != null) {
//			switch (cell.getCellTypeEnum()) {
//			case STRING:
//				value = cell.getStringCellValue();
//				break;
//			case NUMERIC:
//				numericValue = cell.getNumericCellValue();
//				value = (new BigDecimal(numericValue)).toPlainString();
//				break;
//			default:
//				value = null;
//				break;
//			}
//		}
//		return value != null && !value.trim().isEmpty() ? value.trim() : null;
//	}
}
