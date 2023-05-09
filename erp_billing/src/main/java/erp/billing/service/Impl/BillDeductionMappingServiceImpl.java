package erp.billing.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import erp.billing.dao.BillDao;
import erp.billing.dao.BillDeductionDao;
import erp.billing.dao.BillDeductionMappingDao;
import erp.billing.dao.DebitNoteDao;
import erp.billing.dto.BillDeductionMappingDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.DebitNoteItemDTO;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.response.BillAndItemsDTO;
import erp.billing.dto.response.BillDeductionsResponseDTO;
import erp.billing.entity.Bill;
import erp.billing.entity.BillDeduction;
import erp.billing.entity.BillDeductionMapTransac;
import erp.billing.entity.BillDeductionMapping;
import erp.billing.entity.DebitNoteItem;
import erp.billing.enums.EntitiesEnum;
import erp.billing.enums.Responses;
import erp.billing.enums.WorkorderTypes;
import erp.billing.feignClient.service.WorkflowEngineService;
import erp.billing.service.BillDeductionMappingService;
import erp.billing.service.BillService;
import erp.billing.util.CustomValidationUtil;
import erp.billing.util.SetObject;

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

	@Autowired
	private BillService billService;

	@Autowired
	private DebitNoteDao debitNoteDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Override
	public CustomResponse getDeductions(SearchDTO search) {

		try {
			CustomResponse billResponse = billService.getBillById(search);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			BillAndItemsDTO bill = null;
			if (billResponse != null && billResponse.getStatus().equals(Responses.SUCCESS.getCode()))
				bill = mapper.convertValue(billResponse.getData(), BillAndItemsDTO.class);
			if (bill == null)
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Bill does not exist.");

			Double billedAmountWithoutGst = bill.getBilledAmount();
			Double billedAmount = bill.getBilledAmountAfterGst();
			Double deductionAmount = 0.0;
			Boolean fuelDebitAmountIncluded = false;
			Double fuelDebitAmount = 0.0;

			List<BillDeductionMapping> deductions = deductionMapDao.fetchMappedBillDeductions(search);
			List<BillDeductionMappingDTO> deductionsDTO = new ArrayList<>();
			if (bill.getWorkorder().getType().getId().intValue() == WorkorderTypes.Machine_Hiring.getId()) {
				fuelDebitAmount = bill.getFuelDebitAmount() != null ? bill.getFuelDebitAmount() : 0d;
				fuelDebitAmountIncluded = true;
				deductionAmount += fuelDebitAmount;
			}

			if (deductions != null) {
				for (BillDeductionMapping deduction : deductions) {
					BillDeductionMappingDTO obj = setObject.billDeductionMappingEntityToDto(deduction);
					Double amt = 0.0;
					if (obj.getIsPercentage()) {
						amt = (obj.getValue() / 100) * billedAmountWithoutGst;
						obj.setAmount((double) Math.round(amt));
					} else {
						amt = obj.getValue();
						obj.setAmount((double) Math.round(amt));
					}
					deductionAmount += amt;
					deductionsDTO.add(obj);
				}
			}
			BillDeductionsResponseDTO result = new BillDeductionsResponseDTO((double) Math.round(billedAmount),
					(double) Math.round(deductionAmount), (double) Math.round((billedAmount - deductionAmount)),
					deductionsDTO);

			search.setSiteId(bill.getSiteId());
			search.setContractorId(bill.getWorkorder().getContractor().getId());
			search.setFromDate(bill.getFromDate());
			search.setToDate(bill.getToDate());
			search.setWorkorderId(bill.getWorkorder().getId());

			search.setFromDate(null);
			List<Bill> allBills = billDao.fetchBills(search);
			Double totalHandlingChargeCumulative = 0.0;
			Double totalGstAmountCumulative = 0.0;
			Double totalAmountAfterHandlingChargeCumulative = 0.0;
			Double currentBillAmount = 0.0;
			List<DebitNoteItemDTO> uptoCurrentNotes = new ArrayList<>();
			if (allBills != null) {
				for (Bill billTraverse : allBills) {
					if (billTraverse.getToDate().after(bill.getToDate())) {
						continue;
					}
					search.setContractorId(billTraverse.getWorkorder().getContractor().getId());
					search.setToDate(billTraverse.getToDate());
					search.setWorkorderId(billTraverse.getWorkorder().getId());
					List<DebitNoteItem> noteItems = debitNoteDao.fetchDebitNoteItems(search);
					if (noteItems != null) {
						for (DebitNoteItem noteItem : noteItems) {
							DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
							totalHandlingChargeCumulative += noteItemDTO.getHandlingCharge();
							totalAmountAfterHandlingChargeCumulative += noteItemDTO.getFinalAmount();
							totalGstAmountCumulative += noteItemDTO.getGstAmount();
							if (billTraverse.getId().equals(bill.getId())) {
								currentBillAmount += noteItemDTO.getFinalAmount();
							}
							uptoCurrentNotes.add(noteItemDTO);
						}
					}
				}
			}
			result.setFuelDebitAmountIncluded(fuelDebitAmountIncluded);
			result.setFuelDebitAmount((double) Math.round(fuelDebitAmount));
			result.setCurrentDebitNoteAmount((double) Math.round(currentBillAmount));
			result.setUptoCurrentDebitNoteAmount((double) Math.round(totalAmountAfterHandlingChargeCumulative));

			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addOrUpdateBillDeductionMap(BillDeductionMappingDTO mapObj) {

		try {
			CustomResponse isValidate = validationUtil.validateAddOrUpdateBillDeductionMapObject(mapObj);
			if (isValidate.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
				return isValidate;
			}
			Bill bill = billDao.fetchBillById(mapObj.getBillId());
			Boolean billInEditableState = engineService.findIfEntityInEditableState(EntitiesEnum.BILL.getEntityId(),
					mapObj.getCompanyId(), bill.getState().getId());
			if (!billInEditableState) {
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in editable state mode.");
			}
			String deductionName = mapObj.getDeduction().getName().trim();
			Integer deductionId = deductionDao.fetchBillDeductionIdByName(deductionName, mapObj.getCompanyId());
			if (deductionId == null) {
				BillDeduction deduction = new BillDeduction(null, deductionName, true, mapObj.getCompanyId(),
						new Date(), mapObj.getCreatedBy(), new Date(), mapObj.getCreatedBy());
				deductionId = deductionDao.saveBillDeduction(deduction);
				mapObj.getDeduction().setId(deductionId);
			} else {
				mapObj.getDeduction().setId(deductionId);
			}
			if (mapObj.getId() == null) {
				BillDeductionMapping map = setObject.billDeductionMappingDtoToEntity(mapObj);
				map.setIsActive(true);
				map.setCreatedOn(new Date());
				map.setModifiedOn(new Date());
				map.setModifiedBy(map.getCreatedBy());
				Long mapId = deductionMapDao.saveBillDeductionMapping(map);
				if (mapId == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Already exists.");
				}
				return new CustomResponse(Responses.SUCCESS.getCode(), mapId, Responses.SUCCESS.toString());
			} else {
				BillDeductionMapping savedMap = deductionMapDao.fetchBillDeductionMapById(mapObj.getId());
				BillDeductionMapTransac mapTransac = setObject.billDeductionMappingEntityToTransac(savedMap);
				boolean hasChanges = false;
				if (!savedMap.getDeduction().getId().equals(mapObj.getDeduction().getId())) {
					savedMap.setDeduction(new BillDeduction(mapObj.getDeduction().getId()));
					hasChanges = true;
				}
				if (!savedMap.getIsPercentage().equals(mapObj.getIsPercentage())) {
					savedMap.setIsPercentage(mapObj.getIsPercentage());
					hasChanges = true;
				}
				if (!savedMap.getValue().equals(mapObj.getValue())) {
					savedMap.setValue(mapObj.getValue());
					hasChanges = true;
				}
				if (hasChanges) {
					savedMap.setModifiedOn(new Date());
					savedMap.setModifiedBy(mapObj.getCreatedBy());
					boolean isMapUpdated = deductionMapDao.updateBillDeductionMapping(savedMap);
					if (!isMapUpdated) {
						throw new RuntimeException("Already Exists.");
					}
					deductionMapDao.saveBillDeductionMapTransac(mapTransac);
				}
				return new CustomResponse(Responses.SUCCESS.getCode(), null, Responses.SUCCESS.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
