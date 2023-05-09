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
@Table(name = "wo_consultant_work_item_versions")
public class WorkorderConsultantWorkItemMappingVersion implements Serializable {

	private static final long serialVersionUID = 3214813669922967435L;

	private Long id;

	private Long workorderConsultantWorkVersionId;

	private String categoryDescription;

	private String description;

	private Long chainageId;

	private Double quantity;

	private Double rate;

	private Long unitId;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WorkorderConsultantWorkItemMappingVersion() {
		super();
	}

	public WorkorderConsultantWorkItemMappingVersion(Long id, Long workorderConsultantWorkVersionId,
			String categoryDescription, String description, Long chainageId, Double quantity, Double rate, Long unitId,
			String remark, Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderConsultantWorkVersionId = workorderConsultantWorkVersionId;
		this.categoryDescription = categoryDescription;
		this.description = description;
		this.chainageId = chainageId;
		this.quantity = quantity;
		this.rate = rate;
		this.unitId = unitId;
		this.remark = remark;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "wo_consultant_work_version_id")
	public Long getWorkorderConsultantWorkVersionId() {
		return workorderConsultantWorkVersionId;
	}

	public void setWorkorderConsultantWorkVersionId(Long workorderConsultantWorkVersionId) {
		this.workorderConsultantWorkVersionId = workorderConsultantWorkVersionId;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "unit_id")
	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "chainage_id")
	public Long getChainageId() {
		return chainageId;
	}

	public void setChainageId(Long chainageId) {
		this.chainageId = chainageId;
	}

	@Column(name = "category_description")
	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}
