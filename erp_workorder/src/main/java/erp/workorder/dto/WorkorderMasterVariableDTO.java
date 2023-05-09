package erp.workorder.dto;

import java.util.Date;

public class WorkorderMasterVariableDTO {

	private Integer id;

	private String name;

	private String description;

	private String sqlQuery;

	private Boolean isActive;

	private Date createdOn;

	public WorkorderMasterVariableDTO() {
		super();
	}

	public WorkorderMasterVariableDTO(Integer id) {
		super();
		this.id = id;
	}

	public WorkorderMasterVariableDTO(Integer id, String name, String description, String sqlQuery, Boolean isActive,
			Date createdOn) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.sqlQuery = sqlQuery;
		this.isActive = isActive;
		this.createdOn = createdOn;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
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

}
