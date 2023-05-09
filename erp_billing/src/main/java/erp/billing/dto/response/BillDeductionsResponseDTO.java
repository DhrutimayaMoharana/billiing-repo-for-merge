package erp.billing.dto.response;

import java.util.List;

import erp.billing.dto.BillDeductionMappingDTO;

public class BillDeductionsResponseDTO {

	private Double billedAmount;

	private Double deductionAmount;

	private Double netAmount;

	private List<BillDeductionMappingDTO> deductions;

	private Double currentDebitNoteAmount;

	private Double uptoCurrentDebitNoteAmount;

	private Boolean fuelDebitAmountIncluded;

	private Double fuelDebitAmount;

	public BillDeductionsResponseDTO() {
		super();
	}

	public BillDeductionsResponseDTO(Double billedAmount, Double deductionAmount, Double netAmount,
			List<BillDeductionMappingDTO> deductions) {
		super();
		this.billedAmount = billedAmount;
		this.deductionAmount = deductionAmount;
		this.netAmount = netAmount;
		this.deductions = deductions;
		this.setFuelDebitAmountIncluded(false);
		this.fuelDebitAmount = 0.0;
	}

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	public Double getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(Double deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public List<BillDeductionMappingDTO> getDeductions() {
		return deductions;
	}

	public void setDeductions(List<BillDeductionMappingDTO> deductions) {
		this.deductions = deductions;
	}

	public Double getCurrentDebitNoteAmount() {
		return currentDebitNoteAmount;
	}

	public void setCurrentDebitNoteAmount(Double currentDebitNoteAmount) {
		this.currentDebitNoteAmount = currentDebitNoteAmount;
	}

	public Double getUptoCurrentDebitNoteAmount() {
		return uptoCurrentDebitNoteAmount;
	}

	public void setUptoCurrentDebitNoteAmount(Double uptoCurrentDebitNoteAmount) {
		this.uptoCurrentDebitNoteAmount = uptoCurrentDebitNoteAmount;
	}

	public Double getFuelDebitAmount() {
		return fuelDebitAmount;
	}

	public void setFuelDebitAmount(Double fuelDebitAmount) {
		this.fuelDebitAmount = fuelDebitAmount;
	}

	public Boolean getFuelDebitAmountIncluded() {
		return fuelDebitAmountIncluded;
	}

	public void setFuelDebitAmountIncluded(Boolean fuelDebitAmountIncluded) {
		this.fuelDebitAmountIncluded = fuelDebitAmountIncluded;
	}

}
