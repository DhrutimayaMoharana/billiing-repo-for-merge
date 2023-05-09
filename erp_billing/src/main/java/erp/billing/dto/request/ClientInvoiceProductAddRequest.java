package erp.billing.dto.request;

public class ClientInvoiceProductAddRequest {

	private Long id;

	private Integer gstManagementId;

	private Double amount;

	private Boolean isIgst;

	private Double applicableIgstPercentage;

	private Double applicableIgstAmount;

	private Double applicableCgstPercentage;

	private Double applicableCgstAmount;

	private Double applicableSgstPercentage;

	private Double applicableSgstAmount;

	private String remark;

	private Long unitId;

	private Double quantity;

	private Long updatedBy;

	private Long clientInvoiceId;

	public ClientInvoiceProductAddRequest() {
		super();
	}

	public ClientInvoiceProductAddRequest(Long id, Integer gstManagementId, Double amount, Boolean isIgst,
			Double applicableIgstPercentage, Double applicableIgstAmount, Double applicableCgstPercentage,
			Double applicableCgstAmount, Double applicableSgstPercentage, Double applicableSgstAmount, String remark,
			Long unitId, Double quantity, Long updatedBy, Long clientInvoiceId) {
		super();
		this.id = id;
		this.gstManagementId = gstManagementId;
		this.amount = amount;
		this.isIgst = isIgst;
		this.applicableIgstPercentage = applicableIgstPercentage;
		this.applicableIgstAmount = applicableIgstAmount;
		this.applicableCgstPercentage = applicableCgstPercentage;
		this.applicableCgstAmount = applicableCgstAmount;
		this.applicableSgstPercentage = applicableSgstPercentage;
		this.applicableSgstAmount = applicableSgstAmount;
		this.remark = remark;
		this.unitId = unitId;
		this.quantity = quantity;
		this.updatedBy = updatedBy;
		this.clientInvoiceId = clientInvoiceId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGstManagementId() {
		return gstManagementId;
	}

	public void setGstManagementId(Integer gstManagementId) {
		this.gstManagementId = gstManagementId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getIsIgst() {
		return isIgst;
	}

	public void setIsIgst(Boolean isIgst) {
		this.isIgst = isIgst;
	}

	public Double getApplicableIgstPercentage() {
		return applicableIgstPercentage;
	}

	public void setApplicableIgstPercentage(Double applicableIgstPercentage) {
		this.applicableIgstPercentage = applicableIgstPercentage;
	}

	public Double getApplicableIgstAmount() {
		return applicableIgstAmount;
	}

	public void setApplicableIgstAmount(Double applicableIgstAmount) {
		this.applicableIgstAmount = applicableIgstAmount;
	}

	public Double getApplicableCgstPercentage() {
		return applicableCgstPercentage;
	}

	public void setApplicableCgstPercentage(Double applicableCgstPercentage) {
		this.applicableCgstPercentage = applicableCgstPercentage;
	}

	public Double getApplicableCgstAmount() {
		return applicableCgstAmount;
	}

	public void setApplicableCgstAmount(Double applicableCgstAmount) {
		this.applicableCgstAmount = applicableCgstAmount;
	}

	public Double getApplicableSgstPercentage() {
		return applicableSgstPercentage;
	}

	public void setApplicableSgstPercentage(Double applicableSgstPercentage) {
		this.applicableSgstPercentage = applicableSgstPercentage;
	}

	public Double getApplicableSgstAmount() {
		return applicableSgstAmount;
	}

	public void setApplicableSgstAmount(Double applicableSgstAmount) {
		this.applicableSgstAmount = applicableSgstAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getClientInvoiceId() {
		return clientInvoiceId;
	}

	public void setClientInvoiceId(Long clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}

}
