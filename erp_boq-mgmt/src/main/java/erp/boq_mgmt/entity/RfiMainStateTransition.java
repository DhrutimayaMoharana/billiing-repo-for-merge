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
@Table(name = "rfi_main_state_transitions")
public class RfiMainStateTransition implements Serializable {

	private static final long serialVersionUID = 4839923575797444835L;

	private Long id;

	private Long rfiMainId;

	private Integer stateId;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	private EngineState state;

	private User createdByUser;

	private RfiMain rfiMain;

	public RfiMainStateTransition() {
		super();
	}

	public RfiMainStateTransition(Long id, Long rfiMainId, Integer stateId, String remark, Boolean isActive,
			Date createdOn, Integer createdBy) {
		super();
		this.id = id;
		this.rfiMainId = rfiMainId;
		this.stateId = stateId;
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

	@Column(name = "rfi_main_id")
	public Long getRfiMainId() {
		return rfiMainId;
	}

	public void setRfiMainId(Long rfiMainId) {
		this.rfiMainId = rfiMainId;
	}

	@Column(name = "engine_state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@OneToOne
	@JoinColumn(name = "engine_state_id", insertable = false, updatable = false)
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

	@OneToOne
	@JoinColumn(name = "rfi_main_id", insertable = false, updatable = false)
	public RfiMain getRfiMain() {
		return rfiMain;
	}

	public void setRfiMain(RfiMain rfiMain) {
		this.rfiMain = rfiMain;
	}

}
