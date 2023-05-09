package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.BoqCostDefinitionDao;
import erp.boq_mgmt.dao.SiteVariableDao;
import erp.boq_mgmt.dao.UserDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.EntityStateMapDTO;
import erp.boq_mgmt.dto.NextStateTransitDTO;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.StateTransitionDTO;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.dto.request.BoqCostDefinitionAddRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionDeactivateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionLabourAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMachineryAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionMaterialAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.request.BoqCostDefinitionUpdateRequest;
import erp.boq_mgmt.dto.request.UndefinedCostDefinitionBoqsFetchRequest;
import erp.boq_mgmt.dto.response.BoqCostDefinitionFetchByIdResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionFetchFinalSuccessResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionFetchListResponse;
import erp.boq_mgmt.dto.response.BoqCostDefinitionStateTransitionFetchResponse;
import erp.boq_mgmt.dto.response.UndefinedCostDefinitionBoqItemResponse;
import erp.boq_mgmt.entity.BoqCostDefinition;
import erp.boq_mgmt.entity.BoqCostDefinitionStateTransition;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.EngineState;
import erp.boq_mgmt.entity.SiteVariableValue;
import erp.boq_mgmt.entity.User;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.StateAction;
import erp.boq_mgmt.feignClient.service.WorkflowEngineService;
import erp.boq_mgmt.service.BoqCostDefinitionLabourService;
import erp.boq_mgmt.service.BoqCostDefinitionMachineryService;
import erp.boq_mgmt.service.BoqCostDefinitionMaterialService;
import erp.boq_mgmt.service.BoqCostDefinitionService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class BoqCostDefinitionServiceImpl implements BoqCostDefinitionService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BoqCostDefinitionDao boqCostDefinitionDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Autowired
	private SiteVariableDao siteVariableDao;

	@Autowired
	private BoqCostDefinitionLabourService boqCostDefinitionLabourService;

	@Autowired
	private BoqCostDefinitionMaterialService boqCostDefinitionMaterialService;

	@Autowired
	private BoqCostDefinitionMachineryService boqCostDefinitionMachineryService;

	@Override
	public CustomResponse addBoqCostDefinition(BoqCostDefinitionAddRequest requestDTO) {
		try {

			BoqCostDefinition boqCostDefinition = setObject.boqCostDefinitionsAddRequestDtoToEntity(requestDTO);

			Long boqCostDefinitionId = boqCostDefinitionDao.saveBoqCostDefinition(boqCostDefinition);

			if (boqCostDefinitionId != null && boqCostDefinitionId > 0) {

				// save state transition
				BoqCostDefinitionStateTransition stateTransition = new BoqCostDefinitionStateTransition(null,
						boqCostDefinitionId, new EngineState(requestDTO.getStateId()),
						requestDTO.getStateTransitionRemark(), true, new Date(), requestDTO.getUserDetail().getId());
				boqCostDefinitionDao.saveBoqCostDefinitionStateTransitionMapping(stateTransition);

				// save labour list
				if (requestDTO.getLabourList() != null && !requestDTO.getLabourList().isEmpty()) {

					for (BoqCostDefinitionLabourAddUpdateRequest labourReqObj : requestDTO.getLabourList()) {

						BoqCostDefinitionLabourAddUpdateRequest labourObj = new BoqCostDefinitionLabourAddUpdateRequest(
								null, requestDTO.getBoqId(), boqCostDefinitionId, labourReqObj.getLabourTypeId(),
								labourReqObj.getUnitId(), labourReqObj.getQuantity(), labourReqObj.getRate(),
								labourReqObj.getSiteId(), requestDTO.getUserDetail());
						boqCostDefinitionLabourService.addBoqCostDefinitionLabour(labourObj);

					}

				}

				// save material list
				if (requestDTO.getMaterialList() != null && !requestDTO.getMaterialList().isEmpty()) {

					for (BoqCostDefinitionMaterialAddUpdateRequest materialReqObj : requestDTO.getMaterialList()) {

						BoqCostDefinitionMaterialAddUpdateRequest materialObj = new BoqCostDefinitionMaterialAddUpdateRequest(
								null, requestDTO.getBoqId(), boqCostDefinitionId, materialReqObj.getMaterialId(),
								materialReqObj.getUnitId(), materialReqObj.getQuantity(), materialReqObj.getRate(),
								materialReqObj.getSiteId(), requestDTO.getUserDetail());
						boqCostDefinitionMaterialService.addBoqCostDefinitionMaterial(materialObj);

					}

				}

				// save machinery list
				if (requestDTO.getMachineryList() != null && !requestDTO.getMachineryList().isEmpty()) {

					for (BoqCostDefinitionMachineryAddUpdateRequest machineryReqObj : requestDTO.getMachineryList()) {

						BoqCostDefinitionMachineryAddUpdateRequest machineryObj = new BoqCostDefinitionMachineryAddUpdateRequest(
								null, requestDTO.getBoqId(), boqCostDefinitionId,
								machineryReqObj.getMachineryCategoryId(), machineryReqObj.getLeadSiteVariableId(),
								machineryReqObj.getMachineryTrip(), machineryReqObj.getUnitId(),
								machineryReqObj.getRate(), machineryReqObj.getAfterUnitId(),
								machineryReqObj.getAfterRate(), machineryReqObj.getRangeType(),
								machineryReqObj.getRangeUnitId(), machineryReqObj.getRangeQuantity(),
								machineryReqObj.getSiteId(), requestDTO.getUserDetail());
						boqCostDefinitionMachineryService.addBoqCostDefinitionMachinery(machineryObj);

					}

				}

			}

			return new CustomResponse(
					((boqCostDefinitionId != null && boqCostDefinitionId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					boqCostDefinitionId,
					((boqCostDefinitionId != null && boqCostDefinitionId > 0) ? HttpStatus.OK.name()
							: " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateBoqCostDefinition(BoqCostDefinitionUpdateRequest requestDTO) {
		try {

			BoqCostDefinition boqCostDefinition = setObject.boqCostDefinitionsUpdateRequestDtoToEntity(requestDTO);

			BoqCostDefinition dbObj = boqCostDefinitionDao.fetchBoqCostDefinitionById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = boqCostDefinitionDao.updateBoqCostDefinition(boqCostDefinition);

			if (!isUpdated)
				return new CustomResponse((HttpStatus.BAD_REQUEST.value()), isUpdated, "Already exist.");

			// save state transition
			if (isUpdated) {

				BoqCostDefinitionStateTransition stateTransition = new BoqCostDefinitionStateTransition(null,
						dbObj.getId(), new EngineState(requestDTO.getStateId()), requestDTO.getStateTransitionRemark(),
						true, new Date(), requestDTO.getUserDetail().getId());
				boqCostDefinitionDao.saveBoqCostDefinitionStateTransitionMapping(stateTransition);
			}

			return new CustomResponse(HttpStatus.OK.value(), isUpdated, HttpStatus.OK.name());
		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getBoqCostDefinitionById(Long id) {
		try {

			BoqCostDefinition boqCostDefinition = boqCostDefinitionDao.fetchBoqCostDefinitionById(id);

			if (boqCostDefinition == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Set<Integer> distinctSiteVariableIds = new HashSet<Integer>();

			distinctSiteVariableIds.add(boqCostDefinition.getFoamworkSiteVariableId());
			distinctSiteVariableIds.add(boqCostDefinition.getOverheadSiteVariableId());
			distinctSiteVariableIds.add(boqCostDefinition.getContractorProfitSiteVariableId());

			List<SiteVariableValue> siteVariableValueList = siteVariableDao
					.fetchSiteVariableValuesBySiteVariableIdsAndSiteId(distinctSiteVariableIds,
							boqCostDefinition.getSiteId());

			BoqCostDefinitionFetchByIdResponse responseObj = setObject
					.boqCostDefinitionEntityToFetchByIdResponse(boqCostDefinition);

			if (siteVariableValueList != null && !siteVariableValueList.isEmpty()) {
				for (SiteVariableValue siteVariableValue : siteVariableValueList) {
					if (responseObj.getFoamworkSiteVariableValue() == null
							&& responseObj.getFoamworkSiteVariableId() != null
							&& responseObj.getFoamworkSiteVariableId().equals(siteVariableValue.getVariableId())) {
						responseObj.setFoamworkSiteVariableValue(siteVariableValue.getValue());
					}
					if (responseObj.getOverheadSiteVariableValue() == null
							&& responseObj.getOverheadSiteVariableId() != null
							&& responseObj.getOverheadSiteVariableId().equals(siteVariableValue.getVariableId())) {
						responseObj.setOverheadSiteVariableValue(siteVariableValue.getValue());
					}
					if (responseObj.getContractorProfitSiteVariableValue() == null
							&& responseObj.getContractorProfitSiteVariableId() != null && responseObj
									.getContractorProfitSiteVariableId().equals(siteVariableValue.getVariableId())) {
						responseObj.setContractorProfitSiteVariableValue(siteVariableValue.getValue());
					}
				}

			}

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), boqCostDefinition.getCompanyId());

			Boolean isInitial = false;
			Boolean isEditable = false;
			Boolean isFinal = false;
			Boolean isDeletable = false;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.BOQ_COST_DEFINITION.getEntityId())
						&& esm.getStateId().equals(boqCostDefinition.getState().getId())) {
					if (!isInitial && esm.getIsInitial() != null) {
						isInitial = esm.getIsInitial();
					}
					if (!isEditable && esm.getIsEditable() != null) {
						isEditable = esm.getIsEditable();
					}
					if (!isDeletable && esm.getIsDeletable() != null) {
						isDeletable = esm.getIsDeletable();
					}
					if (!isFinal && esm.getIsFinal() != null) {
						isFinal = esm.getIsFinal();
					}

				}

			}

			responseObj.setIsEditable(isEditable);
			responseObj.setIsDeletable(isDeletable);
			responseObj.setIsFinalState(isFinal);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getBoqCostDefinition(BoqCostDefinitionFetchRequest requestDTO) {

		try {

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), requestDTO.getSiteId().longValue(), null, null,
					requestDTO.getUserDetail().getCompanyId());
			if (transitions == null) {
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "State transition not found.");
			}

			Map<Integer, Set<Integer>> roleStateMap = new HashMap<Integer, Set<Integer>>();

//			set next states
			for (StateTransitionDTO st : transitions) {
				if (st.getNextRoleId().equals(requestDTO.getUserDetail().getRoleId())) {

					if (roleStateMap.containsKey(st.getRoleId())) {
						Set<Integer> stateIds = roleStateMap.get(st.getRoleId());
						stateIds.add(st.getStateId());

						roleStateMap.replace(st.getRoleId(), roleStateMap.get(st.getRoleId()), stateIds);

					} else {
						Set<Integer> stateIds = new HashSet<Integer>();
						stateIds.add(st.getStateId());
						roleStateMap.put(st.getRoleId(), stateIds);
					}

				}
			}
			List<BoqCostDefinitionStateTransition> fetchBoqCostDefinitionStateTransitionList = boqCostDefinitionDao
					.fetchBoqCostDefinitionStateTransitionList(requestDTO, roleStateMap);
			Integer boqCostDefinitionStateTransitionsCount = boqCostDefinitionDao
					.fetchBoqCostDefinitionStateTransitionListCount(requestDTO, roleStateMap);

			List<BoqCostDefinition> boqCostDefinitionsList = new ArrayList<>();

			if (fetchBoqCostDefinitionStateTransitionList != null
					&& !fetchBoqCostDefinitionStateTransitionList.isEmpty()) {
				fetchBoqCostDefinitionStateTransitionList
						.forEach(obj -> boqCostDefinitionsList.add(obj.getBoqCostDefinition()));
			}

			List<BoqCostDefinitionFetchListResponse> boqCostDefinitionFetchListResponseDtoList = new ArrayList<>();
			if (boqCostDefinitionsList != null && !boqCostDefinitionsList.isEmpty()) {

				// set entity state data
				List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
						EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), requestDTO.getUserDetail().getCompanyId());

				for (BoqCostDefinition boqCostDefinitionObj : boqCostDefinitionsList) {
					BoqCostDefinitionFetchListResponse responseObj = setObject
							.boqCostDefinitionEntityToFetchListResponseDto(boqCostDefinitionObj);

					Boolean isInitial = false;
					Boolean isEditable = false;
					Boolean isFinal = false;
					Boolean isDeletable = false;

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.BOQ_COST_DEFINITION.getEntityId())
								&& esm.getStateId().equals(boqCostDefinitionObj.getState().getId())) {
							if (!isInitial && esm.getIsInitial() != null) {
								isInitial = esm.getIsInitial();
							}
							if (!isEditable && esm.getIsEditable() != null) {
								isEditable = esm.getIsEditable();
							}
							if (!isDeletable && esm.getIsDeletable() != null) {
								isDeletable = esm.getIsDeletable();
							}
							if (!isFinal && esm.getIsFinal() != null) {
								isFinal = esm.getIsFinal();
							}

						}

					}

					responseObj.setIsEditable(isEditable);
					responseObj.setIsDeletable(isDeletable);
					responseObj.setIsFinalState(isFinal);

					boqCostDefinitionFetchListResponseDtoList.add(responseObj);
				}

			}

			PaginationDTO responseObj = new PaginationDTO(boqCostDefinitionStateTransitionsCount,
					boqCostDefinitionFetchListResponseDtoList);
			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse deactivateBoqCostDefinition(BoqCostDefinitionDeactivateRequest requestDTO) {
		try {
			BoqCostDefinition dbObj = boqCostDefinitionDao.fetchBoqCostDefinitionById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = boqCostDefinitionDao.deactivateBoqCostDefinition(dbObj);
			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(BoqCostDefinitionNextPossibleStatesFetchRequest requestObj) {

		try {
			if (requestObj.getBoqCostDefinitionId() == null) {
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Provide boqCostDefinitionId.");
			}
			BoqCostDefinition boqCostDefinition = boqCostDefinitionDao
					.fetchBoqCostDefinitionById(requestObj.getBoqCostDefinitionId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), null, null, null,
					requestObj.getUser().getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User user = userDao.fetchUserById(boqCostDefinition.getUpdatedBy().longValue());

//			set next states
			if (transitions != null && user != null && user.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(boqCostDefinition.getState().getId())
							&& st.getRoleId().equals(user.getRole().getId())
							&& st.getNextRoleId().equals(requestObj.getUser().getRoleId())) {
						NextStateTransitDTO nst = new NextStateTransitDTO();
						nst.setNextStateId(st.getNextStateId());
						nst.setNextRoleId(st.getNextRoleId());
						nst.setIsButtonRequired(true);
						nst.setNextRoleName(st.getNextRole().getName());
						nst.setNextStateName(st.getNextState().getName());
						nst.setButtonText(st.getNextState().getButtonText());
						nst.setIsButtonRequired(st.getNextState().getButtonText() != null ? true : false);
						nst.setNextStateAlias(st.getNextState().getAlias());
						nst.setRgbColorCode(st.getNextState().getRgbColorCode());
						nextStates.add(nst);
					}
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), nextStates, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBoqCostDefinitionStateTransitionByBoqCostDefinitionId(Long boqCostDefinitionId) {

		try {
			List<BoqCostDefinitionStateTransition> rfiChecklistItemTransitionList = boqCostDefinitionDao
					.fetchBoqCostDefinitionStateTransitionByBoqCostDefinitionId(boqCostDefinitionId);
			List<BoqCostDefinitionStateTransitionFetchResponse> results = new ArrayList<>();
			if (rfiChecklistItemTransitionList != null) {
				rfiChecklistItemTransitionList
						.forEach(obj -> results.add(setObject.boqCostDefinitionStateTransitionEntityToDto(obj)));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), results, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBoqCostDefinitionFinalSuccessList(BoqCostDefinitionFinalSuccessFetchRequest requestDTO) {
		try {

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), requestDTO.getUserDetail().getCompanyId());

			Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.BOQ_COST_DEFINITION.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
					finalSuccessStateIds.add(esm.getStateId());
				}

			}

			List<BoqCostDefinition> boqCostDefinitionsList = boqCostDefinitionDao
					.fetchBoqCostDefinitionByStateIds(finalSuccessStateIds, requestDTO);

			Long boqCostDefinitionsListCount = 0L;
			if (requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0 && requestDTO.getPageSize() != null) {
				boqCostDefinitionsListCount = boqCostDefinitionDao
						.fetchBoqCostDefinitionByStateIdsCount(finalSuccessStateIds, requestDTO);
			}

			List<BoqCostDefinitionFetchFinalSuccessResponse> boqCostDefinitionResponse = new ArrayList<>();
			if (boqCostDefinitionsList != null && !boqCostDefinitionsList.isEmpty()) {
				boqCostDefinitionsList.forEach(obj -> boqCostDefinitionResponse
						.add(setObject.boqCostDefinitionEntityToFetchFinalSuccessListResponseDto(obj)));
			}

			PaginationDTO responseObj = new PaginationDTO(
					boqCostDefinitionsListCount != 0L ? boqCostDefinitionsListCount.intValue()
							: boqCostDefinitionResponse.size(),
					boqCostDefinitionResponse);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getUndefinedCostDefinitionBoqs(UndefinedCostDefinitionBoqsFetchRequest requestDTO) {
		try {
			List<BoqCostDefinition> boqCostDefinitionList = boqCostDefinitionDao.fetchBoqCostDefinitionList(requestDTO);

			Set<Long> distinctDefinedCostDefinitionBoqIds = new HashSet<>();
			if (boqCostDefinitionList != null && !boqCostDefinitionList.isEmpty()) {
				boqCostDefinitionList.forEach(obj -> distinctDefinedCostDefinitionBoqIds.add(obj.getBoqId()));
			}

			List<BoqItem> boqItem = boqCostDefinitionDao.fetchUndefinedCostDefinitionBoqs(requestDTO,
					distinctDefinedCostDefinitionBoqIds);

			List<UndefinedCostDefinitionBoqItemResponse> responseObj = new ArrayList<>();
			if (boqItem != null)
				boqItem.forEach(
						obj -> responseObj.add(setObject.boqItemEntityToUndefinedCostDefinitionBoqItemResponse(obj)));

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse boqCostDefinitionSaveAsDraft(Long boqId, Integer siteId, UserDetail userDetail) {
		try {
			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), userDetail.getCompanyId());

			Integer draftStateId = null;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.BOQ_COST_DEFINITION.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Draft.getValue())) {
					draftStateId = esm.getStateId();
				}

			}

			if (draftStateId == null) {

				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null,
						"Draft state not found for boq cost definition entity.");
			}

			BoqCostDefinitionAddRequest boqCostDefinition = new BoqCostDefinitionAddRequest(null, boqId, 0.0, false,
					null, false, null, false, null, 0.0, null, draftStateId, null, siteId, userDetail);

			CustomResponse response = addBoqCostDefinition(boqCostDefinition);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

}
