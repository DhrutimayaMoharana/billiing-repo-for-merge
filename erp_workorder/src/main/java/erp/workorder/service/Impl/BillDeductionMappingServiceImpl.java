package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.BillDao;
import erp.workorder.dao.BillDeductionDao;
import erp.workorder.dao.BillDeductionMappingDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.request.BillDeductionMappingAddUpdateRequest;
import erp.workorder.dto.request.BillDeductionMappingDTO;
import erp.workorder.entity.BillDeduction;
import erp.workorder.entity.BillDeductionMapTransac;
import erp.workorder.entity.BillDeductionMapping;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.service.WorkflowEngineService;
import erp.workorder.service.BillDeductionMappingService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class BillDeductionMappingServiceImpl implements BillDeductionMappingService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private BillDeductionMappingDao deductionMapDao;

	@Autowired
	private BillDeductionDao deductionDao;

	@Autowired
	private BillDao billDao;

//	@Autowired
//	private BillService billService;
//
//	@Autowired
//	private DebitNoteDao debitNoteDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Override
	public CustomResponse getDeductions(SearchDTO search) {

		try {
//			CustomResponse billResponse = billService.getBillById(search);
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			BillAndItemsDTO bill = null;
//			if (billResponse != null && billResponse.getStatus().equals(Responses.SUCCESS.getCode()))
//				bill = mapper.convertValue(billResponse.getData(), BillAndItemsDTO.class);
//			if (bill == null)
//				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Bill does not exist.");
//
//			Double billedAmountWithoutGst = bill.getBilledAmount();
//			Double billedAmount = bill.getBilledAmountAfterGst();
//			Double deductionAmount = 0.0;
//			Boolean fuelDebitAmountIncluded = false;
//			Double fuelDebitAmount = 0.0;
//
//			List<BillDeductionMapping> deductions = deductionMapDao.fetchMappedBillDeductions(search);
//			List<BillDeductionMappingDTO> deductionsDTO = new ArrayList<>();
//			if (bill.getWorkorder().getType().getId().intValue() == WorkorderTypes.Machine_Hiring.getId()) {
//				fuelDebitAmount = bill.getFuelDebitAmount() != null ? bill.getFuelDebitAmount() : 0d;
//				fuelDebitAmountIncluded = true;
//				deductionAmount += fuelDebitAmount;
//			}
//
//			if (deductions != null) {
//				for (BillDeductionMapping deduction : deductions) {
//					BillDeductionMappingDTO obj = setObject.billDeductionMappingEntityToDto(deduction);
//					if (obj.getIsPercentage()) {
//						obj.setAmount((obj.getValue() / 100) * billedAmountWithoutGst);
//					} else {
//						obj.setAmount(obj.getValue());
//					}
//					deductionAmount += obj.getAmount();
//					deductionsDTO.add(obj);
//				}
//			}
//			BillDeductionsResponseDTO result = new BillDeductionsResponseDTO(billedAmount, deductionAmount,
//					billedAmount - deductionAmount, deductionsDTO);
//
//			search.setSiteId(bill.getSiteId());
//			search.setContractorId(bill.getWorkorder().getContractor().getId());
//			search.setFromDate(bill.getFromDate());
//			search.setToDate(bill.getToDate());
//			search.setWorkorderId(bill.getWorkorder().getId());
//
//			search.setFromDate(null);
//			List<Bill> allBills = billDao.fetchBills(search);
//			Double totalHandlingChargeCumulative = 0.0;
//			Double totalGstAmountCumulative = 0.0;
//			Double totalAmountAfterHandlingChargeCumulative = 0.0;
//			Double currentBillAmount = 0.0;
//			List<DebitNoteItemDTO> uptoCurrentNotes = new ArrayList<>();
//			if (allBills != null) {
//				for (Bill billTraverse : allBills) {
//					if (billTraverse.getToDate().after(bill.getToDate())) {
//						continue;
//					}
//					search.setContractorId(billTraverse.getWorkorder().getContractor().getId());
//					search.setToDate(billTraverse.getToDate());
//					search.setWorkorderId(billTraverse.getWorkorder().getId());
//					List<DebitNoteItem> noteItems = debitNoteDao.fetchDebitNoteItems(search);
//					if (noteItems != null) {
//						for (DebitNoteItem noteItem : noteItems) {
//							DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
//							totalHandlingChargeCumulative += noteItemDTO.getHandlingCharge();
//							totalAmountAfterHandlingChargeCumulative += noteItemDTO.getFinalAmount();
//							totalGstAmountCumulative += noteItemDTO.getGstAmount();
//							if (billTraverse.getId().equals(bill.getId())) {
//								currentBillAmount += noteItemDTO.getFinalAmount();
//							}
//							uptoCurrentNotes.add(noteItemDTO);
//						}
//					}
//				}
//			}
//			result.setFuelDebitAmountIncluded(fuelDebitAmountIncluded);
//			result.setFuelDebitAmount(fuelDebitAmount);
//			result.setCurrentDebitNoteAmount(currentBillAmount);
//			result.setUptoCurrentDebitNoteAmount(totalAmountAfterHandlingChargeCumulative);

			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addOrUpdateBillDeductionMap(BillDeductionMappingAddUpdateRequest requestDTO) {

		try {

			SearchDTO search = new SearchDTO();
			search.setWorkorderBillInfoId(requestDTO.getWorkorderBillInfoId());

			Map<Long, BillDeductionMappingDTO> billDeductionReqMap = requestDTO.getBillDeductionMapRequest().stream()
					.filter(o -> o.getId() != null).collect(Collectors.toMap(o -> o.getId(), o -> o));

			List<BillDeductionMapping> billDeductionMapDbObjList = deductionMapDao.fetchMappedBillDeductions(search);

			Map<Long, BillDeductionMapping> billDeductionDbObjMap = billDeductionMapDbObjList.stream()
					.collect(Collectors.toMap(o -> o.getId(), o -> o));

			List<BillDeductionMapping> listToSave = new ArrayList<>();
			List<BillDeductionMapping> listToUpdate = new ArrayList<>();

			for (BillDeductionMappingDTO reqObj : requestDTO.getBillDeductionMapRequest()) {
				BillDeduction billDeduction = setObject.billDeductionMappingDTOToEntity(reqObj,
						requestDTO.getUserDetail());
				Integer billDeductionId = deductionDao.saveBillDeduction(billDeduction);

				if (reqObj.getId() == null) {
					listToSave.add(new BillDeductionMapping(null, null, requestDTO.getWorkorderBillInfoId(),
							billDeductionId, reqObj.getIsPercentage(), reqObj.getValue(), true, new Date(),
							requestDTO.getUserDetail().getId(), new Date(), requestDTO.getUserDetail().getId()));
				} else {
					BillDeductionMapTransac billDeductionMapTransac = setObject
							.billDeductionMappingEntityToBillDeductionMapTransacEntity(
									billDeductionDbObjMap.get(reqObj.getId()), requestDTO.getUserDetail());
					deductionMapDao.saveBillDeductionMapTransac(billDeductionMapTransac);

					listToUpdate.add(new BillDeductionMapping(reqObj.getId(), null, requestDTO.getWorkorderBillInfoId(),
							billDeductionId, reqObj.getIsPercentage(), reqObj.getValue(), true, new Date(),
							requestDTO.getUserDetail().getId(), new Date(), requestDTO.getUserDetail().getId()));
				}
			}

			for (BillDeductionMapping dbObj : billDeductionMapDbObjList) {
				if (!billDeductionReqMap.containsKey(dbObj.getId())) {
					dbObj.setIsActive(false);
					dbObj.setModifiedBy(requestDTO.getUserDetail().getId());
					dbObj.setModifiedOn(new Date());
					listToUpdate.add(dbObj);
				}
			}

			for (BillDeductionMapping obj : listToSave) {
				deductionMapDao.saveBillDeductionMapping(obj);
			}

			for (BillDeductionMapping obj : listToUpdate) {
				deductionMapDao.updateBillDeductionMapping(obj);
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
