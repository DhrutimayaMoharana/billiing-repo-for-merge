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
@Table(name = "wo_hiring_machine_rate_details")
public class WorkorderHiringMachineRateDetails {

	private Long id;

	private Long woHiringMachineItemId;

	private MachineryShifts shift;

	private Double rate;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	public WorkorderHiringMachineRateDetails() {
		super();
	}

	public WorkorderHiringMachineRateDetails(Long id, Long woHiringMachineItemId, MachineryShifts shift, Double rate,
			Boolean isActive, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.woHiringMachineItemId = woHiringMachineItemId;
		this.shift = shift;
		this.rate = rate;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
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
	public Long getWoHiringMachineItemId() {
		return woHiringMachineItemId;
	}

	public void setWoHiringMachineItemId(Long woHiringMachineItemId) {
		this.woHiringMachineItemId = woHiringMachineItemId;
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

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
