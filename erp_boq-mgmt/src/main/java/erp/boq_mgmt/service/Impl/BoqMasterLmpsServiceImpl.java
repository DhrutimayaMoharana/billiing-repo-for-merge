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

import erp.boq_mgmt.dao.BoqMasterLmpsDao;
import erp.boq_mgmt.dao.UserDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.EntityStateMapDTO;
import erp.boq_mgmt.dto.NextStateTransitDTO;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.StateTransitionDTO;
import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.dto.request.BoqMasterLmpsAddUpdateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsDeactivateRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsFetchRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.request.UndefinedMasterLmpsBoqsFetchRequest;
import erp.boq_mgmt.dto.response.BoqMasterLmpsFetchByIdResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsFetchFinalSuccessResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsFetchListResponse;
import erp.boq_mgmt.dto.response.BoqMasterLmpsStateTransitionFetchResponse;
import erp.boq_mgmt.dto.response.UndefinedMasterLmpsBoqItemResponse;
import erp.boq_mgmt.entity.BoqItem;
import erp.boq_mgmt.entity.BoqMasterLmps;
import erp.boq_mgmt.entity.BoqMasterLmpsStateTransition;
import erp.boq_mgmt.entity.EngineState;
import erp.boq_mgmt.entity.User;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.StateAction;
import erp.boq_mgmt.feignClient.service.WorkflowEngineService;
import erp.boq_mgmt.service.BoqMasterLmpsService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class BoqMasterLmpsServiceImpl implements BoqMasterLmpsService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BoqMasterLmpsDao boqMasterLmpsDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Override
	public CustomResponse addBoqMasterLmps(BoqMasterLmpsAddUpdateRequest requestDTO) {
		try {

			BoqMasterLmps boqMasterLmps = setObject.boqMasterLmpsAddUpdateRequestDtoToEntity(requestDTO);

			Long boqMasterLmpsId = boqMasterLmpsDao.saveBoqMasterLmps(boqMasterLmps);

			if (boqMasterLmpsId != null && boqMasterLmpsId > 0) {

				// save state transition
				BoqMasterLmpsStateTransition stateTransition = new BoqMasterLmpsStateTransition(null, boqMasterLmpsId,
						new EngineState(requestDTO.getStateId()), requestDTO.getStateTransitionRemark(), true,
						new Date(), requestDTO.getUserDetail().getId());
				boqMasterLmpsDao.saveBoqMasterLmpsStateTransitionMapping(stateTransition);

			}

			return new CustomResponse(
					((boqMasterLmpsId != null && boqMasterLmpsId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					boqMasterLmpsId,
					((boqMasterLmpsId != null && boqMasterLmpsId > 0) ? HttpStatus.OK.name() : " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateBoqMasterLmps(BoqMasterLmpsAddUpdateRequest requestDTO) {
		try {

			BoqMasterLmps boqMasterLmps = setObject.boqMasterLmpsAddUpdateRequestDtoToEntity(requestDTO);

			BoqMasterLmps dbObj = boqMasterLmpsDao.fetchBoqMasterLmpsById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = boqMasterLmpsDao.updateBoqMasterLmps(boqMasterLmps);

			if (!isUpdated)
				return new CustomResponse((HttpStatus.BAD_REQUEST.value()), isUpdated, "Already exist.");

			// save state transition
			if (isUpdated) {

				BoqMasterLmpsStateTransition stateTransition = new BoqMasterLmpsStateTransition(null, dbObj.getId(),
						new EngineState(requestDTO.getStateId()), requestDTO.getStateTransitionRemark(), true,
						new Date(), requestDTO.getUserDetail().getId());
				boqMasterLmpsDao.saveBoqMasterLmpsStateTransitionMapping(stateTransition);
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
	public CustomResponse getBoqMasterLmpsById(Long id) {
		try {

			BoqMasterLmps boqMasterLmps = boqMasterLmpsDao.fetchBoqMasterLmpsById(id);

			if (boqMasterLmps == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			BoqMasterLmpsFetchByIdResponse responseObj = setObject
					.boqMasterLmpsEntityToFetchByIdResponse(boqMasterLmps);

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), boqMasterLmps.getCompanyId());

			Boolean isInitial = false;
			Boolean isEditable = false;
			Boolean isFinal = false;
			Boolean isDeletable = false;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.BOQ_COST_DEFINITION.getEntityId())
						&& esm.getStateId().equals(boqMasterLmps.getState().getId())) {
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
	public CustomResponse getBoqMasterLmps(BoqMasterLmpsFetchRequest requestDTO) {

		try {

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.BOQ_MASTER_LMPS.getEntityId(), null, null, null,
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
			List<BoqMasterLmpsStateTransition> fetchBoqMasterLmpsStateTransitionList = boqMasterLmpsDao
					.fetchBoqMasterLmpsStateTransitionList(requestDTO, roleStateMap);
			Integer boqMasterLmpsStateTransitionsCount = boqMasterLmpsDao
					.fetchBoqMasterLmpsStateTransitionListCount(requestDTO, roleStateMap);

			List<BoqMasterLmps> boqMasterLmpsList = new ArrayList<>();

			if (fetchBoqMasterLmpsStateTransitionList != null && !fetchBoqMasterLmpsStateTransitionList.isEmpty()) {
				fetchBoqMasterLmpsStateTransitionList.forEach(obj -> boqMasterLmpsList.add(obj.getBoqMasterLmps()));
			}

			List<BoqMasterLmpsFetchListResponse> boqMasterLmpsFetchListResponseDtoList = new ArrayList<>();
			if (boqMasterLmpsList != null && !boqMasterLmpsList.isEmpty()) {

				// set entity state data
				List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
						EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), requestDTO.getUserDetail().getCompanyId());

				for (BoqMasterLmps boqMasterLmpsObj : boqMasterLmpsList) {
					BoqMasterLmpsFetchListResponse responseObj = setObject
							.boqMasterLmpsEntityToFetchListResponseDto(boqMasterLmpsObj);

					Boolean isInitial = false;
					Boolean isEditable = false;
					Boolean isFinal = false;
					Boolean isDeletable = false;

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.BOQ_COST_DEFINITION.getEntityId())
								&& esm.getStateId().equals(boqMasterLmpsObj.getState().getId())) {
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

					boqMasterLmpsFetchListResponseDtoList.add(responseObj);
				}

			}

			PaginationDTO responseObj = new PaginationDTO(boqMasterLmpsStateTransitionsCount,
					boqMasterLmpsFetchListResponseDtoList);
			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse deactivateBoqMasterLmps(BoqMasterLmpsDeactivateRequest requestDTO) {
		try {
			BoqMasterLmps dbObj = boqMasterLmpsDao.fetchBoqMasterLmpsById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = boqMasterLmpsDao.deactivateBoqMasterLmps(dbObj);
			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(BoqMasterLmpsNextPossibleStatesFetchRequest requestObj) {

		try {
			if (requestObj.getBoqMasterLmpsId() == null) {
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Provide boqMasterLmpsId.");
			}
			BoqMasterLmps boqMasterLmps = boqMasterLmpsDao.fetchBoqMasterLmpsById(requestObj.getBoqMasterLmpsId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.BOQ_COST_DEFINITION.getEntityId(), null, null, null,
					requestObj.getUser().getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User user = userDao.fetchUserById(boqMasterLmps.getUpdatedBy().longValue());

//			set next states
			if (transitions != null && user != null && user.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(boqMasterLmps.getState().getId())
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
	public CustomResponse getBoqMasterLmpsStateTransitionByBoqMasterLmpsId(Long boqMasterLmpsId) {

		try {
			List<BoqMasterLmpsStateTransition> rfiChecklistItemTransitionList = boqMasterLmpsDao
					.fetchBoqMasterLmpsStateTransitionByBoqMasterLmpsId(boqMasterLmpsId);
			List<BoqMasterLmpsStateTransitionFetchResponse> results = new ArrayList<>();
			if (rfiChecklistItemTransitionList != null) {
				rfiChecklistItemTransitionList
						.forEach(obj -> results.add(setObject.boqMasterLmpsStateTransitionEntityToDto(obj)));
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
	public CustomResponse getBoqMasterLmpsFinalSuccessList(BoqMasterLmpsFinalSuccessFetchRequest requestDTO) {
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

			List<BoqMasterLmps> boqMasterLmpsList = boqMasterLmpsDao.fetchBoqMasterLmpsByStateIds(finalSuccessStateIds,
					requestDTO);

			Long boqMasterLmpsListCount = 0L;
			if (requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0 && requestDTO.getPageSize() != null) {
				boqMasterLmpsListCount = boqMasterLmpsDao.fetchBoqMasterLmpsByStateIdsCount(finalSuccessStateIds,
						requestDTO);
			}

			List<BoqMasterLmpsFetchFinalSuccessResponse> boqMasterLmpsResponse = new ArrayList<>();
			if (boqMasterLmpsList != null && !boqMasterLmpsList.isEmpty()) {
				boqMasterLmpsList.forEach(obj -> boqMasterLmpsResponse
						.add(setObject.boqMasterLmpsEntityToFetchFinalSuccessListResponseDto(obj)));
			}

			PaginationDTO responseObj = new PaginationDTO(
					boqMasterLmpsListCount != 0L ? boqMasterLmpsListCount.intValue() : boqMasterLmpsResponse.size(),
					boqMasterLmpsResponse);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getUndefinedMasterLmpsBoqs(UndefinedMasterLmpsBoqsFetchRequest requestDTO) {
		try {
			List<BoqMasterLmps> boqMasterLmpsList = boqMasterLmpsDao.fetchBoqMasterLmpsList(requestDTO);

			Set<Long> distinctDefinedMasterLmpsBoqIds = new HashSet<>();
			if (boqMasterLmpsList != null && !boqMasterLmpsList.isEmpty()) {
				boqMasterLmpsList.forEach(obj -> distinctDefinedMasterLmpsBoqIds.add(obj.getBoqId()));
			}

			List<BoqItem> boqItem = boqMasterLmpsDao.fetchUndefinedMasterLmpsBoqs(requestDTO,
					distinctDefinedMasterLmpsBoqIds);

			List<UndefinedMasterLmpsBoqItemResponse> responseObj = new ArrayList<>();
			if (boqItem != null)
				boqItem.forEach(
						obj -> responseObj.add(setObject.boqItemEntityToUndefinedMasterLmpsBoqItemResponse(obj)));

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse boqMasterLmpsSaveAsDraft(Long boqId, UserDetail userDetail) {
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

			BoqMasterLmpsAddUpdateRequest boqMasterLmps = new BoqMasterLmpsAddUpdateRequest(null, boqId, 0.0, false,
					null, false, null, false, null, 0.0, null, draftStateId, null, userDetail);

			CustomResponse response = addBoqMasterLmps(boqMasterLmps);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getBoqMasterLmpsIdByBoqId(Long boqId) {
		try {

			BoqMasterLmps boqMasterLmps = boqMasterLmpsDao.fetchBoqMasterLmpsByBoqId(boqId);

			if (boqMasterLmps == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			return new CustomResponse(HttpStatus.OK.value(), boqMasterLmps.getId(), HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

}
