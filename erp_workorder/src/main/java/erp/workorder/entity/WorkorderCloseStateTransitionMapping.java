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
@Table(name = "wo_close_state_transitions")
public class WorkorderCloseStateTransitionMapping implements Serializable {

	private static final long serialVersionUID = 7019275683327982625L;

	private Long id;

	private Long workorderCloseId;

	private Integer stateId;

	private String remarks;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private EngineState state;

	private WorkorderClose workorderClose;

	private User createdByUser;

	public WorkorderCloseStateTransitionMapping() {
		super();
	}

	public WorkorderCloseStateTransitionMapping(Long id, Long workorderCloseId, Integer stateId, String remarks,
			Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderCloseId = workorderCloseId;
		this.stateId = stateId;
		this.remarks = remarks;
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

	@Column(name = "wo_close_id")
	public Long getWorkorderCloseId() {
		return workorderCloseId;
	}

	public void setWorkorderCloseId(Long workorderCloseId) {
		this.workorderCloseId = workorderCloseId;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	@JoinColumn(name = "wo_close_id", insertable = false, updatable = false)
	public WorkorderClose getWorkorderClose() {
		return workorderClose;
	}

	public void setWorkorderClose(WorkorderClose workorderClose) {
		this.workorderClose = workorderClose;
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
	@JoinColumn(name = "created_by", insertable = false, updatable = false)
	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

}
