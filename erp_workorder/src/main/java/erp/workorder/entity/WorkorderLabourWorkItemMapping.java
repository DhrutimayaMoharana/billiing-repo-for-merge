package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "wo_labour_work_items")
public class WorkorderLabourWorkItemMapping implements Serializable {

	private static final long serialVersionUID = 2329647704102485713L;

	private Long id;

	private Long workorderLabourWorkId;

	private Integer labourTypeId;

	private Integer labourCount;

	private String description;

	private Double rate;

	private UnitMaster unit;

	private Long fullDurationThreshold;

	private Long halfDurationThreshold;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private WorkorderLabourWork workorderLabourWork;

	private WorkorderLabourType labourType;

	public WorkorderLabourWorkItemMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkorderLabourWorkItemMapping(Long id) {
		super();
		this.id = id;
	}

	public WorkorderLabourWorkItemMapping(Long id, Long workorderLabourWorkId, Integer labourTypeId,
			Integer labourCount, String description, Double rate, UnitMaster unit, Long fullDurationThreshold,
			Long halfDurationThreshold, String remark, Boolean isActive, Date createdOn, Long createdBy,
			Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderLabourWorkId = workorderLabourWorkId;
		this.labourTypeId = labourTypeId;
		this.labourCount = labourCount;
		this.description = description;
		this.rate = rate;
		this.unit = unit;
		this.fullDurationThreshold = fullDurationThreshold;
		this.halfDurationThreshold = halfDurationThreshold;
		this.remark = remark;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@Column(name = "wo_labour_work_id")
	public Long getWorkorderLabourWorkId() {
		return workorderLabourWorkId;
	}

	public void setWorkorderLabourWorkId(Long workorderLabourWorkId) {
		this.workorderLabourWorkId = workorderLabourWorkId;
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

	@OneToOne
	@JoinColumn(name = "unit_id")
	public UnitMaster getUnit() {
		return unit;
	}

	public void setUnit(UnitMaster unit) {
		this.unit = unit;
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

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name = "modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@OneToOne
	@JoinColumn(name = "wo_labour_work_id", insertable = false, updatable = false)
	public WorkorderLabourWork getWorkorderLabourWork() {
		return workorderLabourWork;
	}

	public void setWorkorderLabourWork(WorkorderLabourWork workorderLabourWork) {
		this.workorderLabourWork = workorderLabourWork;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "labour_type_id", insertable = false, updatable = false)
	public WorkorderLabourType getLabourType() {
		return labourType;
	}

	public void setLabourType(WorkorderLabourType labourType) {
		this.labourType = labourType;
	}

}
