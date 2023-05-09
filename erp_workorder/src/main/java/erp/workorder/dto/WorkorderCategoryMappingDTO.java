package erp.workorder.dto;

import java.util.Date;

public class WorkorderCategoryMappingDTO {
	
	private Long id;
	
	private Long workorderId;
	
	private Long categoryId;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private Long createdBy;
	
	private CategoryItemDTO category;

	public WorkorderCategoryMappingDTO() {
		super();
	}

	public WorkorderCategoryMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderCategoryMappingDTO(Long id, Long workorderId, Long categoryId, Boolean isActive,
			Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.categoryId = categoryId;
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

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public CategoryItemDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryItemDTO category) {
		this.category = category;
	}

}
