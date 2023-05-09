package erp.workorder.dto;

import java.util.Date;

public class SiteDTO {

	private Long id;

	private Long parentId;

	private String code;

	private String name;

	private String subject;

	private String description;

	private String contractNo;

	private String address;

	private String billingAddress;

	private String gstNo;

	private EmployeeDTO contactPerson;

	private Double tdsOnIncomeTax;

	private Double tdsOnLabourCess;

	private Double tdsOnGst;

	private Date startDate;

	private Date endDate;

	private CurrencyDTO curreny;

	private Double dieselRetailPrice;

	private Double lubricantsFactor;

	private Double dieselPriceWithLubricants;

	private ChainageDTO startChainage;

	private ChainageDTO endChainage;

	private Double projectLength;

	private CountryStateDTO state;

	private Double thresholdAmount;

	private Double retention;

	private String consultantName;

	private String consultantAddress;

	private Date createdOn;

	private Boolean isActive;

	private Integer companyId;

	private CompanyDTO company;

	private String concessionaireName;

	private String concessionaireDescription;

	public SiteDTO() {
		super();
	}

	public SiteDTO(Long id) {
		super();
		this.id = id;
	}

	public SiteDTO(Long id, Long parentId, String code, String name, String subject, String description,
			String contractNo, String address, String billingAddress, String gstNo, EmployeeDTO contactPerson,
			Double tdsOnIncomeTax, Double tdsOnLabourCess, Double tdsOnGst, Date startDate, Date endDate,
			CurrencyDTO curreny, Double dieselRetailPrice, Double lubricantsFactor, Double dieselPriceWithLubricants,
			ChainageDTO startChainage, ChainageDTO endChainage, Double projectLength, CountryStateDTO state,
			Double thresholdAmount, Double retention, String consultantName, String consultantAddress, Date createdOn,
			Boolean isActive, Integer companyId, CompanyDTO company, String concessionaireName,
			String concessionaireDescription) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.code = code;
		this.name = name;
		this.subject = subject;
		this.description = description;
		this.contractNo = contractNo;
		this.address = address;
		this.billingAddress = billingAddress;
		this.gstNo = gstNo;
		this.contactPerson = contactPerson;
		this.tdsOnIncomeTax = tdsOnIncomeTax;
		this.tdsOnLabourCess = tdsOnLabourCess;
		this.tdsOnGst = tdsOnGst;
		this.startDate = startDate;
		this.endDate = endDate;
		this.curreny = curreny;
		this.dieselRetailPrice = dieselRetailPrice;
		this.lubricantsFactor = lubricantsFactor;
		this.dieselPriceWithLubricants = dieselPriceWithLubricants;
		this.startChainage = startChainage;
		this.endChainage = endChainage;
		this.projectLength = projectLength;
		this.state = state;
		this.thresholdAmount = thresholdAmount;
		this.retention = retention;
		this.consultantName = consultantName;
		this.consultantAddress = consultantAddress;
		this.createdOn = createdOn;
		this.isActive = isActive;
		this.companyId = companyId;
		this.company = company;
		this.concessionaireName = concessionaireName;
		this.concessionaireDescription = concessionaireDescription;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getTdsOnIncomeTax() {
		return tdsOnIncomeTax;
	}

	public void setTdsOnIncomeTax(Double tdsOnIncomeTax) {
		this.tdsOnIncomeTax = tdsOnIncomeTax;
	}

	public Double getTdsOnLabourCess() {
		return tdsOnLabourCess;
	}

	public void setTdsOnLabourCess(Double tdsOnLabourCess) {
		this.tdsOnLabourCess = tdsOnLabourCess;
	}

	public Double getTdsOnGst() {
		return tdsOnGst;
	}

	public void setTdsOnGst(Double tdsOnGst) {
		this.tdsOnGst = tdsOnGst;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public CurrencyDTO getCurreny() {
		return curreny;
	}

	public void setCurreny(CurrencyDTO curreny) {
		this.curreny = curreny;
	}

	public Double getDieselRetailPrice() {
		return dieselRetailPrice;
	}

	public void setDieselRetailPrice(Double dieselRetailPrice) {
		this.dieselRetailPrice = dieselRetailPrice;
	}

	public Double getLubricantsFactor() {
		return lubricantsFactor;
	}

	public void setLubricantsFactor(Double lubricantsFactor) {
		this.lubricantsFactor = lubricantsFactor;
	}

	public Double getDieselPriceWithLubricants() {
		return dieselPriceWithLubricants;
	}

	public void setDieselPriceWithLubricants(Double dieselPriceWithLubricants) {
		this.dieselPriceWithLubricants = dieselPriceWithLubricants;
	}

	public Double getProjectLength() {
		return projectLength;
	}

	public void setProjectLength(Double projectLength) {
		this.projectLength = projectLength;
	}

	public CountryStateDTO getState() {
		return state;
	}

	public void setState(CountryStateDTO state) {
		this.state = state;
	}

	public Double getThresholdAmount() {
		return thresholdAmount;
	}

	public void setThresholdAmount(Double thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}

	public Double getRetention() {
		return retention;
	}

	public void setRetention(Double retention) {
		this.retention = retention;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public EmployeeDTO getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(EmployeeDTO contactPerson) {
		this.contactPerson = contactPerson;
	}

	public ChainageDTO getStartChainage() {
		return startChainage;
	}

	public void setStartChainage(ChainageDTO startChainage) {
		this.startChainage = startChainage;
	}

	public ChainageDTO getEndChainage() {
		return endChainage;
	}

	public void setEndChainage(ChainageDTO endChainage) {
		this.endChainage = endChainage;
	}

	public String getConsultantName() {
		return consultantName;
	}

	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}

	public String getConsultantAddress() {
		return consultantAddress;
	}

	public void setConsultantAddress(String consultantAddress) {
		this.consultantAddress = consultantAddress;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public String getConcessionaireName() {
		return concessionaireName;
	}

	public void setConcessionaireName(String concessionaireName) {
		this.concessionaireName = concessionaireName;
	}

	public String getConcessionaireDescription() {
		return concessionaireDescription;
	}

	public void setConcessionaireDescription(String concessionaireDescription) {
		this.concessionaireDescription = concessionaireDescription;
	}

}
