
package erp.boq_mgmt.service.Impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dao.ChainageBoqQuantityDao;
import erp.boq_mgmt.dao.ChainageDao;
import erp.boq_mgmt.dao.HighwayBoqMapDao;
import erp.boq_mgmt.dao.WorkorderBoqWorkDao;
import erp.boq_mgmt.dto.ChainageBoqQtyImportDTO;
import erp.boq_mgmt.dto.ChainageBoqQuantityMappingDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.HighwayBoqChainageRenderDTO;
import erp.boq_mgmt.dto.HighwayBoqMappingDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Chainage;
import erp.boq_mgmt.entity.ChainageBoqQuantityMapping;
import erp.boq_mgmt.entity.ChainageBoqQuantityTransacs;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.HighwayBoqTransacs;
import erp.boq_mgmt.entity.WorkorderBoqWorkQtyMapping;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.UnitTypes;
import erp.boq_mgmt.service.ChainageBoqQuantityService;
import erp.boq_mgmt.service.ChainageService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class ChainageBoqQuantityServiceImpl implements ChainageBoqQuantityService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private ChainageBoqQuantityDao cbqDao;
	@Autowired
	private SetObject setObject;
	@Autowired
	private ChainageDao chainageDao;
	@Autowired
	private ChainageService chainageService;
	@Autowired
	private HighwayBoqMapDao hbmDao;
	@Autowired
	private WorkorderBoqWorkDao woBoqWorkDao;

	@Override
	public CustomResponse addChainageBoqQuantityMapping(ChainageBoqQuantityMappingDTO cbqMapDTO) {

		try {
			ChainageBoqQuantityMapping cbqObj = setObject.chainageBoqQtyMapDtoToEntity(cbqMapDTO);
			cbqObj.setModifiedOn(new Date());
			Long id = cbqDao.saveChainageBoqQuantityMapping(cbqObj);
			if (id != null && id.longValue() > 0L) {
				ChainageBoqQuantityTransacs cbqTransac = setObject.cbqEntityMappingToTransac(cbqObj);
				cbqTransac.setCreatedOn(new Date());
				cbqDao.saveChainageBoqQuantityTransac(cbqTransac);
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
	public CustomResponse updateChainageBoqQuantityMapping(ChainageBoqQuantityMappingDTO cbqMapDTO) {

		try {
			ChainageBoqQuantityMapping dbObj = cbqDao.fetchCbqById(cbqMapDTO.getId());
			if (dbObj == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), "Does not exist...",
						Responses.SUCCESS.toString());
			ChainageBoqQuantityMapping cbqObj = setObject.updatedCbq(dbObj,
					setObject.chainageBoqQtyMapDtoToEntity(cbqMapDTO));
			Boolean isSaved = cbqDao.updateChainageBoqQuantityMapping(cbqObj);
			if (!(dbObj.getSiteId().equals(cbqObj.getSiteId()) && dbObj.getBoq().getId().equals(cbqObj.getBoq().getId())
					&& dbObj.getIsActive().equals(cbqObj.getIsActive())
					&& dbObj.getChainage().getId().equals(cbqObj.getChainage().getId())
					&& dbObj.getLhsQuantity().equals(cbqObj.getLhsQuantity())
					&& dbObj.getRhsQuantity().equals(cbqObj.getRhsQuantity()))) {
				ChainageBoqQuantityTransacs cbqTransac = setObject.cbqEntityMappingToTransac(cbqObj);
				cbqTransac.setCreatedOn(new Date());
				cbqDao.saveChainageBoqQuantityTransac(cbqTransac);
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
	public CustomResponse getChainageBoqQuantities(SearchDTO search) {

		try {
			List<ChainageBoqQuantityMapping> cbqList = cbqDao.fetchChainageBoqQuantityMappingBySearch(search);
			List<ChainageBoqQuantityMappingDTO> result = new ArrayList<ChainageBoqQuantityMappingDTO>();
			search.setSearchField(null);
			List<Chainage> chainages = chainageDao.fetchChainages(search);
			if (cbqList != null) {
				for (ChainageBoqQuantityMapping cbq : cbqList) {
					result.add(setObject.chainageBoqQtyMapEntityToDto(cbq));
				}
			}
			if (search.getFromChainageId() != null && search.getToChainageId() != null) {
				String fromChainage = null;
				String toChainage = null;
				for (Chainage chainage : chainages) {
					if (chainage.getId().equals(search.getFromChainageId()))
						fromChainage = chainage.getName();
					if (chainage.getId().equals(search.getToChainageId()))
						toChainage = chainage.getName();
				}
				if (fromChainage != null && toChainage != null) {
					String fromChainageNum = fromChainage.replaceAll("[^0-9]", "");
					String toChainageNum = toChainage.replaceAll("[^0-9]", "");
					if (!fromChainageNum.isEmpty() && !toChainageNum.isEmpty()) {
						for (int i = 0; i < result.size(); i++) {
							ChainageBoqQuantityMappingDTO cbqDto = result.get(i);
							if (cbqDto.getChainage() != null && cbqDto.getChainage().getName() != null) {
								String chainageNum = cbqDto.getChainage().getName().replaceAll("[^0-9]", "");
								if (!chainageNum.isEmpty()) {
									int chainageInt = Integer.parseInt(chainageNum);
									int fromChainageInt = Integer.parseInt(fromChainageNum);
									int toChainageInt = Integer.parseInt(toChainageNum);
									if (!(chainageInt >= fromChainageInt && chainageInt <= toChainageInt)) {
										result.set(i, null);
									}
								}
							} else {
								result.set(i, null);
							}
						}
						result.removeAll(Collections.singleton(null));
					}

				}
			}
			if (result != null && result.size() > 0) {
				result.get(0).setLength(0.0);
				result.get(0).setVolume(0.0);
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
	public CustomResponse importChainageBoqQuantityMappingExcel(MultipartFile excelFile, SearchDTO search) {

		try {
			int freeTraversePotential = 15;
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
			Integer sheetCount = workbook.getNumberOfSheets();
			if (search.getHighwayBoqId() == null) {
				workbook.close();
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide highwayBoqId");
			}
			HighwayBoqMapping hbm = hbmDao.fetchCategoryBoqById(search.getHighwayBoqId());
			List<ChainageBoqQuantityMapping> oldCbqs = cbqDao.fetchChainageBoqQuantitiesByHbmId(hbm.getId());
			List<ChainageBoqQtyImportDTO> cbqObjList = new ArrayList<>();
			if (sheetCount != null && sheetCount > 0) {
				for (int i = 0; i < sheetCount; i++) {
					XSSFSheet worksheet = workbook.getSheetAt(i);
					int initialRowIndex = 0, initialColumnIndex = 0;
					for (int j = 0; j < worksheet.getPhysicalNumberOfRows(); j++) {

						XSSFRow tempRow = worksheet.getRow(j);
						for (int k = 0; k < tempRow.getPhysicalNumberOfCells(); k++) {
							if (getCellValueFromCell(tempRow.getCell(k)) != null
									&& (getCellValueFromCell(tempRow.getCell(k)).toLowerCase().trim()
											.contains("chainage")
											|| getCellValueFromCell(tempRow.getCell(k)).toLowerCase().trim()
													.contains("chainage"))) {
								initialRowIndex = j;
								initialColumnIndex = k;
								for (int m = initialRowIndex + 1; m < worksheet.getPhysicalNumberOfRows() + 1; m++) {
									XSSFRow row = worksheet.getRow(m);
									if (m == initialRowIndex + 1
											&& (getCellValueFromCell(row.getCell(initialColumnIndex + 0)) == null
													|| getCellValueFromCell(row.getCell(initialColumnIndex + 0))
															.replaceAll(" ", "").trim().isEmpty())) {
										continue;
									}
									if (m == worksheet.getPhysicalNumberOfRows()) {
										if (cbqObjList != null) {

											List<ChainageBoqQuantityMapping> toSaveObjList = new ArrayList<>();
											LinkedHashSet<String> importedChainageNames = new LinkedHashSet<>();
											List<BigInteger> importedNumericChainages = new ArrayList<>();
											for (ChainageBoqQtyImportDTO excelObj : cbqObjList) {
												if (excelObj.getChainage() != null) {
													String numericChainage = excelObj.getChainage().replaceAll("[^0-9]",
															"");
													importedNumericChainages.add(new BigInteger(numericChainage));
													if (numericChainage.length() > 3) {
														StringBuilder sb = new StringBuilder(numericChainage);
														sb.insert((sb.length() - 3), "+");
														importedChainageNames.add(sb.toString());
													}
												}
											}
											if (importedNumericChainages != null) {
												for (int tempCount = 0; tempCount < importedNumericChainages
														.size(); tempCount++) {
													if (tempCount == 0)
														continue;
													BigInteger currentValue = importedNumericChainages.get(tempCount);
													BigInteger prevValue = importedNumericChainages.get(tempCount - 1);
													if (currentValue.compareTo(prevValue) == -1) {
														workbook.close();
														return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
																"Chainage sequence is incorrect, please verify excel at chainage "
																		+ currentValue + " then try again.");
													}
												}
											}
											List<Chainage> updatedChainages = chainageService
													.checkSaveAndGetAllSiteChainages(importedChainageNames, search);
											for (ChainageBoqQtyImportDTO excelObj : cbqObjList) {
												if (excelObj.getChainage() != null) {
													String numericChainage = excelObj.getChainage().replaceAll("[^0-9]",
															"");
													if (numericChainage.length() > 3) {
														StringBuilder sb = new StringBuilder(numericChainage);
														sb.insert((sb.length() - 3), "+");
														numericChainage = sb.toString();
														if (excelObj.getLHS() == null)
															excelObj.setLHS(0.0);
														if (excelObj.getRHS() == null)
															excelObj.setRHS(0.0);
														Chainage chainage = null;
														for (Chainage obj : updatedChainages) {
															if (obj.getName().equalsIgnoreCase(numericChainage)) {
																chainage = obj;
																break;
															}
														}
														if (chainage == null)
															continue;
														ChainageBoqQuantityMapping obj = new ChainageBoqQuantityMapping(
																null, chainage, hbm.getBoq(), hbm, search.getSiteId(),
																excelObj.getLHS(), excelObj.getRHS(),
																excelObj.getStructureRemark(), true, new Date(),
																search.getUserId(), search.getCompanyId());
														toSaveObjList.add(obj);

													}
												}
											}

											Double totalArea = 0.0;
											Double totalVolume = 0.0;
											for (int cbqIdx = 0; cbqIdx < toSaveObjList.size(); cbqIdx++) {
												ChainageBoqQuantityMapping toSaveObj = toSaveObjList.get(cbqIdx);
												if (cbqIdx == 0) {
													totalArea += (toSaveObj.getLhsQuantity()
															+ toSaveObj.getRhsQuantity());
													continue;
												}
												ChainageBoqQuantityMapping previousToSaveObj = toSaveObjList
														.get(cbqIdx - 1);
												String numericChainage = toSaveObj.getChainage().getName()
														.replaceAll("[^0-9]", "");
												String previousChainage = previousToSaveObj.getChainage().getName()
														.replaceAll("[^0-9]", "");
												Double length = Double.valueOf(numericChainage)
														- Double.valueOf(previousChainage);
												Double previousArea = previousToSaveObj.getLhsQuantity()
														+ previousToSaveObj.getRhsQuantity();
												Double currentArea = toSaveObj.getLhsQuantity()
														+ toSaveObj.getRhsQuantity();
												totalArea += currentArea;
												if (toSaveObj.getStructureRemark() == null
														|| toSaveObj.getStructureRemark().isBlank()) {
													totalVolume += ((currentArea + previousArea) / 2) * length;
												}
											}

											Double totalBoqQuantity = totalArea;
											if (hbm.getBoq().getUnit().getType().getId()
													.equals(UnitTypes.VOLUME.getTypeId())) {
												totalBoqQuantity = totalVolume;
											}
											search.setBoqId(hbm.getBoq().getId());
											List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWorkDao
													.fetchWoBoqWorkQtysByBoqId(search);
											Double workorderQuantity = 0.0;
											if (woBoqQtys != null) {
												for (WorkorderBoqWorkQtyMapping woBoqQty : woBoqQtys) {
													workorderQuantity += woBoqQty.getQuantity();
												}
											}
											if (totalBoqQuantity < workorderQuantity) {
												workbook.close();
												return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
														"Total BOQ quantity is less than quantity issued in workorder for same Highway BOQ.");
											}

											List<ChainageBoqQuantityMapping> cbqToReturn = new ArrayList<ChainageBoqQuantityMapping>();
											if (toSaveObjList != null) {
												List<ChainageBoqQuantityMapping> cbqs = cbqDao
														.fetchChainageBoqQuantitiesByHbmId(hbm.getId());
												for (ChainageBoqQuantityMapping cbqObj : toSaveObjList) {
													search.setChainageId(cbqObj.getChainage().getId());

													if (cbqs != null && cbqs.size() > 0) {
														ChainageBoqQuantityMapping cbq = null;
														boolean isOld = false;
														for (ChainageBoqQuantityMapping cbqIdx : cbqs) {
															if (cbqIdx.getChainage().getId()
																	.equals(cbqObj.getChainage().getId())) {
																cbq = cbqIdx;
																isOld = true;
																break;
															}
														}
														if (!isOld) {
															Long id = cbqDao
																	.forceSaveChainageBoqQuantityMapping(cbqObj);
															if (id != null && id.longValue() > 0) {
																cbqObj.setId(id);
																ChainageBoqQuantityTransacs cbqTransac = setObject
																		.cbqEntityMappingToTransac(cbqObj);
																cbqTransac.setCreatedOn(new Date());
																cbqDao.saveChainageBoqQuantityTransac(cbqTransac);
																cbqToReturn.add(cbqObj);
															}
														}
														Boolean needTransac = false;
														if (!(cbqObj.getLhsQuantity().equals(cbq.getLhsQuantity())
																&& cbqObj.getRhsQuantity()
																		.equals(cbq.getRhsQuantity()))) {
															needTransac = true;
														}
														cbq.setLhsQuantity(cbqObj.getLhsQuantity());
														cbq.setRhsQuantity(cbqObj.getRhsQuantity());
														cbq.setStructureRemark(cbqObj.getStructureRemark());
														cbq.setModifiedBy(cbqObj.getModifiedBy());
														cbq.setModifiedOn(new Date());
														cbq.setIsActive(true);
														cbqDao.forceUpdateChainageBoqQuantityMapping(cbq);
														if (needTransac) {
															ChainageBoqQuantityTransacs cbqTransac = setObject
																	.cbqEntityMappingToTransac(cbq);
															cbqTransac.setCreatedOn(new Date());
															cbqDao.saveChainageBoqQuantityTransac(cbqTransac);
														}
														cbqToReturn.add(cbq);
													} else {

														Long id = cbqDao.forceSaveChainageBoqQuantityMapping(cbqObj);
														if (id != null && id.longValue() > 0) {
															cbqObj.setId(id);
															ChainageBoqQuantityTransacs cbqTransac = setObject
																	.cbqEntityMappingToTransac(cbqObj);
															cbqTransac.setCreatedOn(new Date());
															cbqDao.saveChainageBoqQuantityTransac(cbqTransac);
															cbqToReturn.add(cbqObj);
														}
													}

												}
											}
											workbook.close();

											if (oldCbqs != null) {
												for (ChainageBoqQuantityMapping cbqToRemove : oldCbqs) {
													boolean toRemove = true;
													for (ChainageBoqQuantityMapping newCbq : cbqToReturn) {
														if (newCbq.getId().equals(cbqToRemove.getId())) {
															toRemove = false;
															break;
														}
													}
													if (toRemove) {
														cbqToRemove.setIsActive(false);
														cbqToRemove.setModifiedBy(search.getUserId());
														cbqToRemove.setModifiedOn(new Date());

														Boolean update = cbqDao
																.forceUpdateAfterDetachChainageBoqQuantityMapping(
																		cbqToRemove);
														if (update) {
															ChainageBoqQuantityTransacs cbqTransac = setObject
																	.cbqEntityMappingToTransac(cbqToRemove);
															cbqTransac.setCreatedOn(new Date());
															cbqDao.saveChainageBoqQuantityTransac(cbqTransac);
														}
													}
												}
											}

											if (hbm.getQuantity() == null
													|| hbm.getQuantity().doubleValue() != totalBoqQuantity) {
												hbm.setQuantity(totalBoqQuantity);
												hbm.setVersion(hbm.getVersion() + 1);
												hbm.setQuantityVersion(hbm.getQuantityVersion() + 1);
											}
											hbmDao.updateCategoryBoqMapping(hbm);
											HighwayBoqTransacs bcmTransac = setObject
													.highwayBoqEntityMappingToTransac(hbm);
											bcmTransac.setCreatedOn(new Date());
											hbmDao.saveBoqCategoryTransac(bcmTransac);

											return new CustomResponse(Responses.SUCCESS.getCode(), null,
													Responses.SUCCESS.toString());

										}
									} else {
										ChainageBoqQtyImportDTO cbqImport = new ChainageBoqQtyImportDTO();
										String chainage = getCellValueFromCell(row.getCell(initialColumnIndex + 0));
										if (chainage == null || chainage.trim().isEmpty()) {
											continue;
										}
										cbqImport.setChainage(chainage.trim());
										String lhs = getCellValueFromCell(row.getCell(initialColumnIndex + 2));
										Double lhsNumeric = 0.0;
										if (lhs != null) {
											lhsNumeric = Double.valueOf(lhs);
										}
										cbqImport.setLHS(lhsNumeric);
										String rhs = getCellValueFromCell(row.getCell(initialColumnIndex + 3));
										Double rhsNumeric = 0.0;
										if (rhs != null) {
											rhsNumeric = Double.valueOf(rhs);
										}
										cbqImport.setRHS(rhsNumeric);

										try {
											String structureRemark = getCellValueFromCell(
													row.getCell(initialColumnIndex + 6));
											if (structureRemark != null && !structureRemark.isBlank()) {
												cbqImport.setStructureRemark(structureRemark.trim());
											}
										} catch (Exception e) {
											workbook.close();
											return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
													"Invalid excel, `Structure Remark` column undefined.");
										}

										cbqObjList.add(cbqImport);
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
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getChainageQuantitiesPerBoq(SearchDTO search) {

		try {
			List<ChainageBoqQuantityMapping> cbqList = cbqDao.fetchChainageBoqQuantityMappingBySearch(search);
			List<ChainageBoqQuantityMappingDTO> result = new ArrayList<ChainageBoqQuantityMappingDTO>();
			Set<Long> boqIds = new HashSet<>();
			search.setSearchField(null);
			List<Chainage> chainages = chainageDao.fetchChainages(search);
			if (cbqList != null) {
				for (ChainageBoqQuantityMapping cbq : cbqList) {
					result.add(setObject.chainageBoqQtyMapEntityToDto(cbq));
				}
			}
			if (search.getFromChainageId() != null && search.getToChainageId() != null) {
				String fromChainage = null;
				String toChainage = null;
				for (Chainage chainage : chainages) {
					if (chainage.getId().equals(search.getFromChainageId()))
						fromChainage = chainage.getName();
					if (chainage.getId().equals(search.getToChainageId()))
						toChainage = chainage.getName();
				}
				if (fromChainage != null && toChainage != null) {
					String fromChainageNum = fromChainage.replaceAll("[^0-9]", "");
					String toChainageNum = toChainage.replaceAll("[^0-9]", "");
					if (!fromChainageNum.isEmpty() && !toChainageNum.isEmpty()) {
						for (int i = 0; i < result.size(); i++) {
							ChainageBoqQuantityMappingDTO cbqDto = result.get(i);
							if (cbqDto.getChainage() != null && cbqDto.getChainage().getName() != null) {
								String chainageNum = cbqDto.getChainage().getName().replaceAll("[^0-9]", "");
								if (!chainageNum.isEmpty()) {
									int chainageInt = Integer.parseInt(chainageNum);
									int fromChainageInt = Integer.parseInt(fromChainageNum);
									int toChainageInt = Integer.parseInt(toChainageNum);
									if (!(chainageInt >= fromChainageInt && chainageInt <= toChainageInt)) {
										result.set(i, null);
									}
								}
							} else {
								result.set(i, null);
							}
						}
						result.removeAll(Collections.singleton(null));
					}

				}
			}
			List<ChainageBoqQuantityMappingDTO> boqWiseResult = new ArrayList<ChainageBoqQuantityMappingDTO>();
			if (result != null && result.size() > 0) {
				for (ChainageBoqQuantityMappingDTO obj : result) {
					boqIds.add(obj.getBoq().getId());
				}
				if (boqIds.size() > 0) {
					for (Long boqId : boqIds) {
						ChainageBoqQuantityMappingDTO obj = null;
						for (ChainageBoqQuantityMappingDTO objDTO : result) {
							if (boqId.equals(objDTO.getBoq().getId())) {
								if (obj == null) {
									obj = new ChainageBoqQuantityMappingDTO(null, null, objDTO.getBoq(),
											objDTO.getHighwayBoq(), objDTO.getSiteId(), objDTO.getLhsQuantity(),
											objDTO.getRhsQuantity(), objDTO.getStructureRemark(), true, null, null,
											objDTO.getCompanyId());
								} else {
									Double lhsQuant = obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0;
									Double rhsQuant = obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0;
									obj.setLhsQuantity(lhsQuant);
									obj.setRhsQuantity(rhsQuant);
									obj.setArea(lhsQuant + rhsQuant);
									Double lengthFromPrevious = 0.00;
									if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
										if (obj.getChainage().getName() != null
												&& obj.getChainage().getPrevious().getName() != null) {
											Double previousChainageNum = Double.parseDouble(
													obj.getChainage().getPrevious().getName().replaceAll("[^0-9]", ""));
											Double currentChainageNum = Double
													.parseDouble(obj.getChainage().getName().replaceAll("[^0-9]", ""));
											lengthFromPrevious = currentChainageNum - previousChainageNum;
										}
									}
									obj.setLength(lengthFromPrevious);
									obj.setVolume(obj.getArea() * obj.getLength());
								}
							}
						}
						if (obj != null)
							boqWiseResult.add(obj);
					}
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), boqWiseResult, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public CustomResponse getBoqWiseCbq(SearchDTO search) {

		try {
			List<ChainageBoqQuantityMapping> cbqList = cbqDao.fetchBoqWiseCbq(search);
			List<ChainageBoqQuantityMappingDTO> result = new ArrayList<ChainageBoqQuantityMappingDTO>();
			double totalDistributedQuantity = 0.0;
			if (cbqList != null) {
				int counter = 0;
				for (ChainageBoqQuantityMapping cbq : cbqList) {
					cbq.setBoq(null);
					cbq.setHighwayBoq(null);
					totalDistributedQuantity += ((cbq.getRhsQuantity() != null ? cbq.getRhsQuantity() : 0.0)
							+ (cbq.getLhsQuantity() != null ? cbq.getLhsQuantity() : 0.0));
					ChainageBoqQuantityMappingDTO obj = setObject.chainageBoqQtyMapEntityToDto(cbq);
					obj.setArea((obj.getLhsQuantity() != null ? obj.getLhsQuantity() : 0.0)
							+ (obj.getRhsQuantity() != null ? obj.getRhsQuantity() : 0.0));
					if (obj.getChainage() != null && obj.getChainage().getPrevious() != null) {
						Integer length = ((Integer.parseInt(obj.getChainage().getName().replaceAll("[^0-9]", "")))
								- (Integer
										.parseInt(obj.getChainage().getPrevious().getName().replaceAll("[^0-9]", ""))));
						obj.setLength(length.doubleValue());
						if (counter == 0) {
							obj.setLength(0.0);
							obj.setVolume(0.0);
						}
						if (counter > 0) {
							Double previousArea = (cbqList.get(counter - 1).getLhsQuantity() != null
									? cbqList.get(counter - 1).getLhsQuantity()
									: 0.0)
									+ (cbqList.get(counter - 1).getRhsQuantity() != null
											? cbqList.get(counter - 1).getRhsQuantity()
											: 0.0);
							Double currentArea = obj.getArea();
							double meanArea = (previousArea + currentArea) / 2;
							if (cbq.getStructureRemark() == null || cbq.getStructureRemark().isBlank()) {
								obj.setVolume(meanArea * obj.getLength());
							} else {
								obj.setVolume(0.0);
							}
						}
					} else {
						obj.setLength(0.0);
						obj.setVolume(0.0);
					}
					result.add(obj);
					counter++;
				}
			}
			HighwayBoqMapping hbm = hbmDao.fetchCategoryBoqById(search.getHighwayBoqId());
			HighwayBoqMappingDTO hbmToReturn = setObject.boqCategoryMapEntityToDto(hbm);
			HighwayBoqChainageRenderDTO renderResult = new HighwayBoqChainageRenderDTO(hbmToReturn, result,
					totalDistributedQuantity);
			return new CustomResponse(Responses.SUCCESS.getCode(), renderResult, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}

	}

	@Override
	public CustomResponse getCbqById(SearchDTO search) {

		try {
			ChainageBoqQuantityMapping cbq = cbqDao.fetchCbqById(search.getCbqId());
			ChainageBoqQuantityMappingDTO result = setObject.chainageBoqQtyMapEntityToDto(cbq);
			result.setArea((result.getLhsQuantity() != null ? result.getLhsQuantity() : 0.0)
					+ (result.getRhsQuantity() != null ? result.getRhsQuantity() : 0.0));
			if (result.getChainage() != null && result.getChainage().getPrevious() != null) {
				result.setLength((Double.parseDouble(result.getChainage().getName().replaceAll("[^0-9]", "")))
						- (Double.parseDouble(result.getChainage().getPrevious().getName().replaceAll("[^0-9]", ""))));
				result.setVolume(result.getArea() * result.getLength());
			} else {
				result.setLength(0.0);
				result.setVolume(0.0);
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
	public CustomResponse removeChainageBoqQuantityMapping(SearchDTO search) {

		try {
			ChainageBoqQuantityMapping cbq = cbqDao.fetchCbqById(search.getCbqId());
			HighwayBoqMapping hbm = cbq.getHighwayBoq();
			List<ChainageBoqQuantityMapping> oldCbqs = cbqDao
					.fetchChainageBoqQuantitiesByHbmIdWithOneCbqLess(cbq.getHighwayBoq().getId(), cbq.getId());
			Double totalArea = 0.0;
			Double totalVolume = 0.0;
			for (int i = 0; i < oldCbqs.size(); i++) {
				ChainageBoqQuantityMapping toSaveObj = oldCbqs.get(i);
				totalArea += (toSaveObj.getLhsQuantity() + toSaveObj.getRhsQuantity());
				if (i == 0) {
					continue;
				}
				ChainageBoqQuantityMapping previousToSaveObj = oldCbqs.get(i - 1);
				String numericChainage = toSaveObj.getChainage().getName().replaceAll("[^0-9]", "");
				String previousChainage = previousToSaveObj.getChainage().getName().replaceAll("[^0-9]", "");
				Double length = Double.valueOf(numericChainage) - Double.valueOf(previousChainage);
				Double previousArea = previousToSaveObj.getLhsQuantity() + previousToSaveObj.getRhsQuantity();
				Double currentArea = toSaveObj.getLhsQuantity() + toSaveObj.getRhsQuantity();
				if (!(toSaveObj.getStructureRemark() == null || toSaveObj.getStructureRemark().isBlank())) {
					length = 0.0;
				}
				totalVolume += ((currentArea + previousArea) / 2) * length;
			}
			Double totalBoqQuantity = totalArea;
			if (cbq.getBoq().getUnit().getType().getId().equals(UnitTypes.VOLUME.getTypeId())) {
				totalBoqQuantity = totalVolume;
			}
			search.setBoqId(cbq.getBoq().getId());
			List<WorkorderBoqWorkQtyMapping> woBoqWorkQtys = woBoqWorkDao.fetchWoBoqWorkQtysByBoqId(search);
			Double woBoqQty = 0.0;
			if (woBoqWorkQtys != null) {
				for (WorkorderBoqWorkQtyMapping woBoq : woBoqWorkQtys) {
					woBoqQty += woBoq.getQuantity();
				}
			}
			if (totalBoqQuantity < woBoqQty) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Quantity used in workorder, can't remove!");
			}
			cbq.setIsActive(false);
			cbq.setModifiedBy(search.getUserId());
			cbq.setModifiedOn(new Date());

			Boolean update = cbqDao.updateChainageBoqQuantityMapping(cbq);
			if (update) {
				if (hbm.getQuantity().doubleValue() != totalBoqQuantity) {
					hbm.setQuantity(totalBoqQuantity);
					hbm.setVersion(hbm.getVersion() + 1);
					hbm.setQuantityVersion(hbm.getQuantityVersion() + 1);
				}
				hbmDao.updateCategoryBoqMapping(hbm);
				HighwayBoqTransacs bcmTransac = setObject.highwayBoqEntityMappingToTransac(hbm);
				bcmTransac.setCreatedOn(new Date());
				hbmDao.saveBoqCategoryTransac(bcmTransac);

				ChainageBoqQuantityTransacs cbqTransac = setObject.cbqEntityMappingToTransac(cbq);
				cbqTransac.setCreatedOn(new Date());
				cbqDao.saveChainageBoqQuantityTransac(cbqTransac);
				return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
			}

			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), false, Responses.SUCCESS.toString());

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
			case FORMULA:
				switch (cell.getCachedFormulaResultTypeEnum()) {
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
