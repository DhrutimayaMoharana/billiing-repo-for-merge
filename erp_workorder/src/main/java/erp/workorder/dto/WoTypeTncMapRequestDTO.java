package erp.workorder.dto;

import java.util.Date;
import java.util.List;

public class WoTypeTncMapRequestDTO {

	private Long id;

	private Long woTypeId;

	private List<Long> woTncIds;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Integer companyId;

	private Long siteId;

	public WoTypeTncMapRequestDTO() {
		super();
	}

	public WoTypeTncMapRequestDTO(Long id, Long woTypeId, List<Long> woTncIds, Boolean isActive, Date createdOn,
			Long createdBy, Integer companyId, Long siteId) {
		super();
		this.id = id;
		this.woTypeId = woTypeId;
		this.woTncIds = woTncIds;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.companyId = companyId;
		this.siteId = siteId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWoTypeId() {
		return woTypeId;
	}

	public void setWoTypeId(Long woTypeId) {
		this.woTypeId = woTypeId;
	}

	public List<Long> getWoTncIds() {
		return woTncIds;
	}

	public void setWoTncIds(List<Long> woTncIds) {
		this.woTncIds = woTncIds;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

}
