package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wo_boq_work_locations")
public class WorkorderBoqWorkLocation implements Serializable {

	private static final long serialVersionUID = -2320211116557868037L;

	private Long id;

	private WorkorderBoqWork boqWork;

	private Long structureTypeId;

	private Long fromChainageId;

	private Long toChainageId;

	private Boolean isActive;

	private Long siteId;

	private Date modifiedOn;

	private Long modifiedBy;

	public WorkorderBoqWorkLocation() {
		super();
	}

	public WorkorderBoqWorkLocation(Long id) {
		super();
		this.id = id;
	}

	public WorkorderBoqWorkLocation(Long id, WorkorderBoqWork boqWork, Long structureTypeId, Long fromChainageId,
			Long toChainageId, Boolean isActive, Long siteId, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.boqWork = boqWork;
		this.setStructureTypeId(structureTypeId);
		this.fromChainageId = fromChainageId;
		this.toChainageId = toChainageId;
		this.isActive = isActive;
		this.siteId = siteId;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boq_work_id")
	public WorkorderBoqWork getBoqWork() {
		return boqWork;
	}

	public void setBoqWork(WorkorderBoqWork boqWork) {
		this.boqWork = boqWork;
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

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name = "modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "structure_type_id")
	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

}
