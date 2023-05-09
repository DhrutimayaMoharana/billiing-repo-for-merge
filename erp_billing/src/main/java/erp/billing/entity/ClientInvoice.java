package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import erp.billing.enums.IrnDocumentType;
import erp.billing.enums.IrnSupplyType;

@Entity
@Table(name = "client_invoices")
public class ClientInvoice implements Serializable {

	private static final long serialVersionUID = 7578160364903973412L;

	private Long id;

	private IrnDocumentType invoiceType;

	private IrnSupplyType supplyType;

	private String invoiceNo;

	private Date invoiceDate;

	private Date supplyDate;

	private CountryState supplyState;

	private String termsAndConditions;

	private Boolean reverseCharge;

	private TransportMode transportMode;

	private String vehicleNo;

	private EngineState state;

	private ClientImplementationUnit ciu;

	private City billingCity;

	private String billingAddress;

	private String billingZipCode;

	private City shippingCity;

	private String shippingAddress;

	private String shippingZipCode;

	private Integer dispatchCityId;

	private String dispatchAddress;

	private String dispatchZipCode;

	private String gstNo;

	private String remarks;

	private Long siteId;

	private Integer companyId;

	private Boolean isActive;

	private Date updatedOn;

	private Long updatedBy;

	private User updatedByDetail;

	private Site site;

	private City dispatchCity;

	public ClientInvoice() {
		super();
	}

	public ClientInvoice(Long id, IrnDocumentType invoiceType, IrnSupplyType supplyType, String invoiceNo,
			Date invoiceDate, Date supplyDate, CountryState supplyState, String termsAndConditions,
			Boolean reverseCharge, TransportMode transportMode, String vehicleNo, EngineState state,
			ClientImplementationUnit ciu, City billingCity, String billingAddress, String billingZipCode,
			City shippingCity, String shippingAddress, String shippingZipCode, Integer dispatchCityId,
			String dispatchAddress, String dispatchZipCode, String gstNo, String remarks, Long siteId,
			Integer companyId, Boolean isActive, Date updatedOn, Long updatedBy) {
		super();
		this.id = id;
		this.invoiceType = invoiceType;
		this.supplyType = supplyType;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.supplyDate = supplyDate;
		this.supplyState = supplyState;
		this.termsAndConditions = termsAndConditions;
		this.reverseCharge = reverseCharge;
		this.transportMode = transportMode;
		this.vehicleNo = vehicleNo;
		this.state = state;
		this.ciu = ciu;
		this.billingCity = billingCity;
		this.billingAddress = billingAddress;
		this.billingZipCode = billingZipCode;
		this.shippingCity = shippingCity;
		this.shippingAddress = shippingAddress;
		this.shippingZipCode = shippingZipCode;
		this.dispatchCityId = dispatchCityId;
		this.dispatchAddress = dispatchAddress;
		this.dispatchZipCode = dispatchZipCode;
		this.gstNo = gstNo;
		this.remarks = remarks;
		this.siteId = siteId;
		this.companyId = companyId;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "invoice_type")
	public IrnDocumentType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(IrnDocumentType invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "supply_type")
	public IrnSupplyType getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(IrnSupplyType supplyType) {
		this.supplyType = supplyType;
	}

	@Column(name = "invoice_no")
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "invoice_date")
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Column(name = "supply_date")
	public Date getSupplyDate() {
		return supplyDate;
	}

	public void setSupplyDate(Date supplyDate) {
		this.supplyDate = supplyDate;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "supply_place")
	public CountryState getSupplyState() {
		return supplyState;
	}

	public void setSupplyState(CountryState supplyState) {
		this.supplyState = supplyState;
	}

	@Column(name = "terms_and_conditions")
	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	@Column(name = "reverse_charge")
	public Boolean getReverseCharge() {
		return reverseCharge;
	}

	public void setReverseCharge(Boolean reverseCharge) {
		this.reverseCharge = reverseCharge;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "transport_mode")
	public TransportMode getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(TransportMode transportMode) {
		this.transportMode = transportMode;
	}

	@Column(name = "vehicle_no")
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "state_id")
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "ciu_id")
	public ClientImplementationUnit getCiu() {
		return ciu;
	}

	public void setCiu(ClientImplementationUnit ciu) {
		this.ciu = ciu;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "billing_city_id")
	public City getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(City billingCity) {
		this.billingCity = billingCity;
	}

	@Column(name = "billing_address")
	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	@Column(name = "billing_zip_code")
	public String getBillingZipCode() {
		return billingZipCode;
	}

	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "shipping_city_id")
	public City getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(City shippingCity) {
		this.shippingCity = shippingCity;
	}

	@Column(name = "shipping_address")
	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Column(name = "shipping_zip_code")
	public String getShippingZipCode() {
		return shippingZipCode;
	}

	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}

	@Column(name = "dispatch_city_id")
	public Integer getDispatchCityId() {
		return dispatchCityId;
	}

	public void setDispatchCityId(Integer dispatchCityId) {
		this.dispatchCityId = dispatchCityId;
	}

	@Column(name = "dispatch_address")
	public String getDispatchAddress() {
		return dispatchAddress;
	}

	public void setDispatchAddress(String dispatchAddress) {
		this.dispatchAddress = dispatchAddress;
	}

	@Column(name = "dispatch_zip_code")
	public String getDispatchZipCode() {
		return dispatchZipCode;
	}

	public void setDispatchZipCode(String dispatchZipCode) {
		this.dispatchZipCode = dispatchZipCode;
	}

	@Column(name = "gst_no")
	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "updated_by", insertable = false, updatable = false)
	public User getUpdatedByDetail() {
		return updatedByDetail;
	}

	public void setUpdatedByDetail(User updatedByDetail) {
		this.updatedByDetail = updatedByDetail;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "site_id", insertable = false, updatable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "dispatch_city_id", insertable = false, updatable = false)
	public City getDispatchCity() {
		return dispatchCity;
	}

	public void setDispatchCity(City dispatchCity) {
		this.dispatchCity = dispatchCity;
	}

}
