package erp.workorder.dto;

import java.util.Date;

public class EmployeeDTO {

	private Long id;

	private String name;
	
	private String code;
	
	private String emailId;
	
	private String dob;
	
	private String contactNo;
	
	private Date createdOn;
	
	private Boolean isActive;
	
	private Integer companyId;

	public EmployeeDTO() {
		super();
	}

	public EmployeeDTO(Long id) {
		super();
		this.id = id;
	}

	public EmployeeDTO(Long id, String name, String code, String emailId, String dob, String contactNo, Date createdOn,
			Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.emailId = emailId;
		this.dob = dob;
		this.contactNo = contactNo;
		this.createdOn = createdOn;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
