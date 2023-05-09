package erp.boq_mgmt.entity;

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
@Table(name = "boq_master_lmps_labours")
public class BoqMasterLmpsLabour {

	private Long id;

	private Long boqMasterLmpsId;

	private Integer labourTypeId;

	private Integer unitId;

	private Double quantity;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	private Unit unit;

	private WorkorderLabourType labourType;

	public BoqMasterLmpsLabour() {
		super();
	}

	public BoqMasterLmpsLabour(Long id, Long boqMasterLmpsId, Integer labourTypeId, Integer unitId, Double quantity,
			Boolean isActive, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.boqMasterLmpsId = boqMasterLmpsId;
		this.labourTypeId = labourTypeId;
		this.unitId = unitId;
		this.quantity = quantity;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "boq_master_lmps_id")
	public Long getBoqMasterLmpsId() {
		return boqMasterLmpsId;
	}

	public void setBoqMasterLmpsId(Long boqMasterLmpsId) {
		this.boqMasterLmpsId = boqMasterLmpsId;
	}

	@Column(name = "labour_type_id")
	public Integer getLabourTypeId() {
		return labourTypeId;
	}

	public void setLabourTypeId(Integer labourTypeId) {
		this.labourTypeId = labourTypeId;
	}

	@Column(name = "unit_id")
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
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

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	@OneToOne
	@JoinColumn(name = "unit_id", insertable = false, updatable = false)
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@OneToOne
	@JoinColumn(name = "labour_type_id", insertable = false, updatable = false)
	public WorkorderLabourType getLabourType() {
		return labourType;
	}

	public void setLabourType(WorkorderLabourType labourType) {
		this.labourType = labourType;
	}

}
