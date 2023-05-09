package erp.workorder.entity;

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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "sites")
public class Site implements java.io.Serializable {

	private static final long serialVersionUID = -1395098335205144793L;

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

	private Employee contactPerson;

	private Double tdsOnIncomeTax;

	private Double tdsOnLabourCess;

	private Double tdsOnGst;

	private Date startDate;

	private Date endDate;

	private Currency curreny;

	private Double dieselRetailPrice;

	private Double lubricantsFactor;

	private Double dieselPriceWithLubricants;

	private Chainage startChainage;

	private Chainage endChainage;

	private Double projectLength;

	private CountryState state;

	private Double thresholdAmount;

	private Double retention;

	private String consultantName;

	private String consultantAddress;

	private Date createdOn;

	private Boolean isActive;

	private Integer companyId;

	private Company company;

	private SiteConcessionaire concessionaire;

	public Site() {
	}

	public Site(Long id) {
		this.id = id;
	}

	public Site(Long id, Long parentId, String code, String name, String subject, String description, String contractNo,
			String address, String billingAddress, String gstNo, Employee contactPerson, Double tdsOnIncomeTax,
			Double tdsOnLabourCess, Double tdsOnGst, Date startDate, Date endDate, Currency curreny,
			Double dieselRetailPrice, Double lubricantsFactor, Double dieselPriceWithLubricants, Chainage startChainage,
			Chainage endChainage, Double projectLength, CountryState state, Double thresholdAmount, Double retention,
			String consultantName, String consultantAddress, Date createdOn, Boolean isActive, Integer companyId,
			SiteConcessionaire concessionaire) {
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
		this.consultantAddress = consultantAddress;
		this.consultantName = consultantName;
		this.createdOn = createdOn;
		this.isActive = isActive;
		this.companyId = companyId;
		this.concessionaire = concessionaire;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "billing_address")
	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	@Column(name = "gst_no")
	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	@Column(name = "parent_id")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "contract_no")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "tds_on_income_tax")
	public Double getTdsOnIncomeTax() {
		return tdsOnIncomeTax;
	}

	public void setTdsOnIncomeTax(Double tdsOnIncomeTax) {
		this.tdsOnIncomeTax = tdsOnIncomeTax;
	}

	@Column(name = "tds_on_labour_cess")
	public Double getTdsOnLabourCess() {
		return tdsOnLabourCess;
	}

	public void setTdsOnLabourCess(Double tdsOnLabourCess) {
		this.tdsOnLabourCess = tdsOnLabourCess;
	}

	@Column(name = "tds_on_gst")
	public Double getTdsOnGst() {
		return tdsOnGst;
	}

	public void setTdsOnGst(Double tdsOnGst) {
		this.tdsOnGst = tdsOnGst;
	}

	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	public Currency getCurreny() {
		return curreny;
	}

	public void setCurreny(Currency curreny) {
		this.curreny = curreny;
	}

	@Column(name = "diesel_retail_price")
	public Double getDieselRetailPrice() {
		return dieselRetailPrice;
	}

	public void setDieselRetailPrice(Double dieselRetailPrice) {
		this.dieselRetailPrice = dieselRetailPrice;
	}

	@Column(name = "lubricants_factor")
	public Double getLubricantsFactor() {
		return lubricantsFactor;
	}

	public void setLubricantsFactor(Double lubricantsFactor) {
		this.lubricantsFactor = lubricantsFactor;
	}

	@Column(name = "diesel_price_with_lubricants")
	public Double getDieselPriceWithLubricants() {
		return dieselPriceWithLubricants;
	}

	public void setDieselPriceWithLubricants(Double dieselPriceWithLubricants) {
		this.dieselPriceWithLubricants = dieselPriceWithLubricants;
	}

	@Column(name = "project_length")
	public Double getProjectLength() {
		return projectLength;
	}

	public void setProjectLength(Double projectLength) {
		this.projectLength = projectLength;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	public CountryState getState() {
		return state;
	}

	public void setState(CountryState state) {
		this.state = state;
	}

	@Column(name = "threshold_amount")
	public Double getThresholdAmount() {
		return thresholdAmount;
	}

	public void setThresholdAmount(Double thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}

	@Column(name = "retention")
	public Double getRetention() {
		return retention;
	}

	public void setRetention(Double retention) {
		this.retention = retention;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contact_person_id")
	public Employee getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(Employee contactPerson) {
		this.contactPerson = contactPerson;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "start_chainage_id")
	public Chainage getStartChainage() {
		return startChainage;
	}

	public void setStartChainage(Chainage startChainage) {
		this.startChainage = startChainage;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "end_chainage_id")
	public Chainage getEndChainage() {
		return endChainage;
	}

	public void setEndChainage(Chainage endChainage) {
		this.endChainage = endChainage;
	}

	@Column(name = "consultant_name")
	public String getConsultantName() {
		return consultantName;
	}

	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}

	@Column(name = "consultant_address")
	public String getConsultantAddress() {
		return consultantAddress;
	}

	public void setConsultantAddress(String consultantAddress) {
		this.consultantAddress = consultantAddress;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id", insertable = false, updatable = false)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@OneToOne
	@JoinColumn(name = "site_concessionaire_id", insertable = false, updatable = false)
	public SiteConcessionaire getConcessionaire() {
		return concessionaire;
	}

	public void setConcessionaire(SiteConcessionaire concessionaire) {
		this.concessionaire = concessionaire;
	}

}