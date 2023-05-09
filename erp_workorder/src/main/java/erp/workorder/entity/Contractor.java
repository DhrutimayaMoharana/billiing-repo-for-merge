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
@Table(name = "contractors")
public class Contractor implements Serializable {
	
	private static final long serialVersionUID = 7124203532872341911L;

	private Long id;
	
	private String name;
	
	private String gstNo;
	
	private String panNo;
	
	private String email;
	
	private String address;
	
	private String phoneNo;
	
	private String vendorCode;
	
	private Date createdOn;
	
	private Boolean isActive;
	
	private Integer companyId;

	public Contractor() {
		super();
	}

	public Contractor(Long id, String name, String gstNo, String panNo, String email, String address, String phoneNo,
			String vendorCode, Date createdOn, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.gstNo = gstNo;
		this.panNo = panNo;
		this.email = email;
		this.address = address;
		this.phoneNo = phoneNo;
		this.vendorCode = vendorCode;
		this.createdOn = createdOn;
		this.isActive = isActive;
		this.companyId = companyId;
	}

	public Contractor(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gst_no")
	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	@Column(name = "pan_no")
	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone_no")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "vendor_code")
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
