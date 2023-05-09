package erp.workorder.dto.request;

import java.util.List;

import erp.workorder.dto.UserDetail;
import erp.workorder.enums.MachineryRunningMode;

public class WorkorderHiringMachineWorkItemAddUpdateRequest {

	private Long hmWorkId;

	private Long hmWorkItemId;

	private Long machineCategoryId;

	private Integer machineCount;

	private MachineryRunningMode runningMode;

	private String description;

	private Double quantity;

	private Short unitId;

	private Double rate;

	private Boolean thresholdApplicable;

	private Double thresholdQuantity;

	private Short thresholdUnitId;

	private Double postThresholdRatePerUnit;

	private Boolean isMultiFuel;

	private Double primaryEngineMileage;

	private Short primaryEngineMileageUnitId;

	private Double secondaryEngineMileage;

	private Short secondaryEngineMileageUnitId;

	private Boolean dieselInclusive;

	private Double fuelHandlingCharge;

	private Long siteId;

	private List<WorkorderHiringMachineRateDetailsAddUpdateRequest> machineRateDetails;

	private UserDetail userDetail;

	public WorkorderHiringMachineWorkItemAddUpdateRequest() {
		super();
	}

	public WorkorderHiringMachineWorkItemAddUpdateRequest(Long hmWorkId, Long hmWorkItemId, Long machineCategoryId,
			Integer machineCount, String description, Double quantity, Short unitId, Double rate,
			Boolean thresholdApplicable, Double thresholdQuantity, Short thresholdUnitId,
			Double postThresholdRatePerUnit, Boolean dieselInclusive, Double fuelHandlingCharge) {
		super();
		this.hmWorkId = hmWorkId;
		this.hmWorkItemId = hmWorkItemId;
		this.machineCategoryId = machineCategoryId;
		this.setMachineCount(machineCount);
		this.setDescription(description);
		this.quantity = quantity;
		this.unitId = unitId;
		this.setFuelHandlingCharge(fuelHandlingCharge);
		this.rate = rate;
		this.thresholdApplicable = thresholdApplicable;
		this.thresholdQuantity = thresholdQuantity;
		this.thresholdUnitId = thresholdUnitId;
		this.postThresholdRatePerUnit = postThresholdRatePerUnit;
		this.dieselInclusive = dieselInclusive;
	}

	public Long getHmWorkId() {
		return hmWorkId;
	}

	public void setHmWorkId(Long hmWorkId) {
		this.hmWorkId = hmWorkId;
	}

	public Long getHmWorkItemId() {
		return hmWorkItemId;
	}

	public void setHmWorkItemId(Long hmWorkItemId) {
		this.hmWorkItemId = hmWorkItemId;
	}

	public Long getMachineCategoryId() {
		return machineCategoryId;
	}

	public void setMachineCategoryId(Long machineCategoryId) {
		this.machineCategoryId = machineCategoryId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Short getUnitId() {
		return unitId;
	}

	public void setUnitId(Short unitId) {
		this.unitId = unitId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
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

	public Double getPostThresholdRatePerUnit() {
		return postThresholdRatePerUnit;
	}

	public void setPostThresholdRatePerUnit(Double postThresholdRatePerUnit) {
		this.postThresholdRatePerUnit = postThresholdRatePerUnit;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public Integer getMachineCount() {
		return machineCount;
	}

	public void setMachineCount(Integer machineCount) {
		this.machineCount = machineCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<WorkorderHiringMachineRateDetailsAddUpdateRequest> getMachineRateDetails() {
		return machineRateDetails;
	}

	public void setMachineRateDetails(List<WorkorderHiringMachineRateDetailsAddUpdateRequest> machineRateDetails) {
		this.machineRateDetails = machineRateDetails;
	}

	public MachineryRunningMode getRunningMode() {
		return runningMode;
	}

	public void setRunningMode(MachineryRunningMode runningMode) {
		this.runningMode = runningMode;
	}

}
