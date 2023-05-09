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
@Table(name = "wo_transportation_work_item_versions")
public class WorkorderTransportWorkItemMappingVersion implements Serializable {

	private static final long serialVersionUID = -7744186786145072748L;

	private Long id;

	private Long workorderTransportWorkVersionId;

	private Long boqId;

	private String description;

	private Double quantity;

	private Double rate;

	private Short unitId;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WorkorderTransportWorkItemMappingVersion() {
		super();
	}

	public WorkorderTransportWorkItemMappingVersion(Long id, Long workorderTransportWorkVersionId, Long boqId,
			String description, Double quantity, Double rate, Short unitId, String remark, Boolean isActive,
			Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderTransportWorkVersionId = workorderTransportWorkVersionId;
		this.boqId = boqId;
		this.description = description;
		this.quantity = quantity;
		this.rate = rate;
		this.unitId = unitId;
		this.remark = remark;
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

	@Column(name = "unit_id")
	public Short getUnitId() {
		return unitId;
	}

	public void setUnitId(Short unitId) {
		this.unitId = unitId;
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

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@Column(name = "wo_transport_work_version_id")
	public Long getWorkorderTransportWorkVersionId() {
		return workorderTransportWorkVersionId;
	}

	public void setWorkorderTransportWorkVersionId(Long workorderTransportWorkVersionId) {
		this.workorderTransportWorkVersionId = workorderTransportWorkVersionId;
	}

}
