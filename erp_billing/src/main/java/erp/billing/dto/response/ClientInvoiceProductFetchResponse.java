package erp.billing.dto.response;

import erp.billing.enums.GstTypeEnum;

public class ClientInvoiceProductFetchResponse {

	private Long clientInvoiceProductId;

	private Integer gstManagementId;

	private String name;

	private String hsnCode;

	private Double amount;

	private GstTypeEnum type;

	private Boolean isIgst;

	private Double applicableIgstPercentage;

	private Double applicableIgstAmount;

	private Double applicableCgstPercentage;

	private Double applicableCgstAmount;

	private Double applicableSgstPercentage;

	private Double applicableSgstAmount;

	private Long clientInvoiceId;

	private String remark;

	private String govtUnitAlias;

	private Integer unitId;

	private String unitName;

	private Double quantity;

	public ClientInvoiceProductFetchResponse() {
		super();
	}

	public ClientInvoiceProductFetchResponse(Long clientInvoiceProductId, Integer gstManagementId, String name,
			String hsnCode, Double amount, GstTypeEnum type, Boolean isIgst, Double applicableIgstPercentage,
			Double applicableIgstAmount, Double applicableCgstPercentage, Double applicableCgstAmount,
			Double applicableSgstPercentage, Double applicableSgstAmount, Long clientInvoiceId, String remark,
			String govtUnitAlias, Integer unitId, String unitName, Double quantity) {
		super();
		this.clientInvoiceProductId = clientInvoiceProductId;
		this.gstManagementId = gstManagementId;
		this.name = name;
		this.hsnCode = hsnCode;
		this.amount = amount;
		this.type = type;
		this.isIgst = isIgst;
		this.applicableIgstPercentage = applicableIgstPercentage;
		this.applicableIgstAmount = applicableIgstAmount;
		this.applicableCgstPercentage = applicableCgstPercentage;
		this.applicableCgstAmount = applicableCgstAmount;
		this.applicableSgstPercentage = applicableSgstPercentage;
		this.applicableSgstAmount = applicableSgstAmount;
		this.clientInvoiceId = clientInvoiceId;
		this.remark = remark;
		this.govtUnitAlias = govtUnitAlias;
		this.unitId = unitId;
		this.unitName = unitName;
		this.quantity = quantity;
	}

	public Long getClientInvoiceProductId() {
		return clientInvoiceProductId;
	}

	public void setClientInvoiceProductId(Long clientInvoiceProductId) {
		this.clientInvoiceProductId = clientInvoiceProductId;
	}

	public Integer getGstManagementId() {
		return gstManagementId;
	}

	public void setGstManagementId(Integer gstManagementId) {
		this.gstManagementId = gstManagementId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public GstTypeEnum getType() {
		return type;
	}

	public void setType(GstTypeEnum type) {
		this.type = type;
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

	public Long getClientInvoiceId() {
		return clientInvoiceId;
	}

	public void setClientInvoiceId(Long clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGovtUnitAlias() {
		return govtUnitAlias;
	}

	public void setGovtUnitAlias(String govtUnitAlias) {
		this.govtUnitAlias = govtUnitAlias;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
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

}
