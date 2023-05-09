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

import erp.boq_mgmt.dao.WorkLayerBoqDao;
import erp.boq_mgmt.dao.WorkLayerDao;
import erp.boq_mgmt.dao.UserDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.EntityStateMapDTO;
import erp.boq_mgmt.dto.NextStateTransitDTO;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.StateTransitionDTO;
import erp.boq_mgmt.dto.request.WorkLayerBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.WorkLayerBoqsFetchRequest;
import erp.boq_mgmt.dto.request.WorkLayerDeactivateRequest;
import erp.boq_mgmt.dto.request.WorkLayerFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.WorkLayerNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.response.WorkLayerBoqsFetchResponse;
import erp.boq_mgmt.dto.response.WorkLayerFetchFinalSuccessListResponse;
import erp.boq_mgmt.dto.response.WorkLayerStateTransitionFetchResponse;
import erp.boq_mgmt.entity.EngineState;
import erp.boq_mgmt.entity.WorkLayerBoqs;
import erp.boq_mgmt.entity.WorkLayerBoqsV2;
import erp.boq_mgmt.entity.WorkLayerStateTransition;
import erp.boq_mgmt.entity.WorkLayer;
import erp.boq_mgmt.entity.User;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.StateAction;
import erp.boq_mgmt.feignClient.service.WorkflowEngineService;
import erp.boq_mgmt.service.WorkLayerBoqService;
import erp.boq_mgmt.service.WorkLayerService;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class WorkLayerServiceImpl implements WorkLayerService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkLayerDao workLayerDao;

	@Autowired
	private WorkLayerBoqDao workLayerBoqDao;

	@Autowired
	private WorkLayerBoqService workLayerBoqService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Override
	public CustomResponse addWorkLayer(WorkLayerBoqsAddUpdateRequest requestDTO) {
		try {

			WorkLayer workLayer = setObject.workLayerAddUpdateRequestDtoToEntity(requestDTO);

			Integer workLayerId = workLayerDao.saveWorkLayer(workLayer);

			if (workLayerId != null && workLayerId > 0) {

				requestDTO.setId(workLayerId);

				workLayerBoqService.addUpdateWorkLayerBoq(requestDTO);

				// save state transition
				WorkLayerStateTransition stateTransition = new WorkLayerStateTransition(null, workLayerId,
						new EngineState(requestDTO.getStateId()), requestDTO.getRemark(), true, new Date(),
						requestDTO.getUserDetail().getId());
				workLayerDao.saveWorkLayerStateTransitionMapping(stateTransition);

			}

			return new CustomResponse(
					((workLayerId != null && workLayerId > 0) ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value()),
					workLayerId,
					((workLayerId != null && workLayerId > 0) ? HttpStatus.OK.name() : "Name already exist."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateWorkLayer(WorkLayerBoqsAddUpdateRequest requestDTO) {
		try {

			WorkLayer workLayer = setObject.workLayerAddUpdateRequestDtoToEntity(requestDTO);

			WorkLayer dbObj = workLayerDao.fetchWorkLayerById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			Boolean isUpdated = workLayerDao.updateWorkLayer(workLayer);

			if (!isUpdated)
				return new CustomResponse((HttpStatus.BAD_REQUEST.value()), isUpdated, "Name already exist.");

			// save state transition
			if (isUpdated) {

				WorkLayerStateTransition stateTransition = new WorkLayerStateTransition(null, dbObj.getId(),
						new EngineState(requestDTO.getStateId()), requestDTO.getRemark(), true, new Date(),
						requestDTO.getUserDetail().getId());
				workLayerDao.saveWorkLayerStateTransitionMapping(stateTransition);
			}

			workLayerBoqService.addUpdateWorkLayerBoq(requestDTO);

			return new CustomResponse(HttpStatus.OK.value(), isUpdated, HttpStatus.OK.name());
		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getWorkLayerList(WorkLayerBoqsFetchRequest requestDTO) {
		try {

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.WORK_LAYER.getEntityId(), null, null, null, requestDTO.getUserDetail().getCompanyId());
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

			List<WorkLayerStateTransition> fetchWorkLayerStateTransitionList = workLayerDao
					.fetchWorkLayerStateTransitionList(requestDTO, roleStateMap);
			Integer workLayerStateTransitionsCount = workLayerDao.fetchWorkLayerStateTransitionListCount(requestDTO,
					roleStateMap);

			List<WorkLayer> workLayer = new ArrayList<>();

			if (fetchWorkLayerStateTransitionList != null && !fetchWorkLayerStateTransitionList.isEmpty()) {
				fetchWorkLayerStateTransitionList.forEach(obj -> workLayer.add(obj.getWorkLayer()));
			}

			List<WorkLayerBoqsFetchResponse> workLayerBoqsList = new ArrayList<WorkLayerBoqsFetchResponse>();

			if (workLayer != null && !workLayer.isEmpty()) {

				Set<Integer> workLayerIds = new HashSet<>();
				workLayer.forEach(obj -> workLayerIds.add(obj.getId()));

				// fetching BOQs item list
				List<WorkLayerBoqs> boqsItemList = workLayerBoqDao.fetchWorkLayerBoqsByWorkLayerIds(workLayerIds);

				// set entity state data
				List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
						EntitiesEnum.WORK_LAYER.getEntityId(), requestDTO.getUserDetail().getCompanyId());

				for (WorkLayer wl : workLayer) {

					Set<Long> workLayerBoqsIds = new HashSet<Long>();
					for (WorkLayerBoqs wlb : boqsItemList) {

						if (wlb.getWorkLayerId().equals(wl.getId())) {
							workLayerBoqsIds.add(wlb.getBoqItemId());
						}

					}

					Boolean isInitial = false;
					Boolean isEditable = false;
					Boolean isFinal = false;
					Boolean isDeletable = false;

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.WORK_LAYER.getEntityId())
								&& esm.getStateId().equals(wl.getState().getId())) {
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
					WorkLayerBoqsFetchResponse resultObj = new WorkLayerBoqsFetchResponse(wl.getId(), wl.getCode(),
							wl.getName(), wl.getDescription(), workLayerBoqsIds, wl.getState().getId(),
							wl.getState().getName());

					resultObj.setIsEditable(isEditable);
					resultObj.setIsDeletable(isDeletable);
					resultObj.setIsFinalState(isFinal);

					workLayerBoqsList.add(resultObj);
				}
			}

			PaginationDTO responseObj = new PaginationDTO(workLayerStateTransitionsCount, workLayerBoqsList);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getWorkLayerById(Integer id) {
		try {

			WorkLayer workLayer = workLayerDao.fetchWorkLayerById(id);

			if (workLayer == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			List<WorkLayerBoqs> boqsItemList = workLayerBoqDao
					.fetchWorkLayerBoqsByWorkLayerIds(new HashSet<Integer>(Arrays.asList(workLayer.getId())));

			Set<Long> workLayerBoqsIds = new HashSet<Long>();
			for (WorkLayerBoqs wlb : boqsItemList) {
				workLayerBoqsIds.add(wlb.getBoqItemId());
			}

			WorkLayerBoqsFetchResponse workLayerBoqs = new WorkLayerBoqsFetchResponse(workLayer.getId(),
					workLayer.getCode(), workLayer.getName(), workLayer.getDescription(), workLayerBoqsIds,
					workLayer.getState().getId(), workLayer.getState().getName());

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.WORK_LAYER.getEntityId(), workLayer.getCompanyId());

			Boolean isInitial = false;
			Boolean isEditable = false;
			Boolean isFinal = false;
			Boolean isDeletable = false;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.WORK_LAYER.getEntityId())
						&& esm.getStateId().equals(workLayer.getState().getId())) {
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

			workLayerBoqs.setIsEditable(isEditable);
			workLayerBoqs.setIsDeletable(isDeletable);
			workLayerBoqs.setIsFinalState(isFinal);

			return new CustomResponse(HttpStatus.OK.value(), workLayerBoqs, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse deactivateWorkLayer(WorkLayerDeactivateRequest requestDTO) {
		try {

			WorkLayer dbObj = workLayerDao.fetchWorkLayerById(requestDTO.getId());

			if (dbObj == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			dbObj.setIsActive(false);
			dbObj.setUpdatedOn(new Date());
			dbObj.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

			Boolean isDeactivated = workLayerDao.deactivateWorkLayer(dbObj);

			if (isDeactivated) {
				workLayerBoqService.deactivateWorkLayerBoqsByWorkLayerId(requestDTO);
			}

			return new CustomResponse(HttpStatus.OK.value(), isDeactivated, HttpStatus.OK.name());

		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(WorkLayerNextPossibleStatesFetchRequest requestObj) {

		try {
			if (requestObj.getWorkLayerId() == null) {
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Provide workLayerId.");
			}
			WorkLayer workLayer = workLayerDao.fetchWorkLayerById(requestObj.getWorkLayerId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.WORK_LAYER.getEntityId(), null, null, null, requestObj.getUser().getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User user = userDao.fetchUserById(workLayer.getUpdatedBy().longValue());

//			set next states
			if (transitions != null && user != null && user.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(workLayer.getState().getId())
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
	public CustomResponse getWorkLayerStateTransitionByWorkLayerId(Integer workLayerId) {

		try {
			List<WorkLayerStateTransition> workLayerTransitionList = workLayerDao
					.fetchWorkLayerStateTransitionByWorkLayerId(workLayerId);
			List<WorkLayerStateTransitionFetchResponse> results = new ArrayList<>();
			if (workLayerTransitionList != null) {
				workLayerTransitionList.forEach(obj -> results.add(setObject.workLayerStateTransitionEntityToDto(obj)));
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
	public CustomResponse getWorkLayerFinalSuccessList(WorkLayerFinalSuccessFetchRequest requestDTO) {
		try {

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.WORK_LAYER.getEntityId(), requestDTO.getUserDetail().getCompanyId());

			Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.WORK_LAYER.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
					finalSuccessStateIds.add(esm.getStateId());
				}

			}

			List<WorkLayerBoqsV2> workLayerBoqsList = workLayerBoqDao.fetchWorkLayerBoqsByStateIds(finalSuccessStateIds,
					requestDTO);

			Long workLayerBoqsListCount = 0L;
			if (requestDTO.getPageNo() != null && requestDTO.getPageNo() > 0 && requestDTO.getPageSize() != null) {
				workLayerBoqsListCount = workLayerBoqDao.fetchWorkLayerBoqsByStateIdsCount(finalSuccessStateIds,
						requestDTO);
			}

			List<WorkLayerFetchFinalSuccessListResponse> workLayerResponse = new ArrayList<>();
			if (workLayerBoqsList != null && !workLayerBoqsList.isEmpty()) {
				workLayerBoqsList.forEach(obj -> workLayerResponse
						.add(setObject.workLayerBoqEntityToFetchFinalSuccessListResponseDto(obj)));
			}

			PaginationDTO responseObj = new PaginationDTO(
					workLayerBoqsListCount != 0L ? workLayerBoqsListCount.intValue() : workLayerResponse.size(),
					workLayerResponse);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}
}
