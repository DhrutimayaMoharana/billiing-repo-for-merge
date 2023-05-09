package erp.boq_mgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = -1553009295838527337L;

	private Long id;

	private String name;

	private String code;

	private String emailId;

	private UserRole role;

	private String dob;

	private String contactNo;

	private Date createdOn;

	private Boolean isActive;

	private Integer companyId;

	public User() {
		super();
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	public User(Long id, String name, String code, String emailId, UserRole role, String dob, String contactNo,
			Date createdOn, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.emailId = emailId;
		this.role = role;
		this.dob = dob;
		this.contactNo = contactNo;
		this.createdOn = createdOn;
		this.isActive = isActive;
		this.companyId = companyId;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "employee_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "employee_code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "email_id")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Column(name = "dob")
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Column(name = "phone_no")
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

}