package erp.billing.dto;

import java.util.Date;

public class ContractorBillingAddressDTO {
	
	private Long id;
	
	private Long contractorId;
	
	private String address;
	
	private String gstNo;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private Long createdBy;

	public ContractorBillingAddressDTO() {
		super();
	}

	public ContractorBillingAddressDTO(Long id) {
		super();
		this.id = id;
	}

	public ContractorBillingAddressDTO(Long id, Long contractorId, String address, String gstNo, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.contractorId = contractorId;
		this.address = address;
		this.gstNo = gstNo;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

}
