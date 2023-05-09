package erp.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wo_labour")
public class WorkorderLabour {

	private Integer id;

	private String code;

	private String name;

	private WorkorderLabourDepartment department;

	private WorkorderLabourDesignation designation;

	private WorkorderLabourType type;

	private Integer siteId;

	private Date fromDate;

	private Date toDate;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	private Date modifiedOn;

	private Integer modifiedBy;

	public WorkorderLabour() {
		super();
	}

	public WorkorderLabour(Integer id, String code, String name, WorkorderLabourDepartment department,
			WorkorderLabourDesignation designation, WorkorderLabourType type, Integer siteId, Date fromDate,
			Date toDate, Boolean isActive, Date createdOn, Integer createdBy, Date modifiedOn, Integer modifiedBy) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.department = department;
		this.designation = designation;
		this.type = type;
		this.siteId = siteId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToOne
	@JoinColumn(name = "department_id")
	public WorkorderLabourDepartment getDepartment() {
		return department;
	}

	public void setDepartment(WorkorderLabourDepartment department) {
		this.department = department;
	}

	@OneToOne
	@JoinColumn(name = "designation_id")
	public WorkorderLabourDesignation getDesignation() {
		return designation;
	}

	public void setDesignation(WorkorderLabourDesignation designation) {
		this.designation = designation;
	}

	@OneToOne
	@JoinColumn(name = "type_id")
	public WorkorderLabourType getType() {
		return type;
	}

	public void setType(WorkorderLabourType type) {
		this.type = type;
	}

	@Column(name = "site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name = "from_date")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name = "to_Date")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	@Column(name = "created_by")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name = "modified_by")
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
