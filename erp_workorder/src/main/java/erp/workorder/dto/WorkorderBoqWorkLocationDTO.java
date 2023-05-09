package erp.workorder.dto;

import java.util.Date;

public class WorkorderBoqWorkLocationDTO {

	private Long id;

	private Long structureTypeId;

	private Long structureId;

	private Long fromChainageId;

	private Long toChainageId;

	private Boolean isActive;

	private Long siteId;

	private Date modifiedOn;

	private Long modifiedBy;

	public WorkorderBoqWorkLocationDTO() {
		super();
	}

	public WorkorderBoqWorkLocationDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderBoqWorkLocationDTO(Long id, Long structureTypeId, Long structureId, Long fromChainageId,
			Long toChainageId, Boolean isActive, Long siteId, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.structureTypeId = structureTypeId;
		this.structureId = structureId;
		this.fromChainageId = fromChainageId;
		this.toChainageId = toChainageId;
		this.isActive = isActive;
		this.siteId = siteId;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public Long getFromChainageId() {
		return fromChainageId;
	}

	public void setFromChainageId(Long fromChainageId) {
		this.fromChainageId = fromChainageId;
	}

	public Long getToChainageId() {
		return toChainageId;
	}

	public void setToChainageId(Long toChainageId) {
		this.toChainageId = toChainageId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

}
