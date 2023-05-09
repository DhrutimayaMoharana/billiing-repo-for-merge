package erp.workorder.dto;

import java.util.Date;

public class CategoryContractorMappingDTO {

	private Long id;
	
	private Long categoryId;
	
	private Long contractorId;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private Long createdBy;

	public CategoryContractorMappingDTO() {
		super();
	}

	public CategoryContractorMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public CategoryContractorMappingDTO(Long id, Long categoryId, Long contractorId, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.contractorId = contractorId;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
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

}
