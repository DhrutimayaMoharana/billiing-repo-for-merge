package erp.workorder.entity;

import java.util.Date;

public class EquipmentSummary {

	private Long id;

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

	private Boolean isActive;

	private Date updatedOn;

	public EquipmentSummary() {
		super();
	}

	public EquipmentSummary(Long id, Byte machineType, Long machineId, Double primaryOpeningMeterReading,
			Double primaryClosingMeterReading, Double secondaryOpeningMeterReading, Double secondaryClosingMeterReading,
			Double primaryOpeningActualReading, Double primaryClosingActualReading,
			Double secondaryOpeningActualReading, Double secondaryClosingActualReading, Boolean isActive,
			Date updatedOn) {
		super();
		this.id = id;
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
		this.isActive = isActive;
		this.updatedOn = updatedOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
