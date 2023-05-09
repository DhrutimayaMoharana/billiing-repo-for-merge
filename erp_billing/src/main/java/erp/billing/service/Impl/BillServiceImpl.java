package erp.billing.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.billing.dao.BillBoqItemDao;
import erp.billing.dao.BillDao;
import erp.billing.dao.BillDeductionMappingDao;
import erp.billing.dao.BillMachineDao;
import erp.billing.dao.BillPayMilestoneDao;
import erp.billing.dao.DebitNoteDao;
import erp.billing.dao.DieselRateDao;
import erp.billing.dao.DprHireMachineDao;
import erp.billing.dao.IssueSlipItemDao;
import erp.billing.dao.MachineDao;
import erp.billing.dao.SiteDao;
import erp.billing.dao.UserDao;
import erp.billing.dao.WorkorderBasicDetailDao;
import erp.billing.dao.WorkorderBillInfoDao;
import erp.billing.dao.WorkorderBillInfoMachineMappingDao;
import erp.billing.dao.WorkorderBoqWorkDao;
import erp.billing.dao.WorkorderConsultantWorkDao;
import erp.billing.dao.WorkorderDao;
import erp.billing.dao.WorkorderHireMachineryDao;
import erp.billing.dao.WorkorderPayMilestoneDao;
import erp.billing.dto.BillBoqItemDTO;
import erp.billing.dto.BillBoqQuantityItemDTO;
import erp.billing.dto.BillDTO;
import erp.billing.dto.BillPrintBoqResponseDTO;
import erp.billing.dto.CategoryItemDTO;
import erp.billing.dto.CompanyDTO;
import erp.billing.dto.ContractorBankDetailDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.DebitNoteItemDTO;
import erp.billing.dto.EngineStateDTO;
import erp.billing.dto.EntityStateMapDTO;
import erp.billing.dto.NextStateTransitDTO;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.SiteDTO;
import erp.billing.dto.StateTransitionDTO;
import erp.billing.dto.WorkorderDTO;
import erp.billing.dto.request.BillMachineMapRequest;
import erp.billing.dto.response.BillAndItemsDTO;
import erp.billing.dto.response.BillDeductionResponseDto;
import erp.billing.dto.response.BillItemResponseDTO;
import erp.billing.dto.response.BillListResponse;
import erp.billing.dto.response.BillMachineCategoryResponse;
import erp.billing.dto.response.BillMachineMapResponse;
import erp.billing.dto.response.BillPayMilestoneResponse;
import erp.billing.dto.response.BillPrintDescriptionItemResponseDTO;
import erp.billing.dto.response.BillPrintResponseDTO;
import erp.billing.dto.response.BillPrintTaxInvoiceItemResponseDTO;
import erp.billing.dto.response.ChainageStretchBillItemResponseDTO;
import erp.billing.dto.response.DebitNoteResponseDTO;
import erp.billing.dto.response.IdNameResponseDTO;
import erp.billing.dto.response.MachineDprGetResponse;
import erp.billing.dto.response.PriceEscalationItemsResponseDTO;
import erp.billing.dto.response.PriceEscalationResponseDTO;
import erp.billing.dto.response.StructureTypeBillBoqResponse;
import erp.billing.dto.response.WorkorderBillInfoResponse;
import erp.billing.entity.Bill;
import erp.billing.entity.BillBoqItem;
import erp.billing.entity.BillBoqQuantityItem;
import erp.billing.entity.BillBoqQuantityItemTransac;
import erp.billing.entity.BillDeductionMapping;
import erp.billing.entity.BillDeductionMappingV2;
import erp.billing.entity.BillMachineMapping;
import erp.billing.entity.BillPayMilestones;
import erp.billing.entity.BillStateTransitionMapping;
import erp.billing.entity.BillTransac;
import erp.billing.entity.CategoryItem;
import erp.billing.entity.Chainage;
import erp.billing.entity.DebitNoteItem;
import erp.billing.entity.DieselRateMapping;
import erp.billing.entity.EngineState;
import erp.billing.entity.Equipment;
import erp.billing.entity.FileEntity;
import erp.billing.entity.IssueSlipItem;
import erp.billing.entity.MachineDPR;
import erp.billing.entity.Site;
import erp.billing.entity.Unit;
import erp.billing.entity.UnitMaster;
import erp.billing.entity.User;
import erp.billing.entity.Workorder;
import erp.billing.entity.WorkorderBillInfo;
import erp.billing.entity.WorkorderBillInfoMachineMapping;
import erp.billing.entity.WorkorderBoqWorkQtyMapping;
import erp.billing.entity.WorkorderCategoryMapping;
import erp.billing.entity.WorkorderConsultantWorkItemMapping;
import erp.billing.entity.WorkorderHiringMachineRateDetails;
import erp.billing.entity.WorkorderHiringMachineWork;
import erp.billing.entity.WorkorderHiringMachineWorkItemMapping;
import erp.billing.entity.WorkorderPayMilestone;
import erp.billing.enums.DebitNoteStates;
import erp.billing.enums.EntitiesEnum;
import erp.billing.enums.MachineType;
import erp.billing.enums.MachineryAttendanceStatus;
import erp.billing.enums.MachineryRunningMode;
import erp.billing.enums.MachineryShifts;
import erp.billing.enums.Responses;
import erp.billing.enums.WorkorderTypes;
import erp.billing.feignClient.service.WorkflowEngineService;
import erp.billing.service.BillService;
import erp.billing.util.CustomValidationUtil;
import erp.billing.util.DateUtil;
import erp.billing.util.SetObject;

@Transactional(rollbackFor = Exception.class)
@Service
public class BillServiceImpl implements BillService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BillDao billDao;

	@Autowired
	private BillDeductionMappingDao deductionMapDao;

	@Autowired
	private BillBoqItemDao billItemDao;

	@Autowired
	private WorkorderBoqWorkDao woBoqWorkDao;

	@Autowired
	private WorkorderBasicDetailDao woBasicDao;

	@Autowired
	private SiteDao siteDao;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private DebitNoteDao debitNoteDao;

	@Autowired
	private DieselRateDao dieselRateDao;

	@Autowired
	private WorkorderDao workorderDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Autowired
	private WorkorderHireMachineryDao woHireMachineDao;

	@Autowired
	private BillMachineDao billMachineDao;

	@Autowired
	private MachineDao machineDao;

	@Autowired
	private DprHireMachineDao dprDao;

	@Autowired
	private WorkorderConsultantWorkDao consultantWorkDao;

	@Autowired
	private BillPayMilestoneDao billPayMilestoneDao;

	@Autowired
	private WorkorderPayMilestoneDao woPayMilestoneDao;

	@Autowired
	private IssueSlipItemDao issueSlipItemDao;

	@Autowired
	private WorkorderBillInfoDao workorderBillInfoDao;

	@Autowired
	private WorkorderBillInfoMachineMappingDao workorderBillInfoMachineMappingDao;

	final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public CustomResponse getBills(SearchDTO search) {

		try {
			List<Bill> bills = billDao.fetchBills(search);
			Long workorderId = search.getWorkorderId();
			Set<Long> distinctBillIds = new HashSet<>();
			if (bills != null) {
				bills.forEach(bill -> distinctBillIds.add(bill.getId()));
			}
			search.setWorkorderId(null);
			List<StateTransitionDTO> transitions = engineService.getStateTransition(EntitiesEnum.BILL.getEntityId(),
					search.getSiteId(), null, null, search.getCompanyId());

			List<WorkorderHiringMachineWorkItemMapping> machineItems = woHireMachineDao
					.fetchWorkorderHiringMachineWorkItemMapping(workorderId);

			Set<Long> distinctMachineItemIds = new HashSet<>();
			if (machineItems != null) {
				machineItems.forEach(obj -> {
					distinctMachineItemIds.add(obj.getId());
				});
			}
			List<WorkorderHiringMachineRateDetails> machineRateDetailsList = woHireMachineDao
					.fetchHiringMachineItemRateDetailsByWorkItemIds(distinctMachineItemIds);
			List<BillMachineMapping> allBillMachines = billMachineDao.fetchBillMachineMapping(distinctBillIds);
			WorkorderHiringMachineWork hiringMachineWork = woHireMachineDao
					.fetchWorkorderHiringMachineWork(workorderId);

			List<BillStateTransitionMapping> billsStateMaps = billDao.fetchBillStateMappingsByBillIds(distinctBillIds);
			List<BillBoqQuantityItem> billsQtyItems = billItemDao.fetchBillBoqQuantityItemsByBillIds(distinctBillIds);
			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.BILL.getEntityId(), search.getCompanyId());
			Set<Integer> entityFinalStateIds = new HashSet<>();
			Set<Integer> entityEditableStateIds = new HashSet<>();
			Integer entityInitialStateId = null;
			if (entityStateMaps != null) {
				for (EntityStateMapDTO esMapItr : entityStateMaps) {
					if (entityInitialStateId == null && esMapItr.getIsInitial()) {
						entityInitialStateId = esMapItr.getStateId();
					}
					if (esMapItr.getIsEditable() != null && esMapItr.getIsEditable()) {
						entityEditableStateIds.add(esMapItr.getStateId());
					}
					if (esMapItr.getIsFinal() != null && esMapItr.getIsFinal()) {
						entityFinalStateIds.add(esMapItr.getStateId());
					}
				}
			}

			List<BillDTO> results = new ArrayList<>();
			if (bills != null) {
				for (Bill bill : bills) {
					BillDTO obj = setObject.billEntityToDto(bill);
					User billUser = bill.getModifiedByUser();

//					for initial state obj
					if (obj.getState().getId().equals(entityInitialStateId)) {
						obj.setBillNoText(
								obj.getState().getAlias() + "-" + obj.getType().getName() + "/" + obj.getBillNo());
					} else {
						obj.setBillNoText(obj.getType().getName() + "/" + obj.getBillNo());
					}

//					set bill amount for BOQ Items

					List<BillBoqQuantityItem> qtyItems = new ArrayList<>();
					if (billsQtyItems != null) {
						for (BillBoqQuantityItem qtyItemItr : billsQtyItems) {
							if (qtyItemItr.getBillBoqItem().getBillId().equals(bill.getId())) {
								qtyItems.add(qtyItemItr);
							}
						}
					}

					Double billedAmount = 0.0;
					Double totalFuelDebitAmount = 0.0;
					if (qtyItems != null) {
						for (BillBoqQuantityItem qtyItem : qtyItems) {
							if (qtyItem.getBillBoqItem().getBillId().equals(bill.getId())) {
								billedAmount += (qtyItem.getRate() * qtyItem.getQuantity());
							}
						}
					}
					obj.setBilledAmount(billedAmount);

//					set bill amount for Machine Items
					Map<Long, MachineryRunningMode> categoryRunModeMap = new HashMap<Long, MachineryRunningMode>();

					List<BillMachineCategoryResponse> machineCategories = new ArrayList<>();
					if (machineItems != null) {
						for (WorkorderHiringMachineWorkItemMapping hireMachineItem : machineItems) {
							categoryRunModeMap.put(hireMachineItem.getMachineCatgeoryId(),
									hireMachineItem.getRunningMode());
							machineCategories.add(new BillMachineCategoryResponse(
									hireMachineItem.getMachineCatgeoryId(),
									hireMachineItem.getMachineCategory().getName(), hireMachineItem.getMachineCount(),
									new ArrayList<>(), hireMachineItem.getId(), hireMachineItem.getDescription(),
									hireMachineItem.getMachineCategory().getIsMultiFuel() != null
											? hireMachineItem.getMachineCategory().getIsMultiFuel()
											: false,
									hireMachineItem.getMachineCategory().getPrimaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getPrimaryReadingUnit().getId()
											: null,
									hireMachineItem.getMachineCategory().getPrimaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getPrimaryReadingUnit().getName()
											: null,
									hireMachineItem.getMachineCategory().getSecondaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getSecondaryReadingUnit().getId()
											: null,
									hireMachineItem.getMachineCategory().getSecondaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getSecondaryReadingUnit().getName()
											: null));
						}
					}

					List<BillMachineMapping> billMachines = new ArrayList<BillMachineMapping>();
					if (allBillMachines != null) {
						for (BillMachineMapping bmm : allBillMachines) {
							if (bmm.getBillId().equals(bill.getId())) {
								billMachines.add(bmm);
							}
						}
					}
					if (billMachines != null) {

						Set<Long> machineIds = new HashSet<>();
						billMachines.forEach(bm -> machineIds.add(bm.getMachineId()));

						List<MachineDPR> machinesDPRList = dprDao.fetchMachineDprList(machineIds, bill.getFromDate(),
								bill.getToDate(), bill.getSiteId());

						Date dieselFromDate = bill.getFromDate();
						Date dieselToDate = bill.getToDate();
						search.setFromDate(dieselFromDate);
						search.setToDate(dieselToDate);
						List<DieselRateMapping> dieselRates = dieselRateDao.fetchDieselRates(search);

						List<WorkorderHiringMachineWorkItemMapping> hiringMachineWorkItems = null;
						if (hiringMachineWork != null)
							hiringMachineWorkItems = machineItems;

//						Site site = siteDao.fetchSiteById(search.getSiteId());
						List<IssueSlipItem> issueSlipItems = issueSlipItemDao.fetchIssueSlipItems(dieselFromDate,
								dieselToDate, machineIds);

						for (BillMachineMapping bmm : billMachines) {

							Double fuelTakenFromStore = 0.0;
							Double weightedDieselRate = null;
							Integer dieselRateCount = 0;
							if (dieselRates != null) {
								for (DieselRateMapping dieselRate : dieselRates) {
									Date rateDate = DateUtil.dateWithoutTime(dieselRate.getDate());
									Date billFromDate = DateUtil.dateWithoutTime(bmm.getFromDate());
									Date billToDate = DateUtil.dateWithoutTime(bmm.getToDate());
									if (rateDate.equals(billFromDate) || rateDate.equals(billToDate)
											|| (rateDate.after(billFromDate) && rateDate.before(billToDate))) {
										if (weightedDieselRate == null) {
											dieselRateCount++;
											weightedDieselRate = dieselRate.getRate();
										} else {
											dieselRateCount++;
											weightedDieselRate = (weightedDieselRate + dieselRate.getRate());
										}
									}
								}
							}
							if (weightedDieselRate == null)
								weightedDieselRate = 0.0;
							if (weightedDieselRate > 0.0) {
								weightedDieselRate = weightedDieselRate / dieselRateCount;
							}

							BillMachineMapResponse mappedMachine = null;
							Long categoryId = null;
							UnitMaster primaryEngineUnit = null;
							UnitMaster secondaryEngineUnit = null;
							Boolean isMultiFuel = false;
							if (bmm.getMachineType() == MachineType.Equipment.ordinal()) {
								Equipment machine = machineDao.fetchEquipment(bmm.getMachineId());
								categoryId = machine.getCategory().getId();

								isMultiFuel = machine.getCategory().getIsMultiFuel() != null
										? machine.getCategory().getIsMultiFuel()
										: false;

								if (machine.getCategory().getPrimaryReadingUnit() != null) {
									primaryEngineUnit = new UnitMaster(
											machine.getCategory().getPrimaryReadingUnit().getId().shortValue(),
											machine.getCategory().getPrimaryReadingUnit().getName(), true);
								} else {
									primaryEngineUnit = new UnitMaster();
								}
								if (machine.getCategory().getSecondaryReadingUnit() != null) {
									secondaryEngineUnit = new UnitMaster(
											machine.getCategory().getSecondaryReadingUnit().getId().shortValue(),
											machine.getCategory().getSecondaryReadingUnit().getName(), true);
								} else {
									secondaryEngineUnit = new UnitMaster();
								}

								mappedMachine = new BillMachineMapResponse(bmm.getId(), machine.getId(),
										machine.getAssetCode(), machine.getRegNo(),
										(byte) MachineType.Equipment.ordinal(), bmm.getFromDate(), bmm.getToDate());

								if (issueSlipItems != null) {
									for (IssueSlipItem issueSlipItem : issueSlipItems) {
										Date rateDate = DateUtil
												.dateWithoutTime(issueSlipItem.getIssueSlip().getDateOn());
										Date billFromDate = DateUtil.dateWithoutTime(bmm.getFromDate());
										Date billToDate = DateUtil.dateWithoutTime(bmm.getToDate());

										if (bmm.getMachineId().equals(issueSlipItem.getEquipmentId()) && (rateDate
												.equals(billFromDate) || rateDate.equals(billToDate)
												|| (rateDate.after(billFromDate) && rateDate.before(billToDate)))) {
											Double issuedQuantity = issueSlipItem.getIssuedQuantity() != null
													? issueSlipItem.getIssuedQuantity()
													: 0;
											Double returnedQuantity = issueSlipItem.getReturnedQuantity() != null
													? issueSlipItem.getReturnedQuantity()
													: 0;
											fuelTakenFromStore += (issuedQuantity - returnedQuantity);
										}
									}
								}
							}

							Double netTotalPrimaryActualReading = 0.0;
							Double netTotalSecondaryActualReading = 0.0;
							Integer totalTripsTaken = 0;
							if (mappedMachine != null && categoryId != null) {
								for (BillMachineCategoryResponse categoryResponse : machineCategories) {
									if ((categoryResponse.getId().equals(categoryId)
											&& bmm.getWoHiringMachineWorkItemId() == null)
											|| (categoryResponse.getId().equals(categoryId)
													&& categoryResponse.getWoHiringMachineWorkItemId() != null
													&& bmm.getWoHiringMachineWorkItemId() != null
													&& categoryResponse.getWoHiringMachineWorkItemId()
															.equals(bmm.getWoHiringMachineWorkItemId()))) {
										List<MachineDprGetResponse> machineDprList = new ArrayList<>();
										Date machineFromDate = DateUtil.dateWithoutTime(mappedMachine.getFromDate());
										Date machineToDate = DateUtil.dateWithoutTime(mappedMachine.getToDate());
										Date dprDate = machineFromDate;
										float totalWorkingDays = 0f;
										float totalPresentDaysInNight = 0f;
										float totalPresentDaysInDay = 0f;
										Map<Integer, Double> totalBreakdownHoursInDayMap = new HashMap<Integer, Double>();
										Map<Integer, Double> totalBreakdownHoursInNightMap = new HashMap<Integer, Double>();

										while (!dprDate.after(machineToDate)) {

											MachineDprGetResponse dateDpr = new MachineDprGetResponse(null, dprDate,
													null, null, null, null, null, null, null, null, null, null, null,
													null, null, null, null, null, null);
											if (machinesDPRList != null) {
												for (MachineDPR machineDpr : machinesDPRList) {
													if (machineDpr.getMachineId().equals(mappedMachine.getMachineId())
															&& machineDpr.getMachineType()
																	.equals(mappedMachine.getMachineType())
															&& dprDate.equals(
																	DateUtil.dateWithoutTime(machineDpr.getDated()))) {
														if (bmm.getWoHiringMachineWorkItemId() != null) {
															Boolean hasDifferentRunningMode = false;
															for (WorkorderHiringMachineWorkItemMapping hireMachineItem : machineItems) {
																if (hireMachineItem.getId()
																		.equals(bmm.getWoHiringMachineWorkItemId())
																		&& !hireMachineItem.getRunningMode()
																				.equals(machineDpr.getRunningMode())) {
																	hasDifferentRunningMode = true;
																}
															}
															if (hasDifferentRunningMode) {
																continue;
															}
														} else {
															if (!categoryRunModeMap.get(categoryId)
																	.equals(machineDpr.getRunningMode())) {
																continue;
															}
														}
														dateDpr.setPrimaryOpeningMeterReading(
																machineDpr.getPrimaryOpeningMeterReading());
														dateDpr.setPrimaryClosingMeterReading(
																machineDpr.getPrimaryClosingMeterReading());
														dateDpr.setSecondaryOpeningMeterReading(
																machineDpr.getSecondaryOpeningMeterReading());
														dateDpr.setSecondaryClosingMeterReading(
																machineDpr.getSecondaryClosingMeterReading());
														dateDpr.setPrimaryOpeningActualReading(
																machineDpr.getPrimaryOpeningActualReading());
														dateDpr.setPrimaryClosingActualReading(
																machineDpr.getPrimaryClosingActualReading());
														dateDpr.setSecondaryOpeningActualReading(
																machineDpr.getSecondaryOpeningActualReading());
														dateDpr.setSecondaryClosingActualReading(
																machineDpr.getSecondaryClosingActualReading());

														Integer noOfDaysInMonth = DateUtil
																.getNoOfDaysInTheCurrentMonth(machineDpr.getDated());

														if (machineDpr.getRunningMode()
																.equals(MachineryRunningMode.TRIP)) {
															totalTripsTaken += machineDpr.getTripCount();
															if (machineDpr.getAttendanceStatus() != null
																	&& machineDpr.getAttendanceStatus().equals(
																			MachineryAttendanceStatus.HALFDAY)) {
																totalPresentDaysInDay += 0.5d;
															} else {
																totalPresentDaysInDay += 1d;
															}
														} else if (machineDpr.getRunningMode()
																.equals(MachineryRunningMode.SHIFTS)
																&& machineDpr.getShift() != null) {

															if (machineDpr.getShift().equals(MachineryShifts.DAYTIME)) {

																if (machineDpr.getAttendanceStatus() != null
																		&& machineDpr.getAttendanceStatus().equals(
																				MachineryAttendanceStatus.HALFDAY)) {
																	totalPresentDaysInDay += 0.5d;
																} else {
																	totalPresentDaysInDay += 1d;
																}

																if (machineDpr.getBreakdownHours() != null) {
																	if (totalBreakdownHoursInDayMap != null
																			&& totalBreakdownHoursInDayMap
																					.containsKey(noOfDaysInMonth)) {
																		totalBreakdownHoursInDayMap.put(noOfDaysInMonth,
																				totalBreakdownHoursInDayMap
																						.get(noOfDaysInMonth)
																						+ machineDpr
																								.getBreakdownHours());
																	} else {
																		totalBreakdownHoursInDayMap.put(noOfDaysInMonth,
																				machineDpr.getBreakdownHours());
																	}
																}

															} else {
																if (machineDpr.getAttendanceStatus() != null
																		&& machineDpr.getAttendanceStatus().equals(
																				MachineryAttendanceStatus.HALFDAY)) {
																	totalPresentDaysInNight += 0.5d;
																} else {
																	totalPresentDaysInNight += 1d;
																}

																if (machineDpr.getBreakdownHours() != null) {
																	if (totalBreakdownHoursInNightMap != null
																			&& totalBreakdownHoursInNightMap
																					.containsKey(noOfDaysInMonth)) {
																		totalBreakdownHoursInNightMap.put(
																				noOfDaysInMonth,
																				totalBreakdownHoursInNightMap
																						.get(noOfDaysInMonth)
																						+ machineDpr
																								.getBreakdownHours());
																	} else {
																		totalBreakdownHoursInNightMap.put(
																				noOfDaysInMonth,
																				machineDpr.getBreakdownHours());
																	}
																}
															}

														}

														if (machineDpr.getPrimaryOpeningActualReading() != null
																&& machineDpr
																		.getPrimaryClosingActualReading() != null) {
															Double netPrimaryActualReading = machineDpr
																	.getPrimaryClosingActualReading()
																	- machineDpr.getPrimaryOpeningActualReading();
															dateDpr.setNetPrimaryActualReading(netPrimaryActualReading);
															netTotalPrimaryActualReading += netPrimaryActualReading;
														}
														if (machineDpr.getSecondaryOpeningActualReading() != null
																&& machineDpr
																		.getSecondaryClosingActualReading() != null) {
															Double netSecondaryActualReading = machineDpr
																	.getSecondaryClosingActualReading()
																	- machineDpr.getSecondaryOpeningActualReading();
															dateDpr.setNetSecondaryActualReading(
																	netSecondaryActualReading);
															netTotalSecondaryActualReading += netSecondaryActualReading;
														}

														if (issueSlipItems != null) {

															for (IssueSlipItem issueSlipItem : issueSlipItems) {

																Date rateDate = DateUtil.dateWithoutTime(
																		issueSlipItem.getIssueSlip().getDateOn());
																if (bmm.getMachineType() == MachineType.Equipment
																		.ordinal()) {

																	if (bmm.getMachineId()
																			.equals(issueSlipItem.getEquipmentId())
																			&& rateDate.equals(dprDate)) {
																		Double issuedQuantity = issueSlipItem
																				.getIssuedQuantity() != null
																						? issueSlipItem
																								.getIssuedQuantity()
																						: 0;
																		Double returnedQuantity = issueSlipItem
																				.getReturnedQuantity() != null
																						? issueSlipItem
																								.getReturnedQuantity()
																						: 0;
																		Double netFuelTaken = (issuedQuantity
																				- returnedQuantity);
																		dateDpr.setStoreFuelTransactionQuantity(
																				netFuelTaken);
																	}
																} else if (bmm.getMachineType() == MachineType.Plant
																		.ordinal()) {

																	if (bmm.getMachineId()
																			.equals(issueSlipItem.getPlantId())
																			&& rateDate.equals(dprDate)) {
																		Double issuedQuantity = issueSlipItem
																				.getIssuedQuantity() != null
																						? issueSlipItem
																								.getIssuedQuantity()
																						: 0;
																		Double returnedQuantity = issueSlipItem
																				.getReturnedQuantity() != null
																						? issueSlipItem
																								.getReturnedQuantity()
																						: 0;
																		Double netFuelTaken = (issuedQuantity
																				- returnedQuantity);
																		dateDpr.setStoreFuelTransactionQuantity(
																				netFuelTaken);
																	}

																}

															}

														}

														dateDpr.setRunningMode(machineDpr.getRunningMode());
														dateDpr.setShift(machineDpr.getShift());
														dateDpr.setTripCount(machineDpr.getTripCount());
														dateDpr.setStatus(machineDpr.getAttendanceStatus());
														dateDpr.setRemarks(machineDpr.getRemarks());
														dateDpr.setBreakdownHours(machineDpr.getBreakdownHours());

													}
												}
											}
											machineDprList.add(dateDpr);
											totalWorkingDays++;
											dprDate = DateUtil.nextDateWithoutTime(dprDate);
										}
										mappedMachine.setNetTotalPrimaryActualReading(netTotalPrimaryActualReading);
										mappedMachine.setNetTotalSecondaryActualReading(netTotalSecondaryActualReading);
										mappedMachine.setTotalPresentInDayTime(totalPresentDaysInDay);
										mappedMachine.setTotalPresentInNightTime(totalPresentDaysInNight);
										mappedMachine.setTotalWorkingDays(totalWorkingDays);
										mappedMachine
												.setTotalAbsentInNightTime(totalWorkingDays - totalPresentDaysInNight);
										mappedMachine.setTotalAbsentInDayTime(totalWorkingDays - totalPresentDaysInDay);
										mappedMachine.setTripCount(totalTripsTaken);
										mappedMachine
												.setTotalBreakdownHoursInDayShift(totalBreakdownHoursInDayMap != null
														? totalBreakdownHoursInDayMap.values().stream()
																.mapToDouble(x -> x).sum()
														: null);
										mappedMachine.setTotalBreakdownHoursInNightShift(
												totalBreakdownHoursInNightMap != null
														? totalBreakdownHoursInNightMap.values().stream()
																.mapToDouble(x -> x).sum()
														: null);
										Double machineRateInDayTime = 0.0;
										Double machineRateInNightTime = 0.0;
										if (machineRateDetailsList != null) {
											for (WorkorderHiringMachineRateDetails rateDetail : machineRateDetailsList) {
												if ((rateDetail.getMachineItem().getMachineCatgeoryId()
														.equals(categoryId)
														&& categoryResponse.getWoHiringMachineWorkItemId() == null)
														|| (rateDetail.getMachineItem().getMachineCatgeoryId()
																.equals(categoryId)
																&& categoryResponse.getWoHiringMachineWorkItemId()
																		.equals(rateDetail
																				.getWoHiringMachineItemId()))) {
													if (rateDetail.getMachineItem().getRunningMode()
															.equals(MachineryRunningMode.TRIP)) {
														mappedMachine.setIsShiftBased(false);
														machineRateInDayTime = rateDetail.getRate();
													} else {
														mappedMachine.setIsShiftBased(true);
														if (rateDetail.getShift() != null && rateDetail.getShift()
																.equals(MachineryShifts.DAYTIME)) {
															machineRateInDayTime = rateDetail.getRate();
														} else {
															machineRateInNightTime = rateDetail.getRate();
														}
													}
												}
											}
										}
										mappedMachine.setMachineRateAsPerWorkorderDayShift(machineRateInDayTime);
										mappedMachine.setMachineRateAsPerWorkorderNightShift(machineRateInNightTime);
										mappedMachine.setDprList(machineDprList);
										if (hiringMachineWorkItems != null) {
											for (WorkorderHiringMachineWorkItemMapping hmItem : hiringMachineWorkItems) {
												if ((hmItem.getMachineCatgeoryId().equals(categoryId)
														&& categoryResponse.getWoHiringMachineWorkItemId() == null)
														|| (hmItem.getMachineCatgeoryId().equals(categoryId)
																&& categoryResponse.getWoHiringMachineWorkItemId()
																		.equals(hmItem.getId()))) {
													mappedMachine
															.setPrimaryEngineMileage(hmItem.getPrimaryEngineMileage());
													mappedMachine
															.setPrimaryEngineMileageUnit(primaryEngineUnit.getName());
													Double primaryFuelUsed = 0.0;
													Double secondaryFuelUsed = 0.0;
//													FUEL MILEAGE
													if (primaryEngineUnit.getName() != null
															&& (primaryEngineUnit.getName().toLowerCase().contains("hr")
																	|| primaryEngineUnit.getName().toLowerCase()
																			.contains("hour"))) {

														primaryFuelUsed = netTotalPrimaryActualReading
																* (hmItem.getPrimaryEngineMileage() != null
																		&& hmItem.getPrimaryEngineMileage() != 0
																				? hmItem.getPrimaryEngineMileage()
																				: 0);

													} else {

														primaryFuelUsed = netTotalPrimaryActualReading
																/ (hmItem.getPrimaryEngineMileage() != null
																		&& hmItem.getPrimaryEngineMileage() != 0
																				? hmItem.getPrimaryEngineMileage()
																				: 1);
													}
													Double totalFuelUsed = 0.0;
													totalFuelUsed += primaryFuelUsed;
													if (isMultiFuel) {
														mappedMachine.setSecondaryEngineMileage(
																hmItem.getSecondaryEngineMileage());
														mappedMachine.setSecondaryEngineMileageUnit(
																secondaryEngineUnit.getName());
														if (secondaryEngineUnit.getName() != null
																&& (secondaryEngineUnit.getName().toLowerCase()
																		.contains("hr")
																		|| secondaryEngineUnit.getName().toLowerCase()
																				.contains("hour"))) {

															secondaryFuelUsed = netTotalSecondaryActualReading
																	* (hmItem.getSecondaryEngineMileage() != null
																			&& hmItem.getSecondaryEngineMileage() != 0
																					? hmItem.getSecondaryEngineMileage()
																					: 0);

														} else {

															secondaryFuelUsed = netTotalSecondaryActualReading
																	/ (hmItem.getSecondaryEngineMileage() != null
																			&& hmItem.getSecondaryEngineMileage() != 0
																					? hmItem.getSecondaryEngineMileage()
																					: 1);
														}
														totalFuelUsed += secondaryFuelUsed;
													}
													Double handlingCharge = hmItem.getFuelHandlingCharge() != null
															? weightedDieselRate * hmItem.getFuelHandlingCharge() / 100
															: 0.0;
													mappedMachine.setFuelRateIncludingHandlingCharge(
															weightedDieselRate + handlingCharge);
													mappedMachine.setFuelDebitAmount(
															mappedMachine.getFuelRateIncludingHandlingCharge()
																	* fuelTakenFromStore);

													mappedMachine.setTotalFuelAsPerWorkorder(totalFuelUsed);
													mappedMachine.setFuelTakenFromStore(fuelTakenFromStore);
													Double excessFuelTaken = fuelTakenFromStore - totalFuelUsed;
													mappedMachine.setExcessFuelTaken(
															excessFuelTaken > 0.0 ? excessFuelTaken : 0.0);

													Double dieselEscalationPrice = 0.0;

													if (!hmItem.getDieselInclusive()) {
														if (hiringMachineWork.getDieselRate() != null
																&& hiringMachineWork.getDieselRate() > 0.0) {

															if (!hiringMachineWork.getDieselRate()
																	.equals(weightedDieselRate)) {

																mappedMachine.setDieselRateAsPerWorkorder(
																		hiringMachineWork.getDieselRate());
																mappedMachine
																		.setFuelRateAsPerWorkorderIncludingHandlingCharge(
																				hmItem.getFuelHandlingCharge() != null
																						&& hmItem
																								.getFuelHandlingCharge() > 0
																										? hiringMachineWork
																												.getDieselRate()
																												+ (hmItem
																														.getFuelHandlingCharge()
																														* hiringMachineWork
																																.getDieselRate())
																														/ 100
																										: hiringMachineWork
																												.getDieselRate());

																mappedMachine.setTotalFuelRateAsPerWorkorder(
																		totalFuelUsed * mappedMachine
																				.getFuelRateAsPerWorkorderIncludingHandlingCharge());

																mappedMachine.setTotalFuelRateAsPerWeightedRate(
																		totalFuelUsed * mappedMachine
																				.getFuelRateIncludingHandlingCharge());

																mappedMachine.setDieselEscalationPrice(mappedMachine
																		.getTotalFuelRateAsPerWeightedRate()
																		- mappedMachine
																				.getTotalFuelRateAsPerWorkorder());

																dieselEscalationPrice = mappedMachine
																		.getDieselEscalationPrice();

															}

														}
													}

													if (hmItem.getDieselInclusive()) {

														if (excessFuelTaken > 0) {

															if (weightedDieselRate <= 0.0) {
																DateFormat formatter = new SimpleDateFormat(
																		"dd/MM/yyyy");
																return new CustomResponse(Responses.FORBIDDEN.getCode(),
																		null,
																		"Diesel rates not present for bills for date range : "
																				+ formatter.format(bmm.getFromDate())
																				+ " - "
																				+ formatter.format(bmm.getToDate()));
															}

															mappedMachine.setFuelDebitAmount(
																	mappedMachine.getFuelRateIncludingHandlingCharge()
																			* excessFuelTaken);
														} else {
															excessFuelTaken = 0d;
															mappedMachine.setFuelDebitAmount(
																	mappedMachine.getFuelRateIncludingHandlingCharge()
																			* excessFuelTaken);
														}

													}

													totalFuelDebitAmount += mappedMachine.getFuelDebitAmount();
													mappedMachine
															.setMachineRateAsPerWorkorderDayShift(machineRateInDayTime);
													mappedMachine.setMachineRateAsPerWorkorderNightShift(
															machineRateInNightTime);

													mappedMachine.setMachineRateUnitAsPerWorkorder(
															hmItem.getUnit().getName());
													mappedMachine.setTotalDaysAsPerRateUnit(DateUtil
															.getNoOfDaysInTheCurrentMonth(mappedMachine.getFromDate())
															.doubleValue());

													Double thresholdAmount = 0.0;

													if (hmItem.getThresholdApplicable()) {
														mappedMachine.setIsThresholdApplicable(true);
														mappedMachine
																.setThresholdQuantity(hmItem.getThresholdQuantity());
														mappedMachine.setThresholdQuantityUnit(
																hmItem.getThresholdUnit().getName());
														if (mappedMachine
																.getNetTotalPrimaryActualReading() > mappedMachine
																		.getThresholdQuantity()) {
															thresholdAmount = (mappedMachine
																	.getNetTotalPrimaryActualReading()
																	- mappedMachine.getThresholdQuantity())
																	* hmItem.getPostThresholdRatePerUnit();
														}
													} else {
														mappedMachine.setIsThresholdApplicable(false);
													}

													Double machineTotalAmountInDay = 0.0;
													Double machineTotalAmountInNight = 0.0;

													Double machineTotalBreakdownHoursAmountInDay = 0.0;
													Double machineTotalBreakdownHoursAmountInNight = 0.0;

//													if (mappedMachine.getIsShiftBased()) {
//
//														if (totalBreakdownHoursInDayMap != null) {
//															for (Map.Entry<Integer, Double> dayObj : totalBreakdownHoursInDayMap
//																	.entrySet()) {
//
//																machineTotalBreakdownHoursAmountInDay += (mappedMachine
//																		.getMachineRateAsPerWorkorderDayShift()
//																		/ (dayObj.getKey() * 12)) * dayObj.getValue();
//															}
//														}
//
//														if (totalBreakdownHoursInDayMap != null) {
//															for (Map.Entry<Integer, Double> dayObj : totalBreakdownHoursInNightMap
//																	.entrySet()) {
//
//																machineTotalBreakdownHoursAmountInNight += (mappedMachine
//																		.getMachineRateAsPerWorkorderNightShift()
//																		/ (dayObj.getKey() * 12)) * dayObj.getValue();
//															}
//														}
//													}

													if (mappedMachine.getIsShiftBased()) {

														if (mappedMachine.getTotalBreakdownHoursInDayShift() != null) {

															machineTotalBreakdownHoursAmountInDay += (mappedMachine
																	.getMachineRateAsPerWorkorderDayShift()
																	/ (mappedMachine.getTotalDaysAsPerRateUnit()
																			* hmItem.getShiftSchedule()
																					.getShiftHours()))
																	* mappedMachine.getTotalBreakdownHoursInDayShift();

														}

														if (mappedMachine
																.getTotalBreakdownHoursInNightShift() != null) {
															machineTotalBreakdownHoursAmountInNight += (mappedMachine
																	.getMachineRateAsPerWorkorderNightShift()
																	/ (mappedMachine.getTotalDaysAsPerRateUnit()
																			* hmItem.getShiftSchedule()
																					.getShiftHours()))
																	* mappedMachine
																			.getTotalBreakdownHoursInNightShift();

														}
													}

													if (mappedMachine.getIsShiftBased()) {
														machineTotalAmountInDay = (totalPresentDaysInDay
																/ mappedMachine.getTotalDaysAsPerRateUnit())
																* mappedMachine.getMachineRateAsPerWorkorderDayShift();

														machineTotalAmountInDay -= machineTotalBreakdownHoursAmountInDay;

														machineTotalAmountInNight = (totalPresentDaysInNight
																/ mappedMachine.getTotalDaysAsPerRateUnit())
																* mappedMachine
																		.getMachineRateAsPerWorkorderNightShift();

														machineTotalAmountInNight -= machineTotalBreakdownHoursAmountInNight;
													} else {

														machineTotalAmountInDay = totalTripsTaken
																* mappedMachine.getMachineRateAsPerWorkorderDayShift();
													}
													mappedMachine.setTotalAmount(
															machineTotalAmountInDay + machineTotalAmountInNight
																	+ thresholdAmount + dieselEscalationPrice);

													billedAmount += mappedMachine.getTotalAmount();
												}
											}
										}

										categoryResponse.getMachines().add(mappedMachine);
									}
								}
							}
						}
					}

					obj.setBilledAmount(billedAmount);

//					set edit mode
					obj.setIsEditable(false);
					boolean billInEditableState = false;
					if (entityEditableStateIds.contains(obj.getState().getId())) {
						billInEditableState = true;
						obj.setIsEditable(true);
					}

//					set next states
					if (transitions != null && billUser != null && billUser.getRole() != null) {
						List<NextStateTransitDTO> nextStates = new ArrayList<>();
						for (StateTransitionDTO st : transitions) {
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
								nst.setIsButtonRequired(st.getNextState().getButtonText() != null ? true : false);
								nst.setNextStateAlias(st.getNextState().getAlias());
								nextStates.add(nst);
							}
						}
						obj.setNextStates(nextStates);
					}

//					set final state mode
					obj.setInFinalState(false);
					if (entityFinalStateIds.contains(obj.getState().getId())) {
						obj.setInFinalState(true);
					}

//					add bill in result list
					boolean hasAdded = false;

					if (billInEditableState && bill.getCreatedBy().equals(search.getUserId())) {
						results.add(obj);
						hasAdded = true;
					}

					if (!hasAdded && obj.getNextStates() != null && obj.getNextStates().size() > 0) {
						results.add(obj);
						hasAdded = true;
					}
					if (!hasAdded && !billInEditableState) {
						List<BillStateTransitionMapping> billStateMaps = new ArrayList<>();
						if (billsStateMaps != null) {
							for (BillStateTransitionMapping bsmItr : billsStateMaps) {
								if (bsmItr.getBillId().equals(bill.getId())) {
									billStateMaps.add(bsmItr);
								}
							}
						}
						if (billStateMaps != null) {
							for (BillStateTransitionMapping stm : billStateMaps) {
								if (stm.getCreatedBy().equals(search.getUserId())) {
									results.add(obj);
									hasAdded = true;
									break;
								}
							}
						}
					}
					if (!hasAdded && obj.getInFinalState()) {
						results.add(obj);
						hasAdded = true;
					}

				}
			}

			Double billedAmountTotal = 0.0;
			if (results != null) {
				for (BillDTO bill : results) {
					billedAmountTotal += bill.getBilledAmount();
				}
			}

			BillListResponse responseObj = new BillListResponse(results, billedAmountTotal);

			return new CustomResponse(Responses.SUCCESS.getCode(), responseObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBillById(SearchDTO search) {

		try {
			Workorder workorder = null;
			if (search.getBillId() == null) {
				workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
				if (workorder.getType().getId().intValue() == WorkorderTypes.Structure.getId()) {

					List<WorkorderBoqWorkQtyMapping> woBoqs = woBoqWorkDao.fetchWoBoqWorkQtys(search.getWorkorderId());
					List<BillBoqQuantityItem> qtyItems = billItemDao
							.fetchBillBoqQuantityItemsByWorkorderId(search.getWorkorderId());
					BillAndItemsDTO result = new BillAndItemsDTO();
					List<StructureTypeBillBoqResponse> structureTypeItems = new ArrayList<>();
					if (woBoqs != null) {
						Set<Long> disctinctStructureIds = new HashSet<>();
						for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
							disctinctStructureIds.add(woBoq.getStructureTypeId());
						}
						for (Long structureTypeId : disctinctStructureIds) {
							String structureTypeName = null;
							List<BillItemResponseDTO> billBoqs = new ArrayList<>();
							for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
								if (woBoq.getStructureTypeId().equals(structureTypeId)) {
									if (structureTypeName == null) {
										structureTypeName = woBoq.getStructureType().getName();
									}
									Double totalQuantity = woBoq.getQuantity();
									Double billedQuantity = 0.0;
									if (qtyItems != null) {
										for (BillBoqQuantityItem qtyItem : qtyItems) {
											if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId())
													&& woBoq.getVendorDescription()
															.equals(qtyItem.getBillBoqItem().getVendorDescription())) {
												billedQuantity += qtyItem.getQuantity();
											}
										}
									}
									String boqSubcatMergeDescription = woBoq.getVendorDescription();
									if (woBoq.getBoq().getSubcategory() != null
											&& woBoq.getBoq().getSubcategory().getName() != null) {
										boqSubcatMergeDescription = woBoq.getBoq().getSubcategory().getName()
												+ boqSubcatMergeDescription;
									}
									BillItemResponseDTO obj = new BillItemResponseDTO(null, null, structureTypeId,
											woBoq.getStructureType().getName(),
											setObject.boqItemEntityToDto(woBoq.getBoq()),
											new IdNameResponseDTO(woBoq.getUnit().getId(), woBoq.getUnit().getName()),
											woBoq.getVendorDescription(), boqSubcatMergeDescription, totalQuantity,
											billedQuantity, totalQuantity - billedQuantity, 0.0, woBoq.getRate(), null,
											null);
									billBoqs.add(obj);
								}
							}
							if (billBoqs.size() > 0) {
								structureTypeItems.add(
										new StructureTypeBillBoqResponse(structureTypeId, structureTypeName, billBoqs));
							}
						}
					}
					result.setItems(null);
					result.setStructureTypeItems(structureTypeItems);
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
				} else if (workorder.getType().getId().intValue() == WorkorderTypes.Highway.getId()) {

					List<WorkorderBoqWorkQtyMapping> woBoqs = woBoqWorkDao.fetchWoBoqWorkQtys(search.getWorkorderId());
					List<BillBoqQuantityItem> qtyItems = billItemDao
							.fetchBillBoqQuantityItemsByWorkorderId(search.getWorkorderId());
					BillAndItemsDTO result = new BillAndItemsDTO();
					List<BillItemResponseDTO> billBoqs = new ArrayList<>();
					if (woBoqs != null) {
						for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
							Double totalQuantity = woBoq.getQuantity();
							Double billedQuantity = 0.0;
							if (qtyItems != null) {
								for (BillBoqQuantityItem qtyItem : qtyItems) {
									if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId())
											&& woBoq.getVendorDescription()
													.equals(qtyItem.getBillBoqItem().getVendorDescription())) {
										billedQuantity += qtyItem.getQuantity();
									}
								}
							}
							String boqSubcatMergeDescription = woBoq.getVendorDescription();
							if (woBoq.getBoq().getSubcategory() != null
									&& woBoq.getBoq().getSubcategory().getName() != null) {
								boqSubcatMergeDescription = woBoq.getBoq().getSubcategory().getName()
										+ boqSubcatMergeDescription;
							}
							BillItemResponseDTO obj = new BillItemResponseDTO(null, null, null, null,
									setObject.boqItemEntityToDto(woBoq.getBoq()),
									new IdNameResponseDTO(woBoq.getUnit().getId(), woBoq.getUnit().getName()),
									woBoq.getVendorDescription(), boqSubcatMergeDescription, totalQuantity,
									billedQuantity, totalQuantity - billedQuantity, 0.0, woBoq.getRate(), null, null);
							billBoqs.add(obj);
						}
					}
					result.setItems(billBoqs);
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
				} else if (workorder.getType().getId().intValue() == WorkorderTypes.Machine_Hiring.getId()) {

					BillAndItemsDTO result = new BillAndItemsDTO();
					List<WorkorderHiringMachineWorkItemMapping> machineItems = woHireMachineDao
							.fetchWorkorderHiringMachineWorkItemMapping(search.getWorkorderId());
					List<BillMachineCategoryResponse> machineCategories = new ArrayList<>();
					if (machineItems != null) {
						for (WorkorderHiringMachineWorkItemMapping hireMachineItem : machineItems) {
							machineCategories.add(new BillMachineCategoryResponse(
									hireMachineItem.getMachineCatgeoryId(),
									hireMachineItem.getMachineCategory().getName(), hireMachineItem.getMachineCount(),
									null, hireMachineItem.getId(), hireMachineItem.getDescription(),
									hireMachineItem.getMachineCategory().getIsMultiFuel() != null
											? hireMachineItem.getMachineCategory().getIsMultiFuel()
											: false,
									hireMachineItem.getMachineCategory().getPrimaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getPrimaryReadingUnit().getId()
											: null,
									hireMachineItem.getMachineCategory().getPrimaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getPrimaryReadingUnit().getName()
											: null,
									hireMachineItem.getMachineCategory().getSecondaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getSecondaryReadingUnit().getId()
											: null,
									hireMachineItem.getMachineCategory().getSecondaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getSecondaryReadingUnit().getName()
											: null,
									hireMachineItem.getRunningMode()));
						}
					}
					result.setMachineCategories(machineCategories);
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());

				} else if (workorder.getType().getId().intValue() == WorkorderTypes.Consultancy.getId()) {

					BillAndItemsDTO result = new BillAndItemsDTO();

					List<WorkorderConsultantWorkItemMapping> workItems = consultantWorkDao
							.fetchWorkorderConsultantWorkItemsByWorkorderId(search.getWorkorderId());
					Double workorderAmount = 0.0;

					if (workItems != null) {
						for (WorkorderConsultantWorkItemMapping obj : workItems) {
							workorderAmount += (obj.getRate() * obj.getQuantity());
						}
					}

					List<WorkorderPayMilestone> woPayMilestones = woPayMilestoneDao
							.fetchWorkorderWorkorderPayMilestones(search.getWorkorderId());

					List<BillPayMilestones> woBillPayMilestones = billPayMilestoneDao
							.fetchBillPayMilestonesByWorkorderId(search.getWorkorderId());

					Set<Long> woPayMileIdsUtilised = new HashSet<>();

					if (woBillPayMilestones != null)
						woBillPayMilestones.forEach(item -> woPayMileIdsUtilised.add(item.getWorkorderMilestoneId()));

					List<BillPayMilestoneResponse> billPayMilestones = new ArrayList<>();
					if (woPayMilestones != null) {
						for (WorkorderPayMilestone woPayMilestone : woPayMilestones) {

							Double amount = woPayMilestone.getValue();
							if (woPayMilestone.getIsPercentage()) {
								amount = (amount * workorderAmount) / 100;
							}
							if (woPayMileIdsUtilised.contains(woPayMilestone.getId()))
								continue;
							billPayMilestones.add(new BillPayMilestoneResponse(null, woPayMilestone.getId(),
									woPayMilestone.getDescription(), woPayMilestone.getIsPercentage(),
									woPayMilestone.getValue(), amount, false));
						}
					}
					result.setBillPayMilestones(billPayMilestones);
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());

				}

			} else if (search.getBillId() != null) {
				Bill bill = billDao.fetchBillById(search.getBillId());
				search.setWorkorderId(bill.getWorkorder().getId());
				if (bill.getWorkorder().getType().getId().intValue() == WorkorderTypes.Structure.getId()) {

					Set<Long> distinctWoStructureTypeIds = new HashSet<>();
					Double totalBillAmount = 0.0;
					List<BillBoqItem> boqItems = billItemDao.fetchBillBoqItemsByBillId(bill.getId());

					List<BillBoqQuantityItem> qtyItems = billItemDao
							.fetchBillBoqQuantityItemsByWorkorderId(bill.getWorkorder().getId());
					List<WorkorderBoqWorkQtyMapping> woBoqs = woBoqWorkDao
							.fetchWoBoqWorkQtys(bill.getWorkorder().getId());
					List<BillItemResponseDTO> billBoqs = new ArrayList<>();
					if (boqItems != null && qtyItems != null) {
						for (BillBoqItem billBoq : boqItems) {
							List<ChainageStretchBillItemResponseDTO> billQtyItems = new ArrayList<>();
							Double currentBillQuantity = 0.0;
							for (BillBoqQuantityItem qtyItem : qtyItems) {
								if (qtyItem.getBillBoqItemId().equals(billBoq.getId())) {
									currentBillQuantity += qtyItem.getQuantity();
									billQtyItems.add(new ChainageStretchBillItemResponseDTO(qtyItem.getId(),
											qtyItem.getBillBoqItemId(),
											new IdNameResponseDTO(qtyItem.getFromChainage().getId(),
													qtyItem.getFromChainage().getName()),
											new IdNameResponseDTO(qtyItem.getToChainage().getId(),
													qtyItem.getToChainage().getName()),
											qtyItem.getQuantity()));
								}
							}
							String structureTypeName = null;
							Double totalQuantity = 0.0;
							Double billedQuantity = 0.0;
							Double rate = 0.0;
							String vendorDescription = null;
							if (woBoqs != null) {
								for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
									if (billBoq.getStructureTypeId().equals(woBoq.getStructureTypeId())
											&& woBoq.getBoq().getId().equals(billBoq.getBoq().getId())
											&& woBoq.getVendorDescription().equals(billBoq.getVendorDescription())) {
										structureTypeName = woBoq.getStructureType().getName();
										totalQuantity = woBoq.getQuantity();
										rate = woBoq.getRate();
										vendorDescription = woBoq.getVendorDescription();
										if (qtyItems != null) {
											for (BillBoqQuantityItem qtyItem : qtyItems) {
												if (qtyItem.getBillBoqItem().getStructureTypeId()
														.equals(woBoq.getStructureTypeId())
														&& qtyItem.getBillBoqItem().getBoq().getId()
																.equals(woBoq.getBoqId())
														&& woBoq.getVendorDescription().equals(
																qtyItem.getBillBoqItem().getVendorDescription())) {
													billedQuantity += qtyItem.getQuantity();
													totalBillAmount += (qtyItem.getQuantity() * qtyItem.getRate());
												}
											}
										}
										break;
									}
								}
							}
							String boqSubcatMergeDescription = vendorDescription;
							if (billBoq.getBoq().getSubcategory() != null
									&& billBoq.getBoq().getSubcategory().getName() != null) {
								boqSubcatMergeDescription = billBoq.getBoq().getSubcategory().getName()
										+ boqSubcatMergeDescription;
							}
							if (vendorDescription != null)
								billBoqs.add(new BillItemResponseDTO(billBoq.getId(), billBoq.getBillId(),
										billBoq.getStructureTypeId(), structureTypeName,
										setObject.boqItemEntityToDto(billBoq.getBoq()),
										new IdNameResponseDTO(billBoq.getUnit().getId(), billBoq.getUnit().getName()),
										vendorDescription, boqSubcatMergeDescription, totalQuantity, billedQuantity,
										totalQuantity - billedQuantity, currentBillQuantity, rate, billBoq.getRemark(),
										billQtyItems));

						}
						if (woBoqs != null) {

							for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
								distinctWoStructureTypeIds.add(woBoq.getStructureTypeId());
								boolean isPresent = false;
								for (BillItemResponseDTO obj : billBoqs) {
									if (obj.getStructureTypeId().equals(woBoq.getStructureTypeId())
											&& obj.getBoq().getId().equals(woBoq.getBoqId())
											&& obj.getVendorDescription().equals(woBoq.getVendorDescription())) {
										isPresent = true;
										break;
									}
								}
								if (!isPresent) {
									Double totalQuantity = woBoq.getQuantity();
									Double billedQuantity = 0.0;
									if (qtyItems != null) {
										for (BillBoqQuantityItem qtyItem : qtyItems) {
											if (qtyItem.getBillBoqItem().getStructureTypeId()
													.equals(woBoq.getStructureTypeId())
													&& qtyItem.getBillBoqItem().getBoq().getId()
															.equals(woBoq.getBoqId())
													&& woBoq.getVendorDescription()
															.equals(qtyItem.getBillBoqItem().getVendorDescription())) {
												billedQuantity += qtyItem.getQuantity();
											}
										}
									}
									String boqSubcatMergeDescription = woBoq.getVendorDescription();
									if (woBoq.getBoq().getSubcategory() != null
											&& woBoq.getBoq().getSubcategory().getName() != null) {
										boqSubcatMergeDescription = woBoq.getBoq().getSubcategory().getName()
												+ boqSubcatMergeDescription;
									}
									BillItemResponseDTO obj = new BillItemResponseDTO(null, null,
											woBoq.getStructureTypeId(), woBoq.getStructureType().getName(),
											setObject.boqItemEntityToDto(woBoq.getBoq()),
											new IdNameResponseDTO(woBoq.getUnit().getId(), woBoq.getUnit().getName()),
											woBoq.getVendorDescription(), boqSubcatMergeDescription, totalQuantity,
											billedQuantity, totalQuantity - billedQuantity, 0.0, woBoq.getRate(), null,
											null);
									billBoqs.add(obj);
								}
							}
						}
					}

					List<StructureTypeBillBoqResponse> structureTypeItems = new ArrayList<>();
					for (Long structureTypeId : distinctWoStructureTypeIds) {
						List<BillItemResponseDTO> relatedBoqs = new ArrayList<>();
						String structureTypeName = null;
						for (BillItemResponseDTO obj : billBoqs) {
							if (obj.getStructureTypeId().equals(structureTypeId)) {
								relatedBoqs.add(obj);
								if (structureTypeName == null) {
									structureTypeName = obj.getStructureTypeName();
								}
							}
						}
						structureTypeItems
								.add(new StructureTypeBillBoqResponse(structureTypeId, structureTypeName, relatedBoqs));
					}
					BillAndItemsDTO result = new BillAndItemsDTO(bill.getId(),
							setObject.billTypeEntityToDto(bill.getType()), bill.getBillNo(), bill.getFromDate(),
							bill.getToDate(), null, structureTypeItems,
							setObject.workorderEntityToDto(bill.getWorkorder()), bill.getTaxInvoiceNo(),
							bill.getTaxInvoiceDate(), bill.getApplicableIgst(), bill.getIsIgstOnly(),
							setObject.engineStateEntityToDto(bill.getState()), bill.getSiteId(), bill.getIsActive(),
							bill.getCreatedOn(), bill.getCreatedBy(), bill.getModifiedOn(), bill.getModifiedBy());

