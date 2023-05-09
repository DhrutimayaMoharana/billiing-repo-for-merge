package erp.boq_mgmt.dto;

import java.util.Date;

public class EntityStateMapDTO {

	private Integer id;

	private Integer entityId;

	private Integer stateId;

	private String stateName;

	private Integer stateActionId;

	private Boolean isInitial;

	private Boolean isFinal;

	private Boolean isNecessary;

	private Boolean isEditable;

	private Boolean isDeletable;

	private Boolean maintainVersion;

	private Integer prioritySequence;

	private Integer companyId;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	public EntityStateMapDTO() {
		super();
	}

	public EntityStateMapDTO(Integer id) {
		super();
		this.id = id;
	}

	public EntityStateMapDTO(Integer id, Integer entityId, Integer stateId, String stateName, Integer stateActionId,
			Boolean isInitial, Boolean isFinal, Boolean isNecessary, Boolean isEditable, Boolean isDeletable,
			Boolean maintainVersion, Integer prioritySequence, Integer companyId, Boolean isActive, Date createdOn,
			Integer createdBy) {
		super();
		this.id = id;
		this.entityId = entityId;
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateActionId = stateActionId;
		this.isInitial = isInitial;
		this.isFinal = isFinal;
		this.isNecessary = isNecessary;
		this.isEditable = isEditable;
		this.isDeletable = isDeletable;
		this.maintainVersion = maintainVersion;
		this.prioritySequence = prioritySequence;
		this.companyId = companyId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Boolean getIsInitial() {
		return isInitial;
	}

	public void setIsInitial(Boolean isInitial) {
		this.isInitial = isInitial;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Integer getPrioritySequence() {
		return prioritySequence;
	}

	public void setPrioritySequence(Integer prioritySequence) {
		this.prioritySequence = prioritySequence;
	}

	public Boolean getMaintainVersion() {
		return maintainVersion;
	}

	public void setMaintainVersion(Boolean maintainVersion) {
		this.maintainVersion = maintainVersion;
	}

	public Boolean getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(Boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

	public Integer getStateActionId() {
		return stateActionId;
	}

	public void setStateActionId(Integer stateActionId) {
		this.stateActionId = stateActionId;
	}

	public Boolean getIsNecessary() {
		return isNecessary;
	}

	public void setIsNecessary(Boolean isNecessary) {
		this.isNecessary = isNecessary;
	}

}
