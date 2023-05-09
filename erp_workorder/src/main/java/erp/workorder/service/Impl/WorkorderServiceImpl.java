package erp.workorder.service.Impl;

import java.io.ByteArrayOutputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.BillDao;
import erp.workorder.dao.BillDeductionMappingDao;
import erp.workorder.dao.BoqItemDao;
import erp.workorder.dao.CompanyDao;
import erp.workorder.dao.SiteDao;
import erp.workorder.dao.StateTransitionDao;
import erp.workorder.dao.UserDao;
import erp.workorder.dao.WorkorderBasicDetailDao;
import erp.workorder.dao.WorkorderBillInfoDao;
import erp.workorder.dao.WorkorderBillInfoMachineMappingDao;
import erp.workorder.dao.WorkorderCloseDao;
import erp.workorder.dao.WorkorderConsultantWorkDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderEquipmentIssueDao;
import erp.workorder.dao.WorkorderHiringMachineWorkDao;
import erp.workorder.dao.WorkorderLabourWorkDao;
import erp.workorder.dao.WorkorderMaterialConfigDao;
import erp.workorder.dao.WorkorderTncMapDao;
import erp.workorder.dao.WorkorderTncTypeDao;
import erp.workorder.dao.WorkorderTransportationWorkDao;
import erp.workorder.dto.CategoryItemDTO;
import erp.workorder.dto.CompanyDTO;
import erp.workorder.dto.ContractorBankDetailDTO;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.EntityStateMapDTO;
import erp.workorder.dto.MinimalObjectDTO;
import erp.workorder.dto.MinimalWorkorderBoqLocationDTO;
import erp.workorder.dto.NextStateTransitDTO;
import erp.workorder.dto.PaginationDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.SiteDTO;
import erp.workorder.dto.SiteWorkorderDTO;
import erp.workorder.dto.StateTransitionDTO;
import erp.workorder.dto.WoTncFormulaVariableValueDTO;
import erp.workorder.dto.WoTncMappingDTO;
import erp.workorder.dto.WorkorderBoqWorkParentChildDTO;
import erp.workorder.dto.WorkorderBoqWorkRenderDTO;
import erp.workorder.dto.WorkorderDTO;
import erp.workorder.dto.WorkorderEquipmentIssueDTO;
import erp.workorder.dto.WorkorderEquipmentMaterialRenderDTO;
import erp.workorder.dto.WorkorderListDTO;
import erp.workorder.dto.WorkorderListRenderDTO;
import erp.workorder.dto.WorkorderMaterialConfigDTO;
import erp.workorder.dto.WorkorderPrintDTO;
import erp.workorder.dto.request.BillDeductionMappingAddUpdateRequest;
import erp.workorder.dto.request.WorkoderHireMachineRequest;
import erp.workorder.dto.request.WorkorderBillInfoUpdateRequest;
import erp.workorder.dto.request.WorkorderRenewRequest;
import erp.workorder.dto.response.BillDeductionResponseDto;
import erp.workorder.dto.response.WorkorderBillInfoResponse;
import erp.workorder.dto.response.WorkorderConsultantWorkCategoryWiseItemGetResponse;
import erp.workorder.dto.response.WorkorderConsultantWorkGetResponse;
import erp.workorder.dto.response.WorkorderConsultantWorkItemGetResponse;
import erp.workorder.dto.response.WorkorderHiringMachineRateDetailsResponse;
import erp.workorder.dto.response.WorkorderHiringMachineWorkGetResponse;
import erp.workorder.dto.response.WorkorderHiringMachineWorkItemGetResponse;
import erp.workorder.dto.response.WorkorderLabourWorkGetResponse;
import erp.workorder.dto.response.WorkorderLabourWorkItemGetResponse;
import erp.workorder.dto.response.WorkorderMachineCategoryResponse;
import erp.workorder.dto.response.WorkorderMachineMapResponse;
import erp.workorder.dto.response.WorkorderStructureTypeBoqResponse;
import erp.workorder.dto.response.WorkorderTransportWorkGetResponse;
import erp.workorder.dto.response.WorkorderTransportWorkItemGetResponse;
import erp.workorder.dto.response.WorkordersFinalApprovedResponse;
import erp.workorder.entity.Bill;
import erp.workorder.entity.BillDeductionMapping;
import erp.workorder.entity.BillStateTransitionMapping;
import erp.workorder.entity.BoqItem;
import erp.workorder.entity.CategoryItem;
import erp.workorder.entity.Company;
import erp.workorder.entity.EngineState;
import erp.workorder.entity.EntityStateMap;
import erp.workorder.entity.FileEntity;
import erp.workorder.entity.Site;
import erp.workorder.entity.StateTransition;
import erp.workorder.entity.User;
import erp.workorder.entity.UserRole;
import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.WoTncType;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderBillInfo;
import erp.workorder.entity.WorkorderBillInfoMachineMapping;
import erp.workorder.entity.WorkorderBoqWork;
import erp.workorder.entity.WorkorderBoqWorkLocation;
import erp.workorder.entity.WorkorderBoqWorkQtyMapping;
import erp.workorder.entity.WorkorderCategoryMapping;
import erp.workorder.entity.WorkorderClose;
import erp.workorder.entity.WorkorderConsultantWork;
import erp.workorder.entity.WorkorderConsultantWorkItemMapping;
import erp.workorder.entity.WorkorderEquipmentIssue;
import erp.workorder.entity.WorkorderHiringMachineRateDetails;
import erp.workorder.entity.WorkorderHiringMachineWork;
import erp.workorder.entity.WorkorderHiringMachineWorkItemMapping;
import erp.workorder.entity.WorkorderLabourWork;
import erp.workorder.entity.WorkorderLabourWorkItemMapping;
import erp.workorder.entity.WorkorderMaterialConfig;
import erp.workorder.entity.WorkorderStateTransitionMapping;
import erp.workorder.entity.WorkorderTransportWork;
import erp.workorder.entity.WorkorderTransportWorkItemMapping;
import erp.workorder.entity.WorkorderV2;
import erp.workorder.entity.WorkorderV3;
import erp.workorder.enums.EngineStates;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.ItemType;
import erp.workorder.enums.Responses;
import erp.workorder.enums.StateAction;
import erp.workorder.enums.StateActions;
import erp.workorder.enums.VariableTypes;
import erp.workorder.enums.WorkorderCloseType;
import erp.workorder.enums.WorkorderTypes;
import erp.workorder.feignClient.service.WorkflowEngineService;
import erp.workorder.notificator.service.NotificatorService;
import erp.workorder.service.BillDeductionMappingService;
import erp.workorder.service.WorkorderService;
import erp.workorder.util.CustomSqlHelper;
import erp.workorder.util.DateUtil;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class WorkorderServiceImpl implements WorkorderService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private BoqItemDao boqDao;
	@Autowired
	private WorkorderDao workorderDao;
	@Autowired
	private SiteDao siteDao;
	@Autowired
	private WorkorderBasicDetailDao woBasicDao;
	@Autowired
	private WorkorderTncTypeDao woTncTypeDao;
	@Autowired
	private WorkorderMaterialConfigDao woMaterialConfigDao;
	@Autowired
	private WorkorderEquipmentIssueDao woEquipmentIssueDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private StateTransitionDao stateTransitionDao;
	@Autowired
	private WorkorderTncMapDao woTncMapDao;
	@Autowired
	private BillDao billDao;
	@Autowired
	private WorkflowEngineService engineService;
	@Autowired
	private WorkorderConsultantWorkDao consultantWorkDao;
	@Autowired
	private WorkorderTransportationWorkDao transportWorkDao;
	@Autowired
	private WorkorderHiringMachineWorkDao hmWorkDao;
	@Autowired
	private WorkorderLabourWorkDao labourWorkDao;
	@Autowired
	private NotificatorService notificatorService;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private WorkorderCloseDao workorderCloseDao;

	@Autowired
	private WorkorderBillInfoDao workorderBillInfoDao;

	@Autowired
	private BillDeductionMappingService billDeductionMappingService;

	@Autowired
	private BillDeductionMappingDao billDeductionMappingDao;

	@Autowired
	private WorkorderBillInfoMachineMappingDao workorderBillInfoMachineMappingDao;

	@Override
	public CustomResponse getWorkorders(SearchDTO search) {

		try {
			List<Workorder> workorders = workorderDao.fetchWorkorders(search);
			Set<Long> distinctWorkorderIds = new HashSet<>();
			if (workorders != null)
				workorders.forEach(workorder -> distinctWorkorderIds.add(workorder.getId()));

			List<WorkorderConsultantWorkItemMapping> siteConsultantWorkItems = consultantWorkDao
					.fetchWorkorderConsultantWorkItemsBySiteId(search.getSiteId());

			List<WorkorderTransportWorkItemMapping> siteTransportWorkItems = transportWorkDao
					.fetchWorkorderTransportWorkItemsBySiteId(search.getSiteId());

			List<WorkorderHiringMachineWorkItemMapping> siteHiringMachineItems = hmWorkDao
					.fetchWorkorderHiringMachineWorkItemsBySiteId(search.getSiteId());

			User user = userDao.fetchUserById(search.getUserId());

			search.setWorkorderId(null);
			List<Bill> bills = billDao.fetchBills(search);
			Set<Long> distinctBillIds = new HashSet<>();
			if (bills != null)
				bills.forEach(bill -> distinctBillIds.add(bill.getId()));

			Set<Long> userBillIds = new LinkedHashSet<>();
			UserRole role = user.getRole();

			List<StateTransitionDTO> billTransitions = engineService.getStateTransition(EntitiesEnum.BILL.getEntityId(),
					search.getSiteId(), null, null, search.getCompanyId());
			EntityStateMap billFinalObj = stateTransitionDao.fetchEntityFinalState(EntitiesEnum.BILL.getEntityId());
			List<BillStateTransitionMapping> billsStateMaps = billDao.fetchBillStateMappingsByBillIds(distinctBillIds);

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.WORKORDER.getEntityId(), search.getSiteId(), null, null, search.getCompanyId());
			EntityStateMap finalObj = stateTransitionDao.fetchEntityFinalState(EntitiesEnum.WORKORDER.getEntityId());
			List<WorkorderStateTransitionMapping> workordersStateMaps = workorderDao
					.fetchWorkorderStateMappingsByWorkorderIds(distinctWorkorderIds);

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.WORKORDER.getEntityId(), user.getCompanyId());

			List<WorkorderListDTO> obj = new ArrayList<>();
			if (workorders != null) {
				for (Workorder workorder : workorders) {
					WorkorderListDTO woObj = setObject.workorderEntityToListDto(workorder);
					User woUser = workorder.getModifiedByUser();
					woObj.setModifiedByName(woUser.getName());

//					set bill count
					int billCount = 0;
					if (bills != null && search.getIsApproved() != null && search.getIsApproved()) {
						for (Bill bill : bills) {
							if (bill.getWorkorderId().equals(workorder.getId())) {
								User billUser = bill.getModifiedByUser();
								boolean billInDraft = false;
								if (bill.getState().getId().equals(EngineStates.Draft.getValue())) {
									billInDraft = true;
								}

//								set next states

								List<NextStateTransitDTO> nextStates = new ArrayList<>();
								if (billTransitions != null && billUser != null && billUser.getRole() != null) {
									for (StateTransitionDTO st : billTransitions) {
										if (st.getStateId().equals(bill.getState().getId())
												&& st.getRoleId().equals(billUser.getRole().getId())
												&& st.getNextRoleId().equals(search.getRoleId())) {
											NextStateTransitDTO nst = new NextStateTransitDTO();
											nst.setNextStateId(st.getNextStateId());
											nst.setNextRoleId(st.getNextRoleId());
											nst.setIsButtonRequired(true);
											nst.setNextRoleName(st.getNextRole().getName());
											nst.setNextStateName(st.getNextState().getName());
											nst.setButtonText(st.getNextState().getButtonText());
											nst.setIsButtonRequired(
													st.getNextState().getButtonText() != null ? true : false);
											nst.setNextStateAlias(st.getNextState().getAlias());
											nextStates.add(nst);
										}
									}
								}

//								set final state mode
								boolean inFinalState = false;
								if (billFinalObj != null && bill.getState().getId().equals(billFinalObj.getStateId())) {
									inFinalState = true;
								}

//								add bill in result list
								boolean hasAdded = false;

								if (billInDraft && bill.getCreatedBy().equals(search.getUserId())) {
									billCount++;
									userBillIds.add(bill.getId());
									hasAdded = true;
								}

								if (!hasAdded && nextStates != null && nextStates.size() > 0) {
									billCount++;
									userBillIds.add(bill.getId());
									hasAdded = true;
								}
								if (!hasAdded && !billInDraft) {

									List<BillStateTransitionMapping> billStateMaps = new ArrayList<>();
									if (billsStateMaps != null)
										for (BillStateTransitionMapping billStateItr : billStateMaps) {
											if (billStateItr.getBillId().equals(bill.getId())) {
												billStateMaps.add(billStateItr);
											}
										}

									if (billStateMaps != null) {
										for (BillStateTransitionMapping stm : billStateMaps) {
											if (stm.getCreatedBy().equals(search.getUserId())) {
												billCount++;
												userBillIds.add(bill.getId());
												hasAdded = true;
												break;
											}
										}
									}
								}
								if (!hasAdded && inFinalState) {
									billCount++;
									userBillIds.add(bill.getId());
									hasAdded = true;
								}
							}
						}
					}
					woObj.setBillCount(billCount);

//					set edit mode
					woObj.setIsEditable(false);
					boolean woInDraft = false;
					if (woObj.getStateId().equals(EngineStates.Draft.getValue())) {
						woInDraft = true;
						woObj.setIsEditable(true);
						woObj.setUniqueNo(workorder.getState().getAlias() + "-" + woObj.getUniqueNo());
					}

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.WORKORDER.getEntityId())
								&& esm.getStateId().equals(woObj.getStateId())) {
							if (esm.getIsEditable() != null) {
								woObj.setIsEditable(esm.getIsEditable());
							}

						}

					}

					Set<Integer> renewStateIds = new HashSet<>();

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.WORKORDER.getEntityId())
								&& esm.getStateActionId().equals(StateActions.Renew.getStateActionId())) {
							renewStateIds.add(esm.getStateId());
						}

					}

