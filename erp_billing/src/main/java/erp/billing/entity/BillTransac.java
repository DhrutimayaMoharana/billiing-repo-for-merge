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
@Table(name = "bill_transac")
public class BillTransac implements Serializable {

	private static final long serialVersionUID = -1178120861162542512L;

	private Long id;

	private Short billTypeId;

	private Integer billNo;

	private Date fromDate;

	private Date toDate;

	private Long workorderId;

	private Integer stateId;

	private Long siteId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public BillTransac() {
		super();
	}

	public BillTransac(Long id, Short billTypeId, Integer billNo, Date fromDate, Date toDate, Long workorderId,
			Integer stateId, Long siteId, Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.billTypeId = billTypeId;
		this.billNo = billNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.workorderId = workorderId;
		this.stateId = stateId;
		this.siteId = siteId;
		this.isActive = isActive;
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

	@Column(name = "bill_type_id")
	public Short getBillTypeId() {
		return billTypeId;
	}

	public void setBillTypeId(Short billTypeId) {
		this.billTypeId = billTypeId;
	}

	@Column(name = "bill_no")
	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
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

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
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

	@Column(name = "is_Active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

}
