package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "workorder_category_mapping")
public class WorkorderCategoryMapping implements Serializable {
	
	private static final long serialVersionUID = 7486284211377406629L;

	private Long id;
	
	private Long workorderId;
	
	private Long categoryId;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private Long createdBy;

	public WorkorderCategoryMapping() {
		super();
	}

	public WorkorderCategoryMapping(Long id) {
		super();
		this.id = id;
	}

	public WorkorderCategoryMapping(Long id, Long workorderId, Long categoryId,
			Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.categoryId = categoryId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	@Column(name="workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	@Column(name="category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name="is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name="created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}
