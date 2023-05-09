package erp.billing.dto.response;

import java.util.Date;
import java.util.List;

import erp.billing.enums.IrnDocumentType;
import erp.billing.enums.IrnSupplyType;

public class ClientInvoiceFetchResponse {

	private Long clientInvoiceId;

	private IrnDocumentType invoiceType;

	private IrnSupplyType supplyType;

	private String invoiceNo;

	private Date invoiceDate;

	private Date supplyDate;

	private Long supplyStateId;

	private String supplyStateCode;

	private String supplyStateName;

	private String termsAndConditions;

	private Boolean reverseCharge;

	private Short transportMode;

	private String transportModeName;

	private String vehicleNo;

	private Integer ciuId;

	private String ciuOfficeName;

	private Integer billingStateId;

	private String billingStateCode;

	private String billingStateName;

	private Integer billingCityId;

	private String billingCityName;

	private String billingAddress;

	private String billingZipCode;

	private Integer shippingStateId;

	private String shippingStateName;

	private Integer shippingCityId;

	private String shippingCityName;

	private String shippingAddress;

	private String shippingZipCode;

	private Integer dispatchStateId;

	private String dispatchStateCode;

	private String dispatchStateName;

	private Integer dispatchCityId;

	private String dispatchCityName;

	private String dispatchAddress;

	private String dispatchZipCode;

	private String gstNo;

	private List<ClientInvoiceProductFetchResponse> products;

	private Boolean isIgst;

	private Double applicableIgst;

	private Double applicableIgstAmount;

	private Double applicableCgst;

	private Double applicableCgstAmount;

	private Double applicableSgst;

	private Double applicableSgstAmount;

	private Double totalAmountBeforeGst;

	private Double totalAmountAfterGst;

	private String upiUrl;

	private Long siteId;

	private String siteName;

	private Integer stateId;

	private String stateName;

	private String stateAlias;

	private String stateRgbColorCode;

	private String stateButtonText;

	private String remarks;

	private Date lastUpdatedOn;

	private String lastUpdatedBy;

	private ClientInvoiceIrnInfoResponse clientInvoiceIrnInfo;

	// extra field

	private Boolean isIrnCancelled;

	private Boolean isDeletable;

	private Boolean isEditable;

	private Boolean inFinalState;

	private Boolean isFinalSuccessState;

	public ClientInvoiceFetchResponse() {
		super();
	}

	public ClientInvoiceFetchResponse(Long clientInvoiceId, IrnDocumentType invoiceType, IrnSupplyType supplyType,
			String invoiceNo, Date invoiceDate, Date supplyDate, Long supplyStateId, String supplyStateCode,
			String supplyStateName, String termsAndConditions, Boolean reverseCharge, Short transportMode,
			String transportModeName, String vehicleNo, Integer ciuId, String ciuOfficeName, Integer billingStateId,
			String billingStateCode, String billingStateName, Integer billingCityId, String billingCityName,
			String billingAddress, String billingZipCode, Integer shippingStateId, String shippingStateName,
			Integer shippingCityId, String shippingCityName, String shippingAddress, String shippingZipCode,
			Integer dispatchStateId, String dispatchStateCode, String dispatchStateName, Integer dispatchCityId,
			String dispatchCityName, String dispatchAddress, String dispatchZipCode, String gstNo,
			List<ClientInvoiceProductFetchResponse> products, Boolean isIgst, Double applicableIgst,
			Double applicableCgst, Double applicableSgst, Double totalAmountBeforeGst, Double totalAmountAfterGst,
			String upiUrl, Long siteId, String siteName, Integer stateId, String stateName, String stateAlias,
			String stateRgbColorCode, String stateButtonText, String remarks, Date lastUpdatedOn,
			String lastUpdatedBy) {
		super();
		this.clientInvoiceId = clientInvoiceId;
		this.invoiceType = invoiceType;
		this.supplyType = supplyType;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.supplyDate = supplyDate;
		this.supplyStateId = supplyStateId;
		this.supplyStateCode = supplyStateCode;
		this.supplyStateName = supplyStateName;
		this.termsAndConditions = termsAndConditions;
		this.reverseCharge = reverseCharge;
		this.transportMode = transportMode;
		this.transportModeName = transportModeName;
		this.vehicleNo = vehicleNo;
		this.ciuId = ciuId;
		this.ciuOfficeName = ciuOfficeName;
		this.billingStateId = billingStateId;
		this.billingStateCode = billingStateCode;
		this.billingStateName = billingStateName;
		this.billingCityId = billingCityId;
		this.billingCityName = billingCityName;
		this.billingAddress = billingAddress;
		this.billingZipCode = billingZipCode;
		this.shippingStateId = shippingStateId;
		this.shippingStateName = shippingStateName;
		this.shippingCityId = shippingCityId;
		this.shippingCityName = shippingCityName;
		this.shippingAddress = shippingAddress;
		this.shippingZipCode = shippingZipCode;
		this.dispatchStateId = dispatchStateId;
		this.dispatchStateCode = dispatchStateCode;
		this.dispatchStateName = dispatchStateName;
		this.dispatchCityId = dispatchCityId;
		this.dispatchCityName = dispatchCityName;
		this.dispatchAddress = dispatchAddress;
		this.dispatchZipCode = dispatchZipCode;
		this.gstNo = gstNo;
		this.products = products;
		this.isIgst = isIgst;
		this.applicableIgst = applicableIgst;
		this.applicableCgst = applicableCgst;
		this.applicableSgst = applicableSgst;
		this.totalAmountBeforeGst = totalAmountBeforeGst;
		this.totalAmountAfterGst = totalAmountAfterGst;
		this.upiUrl = upiUrl;
		this.siteId = siteId;
		this.siteName = siteName;
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateAlias = stateAlias;
		this.stateRgbColorCode = stateRgbColorCode;
		this.stateButtonText = stateButtonText;
		this.remarks = remarks;
		this.lastUpdatedOn = lastUpdatedOn;
		this.lastUpdatedBy = lastUpdatedBy;
		;
	}

