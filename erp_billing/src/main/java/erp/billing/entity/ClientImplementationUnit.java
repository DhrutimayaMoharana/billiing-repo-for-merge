package erp.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smm_client_implementation_unit")
public class ClientImplementationUnit {

	private Integer id;

	private Integer officeTypeId;

	private Integer parentId;

	private String code;

	private String officeName;

	private String officerName;

	private String mobileNo;

	private String landLineNo;

	private String faxNo;

	private Integer billingStateId;

	private Integer billingCityId;

	private String billingAddress;

	private String billingZipCode;

	private Integer shippingStateId;

	private Integer shippingCityId;

	private String shippingAddress;

	private String shippingZipCode;

	private String gstNo;

	private String panNo;

	private String emailId;

	private Integer companyId;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	private Date updatedOn;

	private Integer updatedBy;

	public ClientImplementationUnit() {
		super();

	}

	public ClientImplementationUnit(Integer id, Integer officeTypeId, Integer parentId, String code, String officeName,
			String officerName, String mobileNo, String landLineNo, String faxNo, Integer billingStateId,
			Integer billingCityId, String billingAddress, String billingZipCode, Integer shippingStateId,
			Integer shippingCityId, String shippingAddress, String shippingZipCode, String gstNo, String panNo,
			String emailId, Integer companyId, Boolean isActive, Date createdOn, Integer createdBy, Date updatedOn,
			Integer updatedBy) {
		super();
		this.id = id;
		this.officeTypeId = officeTypeId;
		this.parentId = parentId;
		this.code = code;
		this.officeName = officeName;
		this.officerName = officerName;
		this.mobileNo = mobileNo;
		this.landLineNo = landLineNo;
		this.faxNo = faxNo;
		this.billingStateId = billingStateId;
		this.billingCityId = billingCityId;
		this.billingAddress = billingAddress;
		this.billingZipCode = billingZipCode;
		this.shippingStateId = shippingStateId;
		this.shippingCityId = shippingCityId;
		this.shippingAddress = shippingAddress;
		this.shippingZipCode = shippingZipCode;
		this.gstNo = gstNo;
		this.panNo = panNo;
		this.emailId = emailId;
		this.companyId = companyId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	public ClientImplementationUnit(Integer id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "office_type_id")
	public Integer getOfficeTypeId() {
		return officeTypeId;
	}

	public void setOfficeTypeId(Integer officeTypeId) {
		this.officeTypeId = officeTypeId;
	}

	@Column(name = "parent_id")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "office_name")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	@Column(name = "officer_name")
	public String getOfficerName() {
		return officerName;
	}

	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}

	@Column(name = "mobile_no")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "landline_no")
	public String getLandLineNo() {
		return landLineNo;
	}

	public void setLandLineNo(String landLineNo) {
		this.landLineNo = landLineNo;
	}

	@Column(name = "fax_no")
	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	@Column(name = "billing_state_id")
	public Integer getBillingStateId() {
		return billingStateId;
	}

	public void setBillingStateId(Integer billingStateId) {
		this.billingStateId = billingStateId;
	}

	@Column(name = "billing_city_id")
	public Integer getBillingCityId() {
		return billingCityId;
	}

	public void setBillingCityId(Integer billingCityId) {
		this.billingCityId = billingCityId;
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

	@Column(name = "shipping_state_id")
	public Integer getShippingStateId() {
		return shippingStateId;
	}

	public void setShippingStateId(Integer shippingStateId) {
		this.shippingStateId = shippingStateId;
	}

	@Column(name = "shipping_city_id")
	public Integer getShippingCityId() {
		return shippingCityId;
	}

	public void setShippingCityId(Integer shippingCityId) {
		this.shippingCityId = shippingCityId;
	}

	@Column(name = "shipping_zip_code")
	public String getShippingZipCode() {
		return shippingZipCode;
	}

	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}

	@Column(name = "shipping_address")
	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Column(name = "gst_no")
	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	@Column(name = "pan_no")
	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	@Column(name = "email_id")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
