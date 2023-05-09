package erp.billing.dto;

import java.util.Date;

public class DebitNoteItemDTO {

	private Long id;

	private Long workorderId;

	private MaterialDTO material;

	private Integer departmentId;

	private Date fromDate;

	private Date toDate;

	private Double quantity;

	private Double rate;

	private Double amount;

	private Double handlingCharge;

	private Double amountAfterHandlingCharge;

	private Double gst;

	private Double gstAmount;

	private Double finalAmount;

	private Boolean isActive;

	private Double handlingChargePercentage;

	public DebitNoteItemDTO() {
		super();
	}

	public DebitNoteItemDTO(Long id) {
		super();
		this.id = id;
	}

	public DebitNoteItemDTO(Long id, Long workorderId, MaterialDTO material, Integer departmentId, Date fromDate,
			Date toDate, Double quantity, Double rate, Double amount, Double handlingCharge,
			Double amountAfterHandlingCharge, Double gst, Double gstAmount, Double finalAmount, Boolean isActive,
			Double handlingChargePercentage) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.material = material;
		this.departmentId = departmentId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
		this.handlingCharge = handlingCharge;
		this.amountAfterHandlingCharge = amountAfterHandlingCharge;
		this.gst = gst;
		this.gstAmount = gstAmount;
		this.finalAmount = finalAmount;
		this.isActive = isActive;
		this.handlingChargePercentage = handlingChargePercentage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MaterialDTO getMaterial() {
		return material;
	}

	public void setMaterial(MaterialDTO material) {
		this.material = material;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	public Double getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(Double handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	public Double getAmountAfterHandlingCharge() {
		return amountAfterHandlingCharge;
	}

	public void setAmountAfterHandlingCharge(Double amountAfterHandlingCharge) {
		this.amountAfterHandlingCharge = amountAfterHandlingCharge;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(Double gstAmount) {
		this.gstAmount = gstAmount;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Double getHandlingChargePercentage() {
		return handlingChargePercentage;
	}

	public void setHandlingChargePercentage(Double handlingChargePercentage) {
		this.handlingChargePercentage = handlingChargePercentage;
	}

}
