package erp.billing.dto.response;

import java.util.Date;
import java.util.List;

public class BillMachineMapResponse {

	private Long billMachineMapId;

	private Long machineId;

	private String assetCode;

	private String regNo;

	private Byte machineType;

	private Date fromDate;

	private Date toDate;

	private List<MachineDprGetResponse> dprList;

	private Float totalWorkingDays;

	private Float totalAbsentInDayTime;

	private Float totalAbsentInNightTime;

	private Float totalPresentInDayTime;

	private Float totalPresentInNightTime;

	private Double netTotalPrimaryActualReading;

	private Double netTotalSecondaryActualReading;

	private Double primaryEngineMileage;

	private String primaryEngineMileageUnit;

	private Double secondaryEngineMileage;

	private String secondaryEngineMileageUnit;

	private Double totalFuelAsPerWorkorder;

	private Double fuelTakenFromStore;

	private Double dieselRateAsPerWorkorder;

	private Double fuelRateAsPerWorkorderIncludingHandlingCharge;

	private Double totalFuelRateAsPerWorkorder;

	private Double totalFuelRateAsPerWeightedRate;

	private Double dieselEscalationPrice;

	private Double excessFuelTaken;

	private Double fuelRateIncludingHandlingCharge;

	private Double fuelDebitAmount;

	private Boolean isShiftBased;

	private Double machineRateAsPerWorkorderDayShift;

	private Double machineRateAsPerWorkorderNightShift;

	private Integer tripCount;

	private String machineRateUnitAsPerWorkorder;

	private Double totalDaysAsPerRateUnit;

	private Boolean isThresholdApplicable;

	private Double thresholdQuantity;

	private String thresholdQuantityUnit;

	private Double totalAmount;

	private Double totalBreakdownHoursInDayShift;

	private Double totalBreakdownHoursInNightShift;

	private Double totalBreakdownHoursAmount;

	private Double totalBreakdownHours;

	public BillMachineMapResponse() {
		super();
	}

