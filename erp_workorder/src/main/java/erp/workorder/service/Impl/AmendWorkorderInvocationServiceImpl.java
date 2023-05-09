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

import erp.workorder.dao.AmendWorkorderInvocationDao;
import erp.workorder.dao.WorkorderBasicDetailDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderEquipmentIssueDao;
import erp.workorder.dao.WorkorderTncMapDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.EngineStateDTO;
import erp.workorder.dto.EntityStateMapDTO;
import erp.workorder.dto.NextStateTransitDTO;
import erp.workorder.dto.PaginationDTO;
import erp.workorder.dto.StateTransitionDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.request.AmendWorkorderInvocationAddUpdateRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationDeactivateRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationGetRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationNextActionsRequest;
import erp.workorder.dto.request.AmendWorkorderInvocationUpdateStateRequest;
import erp.workorder.dto.response.AmendWorkorderInvocationGetResponse;
import erp.workorder.entity.AmendWorkorderInvocation;
import erp.workorder.entity.AmendWorkorderInvocationTransitionMapping;
import erp.workorder.entity.EngineState;
import erp.workorder.entity.User;
import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderEquipmentIssue;
import erp.workorder.entity.WorkorderStateTransitionMapping;
import erp.workorder.enums.EngineStates;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.enums.StateAction;
import erp.workorder.enums.WorkorderTypes;
import erp.workorder.feignClient.service.WorkflowEngineService;
import erp.workorder.service.AmendWorkorderInvocationService;
import erp.workorder.service.WorkorderBoqWorkService;
import erp.workorder.service.WorkorderConsultantWorkService;
import erp.workorder.service.WorkorderHiringMachineWorkService;
import erp.workorder.service.WorkorderLabourWorkService;
import erp.workorder.service.WorkorderTransportationWorkService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class AmendWorkorderInvocationServiceImpl implements AmendWorkorderInvocationService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AmendWorkorderInvocationDao invokeAmendmentDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Autowired
	private SetObject setObject;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private WorkorderDao workorderDao;

	@Autowired
	private WorkorderBasicDetailDao workorderBasicDetailDao;

	@Autowired
	private WorkorderTncMapDao workorderTncMapDao;

	@Autowired
	private WorkorderEquipmentIssueDao workorderEquipmentIssueDao;

	@Autowired
	private WorkorderBoqWorkService workorderBoqWorkService;

	@Autowired
	private WorkorderConsultantWorkService workorderConsultantWorkService;

	@Autowired
	private WorkorderHiringMachineWorkService workorderHiringMachineWorkService;

	@Autowired
	private WorkorderTransportationWorkService workorderTransportationWorkService;

	@Autowired
	private WorkorderLabourWorkService workorderLabourWorkService;

	@Override
	public CustomResponse getWorkorderAmendmentInvocation(AmendWorkorderInvocationGetRequest clientRequestDTO) {

		try {

			CustomResponse validationRes = validationUtil
					.validateGetWorkorderAmendmentInvocationRequest(clientRequestDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(),
					clientRequestDTO.getUserDetail().getCompanyId());

			if (entityStateMaps == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Entity state mapping not found.");
			}

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(), clientRequestDTO.getSiteId(), null, null,
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

			List<AmendWorkorderInvocationTransitionMapping> fetchMachineryCategoryStateTransitionList = invokeAmendmentDao
					.fetchWorkorderAmendmentInvocationStateTransitionList(clientRequestDTO, roleStateMap, draftStateId,
							stateVisibilityIds);
			Integer machineryCategoryStateTransitionCount = invokeAmendmentDao
					.fetchWorkorderAmendmentInvocationStateTransitionListCount(clientRequestDTO, roleStateMap,
							draftStateId, stateVisibilityIds);

			List<AmendWorkorderInvocationGetResponse> resultList = new ArrayList<>();
			if (fetchMachineryCategoryStateTransitionList != null) {
				for (AmendWorkorderInvocationTransitionMapping invocationST : fetchMachineryCategoryStateTransitionList) {
					AmendWorkorderInvocationGetResponse obj = setObject
							.amendWorkorderInvocationEntityToGetResponseDto(invocationST.getAmendWorkorderInvocation());

					// set next buttons
					if (transitions != null && clientRequestDTO.getUserDetail().getRoleId() != null) {
						List<NextStateTransitDTO> nextStates = new ArrayList<>();
						for (StateTransitionDTO st : transitions) {
							if (st.getStateId().equals(invocationST.getState().getId())
									&& st.getRoleId()
											.equals(invocationST.getAmendWorkorderInvocation().getModifiedByUser()
													.getRole().getId())
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

						if (esm.getEntityId().equals(EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId())
								&& esm.getStateId().equals(invocationST.getAmendWorkorderInvocation().getStateId())) {
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

			PaginationDTO paginatedResultObj = new PaginationDTO(machineryCategoryStateTransitionCount.intValue(),
					resultList);
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
	public CustomResponse addOrUpdateWorkorderAmendmentInvocation(
			AmendWorkorderInvocationAddUpdateRequest clientRequestDTO) {

		try {
			if (clientRequestDTO.getDated() == null)
				clientRequestDTO.setDated(new Date());
			CustomResponse validationRes = validationUtil
					.validateAddOrUpdateWorkorderAmendmentInvocation(clientRequestDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			Workorder workorder = workorderDao.fetchWorkorderById(clientRequestDTO.getWorkorderId());
			if (workorder == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
			}
			if (!workorder.getStartDate().before(clientRequestDTO.getDated())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid date.");
			}
			if (clientRequestDTO.getId() == null) {
				EngineStateDTO initialState = engineService.getEntityInitialState(
						EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(),
						clientRequestDTO.getUserDetail().getCompanyId());
				AmendWorkorderInvocation invokeAmendment = new AmendWorkorderInvocation(null,
						clientRequestDTO.getWorkorderId(), clientRequestDTO.getDated(), clientRequestDTO.getSubject(),
						initialState.getId(), clientRequestDTO.getSiteId(), true, new Date(),
						clientRequestDTO.getUserDetail().getId(), new Date(), clientRequestDTO.getUserDetail().getId());
				Long invokeAmendmentId = invokeAmendmentDao.saveAmendWorkorderInvocation(invokeAmendment);
				if (invokeAmendmentId != null && invokeAmendmentId.longValue() > 0L) {
					AmendWorkorderInvocationTransitionMapping trasition = new AmendWorkorderInvocationTransitionMapping(
							null, invokeAmendmentId, invokeAmendment.getStateId(), true, new Date(),
							clientRequestDTO.getUserDetail().getId());
					invokeAmendmentDao.mapAmendWorkorderInvocationTransition(trasition);
				}
				return new CustomResponse(Responses.SUCCESS.getCode(), invokeAmendmentId,
						invokeAmendmentId != null ? "Added." : "Already exists.");
			} else {
				AmendWorkorderInvocation savedInvocation = invokeAmendmentDao
						.fetchAmendWorkorderInvocationById(clientRequestDTO.getId());
				EntityStateMapDTO esmObj = engineService.getEntityStateMap(
						EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(), savedInvocation.getStateId(),
						clientRequestDTO.getUserDetail().getCompanyId());
				if (!esmObj.getIsEditable()) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Not in editable mode.");
				}
				savedInvocation.setDated(clientRequestDTO.getDated());
				savedInvocation.setSubject(clientRequestDTO.getSubject());
				savedInvocation.setModifiedOn(new Date());
				savedInvocation.setModifiedBy(clientRequestDTO.getUserDetail().getId());
				invokeAmendmentDao.updateAmendWorkorderInvocation(savedInvocation);
				return new CustomResponse(Responses.SUCCESS.getCode(), savedInvocation.getId(), "Updated.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateWorkorderAmendmentInvocation(
			AmendWorkorderInvocationDeactivateRequest clientRequestDTO) {

		try {
			CustomResponse validationRes = validationUtil
					.validateDeactivateWorkorderAmendmentInvocation(clientRequestDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}

			AmendWorkorderInvocation savedInvocation = invokeAmendmentDao
					.fetchAmendWorkorderInvocationById(clientRequestDTO.getAmendWorkorderInvocationId());
			if (savedInvocation == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Provide valid amendWorkorderInvocationId.");

			EntityStateMapDTO esmObj = engineService.getEntityStateMap(
					EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(), savedInvocation.getStateId(),
					clientRequestDTO.getUserDetail().getCompanyId());
			if (!esmObj.getIsInitial()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Unable to deactivate, due to not being in initial state.");
			}
			savedInvocation.setIsActive(false);
			savedInvocation.setModifiedOn(new Date());
			savedInvocation.setModifiedBy(clientRequestDTO.getUserDetail().getId());
			invokeAmendmentDao.forceUpdateAmendWorkorderInvocation(savedInvocation);
			return new CustomResponse(Responses.SUCCESS.getCode(), savedInvocation.getId(), "Deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateWorkorderAmendmentInvocationState(
			AmendWorkorderInvocationUpdateStateRequest clientRequestDTO) {

		try {
			CustomResponse validationRes = validationUtil
					.validateUpdateWorkorderAmendmentInvocationState(clientRequestDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}
			AmendWorkorderInvocation savedInvocation = invokeAmendmentDao
					.fetchAmendWorkorderInvocationById(clientRequestDTO.getAmendWorkorderInvocationId());
			if (savedInvocation.getStateId().equals(clientRequestDTO.getStateId())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Invocation is already in same state.");
			}
			Integer stateId = savedInvocation.getStateId();
			User lastModifiedBy = savedInvocation.getModifiedByUser();

			List<StateTransitionDTO> stateTransits = engineService.getStateTransition(
					EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(), clientRequestDTO.getSiteId(), stateId,
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
			savedInvocation.setStateId(clientRequestDTO.getStateId());
			savedInvocation.setModifiedOn(new Date());
			savedInvocation.setModifiedBy(clientRequestDTO.getUserDetail().getId());
			invokeAmendmentDao.forceUpdateAmendWorkorderInvocation(savedInvocation);
			AmendWorkorderInvocationTransitionMapping trasition = new AmendWorkorderInvocationTransitionMapping(null,
					savedInvocation.getId(), clientRequestDTO.getStateId(), true, new Date(),
					clientRequestDTO.getUserDetail().getId());
			invokeAmendmentDao.mapAmendWorkorderInvocationTransition(trasition);

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(),
					clientRequestDTO.getUserDetail().getCompanyId());

			Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

			for (EntityStateMapDTO esm : entityStateMaps) {
				if (esm.getEntityId().equals(EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
					finalSuccessStateIds.add(esm.getStateId());
				}

			}

			if (finalSuccessStateIds.contains(savedInvocation.getStateId())) {

				CustomResponse amendResponse = amendWorkorder(clientRequestDTO.getAmendWorkorderInvocationId(),
						clientRequestDTO.getUserDetail());
				if (!amendResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return amendResponse;
				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), true, "Updated.");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	private CustomResponse amendWorkorder(Long amendWorkorderInvocationId, UserDetail userDetail) {
		try {

			AmendWorkorderInvocation amendWorkorderInvocation = invokeAmendmentDao
					.fetchAmendWorkorderInvocationById(amendWorkorderInvocationId);

			if (amendWorkorderInvocation == null) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Provide valid amendWorkorderInvocation.");
			}

			Long workorderId = amendWorkorderInvocation.getWorkorderId();

			Workorder workorder = workorderDao.fetchWorkorderById(workorderId);
			if (workorder == null) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
			}

			// TODO update previous workorder status
			String uniqueNo = generateAmendWorkorderUniqueNoByWorkorder(workorder);
			Workorder amendWorkorder = setObject.workorderEntityToAmendEntity(workorder);
			amendWorkorder.setState(new EngineState(EngineStates.Draft.getValue()));
			amendWorkorder.setFromAmendmentId(workorderId);
			amendWorkorder.setUniqueNo(uniqueNo);
			amendWorkorder.setLabourWork(workorder.getLabourWork());
			amendWorkorder.setCreatedBy(userDetail.getId());
			amendWorkorder.setModifiedBy(userDetail.getId());
			Long amendWorkorderId = workorderBasicDetailDao.saveBasicWorkorder(amendWorkorder);

			workorder.setEndDate(amendWorkorderInvocation.getDated());
			workorder.setModifiedOn(new Date());
			workorder.setModifiedBy(userDetail.getId());
			workorderDao.forceUpdateWorkorder(workorder);

			if (amendWorkorderId == null) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid workorderId.");
			}

			WorkorderStateTransitionMapping woStateTransitionMap = new WorkorderStateTransitionMapping(null,
					amendWorkorderId, EngineStates.Draft.getValue(), null, true, new Date(), userDetail.getId());
			workorderDao.mapWorkorderStateTransition(woStateTransitionMap);

			if (amendWorkorder.getType().getId().equals(WorkorderTypes.Highway.getId())
					|| amendWorkorder.getType().getId().equals(WorkorderTypes.Structure.getId())) {
				workorderBoqWorkService.amendWorkorderBoqWorkFlow(userDetail, amendWorkorderId, workorderId);
			}
			if (amendWorkorder.getType().getId().equals(WorkorderTypes.Consultancy.getId())) {
				workorderConsultantWorkService.amendWorkorderConsultantWorkFlow(userDetail, amendWorkorderId,
						workorderId);
			}
			if (amendWorkorder.getType().getId().equals(WorkorderTypes.Machine_Hiring.getId())) {
				workorderHiringMachineWorkService.amendWorkorderHiringMachineWorkFlow(userDetail, amendWorkorderId,
						workorderId);
			}
			if (amendWorkorder.getType().getId().equals(WorkorderTypes.Transportation.getId())) {
				workorderTransportationWorkService.amendWorkorderTransportWorkFlow(userDetail, amendWorkorderId,
						workorderId);
			}
			if (amendWorkorder.getType().getId().equals(WorkorderTypes.Labour_Supply.getId())) {
				workorderLabourWorkService.amendWorkorderLabourWorkFlow(userDetail, amendWorkorderId, workorderId);
			}

			List<WoTncMapping> woTncMappingList = workorderTncMapDao
					.fetchWorkorderTncMapByWorkorderId(amendWorkorderInvocation.getWorkorderId());

			if (woTncMappingList != null && !woTncMappingList.isEmpty()) {

				for (WoTncMapping woTncMapping : woTncMappingList) {

					WoTncMapping amendWoTnc = setObject.workorderTermsAndConditionsEntityToAmendEntity(woTncMapping);
					amendWoTnc.setWorkorder(new Workorder(amendWorkorderId));
					woTncMapping.setModifiedBy(userDetail.getId());

					workorderTncMapDao.saveUpdateWorkorderTncs(amendWoTnc);

				}

			}

			List<WorkorderEquipmentIssue> woEquipmentIssueList = workorderEquipmentIssueDao
					.fetchWorkorderIssuedEquipmentsByWorkorderId(amendWorkorderInvocation.getWorkorderId());

			if (woEquipmentIssueList != null && !woEquipmentIssueList.isEmpty()) {

				for (WorkorderEquipmentIssue equipmentIssue : woEquipmentIssueList) {

					WorkorderEquipmentIssue woEquipmentIssue = setObject
							.workorderEquipmentIssueEntityToAmendEntity(equipmentIssue);
					woEquipmentIssue.setWorkorderId(amendWorkorderId);
					woEquipmentIssue.setModifiedBy(userDetail.getId());

					workorderEquipmentIssueDao.issueEquipment(woEquipmentIssue);

				}

			}

			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.name());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.name());
		}
	}

	private String generateAmendWorkorderUniqueNoByWorkorder(Workorder previousWorkorder) {

		String lastUniqueNo = previousWorkorder.getUniqueNo();
		String uniqueNo = lastUniqueNo;
		if (previousWorkorder.getFromAmendmentId() == null) {
			uniqueNo = uniqueNo + "/AM/1";
		} else {
			String[] breakLastUniqueNo = lastUniqueNo.split("/");
			String textNumber = breakLastUniqueNo[breakLastUniqueNo.length - 1];
			Integer intNumber = Integer.parseInt(textNumber);
			intNumber++;
			textNumber = intNumber.toString();
			breakLastUniqueNo[breakLastUniqueNo.length - 1] = textNumber;
			uniqueNo = String.join("/", breakLastUniqueNo);
		}
		return uniqueNo;
	}

	@Override
	public CustomResponse getNextPossibleStates(AmendWorkorderInvocationNextActionsRequest clientRequestDTO) {

		try {
			CustomResponse validationRes = validationUtil
					.validateAmendWorkorderInvocationNextPossibleStates(clientRequestDTO);
			if (!validationRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationRes;
			}

			AmendWorkorderInvocation savedInvocation = invokeAmendmentDao
					.fetchAmendWorkorderInvocationById(clientRequestDTO.getAmendWorkorderInvocationId());

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(), clientRequestDTO.getSiteId(), null, null,
					clientRequestDTO.getUserDetail().getCompanyId());
			if (transitions == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.name());

			if (clientRequestDTO.getAmendWorkorderInvocationId() == null
					|| clientRequestDTO.getAmendWorkorderInvocationId() <= 0) {
				List<EngineStateDTO> engineStateList = engineService.getMultipleEntityInitialState(
						EntitiesEnum.AMEND_WORKORDER_INVOCATION.getEntityId(),
						clientRequestDTO.getUserDetail().getCompanyId());
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
			User invocationUser = savedInvocation.getModifiedByUser();

//			set next states
			if (transitions != null && invocationUser != null && invocationUser.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(savedInvocation.getStateId())
							&& st.getRoleId().equals(invocationUser.getRole().getId())
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

//	private Boolean versioningWorkorder(Long amendWorkorderInvocationId, UserDetail userDetail) {
//		try {
//
//			AmendWorkorderInvocation amendWorkorderInvocation = invokeAmendmentDao
//					.fetchAmendWorkorderInvocationById(amendWorkorderInvocationId);
//
//			if (amendWorkorderInvocation == null)
//				return false;
//
//			Long workorderVersionId = null;
//
//			List<WoTncMapping> woTncMappingList = workorderTncMapDao
//					.fetchWorkorderTncMapByWorkorderId(amendWorkorderInvocation.getWorkorderId());
//
//			if (woTncMappingList != null && !woTncMappingList.isEmpty()) {
//
//				for (WoTncMapping woTncMapping : woTncMappingList) {
//
//					WoTncMappingVersions woTncVersion = setObject
//							.workorderTermsAndConditionsEntityToVersionEntity(woTncMapping);
//					woTncVersion.setWorkorderVersionId(workorderVersionId);
//					woTncVersion.setCreatedBy(userDetail.getId());
//
//					Long woTncVersionId = workorderTncMapDao.saveWorkorderTermsAndConditionsVersion(woTncVersion);
//
//					if (woTncVersionId != null && woTncMapping.getVariableValues() != null) {
//
//						for (WoTncFormulaVariableValue variableValue : woTncMapping.getVariableValues()) {
//							WoTncFormulaVariableValueVersion woTncVariableValueVersion = setObject
//									.workorderTermsAndConditionsVariableValueEntityToVersionEntity(variableValue);
//							woTncVariableValueVersion.setWoTncVersionId(woTncVersionId);
//							woTncVariableValueVersion.setCreatedBy(userDetail.getId());
//							workorderTncMapDao.saveWorkorderTermsAndConditionsVersion(woTncVersion);
//
//						}
//
//					}
//
//				}
//
//			}
//
//			List<WorkorderEquipmentIssue> woEquipmentIssueList = workorderEquipmentIssueDao
//					.fetchWorkorderIssuedEquipmentsByWorkorderId(amendWorkorderInvocation.getWorkorderId());
//
//			if (woEquipmentIssueList != null && !woEquipmentIssueList.isEmpty()) {
//
//				for (WorkorderEquipmentIssue equipmentIssue : woEquipmentIssueList) {
//
//					WorkorderEquipmentIssueVersion woEquipmentIssueVersion = setObject
//							.workorderEquipmentIssueEntityToVersionEntity(equipmentIssue);
//					woEquipmentIssueVersion.setWorkorderVersionId(workorderVersionId);
//					woEquipmentIssueVersion.setCreatedBy(userDetail.getId());
//
//					workorderEquipmentIssueDao.saveWorkorderEquipmentIssueVersion(woEquipmentIssueVersion);
//
//				}
//
//			}
//
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
//			return false;
//		}
//	}

}
