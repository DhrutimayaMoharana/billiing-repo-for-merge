package erp.boq_mgmt.dto;

public class UnitDTO {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private UnitTypeDTO type;
	
	private Boolean isActive;
	
	private Integer companyId;
	
	private Long siteId;
	private Long createdBy;
	

	public UnitDTO() {
		super();
	}

	public UnitDTO(Long id) {
		super();
		this.id = id;
	}

	public UnitDTO(Long id, String name, String description, UnitTypeDTO type, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.isActive = isActive;
		this.companyId = companyId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UnitTypeDTO getType() {
		return type;
	}

	public void setType(UnitTypeDTO type) {
		this.type = type;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
