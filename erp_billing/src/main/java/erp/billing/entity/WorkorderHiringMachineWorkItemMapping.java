package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import erp.billing.enums.MachineryRunningMode;

@Entity
@Table(name = "wo_hiring_machine_items")
public class WorkorderHiringMachineWorkItemMapping implements Serializable {

	private static final long serialVersionUID = 3214813669922967435L;

	private Long id;

	private Long workorderHmWorkId;

	private String description;

	private Long machineCatgeoryId;

	private EquipmentCategory machineCategory;

	private Integer machineCount;

	private Double quantity;

	private MachineryRunningMode runningMode;

	private Double rate;

	private UnitMaster unit;

	private Boolean thresholdApplicable;

	private Double thresholdQuantity;

	private UnitMaster thresholdUnit;

	private Double postThresholdRatePerUnit;

	private Boolean isMultiFuel;

	private Double primaryEngineMileage;

	private Double secondaryEngineMileage;

	private Boolean dieselInclusive;

	private Double fuelHandlingCharge;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private WorkorderHiringMachineWork workorderHmWork;

	public WorkorderHiringMachineWorkItemMapping() {
		super();
	}

	public WorkorderHiringMachineWorkItemMapping(Long id) {
		super();
		this.id = id;
	}

	public WorkorderHiringMachineWorkItemMapping(Long id, Long workorderHmWorkId, String description,
			Long machineCatgeoryId, Integer machineCount, Double quantity, MachineryRunningMode runningMode,
			Double rate, UnitMaster unit, Boolean thresholdApplicable, Double thresholdQuantity,
			UnitMaster thresholdUnit, Double postThresholdRatePerUnit, Boolean isMultiFuel, Double primaryEngineMileage,
			Double secondaryEngineMileage, Boolean dieselInclusive, Double fuelHandlingCharge, Boolean isActive,
			Date createdOn, Long createdBy, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderHmWorkId = workorderHmWorkId;
		this.description = description;
		this.machineCatgeoryId = machineCatgeoryId;
		this.machineCount = machineCount;
		this.quantity = quantity;
		this.runningMode = runningMode;
		this.rate = rate;
		this.unit = unit;
		this.thresholdApplicable = thresholdApplicable;
		this.thresholdQuantity = thresholdQuantity;
		this.thresholdUnit = thresholdUnit;
		this.isMultiFuel = isMultiFuel;
		this.postThresholdRatePerUnit = postThresholdRatePerUnit;
		this.primaryEngineMileage = primaryEngineMileage;
		this.secondaryEngineMileage = secondaryEngineMileage;
		this.dieselInclusive = dieselInclusive;
		this.fuelHandlingCharge = fuelHandlingCharge;
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

	@OneToOne
	@JoinColumn(name = "wo_hiring_machine_id", insertable = false, updatable = false)
	public WorkorderHiringMachineWork getWorkorderHmWork() {
		return workorderHmWork;
	}

	public void setWorkorderHmWork(WorkorderHiringMachineWork workorderHmWork) {
		this.workorderHmWork = workorderHmWork;
	}

	@Column(name = "wo_hiring_machine_id")
	public Long getWorkorderHmWorkId() {
		return workorderHmWorkId;
	}

	public void setWorkorderHmWorkId(Long workorderHmWorkId) {
		this.workorderHmWorkId = workorderHmWorkId;
	}

	@Column(name = "machine_category_id")
	public Long getMachineCatgeoryId() {
		return machineCatgeoryId;
	}

	public void setMachineCatgeoryId(Long machineCatgeoryId) {
		this.machineCatgeoryId = machineCatgeoryId;
	}

	@Column(name = "machine_count")
	public Integer getMachineCount() {
		return machineCount;
	}

	public void setMachineCount(Integer machineCount) {
		this.machineCount = machineCount;
	}

	@Column(name = "threshold_applicable")
	public Boolean getThresholdApplicable() {
		return thresholdApplicable;
	}

	public void setThresholdApplicable(Boolean thresholdApplicable) {
		this.thresholdApplicable = thresholdApplicable;
	}

	@Column(name = "threshold_quantity")
	public Double getThresholdQuantity() {
		return thresholdQuantity;
	}

	public void setThresholdQuantity(Double thresholdQuantity) {
		this.thresholdQuantity = thresholdQuantity;
	}

	@OneToOne
	@JoinColumn(name = "threshold_unit_id")
	public UnitMaster getThresholdUnit() {
		return thresholdUnit;
	}

	public void setThresholdUnit(UnitMaster thresholdUnit) {
		this.thresholdUnit = thresholdUnit;
	}

	@Column(name = "post_threshold_rate")
	public Double getPostThresholdRatePerUnit() {
		return postThresholdRatePerUnit;
	}

	public void setPostThresholdRatePerUnit(Double postThresholdRatePerUnit) {
		this.postThresholdRatePerUnit = postThresholdRatePerUnit;
	}

	@OneToOne
	@JoinColumn(name = "machine_category_id", insertable = false, updatable = false)
	public EquipmentCategory getMachineCategory() {
		return machineCategory;
	}

	public void setMachineCategory(EquipmentCategory machineCategory) {
		this.machineCategory = machineCategory;
	}

	@Column(name = "primary_engine_mileage")
	public Double getPrimaryEngineMileage() {
		return primaryEngineMileage;
	}

	public void setPrimaryEngineMileage(Double primaryEngineMileage) {
		this.primaryEngineMileage = primaryEngineMileage;
	}

	@Column(name = "secondary_engine_mileage")
	public Double getSecondaryEngineMileage() {
		return secondaryEngineMileage;
	}

	public void setSecondaryEngineMileage(Double secondaryEngineMileage) {
		this.secondaryEngineMileage = secondaryEngineMileage;
	}

	@Column(name = "is_multi_fuel")
	public Boolean getIsMultiFuel() {
		return isMultiFuel;
	}

	public void setIsMultiFuel(Boolean isMultiFuel) {
		this.isMultiFuel = isMultiFuel;
	}

	@Column(name = "diesel_inclusive")
	public Boolean getDieselInclusive() {
		return dieselInclusive;
	}

	public void setDieselInclusive(Boolean dieselInclusive) {
		this.dieselInclusive = dieselInclusive;
	}

	@Column(name = "fuel_handling_charge")
	public Double getFuelHandlingCharge() {
		return fuelHandlingCharge;
	}

	public void setFuelHandlingCharge(Double fuelHandlingCharge) {
		this.fuelHandlingCharge = fuelHandlingCharge;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "running_mode")
	public MachineryRunningMode getRunningMode() {
		return runningMode;
	}

	public void setRunningMode(MachineryRunningMode runningMode) {
		this.runningMode = runningMode;
	}

}
