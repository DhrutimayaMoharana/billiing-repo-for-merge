package erp.workorder.dto;

import java.util.Date;

public class ContractorDTO {
	
	private Long id;
	
	private String name;
	
	private String gstNo;
	
	private String panNo;
	
	private String email;
	
	private String address;
	
	private String phoneNo;
	
	private String vendorCode;
	
	private Date createdOn;
	
	private Boolean isActive;
	
	private Integer companyId;

	public ContractorDTO() {
		super();
	}

	public ContractorDTO(Long id) {
		super();
		this.id = id;
	}

	public ContractorDTO(Long id, String name, String gstNo, String panNo, String email, String address, String phoneNo,
			String vendorCode, Date createdOn, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.gstNo = gstNo;
		this.panNo = panNo;
		this.email = email;
		this.address = address;
		this.phoneNo = phoneNo;
		this.vendorCode = vendorCode;
		this.createdOn = createdOn;
		this.isActive = isActive;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
