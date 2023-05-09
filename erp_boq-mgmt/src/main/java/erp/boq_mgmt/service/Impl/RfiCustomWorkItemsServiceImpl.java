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

import erp.boq_mgmt.dao.RfiCustomWorkItemsDao;
import erp.boq_mgmt.dao.UserDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.EntityStateMapDTO;
import erp.boq_mgmt.dto.NextStateTransitDTO;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.StateTransitionDTO;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.RfiCustomWorkItemNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemFetchByIdResponse;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemFetchFinalSuccessListResponse;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemFetchListResponse;
import erp.boq_mgmt.dto.response.RfiCustomWorkItemStateTransitionFetchResponse;
import erp.boq_mgmt.entity.EngineState;
import erp.boq_mgmt.entity.RfiCustomWorkItemStateTransition;
import erp.boq_mgmt.entity.RfiCustomWorkItems;
import erp.boq_mgmt.entity.RfiCustomWorkItemsV2;
import erp.boq_mgmt.entity.User;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.StateAction;
import erp.boq_mgmt.feignClient.service.WorkflowEngineService;
import erp.boq_mgmt.service.RfiCustomWorkItemsService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class RfiCustomWorkItemsServiceImpl implements RfiCustomWorkItemsService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private RfiCustomWorkItemsDao rfiCustomWorkItemDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Override
	public CustomResponse addRfiCustomWorkItem(RfiCustomWorkItemAddUpdateRequest requestDTO) {
		try {

			RfiCustomWorkItems rfiCustomWorktItem = setObject.rfiCustomWorkItemsAddUpdateRequestDtoToEntity(requestDTO);

			Long rfiCustomWorkItemId = rfiCustomWorkItemDao.saveRfiCustomWorkItem(rfiCustomWorktItem);

			if (rfiCustomWorkItemId != null && rfiCustomWorkItemId > 0) {

				// save state transition
				RfiCustomWorkItemStateTransition stateTransition = new RfiCustomWorkItemStateTransition(null,
						rfiCustomWorkItemId, new EngineState(requestDTO.getStateId()), requestDTO.getRemark(), true,
						new Date(), requestDTO.getUserDetail().getId());
				rfiCustomWorkItemDao.saveRfiCustomWorkItemStateTransitionMapping(stateTransition);

			}

			return new CustomResponse(
					((rfiCustomWorkItemId != null && rfiCustomWorkItemId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					rfiCustomWorkItemId,
					((rfiCustomWorkItemId != null && rfiCustomWorkItemId > 0) ? HttpStatus.OK.name()
							: " Already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateRfiCustomWorkItem(RfiCustomWorkItemAddUpdateRequest requestDTO) {
		try {

			RfiCustomWorkItems rfiCustomWorkItem = setObject.rfiCustomWorkItemsAddUpdateRequestDtoToEntity(requestDTO);

			RfiCustomWorkItems dbObj = rfiCustomWorkItemDao.fetchRfiCustomWorkItemById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = rfiCustomWorkItemDao.updateRfiCustomWorkItem(rfiCustomWorkItem);

			if (!isUpdated)
				return new CustomResponse((HttpStatus.BAD_REQUEST.value()), isUpdated, "Already exist.");

			// save state transition
			if (isUpdated) {

				RfiCustomWorkItemStateTransition stateTransition = new RfiCustomWorkItemStateTransition(null,
						dbObj.getId(), new EngineState(requestDTO.getStateId()), requestDTO.getRemark(), true,
						new Date(), requestDTO.getUserDetail().getId());
				rfiCustomWorkItemDao.saveRfiCustomWorkItemStateTransitionMapping(stateTransition);
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
	public CustomResponse getRfiCustomWorkItemById(Long id) {
		try {

			RfiCustomWorkItems rfiCustomWorkItem = rfiCustomWorkItemDao.fetchRfiCustomWorkItemById(id);

			if (rfiCustomWorkItem == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			RfiCustomWorkItemFetchByIdResponse responseObj = setObject
					.rfiCustomWorkItemEntityToFetchByIdResponse(rfiCustomWorkItem);

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId(), rfiCustomWorkItem.getCompanyId());

			Boolean isInitial = false;
			Boolean isEditable = false;
			Boolean isFinal = false;
			Boolean isDeletable = false;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId())
						&& esm.getStateId().equals(rfiCustomWorkItem.getState().getId())) {
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
	public CustomResponse getRfiCustomWorkItem(RfiCustomWorkItemFetchRequest requestDTO) {

		try {

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId(), null, null, null,
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

			List<RfiCustomWorkItemStateTransition> fetchRfiCustomWorkItemStateTransitionList = rfiCustomWorkItemDao
					.fetchRfiCustomWorkItemStateTransitionList(requestDTO, roleStateMap);
			Integer rfiCustomWorkItemStateTransitionsCount = rfiCustomWorkItemDao
					.fetchRfiCustomWorkItemStateTransitionListCount(requestDTO, roleStateMap);

			List<RfiCustomWorkItems> rfiCustomWorkItemsList = new ArrayList<>();

			if (fetchRfiCustomWorkItemStateTransitionList != null
					&& !fetchRfiCustomWorkItemStateTransitionList.isEmpty()) {
				fetchRfiCustomWorkItemStateTransitionList
						.forEach(obj -> rfiCustomWorkItemsList.add(obj.getRfiCustomWorkItem()));
			}

			List<RfiCustomWorkItemFetchListResponse> rfiCustomWorkItemFetchListResponseDtoList = new ArrayList<>();
			if (rfiCustomWorkItemsList != null && !rfiCustomWorkItemsList.isEmpty()) {

				// set entity state data
				List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
						EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId(), requestDTO.getUserDetail().getCompanyId());

				for (RfiCustomWorkItems rfiCustomWorkItemObj : rfiCustomWorkItemsList) {
					RfiCustomWorkItemFetchListResponse responseObj = setObject
							.rfiCustomWorkItemEntityToFetchListResponseDto(rfiCustomWorkItemObj);

					Boolean isInitial = false;
					Boolean isEditable = false;
					Boolean isFinal = false;
					Boolean isDeletable = false;

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId())
								&& esm.getStateId().equals(rfiCustomWorkItemObj.getState().getId())) {
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

					rfiCustomWorkItemFetchListResponseDtoList.add(responseObj);
				}

			}

			PaginationDTO responseObj = new PaginationDTO(rfiCustomWorkItemStateTransitionsCount,
					rfiCustomWorkItemFetchListResponseDtoList);
			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse deactivateRfiCustomWorkItem(RfiCustomWorkItemDeactivateRequest requestDTO) {
		try {
			RfiCustomWorkItems dbObj = rfiCustomWorkItemDao.fetchRfiCustomWorkItemById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = rfiCustomWorkItemDao.deactivateRfiCustomWorkItem(dbObj);
			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(RfiCustomWorkItemNextPossibleStatesFetchRequest requestObj) {

		try {
			if (requestObj.getRfiCustomWorkItemId() == null) {
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Provide rfiCustomWorkItemId.");
			}
			RfiCustomWorkItems rfiCustomWorkItem = rfiCustomWorkItemDao
					.fetchRfiCustomWorkItemById(requestObj.getRfiCustomWorkItemId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId(), null, null, null,
					requestObj.getUser().getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User user = userDao.fetchUserById(rfiCustomWorkItem.getUpdatedBy().longValue());

//			set next states
			if (transitions != null && user != null && user.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(rfiCustomWorkItem.getState().getId())
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
	public CustomResponse getRfiCustomWorkItemStateTransitionByRfiCustomWorkItemId(Long rfiCustomWorkItemId) {

		try {
			List<RfiCustomWorkItemStateTransition> rfiChecklistItemTransitionList = rfiCustomWorkItemDao
					.fetchRfiCustomWorkItemStateTransitionByRfiCustomWorkItemId(rfiCustomWorkItemId);
			List<RfiCustomWorkItemStateTransitionFetchResponse> results = new ArrayList<>();
			if (rfiChecklistItemTransitionList != null) {
				rfiChecklistItemTransitionList
						.forEach(obj -> results.add(setObject.rfiCustomWorkItemStateTransitionEntityToDto(obj)));
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
	public CustomResponse getRfiCustomWorkItemFinalSuccessList(RfiCustomWorkItemFinalSuccessFetchRequest requestDTO) {
		try {

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId(), requestDTO.getUserDetail().getCompanyId());

			Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.RFI_CUSTOM_WORK_ITEM.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
					finalSuccessStateIds.add(esm.getStateId());
				}

			}

			List<RfiCustomWorkItemsV2> rfiCustomWorkItemsList = rfiCustomWorkItemDao
					.fetchRfiCustomWorkItemByStateIds(finalSuccessStateIds, requestDTO);

			Long rfiCustomWorkItemsListCount = 0L;
			if (requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0 && requestDTO.getPageSize() != null) {
				rfiCustomWorkItemsListCount = rfiCustomWorkItemDao
						.fetchRfiCustomWorkItemByStateIdsCount(finalSuccessStateIds, requestDTO);
			}

			List<RfiCustomWorkItemFetchFinalSuccessListResponse> customWorkItemResponse = new ArrayList<>();
			if (rfiCustomWorkItemsList != null && !rfiCustomWorkItemsList.isEmpty()) {
				rfiCustomWorkItemsList.forEach(obj -> customWorkItemResponse
						.add(setObject.rfiCustomWorkItemEntityToFetchFinalSuccessListResponseDto(obj)));
			}

			PaginationDTO responseObj = new PaginationDTO(
					rfiCustomWorkItemsListCount != 0L ? rfiCustomWorkItemsListCount.intValue()
							: customWorkItemResponse.size(),
					customWorkItemResponse);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

}
