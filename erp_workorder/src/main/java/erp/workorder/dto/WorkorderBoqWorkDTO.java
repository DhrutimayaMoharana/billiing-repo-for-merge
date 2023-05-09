package erp.workorder.dto;

import java.util.Date;
import java.util.List;

public class WorkorderBoqWorkDTO {

	private Long id;

	private Long workorderId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderBoqWorkLocationDTO> locations;

	private List<WorkorderBoqWorkQtyMappingDTO> boqWorkQty;

	private Boolean isActive;

	private Integer version;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	public WorkorderBoqWorkDTO() {
		super();
	}

	public WorkorderBoqWorkDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderBoqWorkDTO(Long id, Long workorderId, String workScope, String annexureNote,
			List<WorkorderBoqWorkLocationDTO> locations, List<WorkorderBoqWorkQtyMappingDTO> boqWorkQty,
			Boolean isActive, Integer version, Date modifiedOn, Long modifiedBy, Long siteId) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.locations = locations;
		this.boqWorkQty = boqWorkQty;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.isActive = isActive;
		this.version = version;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.siteId = siteId;
		this.workorderId = workorderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<WorkorderBoqWorkLocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<WorkorderBoqWorkLocationDTO> locations) {
		this.locations = locations;
	}

	public List<WorkorderBoqWorkQtyMappingDTO> getBoqWorkQty() {
		return boqWorkQty;
	}

	public void setBoqWorkQty(List<WorkorderBoqWorkQtyMappingDTO> boqWorkQty) {
		this.boqWorkQty = boqWorkQty;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getWorkScope() {
		return workScope;
	}

	public void setWorkScope(String workScope) {
		this.workScope = workScope;
	}

	public String getAnnexureNote() {
		return annexureNote;
	}

	public void setAnnexureNote(String annexureNote) {
		this.annexureNote = annexureNote;
	}

}
