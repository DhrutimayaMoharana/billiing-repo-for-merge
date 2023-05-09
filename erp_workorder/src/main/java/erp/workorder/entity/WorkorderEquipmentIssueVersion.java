package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.workorder.enums.EquipmentIssueCostPeriod;
import erp.workorder.enums.EquipmentIssueType;

@Entity
@Table(name = "wo_equipment_issue_versions")
public class WorkorderEquipmentIssueVersion implements Serializable {

	private static final long serialVersionUID = -2638911672680640931L;

	private Long id;

	private Long workorderVersionId;

	private Long equipmentCategoryId;

	private Integer equipmentCount;

	private Double runningCost;

	private EquipmentIssueCostPeriod costPeriod;

	private EquipmentIssueType issueType;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WorkorderEquipmentIssueVersion() {
		super();
	}

	public WorkorderEquipmentIssueVersion(Long id, Long workorderVersionId, Long equipmentCategoryId,
			Integer equipmentCount, Double runningCost, EquipmentIssueCostPeriod costPeriod,
			EquipmentIssueType issueType, Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderVersionId = workorderVersionId;
		this.equipmentCategoryId = equipmentCategoryId;
		this.equipmentCount = equipmentCount;
		this.runningCost = runningCost;
		this.costPeriod = costPeriod;
		this.issueType = issueType;
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

	@Column(name = "eq_category_id")
	public Long getEquipmentCategoryId() {
		return equipmentCategoryId;
	}

	public void setEquipmentCategoryId(Long equipmentCategoryId) {
		this.equipmentCategoryId = equipmentCategoryId;
	}

	@Column(name = "running_cost")
	public Double getRunningCost() {
		return runningCost;
	}

	public void setRunningCost(Double runningCost) {
		this.runningCost = runningCost;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "issue_type")
	public EquipmentIssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(EquipmentIssueType issueType) {
		this.issueType = issueType;
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

	@Column(name = "wo_version_id")
	public Long getWorkorderVersionId() {
		return workorderVersionId;
	}

	public void setWorkorderVersionId(Long workorderVersionId) {
		this.workorderVersionId = workorderVersionId;
	}

	@Column(name = "equipment_count")
	public Integer getEquipmentCount() {
		return equipmentCount;
	}

	public void setEquipmentCount(Integer equipmentCount) {
		this.equipmentCount = equipmentCount;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "cost_period")
	public EquipmentIssueCostPeriod getCostPeriod() {
		return costPeriod;
	}

	public void setCostPeriod(EquipmentIssueCostPeriod costPeriod) {
		this.costPeriod = costPeriod;
	}

}
