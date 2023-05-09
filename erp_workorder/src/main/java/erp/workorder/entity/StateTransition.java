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
@Table(name = "state_transitions")
public class StateTransition implements Serializable {

	private static final long serialVersionUID = -7378482195266278708L;

	private Integer id;

	private Integer entityId;

	private Integer stateId;

	private Integer roleId;

	private Integer nextStateId;

	private Integer nextRoleId;

	private Double autoExpireHours;

	private Integer siteId;

	private Integer companyId;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	public StateTransition() {
		super();
	}

	public StateTransition(Integer id) {
		super();
		this.id = id;
	}

	public StateTransition(Integer id, Integer entityId, Integer stateId, Integer roleId, Integer nextStateId,
			Integer nextRoleId, Double autoExpireHours, Integer siteId, Integer companyId, Boolean isActive,
			Date createdOn, Integer createdBy) {
		super();
		this.id = id;
		this.entityId = entityId;
		this.stateId = stateId;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "entity_id")
	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	@Column(name = "state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Column(name = "role_id")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "next_state_id")
	public Integer getNextStateId() {
		return nextStateId;
	}

	public void setNextStateId(Integer nextStateId) {
		this.nextStateId = nextStateId;
	}

	@Column(name = "next_role_id")
	public Integer getNextRoleId() {
		return nextRoleId;
	}

	public void setNextRoleId(Integer nextRoleId) {
		this.nextRoleId = nextRoleId;
	}

	@Column(name = "auto_expire_hrs")
	public Double getAutoExpireHours() {
		return autoExpireHours;
	}

	public void setAutoExpireHours(Double autoExpireHours) {
		this.autoExpireHours = autoExpireHours;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

}