//					for initial state obj

					EngineStateDTO entityInitialState = engineService
							.getEntityInitialState(EntitiesEnum.BILL.getEntityId(), search.getCompanyId());
					if (result.getState().getId().equals(entityInitialState.getId())) {
						result.setBillNoText(result.getState().getAlias() + "-" + result.getType().getName() + "/"
								+ result.getBillNo());
					} else {
						result.setBillNoText(result.getType().getName() + "/" + result.getBillNo());
					}

					result.setBilledAmount(totalBillAmount);
					if (result.getApplicableIgst() != null) {
						Double gst = result.getApplicableIgst() / 100;
						Double billedAmountAfterGst = totalBillAmount + (totalBillAmount * gst);
						result.setBilledAmountAfterGst(billedAmountAfterGst);
					} else {
						result.setBilledAmountAfterGst(totalBillAmount);
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
				} else if (bill.getWorkorder().getType().getId().intValue() == WorkorderTypes.Highway.getId()) {

					Double totalBillAmount = 0.0;
					List<BillBoqItem> boqItems = billItemDao.fetchBillBoqItemsByBillId(bill.getId());
					List<BillBoqQuantityItem> qtyItems = billItemDao
							.fetchBillBoqQuantityItemsByWorkorderId(bill.getWorkorder().getId());
					List<WorkorderBoqWorkQtyMapping> woBoqs = woBoqWorkDao
							.fetchWoBoqWorkQtys(bill.getWorkorder().getId());
					List<BillItemResponseDTO> billBoqs = new ArrayList<>();
					if (boqItems != null && qtyItems != null) {
						for (BillBoqItem billBoq : boqItems) {
							List<ChainageStretchBillItemResponseDTO> billQtyItems = new ArrayList<>();
							Double currentBillQuantity = 0.0;
							for (BillBoqQuantityItem qtyItem : qtyItems) {
								if (qtyItem.getBillBoqItemId().equals(billBoq.getId())) {
									currentBillQuantity += qtyItem.getQuantity();
									billQtyItems.add(new ChainageStretchBillItemResponseDTO(qtyItem.getId(),
											qtyItem.getBillBoqItemId(),
											new IdNameResponseDTO(qtyItem.getFromChainage().getId(),
													qtyItem.getFromChainage().getName()),
											new IdNameResponseDTO(qtyItem.getToChainage().getId(),
													qtyItem.getToChainage().getName()),
											qtyItem.getQuantity()));
								}
							}
							Double totalQuantity = 0.0;
							Double billedQuantity = 0.0;
							Double rate = 0.0;
							String vendorDescription = null;
							if (woBoqs != null) {
								for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
									if (woBoq.getBoq().getId().equals(billBoq.getBoq().getId())
											&& woBoq.getVendorDescription().equals(billBoq.getVendorDescription())) {
										totalQuantity = woBoq.getQuantity();
										rate = woBoq.getRate();
										vendorDescription = woBoq.getVendorDescription();
										if (qtyItems != null) {
											for (BillBoqQuantityItem qtyItem : qtyItems) {
												if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId())
														&& woBoq.getVendorDescription().equals(
																qtyItem.getBillBoqItem().getVendorDescription())) {
													billedQuantity += qtyItem.getQuantity();
													totalBillAmount += (qtyItem.getQuantity() * qtyItem.getRate());
												}
											}
										}
										break;
									}
								}
							}
							String boqSubcatMergeDescription = vendorDescription;
							if (billBoq.getBoq().getSubcategory() != null
									&& billBoq.getBoq().getSubcategory().getName() != null) {
								boqSubcatMergeDescription = billBoq.getBoq().getSubcategory().getName()
										+ boqSubcatMergeDescription;
							}
							if (vendorDescription != null)
								billBoqs.add(new BillItemResponseDTO(billBoq.getId(), billBoq.getBillId(), null, null,
										setObject.boqItemEntityToDto(billBoq.getBoq()),
										new IdNameResponseDTO(billBoq.getUnit().getId(), billBoq.getUnit().getName()),
										vendorDescription, boqSubcatMergeDescription,
										roundOffUptoTwoDecimal(totalQuantity), roundOffUptoTwoDecimal(billedQuantity),
										roundOffUptoTwoDecimal(totalQuantity - billedQuantity),
										roundOffUptoTwoDecimal(currentBillQuantity), rate, billBoq.getRemark(),
										billQtyItems));

						}
					}
					if (woBoqs != null) {
						for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
							boolean isPresent = false;
							for (BillItemResponseDTO obj : billBoqs) {
								if (obj.getBoq().getId().equals(woBoq.getBoqId())
										&& obj.getVendorDescription().equals(woBoq.getVendorDescription())) {
									isPresent = true;
									break;
								}
							}
							if (!isPresent) {
								Double totalQuantity = woBoq.getQuantity();
								Double billedQuantity = 0.0;
								if (qtyItems != null) {
									for (BillBoqQuantityItem qtyItem : qtyItems) {
										if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId())
												&& woBoq.getVendorDescription()
														.equals(qtyItem.getBillBoqItem().getVendorDescription())) {
											billedQuantity += qtyItem.getQuantity();
										}
									}
								}
								String boqSubcatMergeDescription = woBoq.getVendorDescription();
								if (woBoq.getBoq().getSubcategory() != null
										&& woBoq.getBoq().getSubcategory().getName() != null) {
									boqSubcatMergeDescription = woBoq.getBoq().getSubcategory().getName()
											+ boqSubcatMergeDescription;
								}
								BillItemResponseDTO obj = new BillItemResponseDTO(null, null, null, null,
										setObject.boqItemEntityToDto(woBoq.getBoq()),
										new IdNameResponseDTO(woBoq.getUnit().getId(), woBoq.getUnit().getName()),
										woBoq.getVendorDescription(), boqSubcatMergeDescription,
										roundOffUptoTwoDecimal(totalQuantity), roundOffUptoTwoDecimal(billedQuantity),
										roundOffUptoTwoDecimal(totalQuantity - billedQuantity), 0.0, woBoq.getRate(),
										null, null);
								billBoqs.add(obj);
							}
						}
					}
					BillAndItemsDTO result = new BillAndItemsDTO(bill.getId(),
							setObject.billTypeEntityToDto(bill.getType()), bill.getBillNo(), bill.getFromDate(),
							bill.getToDate(), billBoqs, null, setObject.workorderEntityToDto(bill.getWorkorder()),
							bill.getTaxInvoiceNo(), bill.getTaxInvoiceDate(), bill.getApplicableIgst(),
							bill.getIsIgstOnly(), setObject.engineStateEntityToDto(bill.getState()), bill.getSiteId(),
							bill.getIsActive(), bill.getCreatedOn(), bill.getCreatedBy(), bill.getModifiedOn(),
							bill.getModifiedBy());