	public Long getClientInvoiceId() {
		return clientInvoiceId;
	}

	public void setClientInvoiceId(Long clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}

	public IrnDocumentType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(IrnDocumentType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public IrnSupplyType getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(IrnSupplyType supplyType) {
		this.supplyType = supplyType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getSupplyDate() {
		return supplyDate;
	}

	public void setSupplyDate(Date supplyDate) {
		this.supplyDate = supplyDate;
	}

	public Long getSupplyStateId() {
		return supplyStateId;
	}

	public void setSupplyStateId(Long supplyStateId) {
		this.supplyStateId = supplyStateId;
	}

	public String getSupplyStateCode() {
		return supplyStateCode;
	}

	public void setSupplyStateCode(String supplyStateCode) {
		this.supplyStateCode = supplyStateCode;
	}

	public String getSupplyStateName() {
		return supplyStateName;
	}

	public void setSupplyStateName(String supplyStateName) {
		this.supplyStateName = supplyStateName;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public Boolean getReverseCharge() {
		return reverseCharge;
	}

	public void setReverseCharge(Boolean reverseCharge) {
		this.reverseCharge = reverseCharge;
	}

	public Short getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(Short transportMode) {
		this.transportMode = transportMode;
	}

	public String getTransportModeName() {
		return transportModeName;
	}

	public void setTransportModeName(String transportModeName) {
		this.transportModeName = transportModeName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Integer getCiuId() {
		return ciuId;
	}

	public void setCiuId(Integer ciuId) {
		this.ciuId = ciuId;
	}

	public String getCiuOfficeName() {
		return ciuOfficeName;
	}

	public void setCiuOfficeName(String ciuOfficeName) {
		this.ciuOfficeName = ciuOfficeName;
	}

	public Integer getBillingStateId() {
		return billingStateId;
	}

	public void setBillingStateId(Integer billingStateId) {
		this.billingStateId = billingStateId;
	}

	public String getBillingStateCode() {
		return billingStateCode;
	}

	public void setBillingStateCode(String billingStateCode) {
		this.billingStateCode = billingStateCode;
	}

	public String getBillingStateName() {
		return billingStateName;
	}

	public void setBillingStateName(String billingStateName) {
		this.billingStateName = billingStateName;
	}

	public Integer getBillingCityId() {
		return billingCityId;
	}

	public void setBillingCityId(Integer billingCityId) {
		this.billingCityId = billingCityId;
	}

	public String getBillingCityName() {
		return billingCityName;
	}

	public void setBillingCityName(String billingCityName) {
		this.billingCityName = billingCityName;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingZipCode() {
		return billingZipCode;
	}

	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	public Integer getShippingStateId() {
		return shippingStateId;
	}

	public void setShippingStateId(Integer shippingStateId) {
		this.shippingStateId = shippingStateId;
	}

	public String getShippingStateName() {
		return shippingStateName;
	}

	public void setShippingStateName(String shippingStateName) {
		this.shippingStateName = shippingStateName;
	}

	public Integer getShippingCityId() {
		return shippingCityId;
	}

	public void setShippingCityId(Integer shippingCityId) {
		this.shippingCityId = shippingCityId;
	}

	public String getShippingCityName() {
		return shippingCityName;
	}

	public void setShippingCityName(String shippingCityName) {
		this.shippingCityName = shippingCityName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getShippingZipCode() {
		return shippingZipCode;
	}

	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}

	public Integer getDispatchStateId() {
		return dispatchStateId;
	}

	public void setDispatchStateId(Integer dispatchStateId) {
		this.dispatchStateId = dispatchStateId;
	}

	public String getDispatchStateCode() {
		return dispatchStateCode;
	}

	public void setDispatchStateCode(String dispatchStateCode) {
		this.dispatchStateCode = dispatchStateCode;
	}

	public void setClientInvoiceIrnInfo(ClientInvoiceIrnInfoResponse clientInvoiceIrnInfo) {
		this.clientInvoiceIrnInfo = clientInvoiceIrnInfo;
	}

	public String getDispatchStateName() {
		return dispatchStateName;
	}

	public void setDispatchStateName(String dispatchStateName) {
		this.dispatchStateName = dispatchStateName;
	}

	public Integer getDispatchCityId() {
		return dispatchCityId;
	}

	public void setDispatchCityId(Integer dispatchCityId) {
		this.dispatchCityId = dispatchCityId;
	}

	public String getDispatchCityName() {
		return dispatchCityName;
	}

	public void setDispatchCityName(String dispatchCityName) {
		this.dispatchCityName = dispatchCityName;
	}

	public String getDispatchAddress() {
		return dispatchAddress;
	}

	public void setDispatchAddress(String dispatchAddress) {
		this.dispatchAddress = dispatchAddress;
	}

	public String getDispatchZipCode() {
		return dispatchZipCode;
	}

	public void setDispatchZipCode(String dispatchZipCode) {
		this.dispatchZipCode = dispatchZipCode;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public List<ClientInvoiceProductFetchResponse> getProducts() {
		return products;
	}

	public void setProducts(List<ClientInvoiceProductFetchResponse> products) {
		this.products = products;
	}

	public Boolean getIsIgst() {
		return isIgst;
	}

	public void setIsIgst(Boolean isIgst) {
		this.isIgst = isIgst;
	}

	public Double getApplicableIgst() {
		return applicableIgst;
	}

	public void setApplicableIgst(Double applicableIgst) {
		this.applicableIgst = applicableIgst;
	}

	public Double getApplicableCgst() {
		return applicableCgst;
	}

	public void setApplicableCgst(Double applicableCgst) {
		this.applicableCgst = applicableCgst;
	}

	public Double getApplicableSgst() {
		return applicableSgst;
	}

	public void setApplicableSgst(Double applicableSgst) {
		this.applicableSgst = applicableSgst;
	}

	public Double getTotalAmountBeforeGst() {
		return totalAmountBeforeGst;
	}

	public void setTotalAmountBeforeGst(Double totalAmountBeforeGst) {
		this.totalAmountBeforeGst = totalAmountBeforeGst;
	}

	public Double getTotalAmountAfterGst() {
		return totalAmountAfterGst;
	}

	public void setTotalAmountAfterGst(Double totalAmountAfterGst) {
		this.totalAmountAfterGst = totalAmountAfterGst;
	}

	public String getUpiUrl() {
		return upiUrl;
	}

	public void setUpiUrl(String upiUrl) {
		this.upiUrl = upiUrl;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateAlias() {
		return stateAlias;
	}

	public void setStateAlias(String stateAlias) {
		this.stateAlias = stateAlias;
	}

	public String getStateRgbColorCode() {
		return stateRgbColorCode;
	}

	public void setStateRgbColorCode(String stateRgbColorCode) {
		this.stateRgbColorCode = stateRgbColorCode;
	}

	public String getStateButtonText() {
		return stateButtonText;
	}

	public void setStateButtonText(String stateButtonText) {
		this.stateButtonText = stateButtonText;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Boolean getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(Boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Boolean getInFinalState() {
		return inFinalState;
	}

	public void setInFinalState(Boolean inFinalState) {
		this.inFinalState = inFinalState;
	}

	public Boolean getIsFinalSuccessState() {
		return isFinalSuccessState;
	}

	public void setIsFinalSuccessState(Boolean isFinalSuccessState) {
		this.isFinalSuccessState = isFinalSuccessState;
	}

	public ClientInvoiceIrnInfoResponse getClientInvoiceIrnInfo() {
		return clientInvoiceIrnInfo;
	}

	public void setClientInvoiceIrnInfoResponse(ClientInvoiceIrnInfoResponse clientInvoiceIrnInfo) {
		this.clientInvoiceIrnInfo = clientInvoiceIrnInfo;
	}

	public Double getApplicableIgstAmount() {
		return applicableIgstAmount;
	}

	public void setApplicableIgstAmount(Double applicableIgstAmount) {
		this.applicableIgstAmount = applicableIgstAmount;
	}

	public Double getApplicableCgstAmount() {
		return applicableCgstAmount;
	}

	public void setApplicableCgstAmount(Double applicableCgstAmount) {
		this.applicableCgstAmount = applicableCgstAmount;
	}

	public Double getApplicableSgstAmount() {
		return applicableSgstAmount;
	}

	public void setApplicableSgstAmount(Double applicableSgstAmount) {
		this.applicableSgstAmount = applicableSgstAmount;
	}

	public Boolean getIsIrnCancelled() {
		return isIrnCancelled;
	}

	public void setIsIrnCancelled(Boolean isIrnCancelled) {
		this.isIrnCancelled = isIrnCancelled;
	}

}
