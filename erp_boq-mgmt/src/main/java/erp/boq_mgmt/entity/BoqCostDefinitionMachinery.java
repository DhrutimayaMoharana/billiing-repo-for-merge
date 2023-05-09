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
@Table(name = "boq_lmps_cost_defined_machinery")
public class BoqCostDefinitionMachinery {

	private Long id;

	private Long boqCostDefinitionId;

	private Long machineryCategoryId;

	private Boolean isMachineryLoaded;

	private Integer leadSiteVariableId;

	private Integer unitId;

	private Double rate;

	private Integer afterUnitId;

	private Double afterRate;

	private MachineryRangeType rangeType;

	private Integer rangeUnitId;

	private Double rangeQuantity;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	private EquipmentCategory machineryCategory;

	private Unit unit;

	private Unit afterUnit;

	private Unit rangeUnit;

	private SiteVariable leadSiteVariable;

	public BoqCostDefinitionMachinery() {
		super();
	}

	public BoqCostDefinitionMachinery(Long id, Long boqCostDefinitionId, Long machineryCategoryId,
			Boolean isMachineryLoaded, Integer leadSiteVariableId, Integer unitId, Double rate, Integer afterUnitId,
			Double afterRate, MachineryRangeType rangeType, Integer rangeUnitId, Double rangeQuantity, Boolean isActive,
			Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.boqCostDefinitionId = boqCostDefinitionId;
		this.machineryCategoryId = machineryCategoryId;
		this.isMachineryLoaded = isMachineryLoaded;
		this.leadSiteVariableId = leadSiteVariableId;
		this.unitId = unitId;
		this.rate = rate;
		this.afterUnitId = afterUnitId;
		this.afterRate = afterRate;
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

	@Column(name = "boq_lmps_cost_definition_id")
	public Long getBoqCostDefinitionId() {
		return boqCostDefinitionId;
	}

	public void setBoqCostDefinitionId(Long boqCostDefinitionId) {
		this.boqCostDefinitionId = boqCostDefinitionId;
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

	@Column(name = "lead_site_variable_id")
	public Integer getLeadSiteVariableId() {
		return leadSiteVariableId;
	}

	public void setLeadSiteVariableId(Integer leadSiteVariableId) {
		this.leadSiteVariableId = leadSiteVariableId;
	}

	@Column(name = "unit_id")
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "after_unit_id")
	public Integer getAfterUnitId() {
		return afterUnitId;
	}

	public void setAfterUnitId(Integer afterUnitId) {
		this.afterUnitId = afterUnitId;
	}

	@Column(name = "after_rate")
	public Double getAfterRate() {
		return afterRate;
	}

	public void setAfterRate(Double afterRate) {
		this.afterRate = afterRate;
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
	@JoinColumn(name = "lead_site_variable_id", insertable = false, updatable = false)
	public SiteVariable getLeadSiteVariable() {
		return leadSiteVariable;
	}

	public void setLeadSiteVariable(SiteVariable leadSiteVariable) {
		this.leadSiteVariable = leadSiteVariable;
	}

}
