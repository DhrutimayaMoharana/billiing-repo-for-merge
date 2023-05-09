package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wo_transportation_work_items")
public class WorkorderTransportWorkItemMapping implements Serializable {

	private static final long serialVersionUID = -7744186786145072748L;

	private Long id;

	private Long workorderTransportWorkId;

	private Long boqId;

	private String description;

	private Double quantity;

	private Double rate;

	private UnitMaster unit;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private WorkorderTransportWork workorderTransportWork;

	private BoqItem boq;

	public WorkorderTransportWorkItemMapping() {
		super();
	}

	public WorkorderTransportWorkItemMapping(Long id) {
		super();
		this.id = id;
	}

	public WorkorderTransportWorkItemMapping(Long id, Long workorderTransportWorkId, Long boqId, String description,
			Double quantity, Double rate, UnitMaster unit, String remark, Boolean isActive, Date createdOn,
			Long createdBy, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderTransportWorkId = workorderTransportWorkId;
		this.boqId = boqId;
		this.description = description;
		this.quantity = quantity;
		this.rate = rate;
		this.unit = unit;
		this.remark = remark;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@OneToOne
	@JoinColumn(name = "unit_id")
	public UnitMaster getUnit() {
		return unit;
	}

	public void setUnit(UnitMaster unit) {
		this.unit = unit;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "is_active")
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

	@Column(name = "wo_transport_work_id")
	public Long getWorkorderTransportWorkId() {
		return workorderTransportWorkId;
	}

	public void setWorkorderTransportWorkId(Long workorderTransportWorkId) {
		this.workorderTransportWorkId = workorderTransportWorkId;
	}

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@OneToOne
	@JoinColumn(name = "wo_transport_work_id", insertable = false, updatable = false)
	public WorkorderTransportWork getWorkorderTransportWork() {
		return workorderTransportWork;
	}

	public void setWorkorderTransportWork(WorkorderTransportWork workorderTransportWork) {
		this.workorderTransportWork = workorderTransportWork;
	}

	@OneToOne
	@JoinColumn(name = "boq_id", insertable = false, updatable = false)
	public BoqItem getBoq() {
		return boq;
	}

	public void setBoq(BoqItem boq) {
		this.boq = boq;
	}

}
