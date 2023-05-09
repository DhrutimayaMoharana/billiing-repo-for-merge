package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WoFormulaVariableDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderTncDao;
import erp.workorder.dao.WorkorderTncTypeDao;
import erp.workorder.dao.WorkorderTypeTncMapDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.MinimalObjectDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTncDTO;
import erp.workorder.dto.WoTncFormulaVariableDTO;
import erp.workorder.dto.WoTncFormulaVariableValueDTO;
import erp.workorder.dto.WoTncMappingDTO;
import erp.workorder.dto.WoTypeTncMappingDTO;
import erp.workorder.dto.response.WorkorderMasterVariableGetResponse;
import erp.workorder.entity.WoFormulaVariable;
import erp.workorder.entity.WoTnc;
import erp.workorder.entity.WoTncFormulaVariable;
import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.WoTncType;
import erp.workorder.entity.WoTypeTncMapping;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderMasterVariable;
import erp.workorder.enums.Responses;
import erp.workorder.enums.VariableTypes;
import erp.workorder.service.WorkorderTncService;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class WorkorderTncServiceImpl implements WorkorderTncService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WoFormulaVariableDao woFormulaVariableDao;
	@Autowired
	private WorkorderTncDao woTncDao;
	@Autowired
	private WorkorderDao workorderDao;
	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderTncTypeDao woTncTypeDao;
	@Autowired
	private WorkorderTypeTncMapDao woTypeTncMapDao;

	@Override
	public CustomResponse getWoTypeWiseTermsConditions(SearchDTO search) {

		try {
			if (search.getWorkorderId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), false, Responses.BAD_REQUEST.toString());
			}

			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			if (workorder != null && workorder.getType() != null) {
				search.setWoTypeId(workorder.getType().getId());
			}
			List<WoTncMapping> woTncMaps = workorder.getTermsAndConditions();
			Set<Long> woTncIdsByWo = new HashSet<Long>();
			if (woTncMaps != null) {
				for (WoTncMapping woTncMap : woTncMaps) {
					if (woTncMap.getIsActive() != null && woTncMap.getIsActive()
							&& woTncMap.getTermAndCondition() != null) {
						woTncIdsByWo.add(woTncMap.getTermAndCondition().getId());
					}
				}
			}
			List<WoTypeTncMappingDTO> resultTypeTncs = new ArrayList<>();
			List<WoTypeTncMapping> typeTncs = woTncDao.fetchWoTypeTermsConditions(search.getWoTypeId().longValue());
			if (typeTncs != null) {
				typeTncs.forEach(item -> resultTypeTncs.add(setObject.woTypeTncMappingEntityToDto(item)));
			}
			search.setFilterWoTnC(search.getFilterWoTnC() != null ? search.getFilterWoTnC() : true);
			if (resultTypeTncs != null && search.getFilterWoTnC()) {
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
							vvs.add(vv);
						}
						obj.setVariableValues(vvs);
					}
					resultDTO.add(obj);
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultDTO, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWoTermsConditions(SearchDTO search) {

		try {
			List<WoTncDTO> resultTncs = new ArrayList<>();
			List<WoTnc> tncs = woTncDao.fetchActiveTermsConditionsBySearch(search);
			if (search.getWoTypeId() != null && tncs != null) {
				List<WoTypeTncMapping> woTypeTncMaps = new ArrayList<WoTypeTncMapping>();
				woTypeTncMaps = woTypeTncMapDao.fetchWoTypeTncs(search);
				Set<Long> woTncsToRemove = new LinkedHashSet<>();
				if (woTypeTncMaps != null) {
					for (WoTypeTncMapping obj : woTypeTncMaps) {
						woTncsToRemove.add(obj.getWoTnc().getId());
					}
				}
				for (int i = 0; i < tncs.size(); i++) {
					boolean doRemove = false;
					for (Long idToRemove : woTncsToRemove) {
						if (idToRemove.equals(tncs.get(i).getId())) {
							doRemove = true;
							break;
						}
					}
					if (doRemove) {
						tncs.set(i, null);
					}
				}
				tncs.removeAll(Collections.singleton(null));
			}
			if (tncs != null) {
				tncs.forEach(item -> resultTncs.add(setObject.woTncEntityToDto(item)));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultTncs, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addWoTermsCondition(WoTncDTO woTncDTO) {

		try {
			SearchDTO search = new SearchDTO();
			WoTnc tnc = setObject.woTncDtoToEntity(woTncDTO);
			if (tnc.getCompanyId() == null || tnc.getDescription() == null || tnc.getDescription().isEmpty()
					|| tnc.getModifiedBy() == null || tnc.getType() == null || tnc.getType().getId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			if (tnc.getFormulaVariables() != null && tnc.getFormulaVariables().size() > 0) {
				List<WoTncFormulaVariable> woTncFVs = tnc.getFormulaVariables();
				String description = tnc.getDescription();
				boolean hasAllVariables = true;
				for (WoTncFormulaVariable tncFV : woTncFVs) {
					String variableName = tncFV.getVariable().getName().trim();
					String variableNameWithDelimeter = SetObject.tncVariableDelimeter + variableName;
					if (!(description).toLowerCase().contains(variableNameWithDelimeter.toLowerCase())) {
						hasAllVariables = false;
						break;
					} else {
						String replacedDescription = getReplacedDescriptionRecursion(description,
								variableNameWithDelimeter);
						tnc.setDescription(replacedDescription);
						description = replacedDescription;
					}
				}
				if (!hasAllVariables) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
				}
				for (WoTncFormulaVariable tncFV : woTncFVs) {
					if (tncFV.getVariable() == null || tncFV.getVariable().getName() == null
							|| tncFV.getVariable().getName().isEmpty()) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Variable Name is invalid for variable " + tncFV.getVariable().getName());
					}
					if (tncFV.getValueAtCreation() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"'Value at creation' field cannot be null for variable "
										+ tncFV.getVariable().getName());
					}
					if (!tncFV.getValueAtCreation()
							&& (tncFV.getMasterVariable() == null || tncFV.getMasterVariable().getId() == null)) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Master Variable is invalid for variable " + tncFV.getVariable().getName());
					}
					if (tncFV.getType() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Variable Type cannot be null for variable " + tncFV.getVariable().getName());
					}
					if (tncFV.getUnit() == null || tncFV.getUnit().getId() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Variable Unit is invalid for variable " + tncFV.getVariable().getName());
					}
					String variableName = tncFV.getVariable().getName().toLowerCase().trim();
					search.setName(variableName);
					if (tncFV.getUnit() == null || tncFV.getUnit().getId().longValue() == 0L) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Please provide appropriate unit for variable : " + variableName);
					}
					if (tncFV.getType() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Please provide appropriate variable type for variable : " + variableName);
					}
					search.setCompanyId(tnc.getCompanyId());
					search.setUserId(tnc.getModifiedBy());
					Long variableId = woFormulaVariableDao.getIdByNameOrSave(search);
					WoFormulaVariable variable = new WoFormulaVariable(variableId);
					tncFV.setVariable(variable);
					tncFV.setCreatedOn(new Date());
					tncFV.setIsActive(true);
					tncFV.setCreatedBy(tnc.getModifiedBy());
				}
				tnc.setFormulaVariables(woTncFVs);
			}
			tnc.setModifiedOn(new Date());
			tnc.setIsActive(true);
			Long id = woTncDao.saveTermCondition(tnc);
			return new CustomResponse(Responses.SUCCESS.getCode(), id,
					((id != null && id.longValue() > 0L) ? "Saved" : "Already exists..."));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateWoTermsCondition(WoTncDTO woTncDTO) {

		try {
			SearchDTO search = new SearchDTO();
			WoTnc tnc = setObject.woTncDtoToEntity(woTncDTO);
			WoTnc savedTnc = woTncDao.fetchWoTncById(tnc.getId());
			if (savedTnc == null || tnc.getId() == null || tnc.getCompanyId() == null || tnc.getDescription() == null
					|| tnc.getDescription().isEmpty() || tnc.getModifiedBy() == null || tnc.getType() == null
					|| tnc.getType().getId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			if (tnc.getFormulaVariables() != null && tnc.getFormulaVariables().size() > 0) {
				List<WoTncFormulaVariable> woTncFVs = tnc.getFormulaVariables();
				String description = tnc.getDescription();
				boolean hasAllVariables = true;
				for (WoTncFormulaVariable tncFV : woTncFVs) {
					String variableName = tncFV.getVariable().getName().trim();
					String variableNameWithDelimeter = SetObject.tncVariableDelimeter + variableName;
					if (!description.toLowerCase().contains(variableNameWithDelimeter.toLowerCase())) {
						hasAllVariables = false;
						break;
					} else {
						String replacedDescription = getReplacedDescriptionRecursion(description,
								variableNameWithDelimeter);
						tnc.setDescription(replacedDescription);
						description = replacedDescription;
					}
				}
				if (!hasAllVariables) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"All mentioned variables not present in request.");
				}
				for (WoTncFormulaVariable tncFV : woTncFVs) {
					if (tncFV.getVariable() == null || tncFV.getVariable().getName() == null
							|| tncFV.getVariable().getName().isEmpty()) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Variable Name is invalid for variable " + tncFV.getVariable().getName());
					}
					if (tncFV.getValueAtCreation() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"'Value at creation' field cannot be null for variable "
										+ tncFV.getVariable().getName());
					}

					if (!tncFV.getValueAtCreation()
							&& (tncFV.getMasterVariable() == null || tncFV.getMasterVariable().getId() == null)) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Master Variable is invalid for variable " + tncFV.getVariable().getName());
					}
					if (tncFV.getType() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Variable Type cannot be null for variable " + tncFV.getVariable().getName());
					}
					if (tncFV.getUnit() == null || tncFV.getUnit().getId() == null) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Variable Unit is invalid for variable " + tncFV.getVariable().getName());
					}
					String variableName = tncFV.getVariable().getName().toLowerCase().trim();
					search.setName(variableName);
					search.setCompanyId(tnc.getCompanyId());
					search.setUserId(tnc.getModifiedBy());
					Long variableId = woFormulaVariableDao.getIdByNameOrSave(search);
					WoFormulaVariable variable = new WoFormulaVariable(variableId);
					tncFV.setVariable(variable);
					tncFV.setIsActive(true);
					tncFV.setCreatedOn(new Date());
					tncFV.setCreatedBy(tnc.getModifiedBy());
					tncFV.setId(null);
				}
				tnc.setFormulaVariables(woTncFVs);
			}
			if (savedTnc.getType() != null && tnc.getType() != null
					&& !savedTnc.getType().getId().equals(tnc.getType().getId())) {
				savedTnc.setType(tnc.getType());
			}
			List<WoTncFormulaVariable> woTncFvsSaved = savedTnc.getFormulaVariables();
			List<WoTncFormulaVariable> woTncFvNew = tnc.getFormulaVariables();

			savedTnc.setCode(tnc.getCode());
			savedTnc.setDescription(tnc.getDescription());
			savedTnc.setName(tnc.getName());
			savedTnc.setFormula(tnc.getFormula());
			savedTnc.setType(tnc.getType());
			savedTnc.setModifiedOn(new Date());
			savedTnc.setIsActive(true);
			savedTnc.setModifiedBy(tnc.getModifiedBy());
			boolean isUpdated = false;
			isUpdated = woTncDao.updateTermCondition(savedTnc);
			if (isUpdated) {
				Set<Long> woTncFvUpdatedIds = new LinkedHashSet<Long>();
				if (woTncFvNew != null) {
					for (WoTncFormulaVariable fv : woTncFvNew) {
						Long objId = woTncDao.saveOrUpdateWoTncFormulaVariable(fv);
						woTncFvUpdatedIds.add(objId);
					}
				}
				if (woTncFvsSaved != null) {
					for (WoTncFormulaVariable savedWoTncFv : woTncFvsSaved) {
						boolean isPresent = false;
						for (Long woTncFvUpdatedId : woTncFvUpdatedIds) {
							if (savedWoTncFv.getId().equals(woTncFvUpdatedId)) {
								isPresent = true;
								break;
							}
						}
						if (!isPresent) {
							savedWoTncFv.setCreatedBy(woTncDTO.getModifiedBy());
							woTncDao.forceDeactivateWoTncFormulaVariable(savedWoTncFv);
						}
					}
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), isUpdated,
					!isUpdated ? Responses.SUCCESS.toString() : "Already exists.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	private String getReplacedDescriptionRecursion(String description, String variableNameWithDelimeter) {

		int lastIndex = 0;
		String descriptionLowerCase = description.toLowerCase();
		String variableNameWithDelimeterLowerCase = variableNameWithDelimeter.toLowerCase();

		while (lastIndex != -1) {

			lastIndex = descriptionLowerCase.indexOf(variableNameWithDelimeterLowerCase, lastIndex);

			if (lastIndex != -1) {
				description = description.replace(
						description.substring(lastIndex, lastIndex + variableNameWithDelimeter.length()),
						variableNameWithDelimeterLowerCase);
				lastIndex++;
			}
		}
		return description;
	}

	@Override
	public CustomResponse removeWoTermsCondition(SearchDTO search) {

		try {
			if (search.getId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			WoTnc woTnc = woTncDao.fetchWoTncById(search.getId());
			List<WoTypeTncMapping> woTypeTncMaps = woTypeTncMapDao.fetchWoTypesTncsByTncId(woTnc.getId());
			if (woTypeTncMaps != null && woTypeTncMaps.size() > 0) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Workorder Type - TNC Mapping exists for this tnc, please remove that first.");
			}
			woTnc.setModifiedBy(search.getUserId());
			woTnc.setModifiedOn(new Date());
			woTnc.setIsActive(false);
			woTncDao.forceUpdateWoTnc(woTnc);
			return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getVariableTypes(SearchDTO search) {

		List<VariableTypes> result = new ArrayList<>();
		for (VariableTypes type : VariableTypes.values()) {
			result.add(type);
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
	}

	@Override
	public CustomResponse getWoTermsConditionById(SearchDTO search) {

		try {
			if (search.getId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			WoTnc woTnc = woTncDao.fetchWoTncById(search.getId());
			WoTncDTO result = setObject.woTncEntityToDto(woTnc);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getTncTypewiseTermsConditions(SearchDTO search) {

		try {
			List<WoTncType> tncTypes = woTncTypeDao.fetchActiveWoTncTypes(search);
			List<WoTnc> tncs = woTncDao.fetchActiveTermsConditionsBySearch(search);
			List<MinimalObjectDTO> result = new ArrayList<>();
			if (tncTypes != null) {
				for (WoTncType type : tncTypes) {
					List<WoTncDTO> typeWiseTncs = new ArrayList<>();
					if (tncs != null) {
						for (WoTnc tnc : tncs) {
							if (tnc.getType().getId().equals(type.getId())) {
								WoTncDTO woTncDTO = setObject.woTncEntityToDto(tnc);
								List<WoTncFormulaVariableDTO> tncFVs = new ArrayList<>();
								if (woTncDTO.getFormulaVariables() != null) {
									for (WoTncFormulaVariableDTO fv : woTncDTO.getFormulaVariables()) {
										if (fv.getIsActive()) {
											tncFVs.add(fv);
										}
									}
								}
								woTncDTO.setFormulaVariables(tncFVs);
								typeWiseTncs.add(woTncDTO);
							}
						}
					}
					if (typeWiseTncs.size() > 0) {
						MinimalObjectDTO obj = new MinimalObjectDTO(type.getId(), type.getName(), null, null,
								typeWiseTncs);
						result.add(obj);
					}
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
	public CustomResponse getWorkorderMasterVariables() {
		try {
			List<WorkorderMasterVariable> masterVariables = woTncDao.fetchWorkorderMasterVariables();
			List<WorkorderMasterVariableGetResponse> resultList = new ArrayList<>();
			if (masterVariables != null) {
				for (WorkorderMasterVariable mv : masterVariables) {
					resultList
							.add(new WorkorderMasterVariableGetResponse(mv.getId(), mv.getName(), mv.getDescription()));
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultList, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
