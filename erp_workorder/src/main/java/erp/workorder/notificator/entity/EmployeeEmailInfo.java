package erp.workorder.notificator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class EmployeeEmailInfo {

	private Integer id;

	private String name;

	private Integer roleId;

	private Integer departmentId;

	private String emailAddress;

	private Boolean isActive;

	private Integer companyId;

	private Long siteId;

	public EmployeeEmailInfo() {
		super();
	}

	public EmployeeEmailInfo(Integer id, String name, Integer roleId, Integer departmentId, String emailAddress,
			Boolean isActive, Integer companyId, Long siteId) {
		super();
		this.id = id;
		this.name = name;
		this.roleId = roleId;
		this.departmentId = departmentId;
		this.emailAddress = emailAddress;
		this.isActive = isActive;
		this.companyId = companyId;
		this.siteId = siteId;
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

	@Column(name = "employee_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "role_id")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "department_id")
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "official_email")
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "status")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "primary_site")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

}
