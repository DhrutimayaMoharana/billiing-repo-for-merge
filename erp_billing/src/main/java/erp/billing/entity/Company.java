package erp.billing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "company")
public class Company implements Serializable {

	private static final long serialVersionUID = 1322941270668316906L;

	private Integer id;

	private String name;

	private String address;

	private String gstNo;

	private String regNo;

	private String cinNo;

	private String shortName;

	private String headerLogo;

	private String printLogo;

	private String registeredOfficeAddress;

	private String corporateOfficeAddress;

	private String email;

	private String phoneNo;

	private String website;

	private String upiUrl;

	private CompanyClient client;

	public Company() {
		super();
	}

	public Company(Integer id) {
		super();
		this.id = id;
	}

	public Company(Integer id, String name, String address, String gstNo, String regNo, String cinNo, String shortName,
			String headerLogo, String printLogo, String registeredOfficeAddress, String corporateOfficeAddress,
			String email, String phoneNo, String website, CompanyClient client) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.gstNo = gstNo;
		this.regNo = regNo;
		this.cinNo = cinNo;
		this.shortName = shortName;
		this.headerLogo = headerLogo;
		this.printLogo = printLogo;
		this.registeredOfficeAddress = registeredOfficeAddress;
		this.corporateOfficeAddress = corporateOfficeAddress;
		this.email = email;
		this.phoneNo = phoneNo;
		this.website = website;
		this.client = client;
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

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "gst_no")
	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	@Column(name = "reg_no")
	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	@Column(name = "cin_no")
	public String getCinNo() {
		return cinNo;
	}

	public void setCinNo(String cinNo) {
		this.cinNo = cinNo;
	}

	@Column(name = "short_name")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "header_logo")
	public String getHeaderLogo() {
		return headerLogo;
	}

	public void setHeaderLogo(String headerLogo) {
		this.headerLogo = headerLogo;
	}

	@Column(name = "print_logo")
	public String getPrintLogo() {
		return printLogo;
	}

	public void setPrintLogo(String printLogo) {
		this.printLogo = printLogo;
	}

	@Column(name = "registered_office_address")
	public String getRegisteredOfficeAddress() {
		return registeredOfficeAddress;
	}

	public void setRegisteredOfficeAddress(String registeredOfficeAddress) {
		this.registeredOfficeAddress = registeredOfficeAddress;
	}

	@Column(name = "corprate_office_address")
	public String getCorporateOfficeAddress() {
		return corporateOfficeAddress;
	}

	public void setCorporateOfficeAddress(String corporateOfficeAddress) {
		this.corporateOfficeAddress = corporateOfficeAddress;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone_no")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "website")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "upi_url")
	public String getUpiUrl() {
		return upiUrl;
	}

	public void setUpiUrl(String upiUrl) {
		this.upiUrl = upiUrl;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "client_id")
	public CompanyClient getClient() {
		return client;
	}

	public void setClient(CompanyClient client) {
		this.client = client;
	}

}
