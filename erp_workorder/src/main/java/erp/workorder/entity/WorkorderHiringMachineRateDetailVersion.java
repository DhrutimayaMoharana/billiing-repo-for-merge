package erp.workorder.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.workorder.enums.MachineryShifts;

@Entity
@Table(name = "wo_hiring_machine_rate_detail_versions")
public class WorkorderHiringMachineRateDetailVersion {

	private Long id;

	private Long woHiringMachineItemVersionId;

	private MachineryShifts shift;

	private Double rate;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WorkorderHiringMachineRateDetailVersion() {
		super();
	}

	public WorkorderHiringMachineRateDetailVersion(Long id, Long woHiringMachineItemVersionId, MachineryShifts shift,
			Double rate, Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.woHiringMachineItemVersionId = woHiringMachineItemVersionId;
		this.shift = shift;
		this.rate = rate;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	@Column(name = "wo_hiring_machine_item_id")
	public Long getWoHiringMachineItemVersionId() {
		return woHiringMachineItemVersionId;
	}

	public void setWoHiringMachineItemVersionId(Long woHiringMachineItemVersionId) {
		this.woHiringMachineItemVersionId = woHiringMachineItemVersionId;
	}

	@Column(name = "shift")
	public MachineryShifts getShift() {
		return shift;
	}

	public void setShift(MachineryShifts shift) {
		this.shift = shift;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
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

}
