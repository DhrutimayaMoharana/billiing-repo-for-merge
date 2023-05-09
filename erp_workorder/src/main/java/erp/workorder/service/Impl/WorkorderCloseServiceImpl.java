package erp.workorder.service.Impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import erp.workorder.dao.BillDao;
import erp.workorder.dao.IssueSlipItemDao;
import erp.workorder.dao.WorkorderCloseDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderEquipmentIssueDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.EngineStateDTO;
import erp.workorder.dto.EntityStateMapDTO;
import erp.workorder.dto.NextStateTransitDTO;
import erp.workorder.dto.PaginationDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.StateTransitionDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.WorkorderCloseAddUpdateRequest;
import erp.workorder.dto.request.WorkorderCloseDeactivateRequest;
import erp.workorder.dto.request.WorkorderCloseGetRequest;
import erp.workorder.dto.request.WorkorderCloseNextActionsRequest;
import erp.workorder.dto.request.WorkorderCloseUpdateStateRequest;
import erp.workorder.dto.response.WorkorderCloseGetResponse;
import erp.workorder.dto.response.WorkorderCloseTypeGetResponse;
import erp.workorder.entity.Bill;
import erp.workorder.entity.Equipment;
import erp.workorder.entity.IssueSlipItem;
import erp.workorder.entity.User;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderClose;
import erp.workorder.entity.WorkorderCloseStateTransitionMapping;
import erp.workorder.entity.WorkorderV4;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.enums.StateAction;
import erp.workorder.enums.WorkorderCloseType;
import erp.workorder.feignClient.service.WorkflowEngineService;
import erp.workorder.notificator.service.NotificatorService;
import erp.workorder.service.WorkorderCloseService;
import erp.workorder.util.DateUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderCloseServiceImpl implements WorkorderCloseService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderCloseDao workorderCloseDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkorderDao workorderDao;

	@Autowired
	private WorkorderEquipmentIssueDao workorderEquipmentIssueDao;

	@Autowired
	private IssueSlipItemDao issueSlipItemDao;

	@Autowired
	private BillDao billDao;
	@Autowired
	private NotificatorService notificatorService;

	@Override
	public CustomResponse getWorkorderClose(WorkorderCloseGetRequest clientRequestDTO) {

		try {

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.WORKORDER_CLOSE.getEntityId(), clientRequestDTO.getUserDetail().getCompanyId());

			if (entityStateMaps == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Entity state mapping not found.");
			}

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.WORKORDER_CLOSE.getEntityId(), clientRequestDTO.getSiteId(), null, null,
					clientRequestDTO.getUserDetail().getCompanyId());

			if (transitions == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "State transition not found.");
			}

			Map<Integer, Set<Integer>> roleStateMap = new HashMap<Integer, Set<Integer>>();
			Integer draftStateId = null;
			Set<Integer> stateVisibilityIds = new HashSet<>();

			for (EntityStateMapDTO esm : entityStateMaps) {
				if (esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
					stateVisibilityIds.add(esm.getStateId());
				}
				if (esm.getStateActionId().equals(StateAction.Draft.getValue())) {
					draftStateId = esm.getStateId();
				}

			}