//					for initial state obj
					EngineStateDTO entityInitialState = engineService
							.getEntityInitialState(EntitiesEnum.BILL.getEntityId(), search.getCompanyId());
					if (result.getState().getId().equals(entityInitialState.getId())) {
						result.setBillNoText(result.getState().getAlias() + "-" + result.getType().getName() + "/"
								+ result.getBillNo());
					} else {
						result.setBillNoText(result.getType().getName() + "/" + result.getBillNo());
					}

					result.setBilledAmount(roundOffUptoTwoDecimal(totalBillAmount));
					if (result.getApplicableIgst() != null) {
						Double gst = result.getApplicableIgst() / 100;
						Double billedAmountAfterGst = totalBillAmount + (totalBillAmount * gst);
						result.setBilledAmountAfterGst(roundOffUptoTwoDecimal(billedAmountAfterGst));
					} else {
						result.setBilledAmountAfterGst(roundOffUptoTwoDecimal(totalBillAmount));
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
				} else if (bill.getWorkorder().getType().getId().intValue() == WorkorderTypes.Machine_Hiring.getId()) {

					Double totalBillAmount = 0.0;
					Double totalFuelDebitAmount = 0.0;

					List<WorkorderHiringMachineWorkItemMapping> machineItems = woHireMachineDao
							.fetchWorkorderHiringMachineWorkItemMapping(bill.getWorkorder().getId());
					Set<Long> distinctMachineItemIds = new HashSet<>();
					if (machineItems != null) {
						machineItems.forEach(obj -> {
							distinctMachineItemIds.add(obj.getId());
						});
					}
					List<WorkorderHiringMachineRateDetails> machineRateDetailsList = woHireMachineDao
							.fetchHiringMachineItemRateDetailsByWorkItemIds(distinctMachineItemIds);
					Map<Long, MachineryRunningMode> categoryRunModeMap = new HashMap<Long, MachineryRunningMode>();

					List<BillMachineCategoryResponse> machineCategories = new ArrayList<>();
					if (machineItems != null) {
						for (WorkorderHiringMachineWorkItemMapping hireMachineItem : machineItems) {
							categoryRunModeMap.put(hireMachineItem.getMachineCatgeoryId(),
									hireMachineItem.getRunningMode());
							machineCategories.add(new BillMachineCategoryResponse(
									hireMachineItem.getMachineCatgeoryId(),
									hireMachineItem.getMachineCategory().getName(), hireMachineItem.getMachineCount(),
									new ArrayList<>(), hireMachineItem.getId(), hireMachineItem.getDescription(),
									hireMachineItem.getMachineCategory().getIsMultiFuel() != null
											? hireMachineItem.getMachineCategory().getIsMultiFuel()
											: false,
									hireMachineItem.getMachineCategory().getPrimaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getPrimaryReadingUnit().getId()
											: null,
									hireMachineItem.getMachineCategory().getPrimaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getPrimaryReadingUnit().getName()
											: null,
									hireMachineItem.getMachineCategory().getSecondaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getSecondaryReadingUnit().getId()
											: null,
									hireMachineItem.getMachineCategory().getSecondaryReadingUnit() != null
											? hireMachineItem.getMachineCategory().getSecondaryReadingUnit().getName()
											: null,
									hireMachineItem.getRunningMode()));
						}
					}
					List<BillMachineMapping> billMachines = billMachineDao.fetchBillMachineMapping(bill.getId());

					if (billMachines != null) {

						Set<Long> machineIds = new HashSet<>();
						billMachines.forEach(bm -> machineIds.add(bm.getMachineId()));

						List<MachineDPR> machinesDPRList = dprDao.fetchMachineDprList(machineIds, bill.getFromDate(),
								bill.getToDate(), bill.getSiteId());

						Date dieselFromDate = bill.getFromDate();
						Date dieselToDate = bill.getToDate();
						search.setFromDate(dieselFromDate);
						search.setToDate(dieselToDate);
						List<DieselRateMapping> dieselRates = dieselRateDao.fetchDieselRates(search);

						List<WorkorderHiringMachineWorkItemMapping> hiringMachineWorkItems = null;
						WorkorderHiringMachineWork hiringMachineWork = woHireMachineDao
								.fetchWorkorderHiringMachineWork(bill.getWorkorder().getId());
						if (hiringMachineWork != null)
							hiringMachineWorkItems = machineItems;

//						Site site = siteDao.fetchSiteById(search.getSiteId());
						List<IssueSlipItem> issueSlipItems = issueSlipItemDao.fetchIssueSlipItems(dieselFromDate,
								dieselToDate, machineIds);

						for (BillMachineMapping bmm : billMachines) {

							Double fuelTakenFromStore = 0.0;
							Double weightedDieselRate = null;
							Integer dieselRateCount = 0;
							if (dieselRates != null) {
								for (DieselRateMapping dieselRate : dieselRates) {
									Date rateDate = DateUtil.dateWithoutTime(dieselRate.getDate());
									Date billFromDate = DateUtil.dateWithoutTime(bmm.getFromDate());
									Date billToDate = DateUtil.dateWithoutTime(bmm.getToDate());
									if (rateDate.equals(billFromDate) || rateDate.equals(billToDate)
											|| (rateDate.after(billFromDate) && rateDate.before(billToDate))) {
										if (weightedDieselRate == null) {
											dieselRateCount++;
											weightedDieselRate = dieselRate.getRate();
										} else {
											dieselRateCount++;
											weightedDieselRate = (weightedDieselRate + dieselRate.getRate());
										}
									}
								}
							}
							if (weightedDieselRate == null)
								weightedDieselRate = 0.0;
							if (weightedDieselRate > 0.0) {
								weightedDieselRate = weightedDieselRate / dieselRateCount;
							}

							BillMachineMapResponse mappedMachine = null;
							Long categoryId = null;
							UnitMaster primaryEngineUnit = null;
							UnitMaster secondaryEngineUnit = null;
							Boolean isMultiFuel = false;
							if (bmm.getMachineType() == MachineType.Equipment.ordinal()) {
								Equipment machine = machineDao.fetchEquipment(bmm.getMachineId());
								categoryId = machine.getCategory().getId();

								isMultiFuel = machine.getCategory().getIsMultiFuel() != null
										? machine.getCategory().getIsMultiFuel()
										: false;

								if (machine.getCategory().getPrimaryReadingUnit() != null) {
									primaryEngineUnit = new UnitMaster(
											machine.getCategory().getPrimaryReadingUnit().getId().shortValue(),
											machine.getCategory().getPrimaryReadingUnit().getName(), true);
								} else {
									primaryEngineUnit = new UnitMaster();
								}
								if (machine.getCategory().getSecondaryReadingUnit() != null) {
									secondaryEngineUnit = new UnitMaster(
											machine.getCategory().getSecondaryReadingUnit().getId().shortValue(),
											machine.getCategory().getSecondaryReadingUnit().getName(), true);
								} else {
									secondaryEngineUnit = new UnitMaster();
								}

								mappedMachine = new BillMachineMapResponse(bmm.getId(), machine.getId(),
										machine.getAssetCode(), machine.getRegNo(),
										(byte) MachineType.Equipment.ordinal(), bmm.getFromDate(), bmm.getToDate());

								if (issueSlipItems != null) {
									for (IssueSlipItem issueSlipItem : issueSlipItems) {
										Date rateDate = DateUtil
												.dateWithoutTime(issueSlipItem.getIssueSlip().getDateOn());
										Date billFromDate = DateUtil.dateWithoutTime(bmm.getFromDate());
										Date billToDate = DateUtil.dateWithoutTime(bmm.getToDate());

										if (bmm.getMachineId().equals(issueSlipItem.getEquipmentId()) && (rateDate
												.equals(billFromDate) || rateDate.equals(billToDate)
												|| (rateDate.after(billFromDate) && rateDate.before(billToDate)))) {
											Double issuedQuantity = issueSlipItem.getIssuedQuantity() != null
													? issueSlipItem.getIssuedQuantity()
													: 0;
											Double returnedQuantity = issueSlipItem.getReturnedQuantity() != null
													? issueSlipItem.getReturnedQuantity()
													: 0;
											fuelTakenFromStore += (issuedQuantity - returnedQuantity);
										}
									}
								}
							}

							Double netTotalPrimaryActualReading = 0.0;
							Double netTotalSecondaryActualReading = 0.0;
							Integer totalTripsTaken = 0;
							if (mappedMachine != null && categoryId != null) {
								for (BillMachineCategoryResponse categoryResponse : machineCategories) {
									if ((categoryResponse.getId().equals(categoryId)
											&& bmm.getWoHiringMachineWorkItemId() == null)
											|| (categoryResponse.getId().equals(categoryId)
													&& categoryResponse.getWoHiringMachineWorkItemId() != null
													&& bmm.getWoHiringMachineWorkItemId() != null
													&& categoryResponse.getWoHiringMachineWorkItemId()
															.equals(bmm.getWoHiringMachineWorkItemId()))) {
										List<MachineDprGetResponse> machineDprList = new ArrayList<>();
										Date machineFromDate = DateUtil.dateWithoutTime(mappedMachine.getFromDate());
										Date machineToDate = DateUtil.dateWithoutTime(mappedMachine.getToDate());
										Date dprDate = machineFromDate;
										float totalWorkingDays = 0f;
										float totalPresentDaysInNight = 0f;
										float totalPresentDaysInDay = 0f;
										Map<Integer, Double> totalBreakdownHoursInDayMap = new HashMap<Integer, Double>();
										Map<Integer, Double> totalBreakdownHoursInNightMap = new HashMap<Integer, Double>();

										while (!dprDate.after(machineToDate)) {

											if (machinesDPRList != null) {
												MachineDprGetResponse dateDpr2 = new MachineDprGetResponse(null,
														dprDate, null, null, null, null, null, null, null, null, null,
														null, null, null, null, null, null, null, null);
												for (MachineDPR machineDpr : machinesDPRList) {
													MachineDprGetResponse dateDpr = new MachineDprGetResponse(null,
															dprDate, null, null, null, null, null, null, null, null,
															null, null, null, null, null, null, null, null, null);
													if (machineDpr.getMachineId().equals(mappedMachine.getMachineId())
															&& machineDpr.getMachineType()
																	.equals(mappedMachine.getMachineType())
															&& dprDate.equals(
																	DateUtil.dateWithoutTime(machineDpr.getDated()))) {

														if (bmm.getWoHiringMachineWorkItemId() != null) {
															Boolean hasDifferentRunningMode = false;
															for (WorkorderHiringMachineWorkItemMapping hireMachineItem : machineItems) {
																if (hireMachineItem.getId()
																		.equals(bmm.getWoHiringMachineWorkItemId())
																		&& !hireMachineItem.getRunningMode()
																				.equals(machineDpr.getRunningMode())) {
																	hasDifferentRunningMode = true;
																}
															}
															if (hasDifferentRunningMode) {
																continue;
															}
														} else {
															if (!categoryRunModeMap.get(categoryId)
																	.equals(machineDpr.getRunningMode())) {
																continue;
															}
														}

														dateDpr.setPrimaryOpeningMeterReading(
																machineDpr.getPrimaryOpeningMeterReading());
														dateDpr.setPrimaryClosingMeterReading(
																machineDpr.getPrimaryClosingMeterReading());
														dateDpr.setSecondaryOpeningMeterReading(
																machineDpr.getSecondaryOpeningMeterReading());
														dateDpr.setSecondaryClosingMeterReading(
																machineDpr.getSecondaryClosingMeterReading());
														dateDpr.setPrimaryOpeningActualReading(
																machineDpr.getPrimaryOpeningActualReading());
														dateDpr.setPrimaryClosingActualReading(
																machineDpr.getPrimaryClosingActualReading());
														dateDpr.setSecondaryOpeningActualReading(
																machineDpr.getSecondaryOpeningActualReading());
														dateDpr.setSecondaryClosingActualReading(
																machineDpr.getSecondaryClosingActualReading());

														Integer noOfDaysInMonth = DateUtil
																.getNoOfDaysInTheCurrentMonth(machineDpr.getDated());

														if (machineDpr.getRunningMode()
																.equals(MachineryRunningMode.TRIP)) {
															totalTripsTaken += machineDpr.getTripCount();
															if (machineDpr.getAttendanceStatus() != null
																	&& machineDpr.getAttendanceStatus().equals(
																			MachineryAttendanceStatus.HALFDAY)) {
																totalPresentDaysInDay += 0.5d;
															} else {
																totalPresentDaysInDay += 1d;
															}
														} else if (machineDpr.getRunningMode()
																.equals(MachineryRunningMode.SHIFTS)
																&& machineDpr.getShift() != null) {

															if (machineDpr.getShift().equals(MachineryShifts.DAYTIME)) {

																if (machineDpr.getAttendanceStatus() != null
																		&& machineDpr.getAttendanceStatus().equals(
																				MachineryAttendanceStatus.HALFDAY)) {
																	totalPresentDaysInDay += 0.5d;
																} else {
																	totalPresentDaysInDay += 1d;

																}
																if (machineDpr.getBreakdownHours() != null) {
																	if (totalBreakdownHoursInDayMap != null
																			&& totalBreakdownHoursInDayMap
																					.containsKey(noOfDaysInMonth)) {

																		totalBreakdownHoursInDayMap.put(noOfDaysInMonth,
																				totalBreakdownHoursInDayMap
																						.get(noOfDaysInMonth)
																						+ machineDpr
																								.getBreakdownHours());
																	} else {
																		totalBreakdownHoursInDayMap.put(noOfDaysInMonth,
																				machineDpr.getBreakdownHours());
																	}
																}
															} else {
																if (machineDpr.getAttendanceStatus() != null
																		&& machineDpr.getAttendanceStatus().equals(
																				MachineryAttendanceStatus.HALFDAY)) {
																	totalPresentDaysInNight += 0.5d;
																} else {
																	totalPresentDaysInNight += 1d;
																}
																if (machineDpr.getBreakdownHours() != null) {
																	if (totalBreakdownHoursInNightMap != null
																			&& totalBreakdownHoursInNightMap
																					.containsKey(noOfDaysInMonth)) {
																		totalBreakdownHoursInNightMap.put(
																				noOfDaysInMonth,
																				totalBreakdownHoursInNightMap
																						.get(noOfDaysInMonth)
																						+ machineDpr
																								.getBreakdownHours());
																	} else {
																		totalBreakdownHoursInNightMap.put(
																				noOfDaysInMonth,
																				machineDpr.getBreakdownHours());
																	}
																}
															}
														}

														if (machineDpr.getPrimaryOpeningActualReading() != null
																&& machineDpr
																		.getPrimaryClosingActualReading() != null) {
															Double netPrimaryActualReading = machineDpr
																	.getPrimaryClosingActualReading()
																	- machineDpr.getPrimaryOpeningActualReading();
															dateDpr.setNetPrimaryActualReading(netPrimaryActualReading);
															netTotalPrimaryActualReading += netPrimaryActualReading;
														}
														if (machineDpr.getSecondaryOpeningActualReading() != null
																&& machineDpr
																		.getSecondaryClosingActualReading() != null) {
															Double netSecondaryActualReading = machineDpr
																	.getSecondaryClosingActualReading()
																	- machineDpr.getSecondaryOpeningActualReading();
															dateDpr.setNetSecondaryActualReading(
																	netSecondaryActualReading);
															netTotalSecondaryActualReading += netSecondaryActualReading;
														}

														if (issueSlipItems != null) {

															for (IssueSlipItem issueSlipItem : issueSlipItems) {

																Date rateDate = DateUtil.dateWithoutTime(
																		issueSlipItem.getIssueSlip().getDateOn());
																if (bmm.getMachineType() == MachineType.Equipment
																		.ordinal()) {

																	if (bmm.getMachineId()
																			.equals(issueSlipItem.getEquipmentId())
																			&& rateDate.equals(dprDate)) {
																		Double issuedQuantity = issueSlipItem
																				.getIssuedQuantity() != null
																						? issueSlipItem
																								.getIssuedQuantity()
																						: 0;
																		Double returnedQuantity = issueSlipItem
																				.getReturnedQuantity() != null
																						? issueSlipItem
																								.getReturnedQuantity()
																						: 0;
																		Double netFuelTaken = (issuedQuantity
																				- returnedQuantity);
																		dateDpr.setStoreFuelTransactionQuantity(
																				netFuelTaken);
																	}
																} else if (bmm.getMachineType() == MachineType.Plant
																		.ordinal()) {

																	if (bmm.getMachineId()
																			.equals(issueSlipItem.getPlantId())
																			&& rateDate.equals(dprDate)) {
																		Double issuedQuantity = issueSlipItem
																				.getIssuedQuantity() != null
																						? issueSlipItem
																								.getIssuedQuantity()
																						: 0;
																		Double returnedQuantity = issueSlipItem
																				.getReturnedQuantity() != null
																						? issueSlipItem
																								.getReturnedQuantity()
																						: 0;
																		Double netFuelTaken = (issuedQuantity
																				- returnedQuantity);
																		dateDpr.setStoreFuelTransactionQuantity(
																				netFuelTaken);
																	}

																}

															}

														}

														dateDpr.setRunningMode(machineDpr.getRunningMode());
														dateDpr.setShift(machineDpr.getShift());
														dateDpr.setTripCount(machineDpr.getTripCount());
														dateDpr.setStatus(machineDpr.getAttendanceStatus());
														dateDpr.setRemarks(machineDpr.getRemarks());
														dateDpr.setBreakdownHours(machineDpr.getBreakdownHours());
														machineDprList.add(dateDpr);

													}
												}
												if (machineDprList != null) {
													Map<Date, List<MachineDprGetResponse>> collect = machineDprList
															.stream().collect(Collectors.groupingBy(o -> o.getDated()));
													if (!collect.containsKey(dprDate)) {
														machineDprList.add(dateDpr2);
													}
												}
											}
											totalWorkingDays++;
											dprDate = DateUtil.nextDateWithoutTime(dprDate);
										}

										mappedMachine.setNetTotalPrimaryActualReading(netTotalPrimaryActualReading);
										mappedMachine.setNetTotalSecondaryActualReading(netTotalSecondaryActualReading);
										mappedMachine.setTotalPresentInDayTime(totalPresentDaysInDay);
										mappedMachine.setTotalPresentInNightTime(totalPresentDaysInNight);
										mappedMachine.setTotalWorkingDays(totalWorkingDays);
										mappedMachine
												.setTotalAbsentInNightTime(totalWorkingDays - totalPresentDaysInNight);
										mappedMachine.setTotalAbsentInDayTime(totalWorkingDays - totalPresentDaysInDay);
										mappedMachine.setTripCount(totalTripsTaken);
										mappedMachine
												.setTotalBreakdownHoursInDayShift(totalBreakdownHoursInDayMap != null
														? totalBreakdownHoursInDayMap.values().stream()
																.mapToDouble(x -> x).sum()
														: 0);
										mappedMachine.setTotalBreakdownHoursInNightShift(
												totalBreakdownHoursInNightMap != null
														? totalBreakdownHoursInNightMap.values().stream()
																.mapToDouble(x -> x).sum()
														: 0);
										mappedMachine
												.setTotalBreakdownHours(mappedMachine.getTotalBreakdownHoursInDayShift()
														+ mappedMachine.getTotalBreakdownHoursInNightShift());
										Double machineRateInDayTime = 0.0;
										Double machineRateInNightTime = 0.0;
										if (machineRateDetailsList != null) {
											for (WorkorderHiringMachineRateDetails rateDetail : machineRateDetailsList) {
												if ((rateDetail.getMachineItem().getMachineCatgeoryId()
														.equals(categoryId)
														&& categoryResponse.getWoHiringMachineWorkItemId() == null)
														|| (rateDetail.getMachineItem().getMachineCatgeoryId()
																.equals(categoryId)
																&& categoryResponse.getWoHiringMachineWorkItemId()
																		.equals(rateDetail
																				.getWoHiringMachineItemId()))) {
													if (rateDetail.getMachineItem().getRunningMode()
															.equals(MachineryRunningMode.TRIP)) {
														mappedMachine.setIsShiftBased(false);
														machineRateInDayTime = rateDetail.getRate();
													} else {
														mappedMachine.setIsShiftBased(true);
														if (rateDetail.getShift() != null && rateDetail.getShift()
																.equals(MachineryShifts.DAYTIME)) {
															machineRateInDayTime = rateDetail.getRate();
														} else {
															machineRateInNightTime = rateDetail.getRate();
														}
													}
												}
											}
										}
										mappedMachine.setMachineRateAsPerWorkorderDayShift(machineRateInDayTime);
										mappedMachine.setMachineRateAsPerWorkorderNightShift(machineRateInNightTime);
										mappedMachine.setDprList(machineDprList);
										if (hiringMachineWorkItems != null) {
											for (WorkorderHiringMachineWorkItemMapping hmItem : hiringMachineWorkItems) {
												if ((hmItem.getMachineCatgeoryId().equals(categoryId)
														&& categoryResponse.getWoHiringMachineWorkItemId() == null)
														|| (hmItem.getMachineCatgeoryId().equals(categoryId)
																&& categoryResponse.getWoHiringMachineWorkItemId()
																		.equals(hmItem.getId()))) {
													mappedMachine
															.setPrimaryEngineMileage(hmItem.getPrimaryEngineMileage());
													mappedMachine
															.setPrimaryEngineMileageUnit(primaryEngineUnit.getName());
													Double primaryFuelUsed = 0.0;
													Double secondaryFuelUsed = 0.0;
//													FUEL MILEAGE
													if (primaryEngineUnit.getName() != null
															&& (primaryEngineUnit.getName().toLowerCase().contains("hr")
																	|| primaryEngineUnit.getName().toLowerCase()
																			.contains("hour"))) {

														primaryFuelUsed = netTotalPrimaryActualReading
																* (hmItem.getPrimaryEngineMileage() != null
																		&& hmItem.getPrimaryEngineMileage() != 0
																				? hmItem.getPrimaryEngineMileage()
																				: 0);

													} else {

														primaryFuelUsed = netTotalPrimaryActualReading
																/ (hmItem.getPrimaryEngineMileage() != null
																		&& hmItem.getPrimaryEngineMileage() != 0
																				? hmItem.getPrimaryEngineMileage()
																				: 1);
													}
													Double totalFuelUsed = 0.0;
													totalFuelUsed += primaryFuelUsed;
													if (isMultiFuel) {
														mappedMachine.setSecondaryEngineMileage(
																hmItem.getSecondaryEngineMileage());
														mappedMachine.setSecondaryEngineMileageUnit(
																secondaryEngineUnit.getName());
														if (secondaryEngineUnit.getName() != null
																&& (secondaryEngineUnit.getName().toLowerCase()
																		.contains("hr")
																		|| secondaryEngineUnit.getName().toLowerCase()
																				.contains("hour"))) {

															secondaryFuelUsed = netTotalSecondaryActualReading
																	* (hmItem.getSecondaryEngineMileage() != null
																			&& hmItem.getSecondaryEngineMileage() != 0
																					? hmItem.getSecondaryEngineMileage()
																					: 0);

														} else {

															secondaryFuelUsed = netTotalSecondaryActualReading
																	/ (hmItem.getSecondaryEngineMileage() != null
																			&& hmItem.getSecondaryEngineMileage() != 0
																					? hmItem.getSecondaryEngineMileage()
																					: 1);
														}
														totalFuelUsed += secondaryFuelUsed;
													}
													Double handlingCharge = hmItem.getFuelHandlingCharge() != null
															? weightedDieselRate * hmItem.getFuelHandlingCharge() / 100
															: 0.0;
													mappedMachine.setFuelRateIncludingHandlingCharge(
															weightedDieselRate + handlingCharge);
													mappedMachine.setFuelDebitAmount(
															mappedMachine.getFuelRateIncludingHandlingCharge()
																	* fuelTakenFromStore);

													mappedMachine.setTotalFuelAsPerWorkorder(totalFuelUsed);
													mappedMachine.setFuelTakenFromStore(fuelTakenFromStore);
													Double excessFuelTaken = fuelTakenFromStore - totalFuelUsed;
													mappedMachine.setExcessFuelTaken(
															excessFuelTaken > 0.0 ? excessFuelTaken : 0.0);

													Double dieselEscalationPrice = 0.0;

													if (!hmItem.getDieselInclusive()) {
														if (hiringMachineWork.getDieselRate() != null
																&& hiringMachineWork.getDieselRate() > 0.0) {

															if (!hiringMachineWork.getDieselRate()
																	.equals(weightedDieselRate)) {

																mappedMachine.setDieselRateAsPerWorkorder(
																		hiringMachineWork.getDieselRate());
																mappedMachine
																		.setFuelRateAsPerWorkorderIncludingHandlingCharge(
																				hmItem.getFuelHandlingCharge() != null
																						&& hmItem
																								.getFuelHandlingCharge() > 0
																										? hiringMachineWork
																												.getDieselRate()
																												+ (hmItem
																														.getFuelHandlingCharge()
																														* hiringMachineWork
																																.getDieselRate())
																														/ 100
																										: hiringMachineWork
																												.getDieselRate());

																mappedMachine.setTotalFuelRateAsPerWorkorder(
																		totalFuelUsed * mappedMachine
																				.getFuelRateAsPerWorkorderIncludingHandlingCharge());

																mappedMachine.setTotalFuelRateAsPerWeightedRate(
																		totalFuelUsed * mappedMachine
																				.getFuelRateIncludingHandlingCharge());

																mappedMachine.setDieselEscalationPrice(mappedMachine
																		.getTotalFuelRateAsPerWeightedRate()
																		- mappedMachine
																				.getTotalFuelRateAsPerWorkorder());

																dieselEscalationPrice = mappedMachine
																		.getDieselEscalationPrice();

															}

														}
													}

													if (hmItem.getDieselInclusive()) {

														if (excessFuelTaken > 0) {

															if (weightedDieselRate <= 0.0) {
																DateFormat formatter = new SimpleDateFormat(
																		"dd/MM/yyyy");
																return new CustomResponse(Responses.FORBIDDEN.getCode(),
																		null,
																		"Diesel rates not present for bills for date range : "
																				+ formatter.format(bmm.getFromDate())
																				+ " - "
																				+ formatter.format(bmm.getToDate()));
															}

															mappedMachine.setFuelDebitAmount(
																	mappedMachine.getFuelRateIncludingHandlingCharge()
																			* excessFuelTaken);
														} else {
															excessFuelTaken = 0d;
															mappedMachine.setFuelDebitAmount(
																	mappedMachine.getFuelRateIncludingHandlingCharge()
																			* excessFuelTaken);
														}

													}

													totalFuelDebitAmount += mappedMachine.getFuelDebitAmount();
													mappedMachine
															.setMachineRateAsPerWorkorderDayShift(machineRateInDayTime);
													mappedMachine.setMachineRateAsPerWorkorderNightShift(
															machineRateInNightTime);

													mappedMachine.setMachineRateUnitAsPerWorkorder(
															hmItem.getUnit().getName());
													mappedMachine.setTotalDaysAsPerRateUnit(DateUtil
															.getNoOfDaysInTheCurrentMonth(mappedMachine.getFromDate())
															.doubleValue());

													Double thresholdAmount = 0.0;

													if (hmItem.getThresholdApplicable()) {
														mappedMachine.setIsThresholdApplicable(true);
														mappedMachine
																.setThresholdQuantity(hmItem.getThresholdQuantity());
														mappedMachine.setThresholdQuantityUnit(
																hmItem.getThresholdUnit().getName());
														if (mappedMachine
																.getNetTotalPrimaryActualReading() > mappedMachine
																		.getThresholdQuantity()) {
															thresholdAmount = (mappedMachine
																	.getNetTotalPrimaryActualReading()
																	- mappedMachine.getThresholdQuantity())
																	* hmItem.getPostThresholdRatePerUnit();
														}
													} else {
														mappedMachine.setIsThresholdApplicable(false);
													}

													Double machineTotalAmountInDay = 0.0;
													Double machineTotalAmountInNight = 0.0;
													Double machineTotalBreakdownHoursAmountInDay = 0.0;
													Double machineTotalBreakdownHoursAmountInNight = 0.0;

//													if (mappedMachine.getIsShiftBased()) {
//
//														if (totalBreakdownHoursInDayMap != null) {
//															for (Map.Entry<Integer, Double> dayObj : totalBreakdownHoursInDayMap
//																	.entrySet()) {
//
//																machineTotalBreakdownHoursAmountInDay += (mappedMachine
//																		.getMachineRateAsPerWorkorderDayShift()
//																		/ (dayObj.getKey() * 12)) * dayObj.getValue();
//															}
//														}
//
//														if (totalBreakdownHoursInDayMap != null) {
//															for (Map.Entry<Integer, Double> dayObj : totalBreakdownHoursInNightMap
//																	.entrySet()) {
//
//																machineTotalBreakdownHoursAmountInNight += (mappedMachine
//																		.getMachineRateAsPerWorkorderNightShift()
//																		/ (dayObj.getKey() * 12)) * dayObj.getValue();
//															}
//														}
//													}

													if (mappedMachine.getIsShiftBased()) {

														if (mappedMachine.getTotalBreakdownHoursInDayShift() != null) {

															machineTotalBreakdownHoursAmountInDay += (mappedMachine
																	.getMachineRateAsPerWorkorderDayShift()
																	/ (mappedMachine.getTotalDaysAsPerRateUnit()
																			* hmItem.getShiftSchedule()
																					.getShiftHours()))
																	* mappedMachine.getTotalBreakdownHoursInDayShift();

														}

														if (mappedMachine
																.getTotalBreakdownHoursInNightShift() != null) {
															machineTotalBreakdownHoursAmountInNight += (mappedMachine
																	.getMachineRateAsPerWorkorderNightShift()
																	/ (mappedMachine.getTotalDaysAsPerRateUnit()
																			* hmItem.getShiftSchedule()
																					.getShiftHours()))
																	* mappedMachine
																			.getTotalBreakdownHoursInNightShift();

														}
													}

													mappedMachine.setTotalBreakdownHoursAmount(
															machineTotalBreakdownHoursAmountInDay
																	+ machineTotalBreakdownHoursAmountInNight);

													if (mappedMachine.getIsShiftBased()) {
														machineTotalAmountInDay = (totalPresentDaysInDay
																/ mappedMachine.getTotalDaysAsPerRateUnit())
																* mappedMachine.getMachineRateAsPerWorkorderDayShift();

														machineTotalAmountInDay -= machineTotalBreakdownHoursAmountInDay;

														machineTotalAmountInNight = (totalPresentDaysInNight
																/ mappedMachine.getTotalDaysAsPerRateUnit())
																* mappedMachine
																		.getMachineRateAsPerWorkorderNightShift();
														machineTotalAmountInNight -= machineTotalBreakdownHoursAmountInNight;
													} else {

														machineTotalAmountInDay = totalTripsTaken
																* mappedMachine.getMachineRateAsPerWorkorderDayShift();
													}
													mappedMachine.setTotalAmount(
															machineTotalAmountInDay + machineTotalAmountInNight
																	+ thresholdAmount + dieselEscalationPrice);

													totalBillAmount += mappedMachine.getTotalAmount();
												}
											}
										}

										categoryResponse.getMachines().add(mappedMachine);
									}
								}
							}
						}
					}

					BillAndItemsDTO result = new BillAndItemsDTO(bill.getId(),
							setObject.billTypeEntityToDto(bill.getType()), bill.getBillNo(), bill.getFromDate(),
							bill.getToDate(), null, null, setObject.workorderEntityToDto(bill.getWorkorder()),
							bill.getTaxInvoiceNo(), bill.getTaxInvoiceDate(), bill.getApplicableIgst(),
							bill.getIsIgstOnly(), setObject.engineStateEntityToDto(bill.getState()), bill.getSiteId(),
							bill.getIsActive(), bill.getCreatedOn(), bill.getCreatedBy(), bill.getModifiedOn(),
							bill.getModifiedBy());
					result.setFuelDebitAmount(totalFuelDebitAmount);

					result.setMachineCategories(machineCategories);

//					for initial state obj
					EngineStateDTO entityInitialState = engineService
							.getEntityInitialState(EntitiesEnum.BILL.getEntityId(), search.getCompanyId());
					if (result.getState().getId().equals(entityInitialState.getId())) {
						result.setBillNoText(result.getState().getAlias() + "-" + result.getType().getName() + "/"
								+ result.getBillNo());
					} else {
						result.setBillNoText(result.getType().getName() + "/" + result.getBillNo());
					}

					result.setBilledAmount(totalBillAmount);
					if (result.getApplicableIgst() != null) {
						Double gst = result.getApplicableIgst() / 100;
						Double billedAmountAfterGst = totalBillAmount + (totalBillAmount * gst);
						result.setBilledAmountAfterGst(billedAmountAfterGst);
					} else {
						result.setBilledAmountAfterGst(totalBillAmount);
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());

				} else if (bill.getWorkorder().getType().getId().intValue() == WorkorderTypes.Consultancy.getId()) {

					List<WorkorderConsultantWorkItemMapping> workItems = consultantWorkDao
							.fetchWorkorderConsultantWorkItemsByWorkorderId(search.getWorkorderId());
					Double workorderAmount = 0.0;

					if (workItems != null) {
						for (WorkorderConsultantWorkItemMapping obj : workItems) {
							workorderAmount += (obj.getRate() * obj.getQuantity());
						}
					}

					List<WorkorderPayMilestone> woPayMilestones = woPayMilestoneDao
							.fetchWorkorderWorkorderPayMilestones(bill.getWorkorder().getId());

					List<BillPayMilestones> woBillPayMilestones = billPayMilestoneDao
							.fetchBillPayMilestonesByWorkorderId(bill.getWorkorder().getId());

					Set<Long> woPayMileIdsSavedCurrentBill = new HashSet<>();
					Set<Long> woPayMileIdsSavedOtherBill = new HashSet<>();

					if (woBillPayMilestones != null) {
						for (BillPayMilestones bpm : woBillPayMilestones) {
							if (bpm.getBillId().equals(bill.getId())) {
								woPayMileIdsSavedCurrentBill.add(bpm.getWorkorderMilestoneId());
							} else {
								woPayMileIdsSavedOtherBill.add(bpm.getWorkorderMilestoneId());
							}
						}
					}

					Double totalBillAmount = 0.0;

					List<BillPayMilestoneResponse> billPayMilestones = new ArrayList<>();

					if (woPayMilestones != null) {
						for (WorkorderPayMilestone woPayMilestone : woPayMilestones) {

							Double amount = woPayMilestone.getValue();
							if (woPayMilestone.getIsPercentage()) {
								amount = (amount * workorderAmount) / 100;
							}
							if (woPayMileIdsSavedOtherBill.contains(woPayMilestone.getId()))
								continue;
							Boolean utilisedInCurrent = woPayMileIdsSavedCurrentBill.contains(woPayMilestone.getId())
									? true
									: false;
							if (utilisedInCurrent) {
								totalBillAmount += amount;
							}
							billPayMilestones.add(new BillPayMilestoneResponse(null, woPayMilestone.getId(),
									woPayMilestone.getDescription(), woPayMilestone.getIsPercentage(),
									woPayMilestone.getValue(), amount, utilisedInCurrent));
						}
					}

					BillAndItemsDTO result = new BillAndItemsDTO(bill.getId(),
							setObject.billTypeEntityToDto(bill.getType()), bill.getBillNo(), bill.getFromDate(),
							bill.getToDate(), null, null, setObject.workorderEntityToDto(bill.getWorkorder()),
							bill.getTaxInvoiceNo(), bill.getTaxInvoiceDate(), bill.getApplicableIgst(),
							bill.getIsIgstOnly(), setObject.engineStateEntityToDto(bill.getState()), bill.getSiteId(),
							bill.getIsActive(), bill.getCreatedOn(), bill.getCreatedBy(), bill.getModifiedOn(),
							bill.getModifiedBy());

					result.setBillPayMilestones(billPayMilestones);

//					for initial state obj
					EngineStateDTO entityInitialState = engineService
							.getEntityInitialState(EntitiesEnum.BILL.getEntityId(), search.getCompanyId());
					if (result.getState().getId().equals(entityInitialState.getId())) {
						result.setBillNoText(result.getState().getAlias() + "-" + result.getType().getName() + "/"
								+ result.getBillNo());
					} else {
						result.setBillNoText(result.getType().getName() + "/" + result.getBillNo());
					}

					result.setBilledAmount(totalBillAmount);
					if (result.getApplicableIgst() != null) {
						Double gst = result.getApplicableIgst() / 100;
						Double billedAmountAfterGst = totalBillAmount + (totalBillAmount * gst);
						result.setBilledAmountAfterGst(billedAmountAfterGst);
					} else {
						result.setBilledAmountAfterGst(totalBillAmount);
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());

				}
			}

			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addOrUpdateBill(BillDTO billObj) {

		try {
			Workorder workorder = workorderDao.fetchWorkorderById(billObj.getWorkorder().getId());
			if (workorder == null || workorder.getId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide appropriate workorder id.");
			}

//			if (workorder.getAmendWorkorderInvocation() != null) {
//				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//						"Bill can't be generated for AmendWorkorder.");
//			}

			if ((DateUtil.dateWithoutTime(billObj.getFromDate())
					.compareTo(DateUtil.dateWithoutTime(workorder.getStartDate())) < 0
					|| (workorder.getEndDate() != null && DateUtil.dateWithoutTime(billObj.getFromDate())
							.compareTo(DateUtil.dateWithoutTime(workorder.getEndDate())) > 0))
					&& workorder.getFromAmendmentId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Bill from-date must be lie between workorder dates.");
			}

			if ((DateUtil.dateWithoutTime(billObj.getToDate())
					.compareTo(DateUtil.dateWithoutTime(workorder.getStartDate())) < 0
					|| (workorder.getEndDate() != null && DateUtil.dateWithoutTime(billObj.getToDate())
							.compareTo(DateUtil.dateWithoutTime(workorder.getEndDate())) > 0))
					&& workorder.getFromAmendmentId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Bill to-date must be lie between workorder dates.");
			}

			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.BILL.getEntityId(), billObj.getCompanyId());
			Set<Integer> entityFinalStateIds = new HashSet<>();
			Set<Integer> entityEditableStateIds = new HashSet<>();
			Integer entityInitialStateId = null;
			if (entityStateMaps != null) {
				for (EntityStateMapDTO esMapItr : entityStateMaps) {
					if (entityInitialStateId == null && esMapItr.getIsInitial()) {
						entityInitialStateId = esMapItr.getStateId();
					}
					if (esMapItr.getIsEditable() != null && esMapItr.getIsEditable()) {
						entityEditableStateIds.add(esMapItr.getStateId());
					}
					if (esMapItr.getIsFinal() != null && esMapItr.getIsFinal()) {
						entityFinalStateIds.add(esMapItr.getStateId());
					}
				}
			}
			if (entityFinalStateIds.size() < 1) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Please map Bill with its final state.");
			}

			if (workorder.getType().getId() == WorkorderTypes.Structure.getId()) {

				List<WorkorderBoqWorkQtyMapping> woBoqs = woBoqWorkDao
						.fetchWoBoqWorkQtys(billObj.getWorkorder().getId());
				List<BillBoqQuantityItem> qtyItems = billItemDao
						.fetchBillBoqQuantityItemsByWorkorderId(billObj.getWorkorder().getId());
				if (billObj.getId() == null) {
					CustomResponse response = validationUtil.validateAddStructureBillObject(billObj, woBoqs, qtyItems);
					if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
						return response;
					Bill bill = setObject.billDtoToEntity(billObj);
					Bill lastBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(billObj.getWorkorder().getId(),
							bill.getType().getId());
					if (lastBill != null) {
						if (!entityFinalStateIds.contains(lastBill.getState().getId())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Previous bill is not in final state.");
						}
						if (!lastBill.getToDate().before(bill.getFromDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}
					}

					int lastBillNo = 0;
					if (lastBill == null && workorder.getFromAmendmentId() != null) {

						Bill fromAmendmentBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(
								workorder.getFromAmendmentId(), bill.getType().getId());
						if (fromAmendmentBill != null) {
							lastBillNo = fromAmendmentBill.getBillNo();
						}

						if (!bill.getFromDate().after(fromAmendmentBill.getToDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}

					} else {
						lastBillNo = lastBill != null ? lastBill.getBillNo() : 0;
					}

//					int lastBillNo = lastBill != null ? lastBill.getBillNo() : 0;

//					&& DateUtil.dateWithoutTime(workorder.getSystemBillStartDate())
//					.compareTo(DateUtil.dateWithoutTime(workorder.getStartDate())) >= 0

					if (lastBillNo == 0) {
						lastBillNo = workorder.getPreviousBillNo();
					}
					bill.setBillNo(++lastBillNo);
					bill.setCreatedOn(new Date());
					bill.setModifiedBy(bill.getCreatedBy());
					bill.setModifiedOn(new Date());
					bill.setState(new EngineState(entityInitialStateId));
					bill.setIsActive(true);
					Long billId = billDao.saveBill(bill);
					if (billId == null)
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Bill already exists with same info.");
					BillStateTransitionMapping billStateTransitionMap = new BillStateTransitionMapping(null, billId,
							bill.getState().getId(), true, new Date(), bill.getCreatedBy());
					billDao.mapBillStateTransition(billStateTransitionMap);
					BillBoqItemDTO billBoqDTO = billObj.getBoq();
					BillBoqItem billBoq = setObject.billBoqItemDtoToEntity(billBoqDTO);
					billBoq.setUnit(new Unit(billBoqDTO.getUnit().getId()));
					billBoq.setCreatedBy(bill.getCreatedBy());
					billBoq.setCreatedOn(new Date());
					billBoq.setModifiedBy(bill.getCreatedBy());
					billBoq.setModifiedOn(new Date());
					billBoq.setIsActive(true);
					billBoq.setSiteId(bill.getSiteId());
					billBoq.setBillId(billId);
					Long billBoqItemId = billItemDao.saveBillBoqItem(billBoq);
					if (billBoqItemId == null) {
						throw new RuntimeException("Already Exists.");
					}
					BillBoqQuantityItemDTO qtyItemDTO = billBoqDTO.getQtyItem();
					BillBoqQuantityItem qtyItem = setObject.billBoqQuantityItemDtoToEntity(qtyItemDTO);
					qtyItem.setCreatedBy(bill.getCreatedBy());
					qtyItem.setCreatedOn(new Date());
					qtyItem.setModifiedBy(bill.getCreatedBy());
					qtyItem.setModifiedOn(new Date());
					qtyItem.setIsActive(true);
					qtyItem.setSiteId(bill.getSiteId());
					qtyItem.setWorkorderId(bill.getWorkorder().getId());
					qtyItem.setBillBoqItemId(billBoqItemId);
					Long qtyItemId = billItemDao.saveBillBoqQuantityItem(qtyItem);
					if (qtyItemId == null) {
						throw new RuntimeException("Already Exists.");
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), billId, "Bill created.");
				} else {
					CustomResponse response = validationUtil.validateUpdateStructureBillObject(billObj, woBoqs,
							qtyItems);
					if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
						return response;
					Bill savedBill = billDao.fetchBillById(billObj.getId());
					if (!entityEditableStateIds.contains(savedBill.getState().getId())) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in editable state mode.");
					}
					Bill lastBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(billObj.getWorkorder().getId(),
							savedBill.getType().getId());
					if (lastBill != null && lastBill.getId().equals(savedBill.getId())) {
						lastBill = null;
					}
					BillTransac billTransac = setObject.billEntityToTransac(savedBill);
					boolean hasChangesInBill = false;
					if (!savedBill.getFromDate().equals(billObj.getFromDate())) {
						savedBill.setFromDate(billObj.getFromDate());
						hasChangesInBill = true;
					}
					if (!savedBill.getToDate().equals(billObj.getToDate())) {
						savedBill.setToDate(billObj.getToDate());
						hasChangesInBill = true;
					}
					if (lastBill != null) {
						if (!lastBill.getToDate().before(savedBill.getFromDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}
					}
					if ((savedBill.getTaxInvoiceNo() != null && billObj.getTaxInvoiceNo() == null)
							|| (savedBill.getTaxInvoiceNo() == null && billObj.getTaxInvoiceNo() != null)
							|| (savedBill.getTaxInvoiceNo() != null && billObj.getTaxInvoiceNo() != null
									&& !savedBill.getTaxInvoiceNo().equals(billObj.getTaxInvoiceNo()))) {
						savedBill.setTaxInvoiceNo(billObj.getTaxInvoiceNo());
						hasChangesInBill = true;
					}
					if ((savedBill.getTaxInvoiceDate() != null && billObj.getTaxInvoiceDate() == null)
							|| (savedBill.getTaxInvoiceDate() == null && billObj.getTaxInvoiceDate() != null)
							|| (savedBill.getTaxInvoiceDate() != null && billObj.getTaxInvoiceDate() != null
									&& !savedBill.getTaxInvoiceDate().equals(billObj.getTaxInvoiceDate()))) {
						savedBill.setTaxInvoiceDate(billObj.getTaxInvoiceDate());
						hasChangesInBill = true;
					}
					if ((savedBill.getApplicableIgst() != null && billObj.getApplicableIgst() == null)
							|| (savedBill.getApplicableIgst() == null && billObj.getApplicableIgst() != null)
							|| (savedBill.getApplicableIgst() != null && billObj.getApplicableIgst() != null
									&& !savedBill.getApplicableIgst().equals(billObj.getApplicableIgst()))) {
						savedBill.setApplicableIgst(billObj.getApplicableIgst());
						hasChangesInBill = true;
					}
					if ((savedBill.getIsIgstOnly() != null && billObj.getIsIgstOnly() == null)
							|| (savedBill.getIsIgstOnly() == null && billObj.getIsIgstOnly() != null)
							|| (savedBill.getIsIgstOnly() != null && billObj.getIsIgstOnly() != null
									&& !savedBill.getIsIgstOnly().equals(billObj.getIsIgstOnly()))) {
						savedBill.setIsIgstOnly(billObj.getIsIgstOnly());
						hasChangesInBill = true;
					}
					savedBill.setModifiedOn(new Date());
					savedBill.setModifiedBy(savedBill.getCreatedBy());
					if (hasChangesInBill) {
						boolean isBillUpdated = billDao.updateBill(savedBill);
						if (!isBillUpdated) {
							return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
									"Bill already exists with same info.");
						}
						billDao.saveBillTransac(billTransac);
					}
					BillBoqItem savedBillBoq = billItemDao.fetchBillBoqItemByBillId(savedBill.getId(),
							billObj.getBoq().getBoq().getId(), billObj.getBoq().getVendorDescription(),
							billObj.getBoq().getStructureTypeId());
					if (savedBillBoq == null) {
						BillBoqItemDTO billBoqDTO = billObj.getBoq();
						BillBoqItem billBoq = setObject.billBoqItemDtoToEntity(billBoqDTO);
						billBoq.setCreatedBy(billObj.getCreatedBy());
						billBoq.setCreatedOn(new Date());
						billBoq.setModifiedBy(billObj.getCreatedBy());
						billBoq.setUnit(new Unit(billBoqDTO.getUnit().getId()));
						billBoq.setModifiedOn(new Date());
						billBoq.setIsActive(true);
						billBoq.setSiteId(billObj.getSiteId());
						billBoq.setBillId(billObj.getId());
						Long billBoqItemId = billItemDao.saveBillBoqItem(billBoq);
						if (billBoqItemId == null) {
							throw new RuntimeException("Already Exists.");
						}
						BillBoqQuantityItemDTO qtyItemDTO = billBoqDTO.getQtyItem();
						BillBoqQuantityItem qtyItem = setObject.billBoqQuantityItemDtoToEntity(qtyItemDTO);
						qtyItem.setCreatedBy(billObj.getCreatedBy());
						qtyItem.setCreatedOn(new Date());
						qtyItem.setModifiedBy(billObj.getCreatedBy());
						qtyItem.setModifiedOn(new Date());
						qtyItem.setIsActive(true);
						qtyItem.setSiteId(billObj.getSiteId());
						qtyItem.setWorkorderId(savedBill.getWorkorder().getId());
						qtyItem.setBillBoqItemId(billBoqItemId);
						Long qtyItemId = billItemDao.saveBillBoqQuantityItem(qtyItem);
						if (qtyItemId == null) {
							throw new RuntimeException("Already exists.");
						}
					} else {
						BillBoqItemDTO billBoqDTO = billObj.getBoq();
						savedBillBoq.setRemark(billBoqDTO.getRemark());
						savedBillBoq.setModifiedOn(new Date());
						savedBillBoq.setModifiedBy(billObj.getCreatedBy());
						boolean isBillBoqUpdated = billItemDao.forceUpdateBillBoqItem(savedBillBoq);
						if (!isBillBoqUpdated) {
							throw new RuntimeException("Already exists.");
						}
						BillBoqQuantityItem savedQtyItem = billItemDao
								.fetchBillBoqQuantityItemById(billBoqDTO.getQtyItem().getId());
						if (savedQtyItem == null) {
							BillBoqQuantityItemDTO qtyItemDTO = billBoqDTO.getQtyItem();
							BillBoqQuantityItem qtyItem = setObject.billBoqQuantityItemDtoToEntity(qtyItemDTO);
							qtyItem.setCreatedBy(billObj.getCreatedBy());
							qtyItem.setCreatedOn(new Date());
							qtyItem.setModifiedBy(billObj.getCreatedBy());
							qtyItem.setModifiedOn(new Date());
							qtyItem.setIsActive(true);
							qtyItem.setSiteId(billObj.getSiteId());
							qtyItem.setWorkorderId(savedBill.getWorkorder().getId());
							qtyItem.setBillBoqItemId(savedBillBoq.getId());
							Long qtyItemId = billItemDao.saveBillBoqQuantityItem(qtyItem);
							if (qtyItemId == null) {
								throw new RuntimeException("Already exists.");
							}
						} else {
							BillBoqQuantityItemDTO qtyItemDTO = billBoqDTO.getQtyItem();
							BillBoqQuantityItemTransac qtyItemTransac = setObject
									.billBoqQuantityItemEntityToTransac(savedQtyItem);
							boolean hasChanges = false;
							if (!savedQtyItem.getFromChainage().getId().equals(qtyItemDTO.getFromChainage().getId())) {
								savedQtyItem.setFromChainage(new Chainage(qtyItemDTO.getFromChainage().getId()));
								hasChanges = true;
							}
							if (!savedQtyItem.getToChainage().getId().equals(qtyItemDTO.getToChainage().getId())) {
								savedQtyItem.setToChainage(new Chainage(qtyItemDTO.getToChainage().getId()));
								hasChanges = true;
							}
							if (!savedQtyItem.getQuantity().equals(qtyItemDTO.getQuantity())) {
								savedQtyItem.setQuantity(qtyItemDTO.getQuantity());
								hasChanges = true;
							}
							if (!savedQtyItem.getRate().equals(qtyItemDTO.getRate())) {
								savedQtyItem.setRate(qtyItemDTO.getRate());
								hasChanges = true;
							}
							if (hasChanges) {
								savedQtyItem.setModifiedOn(new Date());
								savedQtyItem.setModifiedBy(billObj.getCreatedBy());
								boolean isBoqQtyUpdated = billItemDao.updateBillBoqQuantityItem(savedQtyItem);
								if (!isBoqQtyUpdated) {
									throw new RuntimeException("Already exists...");
								}
								billItemDao.saveBillBoqQuantityItemTransac(qtyItemTransac);
							}
						}
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), billObj.getId(),
							Responses.SUCCESS.toString());
				}
			} else if (workorder.getType().getId() == WorkorderTypes.Highway.getId()) {

				List<WorkorderBoqWorkQtyMapping> woBoqs = woBoqWorkDao
						.fetchWoBoqWorkQtys(billObj.getWorkorder().getId());
				List<BillBoqQuantityItem> qtyItems = billItemDao
						.fetchBillBoqQuantityItemsByWorkorderId(billObj.getWorkorder().getId());
				if (billObj.getId() == null) {
					CustomResponse response = validationUtil.validateAddHighwayBillObject(billObj, woBoqs, qtyItems);
					if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
						return response;
					Bill bill = setObject.billDtoToEntity(billObj);
					Bill lastBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(billObj.getWorkorder().getId(),
							bill.getType().getId());
					if (lastBill != null) {
						if (!entityFinalStateIds.contains(lastBill.getState().getId())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Previous bill is not in final state.");
						}
						if (!lastBill.getToDate().before(bill.getFromDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}
					}

					int lastBillNo = 0;
					if (lastBill == null && workorder.getFromAmendmentId() != null) {

						Bill fromAmendmentBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(
								workorder.getFromAmendmentId(), bill.getType().getId());
						if (fromAmendmentBill != null) {
							lastBillNo = fromAmendmentBill.getBillNo();
						}

						if (fromAmendmentBill != null && !bill.getFromDate().after(fromAmendmentBill.getToDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}

					} else {
						lastBillNo = lastBill != null ? lastBill.getBillNo() : 0;
					}
//					int lastBillNo = lastBill != null ? lastBill.getBillNo() : 0;

//					&& DateUtil.dateWithoutTime(workorder.getSystemBillStartDate())
//					.compareTo(DateUtil.dateWithoutTime(workorder.getStartDate())) >= 0

					if (lastBillNo == 0) {
						lastBillNo = workorder.getPreviousBillNo();
					}
					bill.setBillNo(++lastBillNo);
					bill.setCreatedOn(new Date());
					bill.setModifiedBy(bill.getCreatedBy());
					bill.setModifiedOn(new Date());
					bill.setState(new EngineState(entityInitialStateId));
					bill.setIsActive(true);
					Long billId = billDao.saveBill(bill);
					if (billId == null)
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Bill already exists with same info.");
					BillStateTransitionMapping billStateTransitionMap = new BillStateTransitionMapping(null, billId,
							bill.getState().getId(), true, new Date(), bill.getCreatedBy());
					billDao.mapBillStateTransition(billStateTransitionMap);
					BillBoqItemDTO billBoqDTO = billObj.getBoq();
					BillBoqItem billBoq = setObject.billBoqItemDtoToEntity(billBoqDTO);
					billBoq.setUnit(new Unit(billBoqDTO.getUnit().getId()));
					billBoq.setCreatedBy(bill.getCreatedBy());
					billBoq.setCreatedOn(new Date());
					billBoq.setModifiedBy(bill.getCreatedBy());
					billBoq.setModifiedOn(new Date());
					billBoq.setIsActive(true);
					billBoq.setSiteId(bill.getSiteId());
					billBoq.setBillId(billId);
					Long billBoqItemId = billItemDao.saveBillBoqItem(billBoq);
					if (billBoqItemId == null) {
						throw new RuntimeException("Already Exists...");
					}
					BillBoqQuantityItemDTO qtyItemDTO = billBoqDTO.getQtyItem();
					BillBoqQuantityItem qtyItem = setObject.billBoqQuantityItemDtoToEntity(qtyItemDTO);
					qtyItem.setCreatedBy(bill.getCreatedBy());
					qtyItem.setCreatedOn(new Date());
					qtyItem.setModifiedBy(bill.getCreatedBy());
					qtyItem.setModifiedOn(new Date());
					qtyItem.setIsActive(true);
					qtyItem.setSiteId(bill.getSiteId());
					qtyItem.setWorkorderId(bill.getWorkorder().getId());
					qtyItem.setBillBoqItemId(billBoqItemId);
					Long qtyItemId = billItemDao.saveBillBoqQuantityItem(qtyItem);
					if (qtyItemId == null) {
						throw new RuntimeException("Already Exists...");
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), billId, "Bill created.");
				} else {
					CustomResponse response = validationUtil.validateUpdateHighwayBillObject(billObj, woBoqs, qtyItems);
					if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
						return response;
					Bill savedBill = billDao.fetchBillById(billObj.getId());
					if (!entityEditableStateIds.contains(savedBill.getState().getId())) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in editable state mode.");
					}
					Bill lastBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(billObj.getWorkorder().getId(),
							savedBill.getType().getId());
					if (lastBill != null && lastBill.getId().equals(savedBill.getId())) {
						lastBill = null;
					}
					BillTransac billTransac = setObject.billEntityToTransac(savedBill);
					boolean hasChangesInBill = false;
					if (!savedBill.getFromDate().equals(billObj.getFromDate())) {
						savedBill.setFromDate(billObj.getFromDate());
						hasChangesInBill = true;
					}
					if (!savedBill.getToDate().equals(billObj.getToDate())) {
						savedBill.setToDate(billObj.getToDate());
						hasChangesInBill = true;
					}
					if (lastBill != null) {
						if (!lastBill.getToDate().before(savedBill.getFromDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}
					}
					if ((savedBill.getTaxInvoiceNo() != null && billObj.getTaxInvoiceNo() == null)
							|| (savedBill.getTaxInvoiceNo() == null && billObj.getTaxInvoiceNo() != null)
							|| (savedBill.getTaxInvoiceNo() != null && billObj.getTaxInvoiceNo() != null
									&& !savedBill.getTaxInvoiceNo().equals(billObj.getTaxInvoiceNo()))) {
						savedBill.setTaxInvoiceNo(billObj.getTaxInvoiceNo());
						hasChangesInBill = true;
					}
					if ((savedBill.getTaxInvoiceDate() != null && billObj.getTaxInvoiceDate() == null)
							|| (savedBill.getTaxInvoiceDate() == null && billObj.getTaxInvoiceDate() != null)
							|| (savedBill.getTaxInvoiceDate() != null && billObj.getTaxInvoiceDate() != null
									&& !savedBill.getTaxInvoiceDate().equals(billObj.getTaxInvoiceDate()))) {
						savedBill.setTaxInvoiceDate(billObj.getTaxInvoiceDate());
						hasChangesInBill = true;
					}
					if ((savedBill.getApplicableIgst() != null && billObj.getApplicableIgst() == null)
							|| (savedBill.getApplicableIgst() == null && billObj.getApplicableIgst() != null)
							|| (savedBill.getApplicableIgst() != null && billObj.getApplicableIgst() != null
									&& !savedBill.getApplicableIgst().equals(billObj.getApplicableIgst()))) {
						savedBill.setApplicableIgst(billObj.getApplicableIgst());
						hasChangesInBill = true;
					}
					if ((savedBill.getIsIgstOnly() != null && billObj.getIsIgstOnly() == null)
							|| (savedBill.getIsIgstOnly() == null && billObj.getIsIgstOnly() != null)
							|| (savedBill.getIsIgstOnly() != null && billObj.getIsIgstOnly() != null
									&& !savedBill.getIsIgstOnly().equals(billObj.getIsIgstOnly()))) {
						savedBill.setIsIgstOnly(billObj.getIsIgstOnly());
						hasChangesInBill = true;
					}
					savedBill.setModifiedOn(new Date());
					savedBill.setModifiedBy(savedBill.getCreatedBy());
					if (hasChangesInBill) {
						boolean isBillUpdated = billDao.updateBill(savedBill);
						if (!isBillUpdated) {
							return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
									"Bill already exists with same info.");
						}
						billDao.saveBillTransac(billTransac);
					}
					BillBoqItem savedBillBoq = billItemDao.fetchBillBoqItemByBillId(savedBill.getId(),
							billObj.getBoq().getBoq().getId(), billObj.getBoq().getVendorDescription(), null);
					if (savedBillBoq == null) {
						BillBoqItemDTO billBoqDTO = billObj.getBoq();
						BillBoqItem billBoq = setObject.billBoqItemDtoToEntity(billBoqDTO);
						billBoq.setCreatedBy(billObj.getCreatedBy());
						billBoq.setCreatedOn(new Date());
						billBoq.setModifiedBy(billObj.getCreatedBy());
						billBoq.setUnit(new Unit(billBoqDTO.getUnit().getId()));
						billBoq.setModifiedOn(new Date());
						billBoq.setIsActive(true);
						billBoq.setSiteId(billObj.getSiteId());
						billBoq.setBillId(billObj.getId());
						Long billBoqItemId = billItemDao.saveBillBoqItem(billBoq);
						if (billBoqItemId == null) {
							throw new RuntimeException("Already Exists...");
						}
						BillBoqQuantityItemDTO qtyItemDTO = billBoqDTO.getQtyItem();
						BillBoqQuantityItem qtyItem = setObject.billBoqQuantityItemDtoToEntity(qtyItemDTO);
						qtyItem.setCreatedBy(billObj.getCreatedBy());
						qtyItem.setCreatedOn(new Date());
						qtyItem.setModifiedBy(billObj.getCreatedBy());
						qtyItem.setModifiedOn(new Date());
						qtyItem.setIsActive(true);
						qtyItem.setSiteId(billObj.getSiteId());
						qtyItem.setWorkorderId(savedBill.getWorkorder().getId());
						qtyItem.setBillBoqItemId(billBoqItemId);
						Long qtyItemId = billItemDao.saveBillBoqQuantityItem(qtyItem);
						if (qtyItemId == null) {
							throw new RuntimeException("Already exists.");
						}
					} else {
						BillBoqItemDTO billBoqDTO = billObj.getBoq();
						savedBillBoq.setRemark(billBoqDTO.getRemark());
						savedBillBoq.setModifiedOn(new Date());
						savedBillBoq.setModifiedBy(billObj.getCreatedBy());
						boolean isBillBoqUpdated = billItemDao.forceUpdateBillBoqItem(savedBillBoq);
						if (!isBillBoqUpdated) {
							throw new RuntimeException("Already exists.");
						}
						BillBoqQuantityItem savedQtyItem = billItemDao
								.fetchBillBoqQuantityItemById(billBoqDTO.getQtyItem().getId());
						if (savedQtyItem == null) {
							BillBoqQuantityItemDTO qtyItemDTO = billBoqDTO.getQtyItem();
							BillBoqQuantityItem qtyItem = setObject.billBoqQuantityItemDtoToEntity(qtyItemDTO);
							qtyItem.setCreatedBy(billObj.getCreatedBy());
							qtyItem.setCreatedOn(new Date());
							qtyItem.setModifiedBy(billObj.getCreatedBy());
							qtyItem.setModifiedOn(new Date());
							qtyItem.setIsActive(true);
							qtyItem.setSiteId(billObj.getSiteId());
							qtyItem.setWorkorderId(savedBill.getWorkorder().getId());
							qtyItem.setBillBoqItemId(savedBillBoq.getId());
							Long qtyItemId = billItemDao.saveBillBoqQuantityItem(qtyItem);
							if (qtyItemId == null) {
								throw new RuntimeException("Already exists.");
							}
						} else {
							BillBoqQuantityItemDTO qtyItemDTO = billBoqDTO.getQtyItem();
							BillBoqQuantityItemTransac qtyItemTransac = setObject
									.billBoqQuantityItemEntityToTransac(savedQtyItem);
							boolean hasChanges = false;
							if (!savedQtyItem.getFromChainage().getId().equals(qtyItemDTO.getFromChainage().getId())) {
								savedQtyItem.setFromChainage(new Chainage(qtyItemDTO.getFromChainage().getId()));
								hasChanges = true;
							}
							if (!savedQtyItem.getToChainage().getId().equals(qtyItemDTO.getToChainage().getId())) {
								savedQtyItem.setToChainage(new Chainage(qtyItemDTO.getToChainage().getId()));
								hasChanges = true;
							}
							if (!savedQtyItem.getQuantity().equals(qtyItemDTO.getQuantity())) {
								savedQtyItem.setQuantity(qtyItemDTO.getQuantity());
								hasChanges = true;
							}
							if (!savedQtyItem.getRate().equals(qtyItemDTO.getRate())) {
								savedQtyItem.setRate(qtyItemDTO.getRate());
								hasChanges = true;
							}
							if (hasChanges) {
								savedQtyItem.setModifiedOn(new Date());
								savedQtyItem.setModifiedBy(billObj.getCreatedBy());
								boolean isBoqQtyUpdated = billItemDao.updateBillBoqQuantityItem(savedQtyItem);
								if (!isBoqQtyUpdated) {
									throw new RuntimeException("Already exists...");
								}
								billItemDao.saveBillBoqQuantityItemTransac(qtyItemTransac);
							}
						}
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), billObj.getId(),
							Responses.SUCCESS.toString());
				}
			} else if (workorder.getType().getId() == WorkorderTypes.Machine_Hiring.getId()) {

				List<WorkorderHiringMachineWorkItemMapping> machineItems = woHireMachineDao
						.fetchWorkorderHiringMachineWorkItemMapping(billObj.getWorkorder().getId());

				Map<Long, WorkorderHiringMachineWorkItemMapping> machineItemDbMap = machineItems.stream()
						.collect(Collectors.toMap(o -> o.getId(), o -> o));

//------------------------------------------------------------------------------------------------------------------------------------------------------------
				Map<Long, List<BillMachineMapRequest>> hireMachineCategoryReqMap = billObj.getHireMachinery().stream()
						.filter(o -> o != null).collect(Collectors.groupingBy(o -> o.getWoHiringMachineWorkItemId()));

				for (Map.Entry<Long, List<BillMachineMapRequest>> billMachineMap : hireMachineCategoryReqMap
						.entrySet()) {
//					categoryShiftMap.containsKey(billMachineMap.getKey()) &&
					if (billMachineMap.getValue() != null && !billMachineMap.getValue().isEmpty()) {
						for (BillMachineMapRequest machineReq : billMachineMap.getValue()) {
							Boolean exist = billMachineDao.fetchBillMachineList(machineReq.getMachineId(),
									machineReq.getFromDate(), machineReq.getToDate(), billObj.getId());

							if (exist)
								return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
										"Bill already exist with machine date");

							List<MachineDPR> machineDprList = dprDao.fetchMachineDprList(
									Set.of(machineReq.getMachineId()), machineReq.getFromDate(), machineReq.getToDate(),
									billObj.getSiteId());

							if (machineItemDbMap.get(billMachineMap.getKey()).getRunningMode() != null
									&& machineItemDbMap.get(billMachineMap.getKey()).getRunningMode()
											.equals(MachineryRunningMode.SHIFTS)) {

								Double shiftId = machineItemDbMap.get(billMachineMap.getKey()).getShiftSchedule()
										.getShiftHours();

								if (machineDprList != null && !machineDprList.isEmpty()) {
									Optional<MachineDPR> findFirst = machineDprList.parallelStream().filter(o -> o
											.getRunningMode().equals(MachineryRunningMode.SHIFTS)
											&& o.getBreakdownHours() != null
											&& ((o.getAttendanceStatus().equals(MachineryAttendanceStatus.PRESENT)
													&& o.getBreakdownHours() > shiftId
													|| (o.getAttendanceStatus()
															.equals(MachineryAttendanceStatus.HALFDAY)
															&& o.getBreakdownHours() > (shiftId / 2)))))
											.findFirst();

									if (findFirst.isPresent())
										return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
												"Please Correct machine Dpr for date "
														+ formatter.format(findFirst.get().getDated())
														+ " and attendance status is "
														+ findFirst.get().getAttendanceStatus());

									if (shiftId == 24) {
										Map<Date, List<MachineDPR>> collect = machineDprList.stream()
												.filter(o -> o.getRunningMode().equals(MachineryRunningMode.SHIFTS))
												.collect(Collectors.groupingBy(o -> o.getDated()));

										for (Map.Entry<Date, List<MachineDPR>> entry : collect.entrySet()) {
											if (entry != null && entry.getValue() != null
													&& entry.getValue().size() > 1)
												return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
														"Please Correct machine Dpr for machine for 24 hour shift.");

										}

									}
									if (shiftId > 12) {
										Map<Date, Double> responseMap = machineDprList.parallelStream()
												.filter(o -> o.getBreakdownHours() != null)
												.collect(Collectors.groupingBy(MachineDPR::getDated,
														Collectors.summingDouble(MachineDPR::getBreakdownHours)))
												.entrySet().stream().filter(entry -> entry.getValue() > 24.0)
												.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

										if (responseMap != null && !responseMap.isEmpty())
											return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
													"Please Correct machine Dpr for machine sum 24 hours.");

									}
								}
							}
						}
					}
				}

