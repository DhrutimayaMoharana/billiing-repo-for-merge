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

import erp.boq_mgmt.enums.MachineryRangeType;

@Entity
@Table(name = "boq_master_lmps_machinery")
public class BoqMasterLmpsMachinery {

	private Long id;

	private Long boqMasterLmpsId;

	private Long machineryCategoryId;

	private Boolean isMachineryLoaded;

	private Integer unitId;

	private Integer siteVariableId;

	private Integer afterUnitId;

	private MachineryRangeType rangeType;

	private Integer rangeUnitId;

	private Double rangeQuantity;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	private SiteVariable siteVariable;

	private EquipmentCategory machineryCategory;

	private Unit unit;

	private Unit afterUnit;

	private Unit rangeUnit;

	public BoqMasterLmpsMachinery() {
		super();
	}

	public BoqMasterLmpsMachinery(Long id, Long boqMasterLmpsId, Long machineryCategoryId, Boolean isMachineryLoaded,
			Integer unitId, Integer siteVariableId, Integer afterUnitId, MachineryRangeType rangeType,
			Integer rangeUnitId, Double rangeQuantity, Boolean isActive, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.boqMasterLmpsId = boqMasterLmpsId;
		this.machineryCategoryId = machineryCategoryId;
		this.isMachineryLoaded = isMachineryLoaded;
		this.unitId = unitId;
		this.siteVariableId = siteVariableId;
		this.afterUnitId = afterUnitId;
		this.rangeType = rangeType;
		this.rangeUnitId = rangeUnitId;
		this.rangeQuantity = rangeQuantity;
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

	@Column(name = "machinery_category_id")
	public Long getMachineryCategoryId() {
		return machineryCategoryId;
	}

	public void setMachineryCategoryId(Long machineryCategoryId) {
		this.machineryCategoryId = machineryCategoryId;
	}

	@Column(name = "is_machinery_loaded")
	public Boolean getIsMachineryLoaded() {
		return isMachineryLoaded;
	}

	public void setIsMachineryLoaded(Boolean isMachineryLoaded) {
		this.isMachineryLoaded = isMachineryLoaded;
	}

	@Column(name = "unit_id")
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	@Column(name = "after_unit_id")
	public Integer getAfterUnitId() {
		return afterUnitId;
	}

	public void setAfterUnitId(Integer afterUnitId) {
		this.afterUnitId = afterUnitId;
	}

	@Column(name = "range_type_id")
	public MachineryRangeType getRangeType() {
		return rangeType;
	}

	public void setRangeType(MachineryRangeType rangeType) {
		this.rangeType = rangeType;
	}

	@Column(name = "range_unit_id")
	public Integer getRangeUnitId() {
		return rangeUnitId;
	}

	public void setRangeUnitId(Integer rangeUnitId) {
		this.rangeUnitId = rangeUnitId;
	}

	@Column(name = "range_quantity")
	public Double getRangeQuantity() {
		return rangeQuantity;
	}

	public void setRangeQuantity(Double rangeQuantity) {
		this.rangeQuantity = rangeQuantity;
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
	@JoinColumn(name = "machinery_category_id", insertable = false, updatable = false)
	public EquipmentCategory getMachineryCategory() {
		return machineryCategory;
	}

	public void setMachineryCategory(EquipmentCategory machineryCategory) {
		this.machineryCategory = machineryCategory;
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
	@JoinColumn(name = "after_unit_id", insertable = false, updatable = false)
	public Unit getAfterUnit() {
		return afterUnit;
	}

	public void setAfterUnit(Unit afterUnit) {
		this.afterUnit = afterUnit;
	}

	@OneToOne
	@JoinColumn(name = "range_unit_id", insertable = false, updatable = false)
	public Unit getRangeUnit() {
		return rangeUnit;
	}

	public void setRangeUnit(Unit rangeUnit) {
		this.rangeUnit = rangeUnit;
	}

	@OneToOne
	@JoinColumn(name = "site_variable_id", insertable = false, updatable = false)
	public SiteVariable getSiteVariable() {
		return siteVariable;
	}

	public void setSiteVariable(SiteVariable siteVariable) {
		this.siteVariable = siteVariable;
	}

	@Column(name = "site_variable_id")
	public Integer getSiteVariableId() {
		return siteVariableId;
	}

	public void setSiteVariableId(Integer siteVariableId) {
		this.siteVariableId = siteVariableId;
	}

}
