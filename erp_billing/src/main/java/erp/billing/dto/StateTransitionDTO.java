package erp.billing.dto;

import java.util.Date;

public class StateTransitionDTO {

	private Integer id;

	private Integer entityId;

	private Integer stateId;

	private Integer roleId;

	private Integer nextStateId;

	private Integer nextRoleId;

	private Double autoExpireHours;

	private String buttonText;

	private Integer siteId;

	private Integer companyId;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	private RoleDTO nextRole;

	private EngineStateDTO nextState;

	private Boolean isNextStateFinal;

	public StateTransitionDTO() {
		super();
	}

	public StateTransitionDTO(Integer id) {
		super();
		this.id = id;
	}

	public StateTransitionDTO(Integer id, Integer entityId, Integer stateId, Integer roleId, Integer nextStateId,
			Integer nextRoleId, Double autoExpireHours, String buttonText, Integer siteId, Integer companyId,
			Boolean isActive, Date createdOn, Integer createdBy) {
		super();
		this.id = id;
		this.entityId = entityId;
		this.stateId = stateId;
		this.buttonText = buttonText;
		this.roleId = roleId;
		this.nextStateId = nextStateId;
		this.nextRoleId = nextRoleId;
		this.autoExpireHours = autoExpireHours;
		this.siteId = siteId;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getNextStateId() {
		return nextStateId;
	}

	public void setNextStateId(Integer nextStateId) {
		this.nextStateId = nextStateId;
	}

	public Integer getNextRoleId() {
		return nextRoleId;
	}

	public void setNextRoleId(Integer nextRoleId) {
		this.nextRoleId = nextRoleId;
	}

	public Double getAutoExpireHours() {
		return autoExpireHours;
	}

	public void setAutoExpireHours(Double autoExpireHours) {
		this.autoExpireHours = autoExpireHours;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
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

	public RoleDTO getNextRole() {
		return nextRole;
	}

	public void setNextRole(RoleDTO nextRole) {
		this.nextRole = nextRole;
	}

	public EngineStateDTO getNextState() {
		return nextState;
	}

	public void setNextState(EngineStateDTO nextState) {
		this.nextState = nextState;
	}

	public Boolean getIsNextStateFinal() {
		return isNextStateFinal;
	}

	public void setIsNextStateFinal(Boolean isNextStateFinal) {
		this.isNextStateFinal = isNextStateFinal;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

}
