package erp.workorder.dto;

import java.util.Date;

public class WoTypeTncMappingDTO {

	private Long id;

	private Long woTypeId;

	private WoTncDTO woTnc;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Integer companyId;

	// extra fields

	private Long siteId;

	public WoTypeTncMappingDTO() {
		super();
	}

	public WoTypeTncMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public WoTypeTncMappingDTO(Long id, Long woTypeId, WoTncDTO woTnc, Boolean isActive, Date createdOn, Long createdBy,
			Integer companyId) {
		super();
		this.id = id;
		this.woTypeId = woTypeId;
		this.woTnc = woTnc;
		this.setIsActive(isActive);
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.setCompanyId(companyId);
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

	public WoTncDTO getWoTnc() {
		return woTnc;
	}

	public void setWoTnc(WoTncDTO woTnc) {
		this.woTnc = woTnc;
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

}