	public BillMachineMapResponse(Long billMachineMapId, Long machineId, String assetCode, String regNo,
			Byte machineType, Date fromDate, Date toDate) {
		super();
		this.billMachineMapId = billMachineMapId;
		this.machineId = machineId;
		this.assetCode = assetCode;
		this.regNo = regNo;
		this.machineType = machineType;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public Boolean getIsShiftBased() {
		return isShiftBased;
	}

	public void setIsShiftBased(Boolean isShiftBased) {
		this.isShiftBased = isShiftBased;
	}

	public Double getMachineRateAsPerWorkorderDayShift() {
		return machineRateAsPerWorkorderDayShift;
	}

	public void setMachineRateAsPerWorkorderDayShift(Double machineRateAsPerWorkorderDayShift) {
		this.machineRateAsPerWorkorderDayShift = machineRateAsPerWorkorderDayShift;
	}

	public Double getMachineRateAsPerWorkorderNightShift() {
		return machineRateAsPerWorkorderNightShift;
	}

	public void setMachineRateAsPerWorkorderNightShift(Double machineRateAsPerWorkorderNightShift) {
		this.machineRateAsPerWorkorderNightShift = machineRateAsPerWorkorderNightShift;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Byte getMachineType() {
		return machineType;
	}

	public void setMachineType(Byte machineType) {
		this.machineType = machineType;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public Long getBillMachineMapId() {
		return billMachineMapId;
	}

	public void setBillMachineMapId(Long billMachineMapId) {
		this.billMachineMapId = billMachineMapId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<MachineDprGetResponse> getDprList() {
		return dprList;
	}

	public void setDprList(List<MachineDprGetResponse> dprList) {
		this.dprList = dprList;
	}

	public Float getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(Float totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	public Double getNetTotalPrimaryActualReading() {
		return netTotalPrimaryActualReading;
	}

	public void setNetTotalPrimaryActualReading(Double netTotalPrimaryActualReading) {
		this.netTotalPrimaryActualReading = netTotalPrimaryActualReading;
	}

	public Double getNetTotalSecondaryActualReading() {
		return netTotalSecondaryActualReading;
	}

	public void setNetTotalSecondaryActualReading(Double netTotalSecondaryActualReading) {
		this.netTotalSecondaryActualReading = netTotalSecondaryActualReading;
	}

	public Double getPrimaryEngineMileage() {
		return primaryEngineMileage;
	}

	public void setPrimaryEngineMileage(Double primaryEngineMileage) {
		this.primaryEngineMileage = primaryEngineMileage;
	}

	public String getPrimaryEngineMileageUnit() {
		return primaryEngineMileageUnit;
	}

	public void setPrimaryEngineMileageUnit(String primaryEngineMileageUnit) {
		this.primaryEngineMileageUnit = primaryEngineMileageUnit;
	}

	public Double getSecondaryEngineMileage() {
		return secondaryEngineMileage;
	}

	public void setSecondaryEngineMileage(Double secondaryEngineMileage) {
		this.secondaryEngineMileage = secondaryEngineMileage;
	}

	public String getSecondaryEngineMileageUnit() {
		return secondaryEngineMileageUnit;
	}

	public void setSecondaryEngineMileageUnit(String secondaryEngineMileageUnit) {
		this.secondaryEngineMileageUnit = secondaryEngineMileageUnit;
	}

	public Double getTotalFuelAsPerWorkorder() {
		return totalFuelAsPerWorkorder;
	}

	public void setTotalFuelAsPerWorkorder(Double totalFuelAsPerWorkorder) {
		this.totalFuelAsPerWorkorder = totalFuelAsPerWorkorder;
	}

	public Double getExcessFuelTaken() {
		return excessFuelTaken;
	}

	public void setExcessFuelTaken(Double excessFuelTaken) {
		this.excessFuelTaken = excessFuelTaken;
	}

	public Double getFuelRateIncludingHandlingCharge() {
		return fuelRateIncludingHandlingCharge;
	}

	public void setFuelRateIncludingHandlingCharge(Double fuelRateIncludingHandlingCharge) {
		this.fuelRateIncludingHandlingCharge = fuelRateIncludingHandlingCharge;
	}

	public Double getFuelDebitAmount() {
		return fuelDebitAmount;
	}

	public void setFuelDebitAmount(Double fuelDebitAmount) {
		this.fuelDebitAmount = fuelDebitAmount;
	}

	public String getMachineRateUnitAsPerWorkorder() {
		return machineRateUnitAsPerWorkorder;
	}

	public void setMachineRateUnitAsPerWorkorder(String machineRateUnitAsPerWorkorder) {
		this.machineRateUnitAsPerWorkorder = machineRateUnitAsPerWorkorder;
	}

	public Double getTotalDaysAsPerRateUnit() {
		return totalDaysAsPerRateUnit;
	}

	public void setTotalDaysAsPerRateUnit(Double totalDaysAsPerRateUnit) {
		this.totalDaysAsPerRateUnit = totalDaysAsPerRateUnit;
	}

	public Double getThresholdQuantity() {
		return thresholdQuantity;
	}

	public void setThresholdQuantity(Double thresholdQuantity) {
		this.thresholdQuantity = thresholdQuantity;
	}

	public String getThresholdQuantityUnit() {
		return thresholdQuantityUnit;
	}

	public void setThresholdQuantityUnit(String thresholdQuantityUnit) {
		this.thresholdQuantityUnit = thresholdQuantityUnit;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Boolean getIsThresholdApplicable() {
		return isThresholdApplicable;
	}

	public void setIsThresholdApplicable(Boolean isThresholdApplicable) {
		this.isThresholdApplicable = isThresholdApplicable;
	}

	public Double getFuelTakenFromStore() {
		return fuelTakenFromStore;
	}

	public void setFuelTakenFromStore(Double fuelTakenFromStore) {
		this.fuelTakenFromStore = fuelTakenFromStore;
	}

	public Float getTotalAbsentInDayTime() {
		return totalAbsentInDayTime;
	}

	public void setTotalAbsentInDayTime(Float totalAbsentInDayTime) {
		this.totalAbsentInDayTime = totalAbsentInDayTime;
	}

	public Float getTotalAbsentInNightTime() {
		return totalAbsentInNightTime;
	}

	public void setTotalAbsentInNightTime(Float totalAbsentInNightTime) {
		this.totalAbsentInNightTime = totalAbsentInNightTime;
	}

	public Float getTotalPresentInDayTime() {
		return totalPresentInDayTime;
	}

	public void setTotalPresentInDayTime(Float totalPresentInDayTime) {
		this.totalPresentInDayTime = totalPresentInDayTime;
	}

	public Float getTotalPresentInNightTime() {
		return totalPresentInNightTime;
	}

	public void setTotalPresentInNightTime(Float totalPresentInNightTime) {
		this.totalPresentInNightTime = totalPresentInNightTime;
	}

	public Integer getTripCount() {
		return tripCount;
	}

	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
	}

	public Double getDieselRateAsPerWorkorder() {
		return dieselRateAsPerWorkorder;
	}

	public void setDieselRateAsPerWorkorder(Double dieselRateAsPerWorkorder) {
		this.dieselRateAsPerWorkorder = dieselRateAsPerWorkorder;
	}

	public Double getFuelRateAsPerWorkorderIncludingHandlingCharge() {
		return fuelRateAsPerWorkorderIncludingHandlingCharge;
	}

	public void setFuelRateAsPerWorkorderIncludingHandlingCharge(Double fuelRateAsPerWorkorderIncludingHandlingCharge) {
		this.fuelRateAsPerWorkorderIncludingHandlingCharge = fuelRateAsPerWorkorderIncludingHandlingCharge;
	}

	public Double getDieselEscalationPrice() {
		return dieselEscalationPrice;
	}

	public void setDieselEscalationPrice(Double dieselEscalationPrice) {
		this.dieselEscalationPrice = dieselEscalationPrice;
	}

	public Double getTotalFuelRateAsPerWorkorder() {
		return totalFuelRateAsPerWorkorder;
	}

	public void setTotalFuelRateAsPerWorkorder(Double totalFuelRateAsPerWorkorder) {
		this.totalFuelRateAsPerWorkorder = totalFuelRateAsPerWorkorder;
	}

	public Double getTotalFuelRateAsPerWeightedRate() {
		return totalFuelRateAsPerWeightedRate;
	}

	public void setTotalFuelRateAsPerWeightedRate(Double totalFuelRateAsPerWeightedRate) {
		this.totalFuelRateAsPerWeightedRate = totalFuelRateAsPerWeightedRate;
	}

	public Double getTotalBreakdownHoursInDayShift() {
		return totalBreakdownHoursInDayShift;
	}

	public void setTotalBreakdownHoursInDayShift(Double totalBreakdownHoursInDayShift) {
		this.totalBreakdownHoursInDayShift = totalBreakdownHoursInDayShift;
	}

	public Double getTotalBreakdownHoursInNightShift() {
		return totalBreakdownHoursInNightShift;
	}

	public void setTotalBreakdownHoursInNightShift(Double totalBreakdownHoursInNightShift) {
		this.totalBreakdownHoursInNightShift = totalBreakdownHoursInNightShift;
	}

	public Double getTotalBreakdownHoursAmount() {
		return totalBreakdownHoursAmount;
	}

	public void setTotalBreakdownHoursAmount(Double totalBreakdownHoursAmount) {
		this.totalBreakdownHoursAmount = totalBreakdownHoursAmount;
	}

	public Double getTotalBreakdownHours() {
		return totalBreakdownHours;
	}

	public void setTotalBreakdownHours(Double totalBreakdownHours) {
		this.totalBreakdownHours = totalBreakdownHours;
	}

}
