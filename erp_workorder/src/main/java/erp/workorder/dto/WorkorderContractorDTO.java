package erp.workorder.dto;

import java.util.Date;

public class WorkorderContractorDTO {
	
	private Long id;
	
	private Long workorderId;
	
	private Long contractorId;
	
	private ContractorBankDetailDTO bankDetail;
	
	private ContractorBillingAddressDTO billingAddress;
	
	private Boolean isActive;
	
	private Date modifiedOn;
	
	private Long modifiedBy;

	public WorkorderContractorDTO() {
		super();
	}

	public WorkorderContractorDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderContractorDTO(Long id, Long workorderId, Long contractorId, ContractorBankDetailDTO bankDetail,
			ContractorBillingAddressDTO billingAddress, Boolean isActive, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.contractorId = contractorId;
		this.bankDetail = bankDetail;
		this.billingAddress = billingAddress;
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	public ContractorBankDetailDTO getBankDetail() {
		return bankDetail;
	}

	public void setBankDetail(ContractorBankDetailDTO bankDetail) {
		this.bankDetail = bankDetail;
	}

	public ContractorBillingAddressDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(ContractorBillingAddressDTO billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

}
