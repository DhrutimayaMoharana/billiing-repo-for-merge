package erp.billing.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.billing.dao.BillDao;
import erp.billing.dao.DebitNoteDao;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.DebitNoteItemDTO;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.response.DebitNoteResponseDTO;
import erp.billing.entity.Bill;
import erp.billing.entity.DebitNoteItem;
import erp.billing.enums.DebitNoteStates;
import erp.billing.enums.Responses;
import erp.billing.service.DebitNoteService;
import erp.billing.util.SetObject;

@Service
@Transactional
public class DebitNoteServiceImpl implements DebitNoteService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SetObject setObject;

	@Autowired
	private DebitNoteDao debitNoteDao;

	@Autowired
	private BillDao billDao;

	@Override
	public CustomResponse getBillDebitNoteItems(SearchDTO search) {

		try {
			Bill bill = billDao.fetchBillById(search.getBillId());
			search.setContractorId(bill.getWorkorder().getContractor().getId());
			search.setFromDate(bill.getFromDate());
			search.setToDate(bill.getToDate());
			search.setWorkorderId(bill.getWorkorder().getId());
			List<DebitNoteItem> noteItems = debitNoteDao.fetchDebitNoteItems(search);
			List<DebitNoteItem> approvedNoteItems = new ArrayList<DebitNoteItem>();
			List<DebitNoteItem> unapprovedNoteItems = new ArrayList<DebitNoteItem>();
			if (noteItems != null) {
				for (DebitNoteItem item : noteItems) {
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

			DebitNoteResponseDTO result = new DebitNoteResponseDTO(approvedItems, totalGstAmount, totalHandlingCharge,
					totalAmountBeforeHandlingCharge, totalAmountAfterHandlingCharge, null,
					totalAmountAfterHandlingCharge, unapprovedCount, totalUnapprovedItemsAmount, unapprovedItems);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getWorkorderDebitNoteItems(SearchDTO search) {

		try {
			Bill currentBill = billDao.fetchBillById(search.getBillId());
			search.setWorkorderId(currentBill.getWorkorder().getId());
			search.setSiteId(currentBill.getSiteId());
			List<Bill> bills = billDao.fetchBills(search);
			Double totalHandlingCharge = 0.0;
			Double totalGstAmount = 0.0;
			Double totalAmountBeforeHandlingCharge = 0.0;
			Double totalAmountAfterHandlingCharge = 0.0;
			Double currentBillAmount = 0.0;
			Double uptoPreviousBillAmount = 0.0;
			List<DebitNoteItemDTO> approvedItems = new ArrayList<>();
			List<DebitNoteItemDTO> unapprovedItems = new ArrayList<>();
			Double totalUnapprovedItemsAmount = 0.0;
			int unapprovedCount = 0;

			if (bills != null) {
				for (Bill bill : bills) {
					if (bill.getToDate().after(currentBill.getToDate())) {
						continue;
					}
					search.setContractorId(bill.getWorkorder().getContractor().getId());
					search.setFromDate(bill.getFromDate());
					search.setToDate(bill.getToDate());
					search.setWorkorderId(bill.getWorkorder().getId());
					List<DebitNoteItem> noteItems = debitNoteDao.fetchDebitNoteItems(search);
					List<DebitNoteItem> approvedNoteItems = new ArrayList<DebitNoteItem>();
					List<DebitNoteItem> unapprovedNoteItems = new ArrayList<DebitNoteItem>();

					if (noteItems != null) {
						for (DebitNoteItem item : noteItems) {
							if (item.getDebitNote().getStatus().equals(DebitNoteStates.FullyApproved.getId())) {
								approvedNoteItems.add(item);
							} else {
								unapprovedNoteItems.add(item);
							}
						}
					}

					if (approvedNoteItems != null) {
						for (DebitNoteItem noteItem : approvedNoteItems) {
							DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
							totalHandlingCharge += noteItemDTO.getHandlingCharge();
							totalAmountAfterHandlingCharge += noteItemDTO.getFinalAmount();
							totalGstAmount += noteItemDTO.getGstAmount();
							if (bill.getId().equals(currentBill.getId())) {
								currentBillAmount += noteItemDTO.getFinalAmount();
							}
							approvedItems.add(noteItemDTO);
						}
					}

					if (unapprovedNoteItems != null) {
						for (DebitNoteItem noteItem : unapprovedNoteItems) {
							DebitNoteItemDTO noteItemDTO = setObject.debitNoteItemEntityToDto(noteItem);
							totalUnapprovedItemsAmount += noteItemDTO.getFinalAmount();
							unapprovedItems.add(noteItemDTO);
							++unapprovedCount;
						}
					}

				}
			}
			totalAmountBeforeHandlingCharge = totalAmountAfterHandlingCharge - totalHandlingCharge;
			uptoPreviousBillAmount = totalAmountAfterHandlingCharge - currentBillAmount;
			DebitNoteResponseDTO result = new DebitNoteResponseDTO(approvedItems, totalGstAmount, totalHandlingCharge,
					totalAmountBeforeHandlingCharge, totalAmountAfterHandlingCharge, uptoPreviousBillAmount,
					currentBillAmount, unapprovedCount, totalUnapprovedItemsAmount, unapprovedItems);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
