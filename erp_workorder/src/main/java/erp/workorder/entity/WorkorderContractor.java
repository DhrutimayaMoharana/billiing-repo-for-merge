package erp.workorder.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "workorder_contractor")
public class WorkorderContractor {
	
	private Long id;
	
	private Long workorderId;
	
	private Long contractorId;
	
	private ContractorBankDetail bankDetail;
	
	private ContractorBillingAddress billingAddress;
	
	private Boolean isActive;
	
	private Date modifiedOn;
	
	private Long modifiedBy;

	public WorkorderContractor() {
		super();
	}

	public WorkorderContractor(Long id) {
		super();
		this.id = id;
	}

	public WorkorderContractor(Long id, Long workorderId, Long contractorId, ContractorBankDetail bankDetail,
			ContractorBillingAddress billingAddress, Boolean isActive, Date modifiedOn, Long modifiedBy) {
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

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="contractor_id")
	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contractor_bank_detail_id")
	public ContractorBankDetail getBankDetail() {
		return bankDetail;
	}

	public void setBankDetail(ContractorBankDetail bankDetail) {
		this.bankDetail = bankDetail;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contractor_billing_address_id")
	public ContractorBillingAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(ContractorBillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	@Column(name="is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name="modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name="modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}
	

}
