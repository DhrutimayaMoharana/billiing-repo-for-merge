package erp.workorder.notificator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nw_user_notification_opt_out")
public class UserNotificationOptOut {

	private Long id;

	private Integer userId;

	private Long ruleActionId;

	private Boolean status;

	private Boolean isActive;

	private Date updatedOn;

	public UserNotificationOptOut() {
		super();
	}

	public UserNotificationOptOut(Long id, Integer userId, Long ruleActionId, Boolean status, Boolean isActive,
			Date updatedOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.ruleActionId = ruleActionId;
		this.status = status;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "rule_action_id")
	public Long getRuleActionId() {
		return ruleActionId;
	}

	public void setRuleActionId(Long ruleActionId) {
		this.ruleActionId = ruleActionId;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