//------------------------------------------------------------------------------------------------------------------------------------------------------------

				if (billObj.getId() == null) {
					CustomResponse response = validationUtil.validateAddHiringBillObject(billObj, machineItems);
					if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
						return response;
					Bill bill = setObject.billDtoToEntity(billObj);
					Bill lastBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(billObj.getWorkorder().getId(),
							bill.getType().getId());
					if (lastBill != null) {
						if (!entityFinalStateIds.contains(lastBill.getState().getId())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Previous bill is not in final state.");
						}
						if (!lastBill.getToDate().before(bill.getFromDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}
					}

					int lastBillNo = 0;
					if (lastBill == null && workorder.getFromAmendmentId() != null) {

						Bill fromAmendmentBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(
								workorder.getFromAmendmentId(), bill.getType().getId());
						if (fromAmendmentBill != null) {
							lastBillNo = fromAmendmentBill.getBillNo();
						}

						if (fromAmendmentBill != null && !bill.getFromDate().after(fromAmendmentBill.getToDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}

					} else {
						lastBillNo = lastBill != null ? lastBill.getBillNo() : 0;
					}
//					int lastBillNo = lastBill != null ? lastBill.getBillNo() : 0;

//					&& DateUtil.dateWithoutTime(workorder.getSystemBillStartDate())
//					.compareTo(DateUtil.dateWithoutTime(workorder.getStartDate())) >= 0

					if (lastBillNo == 0) {
						lastBillNo = workorder.getPreviousBillNo();
					}
					bill.setBillNo(++lastBillNo);
					bill.setCreatedOn(new Date());
					bill.setModifiedBy(bill.getCreatedBy());
					bill.setModifiedOn(new Date());
					bill.setState(new EngineState(entityInitialStateId));
					bill.setIsActive(true);
					Long billId = billDao.saveBill(bill);
					if (billId == null)
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Bill already exists with same info.");
					BillStateTransitionMapping billStateTransitionMap = new BillStateTransitionMapping(null, billId,
							bill.getState().getId(), true, new Date(), bill.getCreatedBy());
					billDao.mapBillStateTransition(billStateTransitionMap);

					for (BillMachineMapRequest bmmrObj : billObj.getHireMachinery()) {

						BillMachineMapping bcmToSave = new BillMachineMapping(null, billId, bmmrObj.getMachineType(),
								bmmrObj.getMachineId(), bmmrObj.getWoHiringMachineWorkItemId(), bmmrObj.getFromDate(),
								bmmrObj.getToDate(), true, new Date(), bill.getCreatedBy(), new Date(),
								bill.getCreatedBy());
						billMachineDao.saveBillMachineMapping(bcmToSave);
					}

					return new CustomResponse(Responses.SUCCESS.getCode(), billId, "Bill created.");

				} else {
					CustomResponse response = validationUtil.validateUpdateHiringBillObject(billObj, machineItems);
					if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
						return response;
					Bill savedBill = billDao.fetchBillById(billObj.getId());
					if (!entityEditableStateIds.contains(savedBill.getState().getId())) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in editable state mode.");
					}
					Bill lastBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(billObj.getWorkorder().getId(),
							savedBill.getType().getId());
					if (lastBill != null && lastBill.getId().equals(savedBill.getId())) {
						lastBill = null;
					}
					BillTransac billTransac = setObject.billEntityToTransac(savedBill);
					boolean hasChangesInBill = false;
					if (!savedBill.getFromDate().equals(billObj.getFromDate())) {
						savedBill.setFromDate(billObj.getFromDate());
						hasChangesInBill = true;
					}
					if (!savedBill.getToDate().equals(billObj.getToDate())) {
						savedBill.setToDate(billObj.getToDate());
						hasChangesInBill = true;
					}
					if (lastBill != null) {
						if (!lastBill.getToDate().before(savedBill.getFromDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}
					}
					if ((savedBill.getTaxInvoiceNo() != null && billObj.getTaxInvoiceNo() == null)
							|| (savedBill.getTaxInvoiceNo() == null && billObj.getTaxInvoiceNo() != null)
							|| (savedBill.getTaxInvoiceNo() != null && billObj.getTaxInvoiceNo() != null
									&& !savedBill.getTaxInvoiceNo().equals(billObj.getTaxInvoiceNo()))) {
						savedBill.setTaxInvoiceNo(billObj.getTaxInvoiceNo());
						hasChangesInBill = true;
					}
					if ((savedBill.getTaxInvoiceDate() != null && billObj.getTaxInvoiceDate() == null)
							|| (savedBill.getTaxInvoiceDate() == null && billObj.getTaxInvoiceDate() != null)
							|| (savedBill.getTaxInvoiceDate() != null && billObj.getTaxInvoiceDate() != null
									&& !savedBill.getTaxInvoiceDate().equals(billObj.getTaxInvoiceDate()))) {
						savedBill.setTaxInvoiceDate(billObj.getTaxInvoiceDate());
						hasChangesInBill = true;
					}
					if ((savedBill.getApplicableIgst() != null && billObj.getApplicableIgst() == null)
							|| (savedBill.getApplicableIgst() == null && billObj.getApplicableIgst() != null)
							|| (savedBill.getApplicableIgst() != null && billObj.getApplicableIgst() != null
									&& !savedBill.getApplicableIgst().equals(billObj.getApplicableIgst()))) {
						savedBill.setApplicableIgst(billObj.getApplicableIgst());
						hasChangesInBill = true;
					}
					if ((savedBill.getIsIgstOnly() != null && billObj.getIsIgstOnly() == null)
							|| (savedBill.getIsIgstOnly() == null && billObj.getIsIgstOnly() != null)
							|| (savedBill.getIsIgstOnly() != null && billObj.getIsIgstOnly() != null
									&& !savedBill.getIsIgstOnly().equals(billObj.getIsIgstOnly()))) {
						savedBill.setIsIgstOnly(billObj.getIsIgstOnly());
						hasChangesInBill = true;
					}
					savedBill.setModifiedOn(new Date());
					savedBill.setModifiedBy(billObj.getCreatedBy());
					if (hasChangesInBill) {
						boolean isBillUpdated = billDao.updateBill(savedBill);
						if (!isBillUpdated) {
							return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
									"Bill already exists with same info.");
						}
						billDao.saveBillTransac(billTransac);
					}
					List<BillMachineMapping> billMachines = billMachineDao.fetchBillMachineMapping(billObj.getId());
					if (billMachines != null) {
						Map<Long, BillMachineMapping> collect = billMachines.stream()
								.collect(Collectors.toMap(o -> o.getId(), o -> o));
						Set<Long> savedBillMachineReqIds = new HashSet<>();
						for (BillMachineMapRequest bmmrObj : billObj.getHireMachinery()) {
							if (bmmrObj.getId() == null) {
								BillMachineMapping bcmToSave = new BillMachineMapping(null, savedBill.getId(),
										bmmrObj.getMachineType(), bmmrObj.getMachineId(),
										bmmrObj.getWoHiringMachineWorkItemId(), bmmrObj.getFromDate(),
										bmmrObj.getToDate(), true, new Date(), billObj.getCreatedBy(), new Date(),
										billObj.getCreatedBy());
								billMachineDao.saveBillMachineMapping(bcmToSave);

							} else {
								savedBillMachineReqIds.add(bmmrObj.getId());
								BillMachineMapping bcmToUpdate = new BillMachineMapping(bmmrObj.getId(),
										savedBill.getId(), bmmrObj.getMachineType(), bmmrObj.getMachineId(),
										bmmrObj.getWoHiringMachineWorkItemId(), bmmrObj.getFromDate(),
										bmmrObj.getToDate(), true, collect.get(bmmrObj.getId()).getCreatedOn(),
										collect.get(bmmrObj.getId()).getCreatedBy(), new Date(),
										billObj.getCreatedBy());
								billMachineDao.forceUpdateBillMachineMapping(bcmToUpdate);
							}
						}
						for (BillMachineMapping bcm : billMachines) {
							if (!savedBillMachineReqIds.contains(bcm.getId())) {
								bcm.setIsActive(false);
								bcm.setModifiedBy(billObj.getCreatedBy());
								bcm.setModifiedOn(new Date());
								billMachineDao.forceUpdateBillMachineMapping(bcm);
							}

						}
					} else {
						for (BillMachineMapRequest bmmrObj : billObj.getHireMachinery()) {

							BillMachineMapping bcmToSave = new BillMachineMapping(null, savedBill.getId(),
									bmmrObj.getMachineType(), bmmrObj.getMachineId(),
									bmmrObj.getWoHiringMachineWorkItemId(), bmmrObj.getFromDate(), bmmrObj.getToDate(),
									true, new Date(), billObj.getCreatedBy(), new Date(), billObj.getCreatedBy());
							billMachineDao.saveBillMachineMapping(bcmToSave);
						}
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), billObj.getId(),
							Responses.SUCCESS.toString());
				}

			} else if (workorder.getType().getId() == WorkorderTypes.Consultancy.getId()) {

				if (billObj.getId() == null) {
					CustomResponse response = validationUtil.validateAddConsultancyBillObject(billObj);
					if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
						return response;
					Bill bill = setObject.billDtoToEntity(billObj);
					Bill lastBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(billObj.getWorkorder().getId(),
							bill.getType().getId());
					if (lastBill != null) {
						if (!entityFinalStateIds.contains(lastBill.getState().getId())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Previous bill is not in final state.");
						}
						if (!lastBill.getToDate().before(bill.getFromDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}
					}

					int lastBillNo = 0;
					if (lastBill == null && workorder.getFromAmendmentId() != null) {

						Bill fromAmendmentBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(
								workorder.getFromAmendmentId(), bill.getType().getId());
						if (fromAmendmentBill != null) {
							lastBillNo = fromAmendmentBill.getBillNo();
						}

						if (fromAmendmentBill != null && !bill.getFromDate().after(fromAmendmentBill.getToDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}

					} else {
						lastBillNo = lastBill != null ? lastBill.getBillNo() : 0;
					}
//					int lastBillNo = lastBill != null ? lastBill.getBillNo() : 0;

//					&& DateUtil.dateWithoutTime(workorder.getSystemBillStartDate())
//					.compareTo(DateUtil.dateWithoutTime(workorder.getStartDate())) >= 0

					if (lastBillNo == 0) {
						lastBillNo = workorder.getPreviousBillNo();
					}
					bill.setBillNo(++lastBillNo);
					bill.setCreatedOn(new Date());
					bill.setModifiedBy(bill.getCreatedBy());
					bill.setModifiedOn(new Date());
					bill.setState(new EngineState(entityInitialStateId));
					bill.setIsActive(true);
					Long billId = billDao.saveBill(bill);
					if (billId == null)
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Bill already exists with same info.");
					BillStateTransitionMapping billStateTransitionMap = new BillStateTransitionMapping(null, billId,
							bill.getState().getId(), true, new Date(), bill.getCreatedBy());
					billDao.mapBillStateTransition(billStateTransitionMap);

					Boolean milstoneSaved = false;
					for (Long woMilestoneIdToSave : billObj.getWorkorderPayMilestoneIds()) {

						BillPayMilestones payMilestone = new BillPayMilestones(null, billId, woMilestoneIdToSave, true,
								new Date(), billObj.getCreatedBy());
						Long billPayMilestoneId = billPayMilestoneDao.saveBillPayMilestone(payMilestone);
						if (!milstoneSaved) {
							if (billPayMilestoneId != null && billPayMilestoneId > 0L)
								milstoneSaved = true;

						}
					}
					if (!milstoneSaved) {
						throw new RuntimeException("Invalid payment milestone.");
					}
					return new CustomResponse(Responses.SUCCESS.getCode(), billId, "Bill created.");

				} else {
					CustomResponse response = validationUtil.validateAddConsultancyBillObject(billObj);
					if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
						return response;
					Bill savedBill = billDao.fetchBillById(billObj.getId());
					if (!entityEditableStateIds.contains(savedBill.getState().getId())) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in editable state mode.");
					}
					Bill lastBill = billDao.fetchLastBillByWorkorderIdAndBillTypeId(billObj.getWorkorder().getId(),
							savedBill.getType().getId());
					if (lastBill != null && lastBill.getId().equals(savedBill.getId())) {
						lastBill = null;
					}
					BillTransac billTransac = setObject.billEntityToTransac(savedBill);
					boolean hasChangesInBill = false;
					if (!savedBill.getFromDate().equals(billObj.getFromDate())) {
						savedBill.setFromDate(billObj.getFromDate());
						hasChangesInBill = true;
					}
					if (!savedBill.getToDate().equals(billObj.getToDate())) {
						savedBill.setToDate(billObj.getToDate());
						hasChangesInBill = true;
					}
					if (lastBill != null) {
						if (!lastBill.getToDate().before(savedBill.getFromDate())) {
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Bill 'from' date must be greater than 'to' date of previous bill.");
						}
					}
					if ((savedBill.getTaxInvoiceNo() != null && billObj.getTaxInvoiceNo() == null)
							|| (savedBill.getTaxInvoiceNo() == null && billObj.getTaxInvoiceNo() != null)
							|| (savedBill.getTaxInvoiceNo() != null && billObj.getTaxInvoiceNo() != null
									&& !savedBill.getTaxInvoiceNo().equals(billObj.getTaxInvoiceNo()))) {
						savedBill.setTaxInvoiceNo(billObj.getTaxInvoiceNo());
						hasChangesInBill = true;
					}
					if ((savedBill.getTaxInvoiceDate() != null && billObj.getTaxInvoiceDate() == null)
							|| (savedBill.getTaxInvoiceDate() == null && billObj.getTaxInvoiceDate() != null)
							|| (savedBill.getTaxInvoiceDate() != null && billObj.getTaxInvoiceDate() != null
									&& !savedBill.getTaxInvoiceDate().equals(billObj.getTaxInvoiceDate()))) {
						savedBill.setTaxInvoiceDate(billObj.getTaxInvoiceDate());
						hasChangesInBill = true;
					}
					if ((savedBill.getApplicableIgst() != null && billObj.getApplicableIgst() == null)
							|| (savedBill.getApplicableIgst() == null && billObj.getApplicableIgst() != null)
							|| (savedBill.getApplicableIgst() != null && billObj.getApplicableIgst() != null
									&& !savedBill.getApplicableIgst().equals(billObj.getApplicableIgst()))) {
						savedBill.setApplicableIgst(billObj.getApplicableIgst());
						hasChangesInBill = true;
					}
					if ((savedBill.getIsIgstOnly() != null && billObj.getIsIgstOnly() == null)
							|| (savedBill.getIsIgstOnly() == null && billObj.getIsIgstOnly() != null)
							|| (savedBill.getIsIgstOnly() != null && billObj.getIsIgstOnly() != null
									&& !savedBill.getIsIgstOnly().equals(billObj.getIsIgstOnly()))) {
						savedBill.setIsIgstOnly(billObj.getIsIgstOnly());
						hasChangesInBill = true;
					}
					savedBill.setModifiedOn(new Date());
					savedBill.setModifiedBy(billObj.getCreatedBy());
					if (hasChangesInBill) {
						boolean isBillUpdated = billDao.updateBill(savedBill);
						if (!isBillUpdated) {
							return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
									"Bill already exists with same info.");
						}
						billDao.saveBillTransac(billTransac);
					}

					List<BillPayMilestones> billPayMilestones = billPayMilestoneDao
							.fetchBillPayMilestones(savedBill.getId());
					if (billPayMilestones != null) {
						int billPMCount = 0;
						Set<Long> woPayMilestoneIds = billObj.getWorkorderPayMilestoneIds();
						Set<Long> savedWoPayMilestoneIds = new HashSet<>();
						billPayMilestones.forEach(item -> savedWoPayMilestoneIds.add(item.getWorkorderMilestoneId()));
						billPMCount = billPayMilestones.size();
						for (Long woPayMilestoneId : woPayMilestoneIds) {
							if (!savedWoPayMilestoneIds.contains(woPayMilestoneId)) {
								BillPayMilestones payMilestone = new BillPayMilestones(null, savedBill.getId(),
										woPayMilestoneId, true, new Date(), billObj.getCreatedBy());
								billPayMilestoneDao.saveBillPayMilestone(payMilestone);
								billPMCount++;
							}
						}

						for (BillPayMilestones billPM : billPayMilestones) {
							if (!woPayMilestoneIds.contains(billPM.getWorkorderMilestoneId())) {
								billPM.setModifiedBy(billObj.getCreatedBy());
								billPM.setModifiedOn(new Date());
								billPM.setIsActive(false);
								billPayMilestoneDao.forceUpdateBillPayMilestone(billPM);
								billPMCount--;
							}
						}
						if (billPMCount <= 0) {
							throw new RuntimeException("Atleast one payment milestone required.");
						}
					}

					return new CustomResponse(Responses.SUCCESS.getCode(), billObj.getId(),
							Responses.SUCCESS.toString());
				}

			}

			return new CustomResponse(Responses.FORBIDDEN.getCode(), null, Responses.FORBIDDEN.toString());
		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBillPrintResponseData(SearchDTO search) {

		try {
			BillPrintResponseDTO printObj = new BillPrintResponseDTO();
			Bill bill = billDao.fetchBillById(search.getBillId());
			BillDTO billDTO = setObject.billEntityToDto(bill);

//			for final success state action obj
//			EntityStateMapDTO entityFinalSuccessStateMap = engineService.getEntityStateByFinalAndStateAction(
//					EntitiesEnum.BILL.getEntityId(), EngineStateActions.Final_Success.getStateActionId(),
//					search.getCompanyId());
//			if (!entityFinalSuccessStateMap.getStateId().equals(bill.getState().getId())) {
//				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in successful final state.");
//			}

			billDTO.setBillNoText(billDTO.getType().getName() + "/" + billDTO.getBillNo());
			WorkorderDTO woObj = billDTO.getWorkorder();

			if (woObj.getWoContractor() != null) {
				if (woObj.getWoContractor().getBankDetail() != null) {
					ContractorBankDetailDTO contractorBankDetail = woObj.getWoContractor().getBankDetail();
					FileEntity cancelledCheque = bill.getWorkorder().getWoContractor().getBankDetail()
							.getCancelChequeFile();
					String cancelChequeFullPath = null;
					if (contractorBankDetail.getCancelChequeFile() != null && cancelledCheque.getModule() != null) {
						String baseUrl = cancelledCheque.getModule().getBaseUrl();
						cancelChequeFullPath = baseUrl + contractorBankDetail.getCancelChequeFile().getPath();
					}
					contractorBankDetail.setCancelChequeFilePath(cancelChequeFullPath);
					woObj.getWoContractor().setBankDetail(contractorBankDetail);
				}
			}

			Site site = siteDao.fetchSiteById(search.getSiteId());
			CompanyDTO company = setObject.companyEntityToDto(site.getCompany());
			SiteDTO siteDTO = setObject.siteEntityToDto(site);
			siteDTO.setCompany(company);
			printObj.setSite(siteDTO);

			search.setSiteId(bill.getSiteId());
			search.setContractorId(bill.getWorkorder().getContractor().getId());
			search.setFromDate(bill.getFromDate());
			search.setToDate(bill.getToDate());
			search.setWorkorderId(bill.getWorkorder().getId());

			search.setFromDate(null);
			search.setToDate(bill.getToDate());
			List<Bill> allBills = billDao.fetchUptoCurrentWorkorderBills(search);

			billDTO.setWorkorder(woObj);
			printObj.setBill(billDTO);

			Integer workorderTypeId = bill.getWorkorder().getType().getId().intValue();

//			previous bill info Response
			WorkorderBillInfoResponse workorderBillInfoResponse = new WorkorderBillInfoResponse();
			WorkorderBillInfo workorderBillInfoDbObj = workorderBillInfoDao.fetchWorkorderBillInfo(search);
//			List<BillDeductionResponseDto> temp = null;

			if (workorderBillInfoDbObj != null && workorderBillInfoDbObj.getId() != null) {
				search.setWorkorderBillInfoId(workorderBillInfoDbObj.getId());
				workorderBillInfoResponse.setId(bill.getWorkorder().getId());
				workorderBillInfoResponse.setPreviousBillAmount(workorderBillInfoDbObj.getPreviousBillAmount());
				workorderBillInfoResponse.setTotal(workorderBillInfoDbObj.getTotal());
				workorderBillInfoResponse.setTotalDeduction(workorderBillInfoDbObj.getTotalDeduction());
				workorderBillInfoResponse.setPayableAmount(workorderBillInfoDbObj.getPayableAmount());
				workorderBillInfoResponse.setApplicableIgst(workorderBillInfoDbObj.getApplicableIgst());
				workorderBillInfoResponse.setIsIgstOnly(workorderBillInfoDbObj.getIsIgstOnly());

				List<BillDeductionResponseDto> billDeductionResponse = new ArrayList<>();

				List<BillDeductionMappingV2> billDeductionList = deductionMapDao
						.fetchMappedBillDeductionsByWorkorderBillInfoId(search);

				if (billDeductionList != null && !billDeductionList.isEmpty()) {
					billDeductionList.forEach(o -> billDeductionResponse
							.add(setObject.billDeductionMappingEntitytoBillDeductionResponseDto(o)));
					workorderBillInfoResponse.setBillDeductionResponse(billDeductionResponse);
//					temp = new ArrayList<>(billDeductionResponse);
				}

			}

			if (workorderTypeId.equals(WorkorderTypes.Structure.getId())) {

				List<DebitNoteItem> currentNoteItems = debitNoteDao.fetchDebitNoteItems(search);
				List<DebitNoteItem> approvedNoteItems = new ArrayList<DebitNoteItem>();
				List<DebitNoteItem> unapprovedNoteItems = new ArrayList<DebitNoteItem>();
				if (currentNoteItems != null) {
					for (DebitNoteItem item : currentNoteItems) {
						if (item.getDebitNote().getStatus().equals(DebitNoteStates.FullyApproved.getId())) {
							approvedNoteItems.add(item);
						} else {
							unapprovedNoteItems.add(item);
						}
					}
				}

				List<DebitNoteItemDTO> approvedItems = new ArrayList<>();
				Double totalHandlingCharge = 0.0;
				Double totalGstAmount = 0.0;
				Double totalAmountBeforeHandlingCharge = 0.0;
				Double totalAmountAfterHandlingCharge = 0.0;

				if (approvedNoteItems != null) {
					for (DebitNoteItem noteItem : approvedNoteItems) {
						DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
						totalHandlingCharge += noteItemDTO.getHandlingCharge();
						totalAmountAfterHandlingCharge += noteItemDTO.getFinalAmount();
						totalGstAmount += noteItemDTO.getGstAmount();
						approvedItems.add(noteItemDTO);
					}
				}
				totalAmountBeforeHandlingCharge = totalAmountAfterHandlingCharge - totalHandlingCharge;

				Double totalUnapprovedItemsAmount = 0.0;
				int unapprovedCount = 0;
				List<DebitNoteItemDTO> unapprovedItems = new ArrayList<>();
				if (unapprovedNoteItems != null) {
					for (DebitNoteItem noteItem : unapprovedNoteItems) {
						DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
						totalUnapprovedItemsAmount += noteItemDTO.getFinalAmount();
						unapprovedItems.add(noteItemDTO);
						++unapprovedCount;
					}
				}

				DebitNoteResponseDTO currentDebitNote = new DebitNoteResponseDTO(approvedItems, totalGstAmount,
						totalHandlingCharge, totalAmountBeforeHandlingCharge, totalAmountAfterHandlingCharge, null,
						totalAmountAfterHandlingCharge, unapprovedCount, totalUnapprovedItemsAmount, unapprovedItems);

				printObj.setCurrentDebitNote(currentDebitNote);
				if (unapprovedCount > 0) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, unapprovedCount
							+ " no. of debit notes are not approved yet. Provide approval then try again.");
				}

				Double totalHandlingChargeCumulative = 0.0;
				Double totalGstAmountCumulative = 0.0;
				Double totalAmountBeforeHandlingChargeCumulative = 0.0;
				Double totalAmountAfterHandlingChargeCumulative = 0.0;
				Double currentBillAmount = 0.0;
				Double uptoPreviousBillAmount = 0.0;
				List<DebitNoteItemDTO> approvedItemsCumulative = new ArrayList<>();
				List<DebitNoteItemDTO> unapprovedItemsCumulative = new ArrayList<>();
				Double totalUnapprovedItemsAmountCumulative = 0.0;
				int unapprovedCountCumulative = 0;

				Set<CategoryItemDTO> categoriesDTO = new LinkedHashSet<>();
				if (woObj.getWoContractor() != null) {
					List<WorkorderCategoryMapping> wccms = woBasicDao
							.fetchWorkorderCategoriesByWorkorderId(woObj.getId());
					List<CategoryItem> categories = woBasicDao
							.fetchActiveCategoryItemsByCompanyId(search.getCompanyId());
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
				billDTO.setWorkorder(woObj);
				printObj.setBill(billDTO);

//				debit notes

				if (allBills != null) {
					for (Bill thisBill : allBills) {
						if (thisBill.getToDate().after(bill.getToDate())) {
							continue;
						}
						search.setContractorId(thisBill.getWorkorder().getContractor().getId());
						search.setFromDate(thisBill.getFromDate());
						search.setToDate(thisBill.getToDate());
						search.setWorkorderId(thisBill.getWorkorder().getId());
						List<DebitNoteItem> noteItems = debitNoteDao.fetchDebitNoteItems(search);
						List<DebitNoteItem> approvedNoteItemsCumulative = new ArrayList<DebitNoteItem>();
						List<DebitNoteItem> unapprovedNoteItemsCumulative = new ArrayList<DebitNoteItem>();

						if (noteItems != null) {
							for (DebitNoteItem item : noteItems) {
								if (item.getDebitNote().getStatus().equals(DebitNoteStates.FullyApproved.getId())) {
									approvedNoteItemsCumulative.add(item);
								} else {
									unapprovedNoteItemsCumulative.add(item);
								}
							}
						}

						if (approvedNoteItemsCumulative != null) {
							for (DebitNoteItem noteItem : approvedNoteItemsCumulative) {
								DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
								totalHandlingChargeCumulative += noteItemDTO.getHandlingCharge();
								totalAmountAfterHandlingChargeCumulative += noteItemDTO.getFinalAmount();
								totalGstAmountCumulative += noteItemDTO.getGstAmount();
								if (thisBill.getId().equals(bill.getId())) {
									currentBillAmount += noteItemDTO.getFinalAmount();
								}
								approvedItemsCumulative.add(noteItemDTO);
							}
						}

						if (unapprovedNoteItemsCumulative != null) {
							for (DebitNoteItem noteItem : unapprovedNoteItemsCumulative) {
								DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
								totalUnapprovedItemsAmountCumulative += noteItemDTO.getFinalAmount();
								unapprovedItemsCumulative.add(noteItemDTO);
								++unapprovedCountCumulative;
							}
						}

					}
				}
				totalAmountBeforeHandlingChargeCumulative = totalAmountAfterHandlingChargeCumulative
						- totalHandlingChargeCumulative;
				uptoPreviousBillAmount = totalAmountAfterHandlingChargeCumulative - currentBillAmount;
				DebitNoteResponseDTO uptoCurrentDebitNote = new DebitNoteResponseDTO(approvedItemsCumulative,
						totalGstAmountCumulative, totalHandlingChargeCumulative,
						totalAmountBeforeHandlingChargeCumulative, totalAmountAfterHandlingChargeCumulative,
						uptoPreviousBillAmount, currentBillAmount, unapprovedCountCumulative,
						totalUnapprovedItemsAmountCumulative, unapprovedItemsCumulative);
				printObj.setUptoCurrentDebitNote(uptoCurrentDebitNote);

//				set bill BOQs

				List<BillPrintBoqResponseDTO> boqs = new ArrayList<>();
				List<BillBoqItem> boqItems = billItemDao.fetchBillBoqItemsByBillId(bill.getId());
				List<BillBoqQuantityItem> qtyItems = billItemDao.fetchBillBoqQuantityItemsByBillId(bill.getId());
				List<BillBoqQuantityItem> woQtyItems = billItemDao.fetchUptoCurrentBillBoqQuantityItemsByWorkorderId(
						bill.getWorkorder().getId(), bill.getToDate());

				List<WorkorderBoqWorkQtyMapping> woBoqs = woBoqWorkDao.fetchWoBoqWorkQtys(bill.getWorkorder().getId());
				Double workorderAmount = 0.0;
				for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
					workorderAmount += (woBoq.getRate() * woBoq.getQuantity());
				}
				printObj.setWorkorderAmount(workorderAmount);

				Double totalBillAmount = 0.0;
				boqs.add(new BillPrintBoqResponseDTO(null, null, "A", bill.getWorkorder().getType().getCode(), null,
						null, null, null, null, null, null, null, null, null));
				if (boqItems != null && qtyItems != null) {

					Set<Long> distinctStructureTypes = new HashSet<>();
					for (BillBoqItem billBoq : boqItems) {
						distinctStructureTypes.add(billBoq.getStructureTypeId());
					}
					for (Long structureTypeId : distinctStructureTypes) {
						String structureTypeName = null;
						for (BillBoqItem billBoq : boqItems) {
							if (!structureTypeId.equals(billBoq.getStructureTypeId())) {
								continue;
							}
							if (structureTypeName == null) {

								boqs.add(new BillPrintBoqResponseDTO(null, null, null,
										billBoq.getStructureType().getName(), null, null, null, null, null, null, null,
										null, null, null));
								structureTypeName = billBoq.getStructureType().getName();
							}
							List<ChainageStretchBillItemResponseDTO> billQtyItems = new ArrayList<>();
							Double currentBillQuantity = 0.0;
							for (BillBoqQuantityItem qtyItem : qtyItems) {
								if (qtyItem.getBillBoqItemId().equals(billBoq.getId())) {
									currentBillQuantity += qtyItem.getQuantity();
									billQtyItems.add(new ChainageStretchBillItemResponseDTO(qtyItem.getId(),
											qtyItem.getBillBoqItemId(),
											new IdNameResponseDTO(qtyItem.getFromChainage().getId(),
													qtyItem.getFromChainage().getName()),
											new IdNameResponseDTO(qtyItem.getToChainage().getId(),
													qtyItem.getToChainage().getName()),
											qtyItem.getQuantity()));
								}
							}
							Double totalQuantity = 0.0;
							Double woBoqPreviousBilledQuantity = 0.0;
							Double billedQuantity = 0.0;
							Double rate = 0.0;
							String vendorDescription = null;
							if (woBoqs != null) {
								for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
									if (woBoq.getStructureTypeId().equals(billBoq.getStructureTypeId())
											&& woBoq.getBoq().getId().equals(billBoq.getBoq().getId())
											&& woBoq.getVendorDescription().equals(billBoq.getVendorDescription())) {
										totalQuantity = woBoq.getQuantity();
										rate = woBoq.getRate();
										vendorDescription = woBoq.getVendorDescription();
										if (qtyItems != null) {
											for (BillBoqQuantityItem qtyItem : qtyItems) {
												if (woBoq.getStructureTypeId()
														.equals(qtyItem.getBillBoqItem().getStructureTypeId())
														&& qtyItem.getBillBoqItem().getBoq().getId()
																.equals(woBoq.getBoqId())
														&& woBoq.getVendorDescription().equals(
																qtyItem.getBillBoqItem().getVendorDescription())) {
													billedQuantity += qtyItem.getQuantity();
												}
											}
										}
										if (woQtyItems != null) {
											for (BillBoqQuantityItem woQtyItem : woQtyItems) {
												if ((woQtyItem.getBillBoqItem().getBill().getToDate()
														.before(bill.getFromDate())
														|| woQtyItem.getBillBoqItem().getBill().getToDate()
																.equals(bill.getFromDate()))
														&& woQtyItem.getBillBoqItem().getBoq().getId()
																.equals(woBoq.getBoqId())
														&& woQtyItem.getBillBoqItem().getStructureTypeId()
																.equals(woBoq.getStructureTypeId())
														&& woBoq.getVendorDescription().equals(
																woQtyItem.getBillBoqItem().getVendorDescription())) {
													woBoqPreviousBilledQuantity += woQtyItem.getQuantity();
												}
											}
										}
										break;
									}
								}
							}
							String boqSubcatMergeDescription = vendorDescription;
							if (billBoq.getBoq().getSubcategory() != null
									&& billBoq.getBoq().getSubcategory().getName() != null) {
								boqSubcatMergeDescription = billBoq.getBoq().getSubcategory().getName()
										+ boqSubcatMergeDescription;
							}
							totalBillAmount += (billedQuantity * rate);
							boqs.add(new BillPrintBoqResponseDTO(billBoq.getBoq().getCategory().getId(),
									billBoq.getBoq().getId(), billBoq.getBoq().getStandardBookCode(),
									boqSubcatMergeDescription, billBoq.getUnit().getName(), totalQuantity, rate,
									totalQuantity * rate, woBoqPreviousBilledQuantity + billedQuantity,
									(woBoqPreviousBilledQuantity + billedQuantity) * rate, woBoqPreviousBilledQuantity,
									woBoqPreviousBilledQuantity * rate, billedQuantity, billedQuantity * rate));
						}
//						TODO
					}
					printObj.setBoqs(boqs);
					printObj.getBill().setBilledAmount(totalBillAmount);

//					price escalation code

					Double valueFo = SetObject.temporaryFoValue;
					Double totalRe = 0.0;
					Double totalVe = 0.0;
					Double currentVe = 0.0;
					Double previousVe = 0.0;
					Boolean isPriceAdjustment = true;
					List<PriceEscalationItemsResponseDTO> peItems = new ArrayList<PriceEscalationItemsResponseDTO>();
					if (allBills == null || allBills.size() < 1) {
						return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Incomplete bill.");
					}
					Date dieselFromDate = allBills.get(0).getFromDate();
					Date dieselToDate = allBills.get(allBills.size() - 1).getToDate();
					search.setFromDate(dieselFromDate);
					search.setToDate(dieselToDate);
					List<DieselRateMapping> dieselRates = dieselRateDao.fetchDieselRates(search);
					Map<Long, Double> billWiseTotalWorkDone = new HashMap<>();
					Map<Long, Double> billWiseTotalWorkDoneWithoutAdjustment = new HashMap<>();
					if (allBills != null) {
						for (Bill billTraverse : allBills) {
							Double currentBillTraverseAmount = 0.0;
							for (BillBoqQuantityItem woQtyItem : woQtyItems) {
								if (woQtyItem.getBillBoqItem().getBill().getId().equals(billTraverse.getId())) {
									currentBillTraverseAmount += (woQtyItem.getQuantity() * woQtyItem.getRate());
								}
							}
							Double weightedDieselRate = null;
							Integer dieselRateCount = 0;
							if (dieselRates != null) {
								for (DieselRateMapping dieselRate : dieselRates) {
									Date rateDate = DateUtil.dateWithoutTime(dieselRate.getDate());
									Date billFromDate = DateUtil.dateWithoutTime(billTraverse.getFromDate());
									Date billToDate = DateUtil.dateWithoutTime(billTraverse.getToDate());
									if (rateDate.equals(billFromDate) || rateDate.equals(billToDate)
											|| (rateDate.after(billFromDate) && rateDate.before(billToDate))) {
										if (weightedDieselRate == null) {
											dieselRateCount++;
											weightedDieselRate = dieselRate.getRate();
										} else {
											dieselRateCount++;
											weightedDieselRate = (weightedDieselRate + dieselRate.getRate());
										}
									}
								}
							}
							if (weightedDieselRate == null) {
								return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
										"Diesel rates not present for bills.");

							}
							if (weightedDieselRate > 0.0) {
								weightedDieselRate = weightedDieselRate / dieselRateCount;
							}
							String duration = null;
							if (DateUtil.fromSameMonthAndYear(billTraverse.getFromDate(), billTraverse.getToDate())) {
								duration = DateUtil.getDayMonthYear(billTraverse.getFromDate(), false);
							} else {
								duration = DateUtil.getDayMonthYear(billTraverse.getFromDate(), true) + " - "
										+ DateUtil.getDayMonthYear(billTraverse.getToDate(), true);
							}
							Double valueVe = 0.4 * currentBillTraverseAmount
									* ((weightedDieselRate - valueFo) / valueFo);
							if (billTraverse.getToDate().before(bill.getFromDate())) {
								previousVe += valueVe;
							}
							if (billTraverse.getId().equals(bill.getId())) {
								currentVe += valueVe;
							}
							totalRe += currentBillTraverseAmount;
							totalVe += valueVe;
							billWiseTotalWorkDone.put(billTraverse.getId(), currentBillTraverseAmount + valueVe);
							billWiseTotalWorkDoneWithoutAdjustment.put(billTraverse.getId(), currentBillTraverseAmount);
							peItems.add(new PriceEscalationItemsResponseDTO(duration, currentBillTraverseAmount,
									weightedDieselRate, valueVe));
						}

					}
					PriceEscalationResponseDTO priceEscalation = new PriceEscalationResponseDTO(valueFo, peItems,
							totalRe, totalVe);
					printObj.setPriceEscalation(priceEscalation);

