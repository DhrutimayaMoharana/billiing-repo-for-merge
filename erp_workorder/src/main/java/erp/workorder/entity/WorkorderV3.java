
package erp.workorder.entity;

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
public class WorkorderV3 implements Serializable {

	private static final long serialVersionUID = 1962731406097731714L;

	private Long id;

	private Date startDate;

	private Date endDate;

	private Date systemBillStartDate;

	private Double previousBillAmount;

	private Integer previousBillNo;

	private Integer stateId;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	private Long siteId;

	private Integer companyId;

	private WorkorderType type;

	private WorkorderHiringMachineWork hiringMachineWork;

	public WorkorderV3() {
		super();
	}

	public WorkorderV3(Long id) {
		super();
		this.id = id;
	}

	public WorkorderV3(Long id, Date startDate, Date endDate, Date systemBillStartDate, Double previousBillAmount,
			Integer previousBillNo, Integer stateId, Boolean isActive, Date modifiedOn, Long modifiedBy, Long siteId,
			Integer companyId) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.systemBillStartDate = systemBillStartDate;
		this.previousBillAmount = previousBillAmount;
		this.previousBillNo = previousBillNo;
		this.stateId = stateId;
		this.isActive = isActive;
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

	@Column(name = "state_id")
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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
	@JoinColumn(name = "hiring_machine_work_id")
	public WorkorderHiringMachineWork getHiringMachineWork() {
		return hiringMachineWork;
	}

	public void setHiringMachineWork(WorkorderHiringMachineWork hiringMachineWork) {
		this.hiringMachineWork = hiringMachineWork;
	}

}
