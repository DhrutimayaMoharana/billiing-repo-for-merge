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
@Table(name = "wo_labour_work_item_versions")
public class WorkorderLabourWorkItemMappingVersion implements Serializable {

	private static final long serialVersionUID = 2329647704102485713L;

	private Long id;

	private Long workorderLabourWorkVersionId;

	private Integer labourTypeId;

	private Integer labourCount;

	private String description;

	private Double rate;

	private Short unitId;

	private Long fullDurationThreshold;

	private Long halfDurationThreshold;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WorkorderLabourWorkItemMappingVersion() {
		super();
	}

	public WorkorderLabourWorkItemMappingVersion(Long id, Long workorderLabourWorkVersionId, Integer labourTypeId,
			Integer labourCount, String description, Double rate, Short unitId, Long fullDurationThreshold,
			Long halfDurationThreshold, String remark, Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderLabourWorkVersionId = workorderLabourWorkVersionId;
		this.labourTypeId = labourTypeId;
		this.labourCount = labourCount;
		this.description = description;
		this.rate = rate;
		this.unitId = unitId;
		this.fullDurationThreshold = fullDurationThreshold;
		this.halfDurationThreshold = halfDurationThreshold;
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

	@Column(name = "labour_work_version_id")
	public Long getWorkorderLabourWorkVersionId() {
		return workorderLabourWorkVersionId;
	}

	public void setWorkorderLabourWorkVersionId(Long workorderLabourWorkVersionId) {
		this.workorderLabourWorkVersionId = workorderLabourWorkVersionId;
	}

	@Column(name = "labour_type_id")
	public Integer getLabourTypeId() {
		return labourTypeId;
	}

	public void setLabourTypeId(Integer labourTypeId) {
		this.labourTypeId = labourTypeId;
	}

	@Column(name = "labour_count")
	public Integer getLabourCount() {
		return labourCount;
	}

	public void setLabourCount(Integer labourCount) {
		this.labourCount = labourCount;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "unit_id")
	public Short getUnitId() {
		return unitId;
	}

	public void setUnitId(Short unitId) {
		this.unitId = unitId;
	}

	@Column(name = "full_duration_threshold")
	public Long getFullDurationThreshold() {
		return fullDurationThreshold;
	}

	public void setFullDurationThreshold(Long fullDurationThreshold) {
		this.fullDurationThreshold = fullDurationThreshold;
	}

	@Column(name = "half_duration_threshold")
	public Long getHalfDurationThreshold() {
		return halfDurationThreshold;
	}

	public void setHalfDurationThreshold(Long halfDurationThreshold) {
		this.halfDurationThreshold = halfDurationThreshold;
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

}
