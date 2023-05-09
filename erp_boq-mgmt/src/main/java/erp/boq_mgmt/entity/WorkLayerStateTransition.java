package erp.boq_mgmt.entity;

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
@Table(name = "work_layer_state_transition")
public class WorkLayerStateTransition implements Serializable {

	private static final long serialVersionUID = -2198976131199873703L;

	private Long id;

	private Integer workLayerId;

	private EngineState state;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private WorkLayer workLayer;

	private User createdByUser;

	public WorkLayerStateTransition() {
		super();
	}

	public WorkLayerStateTransition(Long id, Integer workLayerId, EngineState state, String remark, Boolean isActive,
			Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workLayerId = workLayerId;
		this.state = state;
		this.remark = remark;
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

	@Column(name = "work_layer_id")
	public Integer getWorkLayerId() {
		return workLayerId;
	}

	public void setWorkLayerId(Integer workLayerId) {
		this.workLayerId = workLayerId;
	}

	@OneToOne
	@JoinColumn(name = "state_id")
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
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

	@OneToOne
	@JoinColumn(name = "work_layer_id", insertable = false, updatable = false)
	public WorkLayer getWorkLayer() {
		return workLayer;
	}

	public void setWorkLayer(WorkLayer workLayer) {
		this.workLayer = workLayer;
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
