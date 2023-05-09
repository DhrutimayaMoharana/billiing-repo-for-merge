package erp.billing.dto.response;

import java.util.List;

import erp.billing.dto.DebitNoteItemDTO;

public class DebitNoteResponseDTO {

	List<DebitNoteItemDTO> items;
	
	private Double totalGstAmount;

	private Double totalHandlingCharge;

	private Double totalAmountBeforeHandlingCharge;

	private Double totalAmountAfterHandlingCharge;
	
	private Double amountUptoPrevious;
	
	private Double amountCurrent;
	
	private Integer unapprovedItemsCount;
	
	private Double unapprovedItemsTotalAmountAfterHandlingCharge;

	List<DebitNoteItemDTO> unapprovedItems;

	public DebitNoteResponseDTO() {
		super();
	}

	public DebitNoteResponseDTO(List<DebitNoteItemDTO> items, Double totalGstAmount, Double totalHandlingCharge,
			Double totalAmountBeforeHandlingCharge, Double totalAmountAfterHandlingCharge, Double amountUptoPrevious,
			Double amountCurrent, Integer unapprovedItemsCount, Double unapprovedItemsTotalAmountAfterHandlingCharge,
			List<DebitNoteItemDTO> unapprovedItems) {
		super();
		this.items = items;
		this.totalGstAmount = totalGstAmount;
		this.totalHandlingCharge = totalHandlingCharge;
		this.totalAmountBeforeHandlingCharge = totalAmountBeforeHandlingCharge;
		this.totalAmountAfterHandlingCharge = totalAmountAfterHandlingCharge;
		this.amountUptoPrevious = amountUptoPrevious;
		this.amountCurrent = amountCurrent;
		this.unapprovedItemsCount = unapprovedItemsCount;
		this.unapprovedItemsTotalAmountAfterHandlingCharge = unapprovedItemsTotalAmountAfterHandlingCharge;
		this.unapprovedItems = unapprovedItems;
	}

	public List<DebitNoteItemDTO> getItems() {
		return items;
	}

	public void setItems(List<DebitNoteItemDTO> items) {
		this.items = items;
	}

	public Double getTotalHandlingCharge() {
		return totalHandlingCharge;
	}

	public void setTotalHandlingCharge(Double totalHandlingCharge) {
		this.totalHandlingCharge = totalHandlingCharge;
	}

	public Double getTotalAmountBeforeHandlingCharge() {
		return totalAmountBeforeHandlingCharge;
	}

	public void setTotalAmountBeforeHandlingCharge(Double totalAmountBeforeHandlingCharge) {
		this.totalAmountBeforeHandlingCharge = totalAmountBeforeHandlingCharge;
	}

	public Double getTotalAmountAfterHandlingCharge() {
		return totalAmountAfterHandlingCharge;
	}

	public void setTotalAmountAfterHandlingCharge(Double totalAmountAfterHandlingCharge) {
		this.totalAmountAfterHandlingCharge = totalAmountAfterHandlingCharge;
	}

	public Double getTotalGstAmount() {
		return totalGstAmount;
	}

	public void setTotalGstAmount(Double totalGstAmount) {
		this.totalGstAmount = totalGstAmount;
	}

	public Double getAmountUptoPrevious() {
		return amountUptoPrevious;
	}

	public void setAmountUptoPrevious(Double amountUptoPrevious) {
		this.amountUptoPrevious = amountUptoPrevious;
	}

	public Double getAmountCurrent() {
		return amountCurrent;
	}

	public void setAmountCurrent(Double amountCurrent) {
		this.amountCurrent = amountCurrent;
	}

	public Integer getUnapprovedItemsCount() {
		return unapprovedItemsCount;
	}

	public void setUnapprovedItemsCount(Integer unapprovedItemsCount) {
		this.unapprovedItemsCount = unapprovedItemsCount;
	}

	public Double getUnapprovedItemsTotalAmountAfterHandlingCharge() {
		return unapprovedItemsTotalAmountAfterHandlingCharge;
	}

	public void setUnapprovedItemsTotalAmountAfterHandlingCharge(
			Double unapprovedItemsTotalAmountAfterHandlingCharge) {
		this.unapprovedItemsTotalAmountAfterHandlingCharge = unapprovedItemsTotalAmountAfterHandlingCharge;
	}

	public List<DebitNoteItemDTO> getUnapprovedItems() {
		return unapprovedItems;
	}

	public void setUnapprovedItems(List<DebitNoteItemDTO> unapprovedItems) {
		this.unapprovedItems = unapprovedItems;
	}

}
