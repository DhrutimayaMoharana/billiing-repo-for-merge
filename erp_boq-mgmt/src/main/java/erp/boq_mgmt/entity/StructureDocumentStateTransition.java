package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "structure_doc_state_transition")
public class StructureDocumentStateTransition implements Serializable {

	private static final long serialVersionUID = -2198976131199873703L;

	private Long id;

	private Long structureDocumentId;

	private Integer stateId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public StructureDocumentStateTransition() {
		super();
	}

	public StructureDocumentStateTransition(Long id, Long structureDocumentId, Integer stateId, Boolean isActive,
			Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.structureDocumentId = structureDocumentId;
		this.stateId = stateId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "structure_doc_id")
	public Long getStructureDocumentId() {
		return structureDocumentId;
	}

	public void setStructureDocumentId(Long structureDocumentId) {
		this.structureDocumentId = structureDocumentId;
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

}
