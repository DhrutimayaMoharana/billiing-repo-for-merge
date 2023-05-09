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
@Table(name = "wo_boq_work_qty_transacs")
public class WorkorderBoqWorkQtyTransacs implements Serializable {

	private static final long serialVersionUID = -6567975337239450269L;

	private Long id;

	private Long workorderId;

	private Long boqWorkTransacId;

	private Long boqId;
	
	private Long unitId;

	private String description;

	private String vendorDescription;

	private Double rate;

	private Double quantity;

	private Boolean isActive;

	private Integer version;

	private Date createdOn;

	private Long createdBy;

	public WorkorderBoqWorkQtyTransacs() {
		super();
	}

	public WorkorderBoqWorkQtyTransacs(Long id, Long workorderId, Long boqWorkTransacId, Long boqId, String description,
			String vendorDescription, Double rate, Double quantity, Boolean isActive, Integer version, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.boqWorkTransacId = boqWorkTransacId;
		this.boqId = boqId;
		this.description = description;
		this.vendorDescription = vendorDescription;
		this.rate = rate;
		this.quantity = quantity;
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

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	@Column(name = "boq_work_transac_id")
	public Long getBoqWorkTransacId() {
		return boqWorkTransacId;
	}

	public void setBoqWorkTransacId(Long boqWorkTransacId) {
		this.boqWorkTransacId = boqWorkTransacId;
	}

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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

	@Column(name = "vendor_description")
	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	@Column(name = "boq_description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "unit_id")
	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

}