//					build response

					int srNoIdx = 0;
					List<BillPrintDescriptionItemResponseDTO> ipcItems = new ArrayList<>();
					String billItemSrNo = SetObject.getCharForNumber(++srNoIdx);

					Double itemsTotalUptoDateIpcAmount = 0.0;
					Double itemsTotalUptoPreviousIpcAmount = 0.0;
					Double itemsTotalIpcAmount = 0.0;
					for (CategoryItemDTO item : printObj.getBill().getWorkorder().getCategories()) {
						Double uptoDateIpcAmount = 0.0;
						Double uptoPreviousIpcAmount = 0.0;
						Double ipcAmount = 0.0;
						for (BillPrintBoqResponseDTO billBoq : printObj.getBoqs()) {
							if (billBoq.getCategoryId() == null)
								continue;
							if (billBoq.getCategoryId().equals(item.getId())) {
								uptoDateIpcAmount += billBoq.getCommulativeAmount();
								uptoPreviousIpcAmount += billBoq.getPreviousAmount();
								ipcAmount += billBoq.getCurrentAmount();
							}
						}
						itemsTotalUptoDateIpcAmount += uptoDateIpcAmount;
						itemsTotalUptoPreviousIpcAmount += uptoPreviousIpcAmount;
						itemsTotalIpcAmount += ipcAmount;
					}
					Double totalWorkDoneCurrentWithoutAdjustment = 0.0;
					if (billWiseTotalWorkDoneWithoutAdjustment.get(bill.getId()) != null)
						totalWorkDoneCurrentWithoutAdjustment = billWiseTotalWorkDoneWithoutAdjustment
								.get(bill.getId());

					Double totalWorkDoneCumulativeWithoutAdjustment = 0.0;
					for (Double value : billWiseTotalWorkDoneWithoutAdjustment.values()) {
						totalWorkDoneCumulativeWithoutAdjustment += value;
					}
					String billItemDescription = "Measurement Bills";
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(billItemSrNo, billItemDescription, null,
							totalWorkDoneCumulativeWithoutAdjustment,
							totalWorkDoneCumulativeWithoutAdjustment - totalWorkDoneCurrentWithoutAdjustment,
							totalWorkDoneCurrentWithoutAdjustment, false, false));

					printObj.setCumulativeBillAmount(itemsTotalUptoDateIpcAmount);
					printObj.setCurrentBillAmount(itemsTotalIpcAmount);
					printObj.setPreviousBillAmount(itemsTotalUptoPreviousIpcAmount);

					if (isPriceAdjustment) {
						String priceEscalationText = "Price Adjustment";
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, priceEscalationText, null, totalVe,
								previousVe, currentVe, false, true));
					}

					Double totalWorkDoneCumulative = 0.0;
					Double totalWorkDoneCurrent = 0.0;
					String totalWorkDoneText = "TOTAL WORK DONE";
					for (Double value : billWiseTotalWorkDone.values()) {
						totalWorkDoneCumulative += value;
					}
					if (billWiseTotalWorkDone.get(bill.getId()) != null)
						totalWorkDoneCurrent = billWiseTotalWorkDone.get(bill.getId());
					Double totalWorkDonePrevious = totalWorkDoneCumulative - totalWorkDoneCurrent;
					if (isPriceAdjustment)
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, totalWorkDoneText, null,
								totalWorkDoneCumulative, totalWorkDonePrevious, totalWorkDoneCurrent, true, true));

					Double applicableGst = bill.getApplicableIgst();
					if (applicableGst == null) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Bill is incomplete, GST value not provided.");
					}
					Double withGstItemsTotalIpcAmount = itemsTotalIpcAmount
							+ (itemsTotalIpcAmount * applicableGst / 100);

					printObj.setInvoiceAmountBeforeGst(itemsTotalIpcAmount);
					printObj.setInvoiceAmountAfterGst(withGstItemsTotalIpcAmount);

					if (bill.getIsIgstOnly() != null && bill.getIsIgstOnly()) {
						String igstText = "IGST (" + applicableGst + " %)";
						Double gstUptoDateIpcAmount = totalWorkDoneCumulative * (applicableGst / 100);
						Double gstUptoPreviousIpcAmount = totalWorkDonePrevious * (applicableGst / 100);
						Double gstIpcAmount = totalWorkDoneCurrent * (applicableGst / 100);
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, igstText, null, gstUptoDateIpcAmount,
								gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
					} else {
						String cgstText = "CGST (" + applicableGst / 2 + " %)";
						String sgstText = "SGST (" + applicableGst / 2 + " %)";
						Double gstUptoDateIpcAmount = totalWorkDoneCumulative * (applicableGst / 200);
						Double gstUptoPreviousIpcAmount = totalWorkDonePrevious * (applicableGst / 200);
						Double gstIpcAmount = totalWorkDoneCurrent * (applicableGst / 200);
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, cgstText, null, gstUptoDateIpcAmount,
								gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, sgstText, null, gstUptoDateIpcAmount,
								gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
					}

					Double includingGstUptoDateIpcAmount = totalWorkDoneCumulative
							+ (totalWorkDoneCumulative * (applicableGst / 100));
					Double includingGstUptoPreviousIpcAmount = totalWorkDonePrevious
							+ (totalWorkDonePrevious * (applicableGst / 100));
					Double includingGstIpcAmount = totalWorkDoneCurrent
							+ (totalWorkDoneCurrent * (applicableGst / 100));
					String totalAmountText = "Total Amount (" + SetObject.getCharForNumber(srNoIdx) + ")";
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, totalAmountText, null,
							includingGstUptoDateIpcAmount, includingGstUptoPreviousIpcAmount, includingGstIpcAmount,
							true, true));

