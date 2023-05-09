package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contractor_employee_debit_notes")
public class DebitNote implements Serializable {

	private static final long serialVersionUID = -7646227357529082373L;

	private Long id;

	private Long serialNo;

	private Long partyId;

	private Integer partyType;

	private Integer currentStateId;

	private Date fromDate;

	private Date toDate;

	private Double totalAmount;

	private Integer stateId;

	private Integer status;

	private Long siteId;

	private Integer companyId;

	private Date createdOn;

	public DebitNote() {
		super();
	}

	public DebitNote(Long id, Long serialNo, Long partyId, Integer partyType, Integer currentStateId, Date fromDate,
			Date toDate, Double totalAmount, Integer stateId, Integer status, Long siteId, Integer companyId,
			Date createdOn) {
		super();
		this.id = id;
		this.serialNo = serialNo;
		this.partyId = partyId;
		this.partyType = partyType;
		this.currentStateId = currentStateId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.totalAmount = totalAmount;
		this.stateId = stateId;
		this.status = status;
		this.siteId = siteId;
		this.companyId = companyId;
		this.createdOn = createdOn;
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

	@Column(name = "serial_no")
	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "party_id")
	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	@Column(name = "party_type")
	public Integer getPartyType() {
		return partyType;
	}

	public void setPartyType(Integer partyType) {
		this.partyType = partyType;
	}

	@Column(name = "from_date")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name = "to_date")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Column(name = "total_amount")
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "current_state_id")
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

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "current_state_id", insertable = false, updatable = false)
	public Integer getCurrentStateId() {
		return currentStateId;
	}

	public void setCurrentStateId(Integer currentStateId) {
		this.currentStateId = currentStateId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
