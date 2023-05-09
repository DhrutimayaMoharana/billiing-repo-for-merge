package erp.billing.dto;

public class CompanyDTO {

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

	private CompanyClientDTO client;

	public CompanyDTO() {
		super();
	}

	public CompanyDTO(Integer id, String name, String address, String gstNo, String regNo, String cinNo,
			String shortName, String headerLogo, String printLogo, String registeredOfficeAddress,
			String corporateOfficeAddress, String email, String phoneNo, String website, CompanyClientDTO client) {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getCinNo() {
		return cinNo;
	}

	public void setCinNo(String cinNo) {
		this.cinNo = cinNo;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getHeaderLogo() {
		return headerLogo;
	}

	public void setHeaderLogo(String headerLogo) {
		this.headerLogo = headerLogo;
	}

	public String getPrintLogo() {
		return printLogo;
	}

	public void setPrintLogo(String printLogo) {
		this.printLogo = printLogo;
	}

	public String getRegisteredOfficeAddress() {
		return registeredOfficeAddress;
	}

	public void setRegisteredOfficeAddress(String registeredOfficeAddress) {
		this.registeredOfficeAddress = registeredOfficeAddress;
	}

	public String getCorporateOfficeAddress() {
		return corporateOfficeAddress;
	}

	public void setCorporateOfficeAddress(String corporateOfficeAddress) {
		this.corporateOfficeAddress = corporateOfficeAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public CompanyClientDTO getClient() {
		return client;
	}

	public void setClient(CompanyClientDTO client) {
		this.client = client;
	}

}
