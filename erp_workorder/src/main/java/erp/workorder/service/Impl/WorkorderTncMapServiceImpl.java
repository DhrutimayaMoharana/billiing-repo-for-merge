package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WorkorderBasicDetailDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderTncDao;
import erp.workorder.dao.WorkorderTncMapDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTncFormulaVariableDTO;
import erp.workorder.dto.WoTncFormulaVariableValueDTO;
import erp.workorder.dto.WoTncMappingDTO;
import erp.workorder.dto.WoTncMappingRequestDTO;
import erp.workorder.dto.WoTypeTncMappingDTO;
import erp.workorder.entity.WoTncFormulaVariable;
import erp.workorder.entity.WoTncFormulaVariableValue;
import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.WoTypeTncMapping;
import erp.workorder.entity.Workorder;
import erp.workorder.enums.EngineStates;
import erp.workorder.enums.Responses;
import erp.workorder.enums.VariableTypes;
import erp.workorder.service.WorkorderTncMapService;
import erp.workorder.util.CustomSqlHelper;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderTncMapServiceImpl implements WorkorderTncMapService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderTncDao woTncDao;
	@Autowired
	private SetObject setObject;
	@Autowired
	WorkorderTncMapDao woTncMapDao;
	@Autowired
	WorkorderBasicDetailDao woBasicDao;
	@Autowired
	private WorkorderDao workorderDao;

	@Override
	public CustomResponse getWoTermsConditionsByWoId(SearchDTO search) {

		try {
			Boolean isTncMapped = false;
			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			List<WoTncMapping> woMappedTncs = woTncMapDao.fetchWorkorderTncMapByWorkorderId(workorder.getId());
			Set<WoTncMapping> removeDups = new LinkedHashSet<>();
			if (woMappedTncs != null && woMappedTncs.size() > 0) {
				List<WoTncMappingDTO> resultWoMappedTncs = new ArrayList<>();
				removeDups.addAll(woMappedTncs);
				woMappedTncs.clear();
				woMappedTncs.addAll(removeDups);
				for (WoTncMapping obj : woMappedTncs) {
					List<WoTncFormulaVariableValue> remDup = obj.getVariableValues();
					Set<WoTncFormulaVariableValue> removeDups2 = new LinkedHashSet<>();
					removeDups2.addAll(remDup);
					remDup.clear();
					remDup.addAll(removeDups2);
					obj.setVariableValues(remDup);
				}

				if (woMappedTncs == null || woMappedTncs.isEmpty())
					isTncMapped = true;

				if (woMappedTncs != null) {
					woMappedTncs.forEach(item -> {
						if (item.getIsActive() != null && item.getIsActive())
							resultWoMappedTncs.add(setObject.woTncMappingEntityToDto(item));

					});
					for (WoTncMappingDTO obj : resultWoMappedTncs) {
						String descriptionToReplace = obj.getDescription();
						if (obj.getVariableValues() != null && obj.getVariableValues().size() > 0) {
							for (WoTncFormulaVariableValueDTO vvDTO : obj.getVariableValues()) {
								if (vvDTO.getValueAtCreation() != null && !vvDTO.getValueAtCreation()
										&& vvDTO.getMasterVariable() != null
										&& !vvDTO.getMasterVariable().getSqlQuery().isBlank()) {

									try {
										Map<String, Object> sqlVariablesMap = new HashedMap<>();
//										Insert Variables
										sqlVariablesMap.put("siteId", workorder.getSiteId());
										sqlVariablesMap.put("companyId", search.getCompanyId());
										sqlVariablesMap.put("workorderId", search.getWorkorderId());

//										Update Query
										String sqlQuery = vvDTO.getMasterVariable().getSqlQuery();
										sqlQuery = CustomSqlHelper.getUpdatedSqlExecutionString(sqlQuery,
												sqlVariablesMap);
										Double value = CustomSqlHelper.defaultDoubleVariableValue;
										try {
											value = (Double) woTncMapDao.executeSQLQuery(sqlQuery);
										} catch (Exception e) {
											e.printStackTrace();
										}

										String variableDecimalFormat = "%01d";
										if (value != null) {
											if (vvDTO.getType().equals(VariableTypes.Double)) {
												variableDecimalFormat = "%.2f";
												String valueToReplace = (String.format(variableDecimalFormat, value)
														+ " "
														+ (vvDTO.getUnit() != null ? vvDTO.getUnit().getName() : ""));
												descriptionToReplace = descriptionToReplace.replace(
														SetObject.tncVariableDelimeter + vvDTO.getVariable().getName(),
														valueToReplace);
											} else {
												Integer intValue = value.intValue();
												String valueToReplace = (String.format(variableDecimalFormat, intValue)
														+ " "
														+ (vvDTO.getUnit() != null ? vvDTO.getUnit().getName() : ""));
												descriptionToReplace = descriptionToReplace.replace(
														SetObject.tncVariableDelimeter + vvDTO.getVariable().getName(),
														valueToReplace);
											}
										} else {
											String valueToReplace = (" - "
													+ (vvDTO.getUnit() != null ? vvDTO.getUnit().getName() : ""));
											descriptionToReplace = descriptionToReplace.replace(
													SetObject.tncVariableDelimeter + vvDTO.getVariable().getName(),
													valueToReplace);
										}

									} catch (Exception e) {
										String valueToReplace = (" - "
												+ (vvDTO.getUnit() != null ? vvDTO.getUnit().getName() : ""));
										descriptionToReplace = descriptionToReplace.replace(
												SetObject.tncVariableDelimeter + vvDTO.getVariable().getName(),
												valueToReplace);

									}
								}
							}
						}
						obj.setDescription(descriptionToReplace);
					}

				}
				return new CustomResponse(Responses.SUCCESS.getCode(),
						resultWoMappedTncs != null && resultWoMappedTncs.size() > 0 ? resultWoMappedTncs : null,
						Responses.SUCCESS.toString(), isTncMapped);
			} else {
				List<WoTncMapping> woTncMaps = woTncMapDao.fetchWorkorderTncMapByWorkorderId(workorder.getId());

				if (woTncMaps == null || woTncMaps.isEmpty())
					isTncMapped = true;

				Set<Long> woTncIdsByWo = new HashSet<Long>();
				if (woTncMaps != null) {
					for (WoTncMapping woTncMap : woTncMaps) {
						if (woTncMap.getTermAndCondition() != null) {
							woTncIdsByWo.add(woTncMap.getTermAndCondition().getId());
						}
					}
				}
				List<WoTypeTncMappingDTO> resultTypeTncs = new ArrayList<>();
				List<WoTypeTncMapping> typeTncs = woTncDao
						.fetchWoTypeTermsConditions(workorder.getType().getId().longValue());
				if (typeTncs != null) {
					typeTncs.forEach(item -> resultTypeTncs.add(setObject.woTypeTncMappingEntityToDto(item)));
				}
				if (resultTypeTncs != null) {
					for (int i = 0; i < resultTypeTncs.size(); i++) {
						WoTypeTncMappingDTO woTypeTncMap = resultTypeTncs.get(i);
						for (Long woTncIdByWo : woTncIdsByWo) {
							if (woTncIdByWo.equals(woTypeTncMap.getWoTnc().getId())) {
								resultTypeTncs.set(i, null);
							}
						}
					}
					resultTypeTncs.removeAll(Collections.singleton(null));
				}
				List<WoTncMappingDTO> resultDTO = new ArrayList<>();
				if (typeTncs != null) {
					for (WoTypeTncMappingDTO typeTnc : resultTypeTncs) {
						WoTncMappingDTO obj = new WoTncMappingDTO();
						obj.setTermAndCondition(typeTnc.getWoTnc());
						obj.setDescription(typeTnc.getWoTnc().getDescription());
						String descriptionToReplace = obj.getDescription();
						List<WoTncFormulaVariableValueDTO> vvs = new ArrayList<>();
						if (typeTnc.getWoTnc().getFormulaVariables() != null) {
							for (WoTncFormulaVariableDTO v : typeTnc.getWoTnc().getFormulaVariables()) {
								if (!v.getIsActive()) {
									continue;
								}
								WoTncFormulaVariableValueDTO vv = new WoTncFormulaVariableValueDTO();
								vv.setVariable(v.getVariable());
								vv.setUnit(v.getUnit());
								vv.setType(v.getType());
								if (v.getValueAtCreation() != null && !v.getValueAtCreation()
										&& v.getMasterVariable() != null && v.getMasterVariable().getSqlQuery() != null
										&& !v.getMasterVariable().getSqlQuery().isBlank()) {

									try {
										Map<String, Object> sqlVariablesMap = new HashedMap<>();
//										Insert Variables
										sqlVariablesMap.put("siteId", workorder.getSiteId());
										sqlVariablesMap.put("companyId", search.getCompanyId());
										sqlVariablesMap.put("workorderId", search.getWorkorderId());

//										Update Query
										String sqlQuery = v.getMasterVariable().getSqlQuery();
										sqlQuery = CustomSqlHelper.getUpdatedSqlExecutionString(sqlQuery,
												sqlVariablesMap);
										Double value = CustomSqlHelper.defaultDoubleVariableValue;
										try {
											value = (Double) woTncMapDao.executeSQLQuery(sqlQuery);
										} catch (Exception e) {
											e.printStackTrace();
										}

										String variableDecimalFormat = "%01d";
										if (value != null) {
											if (v.getType().equals(VariableTypes.Double)) {
												variableDecimalFormat = "%.2f";
												String valueToReplace = (String.format(variableDecimalFormat, value)
														+ " " + (v.getUnit() != null ? v.getUnit().getName() : ""));
												descriptionToReplace = descriptionToReplace.replace(
														SetObject.tncVariableDelimeter + v.getVariable().getName(),
														valueToReplace);
											} else {
												Integer intValue = value.intValue();
												String valueToReplace = (String.format(variableDecimalFormat, intValue)
														+ " " + (v.getUnit() != null ? v.getUnit().getName() : ""));
												descriptionToReplace = descriptionToReplace.replace(
														SetObject.tncVariableDelimeter + v.getVariable().getName(),
														valueToReplace);
											}
										} else {
											String valueToReplace = (" - "
													+ (v.getUnit() != null ? v.getUnit().getName() : ""));
											descriptionToReplace = descriptionToReplace.replace(
													SetObject.tncVariableDelimeter + v.getVariable().getName(),
													valueToReplace);
										}

									} catch (Exception e) {
										String valueToReplace = (" - "
												+ (v.getUnit() != null ? v.getUnit().getName() : ""));
										descriptionToReplace = descriptionToReplace.replace(
												SetObject.tncVariableDelimeter + v.getVariable().getName(),
												valueToReplace);
									}
								}
								vvs.add(vv);
							}
							obj.setVariableValues(vvs);
						}
						obj.setDescription(descriptionToReplace);
						resultDTO.add(obj);
					}
				}
				return new CustomResponse(Responses.SUCCESS.getCode(), resultDTO, Responses.SUCCESS.toString(),
						isTncMapped);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse mapWoTermsConditions(WoTncMappingRequestDTO woTncsMap) {

		try {
			if (woTncsMap.getWorkorderId() == null) {
				return new CustomResponse(Responses.SUCCESS.getCode(), false, "Provide appropriate workorderId...");
			}
			Workorder workorder = workorderDao.fetchWorkorderById(woTncsMap.getWorkorderId());
			if (workorder == null) {
				return new CustomResponse(Responses.SUCCESS.getCode(), false, "Provide appropriate workorderId...");
			} else if (workorder != null && workorder.getTermsAndConditions() != null
					&& workorder.getTermsAndConditions().size() > 0) {
				return new CustomResponse(Responses.SUCCESS.getCode(), false,
						"Terms and Conditions have already been created for this workorder...");
			}
			if (!workorder.getState().getId().equals(EngineStates.Issued.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in draft state.");
			List<WoTncMappingDTO> woTncsDTO = woTncsMap.getTncs();
			List<WoTncMapping> woTncs = new ArrayList<>();
			if (woTncsDTO != null) {
				woTncsDTO.forEach(item -> woTncs.add(setObject.woTncMappingDtoToEntity(item, workorder)));
			}
			if (woTncs != null && woTncs.size() > 0) {
				for (WoTncMapping woTnc : woTncs) {
					woTnc.setWorkorder(workorder);
					woTnc.setModifiedOn(new Date());
					woTnc.setModifiedBy(woTncsMap.getCreatedBy());
					woTnc.setIsActive(true);
					List<WoTncFormulaVariableValue> variableValues = woTnc.getVariableValues();
					if (variableValues != null) {
						for (WoTncFormulaVariableValue variableValue : variableValues) {
							variableValue.setWoTnc(woTnc);
							variableValue.setModifiedOn(new Date());
							variableValue.setModifiedBy(woTncsMap.getCreatedBy());
							if (variableValue.getVariable() == null || variableValue.getVariable().getId() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										Responses.BAD_REQUEST.toString());
							}

							WoTncFormulaVariable formulaVar = woTncDao.fetchWoTncFormulaVariableByTncAndVar(
									woTnc.getTermAndCondition().getId(), variableValue.getVariable().getId());
							if (formulaVar != null) {
								variableValue.setMasterVariable(formulaVar.getMasterVariable());
								variableValue.setValueAtCreation(formulaVar.getValueAtCreation());
							}
							if (variableValue.getValueAtCreation() != null && !variableValue.getValueAtCreation()) {
								variableValue.setValue(null);
							}
						}
					}
				}
				workorder.setTermsAndConditions(woTncs);
				workorder.setIsActive(true);
				Boolean result = workorderDao.forceUpdateWorkorder(workorder);
				return new CustomResponse(Responses.SUCCESS.getCode(), (result != null && result ? true : false),
						Responses.SUCCESS.toString());
			} else {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addUpdateWoTermsCondition(WoTncMappingRequestDTO woTncsMap) {

		try {
			if (woTncsMap.getWorkorderId() == null) {
				return new CustomResponse(Responses.SUCCESS.getCode(), false, "Provide appropriate workorderId...");
			}
			Workorder workorder = workorderDao.fetchWorkorderById(woTncsMap.getWorkorderId());

			if (workorder == null) {
				return new CustomResponse(Responses.SUCCESS.getCode(), false, "Provide appropriate workorderId...");
			}
			if (workorder.getState().getId().equals(EngineStates.Issued.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in editable state.");
			List<WoTncMappingDTO> woTncsDTO = woTncsMap.getTncs();
			List<WoTncMapping> woTncs = new ArrayList<>();
			if (woTncsDTO != null) {
				woTncsDTO.forEach(item -> woTncs.add(setObject.woTncMappingDtoToEntity(item, workorder)));
			}
			if (woTncs != null && woTncs.size() > 0) {
				for (WoTncMapping woTnc : woTncs) {
					woTnc.setWorkorder(workorder);
					woTnc.setModifiedOn(new Date());
					woTnc.setModifiedBy(woTncsMap.getCreatedBy());
					woTnc.setIsActive(true);
					List<WoTncFormulaVariableValue> variableValues = woTnc.getVariableValues();
					if (variableValues != null) {
						for (WoTncFormulaVariableValue variableValue : variableValues) {
							if ((woTnc.getId() != null && variableValue.getId() == null)
									|| variableValue.getVariable() == null
									|| variableValue.getVariable().getId() == null || variableValue.getUnit() == null
									|| variableValue.getUnit().getId() == null) {
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										Responses.BAD_REQUEST.toString());
							}
							variableValue.setWoTnc(woTnc);
							WoTncFormulaVariable formulaVar = woTncDao.fetchWoTncFormulaVariableByTncAndVar(
									woTnc.getTermAndCondition().getId(), variableValue.getVariable().getId());
							if (formulaVar != null) {
								variableValue.setType(formulaVar.getType());
								variableValue.setMasterVariable(formulaVar.getMasterVariable());
								variableValue.setValueAtCreation(formulaVar.getValueAtCreation());
							}

							if (variableValue.getValueAtCreation() != null && !variableValue.getValueAtCreation()) {
								variableValue.setValue(null);
							}
							variableValue.setModifiedOn(new Date());
							variableValue.setModifiedBy(woTncsMap.getCreatedBy());
						}
					}
				}
				Boolean result = false;
				for (WoTncMapping woTnc : woTncs)
					result = woTncMapDao.saveUpdateWorkorderTncs(woTnc);
				return new CustomResponse(Responses.SUCCESS.getCode(), (result != null && result ? true : false),
						Responses.SUCCESS.toString());
			} else {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse removeWoTermsCondition(SearchDTO search) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			if (!workorder.getState().getId().equals(EngineStates.Issued.getValue())) {
				WoTncMapping woTncMap = woTncMapDao.fetchWorkorderTncMapById(search.getWoTncId());
				woTncMap.setIsActive(false);
				woTncMap.setModifiedBy(search.getUserId());
				woTncMap.setModifiedOn(new Date());
				woTncMapDao.forceUpdateWorkorderTncMap(woTncMap);
				return new CustomResponse(Responses.SUCCESS.getCode(), true,
						"Inactivated workorder term and condition...");
			} else {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), false, "Workorder is not in editable state.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
