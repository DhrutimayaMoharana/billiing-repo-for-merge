package erp.workorder.dto;

import java.util.Date;

public class StructureTypeDTO {
	
	private Long id;
	
	private String code;
	
	private String name;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private Long createdBy;
	
	private Date modifiedOn;
	
	private Long modifiedBy;
	
	private Integer companyId;
	
	private Long siteId;

	public StructureTypeDTO() {
		super();
	}

	public StructureTypeDTO(Long id) {
		super();
		this.id = id;
	}

	public StructureTypeDTO(Long id, String code, String name, Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn,
			Long modifiedBy, Integer companyId) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
