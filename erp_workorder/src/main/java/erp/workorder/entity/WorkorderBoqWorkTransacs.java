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
@Table(name = "wo_boq_work_transacs")
public class WorkorderBoqWorkTransacs implements Serializable {
	
	private static final long serialVersionUID = -4785871073770650946L;

	private Long id;
	
	private Long workorderId;
	
	private Long structureId;
	
	private Long fromChainageId;
	
	private Long toChainageId;
	
	private Boolean isActive;
	
	private Integer version;
	
	private Date createdOn;
	
	private Long createdBy;

	public WorkorderBoqWorkTransacs() {
		super();
	}

	public WorkorderBoqWorkTransacs(Long id, Long workorderId, Long structureId, Long fromChainageId, Long toChainageId,
			Boolean isActive, Integer version, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.structureId = structureId;
		this.fromChainageId = fromChainageId;
		this.toChainageId = toChainageId;
		this.isActive = isActive;
		this.version = version;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	@Column(name="structure_id")
	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	@Column(name="from_chainage_id")
	public Long getFromChainageId() {
		return fromChainageId;
	}

	public void setFromChainageId(Long fromChainageId) {
		this.fromChainageId = fromChainageId;
	}

	@Column(name="to_chainage_id")
	public Long getToChainageId() {
		return toChainageId;
	}

	public void setToChainageId(Long toChainageId) {
		this.toChainageId = toChainageId;
	}

	@Column(name="is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name="version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name="created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}
