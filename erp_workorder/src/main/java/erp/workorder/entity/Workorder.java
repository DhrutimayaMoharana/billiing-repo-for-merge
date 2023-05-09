
package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import erp.workorder.enums.WorkorderCloseType;

@Entity
@Table(name = "billing_workorder")
public class Workorder implements Serializable {

	private static final long serialVersionUID = 1962731406097731714L;

	private Long id;

	private String subject;

	private String uniqueNo;

	private String referenceWorkorderNo;

	private Date startDate;

	private Date endDate;

	private Contractor contractor;

	private WorkorderContractor woContractor;

	private WorkorderType type;

	private EngineState state;

	private WorkorderCloseType closeType;

	private String remark;

	private WorkorderBoqWork boqWork;

	private WorkorderConsultantWork consultantWork;

	private WorkorderHiringMachineWork hiringMachineWork;

	private WorkorderTransportWork transportWork;

	private WorkorderLabourWork labourWork;

	private List<WoTncMapping> termsAndConditions;

	private Date systemBillStartDate;

	private Double previousBillAmount;

	private Integer previousBillNo;

	private Boolean isActive;

	private Integer version;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	private Integer companyId;

	private User modifiedByUser;

	private Long fromAmendmentId;

	public Workorder() {
		super();
	}

	public Workorder(Long id) {
		super();
		this.id = id;
	}

	public Workorder(Long id, String subject, String uniqueNo, String referenceWorkorderNo, Date startDate,
			Date endDate, Contractor contractor, WorkorderContractor woContractor, WorkorderType type,
			EngineState state, String remark, WorkorderBoqWork boqWork, WorkorderConsultantWork consultantWork,
			WorkorderHiringMachineWork hiringMachineWork, WorkorderTransportWork transportWork,
			List<WoTncMapping> termsAndConditions, Date systemBillStartDate, Double previousBillAmount,
			Integer previousBillNo, Boolean isActive, Integer version, Date createdOn, Long createdBy, Date modifiedOn,
			Long modifiedBy, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.setSubject(subject);
		this.uniqueNo = uniqueNo;
		this.referenceWorkorderNo = referenceWorkorderNo;
		this.startDate = startDate;
		this.startDate = startDate;
		this.contractor = contractor;
		this.remark = remark;
		this.woContractor = woContractor;
		this.type = type;
		this.state = state;
		this.setBoqWork(boqWork);
		this.consultantWork = consultantWork;
		this.hiringMachineWork = hiringMachineWork;
		this.termsAndConditions = termsAndConditions;
		this.systemBillStartDate = systemBillStartDate;
		this.previousBillAmount = previousBillAmount;
		this.previousBillNo = previousBillNo;
		this.isActive = isActive;
		this.version = version;
		this.transportWork = transportWork;
		this.setCreatedOn(createdOn);
		this.setCreatedBy(createdBy);
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.siteId = siteId;
		this.companyId = companyId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "unique_no")
	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contractor_id")
	public Contractor getContractor() {
		return contractor;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	public WorkorderType getType() {
		return type;
	}

	public void setType(WorkorderType type) {
		this.type = type;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "workorder")
	public List<WoTncMapping> getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(List<WoTncMapping> termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name = "modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "wo_contractor_id")
	public WorkorderContractor getWoContractor() {
		return woContractor;
	}

	public void setWoContractor(WorkorderContractor woContractor) {
		this.woContractor = woContractor;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boq_work_id")
	public WorkorderBoqWork getBoqWork() {
		return boqWork;
	}

	public void setBoqWork(WorkorderBoqWork boqWork) {
		this.boqWork = boqWork;
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

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "reference_wo_no")
	public String getReferenceWorkorderNo() {
		return referenceWorkorderNo;
	}

	public void setReferenceWorkorderNo(String referenceWorkorderNo) {
		this.referenceWorkorderNo = referenceWorkorderNo;
	}

	@OneToOne
	@JoinColumn(name = "modified_by", insertable = false, updatable = false)
	public User getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(User modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "consultant_work_id")
	public WorkorderConsultantWork getConsultantWork() {
		return consultantWork;
	}

	public void setConsultantWork(WorkorderConsultantWork consultantWork) {
		this.consultantWork = consultantWork;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hiring_machine_work_id")
	public WorkorderHiringMachineWork getHiringMachineWork() {
		return hiringMachineWork;
	}

	public void setHiringMachineWork(WorkorderHiringMachineWork hiringMachineWork) {
		this.hiringMachineWork = hiringMachineWork;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transport_work_id")
	public WorkorderTransportWork getTransportWork() {
		return transportWork;
	}

	public void setTransportWork(WorkorderTransportWork transportWork) {
		this.transportWork = transportWork;
	}

	@Column(name = "system_bill_start_date")
	public Date getSystemBillStartDate() {
		return systemBillStartDate;
	}

	public void setSystemBillStartDate(Date systemBillStartDate) {
		this.systemBillStartDate = systemBillStartDate;
	}

	@Column(name = "previous_bills_amount")
	public Double getPreviousBillAmount() {
		return previousBillAmount;
	}

	public void setPreviousBillAmount(Double previousBillAmount) {
		this.previousBillAmount = previousBillAmount;
	}

	@Column(name = "last_bill_no")
	public Integer getPreviousBillNo() {
		return previousBillNo;
	}

	public void setPreviousBillNo(Integer previousBillNo) {
		this.previousBillNo = previousBillNo;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "labour_work_id")
	public WorkorderLabourWork getLabourWork() {
		return labourWork;
	}

	public void setLabourWork(WorkorderLabourWork labourWork) {
		this.labourWork = labourWork;
	}

	@Column(name = "amendment_from_id")
	public Long getFromAmendmentId() {
		return fromAmendmentId;
	}

	public void setFromAmendmentId(Long fromAmendmentId) {
		this.fromAmendmentId = fromAmendmentId;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "close_type", insertable = false, updatable = false)
	public WorkorderCloseType getCloseType() {
		return closeType;
	}

	public void setCloseType(WorkorderCloseType closeType) {
		this.closeType = closeType;
	}

}