//			set next states
			for (StateTransitionDTO st : transitions) {
				if (st.getNextRoleId().equals(clientRequestDTO.getUserDetail().getRoleId())) {

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

			List<WorkorderCloseStateTransitionMapping> workorderCloseStateTransitionList = workorderCloseDao
					.fetchWorkorderCloseStateTransitionList(clientRequestDTO, roleStateMap, draftStateId,
							stateVisibilityIds);
			Integer workorderCloseStateTransitionCount = workorderCloseDao.fetchWorkorderCloseStateTransitionListCount(
					clientRequestDTO, roleStateMap, draftStateId, stateVisibilityIds);

			List<WorkorderCloseGetResponse> resultList = new ArrayList<>();
			if (workorderCloseStateTransitionList != null) {
				for (WorkorderCloseStateTransitionMapping woCloseST : workorderCloseStateTransitionList) {
					WorkorderCloseGetResponse obj = setObject
							.workorderCloseEntityToGetResponseDto(woCloseST.getWorkorderClose());

					// set next buttons
					if (transitions != null && clientRequestDTO.getUserDetail().getRoleId() != null) {
						List<NextStateTransitDTO> nextStates = new ArrayList<>();
						for (StateTransitionDTO st : transitions) {
							if (st.getStateId().equals(woCloseST.getState().getId())
									&& st.getRoleId()
											.equals(woCloseST.getWorkorderClose().getUpdatedByUser().getRole().getId())
									&& st.getNextRoleId().equals(clientRequestDTO.getUserDetail().getRoleId())) {
								NextStateTransitDTO nst = new NextStateTransitDTO();
								nst.setNextStateId(st.getNextStateId());
								nst.setNextRoleId(st.getNextRoleId());
								nst.setIsButtonRequired(true);
								nst.setNextRoleName(st.getNextRole().getName());
								nst.setNextStateName(st.getNextState().getName());
								nst.setButtonText(st.getNextState().getButtonText());
								nst.setIsButtonRequired(st.getNextState().getButtonText() != null ? true : false);
								nst.setNextStateAlias(st.getNextState().getAlias());
								nextStates.add(nst);
							}
						}
						obj.setNextStates(nextStates);
					}

					Boolean isInitial = false;
					Boolean isEditable = false;
					Boolean isFinal = false;
					Boolean isDeletable = false;

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.WORKORDER_CLOSE.getEntityId())
								&& esm.getStateId().equals(woCloseST.getWorkorderClose().getEngineStateId())) {
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
					obj.setIsInitial(isInitial);
					obj.setIsEditable(isEditable);
					obj.setInFinalState(isFinal);
					obj.setIsDeletatble(isDeletable);

					resultList.add(obj);

				}
			}

			PaginationDTO paginatedResultObj = new PaginationDTO(workorderCloseStateTransitionCount, resultList);
			return new CustomResponse(Responses.SUCCESS.getCode(), paginatedResultObj, Responses.SUCCESS.toString());
		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addOrUpdateWorkorderClose(WorkorderCloseAddUpdateRequest clientRequestDTO) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(clientRequestDTO.getWorkorderId());
			if (workorder == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
			}
			if (!workorder.getStartDate().before(clientRequestDTO.getDated())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid date.");
			}
			List<Equipment> equipmentIssueList = workorderEquipmentIssueDao
					.fetchEquipmentsByWorkorderId(workorder.getId());

			Set<Long> distinctEquipmentIds = new HashSet<>();

			if (equipmentIssueList != null) {
				equipmentIssueList.forEach(obj -> distinctEquipmentIds.add(obj.getId()));
			}

			// validations
			if (clientRequestDTO.getCloseType().equals(WorkorderCloseType.CANCEL)) {

				List<IssueSlipItem> issueSlipItemList = issueSlipItemDao.fetchIssueSlipItems(null, null,
						distinctEquipmentIds);
				if (issueSlipItemList != null && !issueSlipItemList.isEmpty()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Diesel issued for this workorder.");
				}

				SearchDTO search = new SearchDTO();
				search.setSiteId(workorder.getSiteId());
				search.setWorkorderId(workorder.getId());

				List<Bill> billList = billDao.fetchBills(search);
				if (billList != null && !billList.isEmpty()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Bill has been generated for this workorder.");
				}

			} else {

				List<IssueSlipItem> issueSlipItemList = issueSlipItemDao.fetchIssueSlipItems(
						DateUtil.dateWithoutTime(clientRequestDTO.getDated()), DateUtil.nextDateWithoutTime(new Date()),
						distinctEquipmentIds);
				if (issueSlipItemList != null && !issueSlipItemList.isEmpty()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Date must be greater than last diesel issued date.");
				}

				SearchDTO search = new SearchDTO();
				search.setSiteId(workorder.getSiteId());
				search.setWorkorderId(workorder.getId());

				List<Bill> billList = billDao.fetchBills(search);
				if (billList != null && !billList.isEmpty()) {
					for (Bill bill : billList) {
						if (bill.getToDate().after(clientRequestDTO.getDated())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Date must be greater than last bill date.");
						}

					}

				}

			}

			if (clientRequestDTO.getId() == null) {

				EngineStateDTO initialState = engineService.getEntityInitialState(
						EntitiesEnum.WORKORDER_CLOSE.getEntityId(), clientRequestDTO.getUserDetail().getCompanyId());

				if (workorder.getCloseType() != null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Workorder is already closed or close request is in progress.");
				}

				WorkorderClose workorderClose = new WorkorderClose(null, clientRequestDTO.getWorkorderId(),
						clientRequestDTO.getCloseType(), clientRequestDTO.getDated(), clientRequestDTO.getRemarks(),
						clientRequestDTO.getSiteId(), initialState.getId(), true, new Date(),
						clientRequestDTO.getUserDetail().getId().intValue(), new Date(),
						clientRequestDTO.getUserDetail().getId().intValue());

				Long workorderCloseId = workorderCloseDao.saveWorkorderClose(workorderClose);

				if (workorderCloseId != null && workorderCloseId > 0L) {
					WorkorderCloseStateTransitionMapping trasition = new WorkorderCloseStateTransitionMapping(null,
							workorderCloseId, workorderClose.getEngineStateId(), null, true, new Date(),
							clientRequestDTO.getUserDetail().getId());
					workorderCloseDao.mapWorkorderCloseStateTransition(trasition);

					CustomResponse workorderCloseResponse = updateWorkorderCloseType(clientRequestDTO.getWorkorderId(),
							workorderClose.getCloseType(), null, clientRequestDTO.getUserDetail());

					if (!workorderCloseResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
								Responses.PROBLEM_OCCURRED.toString());
					}
				}

				return new CustomResponse(Responses.SUCCESS.getCode(), workorderCloseId,
						workorderCloseId != null ? "Added." : "Already exists.");
			} else {
				WorkorderClose workorderCloseDbObj = workorderCloseDao
						.fetchWorkorderCloseById(clientRequestDTO.getId());

				EntityStateMapDTO esmObj = engineService.getEntityStateMap(EntitiesEnum.WORKORDER_CLOSE.getEntityId(),
						workorderCloseDbObj.getEngineStateId(), clientRequestDTO.getUserDetail().getCompanyId());

				if (!esmObj.getIsEditable()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Not in editable mode.");
				}
				workorderCloseDbObj.setDated(clientRequestDTO.getDated());
				workorderCloseDbObj.setRemarks(clientRequestDTO.getRemarks());
				workorderCloseDbObj.setUpdatedOn(new Date());
				workorderCloseDbObj.setUpdatedBy(clientRequestDTO.getUserDetail().getId().intValue());
				workorderCloseDao.updateWorkorderClose(workorderCloseDbObj);
				return new CustomResponse(Responses.SUCCESS.getCode(), workorderCloseDbObj.getId(), "Updated.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateWorkorderClose(WorkorderCloseDeactivateRequest clientRequestDTO) {

		try {

			WorkorderClose workorderClose = workorderCloseDao
					.fetchWorkorderCloseById(clientRequestDTO.getWorkorderCloseId());
			if (workorderClose == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderCloseId.");

			EntityStateMapDTO esmObj = engineService.getEntityStateMap(EntitiesEnum.WORKORDER_CLOSE.getEntityId(),
					workorderClose.getEngineStateId(), clientRequestDTO.getUserDetail().getCompanyId());
			if (!esmObj.getIsInitial()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Unable to deactivate, due to not being in initial state.");
			}
			workorderClose.setIsActive(false);
			workorderClose.setUpdatedOn(new Date());
			workorderClose.setUpdatedBy(clientRequestDTO.getUserDetail().getId().intValue());
			workorderCloseDao.forceUpdateWorkorderClose(workorderClose);
			return new CustomResponse(Responses.SUCCESS.getCode(), workorderClose.getId(), "Deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateWorkorderCloseState(WorkorderCloseUpdateStateRequest clientRequestDTO) {

		try {
			WorkorderClose workorderClose = workorderCloseDao
					.fetchWorkorderCloseById(clientRequestDTO.getWorkorderCloseId());
			if (workorderClose.getEngineStateId().equals(clientRequestDTO.getStateId())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Workorder Close is already in same state.");
			}
			Integer stateId = workorderClose.getEngineStateId();
			User lastModifiedBy = workorderClose.getUpdatedByUser();

			List<StateTransitionDTO> stateTransits = engineService.getStateTransition(
					EntitiesEnum.WORKORDER_CLOSE.getEntityId(), clientRequestDTO.getSiteId(), stateId,
					lastModifiedBy.getRole().getId(), clientRequestDTO.getUserDetail().getCompanyId());

			StateTransitionDTO stateTransit = null;
			if (stateTransits != null) {
				for (StateTransitionDTO st : stateTransits) {
					if (st.getNextRoleId().equals(clientRequestDTO.getUserDetail().getRoleId())
							&& st.getNextStateId().equals(clientRequestDTO.getStateId())) {
						stateTransit = st;
						break;
					}
				}
			}

			if (stateTransit == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "No transition available to perform.");
			}

			workorderClose.setEngineStateId(clientRequestDTO.getStateId());
			workorderClose.setUpdatedOn(new Date());
			workorderClose.setUpdatedBy(clientRequestDTO.getUserDetail().getId().intValue());
			workorderCloseDao.forceUpdateWorkorderClose(workorderClose);

			WorkorderCloseStateTransitionMapping trasition = new WorkorderCloseStateTransitionMapping(null,
					workorderClose.getId(), workorderClose.getEngineStateId(), null, true, new Date(),
					clientRequestDTO.getUserDetail().getId());
			workorderCloseDao.mapWorkorderCloseStateTransition(trasition);

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.WORKORDER_CLOSE.getEntityId(), clientRequestDTO.getUserDetail().getCompanyId());

			Set<Integer> finalRejectStateIds = new HashSet<Integer>();
			Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

			for (EntityStateMapDTO esm : entityStateMaps) {
				if (esm.getEntityId().equals(EntitiesEnum.WORKORDER_CLOSE.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Final_Reject.getValue())) {
					finalRejectStateIds.add(esm.getStateId());
				}

				if (esm.getEntityId().equals(EntitiesEnum.WORKORDER_CLOSE.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
					finalSuccessStateIds.add(esm.getStateId());
				}

			}

			if (finalRejectStateIds.contains(workorderClose.getEngineStateId())) {

				CustomResponse workorderCloseResponse = updateWorkorderCloseType(workorderClose.getWorkorderId(), null,
						null, clientRequestDTO.getUserDetail());
				if (!workorderCloseResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
							Responses.PROBLEM_OCCURRED.toString());
				}

			}

			if (finalSuccessStateIds.contains(workorderClose.getEngineStateId())) {

				CustomResponse workorderCloseResponse = updateWorkorderCloseType(workorderClose.getWorkorderId(),
						workorderClose.getCloseType(), workorderClose.getDated(), clientRequestDTO.getUserDetail());
				if (!workorderCloseResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
							Responses.PROBLEM_OCCURRED.toString());
				}

			}

			Map<String, Object> queryVariableValue = new HashMap<>();
			queryVariableValue.put("workorder_id", workorderClose.getWorkorder().getId());
			queryVariableValue.put("site_id", workorderClose.getSiteId());
			queryVariableValue.put("company_id", workorderClose.getWorkorder().getCompanyId());

			notificatorService.sendNotificationV2(EntitiesEnum.WORKORDER_CLOSE.getEntityId(),
					clientRequestDTO.getStateId(), workorderClose.getWorkorder().getId(),
					workorderClose.getSiteId().intValue(), workorderClose.getWorkorder().getCompanyId(),
					SetObject.billingModuleId, queryVariableValue, null);

			return new CustomResponse(Responses.SUCCESS.getCode(), true, "Updated.");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	private CustomResponse updateWorkorderCloseType(Long workorderId, WorkorderCloseType closeType, Date dated,
			UserDetail userDetail) {

		try {
			WorkorderV4 workorder = workorderDao.fetchWorkorderV4ByWorkorderId(workorderId);
			if (workorder == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Workorder not found.");
			}
			if (dated != null) {
				workorder.setEndDate(dated);
			}
			workorder.setCloseType(closeType);
			workorder.setModifiedBy(userDetail.getId());
			workorder.setModifiedOn(new Date());
			workorderDao.forceUpdateWorkorderV4(workorder);

			return new CustomResponse(Responses.SUCCESS.getCode(), true, "Updated.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(WorkorderCloseNextActionsRequest clientRequestDTO) {

		try {

			WorkorderClose workorderClose = workorderCloseDao
					.fetchWorkorderCloseById(clientRequestDTO.getWorkorderCloseId());

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.WORKORDER_CLOSE.getEntityId(), clientRequestDTO.getSiteId(), null, null,
					clientRequestDTO.getUserDetail().getCompanyId());
			if (transitions == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.name());

			if (clientRequestDTO.getWorkorderCloseId() == null || clientRequestDTO.getWorkorderCloseId() <= 0) {
				List<EngineStateDTO> engineStateList = engineService.getMultipleEntityInitialState(
						EntitiesEnum.WORKORDER_CLOSE.getEntityId(), clientRequestDTO.getUserDetail().getCompanyId());
				List<NextStateTransitDTO> nextStates = new ArrayList<>();
				Set<Integer> distinctButtonStateIds = new HashSet<>();

				if (engineStateList != null && !engineStateList.isEmpty()) {
					for (StateTransitionDTO st : transitions) {
						for (EngineStateDTO engineState : engineStateList) {
							if (st.getRoleId().equals(clientRequestDTO.getUserDetail().getRoleId())
									&& st.getStateId().equals(engineState.getId())
									&& !distinctButtonStateIds.contains(engineState.getId())) {
								NextStateTransitDTO nst = new NextStateTransitDTO();
								nst.setNextStateId(engineState.getId());
								nst.setIsButtonRequired(true);
								nst.setNextStateName(engineState.getName());
								nst.setButtonText(engineState.getButtonText());
								nst.setNextStateAlias(engineState.getAlias());
								nst.setRgbColorCode(engineState.getRgbColorCode());
								nextStates.add(nst);
								distinctButtonStateIds.add(engineState.getId());
							}
						}
					}

				}

				return new CustomResponse(Responses.SUCCESS.getCode(), nextStates, Responses.SUCCESS.name());
			}

			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User workorderCloseUser = workorderClose.getUpdatedByUser();

//			set next states
			if (transitions != null && workorderCloseUser != null && workorderCloseUser.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(workorderClose.getEngineStateId())
							&& st.getRoleId().equals(workorderCloseUser.getRole().getId())
							&& st.getNextRoleId().equals(clientRequestDTO.getUserDetail().getRoleId())) {
						NextStateTransitDTO nst = new NextStateTransitDTO();
						nst.setNextStateId(st.getNextStateId());
						nst.setNextRoleId(st.getNextRoleId());
						nst.setIsButtonRequired(true);
						nst.setNextRoleName(st.getNextRole().getName());
						nst.setNextStateName(st.getNextState().getName());
						nst.setButtonText(st.getNextState().getButtonText());
						nst.setIsButtonRequired(st.getNextState().getButtonText() != null ? true : false);
						nst.setNextStateAlias(st.getNextState().getAlias());
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
	public CustomResponse getWorkorderCloseType() {

		try {

			List<WorkorderCloseTypeGetResponse> responseObj = new ArrayList<>();

			for (WorkorderCloseType workorderCloseType : WorkorderCloseType.values()) {
				responseObj.add(new WorkorderCloseTypeGetResponse(workorderCloseType.getId(), workorderCloseType.name(),
						workorderCloseType.getName()));
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), responseObj, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
