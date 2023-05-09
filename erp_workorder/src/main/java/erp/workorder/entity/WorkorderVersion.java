
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
@Table(name = "billing_workorder_version")
public class WorkorderVersion implements Serializable {

	private static final long serialVersionUID = 1962731406097731714L;

	private Long id;

	private Integer fromAmendment;

	private Integer amendmentVersion;

	private Date startDate;

	private Date systemBillStartDate;

	private String subject;

	private String uniqueNo;

	private String referenceWorkorderNo;

	private Long contractorId;

	private Long woContractorId;

	private Integer typeId;

	private Integer stateId;

	private String remark;

	private Long boqWorkId;

	private Long consultantWorkId;

	private Long hiringMachineWorkId;

	private Long transportWorkId;

	private Long labourWorkId;

	private Double previousBillAmount;

	private Integer previousBillNo;

	private Long siteId;

	private Integer companyId;

	private Boolean isActive;

	private Integer version;

	private Date createdOn;

	private Long createdBy;

	public WorkorderVersion() {
		super();
	}

	public WorkorderVersion(Long id, Integer fromAmendment, Integer amendmentVersion, Date startDate,
			Date systemBillStartDate, String subject, String uniqueNo, String referenceWorkorderNo, Long contractorId,
			Long woContractorId, Integer typeId, Integer stateId, String remark, Long boqWorkId, Long consultantWorkId,
			Long hiringMachineWorkId, Long transportWorkId, Long labourWorkId, Double previousBillAmount,
			Integer previousBillNo, Long siteId, Integer companyId, Boolean isActive, Integer version, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.fromAmendment = fromAmendment;
		this.amendmentVersion = amendmentVersion;
		this.startDate = startDate;
		this.systemBillStartDate = systemBillStartDate;
		this.subject = subject;
		this.uniqueNo = uniqueNo;
		this.referenceWorkorderNo = referenceWorkorderNo;
		this.contractorId = contractorId;
		this.woContractorId = woContractorId;
		this.typeId = typeId;
		this.stateId = stateId;
		this.remark = remark;
		this.boqWorkId = boqWorkId;
		this.consultantWorkId = consultantWorkId;
		this.hiringMachineWorkId = hiringMachineWorkId;
		this.transportWorkId = transportWorkId;
		this.labourWorkId = labourWorkId;
		this.previousBillAmount = previousBillAmount;
		this.previousBillNo = previousBillNo;
		this.siteId = siteId;
		this.companyId = companyId;
		this.isActive = isActive;
		this.version = version;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	@Column(name = "from_amendment")
	public Integer getFromAmendment() {
		return fromAmendment;
	}

	public void setFromAmendment(Integer fromAmendment) {
		this.fromAmendment = fromAmendment;
	}

	public Integer getAmendmentVersion() {
		return amendmentVersion;
	}

	public void setAmendmentVersion(Integer amendmentVersion) {
		this.amendmentVersion = amendmentVersion;
	}

	@Column(name = "unique_no")
	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}

	@Column(name = "contractor_id")
	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	@Column(name = "type_id")
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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

	@Column(name = "wo_contractor_id")
	public Long getWoContractorId() {
		return woContractorId;
	}

	public void setWoContractorId(Long woContractorId) {
		this.woContractorId = woContractorId;
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

	@Column(name = "boq_work_id")
	public Long getBoqWorkId() {
		return boqWorkId;
	}

	public void setBoqWorkId(Long boqWorkId) {
		this.boqWorkId = boqWorkId;
	}

	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	@Column(name = "consultant_work_id")
	public Long getConsultantWorkId() {
		return consultantWorkId;
	}

	public void setConsultantWorkId(Long consultantWorkId) {
		this.consultantWorkId = consultantWorkId;
	}

	@Column(name = "hiring_machine_work_id")
	public Long getHiringMachineWorkId() {
		return hiringMachineWorkId;
	}

	public void setHiringMachineWorkId(Long hiringMachineWorkId) {
		this.hiringMachineWorkId = hiringMachineWorkId;
	}

	@Column(name = "transport_work_id")
	public Long getTransportWorkId() {
		return transportWorkId;
	}

	public void setTransportWorkId(Long transportWorkId) {
		this.transportWorkId = transportWorkId;
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

	@Column(name = "labour_work_id")
	public Long getLabourWorkId() {
		return labourWorkId;
	}

	public void setLabourWorkId(Long labourWorkId) {
		this.labourWorkId = labourWorkId;
	}

}
