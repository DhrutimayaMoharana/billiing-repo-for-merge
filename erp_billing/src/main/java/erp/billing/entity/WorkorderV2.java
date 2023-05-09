
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

@Entity
@Table(name = "billing_workorder")
public class WorkorderV2 implements Serializable {

	private static final long serialVersionUID = 1962731406097731714L;

	private Long id;

	private String subject;

	private String uniqueNo;

	private Date startDate;

	private Contractor contractor;

	private WorkorderType type;

	private EngineState state;

	private Boolean isActive;

	private Integer version;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	private Integer companyId;

	private User modifiedByUser;

	private String referenceWorkorderNo;

	public WorkorderV2() {
		super();
	}

	public WorkorderV2(Long id) {
		super();
		this.id = id;
	}

	public WorkorderV2(Long id, String subject, String uniqueNo, Date startDate, Contractor contractor,
			WorkorderType type, EngineState state, Boolean isActive, Integer version, Date createdOn, Long createdBy,
			Date modifiedOn, Long modifiedBy, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.subject = subject;
		this.uniqueNo = uniqueNo;
		this.startDate = startDate;
		this.contractor = contractor;
		this.type = type;
		this.state = state;
		this.isActive = isActive;
		this.version = version;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	@OneToOne
	@JoinColumn(name = "modified_by", updatable = false, insertable = false)
	public User getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(User modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	@Column(name = "reference_wo_no")
	public String getReferenceWorkorderNo() {
		return referenceWorkorderNo;
	}

	public void setReferenceWorkorderNo(String referenceWorkorderNo) {
		this.referenceWorkorderNo = referenceWorkorderNo;
	}

}
