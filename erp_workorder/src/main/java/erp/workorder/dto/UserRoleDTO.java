package erp.workorder.dto;

import java.util.Date;

public class UserRoleDTO {

	private Integer id;
	
	private String name;
	
	private String alias;
	
	private Integer companyId;
	
	private Boolean isActive;
	
	private Date createdOn;
	
	private Integer createdBy;

	public UserRoleDTO() {
		super();
	}

	public UserRoleDTO(Integer id) {
		super();
		this.id = id;
	}

	public UserRoleDTO(Integer id, String name, String alias, Integer companyId, Boolean isActive, Date createdOn,
			Integer createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.companyId = companyId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
