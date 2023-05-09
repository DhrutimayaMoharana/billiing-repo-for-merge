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

import erp.workorder.enums.MachineryAttendanceStatus;
import erp.workorder.enums.MachineryRunningMode;
import erp.workorder.enums.MachineryShifts;

@Entity
@Table(name = "machine_dpr")
public class MachineDPR implements Serializable {

	private static final long serialVersionUID = 5211175115030954881L;

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

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	public MachineDPR() {
		super();
	}

	public MachineDPR(Long id) {
		super();
		this.id = id;
	}

	public MachineDPR(Long id, Date dated, MachineryRunningMode runningMode, MachineryShifts shift, Byte machineType,
			Long machineId, Double primaryOpeningMeterReading, Double primaryClosingMeterReading,
			Double secondaryOpeningMeterReading, Double secondaryClosingMeterReading,
			Double primaryOpeningActualReading, Double primaryClosingActualReading,
			Double secondaryOpeningActualReading, Double secondaryClosingActualReading, Integer tripCount,
			String remarks, Double breakdownHours, MachineryAttendanceStatus attendanceStatus, Long siteId,
			Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.dated = dated;
		this.runningMode = runningMode;
		this.shift = shift;
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
		this.tripCount = tripCount;
		this.remarks = remarks;
		this.breakdownHours = breakdownHours;
		this.attendanceStatus = attendanceStatus;
		this.siteId = siteId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public MachineDPR(Long id, Date dated, MachineryRunningMode runningMode, MachineryShifts shift, Byte machineType,
			Long machineId, Double primaryOpeningMeterReading, Double primaryClosingMeterReading,
			Double secondaryOpeningMeterReading, Double secondaryClosingMeterReading,
			Double primaryOpeningActualReading, Double primaryClosingActualReading,
			Double secondaryOpeningActualReading, Double secondaryClosingActualReading, Integer tripCount,
			String remarks, MachineryAttendanceStatus attendanceStatus, Long siteId, Boolean isActive, Date createdOn,
			Long createdBy, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.dated = dated;
		this.runningMode = runningMode;
		this.shift = shift;
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
		this.tripCount = tripCount;
		this.remarks = remarks;
		this.attendanceStatus = attendanceStatus;
		this.siteId = siteId;
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

	@Column(name = "dated")
	public Date getDated() {
		return dated;
	}

	public void setDated(Date dated) {
		this.dated = dated;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "running_mode")
	public MachineryRunningMode getRunningMode() {
		return runningMode;
	}

	public void setRunningMode(MachineryRunningMode runningMode) {
		this.runningMode = runningMode;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "shift")
	public MachineryShifts getShift() {
		return shift;
	}

	public void setShift(MachineryShifts shift) {
		this.shift = shift;
	}

	@Column(name = "machine_type")
	public Byte getMachineType() {
		return machineType;
	}

	public void setMachineType(Byte machineType) {
		this.machineType = machineType;
	}

	@Column(name = "machine_id")
	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	@Column(name = "primary_open_meter_read")
	public Double getPrimaryOpeningMeterReading() {
		return primaryOpeningMeterReading;
	}

	public void setPrimaryOpeningMeterReading(Double primaryOpeningMeterReading) {
		this.primaryOpeningMeterReading = primaryOpeningMeterReading;
	}

	@Column(name = "primary_close_meter_read")
	public Double getPrimaryClosingMeterReading() {
		return primaryClosingMeterReading;
	}

	public void setPrimaryClosingMeterReading(Double primaryClosingMeterReading) {
		this.primaryClosingMeterReading = primaryClosingMeterReading;
	}

	@Column(name = "secondary_open_meter_read")
	public Double getSecondaryOpeningMeterReading() {
		return secondaryOpeningMeterReading;
	}

	public void setSecondaryOpeningMeterReading(Double secondaryOpeningMeterReading) {
		this.secondaryOpeningMeterReading = secondaryOpeningMeterReading;
	}

	@Column(name = "secondary_close_meter_read")
	public Double getSecondaryClosingMeterReading() {
		return secondaryClosingMeterReading;
	}

	public void setSecondaryClosingMeterReading(Double secondaryClosingMeterReading) {
		this.secondaryClosingMeterReading = secondaryClosingMeterReading;
	}

	@Column(name = "trip_count")
	public Integer getTripCount() {
		return tripCount;
	}

	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "breakdown_hours")
	public Double getBreakdownHours() {
		return breakdownHours;
	}

	public void setBreakdownHours(Double breakdownHours) {
		this.breakdownHours = breakdownHours;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "attendance_status")
	public MachineryAttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(MachineryAttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	@Column(name = "primary_open_actual_read")
	public Double getPrimaryOpeningActualReading() {
		return primaryOpeningActualReading;
	}

	public void setPrimaryOpeningActualReading(Double primaryOpeningActualReading) {
		this.primaryOpeningActualReading = primaryOpeningActualReading;
	}

	@Column(name = "primary_close_actual_read")
	public Double getPrimaryClosingActualReading() {
		return primaryClosingActualReading;
	}

	public void setPrimaryClosingActualReading(Double primaryClosingActualReading) {
		this.primaryClosingActualReading = primaryClosingActualReading;
	}

	@Column(name = "secondary_open_actual_read")
	public Double getSecondaryOpeningActualReading() {
		return secondaryOpeningActualReading;
	}

	public void setSecondaryOpeningActualReading(Double secondaryOpeningActualReading) {
		this.secondaryOpeningActualReading = secondaryOpeningActualReading;
	}

	@Column(name = "secondary_close_actual_read")
	public Double getSecondaryClosingActualReading() {
		return secondaryClosingActualReading;
	}

	public void setSecondaryClosingActualReading(Double secondaryClosingActualReading) {
		this.secondaryClosingActualReading = secondaryClosingActualReading;
	}

	@Override
	public String toString() {
		return "MachineDPR [id=" + id + ", dated=" + dated + ", runningMode=" + runningMode + ", shift=" + shift
				+ ", machineType=" + machineType + ", machineId=" + machineId + ", primaryOpeningMeterReading="
				+ primaryOpeningMeterReading + ", primaryClosingMeterReading=" + primaryClosingMeterReading
				+ ", secondaryOpeningMeterReading=" + secondaryOpeningMeterReading + ", secondaryClosingMeterReading="
				+ secondaryClosingMeterReading + ", primaryOpeningActualReading=" + primaryOpeningActualReading
				+ ", primaryClosingActualReading=" + primaryClosingActualReading + ", secondaryOpeningActualReading="
				+ secondaryOpeningActualReading + ", secondaryClosingActualReading=" + secondaryClosingActualReading
				+ ", tripCount=" + tripCount + ", remarks=" + remarks + ", attendanceStatus=" + attendanceStatus
				+ ", siteId=" + siteId + ", isActive=" + isActive + ", createdOn=" + createdOn + ", createdBy="
				+ createdBy + ", modifiedOn=" + modifiedOn + ", modifiedBy=" + modifiedBy + "]";
	}

}
