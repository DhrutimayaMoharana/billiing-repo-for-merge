package erp.billing.dto;

import java.util.Date;

public class BillDeductionMappingDTO {

	private Long id;

	private Long billId;

	private BillDeductionDTO deduction;

	private Boolean isPercentage;

	private Double value;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;
	
	// extra fields
	
	private Long siteId;
	
	private Double amount;
	
	private Integer companyId;

	public BillDeductionMappingDTO() {
		super();
	}

	public BillDeductionMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public BillDeductionMappingDTO(Long id, Long billId, BillDeductionDTO deduction, Boolean isPercentage, Double value,
			Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.billId = billId;
		this.deduction = deduction;
		this.isPercentage = isPercentage;
		this.value = value;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public BillDeductionDTO getDeduction() {
		return deduction;
	}

	public void setDeduction(BillDeductionDTO deduction) {
		this.deduction = deduction;
	}

	public Boolean getIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}
