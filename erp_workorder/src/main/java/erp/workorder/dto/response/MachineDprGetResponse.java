package erp.workorder.dto.response;

import java.util.Date;

import erp.workorder.enums.MachineryAttendanceStatus;
import erp.workorder.enums.MachineryRunningMode;
import erp.workorder.enums.MachineryShifts;

public class MachineDprGetResponse {

	private Long dprId;

	private Date dated;

	private MachineryRunningMode runningMode;

	private MachineryShifts shift;

	private Double primaryOpeningMeterReading;

	private Double primaryClosingMeterReading;

	private Double secondaryOpeningMeterReading;

	private Double secondaryClosingMeterReading;

	private Double primaryOpeningActualReading;

	private Double primaryClosingActualReading;

	private Double secondaryOpeningActualReading;

	private Double secondaryClosingActualReading;

	private Double netPrimaryMeterReading;

	private Double netPrimaryActualReading;

	private Double netSecondaryMeterReading;

	private Double netSecondaryActualReading;

	private Integer tripCount;

	private String remarks;

	private MachineryAttendanceStatus attendanceStatus;

	private Double breakdownHours;

	public MachineDprGetResponse(Long dprId, Date dated, MachineryRunningMode runningMode, MachineryShifts shift,
			Double primaryOpeningMeterReading, Double primaryClosingMeterReading, Double secondaryOpeningMeterReading,
			Double secondaryClosingMeterReading, Double primaryOpeningActualReading, Double primaryClosingActualReading,
			Double secondaryOpeningActualReading, Double secondaryClosingActualReading, Double netPrimaryMeterReading,
			Double netPrimaryActualReading, Double netSecondaryMeterReading, Double netSecondaryActualReading,
			Integer tripCount, String remarks, MachineryAttendanceStatus attendanceStatus, Double breakdownHours) {
		super();
		this.dprId = dprId;
		this.dated = dated;
		this.runningMode = runningMode;
		this.shift = shift;
		this.primaryOpeningMeterReading = primaryOpeningMeterReading;
		this.primaryClosingMeterReading = primaryClosingMeterReading;
		this.secondaryOpeningMeterReading = secondaryOpeningMeterReading;
		this.secondaryClosingMeterReading = secondaryClosingMeterReading;
		this.primaryOpeningActualReading = primaryOpeningActualReading;
		this.primaryClosingActualReading = primaryClosingActualReading;
		this.secondaryOpeningActualReading = secondaryOpeningActualReading;
		this.secondaryClosingActualReading = secondaryClosingActualReading;
		this.netPrimaryMeterReading = netPrimaryMeterReading;
		this.netPrimaryActualReading = netPrimaryActualReading;
		this.netSecondaryMeterReading = netSecondaryMeterReading;
		this.netSecondaryActualReading = netSecondaryActualReading;
		this.tripCount = tripCount;
		this.remarks = remarks;
		this.attendanceStatus = attendanceStatus;
		this.breakdownHours = breakdownHours;
	}

	public Long getDprId() {
		return dprId;
	}

	public void setDprId(Long dprId) {
		this.dprId = dprId;
	}

	public Date getDated() {
		return dated;
	}

	public void setDated(Date dated) {
		this.dated = dated;
	}

	public Double getNetPrimaryMeterReading() {
		return netPrimaryMeterReading;
	}

	public void setNetPrimaryMeterReading(Double netPrimaryMeterReading) {
		this.netPrimaryMeterReading = netPrimaryMeterReading;
	}

	public Double getNetPrimaryActualReading() {
		return netPrimaryActualReading;
	}

	public void setNetPrimaryActualReading(Double netPrimaryActualReading) {
		this.netPrimaryActualReading = netPrimaryActualReading;
	}

	public Double getNetSecondaryMeterReading() {
		return netSecondaryMeterReading;
	}

	public void setNetSecondaryMeterReading(Double netSecondaryMeterReading) {
		this.netSecondaryMeterReading = netSecondaryMeterReading;
	}

	public Double getNetSecondaryActualReading() {
		return netSecondaryActualReading;
	}

	public void setNetSecondaryActualReading(Double netSecondaryActualReading) {
		this.netSecondaryActualReading = netSecondaryActualReading;
	}

	public Double getPrimaryOpeningMeterReading() {
		return primaryOpeningMeterReading;
	}

	public void setPrimaryOpeningMeterReading(Double primaryOpeningMeterReading) {
		this.primaryOpeningMeterReading = primaryOpeningMeterReading;
	}

	public Double getPrimaryClosingMeterReading() {
		return primaryClosingMeterReading;
	}

	public void setPrimaryClosingMeterReading(Double primaryClosingMeterReading) {
		this.primaryClosingMeterReading = primaryClosingMeterReading;
	}

	public Double getSecondaryOpeningMeterReading() {
		return secondaryOpeningMeterReading;
	}

	public void setSecondaryOpeningMeterReading(Double secondaryOpeningMeterReading) {
		this.secondaryOpeningMeterReading = secondaryOpeningMeterReading;
	}

	public Double getSecondaryClosingMeterReading() {
		return secondaryClosingMeterReading;
	}

	public void setSecondaryClosingMeterReading(Double secondaryClosingMeterReading) {
		this.secondaryClosingMeterReading = secondaryClosingMeterReading;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getPrimaryOpeningActualReading() {
		return primaryOpeningActualReading;
	}

	public void setPrimaryOpeningActualReading(Double primaryOpeningActualReading) {
		this.primaryOpeningActualReading = primaryOpeningActualReading;
	}

	public Double getPrimaryClosingActualReading() {
		return primaryClosingActualReading;
	}

	public void setPrimaryClosingActualReading(Double primaryClosingActualReading) {
		this.primaryClosingActualReading = primaryClosingActualReading;
	}

	public Double getSecondaryOpeningActualReading() {
		return secondaryOpeningActualReading;
	}

	public void setSecondaryOpeningActualReading(Double secondaryOpeningActualReading) {
		this.secondaryOpeningActualReading = secondaryOpeningActualReading;
	}

	public Double getSecondaryClosingActualReading() {
		return secondaryClosingActualReading;
	}

	public void setSecondaryClosingActualReading(Double secondaryClosingActualReading) {
		this.secondaryClosingActualReading = secondaryClosingActualReading;
	}

	public MachineryAttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(MachineryAttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public MachineryRunningMode getRunningMode() {
		return runningMode;
	}

	public void setRunningMode(MachineryRunningMode runningMode) {
		this.runningMode = runningMode;
	}

	public MachineryShifts getShift() {
		return shift;
	}

	public void setShift(MachineryShifts shift) {
		this.shift = shift;
	}

	public Integer getTripCount() {
		return tripCount;
	}

	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
	}

	public Double getBreakdownHours() {
		return breakdownHours;
	}

	public void setBreakdownHours(Double breakdownHours) {
		this.breakdownHours = breakdownHours;
	}

}
