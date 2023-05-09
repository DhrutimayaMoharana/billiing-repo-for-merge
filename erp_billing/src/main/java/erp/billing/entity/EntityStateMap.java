package erp.billing.entity;

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
@Table(name = "entity_state_mapping")
public class EntityStateMap implements Serializable {

	private static final long serialVersionUID = -3771753928900997534L;

	private Integer id;

	private Integer entityId;

	private Integer stateId;

	private EngineState state;

	private Boolean isInitial;

	private Boolean isFinal;

	private Boolean isEditable;

	private Integer prioritySequence;

	private Integer companyId;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	public EntityStateMap() {
		super();
	}

	public EntityStateMap(Integer id) {
		super();
		this.id = id;
	}

	public EntityStateMap(Integer id, Integer entityId, Integer stateId, Boolean isInitial, Boolean isFinal,
			Boolean isEditable, Integer prioritySequence, Integer companyId, Boolean isActive, Date createdOn,
			Integer createdBy) {
		super();
		this.id = id;
		this.entityId = entityId;
		this.stateId = stateId;
		this.isInitial = isInitial;
		this.isFinal = isFinal;
		this.isEditable = isEditable;
		this.prioritySequence = prioritySequence;
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

	@Column(name = "is_final")
	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
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

	@OneToOne
	@JoinColumn(name = "state_id", insertable = false, updatable = false)
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
	}

	@Column(name = "is_initial")
	public Boolean getIsInitial() {
		return isInitial;
	}

	public void setIsInitial(Boolean isInitial) {
		this.isInitial = isInitial;
	}

	@Column(name = "is_editable")
	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	@Column(name = "priority_sequence")
	public Integer getPrioritySequence() {
		return prioritySequence;
	}

	public void setPrioritySequence(Integer prioritySequence) {
		this.prioritySequence = prioritySequence;
	}
}
