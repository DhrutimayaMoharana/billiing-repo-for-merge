package erp.workorder.dto;

import java.util.Date;

import erp.workorder.enums.EquipmentIssueCostPeriod;
import erp.workorder.enums.EquipmentIssueType;

public class WorkorderEquipmentIssueDTO {

	private Long id;
	
	private Long workorderId;

	private EquipmentCategoryDTO equipmentCategory;
	
	private Integer equipmentCount;
	
	private Double runningCost;
	
	private EquipmentIssueCostPeriod costPeriod;
	
	private EquipmentIssueType issueType;
	
	private Boolean isActive;
	
	private Date modifiedOn;
	
	private Long modifiedBy;
	
//	extra fields
	
	private Long siteId;
	
	private Integer companyId;

	public WorkorderEquipmentIssueDTO() {
		super();
	}

	public WorkorderEquipmentIssueDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderEquipmentIssueDTO(Long id, Long workorderId, EquipmentCategoryDTO equipmentCategory,
			Integer equipmentCount, Double runningCost, EquipmentIssueCostPeriod costPeriod,
			EquipmentIssueType issueType, Boolean isActive, Date modifiedOn, Long modifiedBy) {
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

	public EquipmentCategoryDTO getEquipmentCategory() {
		return equipmentCategory;
	}

	public void setEquipmentCategory(EquipmentCategoryDTO equipmentCategory) {
		this.equipmentCategory = equipmentCategory;
	}

	public Integer getEquipmentCount() {
		return equipmentCount;
	}

	public void setEquipmentCount(Integer equipmentCount) {
		this.equipmentCount = equipmentCount;
	}

	public Double getRunningCost() {
		return runningCost;
	}

	public void setRunningCost(Double runningCost) {
		this.runningCost = runningCost;
	}

	public EquipmentIssueCostPeriod getCostPeriod() {
		return costPeriod;
	}

	public void setCostPeriod(EquipmentIssueCostPeriod costPeriod) {
		this.costPeriod = costPeriod;
	}

	public EquipmentIssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(EquipmentIssueType issueType) {
		this.issueType = issueType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
