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

@Entity
@Table(name = "wo_amend_invocation_state_transitions")
public class AmendWorkorderInvocationTransitionMapping implements Serializable {

	private static final long serialVersionUID = 7019275683327982625L;

	private Long id;

	private Long amendWorkorderInvocationId;

	private Integer stateId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private EngineState state;

	private AmendWorkorderInvocation amendWorkorderInvocation;

	private User createdByUser;

	public AmendWorkorderInvocationTransitionMapping() {
		super();
	}

	public AmendWorkorderInvocationTransitionMapping(Long id, Long amendWorkorderInvocationId, Integer stateId,
			Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.amendWorkorderInvocationId = amendWorkorderInvocationId;
		this.stateId = stateId;
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

	@Column(name = "wo_amend_invocation_id")
	public Long getAmendWorkorderInvocationId() {
		return amendWorkorderInvocationId;
	}

	public void setAmendWorkorderInvocationId(Long amendWorkorderInvocationId) {
		this.amendWorkorderInvocationId = amendWorkorderInvocationId;
	}

	@Column(name = "state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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

	@OneToOne
	@JoinColumn(name = "state_id", insertable = false, updatable = false)
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
	}

	@OneToOne
	@JoinColumn(name = "wo_amend_invocation_id", insertable = false, updatable = false)
	public AmendWorkorderInvocation getAmendWorkorderInvocation() {
		return amendWorkorderInvocation;
	}

	public void setAmendWorkorderInvocation(AmendWorkorderInvocation amendWorkorderInvocation) {
		this.amendWorkorderInvocation = amendWorkorderInvocation;
	}

	@OneToOne
	@JoinColumn(name = "created_by", insertable = false, updatable = false)
	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

}
