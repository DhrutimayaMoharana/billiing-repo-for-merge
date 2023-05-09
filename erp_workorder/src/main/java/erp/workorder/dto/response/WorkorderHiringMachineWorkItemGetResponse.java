package erp.workorder.dto.response;

import java.util.List;

import erp.workorder.enums.MachineryRunningMode;

public class WorkorderHiringMachineWorkItemGetResponse {

	private Long hmWorkItemId;

	private String description;

	private Long machineCatgeoryId;

	private String machineCatgeoryName;

	private Integer machineCount;

	private Integer runningModeId;

	private MachineryRunningMode runningMode;

	private Short unitId;

	private String unitName;

	private Double quantity;

	private Double rate;

	private Double amount;

	private Boolean thresholdApplicable;

	private Double thresholdQuantity;

	private Short thresholdUnitId;

	private String thresholdUnitName;

	private Double postThresholdRatePerUnit;

	private Boolean isMultiFuel;

	private Double primaryEngineMileage;

	private Short primaryEngineMileageUnitId;

	private String primaryEngineMileageUnitName;

	private Double secondaryEngineMileage;

	private Short secondaryEngineMileageUnitId;

	private String secondaryEngineMileageUnitName;

	private Boolean dieselInclusive;

	private Double fuelHandlingCharge;

	private List<WorkorderHiringMachineRateDetailsResponse> machineRateDetails;

	public WorkorderHiringMachineWorkItemGetResponse() {
		super();
	}

	public WorkorderHiringMachineWorkItemGetResponse(Long hmWorkItemId, String description, Long machineCatgeoryId,
			String machineCatgeoryName, Integer machineCount, Integer runningModeId, MachineryRunningMode runningMode,
			Short unitId, String unitName, Double quantity, Double rate, Double amount, Boolean thresholdApplicable,
			Double thresholdQuantity, Short thresholdUnitId, String thresholdUnitName, Double postThresholdRatePerUnit,
			Boolean isMultiFuel, Double primaryEngineMileage, Short primaryEngineMileageUnitId,
			String primaryEngineMileageUnitName, Double secondaryEngineMileage, Short secondaryEngineMileageUnitId,
			String secondaryEngineMileageUnitName, Boolean dieselInclusive, Double fuelHandlingCharge) {
		super();
		this.hmWorkItemId = hmWorkItemId;
		this.description = description;
		this.machineCatgeoryId = machineCatgeoryId;
		this.machineCatgeoryName = machineCatgeoryName;
		this.machineCount = machineCount;
		this.runningModeId = runningModeId;
		this.runningMode = runningMode;
		this.unitId = unitId;
		this.unitName = unitName;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
		this.thresholdApplicable = thresholdApplicable;
		this.thresholdQuantity = thresholdQuantity;
		this.thresholdUnitId = thresholdUnitId;
		this.thresholdUnitName = thresholdUnitName;
		this.postThresholdRatePerUnit = postThresholdRatePerUnit;
		this.isMultiFuel = isMultiFuel;
		this.primaryEngineMileage = primaryEngineMileage;
		this.primaryEngineMileageUnitId = primaryEngineMileageUnitId;
		this.primaryEngineMileageUnitName = primaryEngineMileageUnitName;
		this.secondaryEngineMileage = secondaryEngineMileage;
		this.secondaryEngineMileageUnitId = secondaryEngineMileageUnitId;
		this.secondaryEngineMileageUnitName = secondaryEngineMileageUnitName;
		this.dieselInclusive = dieselInclusive;
		this.setFuelHandlingCharge(fuelHandlingCharge);
	}

	public Long getHmWorkItemId() {
		return hmWorkItemId;
	}

