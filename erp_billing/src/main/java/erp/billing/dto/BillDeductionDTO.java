package erp.billing.dto;

import java.util.Date;

public class BillDeductionDTO {

	private Integer id;

	private String name;

	private Boolean isActive;

	private Integer companyId;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	public BillDeductionDTO() {
		super();
	}

	public BillDeductionDTO(Integer id) {
		super();
		this.id = id;
	}

	public BillDeductionDTO(Integer id, String name, Boolean isActive, Integer companyId, Date createdOn,
			Long createdBy, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.companyId = companyId;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.setModifiedOn(modifiedOn);
		this.setModifiedBy(modifiedBy);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