//					set next states
					if (transitions != null && woUser != null && woUser.getRole() != null) {
						List<NextStateTransitDTO> nextStates = new ArrayList<>();
						for (StateTransitionDTO st : transitions) {
							if (st.getStateId().equals(woObj.getStateId())
									&& st.getRoleId().equals(woUser.getRole().getId())
									&& st.getNextRoleId().equals(role.getId())) {
								if (renewStateIds != null && renewStateIds.contains(st.getNextStateId())) {
									continue;
								}
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
						woObj.setNextStates(nextStates);
					}

//					set final state mode
					woObj.setInFinalState(false);
					if (finalObj != null && woObj.getStateId().equals(finalObj.getStateId())) {
						woObj.setInFinalState(true);
					}

//					add workorder in result list
					boolean hasAdded = false;

					if (woInDraft && workorder.getCreatedBy().equals(user.getId())) {
						obj.add(woObj);
						hasAdded = true;
					}

					if (!hasAdded && woObj.getNextStates() != null && woObj.getNextStates().size() > 0) {
						obj.add(woObj);
						hasAdded = true;
					}
					if (!hasAdded && !woInDraft) {
						List<WorkorderStateTransitionMapping> woStateMaps = new ArrayList<>();
						if (workordersStateMaps != null) {
							for (WorkorderStateTransitionMapping woStateItr : workordersStateMaps) {
								if (woStateItr.getWorkorderId().equals(workorder.getId())) {
									woStateMaps.add(woStateItr);
								}
							}
						}

						if (woStateMaps != null) {
							for (WorkorderStateTransitionMapping stm : woStateMaps) {
								if (stm.getCreatedBy().equals(user.getId())) {
									obj.add(woObj);
									hasAdded = true;
									break;
								}
							}
						}
					}
					if (!hasAdded && woObj.getInFinalState()) {
						obj.add(woObj);
						hasAdded = true;
					}

				}
			}

			HashSet<String> systemGeneratedWorkInfoSet = new HashSet<>();
			String systemGeneratedWorkInfo = null;

			if (obj != null) {
				for (WorkorderListDTO woDTO : obj) {

//					set workorder amount

					Double totalWorkorderAmount = 0.0;
					systemGeneratedWorkInfoSet.clear();
					systemGeneratedWorkInfo = "";

					for (Workorder workorder : workorders) {
						if (woDTO.getId().equals(workorder.getId())) {
							WorkorderBoqWork woBoqWork = workorder.getBoqWork();
							if (woBoqWork != null) {
								List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWork.getBoqWorkQty();
								if (woBoqQtys != null) {
									Double boqAmount = 0.0;
									for (WorkorderBoqWorkQtyMapping woBoqQty : woBoqQtys) {
										if (woBoqQty.getIsActive() == null || woBoqQty.getIsActive()) {
											if (woBoqQty.getQuantity() != null && woBoqQty.getRate() != null) {
												boqAmount += woBoqQty.getQuantity() * woBoqQty.getRate();

												try {
													systemGeneratedWorkInfoSet
															.add(woBoqQty.getBoq().getCategory().getName());
												} catch (Exception e) {
													e.printStackTrace();
												}
											}

										}
									}
									totalWorkorderAmount += boqAmount;
								}
							}
						}
					}
					if (siteConsultantWorkItems != null) {
						Double consultantAmount = 0.0;
						for (WorkorderConsultantWorkItemMapping consultantWorkItem : siteConsultantWorkItems) {
							if (consultantWorkItem.getWorkorderConsultantWork().getWorkorderId()
									.equals(woDTO.getId())) {
								consultantAmount += consultantWorkItem.getRate() * consultantWorkItem.getQuantity();
								try {

									systemGeneratedWorkInfoSet.add(consultantWorkItem.getCategoryDescription());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						}
						totalWorkorderAmount += consultantAmount;
					}

					if (siteTransportWorkItems != null) {

						Double transportAmount = 0.0;
						for (WorkorderTransportWorkItemMapping transportWorkItem : siteTransportWorkItems) {
							if (transportWorkItem.getWorkorderTransportWork().getWorkorderId().equals(woDTO.getId())) {
								transportAmount += transportWorkItem.getRate() * transportWorkItem.getQuantity();
								try {
									systemGeneratedWorkInfoSet.add(transportWorkItem.getDescription());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						totalWorkorderAmount += transportAmount;
					}

					if (siteHiringMachineItems != null) {
						Set<Long> distinctHmWorkItemIds = new HashSet<>();
						siteHiringMachineItems.forEach(hm -> distinctHmWorkItemIds.add(hm.getId()));
						List<WorkorderHiringMachineRateDetails> hmWorkItemsRateDetails = hmWorkDao
								.fetchHiringMachineItemRateDetailsByWorkItemIdsAndIsActive(distinctHmWorkItemIds, true);

						Double hmAmount = 0.0;

						for (WorkorderHiringMachineWorkItemMapping hmWorkItem : siteHiringMachineItems) {
							Double hmRate = 0.0;
							if (hmWorkItem.getWorkorderHmWork().getWorkorderId().equals(woDTO.getId())) {

								if (hmWorkItemsRateDetails != null && !hmWorkItemsRateDetails.isEmpty())
									for (WorkorderHiringMachineRateDetails rateDetails : hmWorkItemsRateDetails) {
										if (hmWorkItem.getRunningMode() != null
												&& rateDetails.getWoHiringMachineItemId().equals(hmWorkItem.getId())) {
											hmRate += rateDetails.getRate();

										}
									}

								hmAmount += hmRate * hmWorkItem.getQuantity() * hmWorkItem.getMachineCount();

								try {
									systemGeneratedWorkInfoSet.add(hmWorkItem.getMachineCategory().getName());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						totalWorkorderAmount += hmAmount;
					}
					try {
						systemGeneratedWorkInfo = systemGeneratedWorkInfoSet.stream().map(Object::toString)
								.collect(Collectors.joining(","));
					} catch (Exception e) {
						e.printStackTrace();
					}
					woDTO.setSystemGeneratedWorkInfo(systemGeneratedWorkInfo);
					woDTO.setAmount(totalWorkorderAmount);

				}
			}

			Site site = siteDao.fetchSiteById(search.getSiteId());
			int resultListTotalLength = obj.size();
			int minResult = 0;
			int maxResult = resultListTotalLength;
			boolean minimumIdxGreaterThanListLength = false;
			if (search.getPageNo() != null && search.getPageSize() != null && search.getPageNo().intValue() > 0
					&& search.getPageSize() > 0) {
				minResult = (search.getPageNo() - 1) * search.getPageSize();
				maxResult = ((search.getPageNo() - 1) * search.getPageSize()) + search.getPageSize();
				if (minResult > (resultListTotalLength - 1) && resultListTotalLength > 0) {
					minResult = resultListTotalLength - 1;
					minimumIdxGreaterThanListLength = true;
				}
				if (maxResult > resultListTotalLength)
					maxResult = resultListTotalLength;
				if (maxResult < 0)
					maxResult = 0;
				if (minResult < 0)
					minResult = 0;
				if (maxResult < minResult)
					minResult = maxResult;
			}
			WorkorderListRenderDTO result = new WorkorderListRenderDTO(obj.subList(minResult, maxResult),
					setObject.siteEntityToDto(site));

			Set<Long> distinctWorkorderRenderIds = new HashSet<>();
			if (result != null && result.getWorkorders() != null) {

				for (WorkorderListDTO woObj : result.getWorkorders()) {
					if (woObj.getCloseType() != null && woObj.getCloseType().equals(WorkorderCloseType.CANCEL)) {
						woObj.setDisableBillGeneration(true);
					} else if (woObj.getCloseType() != null
							&& woObj.getCloseType().equals(WorkorderCloseType.FULL_AND_FINAL)) {
						distinctWorkorderRenderIds.add(woObj.getId());
					}

				}

				// get entity state data
				List<EntityStateMapDTO> workorderCloseEntityStateMaps = engineService
						.getEntityStatesByCompanyId(EntitiesEnum.WORKORDER_CLOSE.getEntityId(), search.getCompanyId());

				Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

				for (EntityStateMapDTO esm : workorderCloseEntityStateMaps) {
					if (esm.getEntityId().equals(EntitiesEnum.WORKORDER_CLOSE.getEntityId())
							&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
						finalSuccessStateIds.add(esm.getStateId());
					}

				}

				List<WorkorderClose> workorderCloseList = workorderCloseDao
						.fetchWorkorderCloseListByWorkorderIdsAndStateIds(distinctWorkorderRenderIds,
								finalSuccessStateIds);

				if (workorderCloseList != null) {
					for (WorkorderListDTO woObj : result.getWorkorders()) {
						if (woObj.getCloseType() != null
								&& woObj.getCloseType().equals(WorkorderCloseType.FULL_AND_FINAL)) {
							woObj.setDisableBillGeneration(true);
							for (WorkorderClose workorderClose : workorderCloseList) {
								if (workorderClose.getWorkorderId().equals(woObj.getId())) {
									woObj.setDisableBillGeneration(false);
									break;
								}

							}
						}

					}
				}

			}

			PaginationDTO resultObj = new PaginationDTO(resultListTotalLength, result);
			if (minimumIdxGreaterThanListLength) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Invalid page no.");
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse renderWorkorderById(SearchDTO search) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			if (workorder == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), false, "Provide valid workorderId");

			WorkorderPrintDTO printObj = new WorkorderPrintDTO();

			Double workorderTotalAmount = 0.0;

//			Basic Detail Object

			WorkorderDTO woObj = setObject.workorderEntityToDto(workorder);
			if (woObj.getState().getId().equals(EngineStates.Draft.getValue())) {
				woObj.setUniqueNo(workorder.getState().getAlias() + "-" + woObj.getUniqueNo());
			}
			if (woObj.getWoContractor() != null) {
				if (woObj.getWoContractor().getBankDetail() != null) {
					ContractorBankDetailDTO contractorBankDetail = woObj.getWoContractor().getBankDetail();
					FileEntity cancelledCheque = workorder.getWoContractor().getBankDetail().getCancelChequeFile();
					String cancelChequeFullPath = null;
					if (contractorBankDetail.getCancelChequeFile() != null && cancelledCheque.getModule() != null) {
						String baseUrl = cancelledCheque.getModule().getBaseUrl();
						cancelChequeFullPath = baseUrl + contractorBankDetail.getCancelChequeFile().getPath();
					}
					contractorBankDetail.setCancelChequeFilePath(cancelChequeFullPath);
					woObj.getWoContractor().setBankDetail(contractorBankDetail);
				}
			}
			Set<CategoryItemDTO> categoriesDTO = new LinkedHashSet<>();
			if (woObj.getWoContractor() != null) {
				List<WorkorderCategoryMapping> wccms = woBasicDao.fetchWorkorderCategoriesByWorkorderId(woObj.getId());
				List<CategoryItem> categories = woBasicDao.fetchActiveCategoryItemsByCompanyId(search.getCompanyId());
				if (categories != null) {
					for (WorkorderCategoryMapping wccm : wccms) {
						for (CategoryItem category : categories) {
							if (wccm.getCategoryId().equals(category.getId())) {
								categoriesDTO.add(setObject.categoryItemEntityToDto(category));
								break;
							}
						}
					}
				}
				woObj.setCategories(categoriesDTO);
			}
			printObj.setBasicDetail(woObj);

//			BOQ work for Highway type workorders

			if (workorder.getType().getId().equals(WorkorderTypes.Highway.getId())) {
				WorkorderBoqWork boqWork = workorder.getBoqWork();
				if (boqWork == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), false,
							"Work details are not defined yet.");
				}
				List<WorkorderBoqWorkQtyMapping> boqWorkQtys = boqWork.getBoqWorkQty();
				List<BoqItem> boqs = boqDao.fetchBoqItems(search);
				List<MinimalWorkorderBoqLocationDTO> locations = new ArrayList<>();
				if (boqWork.getLocations() != null) {
					for (WorkorderBoqWorkLocation loc : boqWork.getLocations()) {

						MinimalObjectDTO fromChainageMinDTO = null;
						MinimalObjectDTO toChainageMinDTO = null;
						if (loc.getFromChainageId() != null && loc.getToChainageId() != null) {
							fromChainageMinDTO = new MinimalObjectDTO(loc.getFromChainage().getId(),
									loc.getFromChainage().getName());
							toChainageMinDTO = new MinimalObjectDTO(loc.getToChainage().getId(),
									loc.getToChainage().getName());
						}

						locations.add(new MinimalWorkorderBoqLocationDTO(loc.getId(), fromChainageMinDTO,
								toChainageMinDTO, null));
					}
				}

				List<WorkorderBoqWorkParentChildDTO> pcBoqs = setObject.renderParentChildWoBoqWork(boqWorkQtys, boqs);
				WorkorderBoqWorkRenderDTO renderDTO = new WorkorderBoqWorkRenderDTO(boqWork.getId(), workorder.getId(),
						locations, pcBoqs, boqWork.getWorkScope(), boqWork.getAnnexureNote());
				Double boqsTotalAmount = 0.0;
				if (pcBoqs != null) {
					for (WorkorderBoqWorkParentChildDTO obj : pcBoqs) {
						if (obj.getType().equals(ItemType.Boq.toString())) {
							boqsTotalAmount += obj.getAllocatedAmount();
						}
					}
				}
				workorderTotalAmount += boqsTotalAmount;
				renderDTO.setTotalAmount(boqsTotalAmount);
				printObj.setBoqWork(renderDTO);
			}

//			BOQ work for Structure type workorders

			if (workorder.getType().getId().equals(WorkorderTypes.Structure.getId())) {
				WorkorderBoqWork boqWork = workorder.getBoqWork();
				List<WorkorderBoqWorkQtyMapping> boqWorkQtys = boqWork.getBoqWorkQty();
				List<BoqItem> boqs = boqDao.fetchBoqItems(search);
				List<MinimalWorkorderBoqLocationDTO> locations = new ArrayList<>();
				if (boqWork.getLocations() != null) {
					for (WorkorderBoqWorkLocation loc : boqWork.getLocations()) {
						if (loc.getIsActive()) {
							MinimalObjectDTO fromChainageMinDTO = null;
							MinimalObjectDTO toChainageMinDTO = null;
							MinimalObjectDTO structureMinDTO = null;
							if (loc.getFromChainageId() != null && loc.getToChainageId() != null) {
								fromChainageMinDTO = new MinimalObjectDTO(loc.getFromChainage().getId(),
										loc.getFromChainage().getName());
								toChainageMinDTO = new MinimalObjectDTO(loc.getToChainage().getId(),
										loc.getToChainage().getName());
							}

							if (loc.getStructureId() != null && loc.getStructureId() != null) {

								structureMinDTO = new MinimalObjectDTO(loc.getStructureType().getId(),
										loc.getStructureType().getName(), loc.getStructureId(),
										loc.getStructure().getName(), null);
								structureMinDTO.setTypeId(loc.getStructureType().getId().intValue());
							} else {
								structureMinDTO = new MinimalObjectDTO(loc.getStructureType().getId(),
										loc.getStructureType().getName());
								structureMinDTO.setTypeId(loc.getStructureType().getId().intValue());
							}
							locations.add(new MinimalWorkorderBoqLocationDTO(loc.getId(), fromChainageMinDTO,
									toChainageMinDTO, structureMinDTO));
						}
					}
				}
				Double boqsTotalAmount = 0.0;
				List<WorkorderStructureTypeBoqResponse> structureBoqsList = new ArrayList<>();
				for (WorkorderBoqWorkLocation loc : boqWork.getLocations()) {
					if (loc.getIsActive()) {
						List<WorkorderBoqWorkQtyMapping> woStructureBoq = new ArrayList<>();
						for (WorkorderBoqWorkQtyMapping obj : boqWorkQtys) {
							if (obj.getIsActive() != null && obj.getIsActive() && obj.getStructureTypeId() != null
									&& obj.getStructureTypeId().equals(loc.getStructureTypeId())
									&& ((obj.getStructureId() == null && loc.getStructureId() == null)
											|| (obj.getStructureId() != null && loc.getStructureId() != null
													&& obj.getStructureId().equals(loc.getStructureId())))) {
								woStructureBoq.add(obj);
							}
						}
						if (woStructureBoq.size() == 0) {
							continue;
						}
						List<WorkorderBoqWorkParentChildDTO> psBoqs = setObject
								.renderParentChildWoBoqWork(woStructureBoq, boqs);
						double structureBoqAmount = 0.0;
						if (psBoqs != null) {
							for (WorkorderBoqWorkParentChildDTO obj : psBoqs) {
								if (obj.getType().equals(ItemType.Boq.toString())) {
									boqsTotalAmount += obj.getAllocatedAmount();
									structureBoqAmount += obj.getAllocatedAmount();
								}
							}
						}
						WorkorderStructureTypeBoqResponse structureBoqs = new WorkorderStructureTypeBoqResponse(
								loc.getStructureTypeId(), loc.getStructureType().getName(), loc.getStructureId(),
								loc.getStructure() != null ? loc.getStructure().getName() : null, psBoqs);
						structureBoqs.setAmount(structureBoqAmount);
						structureBoqsList.add(structureBoqs);
					}
				}
				WorkorderBoqWorkRenderDTO renderDTO = new WorkorderBoqWorkRenderDTO(boqWork.getId(), workorder.getId(),
						locations, null, boqWork.getWorkScope(), boqWork.getAnnexureNote());
				renderDTO.setStructureBoqs(structureBoqsList);
				workorderTotalAmount += boqsTotalAmount;
				renderDTO.setTotalAmount(boqsTotalAmount);
				printObj.setBoqWork(renderDTO);
			}

//			Workorder Consultant Work

			if (workorder.getType().getId().equals(WorkorderTypes.Consultancy.getId())) {
				WorkorderConsultantWork consultantWork = workorder.getConsultantWork();

				List<WorkorderConsultantWorkItemMapping> consultantWorkItems = consultantWorkDao
						.fetchWorkorderConsultantWorkItemsByConsultantWorkId(consultantWork.getId());

				List<WorkorderConsultantWorkCategoryWiseItemGetResponse> categoryWiseWorkItemsDto = new ArrayList<>();
				Set<String> distinctCategoryDescriptions = new LinkedHashSet<>();
				List<WorkorderConsultantWorkItemGetResponse> workItemsDto = new ArrayList<>();
				Double consultantWorkAmount = 0.0;
				if (consultantWorkItems != null) {

					for (WorkorderConsultantWorkItemMapping workItem : consultantWorkItems) {
						WorkorderConsultantWorkItemGetResponse workItemDto = setObject
								.workorderConsultantWorkItemMappingEntityToGetResponse(workItem);
						consultantWorkAmount += workItemDto.getAmount();
						workItemsDto.add(workItemDto);
					}

					boolean hasAddedNullCategoryItem = false;
					for (WorkorderConsultantWorkItemMapping workItem : consultantWorkItems) {
						if (workItem.getCategoryDescription() != null && !workItem.getCategoryDescription().isBlank()) {
							distinctCategoryDescriptions.add(workItem.getCategoryDescription());
						} else if (workItem.getCategoryDescription() == null && !hasAddedNullCategoryItem) {
							hasAddedNullCategoryItem = true;
						}
					}
					if (hasAddedNullCategoryItem) {
						categoryWiseWorkItemsDto
								.add(new WorkorderConsultantWorkCategoryWiseItemGetResponse(null, new ArrayList<>()));
					}
					for (String distinctCategoryDescription : distinctCategoryDescriptions) {
						categoryWiseWorkItemsDto.add(new WorkorderConsultantWorkCategoryWiseItemGetResponse(
								distinctCategoryDescription, new ArrayList<>()));
					}

					for (WorkorderConsultantWorkCategoryWiseItemGetResponse categoryWIList : categoryWiseWorkItemsDto) {

						for (WorkorderConsultantWorkItemGetResponse workItemDto : workItemsDto) {
							if (categoryWIList.getCategoryDescription() == null
									&& workItemDto.getCategoryDescription() == null) {
								categoryWIList.getConsultantWorkItems().add(workItemDto);
							} else if (categoryWIList.getCategoryDescription() != null
									&& workItemDto.getCategoryDescription() != null
									&& categoryWIList.getCategoryDescription()
											.equalsIgnoreCase(workItemDto.getCategoryDescription())) {
								categoryWIList.getConsultantWorkItems().add(workItemDto);
							}
						}

					}
				}

				WorkorderConsultantWorkGetResponse consultantWorkDto = new WorkorderConsultantWorkGetResponse(
						consultantWork.getId(), consultantWork.getWorkScope(), consultantWork.getAnnexureNote(),
						categoryWiseWorkItemsDto, consultantWorkAmount);
				workorderTotalAmount += consultantWorkAmount;
				printObj.setConsultantWork(consultantWorkDto);
			}

//			Workorder Transportation Work

			if (workorder.getType().getId().equals(WorkorderTypes.Transportation.getId())) {
				Double transportWorkAmount = 0.0;

				WorkorderTransportWork transportWork = workorder.getTransportWork();
				if (transportWork == null) {
					return new CustomResponse(Responses.NOT_FOUND.getCode(), null,
							"Transport work is not defined yet.");
				}
				List<WorkorderTransportWorkItemMapping> transportWorkItems = transportWorkDao
						.fetchWorkorderTransportWorkItemsByTransportWorkId(transportWork.getId());

				List<WorkorderTransportWorkItemGetResponse> workItemsDto = new ArrayList<>();
				if (transportWorkItems != null) {

					for (WorkorderTransportWorkItemMapping workItem : transportWorkItems) {
						WorkorderTransportWorkItemGetResponse workItemDto = setObject
								.workorderTransportWorkItemMappingEntityToGetResponse(workItem);
						transportWorkAmount += workItemDto.getAmount();
						workItemsDto.add(workItemDto);
					}

				}

				WorkorderTransportWorkGetResponse transportWorkDTO = new WorkorderTransportWorkGetResponse(
						transportWork.getId(), transportWork.getWorkScope(), transportWork.getAnnexureNote(),
						workItemsDto, transportWorkAmount);
				workorderTotalAmount += transportWorkAmount;
				printObj.setTransportWork(transportWorkDTO);
			}

// 			Workorder Hiring Machine Work

			if (workorder.getType().getId().equals(WorkorderTypes.Machine_Hiring.getId())) {
				WorkorderHiringMachineWork hmWork = workorder.getHiringMachineWork();
				List<WorkorderHiringMachineWorkItemMapping> hmWorkItems = hmWorkDao
						.fetchWorkorderHiringMachineWorkItemsByHmWorkId(hmWork.getId());

				List<WorkorderHiringMachineWorkItemGetResponse> workItemsDto = new ArrayList<>();
				Double hmWorkAmount = 0.0;
				if (hmWorkItems != null) {
					Set<Long> distinctHmWorkItemIds = new HashSet<>();
					hmWorkItems.forEach(obj -> distinctHmWorkItemIds.add(obj.getId()));
					List<WorkorderHiringMachineRateDetails> hmWorkItemsRateDetails = hmWorkDao
							.fetchHiringMachineItemRateDetailsByWorkItemIdsAndIsActive(distinctHmWorkItemIds, true);
					for (WorkorderHiringMachineWorkItemMapping workItem : hmWorkItems) {

						Double hmWorkRate = 0.0;
						List<WorkorderHiringMachineRateDetailsResponse> rateDetailsResponseObj = new ArrayList<>();

						if (hmWorkItemsRateDetails != null && !hmWorkItemsRateDetails.isEmpty())
							for (WorkorderHiringMachineRateDetails rateDetails : hmWorkItemsRateDetails) {
								if (workItem.getRunningMode() != null
										&& rateDetails.getWoHiringMachineItemId().equals(workItem.getId())) {
									hmWorkRate += rateDetails.getRate();

									rateDetailsResponseObj.add(new WorkorderHiringMachineRateDetailsResponse(
											rateDetails.getShift() != null ? rateDetails.getShift().getId() : null,
											rateDetails.getShift(), rateDetails.getRate()));
								}

							}

						WorkorderHiringMachineWorkItemGetResponse workItemDto = setObject
								.workorderHiringMachineWorkItemMappingEntityToGetResponse(workItem, hmWorkRate);
						workItemDto.setRate(hmWorkRate);
						workItemDto.setMachineRateDetails(rateDetailsResponseObj);
						hmWorkAmount += workItemDto.getAmount();
						workItemsDto.add(workItemDto);
					}
				}

				WorkorderHiringMachineWorkGetResponse hmWorkDto = new WorkorderHiringMachineWorkGetResponse(
						hmWork.getId(), hmWork.getWorkScope(), hmWork.getAnnexureNote(), workItemsDto,
						hmWork.getDieselRate(), hmWorkAmount);
				workorderTotalAmount += hmWorkAmount;
				printObj.setHireMachineWork(hmWorkDto);
			}

// 			Workorder Supply of Labour Work

			if (workorder.getType().getId().equals(WorkorderTypes.Labour_Supply.getId())) {
				WorkorderLabourWork labourWork = workorder.getLabourWork();

				List<WorkorderLabourWorkItemMapping> labourWorkItems = labourWorkDao
						.fetchWorkorderLabourWorkItemsByLabourWorkId(labourWork.getId());

				List<WorkorderLabourWorkItemGetResponse> workItemsDto = new ArrayList<>();
				Double labourWorkAmount = 0.0;
				if (labourWorkItems != null) {

					for (WorkorderLabourWorkItemMapping workItem : labourWorkItems) {
						WorkorderLabourWorkItemGetResponse workItemDto = setObject
								.workorderLabourWorkItemMappingEntityToGetResponse(workItem);
						labourWorkAmount += workItemDto.getAmount();
						workItemsDto.add(workItemDto);
					}

				}

				WorkorderLabourWorkGetResponse labourWorkDto = new WorkorderLabourWorkGetResponse(labourWork.getId(),
						labourWork.getWorkScope(), labourWork.getAnnexureNote(), workItemsDto, labourWorkAmount);
				workorderTotalAmount += labourWorkAmount;
				printObj.setLabourWork(labourWorkDto);
			}

//			Set workorder amount

			printObj.setAmount(workorderTotalAmount);

//			Workorder TNC Mapping

			List<WoTncMapping> entityWoMappedTncs = workorderDao.fetchWorkorderTnCByWorkorderId(workorder.getId());
			List<WoTncMappingDTO> woMappedTncs = new ArrayList<>();
			if (entityWoMappedTncs != null) {
				entityWoMappedTncs.forEach(item -> woMappedTncs.add(setObject.woTncMappingEntityToDto(item)));
			}

			List<WoTncType> tncTypes = woTncTypeDao.fetchActiveWoTncTypes(search);
			Set<WoTncMappingDTO> removeDups = new LinkedHashSet<>();
			if (woMappedTncs != null && woMappedTncs.size() > 0) {
				removeDups.addAll(woMappedTncs);
				woMappedTncs.clear();
				woMappedTncs.addAll(removeDups);
				for (WoTncMappingDTO obj : woMappedTncs) {
					List<WoTncFormulaVariableValueDTO> remDup = obj.getVariableValues();
					Set<WoTncFormulaVariableValueDTO> removeDups2 = new LinkedHashSet<>();
					removeDups2.addAll(remDup);
					remDup.clear();
					remDup.addAll(removeDups2);
					String descriptionToReplace = obj.getDescription();
					Map<String, String> replaceVariablesMap = new HashMap<>();
					if (remDup != null) {
						for (WoTncFormulaVariableValueDTO formulaVariableValue : remDup) {
							if (formulaVariableValue.getValueAtCreation() != null
									&& !formulaVariableValue.getValueAtCreation()) {

								try {
									Map<String, Object> sqlVariablesMap = new HashedMap<>();
//									Insert Variables
									sqlVariablesMap.put("siteId", workorder.getSiteId());
									sqlVariablesMap.put("companyId", search.getCompanyId());
									sqlVariablesMap.put("workorderId", search.getWorkorderId());

//									Update Query
									String sqlQuery = formulaVariableValue.getMasterVariable().getSqlQuery();
									sqlQuery = CustomSqlHelper.getUpdatedSqlExecutionString(sqlQuery, sqlVariablesMap);
									Double value = CustomSqlHelper.defaultDoubleVariableValue;
									try {
										value = (Double) woTncMapDao.executeSQLQuery(sqlQuery);
									} catch (Exception e) {
										e.printStackTrace();
									}

									String variableDecimalFormat = "%01d";
									if (value != null) {
										if (formulaVariableValue.getType().equals(VariableTypes.Double)) {
											variableDecimalFormat = "%.2f";
											String valueToReplace = (String.format(variableDecimalFormat, value) + " "
													+ (formulaVariableValue.getUnit() != null
															? formulaVariableValue.getUnit().getName()
															: ""));
											descriptionToReplace = descriptionToReplace.replace(
													SetObject.tncVariableDelimeter
															+ formulaVariableValue.getVariable().getName() + " ",
													valueToReplace + " ");
										} else {
											Integer intValue = value.intValue();
											String valueToReplace = (String.format(variableDecimalFormat, intValue)
													+ " "
													+ (formulaVariableValue.getUnit() != null
															? formulaVariableValue.getUnit().getName()
															: ""));
											descriptionToReplace = descriptionToReplace.replace(
													SetObject.tncVariableDelimeter
															+ formulaVariableValue.getVariable().getName() + " ",
													valueToReplace + " ");
										}
									} else {
										String valueToReplace = (" - " + (formulaVariableValue.getUnit() != null
												? formulaVariableValue.getUnit().getName()
												: ""));
										descriptionToReplace = descriptionToReplace.replace(
												SetObject.tncVariableDelimeter
														+ formulaVariableValue.getVariable().getName() + " ",
												valueToReplace + " ");
									}

								} catch (Exception e) {
									String valueToReplace = (" - " + (formulaVariableValue.getUnit() != null
											? formulaVariableValue.getUnit().getName()
											: ""));
									descriptionToReplace = descriptionToReplace.replace(
											SetObject.tncVariableDelimeter
													+ formulaVariableValue.getVariable().getName() + " ",
											valueToReplace + " ");
								}
							} else {
								replaceVariablesMap.put(
										(SetObject.tncVariableDelimeter + formulaVariableValue.getVariable().getName()),
										String.format("%.2f", formulaVariableValue.getValue()));
							}
						}
					}
					obj.setDescription(descriptionToReplace);
					obj.setReplacedDescription(obj.getDescription());
					if (obj.getReplacedDescription() != null) {
						String tempDescription = obj.getReplacedDescription();
						for (Map.Entry<String, String> entry : replaceVariablesMap.entrySet()) {
							tempDescription = tempDescription.replace(entry.getKey(), entry.getValue());
						}
						obj.setReplacedDescription(tempDescription);
					}
					obj.setVariableValues(remDup);
				}
				if (tncTypes != null) {
					List<MinimalObjectDTO> tncTypewiseTnc = new ArrayList<>();
					for (WoTncType tncType : tncTypes) {
						List<WoTncMappingDTO> relatedWoTnc = new ArrayList<>();
						for (WoTncMappingDTO woTncMap : woMappedTncs) {
							if (woTncMap.getTermAndCondition().getType() != null
									&& woTncMap.getTermAndCondition().getType().getId().equals(tncType.getId())) {
								relatedWoTnc.add(woTncMap);
							}
						}
						if (relatedWoTnc.size() > 0) {
							MinimalObjectDTO relType = new MinimalObjectDTO(tncType.getId(), tncType.getName());
							relType.setData(relatedWoTnc);
							tncTypewiseTnc.add(relType);
						}
					}
					printObj.setTncs(tncTypewiseTnc);
				}
			}
			Site site = siteDao.fetchSiteById(search.getSiteId());
			CompanyDTO company = setObject.companyEntityToDto(site.getCompany());
			SiteDTO siteDTO = setObject.siteEntityToDto(site);
			siteDTO.setCompany(company);
			printObj.setSite(siteDTO);
			return new CustomResponse(Responses.SUCCESS.getCode(), printObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderMaterialEquipmentById(SearchDTO search) {

		try {
			List<WorkorderMaterialConfigDTO> resultMaterialConfig = new ArrayList<>();
			List<WorkorderEquipmentIssueDTO> resultEquipmentIssue = new ArrayList<>();
			List<WorkorderMaterialConfig> materialConfig = woMaterialConfigDao.fetchWorkorderMaterialConfig(search);
			List<WorkorderEquipmentIssue> issuedEquipment = woEquipmentIssueDao.fetchWorkorderIssuedEquipments(search);
			if (materialConfig != null) {
				materialConfig
						.forEach(item -> resultMaterialConfig.add(setObject.workorderMaterialConfigEntityToDto(item)));
			}
			if (issuedEquipment != null) {
				issuedEquipment
						.forEach(item -> resultEquipmentIssue.add(setObject.workorderEquipmentIssueEntityToDto(item)));
			}
			WorkorderEquipmentMaterialRenderDTO result = new WorkorderEquipmentMaterialRenderDTO(resultMaterialConfig,
					resultEquipmentIssue);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse finishDraft(SearchDTO search) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			Boolean result = false;
			if (workorder.getState().getId().equals(EngineStates.Draft.getValue())) {
				if (workorder.getTermsAndConditions().size() > 0) {
					result = workorderDao.finishDraft(workorder.getId());
					if (result != null && result) {
						WorkorderStateTransitionMapping woStateTransitionMap = new WorkorderStateTransitionMapping(null,
								workorder.getId(), EngineStates.Wait_for_Approval.getValue(), null, true, new Date(),
								search.getUserId());
						workorderDao.mapWorkorderStateTransition(woStateTransitionMap);
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
	public CustomResponse changeWorkorderState(SearchDTO search) {

		try {
			if (search.getWorkorderId() == null || search.getStateId() == null || search.getUserId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			if (workorder.getState().getId().equals(search.getStateId())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Workorder is already in the same state.");
			}
			Integer stateId = workorder.getState().getId();
			User lastModifiedBy = userDao.fetchUserById(workorder.getModifiedBy());
			List<StateTransition> stateTransits = stateTransitionDao.fetchRoleStateTransitions(
					EntitiesEnum.WORKORDER.getEntityId(), search.getSiteId().intValue(), stateId,
					lastModifiedBy.getRole().getId());
			StateTransition stateTransit = null;
			if (stateTransits != null) {
				for (StateTransition st : stateTransits) {
					if (st.getNextRoleId().equals(search.getRoleId())
							&& st.getNextStateId().equals(search.getStateId())) {
						stateTransit = st;
						break;
					}
				}
			}
			if (stateTransit == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "No transition available to perform.");
			}
			workorder.setState(new EngineState(search.getStateId()));
			workorder.setModifiedOn(new Date());
			workorder.setModifiedBy(search.getUserId());
			Boolean result = workorderDao.forceUpdateWorkorder(workorder);
			if (result != null && result) {
				WorkorderStateTransitionMapping woStateTransitionMap = new WorkorderStateTransitionMapping(null,
						workorder.getId(), search.getStateId(), null, true, new Date(), search.getUserId());
				workorderDao.mapWorkorderStateTransition(woStateTransitionMap);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderNextStates(SearchDTO search) {

		try {
			if (search.getWorkorderId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide workorderId.");
			}
			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.WORKORDER.getEntityId(), search.getSiteId(), null, null, search.getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User woUser = userDao.fetchUserById(workorder.getModifiedBy());

			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.WORKORDER.getEntityId(), search.getCompanyId());

			Set<Integer> renewStateIds = new HashSet<>();

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.WORKORDER.getEntityId())
						&& esm.getStateActionId().equals(StateActions.Renew.getStateActionId())) {
					renewStateIds.add(esm.getStateId());
				}

			}

//			set next states
			if (transitions != null && woUser != null && woUser.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(workorder.getState().getId())
							&& st.getRoleId().equals(woUser.getRole().getId())
							&& st.getNextRoleId().equals(search.getRoleId())) {
						if (renewStateIds != null && renewStateIds.contains(st.getNextStateId())) {
							continue;
						}
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
	public CustomResponse getSitesWorkorders(SearchDTO search) {

		try {
			List<Site> sites = siteDao.fetchSites(search);
			Set<Long> distinctSiteIds = new HashSet<>();
			if (sites != null)
				sites.forEach(site -> distinctSiteIds.add(site.getId()));
			User user = userDao.fetchUserById(search.getUserId());
			EntityStateMap finalObj = stateTransitionDao.fetchEntityFinalState(EntitiesEnum.WORKORDER.getEntityId());
			List<WorkorderV2> sitesWorkorders = workorderDao.fetchWorkordersBySiteIds(distinctSiteIds);
			Set<Long> distinctWorkorderIds = new HashSet<>();
			if (sitesWorkorders != null)
				sitesWorkorders.forEach(workorder -> distinctWorkorderIds.add(workorder.getId()));
			List<WorkorderStateTransitionMapping> workordersStateMaps = workorderDao
					.fetchWorkorderStateMappingsByWorkorderIds(distinctWorkorderIds);
			search.setRoleId(user.getRole().getId());
			List<SiteWorkorderDTO> resultObj = new ArrayList<SiteWorkorderDTO>();
			List<StateTransitionDTO> allSiteTransitions = engineService
					.getStateTransition(EntitiesEnum.WORKORDER.getEntityId(), null, null, null, search.getCompanyId());
			if (sites != null) {
				for (Site site : sites) {
					search.setSiteId(site.getId());
					List<WorkorderV2> workorders = new ArrayList<>();
					if (sitesWorkorders != null) {
						for (WorkorderV2 woItr : sitesWorkorders) {
							if (woItr.getSiteId().equals(site.getId())) {
								workorders.add(woItr);
							}
						}
					}
					List<StateTransitionDTO> transitions = new ArrayList<>();
					if (allSiteTransitions != null) {
						for (StateTransitionDTO stDTO : allSiteTransitions) {
							if (stDTO.getSiteId() != null && stDTO.getSiteId().equals(site.getId().intValue())) {
								transitions.add(stDTO);
							}
						}
					}
					search.setWorkorderId(null);
					UserRole role = user.getRole();
					List<WorkorderListDTO> obj = new ArrayList<>();
					if (workorders != null) {
						for (WorkorderV2 workorder : workorders) {
							WorkorderListDTO woObj = setObject.workorderV2EntityToListDto(workorder);
							User woUser = workorder.getModifiedByUser();
							woObj.setModifiedByName(woUser.getName());

//							set edit mode
							woObj.setIsEditable(false);
							boolean woInDraft = false;
							if (woObj.getStateId().equals(EngineStates.Draft.getValue())) {
								woInDraft = true;
								woObj.setIsEditable(true);
								woObj.setUniqueNo(workorder.getState().getAlias() + "-" + woObj.getUniqueNo());
							}

//							set next states
							if (transitions != null && woUser != null && woUser.getRole() != null) {
								List<NextStateTransitDTO> nextStates = new ArrayList<>();
								for (StateTransitionDTO st : transitions) {
									if (st.getStateId().equals(woObj.getStateId())
											&& st.getRoleId().equals(woUser.getRole().getId())
											&& st.getNextRoleId().equals(role.getId())) {
										NextStateTransitDTO nst = new NextStateTransitDTO();
										nst.setNextStateId(st.getNextStateId());
										nst.setNextRoleId(st.getNextRoleId());
										nst.setIsButtonRequired(true);
										nextStates.add(nst);
									}
								}
								woObj.setNextStates(nextStates);
							}

//							set final state mode
							woObj.setInFinalState(false);
							if (finalObj != null && woObj.getStateId().equals(finalObj.getStateId())) {
								woObj.setInFinalState(true);
							}

//							add workorder in result list
							boolean hasAdded = false;

							if (woInDraft && workorder.getCreatedBy().equals(user.getId())) {
								obj.add(woObj);
								hasAdded = true;
							}

							if (!hasAdded && woObj.getNextStates() != null && woObj.getNextStates().size() > 0) {
								obj.add(woObj);
								hasAdded = true;
							}
							if (!hasAdded && !woInDraft) {
								List<WorkorderStateTransitionMapping> woStateMaps = new ArrayList<>();
								if (workordersStateMaps != null) {
									for (WorkorderStateTransitionMapping woStateItr : workordersStateMaps) {
										if (woStateItr.getWorkorderId().equals(workorder.getId())) {
											woStateMaps.add(woStateItr);
										}
									}
								}

								if (woStateMaps != null) {
									for (WorkorderStateTransitionMapping stm : woStateMaps) {
										if (stm.getCreatedBy().equals(user.getId())) {
											obj.add(woObj);
											hasAdded = true;
											break;
										}
									}
								}
							}
							if (!hasAdded && woObj.getInFinalState()) {
								obj.add(woObj);
								hasAdded = true;
							}

						}
					}

					SiteWorkorderDTO siteWorkorder = new SiteWorkorderDTO(site.getId(), obj.size());
					resultObj.add(siteWorkorder);
				}
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getFinalApprovedWorkorders(SearchDTO search) {

		try {
			EntityStateMapDTO entityFinalSuccessState = engineService.getEntityStateByFinalAndStateAction(
					EntitiesEnum.WORKORDER.getEntityId(), StateActions.FinalSuccess.getStateActionId(),
					search.getCompanyId());
			Integer entityFinalSuccessStateId = null;
			if (entityFinalSuccessState != null) {
				entityFinalSuccessStateId = entityFinalSuccessState.getStateId();
			}
			search.setStateId(entityFinalSuccessStateId);
			List<WorkorderV2> workorders = workorderDao.fetchWorkordersByStateId(search);
			List<WorkordersFinalApprovedResponse> resultList = new ArrayList<>();
			if (workorders != null) {
				for (WorkorderV2 wo : workorders) {
					if (wo.getEndDate() == null)
						resultList.add(new WorkordersFinalApprovedResponse(wo.getId(), wo.getSubject(),
								wo.getUniqueNo(), wo.getStartDate(), wo.getContractor().getName(),
								wo.getType().getName(), wo.getState().getName(), wo.getVersion()));

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

	@Override
	public CustomResponse getWorkorderBillInfoByWorkorderId(Long workorderId) {

		try {

			if (workorderId == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), "Please provide workorder id.",
						Responses.BAD_REQUEST.toString());

			WorkorderV3 workorder = workorderDao.fetchWorkorderV3ByWorkorderId(workorderId);

			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), "Workorder not found.",
						Responses.BAD_REQUEST.toString());

			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setWorkorderId(workorder.getId());
			searchDTO.setSiteId(workorder.getSiteId());

			List<Bill> billList = billDao.fetchBills(searchDTO);

			WorkorderHiringMachineWork hmWork = workorder.getHiringMachineWork();

			List<WorkorderHiringMachineWorkItemMapping> hmWorkItems = hmWorkDao
					.fetchWorkorderHiringMachineWorkItemsByHmWorkId(hmWork.getId());

			List<WorkorderMachineCategoryResponse> categoryResponses = new ArrayList<>();

			if (hmWorkItems != null && !hmWorkItems.isEmpty())
				hmWorkItems.stream()
						.forEach(o -> categoryResponses.add(new WorkorderMachineCategoryResponse(
								o.getWorkorderHmWorkId(), o.getId(), o.getMachineCategory().getId(),
								o.getMachineCategory().getName(), o.getMachineCount())));

			WorkorderBillInfoResponse resultObj = new WorkorderBillInfoResponse(workorder.getId(),
					workorder.getType().getId(), workorder.getStartDate(), workorder.getSystemBillStartDate(),
					workorder.getPreviousBillAmount(), workorder.getPreviousBillNo(),
					billList != null && !billList.isEmpty() ? false : false);

			resultObj.setMachinerCategories(categoryResponses);

			WorkorderBillInfo workorderBillInfoDbObj = workorderBillInfoDao.fetchWorkorderBillInfo(searchDTO);

			if (workorderBillInfoDbObj != null && workorderBillInfoDbObj.getId() != null) {
				searchDTO.setWorkorderBillInfoId(workorderBillInfoDbObj.getId());

				List<WorkorderBillInfoMachineMapping> billMachines = workorderBillInfoMachineMappingDao
						.fetchWorkorderBillInfoMachineMapping(workorderBillInfoDbObj.getId());

				if (billMachines != null && !billMachines.isEmpty()) {
					for (WorkorderMachineCategoryResponse catObj : categoryResponses) {
						List<WorkorderMachineMapResponse> machines = new ArrayList<>();
						billMachines.stream().filter(
								o -> catObj.getWoHiringMachineWorkItemId().equals(o.getWoHiringMachineWorkItemId()))
								.forEach(o -> machines.add(new WorkorderMachineMapResponse(o.getId(),
										o.getEquipment().getId(), o.getMachineType(), o.getEquipment().getAssetCode(),
										o.getEquipment().getRegNo(), o.getAmount())));
						catObj.setMachines(machines);
					}
				}

				List<BillDeductionResponseDto> billDeductionResponse = new ArrayList<>();

				resultObj.setTotal(workorderBillInfoDbObj.getTotal());
				resultObj.setTotalDeduction(workorderBillInfoDbObj.getTotalDeduction());
				resultObj.setPayableAmount(workorderBillInfoDbObj.getPayableAmount());
				resultObj.setApplicableIgst(workorderBillInfoDbObj.getApplicableIgst());
				resultObj.setIsIgstOnly(workorderBillInfoDbObj.getIsIgstOnly());

				List<BillDeductionMapping> billDeductionList = billDeductionMappingDao
						.fetchMappedBillDeductions(searchDTO);

				if (billDeductionList != null && !billDeductionList.isEmpty()) {
					billDeductionList.forEach(o -> billDeductionResponse
							.add(setObject.billDeductionMappingEntitytoBillDeductionResponseDto(o)));
					resultObj.setBillDeductionResponse(billDeductionResponse);
				}
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateWorkorderBillInfo(WorkorderBillInfoUpdateRequest requestDTO) {

		try {

			if (requestDTO.getId() == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), "Please provide workorder id.",
						Responses.BAD_REQUEST.toString());

			WorkorderV3 workorder = workorderDao.fetchWorkorderV3ByWorkorderId(requestDTO.getId());

			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), "Workorder not found.",
						Responses.BAD_REQUEST.toString());

			if (DateUtil.dateWithoutTime(requestDTO.getSystemBillStartDate())
					.before(DateUtil.dateWithoutTime(workorder.getStartDate()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(),
						"System bill start date cannot be less than workorder start date.",
						Responses.BAD_REQUEST.toString());
			}
			if (DateUtil.dateWithoutTime(requestDTO.getSystemBillStartDate())
					.after(DateUtil.dateWithoutTime(new Date()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(),
						"System bill start date cannot be greater than today's date.",
						Responses.BAD_REQUEST.toString());
			}

			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setWorkorderId(requestDTO.getId());
			searchDTO.setSiteId(requestDTO.getSiteId());

			List<Bill> billList = billDao.fetchBills(searchDTO);

			if (billList != null && !billList.isEmpty()
					&& requestDTO.getSystemBillStartDate().after(billList.get(0).getFromDate())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Bill already generated before system start Date.");
			}

			if (billList != null && !billList.isEmpty()
					&& requestDTO.getPreviousBillNo() >= billList.get(0).getBillNo()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide proper bill No. sequence");
			}

//			if (billList != null && !billList.isEmpty()) {
//				return new CustomResponse(Responses.BAD_REQUEST.getCode(),
//						"Bill already generated with this workorder id.", Responses.BAD_REQUEST.toString());
//			}

			workorder.setModifiedOn(new Date());
			workorder.setModifiedBy(requestDTO.getUserDetail().getId());
			workorder.setPreviousBillAmount(requestDTO.getPreviousBillAmount());
			workorder.setPreviousBillNo(requestDTO.getPreviousBillNo());
			workorder.setSystemBillStartDate(requestDTO.getSystemBillStartDate());

			Boolean isUpdate = workorderDao.forceUpdateWorkorderBillInfo(workorder);

			WorkorderBillInfo workorderBillInfo = setObject.workorderBillInfoUpdateRequestDtoToEntity(requestDTO);

			Integer workorderBillInfoId = workorderBillInfoDao.saveWorkorderBillInfo(workorderBillInfo);

			List<WorkorderBillInfoMachineMapping> billMachines = workorderBillInfoMachineMappingDao
					.fetchWorkorderBillInfoMachineMapping(workorderBillInfoId);
			if (billMachines != null) {
				Map<Long, WorkorderBillInfoMachineMapping> collect = billMachines.stream()
						.collect(Collectors.toMap(o -> o.getId(), o -> o));
				Set<Long> savedBillMachineReqIds = new HashSet<>();
				for (WorkoderHireMachineRequest bmmrObj : requestDTO.getHireMachineRequest()) {
					if (bmmrObj.getId() == null) {
						WorkorderBillInfoMachineMapping bcmToSave = new WorkorderBillInfoMachineMapping(null,
								workorderBillInfoId, bmmrObj.getMachineType(), bmmrObj.getMachineId(),
								bmmrObj.getWoHiringMachineWorkItemId(), bmmrObj.getAmount(), true, new Date(),
								requestDTO.getUserDetail().getId(), new Date(), requestDTO.getUserDetail().getId());
						workorderBillInfoMachineMappingDao.saveWorkorderBillInfoMachineMapping(bcmToSave);

					} else {
						savedBillMachineReqIds.add(bmmrObj.getId());
						WorkorderBillInfoMachineMapping bcmToUpdate = new WorkorderBillInfoMachineMapping(
								bmmrObj.getId(), workorderBillInfoId, bmmrObj.getMachineType(), bmmrObj.getMachineId(),
								bmmrObj.getWoHiringMachineWorkItemId(), bmmrObj.getAmount(), true,
								collect.get(bmmrObj.getId()).getCreatedOn(),
								collect.get(bmmrObj.getId()).getCreatedBy(), new Date(),
								requestDTO.getUserDetail().getId());
						workorderBillInfoMachineMappingDao.forceUpdateWorkorderBillInfoMachineMapping(bcmToUpdate);
					}
				}
				for (WorkorderBillInfoMachineMapping bcm : billMachines) {
					if (!savedBillMachineReqIds.contains(bcm.getId())) {
						bcm.setIsActive(false);
						bcm.setModifiedBy(requestDTO.getUserDetail().getId());
						bcm.setModifiedOn(new Date());
						workorderBillInfoMachineMappingDao.forceUpdateWorkorderBillInfoMachineMapping(bcm);
					}

				}
			} else {
				for (WorkoderHireMachineRequest bmmrObj : requestDTO.getHireMachineRequest()) {

					WorkorderBillInfoMachineMapping bcmToSave = new WorkorderBillInfoMachineMapping(null,
							workorderBillInfoId, bmmrObj.getMachineType(), bmmrObj.getMachineId(),
							bmmrObj.getWoHiringMachineWorkItemId(), bmmrObj.getAmount(), true, new Date(),
							requestDTO.getUserDetail().getId(), new Date(), requestDTO.getUserDetail().getId());
					workorderBillInfoMachineMappingDao.saveWorkorderBillInfoMachineMapping(bcmToSave);
				}
			}

			CustomResponse addOrUpdateBillDeductionMap = billDeductionMappingService
					.addOrUpdateBillDeductionMap(new BillDeductionMappingAddUpdateRequest(workorderBillInfoId,
							requestDTO.getBillDeductionRequest(), requestDTO.getUserDetail()));

			return new CustomResponse(Responses.SUCCESS.getCode(), isUpdate, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public List<WorkorderListDTO> getWorkordersWithoutPagination(SearchDTO search) {
		try {

			List<Workorder> workorders = workorderDao.fetchWorkorders(search);
			Set<Long> distinctWorkorderIds = new HashSet<>();
			if (workorders != null)
				workorders.forEach(workorder -> distinctWorkorderIds.add(workorder.getId()));

			List<WorkorderConsultantWorkItemMapping> siteConsultantWorkItems = consultantWorkDao
					.fetchWorkorderConsultantWorkItemsBySiteId(search.getSiteId());

			List<WorkorderTransportWorkItemMapping> siteTransportWorkItems = transportWorkDao
					.fetchWorkorderTransportWorkItemsBySiteId(search.getSiteId());

			List<WorkorderHiringMachineWorkItemMapping> siteHiringMachineItems = hmWorkDao
					.fetchWorkorderHiringMachineWorkItemsBySiteId(search.getSiteId());

			User user = userDao.fetchUserById(search.getUserId());

			search.setWorkorderId(null);
			List<Bill> bills = billDao.fetchBills(search);
			Set<Long> distinctBillIds = new HashSet<>();
			if (bills != null)
				bills.forEach(bill -> distinctBillIds.add(bill.getId()));

			Set<Long> userBillIds = new LinkedHashSet<>();
			UserRole role = user.getRole();

			List<StateTransitionDTO> billTransitions = engineService.getStateTransition(EntitiesEnum.BILL.getEntityId(),
					search.getSiteId(), null, null, search.getCompanyId());
			EntityStateMap billFinalObj = stateTransitionDao.fetchEntityFinalState(EntitiesEnum.BILL.getEntityId());
			List<BillStateTransitionMapping> billsStateMaps = billDao.fetchBillStateMappingsByBillIds(distinctBillIds);

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.WORKORDER.getEntityId(), search.getSiteId(), null, null, search.getCompanyId());
			EntityStateMap finalObj = stateTransitionDao.fetchEntityFinalState(EntitiesEnum.WORKORDER.getEntityId());
			List<WorkorderStateTransitionMapping> workordersStateMaps = workorderDao
					.fetchWorkorderStateMappingsByWorkorderIds(distinctWorkorderIds);

//			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.WORKORDER.getEntityId(), user.getCompanyId());

			List<WorkorderListDTO> obj = new ArrayList<>();
			if (workorders != null) {
				for (Workorder workorder : workorders) {
					WorkorderListDTO woObj = setObject.workorderEntityToListDto(workorder);
					User woUser = workorder.getModifiedByUser();
					woObj.setModifiedByName(woUser.getName());

//					set bill count
					int billCount = 0;
					if (bills != null && search.getIsApproved() != null && search.getIsApproved()) {
						for (Bill bill : bills) {
							if (bill.getWorkorderId().equals(workorder.getId())) {
								User billUser = bill.getModifiedByUser();
								boolean billInDraft = false;
								if (bill.getState().getId().equals(EngineStates.Draft.getValue())) {
									billInDraft = true;
								}

//								set next states

								List<NextStateTransitDTO> nextStates = new ArrayList<>();
								if (billTransitions != null && billUser != null && billUser.getRole() != null) {
									for (StateTransitionDTO st : billTransitions) {
										if (st.getStateId().equals(bill.getState().getId())
												&& st.getRoleId().equals(billUser.getRole().getId())
												&& st.getNextRoleId().equals(search.getRoleId())) {
											NextStateTransitDTO nst = new NextStateTransitDTO();
											nst.setNextStateId(st.getNextStateId());
											nst.setNextRoleId(st.getNextRoleId());
											nst.setIsButtonRequired(true);
											nst.setNextRoleName(st.getNextRole().getName());
											nst.setNextStateName(st.getNextState().getName());
											nst.setButtonText(st.getNextState().getButtonText());
											nst.setIsButtonRequired(
													st.getNextState().getButtonText() != null ? true : false);
											nst.setNextStateAlias(st.getNextState().getAlias());
											nextStates.add(nst);
										}
									}
								}

//								set final state mode
								boolean inFinalState = false;
								if (billFinalObj != null && bill.getState().getId().equals(billFinalObj.getStateId())) {
									inFinalState = true;
								}

//								add bill in result list
								boolean hasAdded = false;

								if (billInDraft && bill.getCreatedBy().equals(search.getUserId())) {
									billCount++;
									userBillIds.add(bill.getId());
									hasAdded = true;
								}

								if (!hasAdded && nextStates != null && nextStates.size() > 0) {
									billCount++;
									userBillIds.add(bill.getId());
									hasAdded = true;
								}
								if (!hasAdded && !billInDraft) {

									List<BillStateTransitionMapping> billStateMaps = new ArrayList<>();
									if (billsStateMaps != null)
										for (BillStateTransitionMapping billStateItr : billStateMaps) {
											if (billStateItr.getBillId().equals(bill.getId())) {
												billStateMaps.add(billStateItr);
											}
										}

									if (billStateMaps != null) {
										for (BillStateTransitionMapping stm : billStateMaps) {
											if (stm.getCreatedBy().equals(search.getUserId())) {
												billCount++;
												userBillIds.add(bill.getId());
												hasAdded = true;
												break;
											}
										}
									}
								}
								if (!hasAdded && inFinalState) {
									billCount++;
									userBillIds.add(bill.getId());
									hasAdded = true;
								}
							}
						}
					}
					woObj.setBillCount(billCount);

//					set edit mode
					woObj.setIsEditable(false);
					boolean woInDraft = false;
					if (woObj.getStateId().equals(EngineStates.Draft.getValue())) {
						woInDraft = true;
						woObj.setIsEditable(true);
						woObj.setUniqueNo(workorder.getState().getAlias() + "-" + woObj.getUniqueNo());
					}

					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.WORKORDER.getEntityId())
								&& esm.getStateId().equals(woObj.getStateId())) {
							if (esm.getIsEditable() != null) {
								woObj.setIsEditable(esm.getIsEditable());
							}

						}

					}

//					set next states
					if (transitions != null && woUser != null && woUser.getRole() != null) {
						List<NextStateTransitDTO> nextStates = new ArrayList<>();
						for (StateTransitionDTO st : transitions) {
							if (st.getStateId().equals(woObj.getStateId())
									&& st.getRoleId().equals(woUser.getRole().getId())
									&& st.getNextRoleId().equals(role.getId())) {
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
						woObj.setNextStates(nextStates);
					}

//					set final state mode
					woObj.setInFinalState(false);
					if (finalObj != null && woObj.getStateId().equals(finalObj.getStateId())) {
						woObj.setInFinalState(true);
					}

//					add workorder in result list
					boolean hasAdded = false;

					if (woInDraft && workorder.getCreatedBy().equals(user.getId())) {
						obj.add(woObj);
						hasAdded = true;
					}

					if (!hasAdded && woObj.getNextStates() != null && woObj.getNextStates().size() > 0) {
						obj.add(woObj);
						hasAdded = true;
					}
					if (!hasAdded && !woInDraft) {
						List<WorkorderStateTransitionMapping> woStateMaps = new ArrayList<>();
						if (workordersStateMaps != null) {
							for (WorkorderStateTransitionMapping woStateItr : workordersStateMaps) {
								if (woStateItr.getWorkorderId().equals(workorder.getId())) {
									woStateMaps.add(woStateItr);
								}
							}
						}

						if (woStateMaps != null) {
							for (WorkorderStateTransitionMapping stm : woStateMaps) {
								if (stm.getCreatedBy().equals(user.getId())) {
									obj.add(woObj);
									hasAdded = true;
									break;
								}
							}
						}
					}
					if (!hasAdded && woObj.getInFinalState()) {
						obj.add(woObj);
						hasAdded = true;
					}

				}
			}

			HashSet<String> systemGeneratedWorkInfoSet = new HashSet<>();
			String systemGeneratedWorkInfo = null;

			if (obj != null) {
				for (WorkorderListDTO woDTO : obj) {

//					set workorder amount

					Double totalWorkorderAmount = 0.0;
					systemGeneratedWorkInfoSet.clear();
					systemGeneratedWorkInfo = "";

					for (Workorder workorder : workorders) {
						if (woDTO.getId().equals(workorder.getId())) {
							WorkorderBoqWork woBoqWork = workorder.getBoqWork();
							if (woBoqWork != null) {
								List<WorkorderBoqWorkQtyMapping> woBoqQtys = woBoqWork.getBoqWorkQty();
								if (woBoqQtys != null) {
									Double boqAmount = 0.0;
									for (WorkorderBoqWorkQtyMapping woBoqQty : woBoqQtys) {
										if (woBoqQty.getIsActive() == null || woBoqQty.getIsActive()) {
											if (woBoqQty.getQuantity() != null && woBoqQty.getRate() != null) {
												boqAmount += woBoqQty.getQuantity() * woBoqQty.getRate();

												try {
													systemGeneratedWorkInfoSet
															.add(woBoqQty.getBoq().getCategory().getName());
												} catch (Exception e) {
													e.printStackTrace();
												}
											}

										}
									}
									totalWorkorderAmount += boqAmount;
								}
							}
						}
					}
					if (siteConsultantWorkItems != null) {
						Double consultantAmount = 0.0;
						for (WorkorderConsultantWorkItemMapping consultantWorkItem : siteConsultantWorkItems) {
							if (consultantWorkItem.getWorkorderConsultantWork().getWorkorderId()
									.equals(woDTO.getId())) {
								consultantAmount += consultantWorkItem.getRate() * consultantWorkItem.getQuantity();
								try {

									systemGeneratedWorkInfoSet.add(consultantWorkItem.getCategoryDescription());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						}
						totalWorkorderAmount += consultantAmount;
					}

					if (siteTransportWorkItems != null) {

						Double transportAmount = 0.0;
						for (WorkorderTransportWorkItemMapping transportWorkItem : siteTransportWorkItems) {
							if (transportWorkItem.getWorkorderTransportWork().getWorkorderId().equals(woDTO.getId())) {
								transportAmount += transportWorkItem.getRate() * transportWorkItem.getQuantity();
								try {
									systemGeneratedWorkInfoSet.add(transportWorkItem.getDescription());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						totalWorkorderAmount += transportAmount;
					}

					if (siteHiringMachineItems != null) {
						Set<Long> distinctHmWorkItemIds = new HashSet<>();
						siteHiringMachineItems.forEach(hm -> distinctHmWorkItemIds.add(hm.getId()));
						List<WorkorderHiringMachineRateDetails> hmWorkItemsRateDetails = hmWorkDao
								.fetchHiringMachineItemRateDetailsByWorkItemIdsAndIsActive(distinctHmWorkItemIds, true);

						Double hmAmount = 0.0;
						Double hmRate = 0.0;
						for (WorkorderHiringMachineWorkItemMapping hmWorkItem : siteHiringMachineItems) {
							if (hmWorkItem.getWorkorderHmWork().getWorkorderId().equals(woDTO.getId())) {

								if (hmWorkItemsRateDetails != null && !hmWorkItemsRateDetails.isEmpty())
									for (WorkorderHiringMachineRateDetails rateDetails : hmWorkItemsRateDetails) {
										if (hmWorkItem.getRunningMode() != null
												&& rateDetails.getWoHiringMachineItemId().equals(hmWorkItem.getId())) {
											hmRate += rateDetails.getRate();

										}
									}

								hmAmount += hmRate * hmWorkItem.getQuantity();

								try {
									systemGeneratedWorkInfoSet.add(hmWorkItem.getMachineCategory().getName());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						totalWorkorderAmount += hmAmount;
					}
					try {
						systemGeneratedWorkInfo = systemGeneratedWorkInfoSet.stream().map(Object::toString)
								.collect(Collectors.joining(","));
					} catch (Exception e) {
						e.printStackTrace();
					}
					woDTO.setSystemGeneratedWorkInfo(systemGeneratedWorkInfo);
					woDTO.setAmount(totalWorkorderAmount);

				}
			}

			return obj;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return null;
		}

	}

	@Override
	public CustomResponse exportWorkOrderExcel(SearchDTO search) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
			List<WorkorderListDTO> resultObj = getWorkordersWithoutPagination(search);
			Site site = siteDao.fetchSiteById(search.getSiteId());
			System.out.println("\n\n\n " + site.getName() + "\n\n\n");
			Sheet sheet = workbook.createSheet("Workorder - " + site.getName());

			int rowIndex = 0;

			CellStyle boldCellStyle = workbook.createCellStyle();
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);
			boldFont.setFontHeightInPoints((short) 12);
			boldCellStyle.setFont(boldFont);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());
			headerFont.setFontHeightInPoints((short) 7);

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFont(boldFont);
			headerCellStyle.setWrapText(true);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

			CellStyle headerCellStyle1 = workbook.createCellStyle();
			headerCellStyle1.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			headerCellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle1.setFont(boldFont);
			headerCellStyle1.setWrapText(true);
			headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);

			Row row = sheet.createRow(rowIndex++);
			row.setHeight((short) 800);
			XSSFCell c01 = (XSSFCell) row.createCell(0);
			c01.setCellValue("Workorder - " + site.getName());
			c01.setCellStyle(headerCellStyle);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

			row = sheet.createRow(rowIndex++);
			if (rowIndex > 0) {
				sheet.setDefaultColumnWidth(20);
				sheet.setColumnWidth(0, 2000);
				sheet.setColumnWidth(1, 7500);
				sheet.setColumnWidth(2, 7500);
				sheet.setColumnWidth(3, 7500);
				sheet.setColumnWidth(4, 7500);
				sheet.setColumnWidth(5, 4500);
				sheet.setColumnWidth(6, 7500);
				sheet.setColumnWidth(7, 7500);
				sheet.setColumnWidth(8, 4500);

			}

			List<String> headerColumns = new LinkedList<String>();

			headerColumns.add("Sr. No.");
			headerColumns.add("WorkOrder Type");
			headerColumns.add("WorkOrder Number");
			headerColumns.add("Contractor Name");
			headerColumns.add("WorkInfo");
			headerColumns.add("State");
			headerColumns.add("Last Updated By ");
			headerColumns.add("Last Updated On");
			headerColumns.add("Amount");

			for (int i = 0; i < headerColumns.size(); i++) {

				XSSFCell cell = (XSSFCell) row.createCell(i);
				cell.setCellValue(headerColumns.get(i));
				cell.setCellStyle(headerCellStyle1);

			}

			DateFormat date = new SimpleDateFormat("dd/MMM/yyyy");
			for (WorkorderListDTO woList : resultObj) {
				row = sheet.createRow(rowIndex++);
				Cell cell0 = row.createCell(0);
				cell0.setCellValue(rowIndex - 2);
				row.createCell(1).setCellValue(woList.getTypeName() != null ? woList.getTypeName() : "-");
				row.createCell(2).setCellValue(woList.getUniqueNo() != null ? woList.getUniqueNo() : "-");
				row.createCell(3).setCellValue(woList.getContractor() != null ? woList.getContractor().getName() : "-");
				row.createCell(4).setCellValue(
						woList.getSystemGeneratedWorkInfo() != null ? woList.getSystemGeneratedWorkInfo() : "-");
				row.createCell(5).setCellValue(woList.getStateName() != null ? woList.getStateName() : "-");
				row.createCell(6).setCellValue(woList.getModifiedByName() != null ? woList.getModifiedByName() : "-");
				row.createCell(7).setCellValue(
						date.format(woList.getModifiedOn()) != null ? date.format(woList.getModifiedOn()) : "-");
				row.createCell(8)
						.setCellValue(woList.getAmount().toString() != null ? woList.getAmount().toString() : "-");
			}

			workbook.write(bos);
			byte[] bytes = bos.toByteArray();
			return new CustomResponse(Responses.SUCCESS.getCode(), Base64.getEncoder().encodeToString(bytes),
					Responses.SUCCESS.name());

		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null, Responses.PROBLEM_OCCURRED.name());
		}
	}

	@Override
	public void triggerWorkorderBeforeExpiryMail() {
		try {

			List<Company> companyList = companyDao.fetchCompanyList();

			for (Company company : companyList) {

				Date date = DateUtil.dateWithoutTime(new Date());
				Set<Date> distinctDateList = new HashSet<>();
				if (company.getCompanyConfig() != null && company.getCompanyConfig().getEnableWorkorderExpiryMail()
						&& company.getCompanyConfig().getDaysBeforeMailRequiredForWorkorderExpiry() != null) {

					for (Integer days : company.getCompanyConfig().getDaysBeforeMailRequiredForWorkorderExpiry()) {
						distinctDateList.add(DateUtil.dateAfterAddingDays(date, days));
					}
				} else {
					continue;
				}

//				 get entity state data
				List<EntityStateMapDTO> entityStateMaps = engineService
						.getEntityStatesByCompanyId(EntitiesEnum.WORKORDER.getEntityId(), company.getId());

				Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

				for (EntityStateMapDTO esm : entityStateMaps) {

					if (esm.getEntityId().equals(EntitiesEnum.WORKORDER.getEntityId())
							&& esm.getStateActionId().equals(StateActions.FinalSuccess.getStateActionId())) {
						finalSuccessStateIds.add(esm.getStateId());
					}

				}

				List<Workorder> workorderList = workorderDao.fetchWorkordersByEndDatesAndStateIds(finalSuccessStateIds,
						distinctDateList, company.getId());

				if (workorderList != null && !workorderList.isEmpty()) {
					for (Workorder workorder : workorderList) {

						Map<String, Object> queryVariableValue = new HashMap<>();
						queryVariableValue.put("workorder_id", workorder.getId());
						queryVariableValue.put("site_id", workorder.getSiteId());
						queryVariableValue.put("company_id", workorder.getCompanyId());

						notificatorService.sendNotificationV2(EntitiesEnum.WORKORDER.getEntityId(),
								workorder.getEndDate().before(date) ? EngineStates.Expired.getValue()
										: workorder.getState().getId(),
								workorder.getId(), workorder.getSiteId().intValue(), workorder.getCompanyId(),
								SetObject.billingModuleId, queryVariableValue, null);

					}
				}

			}

		} catch (Exception e) {
			LOGGER.info(e.toString());
			LOGGER.info("----WORKORDER BEFORE EXPIRY MAIL NOT SENT----");
		}
	}

	@Override
	public CustomResponse renewWorkorder(WorkorderRenewRequest requestDTO) {

		try {

			if (requestDTO.getId() == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Please provide workorder id.");

			WorkorderV3 workorder = workorderDao.fetchWorkorderV3ByWorkorderId(requestDTO.getId());

			if (workorder == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Workorder not found.");

			if (workorder.getEndDate() != null && DateUtil.dateWithoutTime(requestDTO.getEndDate())
					.before(DateUtil.dateWithoutTime(workorder.getEndDate()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid end date.");
			}

			if (workorder.getStartDate() != null && DateUtil.dateWithoutTime(requestDTO.getEndDate())
					.before(DateUtil.dateWithoutTime(workorder.getStartDate()))) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid end date.");
			}

			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setWorkorderId(requestDTO.getId());
			searchDTO.setSiteId(requestDTO.getSiteId());

			List<Bill> billList = billDao.fetchBills(searchDTO);

			if (billList != null && !billList.isEmpty()) {
				for (Bill bill : billList) {
					if (DateUtil.dateWithoutTime(requestDTO.getEndDate())
							.before(DateUtil.dateWithoutTime(bill.getToDate()))) {
						return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
								"Bill already generated for this date.");
					}
				}
			}

//			 get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.WORKORDER.getEntityId(), requestDTO.getUserDetail().getCompanyId());

			Integer previousStateId = workorder.getStateId();

			Integer renewStateId = null;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.WORKORDER.getEntityId())
						&& esm.getStateActionId().equals(StateActions.Renew.getStateActionId())) {
					renewStateId = esm.getStateId();
				}

			}

			if (renewStateId == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "No renew state found.");
			}

			String systemGeneratedRemark = "Workorder end date has been changed from " + workorder.getEndDate() + " to "
					+ requestDTO.getEndDate();

			workorder.setEndDate(requestDTO.getEndDate());
			workorder.setModifiedOn(new Date());
			workorder.setModifiedBy(requestDTO.getUserDetail().getId());

			Boolean hasUpdated = workorderDao.forceUpdateWorkorderV3(workorder);

			if (hasUpdated) {
				WorkorderStateTransitionMapping woStateTransitionMap = new WorkorderStateTransitionMapping(null,
						workorder.getId(), renewStateId, systemGeneratedRemark, true, new Date(),
						requestDTO.getUserDetail().getId());
				workorderDao.mapWorkorderStateTransition(woStateTransitionMap);

				woStateTransitionMap = new WorkorderStateTransitionMapping(null, workorder.getId(), previousStateId,
						null, true, new Date(), requestDTO.getUserDetail().getId());
				workorderDao.mapWorkorderStateTransition(woStateTransitionMap);
			}

			Map<String, Object> queryVariableValue = new HashMap<>();
			queryVariableValue.put("workorder_id", workorder.getId());
			queryVariableValue.put("site_id", workorder.getSiteId());
			queryVariableValue.put("company_id", workorder.getCompanyId());

			notificatorService.sendNotificationV2(EntitiesEnum.WORKORDER.getEntityId(), renewStateId, workorder.getId(),
					workorder.getSiteId().intValue(), workorder.getCompanyId(), SetObject.billingModuleId,
					queryVariableValue, null);

			return new CustomResponse(Responses.SUCCESS.getCode(), hasUpdated, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
