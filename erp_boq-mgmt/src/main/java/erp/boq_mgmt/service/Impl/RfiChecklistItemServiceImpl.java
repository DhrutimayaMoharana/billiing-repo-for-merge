package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.RfiChecklistItemBoqDao;
import erp.boq_mgmt.dao.RfiChecklistItemDao;
import erp.boq_mgmt.dao.UserDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.EntityStateMapDTO;
import erp.boq_mgmt.dto.NextStateTransitDTO;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.StateTransitionDTO;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsFetchRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.response.RfiChecklistItemBoqsFetchResponse;
import erp.boq_mgmt.dto.response.RfiChecklistItemFetchFinalSuccessListResponse;
import erp.boq_mgmt.dto.response.RfiChecklistItemStateTransitionFetchResponse;
import erp.boq_mgmt.entity.EngineState;
import erp.boq_mgmt.entity.RfiChecklistItemBoqs;
import erp.boq_mgmt.entity.RfiChecklistItemBoqsV2;
import erp.boq_mgmt.entity.RfiChecklistItemStateTransition;
import erp.boq_mgmt.entity.RfiChecklistItems;
import erp.boq_mgmt.entity.User;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.StateAction;
import erp.boq_mgmt.feignClient.service.WorkflowEngineService;
import erp.boq_mgmt.service.RfiChecklistItemBoqService;
import erp.boq_mgmt.service.RfiChecklistItemService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class RfiChecklistItemServiceImpl implements RfiChecklistItemService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private RfiChecklistItemDao rfiChecklistItemDao;

	@Autowired
	private RfiChecklistItemBoqDao rfiChecklistItemBoqDao;

	@Autowired
	private RfiChecklistItemBoqService rfiChecklistItemBoqService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Override
	public CustomResponse addRfiChecklistItem(RfiChecklistItemBoqsAddUpdateRequest requestDTO) {
		try {

			RfiChecklistItems rfiChecklistItem = setObject.rfiChecklistItemAddUpdateRequestDtoToEntity(requestDTO);

			Integer rfiChecklistItemId = rfiChecklistItemDao.saveRfiChecklistItem(rfiChecklistItem);

			if (rfiChecklistItemId != null && rfiChecklistItemId > 0) {

				requestDTO.setId(rfiChecklistItemId);

				rfiChecklistItemBoqService.addUpdateRfiChecklistItemBoq(requestDTO);

				// save state transition
				RfiChecklistItemStateTransition stateTransition = new RfiChecklistItemStateTransition(null,
						rfiChecklistItemId, new EngineState(requestDTO.getStateId()), requestDTO.getRemark(), true,
						new Date(), requestDTO.getUserDetail().getId());
				rfiChecklistItemDao.saveRfiChecklistItemStateTransitionMapping(stateTransition);

			}

			return new CustomResponse(
					((rfiChecklistItemId != null && rfiChecklistItemId > 0) ? HttpStatus.OK.value()
							: HttpStatus.BAD_REQUEST.value()),
					rfiChecklistItemId, ((rfiChecklistItemId != null && rfiChecklistItemId > 0) ? HttpStatus.OK.name()
							: "Name already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateRfiChecklistItem(RfiChecklistItemBoqsAddUpdateRequest requestDTO) {
		try {

			RfiChecklistItems rfiChecklistItem = setObject.rfiChecklistItemAddUpdateRequestDtoToEntity(requestDTO);

			RfiChecklistItems dbObj = rfiChecklistItemDao.fetchRfiChecklistItemById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = rfiChecklistItemDao.updateRfiChecklistItem(rfiChecklistItem);

			if (!isUpdated)
				return new CustomResponse((HttpStatus.BAD_REQUEST.value()), isUpdated, "Name already exist.");

			// save state transition
			if (isUpdated) {

				RfiChecklistItemStateTransition stateTransition = new RfiChecklistItemStateTransition(null,
						dbObj.getId(), new EngineState(requestDTO.getStateId()), requestDTO.getRemark(), true,
						new Date(), requestDTO.getUserDetail().getId());
				rfiChecklistItemDao.saveRfiChecklistItemStateTransitionMapping(stateTransition);
			}

			rfiChecklistItemBoqService.addUpdateRfiChecklistItemBoq(requestDTO);

			return new CustomResponse(HttpStatus.OK.value(), isUpdated, HttpStatus.OK.name());
		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getRfiChecklistItemList(RfiChecklistItemBoqsFetchRequest requestDTO) {
		try {

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId(), null, null, null,
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

			List<RfiChecklistItemStateTransition> fetchRfiChecklistItemStateTransitionList = rfiChecklistItemDao
					.fetchRfiChecklistItemStateTransitionList(requestDTO, roleStateMap);
			Integer rfiChecklistItemStateTransitionsCount = rfiChecklistItemDao
					.fetchRfiChecklistItemStateTransitionListCount(requestDTO, roleStateMap);

			List<RfiChecklistItems> rfiChecklistItems = new ArrayList<>();

			if (fetchRfiChecklistItemStateTransitionList != null
					&& !fetchRfiChecklistItemStateTransitionList.isEmpty()) {
				fetchRfiChecklistItemStateTransitionList
						.forEach(obj -> rfiChecklistItems.add(obj.getRfiChecklistItem()));
			}

			List<RfiChecklistItemBoqsFetchResponse> rfiChecklistItemBoqsList = new ArrayList<RfiChecklistItemBoqsFetchResponse>();

			if (rfiChecklistItems != null && !rfiChecklistItems.isEmpty()) {

				Set<Integer> rfiChecklistItemIds = new HashSet<>();
				rfiChecklistItems.forEach(obj -> rfiChecklistItemIds.add(obj.getId()));

				// fetching BOQs item list
				List<RfiChecklistItemBoqs> boqsItemList = rfiChecklistItemBoqDao
						.fetchRfiChecklistItemBoqsByCheckListItemIds(rfiChecklistItemIds);

				// set entity state data
				List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
						EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId(), requestDTO.getUserDetail().getCompanyId());

				for (RfiChecklistItems cli : rfiChecklistItems) {

					Set<Long> checklistItemBoqsIds = new HashSet<Long>();
					for (RfiChecklistItemBoqs clib : boqsItemList) {

						if (clib.getChecklistItemId().equals(cli.getId())) {
							checklistItemBoqsIds.add(clib.getBoqItemId());
						}

					}

					Boolean isInitial = false;
					Boolean isEditable = false;
					Boolean isFinal = false;
					Boolean isDeletable = false;

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId())
								&& esm.getStateId().equals(cli.getState().getId())) {
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

					// result obj
					RfiChecklistItemBoqsFetchResponse resultObj = new RfiChecklistItemBoqsFetchResponse(cli.getId(),
							cli.getName(), cli.getDescription(), checklistItemBoqsIds, cli.getState().getId(),
							cli.getState().getName());

					resultObj.setIsEditable(isEditable);
					resultObj.setIsDeletable(isDeletable);
					resultObj.setIsFinalState(isFinal);

					rfiChecklistItemBoqsList.add(resultObj);
				}
			}

			PaginationDTO responseObj = new PaginationDTO(rfiChecklistItemStateTransitionsCount,
					rfiChecklistItemBoqsList);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getRfiChecklistItemById(Integer id) {
		try {

			RfiChecklistItems rfiChecklistItems = rfiChecklistItemDao.fetchRfiChecklistItemById(id);

			if (rfiChecklistItems == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			List<RfiChecklistItemBoqs> boqsItemList = rfiChecklistItemBoqDao
					.fetchRfiChecklistItemBoqsByCheckListItemIds(
							new HashSet<Integer>(Arrays.asList(rfiChecklistItems.getId())));

			Set<Long> checklistItemBoqsIds = new HashSet<Long>();
			for (RfiChecklistItemBoqs clib : boqsItemList) {
				checklistItemBoqsIds.add(clib.getBoqItemId());
			}

			RfiChecklistItemBoqsFetchResponse rfiChecklistItemBoqs = new RfiChecklistItemBoqsFetchResponse(
					rfiChecklistItems.getId(), rfiChecklistItems.getName(), rfiChecklistItems.getDescription(),
					checklistItemBoqsIds, rfiChecklistItems.getState().getId(), rfiChecklistItems.getState().getName());

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId(), rfiChecklistItems.getCompanyId());

			Boolean isInitial = false;
			Boolean isEditable = false;
			Boolean isFinal = false;
			Boolean isDeletable = false;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId())
						&& esm.getStateId().equals(rfiChecklistItems.getState().getId())) {
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

			rfiChecklistItemBoqs.setIsEditable(isEditable);
			rfiChecklistItemBoqs.setIsDeletable(isDeletable);
			rfiChecklistItemBoqs.setIsFinalState(isFinal);

			return new CustomResponse(HttpStatus.OK.value(), rfiChecklistItemBoqs, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse deactivateRfiChecklistItem(RfiChecklistItemDeactivateRequest requestDTO) {
		try {

			RfiChecklistItems dbObj = rfiChecklistItemDao.fetchRfiChecklistItemById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = rfiChecklistItemDao.deactivateRfiChecklistItem(dbObj);

			if (isDeactivated) {
				rfiChecklistItemBoqService.deactivateRfiChecklistItemBoqsByCheckListItemId(requestDTO);
			}

			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(RfiChecklistItemNextPossibleStatesFetchRequest requestObj) {

		try {
			if (requestObj.getRfiChecklistItemId() == null) {
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Provide rfiChecklistItemId.");
			}
			RfiChecklistItems rfiChecklistItem = rfiChecklistItemDao
					.fetchRfiChecklistItemById(requestObj.getRfiChecklistItemId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId(), null, null, null,
					requestObj.getUser().getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User user = userDao.fetchUserById(rfiChecklistItem.getUpdatedBy().longValue());

//			set next states
			if (transitions != null && user != null && user.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(rfiChecklistItem.getState().getId())
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
	public CustomResponse getRfiChecklistItemStateTransitionByRfiChecklistItemId(Integer rfiChecklistItemId) {

		try {
			List<RfiChecklistItemStateTransition> rfiChecklistItemTransitionList = rfiChecklistItemDao
					.fetchRfiChecklistItemStateTransitionByRfiChecklistItemId(rfiChecklistItemId);
			List<RfiChecklistItemStateTransitionFetchResponse> results = new ArrayList<>();
			if (rfiChecklistItemTransitionList != null) {
				rfiChecklistItemTransitionList
						.forEach(obj -> results.add(setObject.rfiChecklistItemStateTransitionEntityToDto(obj)));
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
	public CustomResponse getRfiChecklistItemFinalSuccessList(RfiChecklistItemFinalSuccessFetchRequest requestDTO) {
		try {

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId(), requestDTO.getUserDetail().getCompanyId());

			Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.RFI_CHECKLIST_ITEM.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
					finalSuccessStateIds.add(esm.getStateId());
				}

			}

			List<RfiChecklistItemBoqsV2> rfiChecklistItemBoqsList = rfiChecklistItemBoqDao
					.fetchRfiChecklistItemBoqsByStateIds(finalSuccessStateIds, requestDTO);

			Long rfiChecklistItemBoqsListCount = 0L;
			if (requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0 && requestDTO.getPageSize() != null) {
				rfiChecklistItemBoqsListCount = rfiChecklistItemBoqDao
						.fetchRfiChecklistItemBoqsByStateIdsCount(finalSuccessStateIds, requestDTO);
			}

			List<RfiChecklistItemFetchFinalSuccessListResponse> checklistItemResponse = new ArrayList<>();
			if (rfiChecklistItemBoqsList != null && !rfiChecklistItemBoqsList.isEmpty()) {
				rfiChecklistItemBoqsList.forEach(obj -> checklistItemResponse
						.add(setObject.rfiChecklistItemBoqEntityToFetchFinalSuccessListResponseDto(obj)));
			}

			PaginationDTO responseObj = new PaginationDTO(
					rfiChecklistItemBoqsListCount != 0L ? rfiChecklistItemBoqsListCount.intValue()
							: checklistItemResponse.size(),
					checklistItemResponse);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}
}
