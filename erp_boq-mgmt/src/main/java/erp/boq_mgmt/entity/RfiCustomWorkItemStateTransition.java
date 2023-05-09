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
@Table(name = "rfi_custom_work_item_state_transition")
public class RfiCustomWorkItemStateTransition implements Serializable {

	private static final long serialVersionUID = -2198976131199873703L;

	private Long id;

	private Long rfiCustomWorkItemId;

	private EngineState state;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private User createdByUser;

	private RfiCustomWorkItems rfiCustomWorkItem;

	public RfiCustomWorkItemStateTransition() {
		super();
	}

	public RfiCustomWorkItemStateTransition(Long id, Long rfiCustomWorkItemId, EngineState state, String remark,
			Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.rfiCustomWorkItemId = rfiCustomWorkItemId;
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

	@Column(name = "rfi_custom_work_item_id")
	public Long getRfiCustomWorkItemId() {
		return rfiCustomWorkItemId;
	}

	public void setRfiCustomWorkItemId(Long rfiCustomWorkItemId) {
		this.rfiCustomWorkItemId = rfiCustomWorkItemId;
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
	@JoinColumn(name = "created_by", insertable = false, updatable = false)
	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	@OneToOne
	@JoinColumn(name = "rfi_custom_work_item_id", insertable = false, updatable = false)
	public RfiCustomWorkItems getRfiCustomWorkItem() {
		return rfiCustomWorkItem;
	}

	public void setRfiCustomWorkItem(RfiCustomWorkItems rfiCustomWorkItem) {
		this.rfiCustomWorkItem = rfiCustomWorkItem;
	}

}
