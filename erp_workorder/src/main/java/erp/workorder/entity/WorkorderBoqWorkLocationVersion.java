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
@Table(name = "wo_boq_work_location_version")
public class WorkorderBoqWorkLocationVersion implements Serializable {

	private static final long serialVersionUID = -2320211116557868037L;

	private Long id;

	private Long boqWorkVersionId;

	private Long structureTypeId;

	private Long structureId;

	private Long fromChainageId;

	private Long toChainageId;

	private Boolean isActive;

	private Long siteId;

	private Date createdOn;

	private Long createdBy;

	public WorkorderBoqWorkLocationVersion() {
		super();
	}

	public WorkorderBoqWorkLocationVersion(Long id) {
		super();
		this.id = id;
	}

	public WorkorderBoqWorkLocationVersion(Long id, Long boqWorkVersionId, Long structureTypeId, Long structureId,
			Long fromChainageId, Long toChainageId, Boolean isActive, Long siteId, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.boqWorkVersionId = boqWorkVersionId;
		this.structureTypeId = structureTypeId;
		this.structureId = structureId;
		this.fromChainageId = fromChainageId;
		this.toChainageId = toChainageId;
		this.isActive = isActive;
		this.siteId = siteId;
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

	@Column(name = "boq_work_id")
	public Long getBoqWorkVersionId() {
		return boqWorkVersionId;
	}

	public void setBoqWorkVersionId(Long boqWorkVersionId) {
		this.boqWorkVersionId = boqWorkVersionId;
	}

	@Column(name = "structure_type_id")
	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	@Column(name = "from_chainage_id")
	public Long getFromChainageId() {
		return fromChainageId;
	}

	public void setFromChainageId(Long fromChainageId) {
		this.fromChainageId = fromChainageId;
	}

	@Column(name = "to_chainage_id")
	public Long getToChainageId() {
		return toChainageId;
	}

	public void setToChainageId(Long toChainageId) {
		this.toChainageId = toChainageId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "structure_id")
	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
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
