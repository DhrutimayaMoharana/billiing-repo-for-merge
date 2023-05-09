package erp.billing.dto;

import java.util.Date;

public class ChainageTraverseDTO {

	private Long id;
	
	private String name;
	
	private Boolean isActive;
	
	private Long siteId;
	
	private Integer companyId;
	
	private Date modifiedOn;
	
	private Long modifiedBy;

	public ChainageTraverseDTO() {
		super();
	}

	public ChainageTraverseDTO(Long id) {
		super();
		this.id = id;
	}

	public ChainageTraverseDTO(Long id, String name, Boolean isActive, Long siteId, Integer companyId, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.siteId = siteId;
		this.companyId = companyId;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

}
