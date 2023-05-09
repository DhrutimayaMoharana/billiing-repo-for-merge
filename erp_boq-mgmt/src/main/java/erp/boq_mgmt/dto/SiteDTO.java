package erp.boq_mgmt.dto;

public class SiteDTO {
	
	private Long id;
	
	private String name;
	
	private Boolean isActive;
	
	private Integer companyId;

	public SiteDTO() {
		super();
	}

	public SiteDTO(Long id) {
		super();
		this.id = id;
	}

	public SiteDTO(Long id, String name, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