	public void setHmWorkItemId(Long hmWorkItemId) {
		this.hmWorkItemId = hmWorkItemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getMachineCatgeoryId() {
		return machineCatgeoryId;
	}

	public void setMachineCatgeoryId(Long machineCatgeoryId) {
		this.machineCatgeoryId = machineCatgeoryId;
	}

	public String getMachineCatgeoryName() {
		return machineCatgeoryName;
	}

	public void setMachineCatgeoryName(String machineCatgeoryName) {
		this.machineCatgeoryName = machineCatgeoryName;
	}

	public Integer getMachineCount() {
		return machineCount;
	}

	public void setMachineCount(Integer machineCount) {
		this.machineCount = machineCount;
	}

	public Integer getRunningModeId() {
		return runningModeId;
	}

	public void setRunningModeId(Integer runningModeId) {
		this.runningModeId = runningModeId;
	}

	public MachineryRunningMode getRunningMode() {
		return runningMode;
	}

	public void setRunningMode(MachineryRunningMode runningMode) {
		this.runningMode = runningMode;
	}

	public Short getUnitId() {
		return unitId;
	}

	public void setUnitId(Short unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getThresholdApplicable() {
		return thresholdApplicable;
	}

	public void setThresholdApplicable(Boolean thresholdApplicable) {
		this.thresholdApplicable = thresholdApplicable;
	}

	public Double getThresholdQuantity() {
		return thresholdQuantity;
	}

	public void setThresholdQuantity(Double thresholdQuantity) {
		this.thresholdQuantity = thresholdQuantity;
	}

	public Short getThresholdUnitId() {
		return thresholdUnitId;
	}

	public void setThresholdUnitId(Short thresholdUnitId) {
		this.thresholdUnitId = thresholdUnitId;
	}

	public String getThresholdUnitName() {
		return thresholdUnitName;
	}

	public void setThresholdUnitName(String thresholdUnitName) {
		this.thresholdUnitName = thresholdUnitName;
	}

	public Double getPostThresholdRatePerUnit() {
		return postThresholdRatePerUnit;
	}

	public void setPostThresholdRatePerUnit(Double postThresholdRatePerUnit) {
		this.postThresholdRatePerUnit = postThresholdRatePerUnit;
	}

	public Boolean getIsMultiFuel() {
		return isMultiFuel;
	}

	public void setIsMultiFuel(Boolean isMultiFuel) {
		this.isMultiFuel = isMultiFuel;
	}

	public Double getPrimaryEngineMileage() {
		return primaryEngineMileage;
	}

	public void setPrimaryEngineMileage(Double primaryEngineMileage) {
		this.primaryEngineMileage = primaryEngineMileage;
	}

	public Short getPrimaryEngineMileageUnitId() {
		return primaryEngineMileageUnitId;
	}

	public void setPrimaryEngineMileageUnitId(Short primaryEngineMileageUnitId) {
		this.primaryEngineMileageUnitId = primaryEngineMileageUnitId;
	}

	public String getPrimaryEngineMileageUnitName() {
		return primaryEngineMileageUnitName;
	}

	public void setPrimaryEngineMileageUnitName(String primaryEngineMileageUnitName) {
		this.primaryEngineMileageUnitName = primaryEngineMileageUnitName;
	}

	public Double getSecondaryEngineMileage() {
		return secondaryEngineMileage;
	}

	public void setSecondaryEngineMileage(Double secondaryEngineMileage) {
		this.secondaryEngineMileage = secondaryEngineMileage;
	}

	public Short getSecondaryEngineMileageUnitId() {
		return secondaryEngineMileageUnitId;
	}

	public void setSecondaryEngineMileageUnitId(Short secondaryEngineMileageUnitId) {
		this.secondaryEngineMileageUnitId = secondaryEngineMileageUnitId;
	}

	public String getSecondaryEngineMileageUnitName() {
		return secondaryEngineMileageUnitName;
	}

	public void setSecondaryEngineMileageUnitName(String secondaryEngineMileageUnitName) {
		this.secondaryEngineMileageUnitName = secondaryEngineMileageUnitName;
	}

	public Boolean getDieselInclusive() {
		return dieselInclusive;
	}

	public void setDieselInclusive(Boolean dieselInclusive) {
		this.dieselInclusive = dieselInclusive;
	}

	public Double getFuelHandlingCharge() {
		return fuelHandlingCharge;
	}

	public void setFuelHandlingCharge(Double fuelHandlingCharge) {
		this.fuelHandlingCharge = fuelHandlingCharge;
	}

	public List<WorkorderHiringMachineRateDetailsResponse> getMachineRateDetails() {
		return machineRateDetails;
	}

	public void setMachineRateDetails(List<WorkorderHiringMachineRateDetailsResponse> machineRateDetails) {
		this.machineRateDetails = machineRateDetails;
	}

}
