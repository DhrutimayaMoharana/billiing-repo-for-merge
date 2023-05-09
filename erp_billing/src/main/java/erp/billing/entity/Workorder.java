package erp.billing.entity;

import java.io.Serializable;
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
@Table(name = "billing_workorder")
public class Workorder implements Serializable {

	private static final long serialVersionUID = 1962731406097731714L;

	private Long id;

	private String subject;

	private String uniqueNo;

	private Date startDate;

	private Date endDate;

	private Contractor contractor;

	private WorkorderContractor woContractor;

	private WorkorderType type;

	private EngineState state;

	private Date systemBillStartDate;

	private Double previousBillAmount;

	private Integer previousBillNo;

	private Boolean isActive;

	private Integer version;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	private Integer companyId;

	private Long fromAmendmentId;

	private AmendWorkorderInvocation amendWorkorderInvocation;

	public Workorder() {
		super();
	}

	public Workorder(Long id) {
		super();
		this.id = id;
	}

	public Workorder(Long id, String subject, String uniqueNo, Date startDate, Date endDate, Contractor contractor,
			WorkorderContractor woContractor, WorkorderType type, EngineState state, Date systemBillStartDate,
			Double previousBillAmount, Integer previousBillNo, Boolean isActive, Integer version, Date modifiedOn,
			Long modifiedBy, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.subject = subject;
		this.uniqueNo = uniqueNo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contractor = contractor;
		this.woContractor = woContractor;
		this.type = type;
		this.state = state;
		this.systemBillStartDate = systemBillStartDate;
		this.previousBillAmount = previousBillAmount;
		this.previousBillNo = previousBillNo;
		this.isActive = isActive;
		this.version = version;
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

	@OneToOne(mappedBy = "workorder")
	@NotFound(action = NotFoundAction.IGNORE)
	public AmendWorkorderInvocation getAmendWorkorderInvocation() {
		return amendWorkorderInvocation;
	}

	public void setAmendWorkorderInvocation(AmendWorkorderInvocation amendWorkorderInvocation) {
		this.amendWorkorderInvocation = amendWorkorderInvocation;
	}

	@Column(name = "amendment_from_id")
	public Long getFromAmendmentId() {
		return fromAmendmentId;
	}

	public void setFromAmendmentId(Long fromAmendmentId) {
		this.fromAmendmentId = fromAmendmentId;
	}

}
