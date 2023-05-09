package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import erp.workorder.enums.EquipmentIssueCostPeriod;
import erp.workorder.enums.EquipmentIssueType;

@Entity
@Table(name = "wo_equipment_issue")
public class WorkorderEquipmentIssue implements Serializable {

	private static final long serialVersionUID = -2638911672680640931L;

	private Long id;
	
	private Long workorderId;

	private EquipmentCategory equipmentCategory;
	
	private Integer equipmentCount;
	
	private Double runningCost;
	
	private EquipmentIssueCostPeriod costPeriod;
	
	private EquipmentIssueType issueType;
	
	private Boolean isActive;
	
	private Date modifiedOn;
	
	private Long modifiedBy;

	public WorkorderEquipmentIssue() {
		super();
	}

	public WorkorderEquipmentIssue(Long id, Long workorderId, EquipmentCategory equipmentCategory, Integer equipmentCount, Double runningCost,
			EquipmentIssueCostPeriod costPeriod, EquipmentIssueType issueType, Boolean isActive, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.equipmentCategory = equipmentCategory;
		this.equipmentCount = equipmentCount;
		this.runningCost = runningCost;
		this.costPeriod = costPeriod;
		this.issueType = issueType;
		this.isActive = isActive;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "eq_category_id")
	public EquipmentCategory getEquipmentCategory() {
		return equipmentCategory;
	}

	public void setEquipmentCategory(EquipmentCategory equipmentCategory) {
		this.equipmentCategory = equipmentCategory;
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

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
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
