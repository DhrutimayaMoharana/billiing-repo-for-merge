package erp.workorder.dto.request;

import java.util.Date;

import erp.workorder.dto.UserDetail;
import erp.workorder.enums.MachineryAttendanceStatus;
import erp.workorder.enums.MachineryRunningMode;
import erp.workorder.enums.MachineryShifts;

public class MachineDprAddUpdateRequest {

	private Long id;

	private Date dated;

	private MachineryRunningMode runningMode;

	private MachineryShifts shift;

	private Byte machineType;

	private Long machineId;

	private Double primaryOpeningMeterReading;

	private Double primaryClosingMeterReading;

	private Double secondaryOpeningMeterReading;

	private Double secondaryClosingMeterReading;

	private Double primaryOpeningActualReading;

	private Double primaryClosingActualReading;

	private Double secondaryOpeningActualReading;

	private Double secondaryClosingActualReading;

	private Integer tripCount;

	private String remarks;

	private Double breakdownHours;

	private MachineryAttendanceStatus attendanceStatus;

	private Long siteId;

	private UserDetail userDetail;

	public MachineDprAddUpdateRequest() {
		super();
	}

	public MachineDprAddUpdateRequest(Long id) {
		super();
		this.id = id;
	}

	public MachineDprAddUpdateRequest(Long id, Date dated, Byte machineType, Long machineId,
			Double primaryOpeningMeterReading, Double primaryClosingMeterReading, Double secondaryOpeningMeterReading,
			Double secondaryClosingMeterReading, Double primaryOpeningActualReading, Double primaryClosingActualReading,
			Double secondaryOpeningActualReading, Double secondaryClosingActualReading, String remarks,
			Double breakdownHours, Long siteId, UserDetail userDetail) {
		super();
		this.id = id;
		this.dated = dated;
		this.machineType = machineType;
		this.machineId = machineId;
		this.primaryOpeningMeterReading = primaryOpeningMeterReading;
		this.primaryClosingMeterReading = primaryClosingMeterReading;
		this.secondaryOpeningMeterReading = secondaryOpeningMeterReading;
		this.secondaryClosingMeterReading = secondaryClosingMeterReading;
		this.primaryOpeningActualReading = primaryOpeningActualReading;
		this.primaryClosingActualReading = primaryClosingActualReading;
		this.secondaryOpeningActualReading = secondaryOpeningActualReading;
		this.secondaryClosingActualReading = secondaryClosingActualReading;
		this.remarks = remarks;
		this.breakdownHours = breakdownHours;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDated() {
		return dated;
	}

	public void setDated(Date dated) {
		this.dated = dated;
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

	public Integer getTripCount() {
		return tripCount;
	}

	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getBreakdownHours() {
		return breakdownHours;
	}

	public void setBreakdownHours(Double breakdownHours) {
		this.breakdownHours = breakdownHours;
	}

	public MachineryAttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(MachineryAttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
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

}
