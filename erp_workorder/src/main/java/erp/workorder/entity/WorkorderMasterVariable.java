package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wo_master_variables")
public class WorkorderMasterVariable implements Serializable {

	private static final long serialVersionUID = -5931708950719440249L;

	private Integer id;

	private String name;

	private String description;

	private String sqlQuery;

	private Boolean isActive;

	private Date createdOn;

	public WorkorderMasterVariable() {
		super();
	}

	public WorkorderMasterVariable(Integer id) {
		super();
		this.id = id;
	}

	public WorkorderMasterVariable(Integer id, String name, String description, String sqlQuery, Boolean isActive,
			Date createdOn) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.sqlQuery = sqlQuery;
		this.isActive = isActive;
		this.createdOn = createdOn;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "sql_query")
	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
