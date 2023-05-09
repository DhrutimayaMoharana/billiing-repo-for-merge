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
@Table(name = "category_contractor_mapping")
public class CategoryContractorMapping implements Serializable {
	
	private static final long serialVersionUID = 5514395135176646174L;

	private Long id;
	
	private Long categoryId;
	
	private Long contractorId;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private Long createdBy;

	public CategoryContractorMapping() {
		super();
	}

	public CategoryContractorMapping(Long id) {
		super();
		this.id = id;
	}

	public CategoryContractorMapping(Long id, Long categoryId, Long contractorId, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.contractorId = contractorId;
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

	@Column(name="category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name="contractor_id")
	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
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
