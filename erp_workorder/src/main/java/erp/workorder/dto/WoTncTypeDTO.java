package erp.workorder.dto;

import java.util.Date;

public class WoTncTypeDTO {

	private Long id;

	private String name;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Integer companyId;

	// extra fields

	private Long siteId;

	public WoTncTypeDTO() {
		super();
	}

	public WoTncTypeDTO(Long id) {
		super();
		this.id = id;
	}

	public WoTncTypeDTO(Long id, String name, Boolean isActive, Date createdOn, Long createdBy, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