//					deductions in build res

					String deductionText = "Deductions :";
					String deductionSrNo = SetObject.getCharForNumber(++srNoIdx);
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(deductionSrNo, deductionText, null, null, null,
							null, true, false));
					List<BillDeductionMapping> woBillDeductions = deductionMapDao
							.fetchUptoCurrentMappedBillDeductionsByWorkorderId(bill.getWorkorder().getId(),
									bill.getToDate());
					Integer deductionCount = 0;
					Set<String> distinctDeductionIdTypeValuePairs = new LinkedHashSet<>();
					Double deductionUptoDateIpcAmount = 0.0;
					Double deductionIpcAmount = 0.0;
					Double deductionUptoPreviousIpcAmount = 0.0;

					if (woBillDeductions != null) {

						for (BillDeductionMapping deductionMap : woBillDeductions) {
							distinctDeductionIdTypeValuePairs.add(deductionMap.getDeduction().getId() + "-"
									+ (deductionMap.getIsPercentage() ? "1" : "0") + "-"
									+ (deductionMap.getIsPercentage() ? (deductionMap.getValue().toString().length() > 5
											? deductionMap.getValue().toString().substring(0, 4)
											: deductionMap.getValue().toString()) : 0));
						}

						for (String deductionPair : distinctDeductionIdTypeValuePairs) {

							String[] idTypeValue = deductionPair.split("-");
							Integer distinctDeductionId = Integer.valueOf(idTypeValue[0]);
							boolean distinctDeductionTypePercentage = (Integer.valueOf(idTypeValue[1]) == 1) ? true
									: false;
							Double distinctDeductionValue = Double.valueOf(idTypeValue[2]);

							Double uptoDateDeductionIpcAmount = 0.0;
							Double uptoPreviousDeductionIpcAmount = 0.0;
							Double currentDeductionIpcAmount = 0.0;
							Double deductionValue = null;
							String deductionName = null;

							for (BillDeductionMapping deductionMap : woBillDeductions) {

								if (distinctDeductionId.equals(deductionMap.getDeduction().getId())
										&& deductionMap.getIsPercentage().equals(distinctDeductionTypePercentage)
										&& !distinctDeductionTypePercentage && bill.getWorkorder().getId()
												.equals(deductionMap.getBill().getWorkorder().getId())) {

									if (deductionName == null) {
										deductionName = deductionMap.getDeduction().getName();
									}
									if (deductionMap.getBillId().equals(bill.getId())) {
										currentDeductionIpcAmount += (deductionMap.getValue());
										deductionValue = deductionMap.getValue();
									} else {
										uptoPreviousDeductionIpcAmount += (deductionMap.getValue());
									}

								} else if (distinctDeductionId.equals(deductionMap.getDeduction().getId())
										&& deductionMap.getIsPercentage().equals(distinctDeductionTypePercentage)
										&& distinctDeductionValue.equals(deductionMap.getValue())
										&& distinctDeductionTypePercentage && bill.getWorkorder().getId()
												.equals(deductionMap.getBill().getWorkorder().getId())) {

									if (deductionName == null) {
										deductionName = deductionMap.getDeduction().getName();
									}
									deductionValue = deductionMap.getValue();

									if (deductionMap.getBillId().equals(bill.getId())) {
										Double deductionBillWorkdone = 0.00;
										if (billWiseTotalWorkDone.get(deductionMap.getBillId()) != null)
											deductionBillWorkdone = (Double) billWiseTotalWorkDone
													.get(deductionMap.getBillId());
										currentDeductionIpcAmount = (double) Math
												.round((deductionValue / 100) * deductionBillWorkdone * 100) / 100;
									} else {
										Double deductionBillWorkdone = 0.00;
										if (billWiseTotalWorkDone.get(deductionMap.getBillId()) != null)
											deductionBillWorkdone = (Double) billWiseTotalWorkDone
													.get(deductionMap.getBillId());
										uptoPreviousDeductionIpcAmount += (double) Math
												.round((deductionValue / 100) * deductionBillWorkdone * 100) / 100;
									}
								}
							}
							if (distinctDeductionTypePercentage) {
								deductionName += (" @ " + distinctDeductionValue + "%");
							}
							uptoDateDeductionIpcAmount = uptoPreviousDeductionIpcAmount + currentDeductionIpcAmount;
							deductionIpcAmount += currentDeductionIpcAmount;
							deductionUptoDateIpcAmount += uptoDateDeductionIpcAmount;
							deductionUptoPreviousIpcAmount += uptoPreviousDeductionIpcAmount;
							ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount), deductionName,
									deductionValue, uptoDateDeductionIpcAmount, uptoPreviousDeductionIpcAmount,
									currentDeductionIpcAmount, false, false));

						}
					}
					Double materialUptoDateIpcAmount = printObj.getUptoCurrentDebitNote()
							.getTotalAmountAfterHandlingCharge();
					Double materialIpcAmount = printObj.getUptoCurrentDebitNote().getAmountCurrent();
					Double materialUptoPreviousAmount = printObj.getUptoCurrentDebitNote().getAmountUptoPrevious();
					ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount), "Debit for materials",
							null, materialUptoDateIpcAmount, materialUptoPreviousAmount, materialIpcAmount, false,
							false));

					deductionUptoDateIpcAmount += materialUptoDateIpcAmount;
					deductionIpcAmount += materialIpcAmount;
					deductionUptoPreviousIpcAmount += materialUptoPreviousAmount;
					String deductionsTotalText = "TOTAL DEDUCTION (" + deductionSrNo + ")";
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, deductionsTotalText, null,
							deductionUptoDateIpcAmount, deductionUptoPreviousIpcAmount, deductionIpcAmount, true,
							false));

					String netPayText = "PAYABLE AMOUNT (" + billItemSrNo + " - " + deductionSrNo + ")";
					Double netUptoDateIpcAmount = includingGstUptoDateIpcAmount - deductionUptoDateIpcAmount;
					Double netUptoPreviousIpcAmount = includingGstUptoPreviousIpcAmount
							- deductionUptoPreviousIpcAmount;
					Double netIpcAmount = includingGstIpcAmount - deductionIpcAmount;
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, netPayText, null, netUptoDateIpcAmount,
							netUptoPreviousIpcAmount, netIpcAmount, true, false));
					printObj.setIpcItems(ipcItems);

					Integer invoiceItemsCount = 0;
					List<BillPrintTaxInvoiceItemResponseDTO> invoiceItems = new ArrayList<>();
					for (CategoryItemDTO item : printObj.getBill().getWorkorder().getCategories()) {
						if (printObj.getBill().getWorkorder().getCategories().size() == 1) {
							invoiceItems.add(new BillPrintTaxInvoiceItemResponseDTO((++invoiceItemsCount),
									item.getName(), itemsTotalIpcAmount));
							break;
						}
						invoiceItems.add(
								new BillPrintTaxInvoiceItemResponseDTO((++invoiceItemsCount), item.getName(), null));
					}
					if (invoiceItems.size() > 1) {
						invoiceItems.add(new BillPrintTaxInvoiceItemResponseDTO((++invoiceItemsCount), "TOTAL",
								withGstItemsTotalIpcAmount));
					}
					printObj.setInvoiceItems(invoiceItems);

				} else {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Bill is incomplete.");
				}
			} else if (workorderTypeId.equals(WorkorderTypes.Highway.getId())) {

				List<DebitNoteItem> currentNoteItems = debitNoteDao.fetchDebitNoteItems(search);
				List<DebitNoteItem> approvedNoteItems = new ArrayList<DebitNoteItem>();
				List<DebitNoteItem> unapprovedNoteItems = new ArrayList<DebitNoteItem>();
				if (currentNoteItems != null) {
					for (DebitNoteItem item : currentNoteItems) {
						if (item.getDebitNote().getStatus().equals(DebitNoteStates.FullyApproved.getId())) {
							approvedNoteItems.add(item);
						} else {
							unapprovedNoteItems.add(item);
						}
					}
				}

				List<DebitNoteItemDTO> approvedItems = new ArrayList<>();
				Double totalHandlingCharge = 0.0;
				Double totalGstAmount = 0.0;
				Double totalAmountBeforeHandlingCharge = 0.0;
				Double totalAmountAfterHandlingCharge = 0.0;

				if (approvedNoteItems != null) {
					for (DebitNoteItem noteItem : approvedNoteItems) {
						DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
						totalHandlingCharge += noteItemDTO.getHandlingCharge();
						totalAmountAfterHandlingCharge += noteItemDTO.getFinalAmount();
						totalGstAmount += noteItemDTO.getGstAmount();
						approvedItems.add(noteItemDTO);
					}
				}
				totalAmountBeforeHandlingCharge = totalAmountAfterHandlingCharge - totalHandlingCharge;

				Double totalUnapprovedItemsAmount = 0.0;
				int unapprovedCount = 0;
				List<DebitNoteItemDTO> unapprovedItems = new ArrayList<>();
				if (unapprovedNoteItems != null) {
					for (DebitNoteItem noteItem : unapprovedNoteItems) {
						DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
						totalUnapprovedItemsAmount += noteItemDTO.getFinalAmount();
						unapprovedItems.add(noteItemDTO);
						++unapprovedCount;
					}
				}

				DebitNoteResponseDTO currentDebitNote = new DebitNoteResponseDTO(approvedItems, totalGstAmount,
						totalHandlingCharge, totalAmountBeforeHandlingCharge, totalAmountAfterHandlingCharge, null,
						totalAmountAfterHandlingCharge, unapprovedCount, totalUnapprovedItemsAmount, unapprovedItems);

				printObj.setCurrentDebitNote(currentDebitNote);
				if (unapprovedCount > 0) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, unapprovedCount
							+ " no. of debit notes are not approved yet. Provide approval then try again.");
				}

				Double totalHandlingChargeCumulative = 0.0;
				Double totalGstAmountCumulative = 0.0;
				Double totalAmountBeforeHandlingChargeCumulative = 0.0;
				Double totalAmountAfterHandlingChargeCumulative = 0.0;
				Double currentBillAmount = 0.0;
				Double uptoPreviousBillAmount = 0.0;
				List<DebitNoteItemDTO> approvedItemsCumulative = new ArrayList<>();
				List<DebitNoteItemDTO> unapprovedItemsCumulative = new ArrayList<>();
				Double totalUnapprovedItemsAmountCumulative = 0.0;
				int unapprovedCountCumulative = 0;

				Set<CategoryItemDTO> categoriesDTO = new LinkedHashSet<>();
				if (woObj.getWoContractor() != null) {
					List<WorkorderCategoryMapping> wccms = woBasicDao
							.fetchWorkorderCategoriesByWorkorderId(woObj.getId());
					List<CategoryItem> categories = woBasicDao
							.fetchActiveCategoryItemsByCompanyId(search.getCompanyId());
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
				billDTO.setWorkorder(woObj);
				printObj.setBill(billDTO);

//				debit notes

				if (allBills != null) {
					for (Bill thisBill : allBills) {
						if (thisBill.getToDate().after(bill.getToDate())) {
							continue;
						}
						search.setContractorId(thisBill.getWorkorder().getContractor().getId());
						search.setFromDate(thisBill.getFromDate());
						search.setToDate(thisBill.getToDate());
						search.setWorkorderId(thisBill.getWorkorder().getId());
						List<DebitNoteItem> noteItems = debitNoteDao.fetchDebitNoteItems(search);
						List<DebitNoteItem> approvedNoteItemsCumulative = new ArrayList<DebitNoteItem>();
						List<DebitNoteItem> unapprovedNoteItemsCumulative = new ArrayList<DebitNoteItem>();

						if (noteItems != null) {
							for (DebitNoteItem item : noteItems) {
								if (item.getDebitNote().getStatus().equals(DebitNoteStates.FullyApproved.getId())) {
									approvedNoteItemsCumulative.add(item);
								} else {
									unapprovedNoteItemsCumulative.add(item);
								}
							}
						}

						if (approvedNoteItemsCumulative != null) {
							for (DebitNoteItem noteItem : approvedNoteItemsCumulative) {
								DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
								totalHandlingChargeCumulative += noteItemDTO.getHandlingCharge();
								totalAmountAfterHandlingChargeCumulative += noteItemDTO.getFinalAmount();
								totalGstAmountCumulative += noteItemDTO.getGstAmount();
								if (thisBill.getId().equals(bill.getId())) {
									currentBillAmount += noteItemDTO.getFinalAmount();
								}
								approvedItemsCumulative.add(noteItemDTO);
							}
						}

						if (unapprovedNoteItemsCumulative != null) {
							for (DebitNoteItem noteItem : unapprovedNoteItemsCumulative) {
								DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
								totalUnapprovedItemsAmountCumulative += noteItemDTO.getFinalAmount();
								unapprovedItemsCumulative.add(noteItemDTO);
								++unapprovedCountCumulative;
							}
						}

					}
				}
				totalAmountBeforeHandlingChargeCumulative = totalAmountAfterHandlingChargeCumulative
						- totalHandlingChargeCumulative;
				uptoPreviousBillAmount = totalAmountAfterHandlingChargeCumulative - currentBillAmount;
				DebitNoteResponseDTO uptoCurrentDebitNote = new DebitNoteResponseDTO(approvedItemsCumulative,
						totalGstAmountCumulative, totalHandlingChargeCumulative,
						totalAmountBeforeHandlingChargeCumulative, totalAmountAfterHandlingChargeCumulative,
						uptoPreviousBillAmount, currentBillAmount, unapprovedCountCumulative,
						totalUnapprovedItemsAmountCumulative, unapprovedItemsCumulative);
				printObj.setUptoCurrentDebitNote(uptoCurrentDebitNote);

//				set bill BOQs

				List<BillPrintBoqResponseDTO> boqs = new ArrayList<>();
				List<BillBoqItem> boqItems = billItemDao.fetchBillBoqItemsByBillId(bill.getId());
				List<BillBoqQuantityItem> qtyItems = billItemDao.fetchBillBoqQuantityItemsByBillId(bill.getId());
				List<BillBoqQuantityItem> woQtyItems = billItemDao.fetchUptoCurrentBillBoqQuantityItemsByWorkorderId(
						bill.getWorkorder().getId(), bill.getToDate());

				List<WorkorderBoqWorkQtyMapping> woBoqs = woBoqWorkDao.fetchWoBoqWorkQtys(bill.getWorkorder().getId());
				Double workorderAmount = 0.0;
				for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
					workorderAmount += (woBoq.getRate() * woBoq.getQuantity());
				}
				printObj.setWorkorderAmount(workorderAmount);

				Double totalBillAmount = 0.0;
				boqs.add(new BillPrintBoqResponseDTO(null, null, "A", bill.getWorkorder().getType().getCode(), null,
						null, null, null, null, null, null, null, null, null));
				if (boqItems != null && qtyItems != null) {
					for (BillBoqItem billBoq : boqItems) {
						List<ChainageStretchBillItemResponseDTO> billQtyItems = new ArrayList<>();
						Double currentBillQuantity = 0.0;
						for (BillBoqQuantityItem qtyItem : qtyItems) {
							if (qtyItem.getBillBoqItemId().equals(billBoq.getId())) {
								currentBillQuantity += qtyItem.getQuantity();
								billQtyItems.add(new ChainageStretchBillItemResponseDTO(qtyItem.getId(),
										qtyItem.getBillBoqItemId(),
										new IdNameResponseDTO(qtyItem.getFromChainage().getId(),
												qtyItem.getFromChainage().getName()),
										new IdNameResponseDTO(qtyItem.getToChainage().getId(),
												qtyItem.getToChainage().getName()),
										qtyItem.getQuantity()));
							}
						}
						Double totalQuantity = 0.0;
						Double woBoqPreviousBilledQuantity = 0.0;
						Double billedQuantity = 0.0;
						Double rate = 0.0;
						String vendorDescription = null;
						if (woBoqs != null) {
							for (WorkorderBoqWorkQtyMapping woBoq : woBoqs) {
								if (woBoq.getBoq().getId().equals(billBoq.getBoq().getId())
										&& woBoq.getVendorDescription().equals(billBoq.getVendorDescription())) {
									totalQuantity = woBoq.getQuantity();
									rate = woBoq.getRate();
									vendorDescription = woBoq.getVendorDescription();
									if (qtyItems != null) {
										for (BillBoqQuantityItem qtyItem : qtyItems) {
											if (qtyItem.getBillBoqItem().getBoq().getId().equals(woBoq.getBoqId())
													&& woBoq.getVendorDescription()
															.equals(qtyItem.getBillBoqItem().getVendorDescription())) {
												billedQuantity += qtyItem.getQuantity();
											}
										}
									}
									if (woQtyItems != null) {
										for (BillBoqQuantityItem woQtyItem : woQtyItems) {
											if ((woQtyItem.getBillBoqItem().getBill().getToDate()
													.before(bill.getFromDate())
													|| woQtyItem.getBillBoqItem().getBill().getToDate()
															.equals(bill.getFromDate()))
													&& woQtyItem.getBillBoqItem().getBoq().getId()
															.equals(woBoq.getBoqId())
													&& woBoq.getVendorDescription().equals(
															woQtyItem.getBillBoqItem().getVendorDescription())) {
												woBoqPreviousBilledQuantity += woQtyItem.getQuantity();
											}
										}
									}
									break;
								}
							}
						}
						String boqSubcatMergeDescription = vendorDescription;
						if (billBoq.getBoq().getSubcategory() != null
								&& billBoq.getBoq().getSubcategory().getName() != null) {
							boqSubcatMergeDescription = billBoq.getBoq().getSubcategory().getName()
									+ boqSubcatMergeDescription;
						}
						totalBillAmount += (billedQuantity * rate);
						boqs.add(new BillPrintBoqResponseDTO(billBoq.getBoq().getCategory().getId(),
								billBoq.getBoq().getId(), billBoq.getBoq().getStandardBookCode(),
								boqSubcatMergeDescription, billBoq.getUnit().getName(), totalQuantity, rate,
								totalQuantity * rate, woBoqPreviousBilledQuantity + billedQuantity,
								(woBoqPreviousBilledQuantity + billedQuantity) * rate, woBoqPreviousBilledQuantity,
								woBoqPreviousBilledQuantity * rate, billedQuantity, billedQuantity * rate));
					}
					printObj.setBoqs(boqs);
					printObj.getBill().setBilledAmount(totalBillAmount);

//					price escalation code

					Double valueFo = SetObject.temporaryFoValue;
					Double totalRe = 0.0;
					Double totalVe = 0.0;
					Double currentVe = 0.0;
					Double previousVe = 0.0;
					Boolean isPriceAdjustment = true;
					List<PriceEscalationItemsResponseDTO> peItems = new ArrayList<PriceEscalationItemsResponseDTO>();
					Date dieselFromDate = allBills.get(0).getFromDate();
					Date dieselToDate = allBills.get(allBills.size() - 1).getToDate();
					search.setFromDate(dieselFromDate);
					search.setToDate(dieselToDate);
					List<DieselRateMapping> dieselRates = dieselRateDao.fetchDieselRates(search);
					Map<Long, Double> billWiseTotalWorkDone = new HashMap<>();
					Map<Long, Double> billWiseTotalWorkDoneWithoutAdjustment = new HashMap<>();
					if (allBills != null) {
						for (Bill billTraverse : allBills) {
							Double currentBillTraverseAmount = 0.0;
							for (BillBoqQuantityItem woQtyItem : woQtyItems) {
								if (woQtyItem.getBillBoqItem().getBill().getId().equals(billTraverse.getId())) {
									currentBillTraverseAmount += (woQtyItem.getQuantity() * woQtyItem.getRate());
								}
							}
							Double weightedDieselRate = null;
							Integer dieselRateCount = 0;
							if (dieselRates != null) {
								for (DieselRateMapping dieselRate : dieselRates) {
									Date rateDate = DateUtil.dateWithoutTime(dieselRate.getDate());
									Date billFromDate = DateUtil.dateWithoutTime(billTraverse.getFromDate());
									Date billToDate = DateUtil.dateWithoutTime(billTraverse.getToDate());
									if (rateDate.equals(billFromDate) || rateDate.equals(billToDate)
											|| (rateDate.after(billFromDate) && rateDate.before(billToDate))) {
										if (weightedDieselRate == null) {
											dieselRateCount++;
											weightedDieselRate = dieselRate.getRate();
										} else {
											dieselRateCount++;
											weightedDieselRate = (weightedDieselRate + dieselRate.getRate());
										}
									}
								}
							}
							if (weightedDieselRate == null) {
								return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
										"Diesel rates not present for bills.");

							}
							if (weightedDieselRate > 0.0) {
								weightedDieselRate = weightedDieselRate / dieselRateCount;
							}
							String duration = null;
							if (DateUtil.fromSameMonthAndYear(billTraverse.getFromDate(), billTraverse.getToDate())) {
								duration = DateUtil.getDayMonthYear(billTraverse.getFromDate(), false);
							} else {
								duration = DateUtil.getDayMonthYear(billTraverse.getFromDate(), true) + " - "
										+ DateUtil.getDayMonthYear(billTraverse.getToDate(), true);
							}
							Double valueVe = 0.4 * currentBillTraverseAmount
									* ((weightedDieselRate - valueFo) / valueFo);
							if (billTraverse.getToDate().before(bill.getFromDate())) {
								previousVe += valueVe;
							}
							if (billTraverse.getId().equals(bill.getId())) {
								currentVe += valueVe;
							}
							totalRe += currentBillTraverseAmount;
							totalVe += valueVe;
							billWiseTotalWorkDone.put(billTraverse.getId(), currentBillTraverseAmount + valueVe);
							billWiseTotalWorkDoneWithoutAdjustment.put(billTraverse.getId(), currentBillTraverseAmount);
							peItems.add(new PriceEscalationItemsResponseDTO(duration, currentBillTraverseAmount,
									weightedDieselRate, valueVe));
						}

					}
					PriceEscalationResponseDTO priceEscalation = new PriceEscalationResponseDTO(valueFo, peItems,
							totalRe, totalVe);
					printObj.setPriceEscalation(priceEscalation);

//					build response

					int srNoIdx = 0;
					List<BillPrintDescriptionItemResponseDTO> ipcItems = new ArrayList<>();
					String billItemSrNo = SetObject.getCharForNumber(++srNoIdx);

					Double itemsTotalUptoDateIpcAmount = 0.0;
					Double itemsTotalUptoPreviousIpcAmount = 0.0;
					Double itemsTotalIpcAmount = 0.0;
					for (CategoryItemDTO item : printObj.getBill().getWorkorder().getCategories()) {
						Double uptoDateIpcAmount = 0.0;
						Double uptoPreviousIpcAmount = 0.0;
						Double ipcAmount = 0.0;
						for (BillPrintBoqResponseDTO billBoq : printObj.getBoqs()) {
							if (billBoq.getCategoryId() == null)
								continue;
							if (billBoq.getCategoryId().equals(item.getId())) {
								uptoDateIpcAmount += billBoq.getCommulativeAmount();
								uptoPreviousIpcAmount += billBoq.getPreviousAmount();
								ipcAmount += billBoq.getCurrentAmount();
							}
						}
						itemsTotalUptoDateIpcAmount += uptoDateIpcAmount;
						itemsTotalUptoPreviousIpcAmount += uptoPreviousIpcAmount;
						itemsTotalIpcAmount += ipcAmount;
					}
					Double totalWorkDoneCurrentWithoutAdjustment = 0.0;
					if (billWiseTotalWorkDoneWithoutAdjustment.get(bill.getId()) != null)
						totalWorkDoneCurrentWithoutAdjustment = billWiseTotalWorkDoneWithoutAdjustment
								.get(bill.getId());

					Double totalWorkDoneCumulativeWithoutAdjustment = 0.0;
					for (Double value : billWiseTotalWorkDoneWithoutAdjustment.values()) {
						totalWorkDoneCumulativeWithoutAdjustment += value;
					}
					String billItemDescription = "Measurement Bills";
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(billItemSrNo, billItemDescription, null,
							totalWorkDoneCumulativeWithoutAdjustment,
							totalWorkDoneCumulativeWithoutAdjustment - totalWorkDoneCurrentWithoutAdjustment,
							totalWorkDoneCurrentWithoutAdjustment, false, false));

					printObj.setCumulativeBillAmount(itemsTotalUptoDateIpcAmount);
					printObj.setCurrentBillAmount(itemsTotalIpcAmount);
					printObj.setPreviousBillAmount(itemsTotalUptoPreviousIpcAmount);

					if (isPriceAdjustment) {
						String priceEscalationText = "Price Adjustment";
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, priceEscalationText, null, totalVe,
								previousVe, currentVe, false, true));
					}

					Double totalWorkDoneCumulative = 0.0;
					Double totalWorkDoneCurrent = 0.0;
					String totalWorkDoneText = "TOTAL WORK DONE";
					for (Double value : billWiseTotalWorkDone.values()) {
						totalWorkDoneCumulative += value;
					}
					if (billWiseTotalWorkDone.get(bill.getId()) != null)
						totalWorkDoneCurrent = billWiseTotalWorkDone.get(bill.getId());
					Double totalWorkDonePrevious = totalWorkDoneCumulative - totalWorkDoneCurrent;
					if (isPriceAdjustment)
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, totalWorkDoneText, null,
								totalWorkDoneCumulative, totalWorkDonePrevious, totalWorkDoneCurrent, true, true));

					Double applicableGst = bill.getApplicableIgst();
					if (applicableGst == null) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Bill is incomplete, GST value not provided.");
					}
					Double withGstItemsTotalIpcAmount = itemsTotalIpcAmount
							+ (itemsTotalIpcAmount * applicableGst / 100);

					printObj.setInvoiceAmountBeforeGst(itemsTotalIpcAmount);
					printObj.setInvoiceAmountAfterGst(withGstItemsTotalIpcAmount);

					if (bill.getIsIgstOnly() != null && bill.getIsIgstOnly()) {
						String igstText = "IGST (" + applicableGst + " %)";
						Double gstUptoDateIpcAmount = totalWorkDoneCumulative * (applicableGst / 100);
						Double gstUptoPreviousIpcAmount = totalWorkDonePrevious * (applicableGst / 100);
						Double gstIpcAmount = totalWorkDoneCurrent * (applicableGst / 100);
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, igstText, null, gstUptoDateIpcAmount,
								gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
					} else {
						String cgstText = "CGST (" + applicableGst / 2 + " %)";
						String sgstText = "SGST (" + applicableGst / 2 + " %)";
						Double gstUptoDateIpcAmount = totalWorkDoneCumulative * (applicableGst / 200);
						Double gstUptoPreviousIpcAmount = totalWorkDonePrevious * (applicableGst / 200);
						Double gstIpcAmount = totalWorkDoneCurrent * (applicableGst / 200);
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, cgstText, null, gstUptoDateIpcAmount,
								gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
						ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, sgstText, null, gstUptoDateIpcAmount,
								gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
					}

					Double includingGstUptoDateIpcAmount = totalWorkDoneCumulative
							+ (totalWorkDoneCumulative * (applicableGst / 100));
					Double includingGstUptoPreviousIpcAmount = totalWorkDonePrevious
							+ (totalWorkDonePrevious * (applicableGst / 100));
					Double includingGstIpcAmount = totalWorkDoneCurrent
							+ (totalWorkDoneCurrent * (applicableGst / 100));
					String totalAmountText = "Total Amount (" + SetObject.getCharForNumber(srNoIdx) + ")";
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, totalAmountText, null,
							includingGstUptoDateIpcAmount, includingGstUptoPreviousIpcAmount, includingGstIpcAmount,
							true, true));

