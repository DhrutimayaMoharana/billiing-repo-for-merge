package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.workorder.enums.MachineryRunningMode;

@Entity
@Table(name = "wo_hiring_machine_item_version")
public class WorkorderHiringMachineWorkItemMappingVersion implements Serializable {

	private static final long serialVersionUID = 3214813669922967435L;

	private Long id;

	private Long workorderHmWorkVersionId;

	private String description;

	private Long machineCategoryId;

	private Integer machineCount;

	private Double quantity;

	private Double rate;

	private MachineryRunningMode runningMode;

	private Short unitId;

	private Boolean thresholdApplicable;

	private Double thresholdQuantity;

	private Short thresholdUnitId;

	private Double postThresholdRatePerUnit;

	private Boolean isMultiFuel;

	private Double primaryEngineMileage;

	private Double secondaryEngineMileage;

	private Boolean dieselInclusive;

	private Double fuelHandlingCharge;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WorkorderHiringMachineWorkItemMappingVersion() {
		super();
	}

	public WorkorderHiringMachineWorkItemMappingVersion(Long id, Long workorderHmWorkVersionId, String description,
			Long machineCategoryId, Integer machineCount, Double quantity, Double rate,
			MachineryRunningMode runningMode, Short unitId, Boolean thresholdApplicable, Double thresholdQuantity,
			Short thresholdUnitId, Double postThresholdRatePerUnit, Boolean isMultiFuel, Double primaryEngineMileage,
			Double secondaryEngineMileage, Boolean dieselInclusive, Double fuelHandlingCharge, Boolean isActive,
			Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderHmWorkVersionId = workorderHmWorkVersionId;
		this.description = description;
		this.machineCategoryId = machineCategoryId;
		this.machineCount = machineCount;
		this.quantity = quantity;
		this.rate = rate;
		this.runningMode = runningMode;
		this.unitId = unitId;
		this.thresholdApplicable = thresholdApplicable;
		this.thresholdQuantity = thresholdQuantity;
		this.thresholdUnitId = thresholdUnitId;
		this.postThresholdRatePerUnit = postThresholdRatePerUnit;
		this.isMultiFuel = isMultiFuel;
		this.primaryEngineMileage = primaryEngineMileage;
		this.secondaryEngineMileage = secondaryEngineMileage;
		this.dieselInclusive = dieselInclusive;
		this.fuelHandlingCharge = fuelHandlingCharge;
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

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "running_mode")
	public MachineryRunningMode getRunningMode() {
		return runningMode;
	}

	public void setRunningMode(MachineryRunningMode runningMode) {
		this.runningMode = runningMode;
	}

	@Column(name = "unit_id")
	public Short getUnitId() {
		return unitId;
	}

	public void setUnitId(Short unitId) {
		this.unitId = unitId;
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

	@Column(name = "wo_hiring_machine_id")
	public Long getWorkorderHmWorkVersionId() {
		return workorderHmWorkVersionId;
	}

	public void setWorkorderHmWorkVersionId(Long workorderHmWorkVersionId) {
		this.workorderHmWorkVersionId = workorderHmWorkVersionId;
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

	@Column(name = "threshold_unit_id")
	public Short getThresholdUnitId() {
		return thresholdUnitId;
	}

	public void setThresholdUnitId(Short thresholdUnitId) {
		this.thresholdUnitId = thresholdUnitId;
	}

	@Column(name = "post_threshold_rate")
	public Double getPostThresholdRatePerUnit() {
		return postThresholdRatePerUnit;
	}

	public void setPostThresholdRatePerUnit(Double postThresholdRatePerUnit) {
		this.postThresholdRatePerUnit = postThresholdRatePerUnit;
	}

	@Column(name = "machine_category_id")
	public Long getMachineCategoryId() {
		return machineCategoryId;
	}

	public void setMachineCategoryId(Long machineCategoryId) {
		this.machineCategoryId = machineCategoryId;
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
}
