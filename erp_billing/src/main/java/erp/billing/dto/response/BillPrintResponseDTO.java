package erp.billing.dto.response;

import java.util.List;

import erp.billing.dto.BillDTO;
import erp.billing.dto.BillPrintBoqResponseDTO;
import erp.billing.dto.SiteDTO;

public class BillPrintResponseDTO {

	private BillDTO bill;

	private List<BillPrintDescriptionItemResponseDTO> ipcItems;

	private List<BillPrintBoqResponseDTO> boqs;

	private List<BillMachineCategoryResponse> machineCategories;

	private Double cumulativeBillAmount;

	private Double previousBillAmount;

	private Double currentBillAmount;

	private Double invoiceAmountBeforeGst;

	private Double invoiceAmountAfterGst;

	private Double workorderAmount;

	private SiteDTO site;

	private List<BillPrintTaxInvoiceItemResponseDTO> invoiceItems;

	private DebitNoteResponseDTO currentDebitNote;

	private DebitNoteResponseDTO uptoCurrentDebitNote;

	private PriceEscalationResponseDTO priceEscalation;

	public BillPrintResponseDTO() {
		super();
	}

	public List<BillPrintDescriptionItemResponseDTO> getIpcItems() {
		return ipcItems;
	}

	public void setIpcItems(List<BillPrintDescriptionItemResponseDTO> ipcItems) {
		this.ipcItems = ipcItems;
	}

	public BillDTO getBill() {
		return bill;
	}

	public void setBill(BillDTO bill) {
		this.bill = bill;
	}

	public SiteDTO getSite() {
		return site;
	}

	public void setSite(SiteDTO site) {
		this.site = site;
	}

	public List<BillPrintBoqResponseDTO> getBoqs() {
		return boqs;
	}

	public void setBoqs(List<BillPrintBoqResponseDTO> boqs) {
		this.boqs = boqs;
	}

	public List<BillPrintTaxInvoiceItemResponseDTO> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(List<BillPrintTaxInvoiceItemResponseDTO> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	public DebitNoteResponseDTO getCurrentDebitNote() {
		return currentDebitNote;
	}

	public void setCurrentDebitNote(DebitNoteResponseDTO currentDebitNote) {
		this.currentDebitNote = currentDebitNote;
	}

	public DebitNoteResponseDTO getUptoCurrentDebitNote() {
		return uptoCurrentDebitNote;
	}

	public void setUptoCurrentDebitNote(DebitNoteResponseDTO uptoCurrentDebitNote) {
		this.uptoCurrentDebitNote = uptoCurrentDebitNote;
	}

	public PriceEscalationResponseDTO getPriceEscalation() {
		return priceEscalation;
	}

	public void setPriceEscalation(PriceEscalationResponseDTO priceEscalation) {
		this.priceEscalation = priceEscalation;
	}

	public Double getCumulativeBillAmount() {
		return cumulativeBillAmount;
	}

	public void setCumulativeBillAmount(Double cumulativeBillAmount) {
		this.cumulativeBillAmount = cumulativeBillAmount;
	}

	public Double getPreviousBillAmount() {
		return previousBillAmount;
	}

	public void setPreviousBillAmount(Double previousBillAmount) {
		this.previousBillAmount = previousBillAmount;
	}

	public Double getCurrentBillAmount() {
		return currentBillAmount;
	}

	public void setCurrentBillAmount(Double currentBillAmount) {
		this.currentBillAmount = currentBillAmount;
	}

	public Double getInvoiceAmountBeforeGst() {
		return invoiceAmountBeforeGst;
	}

	public void setInvoiceAmountBeforeGst(Double invoiceAmountBeforeGst) {
		this.invoiceAmountBeforeGst = invoiceAmountBeforeGst;
	}

	public Double getInvoiceAmountAfterGst() {
		return invoiceAmountAfterGst;
	}

	public void setInvoiceAmountAfterGst(Double invoiceAmountAfterGst) {
		this.invoiceAmountAfterGst = invoiceAmountAfterGst;
	}

	public Double getWorkorderAmount() {
		return workorderAmount;
	}

	public void setWorkorderAmount(Double workorderAmount) {
		this.workorderAmount = workorderAmount;
	}

	public List<BillMachineCategoryResponse> getMachineCategories() {
		return machineCategories;
	}

	public void setMachineCategories(List<BillMachineCategoryResponse> machineCategories) {
		this.machineCategories = machineCategories;
	}

}
