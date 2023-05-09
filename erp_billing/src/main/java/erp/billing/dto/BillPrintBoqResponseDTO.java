package erp.billing.dto;

public class BillPrintBoqResponseDTO {

	private Long categoryId;

	private Long boqId;

	private String sdbCode;

	private String vendorDescription;

	private String unitName;

	private Double quantity;

	private Double rate;

	private Double amount;

	private Double commulativeQuantity;

	private Double commulativeAmount;

	private Double previousQuantity;

	private Double previousAmount;

	private Double currentQuantity;

	private Double currentAmount;

	public BillPrintBoqResponseDTO() {
		super();
	}

	public BillPrintBoqResponseDTO(Long categoryId, Long boqId, String sdbCode, String vendorDescription,
			String unitName, Double quantity, Double rate, Double amount, Double commulativeQuantity,
			Double commulativeAmount, Double previousQuantity, Double previousAmount, Double currentQuantity,
			Double currentAmount) {
		super();
		this.setCategoryId(categoryId);
		this.setBoqId(boqId);
		this.sdbCode = sdbCode;
		this.vendorDescription = vendorDescription;
		this.unitName = unitName;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
		this.commulativeQuantity = commulativeQuantity;
		this.commulativeAmount = commulativeAmount;
		this.previousQuantity = previousQuantity;
		this.previousAmount = previousAmount;
		this.currentQuantity = currentQuantity;
		this.currentAmount = currentAmount;
	}

	public String getSdbCode() {
		return sdbCode;
	}

	public void setSdbCode(String sdbCode) {
		this.sdbCode = sdbCode;
	}

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getCommulativeQuantity() {
		return commulativeQuantity;
	}

	public void setCommulativeQuantity(Double commulativeQuantity) {
		this.commulativeQuantity = commulativeQuantity;
	}

	public Double getCommulativeAmount() {
		return commulativeAmount;
	}

	public void setCommulativeAmount(Double commulativeAmount) {
		this.commulativeAmount = commulativeAmount;
	}

	public Double getPreviousQuantity() {
		return previousQuantity;
	}

	public void setPreviousQuantity(Double previousQuantity) {
		this.previousQuantity = previousQuantity;
	}

	public Double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(Double previousAmount) {
		this.previousAmount = previousAmount;
	}

	public Double getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(Double currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public Double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

}
