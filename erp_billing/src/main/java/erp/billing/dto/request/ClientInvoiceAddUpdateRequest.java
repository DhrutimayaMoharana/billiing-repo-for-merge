package erp.billing.dto.request;

import java.util.Date;
import java.util.List;

import erp.billing.enums.IrnDocumentType;
import erp.billing.enums.IrnSupplyType;

public class ClientInvoiceAddUpdateRequest {

	private Long id;

	private IrnDocumentType invoiceType;

	private IrnSupplyType supplyType;

	private Date invoiceDate;

	private Date supplyDate;

	private Long supplyStateId;

	private String termsAndConditions;

	private Boolean reverseCharge;

	private Short transportMode;

	private String vehicleNo;

	private Integer stateId;

	private List<ClientInvoiceProductAddRequest> products;

	private Integer ciuId;

	private Integer billingCityId;

	private String billingAddress;

	private String billingZipCode;

	private Integer shippingCityId;

	private String shippingAddress;

	private String shippingZipCode;

	private Integer dispatchCityId;

	private String dispatchAddress;

	private String dispatchZipCode;

	private String gstNo;

	private String remarks;

	private Long siteId;

//	Extra Fields	
	private Long updatedBy;

	private Integer companyId;

	public ClientInvoiceAddUpdateRequest() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public List<ClientInvoiceProductAddRequest> getProducts() {
		return products;
	}

	public void setProducts(List<ClientInvoiceProductAddRequest> products) {
		this.products = products;
	}

	public Integer getCiuId() {
		return ciuId;
	}

	public void setCiuId(Integer ciuId) {
		this.ciuId = ciuId;
	}

	public Integer getBillingCityId() {
		return billingCityId;
	}

	public void setBillingCityId(Integer billingCityId) {
		this.billingCityId = billingCityId;
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

	public Integer getShippingCityId() {
		return shippingCityId;
	}

	public void setShippingCityId(Integer shippingCityId) {
		this.shippingCityId = shippingCityId;
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

	public Integer getDispatchCityId() {
		return dispatchCityId;
	}

	public void setDispatchCityId(Integer dispatchCityId) {
		this.dispatchCityId = dispatchCityId;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
