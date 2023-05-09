package erp.workorder.dto;

import java.util.Date;

public class MaterialGroupDTO {

	private Long id;

	private String name;

	private Boolean isActive;

	private Date createdOn;

	private Integer companyId;

	public MaterialGroupDTO() {
		super();
	}

	public MaterialGroupDTO(Long id) {
		super();
		this.id = id;
	}

	public MaterialGroupDTO(Long id, String name, Boolean isActive, Date createdOn, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.createdOn = createdOn;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