//					deductions in build res

					String deductionText = "Deductions :";
					String deductionSrNo = SetObject.getCharForNumber(++srNoIdx);
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(deductionSrNo, deductionText, null, null, null,
							null, true, false));
					List<BillDeductionMapping> woBillDeductions = deductionMapDao
							.fetchUptoCurrentMappedBillDeductionsByWorkorderId(bill.getWorkorder().getId(),
									bill.getToDate());
					Integer deductionCount = 0;
					Set<String> distinctDeductionIdTypeValuePairs = new LinkedHashSet<>();
					Double deductionUptoDateIpcAmount = 0.0;
					Double deductionIpcAmount = 0.0;
					Double deductionUptoPreviousIpcAmount = 0.0;

					if (woBillDeductions != null) {

						for (BillDeductionMapping deductionMap : woBillDeductions) {
							distinctDeductionIdTypeValuePairs.add(deductionMap.getDeduction().getId() + "-"
									+ (deductionMap.getIsPercentage() ? "1" : "0") + "-"
									+ (deductionMap.getIsPercentage() ? (deductionMap.getValue().toString().length() > 5
											? deductionMap.getValue().toString().substring(0, 4)
											: deductionMap.getValue().toString()) : 0));
						}

						for (String deductionPair : distinctDeductionIdTypeValuePairs) {

							String[] idTypeValue = deductionPair.split("-");
							Integer distinctDeductionId = Integer.valueOf(idTypeValue[0]);
							boolean distinctDeductionTypePercentage = (Integer.valueOf(idTypeValue[1]) == 1) ? true
									: false;
							Double distinctDeductionValue = Double.valueOf(idTypeValue[2]);

							Double uptoDateDeductionIpcAmount = 0.0;
							Double uptoPreviousDeductionIpcAmount = 0.0;
							Double currentDeductionIpcAmount = 0.0;
							Double deductionValue = null;
							String deductionName = null;

							for (BillDeductionMapping deductionMap : woBillDeductions) {

								if (distinctDeductionId.equals(deductionMap.getDeduction().getId())
										&& deductionMap.getIsPercentage().equals(distinctDeductionTypePercentage)
										&& !distinctDeductionTypePercentage && bill.getWorkorder().getId()
												.equals(deductionMap.getBill().getWorkorder().getId())) {

									if (deductionName == null) {
										deductionName = deductionMap.getDeduction().getName();
									}
									if (deductionMap.getBillId().equals(bill.getId())) {
										currentDeductionIpcAmount += (deductionMap.getValue());
										deductionValue = deductionMap.getValue();
									} else {
										uptoPreviousDeductionIpcAmount += (deductionMap.getValue());
									}

								} else if (distinctDeductionId.equals(deductionMap.getDeduction().getId())
										&& deductionMap.getIsPercentage().equals(distinctDeductionTypePercentage)
										&& distinctDeductionValue.equals(deductionMap.getValue())
										&& distinctDeductionTypePercentage && bill.getWorkorder().getId()
												.equals(deductionMap.getBill().getWorkorder().getId())) {

									if (deductionName == null) {
										deductionName = deductionMap.getDeduction().getName();
									}
									deductionValue = deductionMap.getValue();

									if (deductionMap.getBillId().equals(bill.getId())) {
										Double deductionBillWorkdone = 0.00;
										if (billWiseTotalWorkDone.get(deductionMap.getBillId()) != null)
											deductionBillWorkdone = (Double) billWiseTotalWorkDone
													.get(deductionMap.getBillId());
										currentDeductionIpcAmount = (double) Math
												.round((deductionValue / 100) * deductionBillWorkdone * 100) / 100;
									} else {
										Double deductionBillWorkdone = 0.00;
										if (billWiseTotalWorkDone.get(deductionMap.getBillId()) != null)
											deductionBillWorkdone = (Double) billWiseTotalWorkDone
													.get(deductionMap.getBillId());
										uptoPreviousDeductionIpcAmount += (double) Math
												.round((deductionValue / 100) * deductionBillWorkdone * 100) / 100;
									}
								}
							}
							if (distinctDeductionTypePercentage) {
								deductionName += (" @ " + distinctDeductionValue + "%");
							}
							uptoDateDeductionIpcAmount = uptoPreviousDeductionIpcAmount + currentDeductionIpcAmount;
							deductionIpcAmount += currentDeductionIpcAmount;
							deductionUptoDateIpcAmount += uptoDateDeductionIpcAmount;
							deductionUptoPreviousIpcAmount += uptoPreviousDeductionIpcAmount;
							ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount), deductionName,
									deductionValue, uptoDateDeductionIpcAmount, uptoPreviousDeductionIpcAmount,
									currentDeductionIpcAmount, false, false));

						}
					}
					Double materialUptoDateIpcAmount = printObj.getUptoCurrentDebitNote()
							.getTotalAmountAfterHandlingCharge();
					Double materialIpcAmount = printObj.getUptoCurrentDebitNote().getAmountCurrent();
					Double materialUptoPreviousAmount = printObj.getUptoCurrentDebitNote().getAmountUptoPrevious();
					ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount), "Debit for materials",
							null, materialUptoDateIpcAmount, materialUptoPreviousAmount, materialIpcAmount, false,
							false));

					deductionUptoDateIpcAmount += materialUptoDateIpcAmount;
					deductionIpcAmount += materialIpcAmount;
					deductionUptoPreviousIpcAmount += materialUptoPreviousAmount;
					String deductionsTotalText = "TOTAL DEDUCTION (" + deductionSrNo + ")";
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, deductionsTotalText, null,
							deductionUptoDateIpcAmount, deductionUptoPreviousIpcAmount, deductionIpcAmount, true,
							false));

					String netPayText = "PAYABLE AMOUNT (" + billItemSrNo + " - " + deductionSrNo + ")";
					Double netUptoDateIpcAmount = includingGstUptoDateIpcAmount - deductionUptoDateIpcAmount;
					Double netUptoPreviousIpcAmount = includingGstUptoPreviousIpcAmount
							- deductionUptoPreviousIpcAmount;
					Double netIpcAmount = includingGstIpcAmount - deductionIpcAmount;
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, netPayText, null, netUptoDateIpcAmount,
							netUptoPreviousIpcAmount, netIpcAmount, true, false));
					printObj.setIpcItems(ipcItems);

					Integer invoiceItemsCount = 0;
					List<BillPrintTaxInvoiceItemResponseDTO> invoiceItems = new ArrayList<>();
					for (CategoryItemDTO item : printObj.getBill().getWorkorder().getCategories()) {
						if (printObj.getBill().getWorkorder().getCategories().size() == 1) {
							invoiceItems.add(new BillPrintTaxInvoiceItemResponseDTO((++invoiceItemsCount),
									item.getName(), itemsTotalIpcAmount));
							break;
						}
						invoiceItems.add(
								new BillPrintTaxInvoiceItemResponseDTO((++invoiceItemsCount), item.getName(), null));
					}
					if (invoiceItems.size() > 1) {
						invoiceItems.add(new BillPrintTaxInvoiceItemResponseDTO((++invoiceItemsCount), "TOTAL",
								withGstItemsTotalIpcAmount));
					}
					printObj.setInvoiceItems(invoiceItems);

				} else {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Bill is incomplete.");
				}
			} else if (workorderTypeId.equals(WorkorderTypes.Machine_Hiring.getId())) {

				Double totalBillAmount = 0.0;

				Date firstBillFromDate = allBills.get(0).getFromDate();
				Date lastBillToDate = allBills.get(allBills.size() - 1).getToDate();
				search.setFromDate(firstBillFromDate);
				search.setToDate(lastBillToDate);
				List<DieselRateMapping> dieselRates = dieselRateDao.fetchDieselRates(search);

				List<WorkorderHiringMachineWorkItemMapping> machineItems = woHireMachineDao
						.fetchWorkorderHiringMachineWorkItemMapping(bill.getWorkorder().getId());
				Set<Long> distinctMachineItemIds = new HashSet<>();
				if (machineItems != null) {
					machineItems.forEach(obj -> {
						distinctMachineItemIds.add(obj.getId());
					});
				}
				List<WorkorderHiringMachineRateDetails> machineRateDetailsList = woHireMachineDao
						.fetchHiringMachineItemRateDetailsByWorkItemIds(distinctMachineItemIds);

				Map<Long, MachineryRunningMode> categoryRunModeMap = new HashMap<Long, MachineryRunningMode>();

				List<BillMachineCategoryResponse> machineCategories = new ArrayList<>();
				if (machineItems != null) {
					for (WorkorderHiringMachineWorkItemMapping hireMachineItem : machineItems) {
						categoryRunModeMap.put(hireMachineItem.getMachineCatgeoryId(),
								hireMachineItem.getRunningMode());
						machineCategories.add(new BillMachineCategoryResponse(hireMachineItem.getMachineCatgeoryId(),
								hireMachineItem.getMachineCategory().getName(), hireMachineItem.getMachineCount(),
								new ArrayList<>(), hireMachineItem.getId(), hireMachineItem.getDescription(),
								hireMachineItem.getMachineCategory().getIsMultiFuel() != null
										? hireMachineItem.getMachineCategory().getIsMultiFuel()
										: false,
								hireMachineItem.getMachineCategory().getPrimaryReadingUnit() != null
										? hireMachineItem.getMachineCategory().getPrimaryReadingUnit().getId()
										: null,
								hireMachineItem.getMachineCategory().getPrimaryReadingUnit() != null
										? hireMachineItem.getMachineCategory().getPrimaryReadingUnit().getName()
										: null,
								hireMachineItem.getMachineCategory().getSecondaryReadingUnit() != null
										? hireMachineItem.getMachineCategory().getSecondaryReadingUnit().getId()
										: null,
								hireMachineItem.getMachineCategory().getSecondaryReadingUnit() != null
										? hireMachineItem.getMachineCategory().getSecondaryReadingUnit().getName()
										: null,
								hireMachineItem.getRunningMode()));
					}
				}
				Set<Long> billIds = new HashSet<>();
				for (Bill billTraverse : allBills) {
					billIds.add(billTraverse.getId());
				}

				List<BillMachineMapping> billMachines = billMachineDao.fetchBillMachineMappingByBillIds(billIds);

				WorkorderHiringMachineWork hiringMachineWork = woHireMachineDao
						.fetchWorkorderHiringMachineWork(bill.getWorkorder().getId());

				Map<Long, Double> billWiseTotalWorkDone = new HashMap<>();

				Map<String, Double> machineWiseTotalWorkDone = new HashMap<>();

				Map<String, Double> machineWiseCurrentWorkDone = new HashMap<>();

				Map<String, String> machineWiseIpcDescriptions = new HashMap<>();

				List<WorkorderBillInfoMachineMapping> workorderBillInfoMachineMapp = new ArrayList<>();

				Double fuelDebitCumulativeAmount = 0d;
				Double fuelDebitCurrentAmount = 0d;

				if (billMachines != null) {

					Set<Long> machineIds = new HashSet<>();
					billMachines.forEach(bm -> machineIds.add(bm.getMachineId()));

					List<MachineDPR> machinesDPRList = dprDao.fetchMachineDprList(machineIds, firstBillFromDate,
							lastBillToDate, bill.getSiteId());

					List<WorkorderHiringMachineWorkItemMapping> hiringMachineWorkItems = null;
					if (hiringMachineWork != null)
						hiringMachineWorkItems = machineItems;

					List<IssueSlipItem> issueSlipItems = issueSlipItemDao.fetchIssueSlipItems(firstBillFromDate,
							lastBillToDate, machineIds);

					for (BillMachineMapping bmm : billMachines) {

						BillMachineMapResponse mappedMachine = null;
						Double fuelTakenFromStore = 0.0;
						Long categoryId = null;
						String categoryName = null;
						String machineRegNo = null;
						Boolean isMultiFuel = false;
						UnitMaster primaryEngineUnit = null;
						UnitMaster secondaryEngineUnit = null;

//						for equipments
						if (bmm.getMachineType() == MachineType.Equipment.ordinal()) {
							Equipment machine = machineDao.fetchEquipment(bmm.getMachineId());
							machineRegNo = !machine.getRegNo().isBlank() ? machine.getRegNo() : machine.getAssetCode();
							categoryId = machine.getCategory().getId();
							categoryName = machine.getCategory().getName();
							isMultiFuel = machine.getCategory().getIsMultiFuel() != null
									? machine.getCategory().getIsMultiFuel()
									: false;
							if (machine.getCategory().getPrimaryReadingUnit() != null) {
								primaryEngineUnit = new UnitMaster(
										machine.getCategory().getPrimaryReadingUnit().getId().shortValue(),
										machine.getCategory().getPrimaryReadingUnit().getName(), true);
							} else {
								primaryEngineUnit = new UnitMaster();
							}
							if (machine.getCategory().getSecondaryReadingUnit() != null) {
								secondaryEngineUnit = new UnitMaster(
										machine.getCategory().getSecondaryReadingUnit().getId().shortValue(),
										machine.getCategory().getSecondaryReadingUnit().getName(), true);
							} else {
								secondaryEngineUnit = new UnitMaster();
							}
							mappedMachine = new BillMachineMapResponse(bmm.getId(), machine.getId(),
									machine.getAssetCode(),
									!machine.getRegNo().isBlank() ? machine.getRegNo() : machine.getAssetCode(),
									(byte) MachineType.Equipment.ordinal(), bmm.getFromDate(), bmm.getToDate());

							if (issueSlipItems != null) {
								for (IssueSlipItem issueSlipItem : issueSlipItems) {
									Date rateDate = DateUtil.dateWithoutTime(issueSlipItem.getIssueSlip().getDateOn());
									Date billFromDate = DateUtil.dateWithoutTime(bmm.getFromDate());
									Date billToDate = DateUtil.dateWithoutTime(bmm.getToDate());

									if (bmm.getMachineId().equals(issueSlipItem.getEquipmentId())
											&& (rateDate.equals(billFromDate) || rateDate.equals(billToDate)
													|| (rateDate.after(billFromDate) && rateDate.before(billToDate)))) {
										Double issuedQuantity = issueSlipItem.getIssuedQuantity() != null
												? issueSlipItem.getIssuedQuantity()
												: 0;
										Double returnedQuantity = issueSlipItem.getReturnedQuantity() != null
												? issueSlipItem.getReturnedQuantity()
												: 0;
										fuelTakenFromStore += (issuedQuantity - returnedQuantity);
									}
								}
							}

						}

						Double weightedDieselRate = null;
						Integer dieselRateCount = 0;
						if (dieselRates != null) {
							for (DieselRateMapping dieselRate : dieselRates) {
								Date rateDate = DateUtil.dateWithoutTime(dieselRate.getDate());
								Date billFromDate = DateUtil.dateWithoutTime(bmm.getFromDate());
								Date billToDate = DateUtil.dateWithoutTime(bmm.getToDate());
								if (rateDate.equals(billFromDate) || rateDate.equals(billToDate)
										|| (rateDate.after(billFromDate) && rateDate.before(billToDate))) {
									if (weightedDieselRate == null) {
										dieselRateCount++;
										weightedDieselRate = dieselRate.getRate();
									} else {
										dieselRateCount++;
										weightedDieselRate = (weightedDieselRate + dieselRate.getRate());
									}
								}
							}
						}
						if (weightedDieselRate == null)
							weightedDieselRate = 0.0;

						if (weightedDieselRate > 0.0) {
							weightedDieselRate = weightedDieselRate / dieselRateCount;
						}
						Double netTotalPrimaryActualReading = 0.0;
						Double netTotalSecondaryActualReading = 0.0;
						Integer totalTripsTaken = 0;
						if (mappedMachine != null && categoryId != null) {
							for (BillMachineCategoryResponse categoryResponse : machineCategories) {
								if ((categoryResponse.getId().equals(categoryId)
										&& bmm.getWoHiringMachineWorkItemId() == null)
										|| (categoryResponse.getId().equals(categoryId)
												&& categoryResponse.getWoHiringMachineWorkItemId() != null
												&& bmm.getWoHiringMachineWorkItemId() != null
												&& categoryResponse.getWoHiringMachineWorkItemId()
														.equals(bmm.getWoHiringMachineWorkItemId()))) {
									List<MachineDprGetResponse> machineDprList = new ArrayList<>();
									Date machineFromDate = DateUtil.dateWithoutTime(mappedMachine.getFromDate());
									Date machineToDate = DateUtil.dateWithoutTime(mappedMachine.getToDate());
									Date dprDate = machineFromDate;
									float totalWorkingDays = 0f;
									float totalPresentDaysInNight = 0f;
									float totalPresentDaysInDay = 0f;
									Map<Integer, Double> totalBreakdownHoursInDayMap = new HashMap<Integer, Double>();
									Map<Integer, Double> totalBreakdownHoursInNightMap = new HashMap<Integer, Double>();
									while (!dprDate.after(machineToDate)) {

										if (machinesDPRList != null) {
											MachineDprGetResponse dateDpr2 = new MachineDprGetResponse(null, dprDate,
													null, null, null, null, null, null, null, null, null, null, null,
													null, null, null, null, null, null);
											for (MachineDPR machineDpr : machinesDPRList) {
												MachineDprGetResponse dateDpr = new MachineDprGetResponse(null, dprDate,
														null, null, null, null, null, null, null, null, null, null,
														null, null, null, null, null, null, null);
												if (machineDpr.getMachineId().equals(mappedMachine.getMachineId())
														&& machineDpr.getMachineType()
																.equals(mappedMachine.getMachineType())
														&& dprDate.equals(
																DateUtil.dateWithoutTime(machineDpr.getDated()))) {
													if (bmm.getWoHiringMachineWorkItemId() != null) {
														Boolean hasDifferentRunningMode = false;
														for (WorkorderHiringMachineWorkItemMapping hireMachineItem : machineItems) {
															if (hireMachineItem.getId()
																	.equals(bmm.getWoHiringMachineWorkItemId())
																	&& !hireMachineItem.getRunningMode()
																			.equals(machineDpr.getRunningMode())) {
																hasDifferentRunningMode = true;
															}
														}
														if (hasDifferentRunningMode) {
															continue;
														}
													} else {
														if (!categoryRunModeMap.get(categoryId)
																.equals(machineDpr.getRunningMode())) {
															continue;
														}
													}
													dateDpr.setPrimaryOpeningMeterReading(
															machineDpr.getPrimaryOpeningMeterReading());
													dateDpr.setPrimaryClosingMeterReading(
															machineDpr.getPrimaryClosingMeterReading());
													dateDpr.setSecondaryOpeningMeterReading(
															machineDpr.getSecondaryOpeningMeterReading());
													dateDpr.setSecondaryClosingMeterReading(
															machineDpr.getSecondaryClosingMeterReading());
													dateDpr.setPrimaryOpeningActualReading(
															machineDpr.getPrimaryOpeningActualReading());
													dateDpr.setPrimaryClosingActualReading(
															machineDpr.getPrimaryClosingActualReading());
													dateDpr.setSecondaryOpeningActualReading(
															machineDpr.getSecondaryOpeningActualReading());
													dateDpr.setSecondaryClosingActualReading(
															machineDpr.getSecondaryClosingActualReading());
													Integer noOfDaysInMonth = DateUtil
															.getNoOfDaysInTheCurrentMonth(machineDpr.getDated());

													if (machineDpr.getRunningMode().equals(MachineryRunningMode.TRIP)) {
														totalTripsTaken += machineDpr.getTripCount();
														if (machineDpr.getAttendanceStatus() != null
																&& machineDpr.getAttendanceStatus()
																		.equals(MachineryAttendanceStatus.HALFDAY)) {
															totalPresentDaysInDay += 0.5d;
														} else {
															totalPresentDaysInDay += 1d;
														}
													} else if (machineDpr.getRunningMode()
															.equals(MachineryRunningMode.SHIFTS)
															&& machineDpr.getShift() != null) {

														if (machineDpr.getShift().equals(MachineryShifts.DAYTIME)) {

															if (machineDpr.getAttendanceStatus() != null
																	&& machineDpr.getAttendanceStatus().equals(
																			MachineryAttendanceStatus.HALFDAY)) {
																totalPresentDaysInDay += 0.5d;
															} else {
																totalPresentDaysInDay += 1d;
															}

															if (machineDpr.getBreakdownHours() != null) {
																if (totalBreakdownHoursInDayMap != null
																		&& totalBreakdownHoursInDayMap
																				.containsKey(noOfDaysInMonth)) {
																	totalBreakdownHoursInDayMap.put(noOfDaysInMonth,
																			totalBreakdownHoursInDayMap
																					.get(noOfDaysInMonth)
																					+ machineDpr.getBreakdownHours());
																} else {
																	totalBreakdownHoursInDayMap.put(noOfDaysInMonth,
																			machineDpr.getBreakdownHours());
																}
															}
														} else {
															if (machineDpr.getAttendanceStatus() != null
																	&& machineDpr.getAttendanceStatus().equals(
																			MachineryAttendanceStatus.HALFDAY)) {
																totalPresentDaysInNight += 0.5d;
															} else {
																totalPresentDaysInNight += 1d;
															}

															if (machineDpr.getBreakdownHours() != null) {
																if (totalBreakdownHoursInNightMap != null
																		&& totalBreakdownHoursInNightMap
																				.containsKey(noOfDaysInMonth)) {
																	totalBreakdownHoursInNightMap.put(noOfDaysInMonth,
																			totalBreakdownHoursInNightMap
																					.get(noOfDaysInMonth)
																					+ machineDpr.getBreakdownHours());
																} else {
																	totalBreakdownHoursInNightMap.put(noOfDaysInMonth,
																			machineDpr.getBreakdownHours());
																}
															}
														}

													}

													if (machineDpr.getPrimaryOpeningActualReading() != null
															&& machineDpr.getPrimaryClosingActualReading() != null) {
														Double netPrimaryActualReading = machineDpr
																.getPrimaryClosingActualReading()
																- machineDpr.getPrimaryOpeningActualReading();
														dateDpr.setNetPrimaryActualReading(netPrimaryActualReading);
														netTotalPrimaryActualReading += netPrimaryActualReading;
													}
													if (machineDpr.getSecondaryOpeningActualReading() != null
															&& machineDpr.getSecondaryClosingActualReading() != null) {
														Double netSecondaryActualReading = machineDpr
																.getSecondaryClosingActualReading()
																- machineDpr.getSecondaryOpeningActualReading();
														dateDpr.setNetSecondaryActualReading(netSecondaryActualReading);
														netTotalSecondaryActualReading += netSecondaryActualReading;
													}

													if (issueSlipItems != null) {

														for (IssueSlipItem issueSlipItem : issueSlipItems) {

															Date rateDate = DateUtil.dateWithoutTime(
																	issueSlipItem.getIssueSlip().getDateOn());
															if (bmm.getMachineType() == MachineType.Equipment
																	.ordinal()) {

																if (bmm.getMachineId()
																		.equals(issueSlipItem.getEquipmentId())
																		&& rateDate.equals(dprDate)) {
																	Double issuedQuantity = issueSlipItem
																			.getIssuedQuantity() != null
																					? issueSlipItem.getIssuedQuantity()
																					: 0;
																	Double returnedQuantity = issueSlipItem
																			.getReturnedQuantity() != null
																					? issueSlipItem
																							.getReturnedQuantity()
																					: 0;
																	Double netFuelTaken = (issuedQuantity
																			- returnedQuantity);
																	System.out.println(
																			dprDate + "---------" + netFuelTaken);
																	dateDpr.setStoreFuelTransactionQuantity(
																			netFuelTaken);
																}
															} else if (bmm.getMachineType() == MachineType.Plant
																	.ordinal()) {

																if (bmm.getMachineId()
																		.equals(issueSlipItem.getPlantId())
																		&& rateDate.equals(dprDate)) {
																	Double issuedQuantity = issueSlipItem
																			.getIssuedQuantity() != null
																					? issueSlipItem.getIssuedQuantity()
																					: 0;
																	Double returnedQuantity = issueSlipItem
																			.getReturnedQuantity() != null
																					? issueSlipItem
																							.getReturnedQuantity()
																					: 0;
																	Double netFuelTaken = (issuedQuantity
																			- returnedQuantity);
																	dateDpr.setStoreFuelTransactionQuantity(
																			netFuelTaken);
																}
															}
														}
													}

													dateDpr.setRunningMode(machineDpr.getRunningMode());
													dateDpr.setShift(machineDpr.getShift());
													dateDpr.setTripCount(machineDpr.getTripCount());
													dateDpr.setStatus(machineDpr.getAttendanceStatus());
													dateDpr.setRemarks(machineDpr.getRemarks());
													dateDpr.setBreakdownHours(machineDpr.getBreakdownHours());
													machineDprList.add(dateDpr);
												}
											}
											if (machineDprList != null) {
												Map<Date, List<MachineDprGetResponse>> collect = machineDprList.stream()
														.collect(Collectors.groupingBy(o -> o.getDated()));
												if (!collect.containsKey(dprDate)) {
													machineDprList.add(dateDpr2);
												}
											}
										}
										totalWorkingDays++;
										dprDate = DateUtil.nextDateWithoutTime(dprDate);
									}
									mappedMachine.setNetTotalPrimaryActualReading(netTotalPrimaryActualReading);
									mappedMachine.setNetTotalSecondaryActualReading(netTotalSecondaryActualReading);
									mappedMachine.setTotalPresentInDayTime(totalPresentDaysInDay);
									mappedMachine.setTotalPresentInNightTime(totalPresentDaysInNight);
									mappedMachine.setTotalWorkingDays(totalWorkingDays);
									mappedMachine.setTotalAbsentInNightTime(totalWorkingDays - totalPresentDaysInNight);
									mappedMachine.setTotalAbsentInDayTime(totalWorkingDays - totalPresentDaysInDay);
									mappedMachine.setTripCount(totalTripsTaken);
									mappedMachine.setTotalBreakdownHoursInDayShift(totalBreakdownHoursInDayMap != null
											? totalBreakdownHoursInDayMap.values().stream().mapToDouble(x -> x).sum()
											: 0);
									mappedMachine
											.setTotalBreakdownHoursInNightShift(totalBreakdownHoursInNightMap != null
													? totalBreakdownHoursInNightMap.values().stream()
															.mapToDouble(x -> x).sum()
													: 0);
									mappedMachine
											.setTotalBreakdownHours(mappedMachine.getTotalBreakdownHoursInDayShift()
													+ mappedMachine.getTotalBreakdownHoursInNightShift());

									Double machineRateInDayTime = 0.0;
									Double machineRateInNightTime = 0.0;
									if (machineRateDetailsList != null) {
										for (WorkorderHiringMachineRateDetails rateDetail : machineRateDetailsList) {
											if ((rateDetail.getMachineItem().getMachineCatgeoryId().equals(categoryId)
													&& categoryResponse.getWoHiringMachineWorkItemId() == null)
													|| (rateDetail.getMachineItem().getMachineCatgeoryId()
															.equals(categoryId)
															&& categoryResponse.getWoHiringMachineWorkItemId()
																	.equals(rateDetail.getWoHiringMachineItemId()))) {
												if (rateDetail.getMachineItem().getRunningMode()
														.equals(MachineryRunningMode.TRIP)) {
													machineRateInDayTime = rateDetail.getRate();
													mappedMachine.setIsShiftBased(false);
												} else {
													mappedMachine.setIsShiftBased(true);
													if (rateDetail.getShift() != null
															&& rateDetail.getShift().equals(MachineryShifts.DAYTIME)) {
														machineRateInDayTime = rateDetail.getRate();
													} else {
														machineRateInNightTime = rateDetail.getRate();
													}
												}
											}
										}
									}
									mappedMachine.setMachineRateAsPerWorkorderDayShift(machineRateInDayTime);
									mappedMachine.setMachineRateAsPerWorkorderNightShift(machineRateInNightTime);
									mappedMachine.setDprList(machineDprList);
									if (hiringMachineWorkItems != null) {
										for (WorkorderHiringMachineWorkItemMapping hmItem : hiringMachineWorkItems) {
											if ((hmItem.getMachineCatgeoryId().equals(categoryId)
													&& categoryResponse.getWoHiringMachineWorkItemId() == null)
													|| (hmItem.getMachineCatgeoryId().equals(categoryId)
															&& categoryResponse.getWoHiringMachineWorkItemId()
																	.equals(hmItem.getId()))) {
												mappedMachine.setPrimaryEngineMileage(hmItem.getPrimaryEngineMileage());
												mappedMachine.setPrimaryEngineMileageUnit(primaryEngineUnit.getName());
												Double primaryFuelUsed = 0.0;
												Double secondaryFuelUsed = 0.0;
//												FUEL MILEAGE
												if (primaryEngineUnit.getName() != null
														&& (primaryEngineUnit.getName().toLowerCase().contains("hr")
																|| primaryEngineUnit.getName().toLowerCase()
																		.contains("hour"))) {

													primaryFuelUsed = netTotalPrimaryActualReading
															* (hmItem.getPrimaryEngineMileage() != null
																	&& hmItem.getPrimaryEngineMileage() != 0
																			? hmItem.getPrimaryEngineMileage()
																			: 0);

												} else {

													primaryFuelUsed = netTotalPrimaryActualReading
															/ (hmItem.getPrimaryEngineMileage() != null
																	&& hmItem.getPrimaryEngineMileage() != 0
																			? hmItem.getPrimaryEngineMileage()
																			: 1);
												}
												Double totalFuelUsed = 0.0;
												totalFuelUsed += primaryFuelUsed;
												if (isMultiFuel) {
													mappedMachine.setSecondaryEngineMileage(
															hmItem.getSecondaryEngineMileage());
													mappedMachine.setSecondaryEngineMileageUnit(
															secondaryEngineUnit.getName());

													if (secondaryEngineUnit.getName() != null && (secondaryEngineUnit
															.getName().toLowerCase().contains("hr")
															|| secondaryEngineUnit.getName().toLowerCase()
																	.contains("hour"))) {

														secondaryFuelUsed = netTotalSecondaryActualReading
																* (hmItem.getSecondaryEngineMileage() != null
																		&& hmItem.getSecondaryEngineMileage() != 0
																				? hmItem.getSecondaryEngineMileage()
																				: 0);

													} else {

														secondaryFuelUsed = netTotalSecondaryActualReading
																/ (hmItem.getSecondaryEngineMileage() != null
																		&& hmItem.getSecondaryEngineMileage() != 0
																				? hmItem.getSecondaryEngineMileage()
																				: 1);
													}

													totalFuelUsed += secondaryFuelUsed;
												}

												Double handlingCharge = hmItem.getFuelHandlingCharge() != null
														? weightedDieselRate * hmItem.getFuelHandlingCharge() / 100
														: 0.0;
												mappedMachine.setFuelRateIncludingHandlingCharge(
														weightedDieselRate + handlingCharge);
												mappedMachine.setFuelDebitAmount(
														mappedMachine.getFuelRateIncludingHandlingCharge()
																* fuelTakenFromStore);

												if (!hmItem.getDieselInclusive() && weightedDieselRate <= 0.0) {

													DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

													return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
															"Diesel rates not present for bills for date range : "
																	+ formatter.format(bmm.getFromDate()) + " - "
																	+ formatter.format(bmm.getToDate()));

												}
												mappedMachine.setTotalFuelAsPerWorkorder(totalFuelUsed);
												mappedMachine.setFuelTakenFromStore(fuelTakenFromStore);
												Double excessFuelTaken = fuelTakenFromStore - totalFuelUsed;
												mappedMachine.setExcessFuelTaken(
														excessFuelTaken > 0.0 ? excessFuelTaken : 0.0);

												Double dieselEscalationPrice = 0.0;

												if (!hmItem.getDieselInclusive()) {
													if (hiringMachineWork.getDieselRate() != null
															&& hiringMachineWork.getDieselRate() > 0.0) {

														if (!hiringMachineWork.getDieselRate()
																.equals(weightedDieselRate)) {

															mappedMachine.setDieselRateAsPerWorkorder(
																	hiringMachineWork.getDieselRate());
															mappedMachine
																	.setFuelRateAsPerWorkorderIncludingHandlingCharge(
																			hmItem.getFuelHandlingCharge() != null
																					&& hmItem
																							.getFuelHandlingCharge() > 0
																									? hiringMachineWork
																											.getDieselRate()
																											+ (hmItem
																													.getFuelHandlingCharge()
																													* hiringMachineWork
																															.getDieselRate())
																													/ 100
																									: hiringMachineWork
																											.getDieselRate());

															mappedMachine.setTotalFuelRateAsPerWorkorder(
																	totalFuelUsed * mappedMachine
																			.getFuelRateAsPerWorkorderIncludingHandlingCharge());

															mappedMachine.setTotalFuelRateAsPerWeightedRate(
																	totalFuelUsed * mappedMachine
																			.getFuelRateIncludingHandlingCharge());

															mappedMachine.setDieselEscalationPrice(mappedMachine
																	.getTotalFuelRateAsPerWeightedRate()
																	- mappedMachine.getTotalFuelRateAsPerWorkorder());

															dieselEscalationPrice = mappedMachine
																	.getDieselEscalationPrice();

														}

													}
												}

												if (hmItem.getDieselInclusive()) {

													if (excessFuelTaken > 0) {

														if (weightedDieselRate <= 0.0) {
															DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
															return new CustomResponse(Responses.FORBIDDEN.getCode(),
																	null,
																	"Diesel rates not present for bills for date range : "
																			+ formatter.format(bmm.getFromDate())
																			+ " - "
																			+ formatter.format(bmm.getToDate()));
														}

														mappedMachine.setFuelDebitAmount(
																mappedMachine.getFuelRateIncludingHandlingCharge()
																		* excessFuelTaken);
													} else {
														excessFuelTaken = 0d;
														mappedMachine.setFuelDebitAmount(
																mappedMachine.getFuelRateIncludingHandlingCharge()
																		* excessFuelTaken);
													}

												}
												mappedMachine
														.setMachineRateAsPerWorkorderDayShift(machineRateInDayTime);
												mappedMachine
														.setMachineRateAsPerWorkorderNightShift(machineRateInNightTime);
												mappedMachine
														.setMachineRateUnitAsPerWorkorder(hmItem.getUnit().getName());
												mappedMachine.setTotalDaysAsPerRateUnit(DateUtil
														.getNoOfDaysInTheCurrentMonth(mappedMachine.getFromDate())
														.doubleValue());

												Double thresholdAmount = 0.0;

												if (hmItem.getThresholdApplicable()) {
													mappedMachine.setIsThresholdApplicable(true);
													mappedMachine.setThresholdQuantity(hmItem.getThresholdQuantity());
													mappedMachine.setThresholdQuantityUnit(
															hmItem.getThresholdUnit().getName());

													if (mappedMachine.getNetTotalPrimaryActualReading() > mappedMachine
															.getThresholdQuantity()) {
														thresholdAmount = (mappedMachine
																.getNetTotalPrimaryActualReading()
																- mappedMachine.getThresholdQuantity())
																* hmItem.getPostThresholdRatePerUnit();
													}

												} else {
													mappedMachine.setIsThresholdApplicable(false);
												}

												Double machineTotalAmountInDay = 0.0;
												Double machineTotalAmountInNight = 0.0;
												Double machineTotalBreakdownHoursAmountInDay = 0.0;
												Double machineTotalBreakdownHoursAmountInNight = 0.0;

//												if (mappedMachine.getIsShiftBased()) {
//
//													if (totalBreakdownHoursInDayMap != null) {
//														for (Map.Entry<Integer, Double> dayObj : totalBreakdownHoursInDayMap
//																.entrySet()) {
//
//															machineTotalBreakdownHoursAmountInDay += (mappedMachine
//																	.getMachineRateAsPerWorkorderDayShift()
//																	/ (dayObj.getKey() * 12)) * dayObj.getValue();
//														}
//													}
//
//													if (totalBreakdownHoursInDayMap != null) {
//														for (Map.Entry<Integer, Double> dayObj : totalBreakdownHoursInNightMap
//																.entrySet()) {
//
//															machineTotalBreakdownHoursAmountInNight += (mappedMachine
//																	.getMachineRateAsPerWorkorderNightShift()
//																	/ (dayObj.getKey() * 12)) * dayObj.getValue();
//														}
//													}
//												}

												if (mappedMachine.getIsShiftBased()) {

													if (mappedMachine.getTotalBreakdownHoursInDayShift() != null) {

														machineTotalBreakdownHoursAmountInDay += (mappedMachine
																.getMachineRateAsPerWorkorderDayShift()
																/ (mappedMachine.getTotalDaysAsPerRateUnit()
																		* hmItem.getShiftSchedule().getShiftHours()))
																* mappedMachine.getTotalBreakdownHoursInDayShift();

													}

													if (mappedMachine.getTotalBreakdownHoursInNightShift() != null) {
														machineTotalBreakdownHoursAmountInNight += (mappedMachine
																.getMachineRateAsPerWorkorderNightShift()
																/ (mappedMachine.getTotalDaysAsPerRateUnit()
																		* hmItem.getShiftSchedule().getShiftHours()))
																* mappedMachine.getTotalBreakdownHoursInNightShift();

													}
												}

												mappedMachine.setTotalBreakdownHoursAmount(
														machineTotalBreakdownHoursAmountInDay
																+ machineTotalBreakdownHoursAmountInNight);

												if (mappedMachine.getIsShiftBased()) {
													machineTotalAmountInDay = (totalPresentDaysInDay
															/ mappedMachine.getTotalDaysAsPerRateUnit())
															* mappedMachine.getMachineRateAsPerWorkorderDayShift();
													machineTotalAmountInDay -= machineTotalBreakdownHoursAmountInDay;

													machineTotalAmountInNight = (totalPresentDaysInNight
															/ mappedMachine.getTotalDaysAsPerRateUnit())
															* mappedMachine.getMachineRateAsPerWorkorderNightShift();
													machineTotalAmountInNight -= machineTotalBreakdownHoursAmountInNight;

												} else {

													machineTotalAmountInDay = totalTripsTaken
															* mappedMachine.getMachineRateAsPerWorkorderDayShift();
												}

												mappedMachine.setTotalAmount(
														machineTotalAmountInDay + machineTotalAmountInNight
																+ thresholdAmount + dieselEscalationPrice);

												if (bmm.getBillId().equals(bill.getId())) {
													totalBillAmount += mappedMachine.getTotalAmount();
													fuelDebitCurrentAmount += mappedMachine.getFuelDebitAmount();
												}
												fuelDebitCumulativeAmount += mappedMachine.getFuelDebitAmount();
												if (billWiseTotalWorkDone.get(bmm.getBillId()) == null) {
													billWiseTotalWorkDone.put(bmm.getBillId(),
															mappedMachine.getTotalAmount());

												} else {
													billWiseTotalWorkDone.put(bmm.getBillId(),
															billWiseTotalWorkDone.get(bmm.getBillId())
																	+ mappedMachine.getTotalAmount());
												}
												String machineIdentity = bmm.getMachineType() + "/"
														+ bmm.getMachineId();
												if (machineWiseTotalWorkDone.get(machineIdentity) == null) {
													machineWiseTotalWorkDone.put(machineIdentity,
															mappedMachine.getTotalAmount());

												} else {
													machineWiseTotalWorkDone.put(machineIdentity,
															machineWiseTotalWorkDone.get(machineIdentity)
																	+ mappedMachine.getTotalAmount());
												}

												if (bill.getId().equals(bmm.getBillId())) {
													if (machineWiseCurrentWorkDone.get(machineIdentity) == null) {
														machineWiseCurrentWorkDone.put(machineIdentity,
																mappedMachine.getTotalAmount());

													} else {
														machineWiseCurrentWorkDone.put(machineIdentity,
																machineWiseCurrentWorkDone.get(machineIdentity)
																		+ mappedMachine.getTotalAmount());
													}

													if (machineWiseIpcDescriptions.get(machineIdentity) == null) {
														machineWiseIpcDescriptions.put(machineIdentity,
																"Hire Charges for " + categoryName + " Reg No-"
																		+ machineRegNo);

													}
												} else {
													if (machineWiseIpcDescriptions.get(machineIdentity) == null) {
														machineWiseIpcDescriptions.put(machineIdentity,
																"Hire Charges for " + categoryName + " Reg No-"
																		+ machineRegNo);
													}
												}

											}
										}
									}
									if (bmm.getBillId().equals(bill.getId())) {
										categoryResponse.getMachines().add(mappedMachine);
									}
								}
							}
						}
					}
				}

				printObj.setMachineCategories(machineCategories);
				printObj.getBill().setBilledAmount(totalBillAmount);

				if (workorderBillInfoDbObj != null) {
					workorderBillInfoMachineMapp = workorderBillInfoMachineMappingDao
							.fetchWorkorderBillInfoMachineMapping(workorderBillInfoDbObj.getId());

					if (workorderBillInfoMachineMapp != null && !workorderBillInfoMachineMapp.isEmpty()) {
						for (WorkorderBillInfoMachineMapping obj : workorderBillInfoMachineMapp) {

							String machineIdentity = obj.getMachineType() + "/" + obj.getMachineId();

							if (machineWiseIpcDescriptions.get(machineIdentity) == null) {
								machineWiseIpcDescriptions.put(machineIdentity,
										"Hire Charges for " + obj.getEquipment().getCategory().getName() + " Reg No-"
												+ obj.getEquipment().getRegNo());
							}

							if (machineWiseTotalWorkDone.get(machineIdentity) == null) {
								machineWiseTotalWorkDone.put(machineIdentity, obj.getAmount());
							} else {
								machineWiseTotalWorkDone.put(machineIdentity,
										machineWiseTotalWorkDone.get(machineIdentity) + obj.getAmount());
							}

						}
					}
				}

//				build response

				int srNoIdx = 0;
				List<BillPrintDescriptionItemResponseDTO> ipcItems = new ArrayList<>();

				String paymentDetailsText = "Payment Details";
				String paymentDetailsSrNo = SetObject.getCharForNumber(++srNoIdx);
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(paymentDetailsSrNo, paymentDetailsText, null, null,
						null, null, true, false));

				Integer paymentSubSrNo = 0;

//				previous bill info 

//				if (workorderBillInfoResponse != null) {
//					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, "Previous bills Amount", null,
//							workorderBillInfoResponse.getPreviousBillAmount(),
//							workorderBillInfoResponse.getPreviousBillAmount(), null, true, false));
//				}

				for (String machineIdentity : machineWiseIpcDescriptions.keySet()) {

					if (machineWiseCurrentWorkDone.containsKey(machineIdentity)) {
						Double uptoDateAmount = machineWiseTotalWorkDone.get(machineIdentity);
						Double currentDateAmount = machineWiseCurrentWorkDone.get(machineIdentity);
						ipcItems.add(new BillPrintDescriptionItemResponseDTO((++paymentSubSrNo).toString(),
								machineWiseIpcDescriptions.get(machineIdentity), null, uptoDateAmount,
								uptoDateAmount - currentDateAmount, currentDateAmount, true, false));
					} else {
						ipcItems.add(new BillPrintDescriptionItemResponseDTO((++paymentSubSrNo).toString(),
								machineWiseIpcDescriptions.get(machineIdentity), null,
								machineWiseTotalWorkDone.get(machineIdentity),
								machineWiseTotalWorkDone.get(machineIdentity), null, true, false));
					}
				}

				Double totalWorkDoneCumulative = 0.0;
				Double totalWorkDoneCurrent = 0.0;
				String totalWorkDoneText = "TOTAL (A)";
				for (Double value : billWiseTotalWorkDone.values()) {
					totalWorkDoneCumulative += value;
				}

				if (billWiseTotalWorkDone.get(bill.getId()) != null)
					totalWorkDoneCurrent = billWiseTotalWorkDone.get(bill.getId());
				Double totalWorkDonePrevious = totalWorkDoneCumulative - totalWorkDoneCurrent;

//				previous bill total amount(A)
				if (workorderBillInfoResponse != null && workorderBillInfoResponse.getPreviousBillAmount() != null) {
					totalWorkDoneCumulative = totalWorkDoneCumulative
							+ workorderBillInfoResponse.getPreviousBillAmount();
					totalWorkDonePrevious = totalWorkDonePrevious + workorderBillInfoResponse.getPreviousBillAmount();
//					totalWorkDoneCurrent = totalWorkDoneCurrent + workorderBillInfoResponse.getPreviousBillAmount();
				}

				printObj.setCumulativeBillAmount(totalWorkDoneCumulative);
				printObj.setCurrentBillAmount(totalWorkDoneCurrent);
				printObj.setPreviousBillAmount(totalWorkDonePrevious);

				ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, totalWorkDoneText, null,
						totalWorkDoneCumulative, totalWorkDonePrevious, totalWorkDoneCurrent, true, true));

				Double applicableGst = bill.getApplicableIgst();
				if (applicableGst == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
							"Bill is incomplete, GST value not provided.");
				}
				Double withGstItemsTotalIpcAmount = totalWorkDoneCurrent + (totalWorkDoneCurrent * applicableGst / 100);

				printObj.setInvoiceAmountBeforeGst(totalWorkDoneCurrent);
				printObj.setInvoiceAmountAfterGst(withGstItemsTotalIpcAmount);

				if (bill.getIsIgstOnly() != null && bill.getIsIgstOnly()) {
					String igstText = "IGST (" + applicableGst + " %)";
					Double gstUptoDateIpcAmount = totalWorkDoneCumulative * (applicableGst / 100);
					Double gstUptoPreviousIpcAmount = totalWorkDonePrevious * (applicableGst / 100);
					Double gstIpcAmount = totalWorkDoneCurrent * (applicableGst / 100);

					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, igstText, null, gstUptoDateIpcAmount,
							gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
				} else {
					String cgstText = "CGST (" + applicableGst / 2 + " %)";
					String sgstText = "SGST (" + applicableGst / 2 + " %)";
					Double gstUptoDateIpcAmount = totalWorkDoneCumulative * (applicableGst / 200);
					Double gstUptoPreviousIpcAmount = totalWorkDonePrevious * (applicableGst / 200);
					Double gstIpcAmount = totalWorkDoneCurrent * (applicableGst / 200);

					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, cgstText, null, gstUptoDateIpcAmount,
							gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, sgstText, null, gstUptoDateIpcAmount,
							gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
				}

				Double includingGstUptoDateIpcAmount = totalWorkDoneCumulative
						+ (totalWorkDoneCumulative * (applicableGst / 100));
				Double includingGstUptoPreviousIpcAmount = totalWorkDonePrevious
						+ (totalWorkDonePrevious * (applicableGst / 100));
				Double includingGstIpcAmount = totalWorkDoneCurrent + (totalWorkDoneCurrent * (applicableGst / 100));
				String totalAmountText = "Total Amount (" + SetObject.getCharForNumber(srNoIdx) + ")";
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, totalAmountText, null,
						includingGstUptoDateIpcAmount, includingGstUptoPreviousIpcAmount, includingGstIpcAmount, true,
						true));

//				deductions in build res

				String deductionText = "Deductions :";
				String deductionSrNo = SetObject.getCharForNumber(++srNoIdx);
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(deductionSrNo, deductionText, null, null, null,
						null, true, false));
				List<BillDeductionMapping> woBillDeductions = deductionMapDao
						.fetchUptoCurrentMappedBillDeductionsByWorkorderId(bill.getWorkorder().getId(),
								bill.getToDate());

				if (workorderBillInfoDbObj != null && workorderBillInfoDbObj.getId() != null) {
					List<BillDeductionMapping> previousBillsDeductionList = deductionMapDao
							.fetchUptoCurrentMappedBillDeductionsByWorkorderBillInfoId(workorderBillInfoDbObj.getId());

					woBillDeductions.addAll(previousBillsDeductionList);
				}

				Integer deductionCount = 0;
				Set<String> distinctDeductionIdTypeValuePairs = new LinkedHashSet<>();
				Double deductionUptoDateIpcAmount = 0.0;
				Double deductionIpcAmount = 0.0;
				Double deductionUptoPreviousIpcAmount = 0.0;

//				fuel debit deductions

				deductionUptoDateIpcAmount += fuelDebitCumulativeAmount;
				deductionIpcAmount += fuelDebitCurrentAmount;
				deductionUptoPreviousIpcAmount = deductionUptoDateIpcAmount - deductionIpcAmount;

				String deductionsFueDebitText = "Diesel Debit Amount";
				ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount), deductionsFueDebitText,
						0d, deductionUptoDateIpcAmount, deductionUptoPreviousIpcAmount, deductionIpcAmount, false,
						false));

				if (woBillDeductions != null) {

					for (BillDeductionMapping deductionMap : woBillDeductions) {
						distinctDeductionIdTypeValuePairs.add(deductionMap.getDeduction().getId() + "-"
								+ (deductionMap.getIsPercentage() ? "1" : "0") + "-"
								+ (deductionMap.getIsPercentage()
										? (deductionMap.getValue().toString().length() > 5
												? deductionMap.getValue().toString().substring(0, 4)
												: deductionMap.getValue().toString())
										: 0));
					}

					for (String deductionPair : distinctDeductionIdTypeValuePairs) {

						String[] idTypeValue = deductionPair.split("-");
						Integer distinctDeductionId = Integer.valueOf(idTypeValue[0]);
						boolean distinctDeductionTypePercentage = (Integer.valueOf(idTypeValue[1]) == 1) ? true : false;
						Double distinctDeductionValue = Double.valueOf(idTypeValue[2]);

						Double uptoDateDeductionIpcAmount = 0.0;
						Double uptoPreviousDeductionIpcAmount = 0.0;
						Double currentDeductionIpcAmount = 0.0;
						Double deductionValue = null;
						String deductionName = null;
						for (BillDeductionMapping deductionMap : woBillDeductions) {

//							previous bill duplicate deduction 
//							if (workorderBillInfoResponse != null
//									&& workorderBillInfoResponse.getBillDeductionResponse() != null) {
//								for (BillDeductionResponseDto response : workorderBillInfoResponse
//										.getBillDeductionResponse()) {
//									if (response.getDeductionId().equals(deductionMap.getDeduction().getId())
//											&& response.getIsPercentage().equals(deductionMap.getIsPercentage())
//											&& response.getValue().equals(deductionMap.getValue())) {
//										temp.remove(response);
//									}
//								}
//							}

							if (distinctDeductionId.equals(deductionMap.getDeduction().getId())
									&& deductionMap.getIsPercentage().equals(distinctDeductionTypePercentage)
									&& !distinctDeductionTypePercentage
									&& ((deductionMap.getBill() != null
											&& bill.getWorkorder().getId()
													.equals(deductionMap.getBill().getWorkorder().getId())
											|| (deductionMap.getWorkorderBillInfoId() != null)))) {

								if (deductionName == null) {
									deductionName = deductionMap.getDeduction().getName();
								}
								if (deductionMap.getBillId() != null) {
									if (deductionMap.getBillId().equals(bill.getId())) {
										currentDeductionIpcAmount += (deductionMap.getValue());
										deductionValue = deductionMap.getValue();
									} else {
										uptoPreviousDeductionIpcAmount += (deductionMap.getValue());
									}
								} else if (deductionMap.getWorkorderBillInfoId() != null) {
									uptoPreviousDeductionIpcAmount += (deductionMap.getValue());

								}

							} else if (distinctDeductionId.equals(deductionMap.getDeduction().getId())
									&& deductionMap.getIsPercentage().equals(distinctDeductionTypePercentage)
									&& distinctDeductionValue.equals(deductionMap.getValue())
									&& distinctDeductionTypePercentage
									&& (deductionMap.getBill() != null
											&& bill.getWorkorder().getId()
													.equals(deductionMap.getBill().getWorkorder().getId())
											|| (deductionMap.getWorkorderBillInfoId() != null))) {

								if (deductionName == null) {
									deductionName = deductionMap.getDeduction().getName();
								}
								deductionValue = deductionMap.getValue();

								if (deductionMap.getBillId() != null) {
									if (deductionMap.getBillId().equals(bill.getId())) {
										Double deductionBillWorkdone = 0.00;
										if (billWiseTotalWorkDone.get(deductionMap.getBillId()) != null)
											deductionBillWorkdone = (Double) billWiseTotalWorkDone
													.get(deductionMap.getBillId());
										currentDeductionIpcAmount = (double) Math
												.round((deductionValue / 100) * deductionBillWorkdone * 100) / 100;
									} else {
										Double deductionBillWorkdone = 0.00;
										if (billWiseTotalWorkDone.get(deductionMap.getBillId()) != null)
											deductionBillWorkdone = (Double) billWiseTotalWorkDone
													.get(deductionMap.getBillId());
										uptoPreviousDeductionIpcAmount += (double) Math
												.round((deductionValue / 100) * deductionBillWorkdone * 100) / 100;
									}
								} else if (deductionMap.getWorkorderBillInfoId() != null) {
									uptoPreviousDeductionIpcAmount += (double) Math.round((deductionValue / 100)
											* workorderBillInfoDbObj.getPreviousBillAmount() * 100) / 100;

								}
							}
						}
						if (distinctDeductionTypePercentage) {
							deductionName += (" @ " + distinctDeductionValue + "%");
						}
						uptoDateDeductionIpcAmount = uptoPreviousDeductionIpcAmount + currentDeductionIpcAmount;
						deductionIpcAmount += currentDeductionIpcAmount;
						deductionUptoDateIpcAmount += uptoDateDeductionIpcAmount;
						deductionUptoPreviousIpcAmount += uptoPreviousDeductionIpcAmount;
						ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount), deductionName,
								deductionValue, uptoDateDeductionIpcAmount, uptoPreviousDeductionIpcAmount,
								currentDeductionIpcAmount, false, false));

					}
				}

//				if (temp != null) {
//					for (BillDeductionResponseDto obj : temp) {
//						String description = null;
//						if (obj.getIsPercentage()) {
//							description = (obj.getDeductionName() + " @ " + obj.getValue() + "%");
//							ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount), description,
//									obj.getValue(), totalWorkDoneCumulative * (obj.getValue() / 100),
//									totalWorkDonePrevious * (obj.getValue() / 100), null, false, false));
////							deductionIpcAmount += totalWorkDoneCurrent * (obj.getValue() / 100);
//							deductionUptoDateIpcAmount += totalWorkDoneCumulative * (obj.getValue() / 100);
//							deductionUptoPreviousIpcAmount += totalWorkDonePrevious * (obj.getValue() / 100);
//						} else {
//							ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount),
//									obj.getDeductionName(), obj.getValue(), obj.getValue(), obj.getValue(), null, false,
//									false));
////							deductionIpcAmount += obj.getValue();
//							deductionUptoDateIpcAmount += obj.getValue();
//							deductionUptoPreviousIpcAmount += obj.getValue();
//						}
//
//					}
//				}

				String deductionsTotalText = "TOTAL DEDUCTION (" + deductionSrNo + ")";
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, deductionsTotalText, null,
						deductionUptoDateIpcAmount, deductionUptoPreviousIpcAmount, deductionIpcAmount, true, false));

				String netPayText = "PAYABLE AMOUNT (" + paymentDetailsSrNo + " - " + deductionSrNo + ")";
				Double netUptoDateIpcAmount = includingGstUptoDateIpcAmount - deductionUptoDateIpcAmount;
				Double netUptoPreviousIpcAmount = includingGstUptoPreviousIpcAmount - deductionUptoPreviousIpcAmount;
				Double netIpcAmount = includingGstIpcAmount - deductionIpcAmount;
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, netPayText, null, netUptoDateIpcAmount,
						netUptoPreviousIpcAmount, netIpcAmount, true, false));
				printObj.setIpcItems(ipcItems);

				Integer invoiceItemsCount = 0;
				List<BillPrintTaxInvoiceItemResponseDTO> invoiceItems = new ArrayList<>();

				if (invoiceItems.size() > 1) {
					invoiceItems.add(new BillPrintTaxInvoiceItemResponseDTO((++invoiceItemsCount), "TOTAL",
							withGstItemsTotalIpcAmount));
				}
				printObj.setInvoiceItems(invoiceItems);

			} else if (workorderTypeId.equals(WorkorderTypes.Consultancy.getId())) {

				Double totalBillAmount = 0.0;

				Set<Long> billIds = new HashSet<>();
				for (Bill billTraverse : allBills) {
					billIds.add(billTraverse.getId());
				}

				Map<Long, Double> billWiseTotalWorkDone = new HashMap<>();

				Map<String, Double> machineWiseTotalWorkDone = new HashMap<>();

				Map<String, Double> machineWiseCurrentWorkDone = new HashMap<>();

				Map<String, String> machineWiseIpcDescriptions = new HashMap<>();

				printObj.getBill().setBilledAmount(totalBillAmount);

//				build response

				int srNoIdx = 0;
				List<BillPrintDescriptionItemResponseDTO> ipcItems = new ArrayList<>();

				String paymentDetailsText = "Payment Details";
				String paymentDetailsSrNo = SetObject.getCharForNumber(++srNoIdx);
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(paymentDetailsSrNo, paymentDetailsText, null, null,
						null, null, true, false));

				Integer paymentSubSrNo = 0;
				for (String machineIdentity : machineWiseIpcDescriptions.keySet()) {

					Double uptoDateAmount = machineWiseTotalWorkDone.get(machineIdentity);
					Double currentDateAmount = machineWiseCurrentWorkDone.get(machineIdentity);
					ipcItems.add(new BillPrintDescriptionItemResponseDTO((++paymentSubSrNo).toString(),
							paymentDetailsText, null, uptoDateAmount, uptoDateAmount - currentDateAmount,
							currentDateAmount, true, false));
				}

				Double totalWorkDoneCumulative = 0.0;
				Double totalWorkDoneCurrent = 0.0;
				String totalWorkDoneText = "TOTAL (A)";
				for (Double value : billWiseTotalWorkDone.values()) {
					totalWorkDoneCumulative += value;
				}

				if (billWiseTotalWorkDone.get(bill.getId()) != null)
					totalWorkDoneCurrent = billWiseTotalWorkDone.get(bill.getId());
				Double totalWorkDonePrevious = totalWorkDoneCumulative - totalWorkDoneCurrent;

				printObj.setCumulativeBillAmount(totalWorkDoneCumulative);
				printObj.setCurrentBillAmount(totalWorkDoneCurrent);
				printObj.setPreviousBillAmount(totalWorkDonePrevious);

				ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, totalWorkDoneText, null,
						totalWorkDoneCumulative, totalWorkDonePrevious, totalWorkDoneCurrent, true, true));

				Double applicableGst = bill.getApplicableIgst();
				if (applicableGst == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
							"Bill is incomplete, GST value not provided.");
				}
				Double withGstItemsTotalIpcAmount = totalWorkDoneCurrent + (totalWorkDoneCurrent * applicableGst / 100);

				printObj.setInvoiceAmountBeforeGst(totalWorkDoneCurrent);
				printObj.setInvoiceAmountAfterGst(withGstItemsTotalIpcAmount);

				if (bill.getIsIgstOnly() != null && bill.getIsIgstOnly()) {
					String igstText = "IGST (" + applicableGst + " %)";
					Double gstUptoDateIpcAmount = totalWorkDoneCumulative * (applicableGst / 100);
					Double gstUptoPreviousIpcAmount = totalWorkDonePrevious * (applicableGst / 100);
					Double gstIpcAmount = totalWorkDoneCurrent * (applicableGst / 100);
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, igstText, null, gstUptoDateIpcAmount,
							gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
				} else {
					String cgstText = "CGST (" + applicableGst / 2 + " %)";
					String sgstText = "SGST (" + applicableGst / 2 + " %)";
					Double gstUptoDateIpcAmount = totalWorkDoneCumulative * (applicableGst / 200);
					Double gstUptoPreviousIpcAmount = totalWorkDonePrevious * (applicableGst / 200);
					Double gstIpcAmount = totalWorkDoneCurrent * (applicableGst / 200);
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, cgstText, null, gstUptoDateIpcAmount,
							gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
					ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, sgstText, null, gstUptoDateIpcAmount,
							gstUptoPreviousIpcAmount, gstIpcAmount, false, true));
				}

				Double includingGstUptoDateIpcAmount = totalWorkDoneCumulative
						+ (totalWorkDoneCumulative * (applicableGst / 100));
				Double includingGstUptoPreviousIpcAmount = totalWorkDonePrevious
						+ (totalWorkDonePrevious * (applicableGst / 100));
				Double includingGstIpcAmount = totalWorkDoneCurrent + (totalWorkDoneCurrent * (applicableGst / 100));
				String totalAmountText = "Total Amount (" + SetObject.getCharForNumber(srNoIdx) + ")";
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, totalAmountText, null,
						includingGstUptoDateIpcAmount, includingGstUptoPreviousIpcAmount, includingGstIpcAmount, true,
						true));

//				deductions in build res

				String deductionText = "Deductions :";
				String deductionSrNo = SetObject.getCharForNumber(++srNoIdx);
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(deductionSrNo, deductionText, null, null, null,
						null, true, false));
				List<BillDeductionMapping> woBillDeductions = deductionMapDao
						.fetchUptoCurrentMappedBillDeductionsByWorkorderId(bill.getWorkorder().getId(),
								bill.getToDate());
				Integer deductionCount = 0;
				Set<String> distinctDeductionIdTypeValuePairs = new LinkedHashSet<>();
				Double deductionUptoDateIpcAmount = 0.0;
				Double deductionIpcAmount = 0.0;
				Double deductionUptoPreviousIpcAmount = 0.0;

				if (woBillDeductions != null) {

					for (BillDeductionMapping deductionMap : woBillDeductions) {
						distinctDeductionIdTypeValuePairs.add(deductionMap.getDeduction().getId() + "-"
								+ (deductionMap.getIsPercentage() ? "1" : "0") + "-"
								+ (deductionMap.getIsPercentage()
										? (deductionMap.getValue().toString().length() > 5
												? deductionMap.getValue().toString().substring(0, 4)
												: deductionMap.getValue().toString())
										: 0));
					}

					for (String deductionPair : distinctDeductionIdTypeValuePairs) {

						String[] idTypeValue = deductionPair.split("-");
						Integer distinctDeductionId = Integer.valueOf(idTypeValue[0]);
						boolean distinctDeductionTypePercentage = (Integer.valueOf(idTypeValue[1]) == 1) ? true : false;
						Double distinctDeductionValue = Double.valueOf(idTypeValue[2]);

						Double uptoDateDeductionIpcAmount = 0.0;
						Double uptoPreviousDeductionIpcAmount = 0.0;
						Double currentDeductionIpcAmount = 0.0;
						Double deductionValue = null;
						String deductionName = null;

						for (BillDeductionMapping deductionMap : woBillDeductions) {

							if (distinctDeductionId.equals(deductionMap.getDeduction().getId())
									&& deductionMap.getIsPercentage().equals(distinctDeductionTypePercentage)
									&& !distinctDeductionTypePercentage && bill.getWorkorder().getId()
											.equals(deductionMap.getBill().getWorkorder().getId())) {

								if (deductionName == null) {
									deductionName = deductionMap.getDeduction().getName();
								}
								if (deductionMap.getBillId().equals(bill.getId())) {
									currentDeductionIpcAmount += (deductionMap.getValue());
									deductionValue = deductionMap.getValue();
								} else {
									uptoPreviousDeductionIpcAmount += (deductionMap.getValue());
								}

							} else if (distinctDeductionId.equals(deductionMap.getDeduction().getId())
									&& deductionMap.getIsPercentage().equals(distinctDeductionTypePercentage)
									&& distinctDeductionValue.equals(deductionMap.getValue())
									&& distinctDeductionTypePercentage && bill.getWorkorder().getId()
											.equals(deductionMap.getBill().getWorkorder().getId())) {

								if (deductionName == null) {
									deductionName = deductionMap.getDeduction().getName();
								}
								deductionValue = deductionMap.getValue();

								if (deductionMap.getBillId().equals(bill.getId())) {
									Double deductionBillWorkdone = 0.00;
									if (billWiseTotalWorkDone.get(deductionMap.getBillId()) != null)
										deductionBillWorkdone = (Double) billWiseTotalWorkDone
												.get(deductionMap.getBillId());
									currentDeductionIpcAmount = (double) Math
											.round((deductionValue / 100) * deductionBillWorkdone * 100) / 100;
								} else {
									Double deductionBillWorkdone = 0.00;
									if (billWiseTotalWorkDone.get(deductionMap.getBillId()) != null)
										deductionBillWorkdone = (Double) billWiseTotalWorkDone
												.get(deductionMap.getBillId());
									uptoPreviousDeductionIpcAmount += (double) Math
											.round((deductionValue / 100) * deductionBillWorkdone * 100) / 100;
								}
							}
						}
						if (distinctDeductionTypePercentage) {
							deductionName += (" @ " + distinctDeductionValue + "%");
						}
						uptoDateDeductionIpcAmount = uptoPreviousDeductionIpcAmount + currentDeductionIpcAmount;
						deductionIpcAmount += currentDeductionIpcAmount;
						deductionUptoDateIpcAmount += uptoDateDeductionIpcAmount;
						deductionUptoPreviousIpcAmount += uptoPreviousDeductionIpcAmount;
						ipcItems.add(new BillPrintDescriptionItemResponseDTO("" + (++deductionCount), deductionName,
								deductionValue, uptoDateDeductionIpcAmount, uptoPreviousDeductionIpcAmount,
								currentDeductionIpcAmount, false, false));

					}
				}

				String deductionsTotalText = "TOTAL DEDUCTION (" + deductionSrNo + ")";
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, deductionsTotalText, null,
						deductionUptoDateIpcAmount, deductionUptoPreviousIpcAmount, deductionIpcAmount, true, false));

				String netPayText = "PAYABLE AMOUNT (" + paymentDetailsSrNo + " - " + deductionSrNo + ")";
				Double netUptoDateIpcAmount = includingGstUptoDateIpcAmount - deductionUptoDateIpcAmount;
				Double netUptoPreviousIpcAmount = includingGstUptoPreviousIpcAmount - deductionUptoPreviousIpcAmount;
				Double netIpcAmount = includingGstIpcAmount - deductionIpcAmount;
				ipcItems.add(new BillPrintDescriptionItemResponseDTO(null, netPayText, null, netUptoDateIpcAmount,
						netUptoPreviousIpcAmount, netIpcAmount, true, false));
				printObj.setIpcItems(ipcItems);

				Integer invoiceItemsCount = 0;
				List<BillPrintTaxInvoiceItemResponseDTO> invoiceItems = new ArrayList<>();

				if (invoiceItems.size() > 1) {
					invoiceItems.add(new BillPrintTaxInvoiceItemResponseDTO((++invoiceItemsCount), "TOTAL",
							withGstItemsTotalIpcAmount));
				}
				printObj.setInvoiceItems(invoiceItems);

			} else {
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null,
						"Print functionality will be available soon.");
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), printObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBillPriceEscalationData(SearchDTO search) {

		try {
			Bill bill = billDao.fetchBillById(search.getBillId());
			List<BillBoqQuantityItem> woQtyItems = billItemDao
					.fetchBillBoqQuantityItemsByWorkorderId(bill.getWorkorder().getId());
			search.setWorkorderId(bill.getWorkorder().getId());
			List<Bill> allBills = billDao.fetchBills(search);
			Double valueFo = SetObject.temporaryFoValue;
			Double totalRe = 0.0;
			Double totalVe = 0.0;
			List<PriceEscalationItemsResponseDTO> peItems = new ArrayList<PriceEscalationItemsResponseDTO>();
			Date dieselFromDate = allBills.get(0).getFromDate();
			Date dieselToDate = allBills.get(allBills.size() - 1).getToDate();
			search.setFromDate(dieselFromDate);
			search.setToDate(dieselToDate);
			List<DieselRateMapping> dieselRates = dieselRateDao.fetchDieselRates(search);
			if (allBills != null) {
				for (Bill billTraverse : allBills) {
					Double currentBillTraverseAmount = 0.0;
					for (BillBoqQuantityItem woQtyItem : woQtyItems) {
						if (woQtyItem.getBillBoqItem().getBill().getId().equals(billTraverse.getId())) {
							currentBillTraverseAmount += (woQtyItem.getQuantity() * woQtyItem.getRate());
						}
					}
					Double weightedDieselRate = null;
					Integer dieselRateCount = 0;
					if (dieselRates != null) {
						for (DieselRateMapping dieselRate : dieselRates) {
							Date rateDate = DateUtil.dateWithoutTime(dieselRate.getDate());
							Date billFromDate = DateUtil.dateWithoutTime(billTraverse.getFromDate());
							Date billToDate = DateUtil.dateWithoutTime(billTraverse.getToDate());
							if (rateDate.equals(billFromDate) || rateDate.equals(billToDate)
									|| (rateDate.after(billFromDate) && rateDate.before(billToDate))) {
								if (weightedDieselRate == null) {
									dieselRateCount++;
									weightedDieselRate = dieselRate.getRate();
								} else {
									dieselRateCount++;
									weightedDieselRate = (weightedDieselRate + dieselRate.getRate());
								}
							}
						}
					}

					if (weightedDieselRate == null) {
						return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
								"Diesel rates not present for bills.");

					}
					if (weightedDieselRate > 0.0) {
						weightedDieselRate = weightedDieselRate / dieselRateCount;
					}
					String duration = null;
					if (DateUtil.fromSameMonthAndYear(billTraverse.getFromDate(), billTraverse.getToDate())) {
						duration = DateUtil.getDayMonthYear(billTraverse.getFromDate(), false);
					} else {
						duration = DateUtil.getDayMonthYear(billTraverse.getFromDate(), true) + " - "
								+ DateUtil.getDayMonthYear(billTraverse.getToDate(), true);
					}
					Double valueVe = 0.4 * currentBillTraverseAmount * ((weightedDieselRate - valueFo) / valueFo);
					totalRe += currentBillTraverseAmount;
					totalVe += valueVe;
					peItems.add(new PriceEscalationItemsResponseDTO(duration, currentBillTraverseAmount,
							weightedDieselRate, valueVe));
				}

			}
			PriceEscalationResponseDTO priceEscalation = new PriceEscalationResponseDTO(valueFo, peItems, totalRe,
					totalVe);
			return new CustomResponse(Responses.SUCCESS.getCode(), priceEscalation, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateBillState(SearchDTO search) {

		try {
			if (search.getBillId() == null || search.getStateId() == null || search.getUserId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			Bill bill = billDao.fetchBillById(search.getBillId());
			if (bill.getState().getId().equals(search.getStateId())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Bill is already in the same state.");
			}
			Integer stateId = bill.getState().getId();
			User lastModifiedBy = userDao.fetchUserById(bill.getModifiedBy());

			List<StateTransitionDTO> stateTransits = engineService.getStateTransition(EntitiesEnum.BILL.getEntityId(),
					search.getSiteId(), stateId, lastModifiedBy.getRole().getId(), search.getCompanyId());
			StateTransitionDTO stateTransit = null;
			if (stateTransits != null) {
				for (StateTransitionDTO st : stateTransits) {
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
//			TODO 
//			maintain versions
//			Integer currentDocVersion = bill.getVersion();
//			EntityStateMapDTO entityStateMap = engineService.getEntityStateMap(
//					EntitiesEnum.BILL.getEntityId(), search.getStateId(), search.getCompanyId());
//
//			Boolean isVersionableState = false;
//			if (entityStateMap != null && entityStateMap.getMaintainVersion()) {
//				isVersionableState = true;
//			}
//			if (isVersionableState) {
//				
//			}

			bill.setState(new EngineState(search.getStateId()));
			bill.setModifiedOn(new Date());
			bill.setModifiedBy(search.getUserId());
			Boolean result = billDao.forceUpdateBill(bill);
			if (result != null && result) {
				BillStateTransitionMapping billStateTransitionMap = new BillStateTransitionMapping(null, bill.getId(),
						search.getStateId(), true, new Date(), search.getUserId());
				billDao.mapBillStateTransition(billStateTransitionMap);
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
	public CustomResponse getNextPossibleStates(SearchDTO search) {

		try {
			if (search.getBillId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide billId.");
			}
			Bill bill = billDao.fetchBillById(search.getBillId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(EntitiesEnum.BILL.getEntityId(),
					search.getSiteId(), null, null, search.getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User billUser = userDao.fetchUserById(bill.getModifiedBy());

//			set next states
			if (transitions != null && billUser != null && billUser.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
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

	public Double roundOffUptoTwoDecimal(Double value) {
		return (double) Math.round(value * 100) / 100;
	}

}
