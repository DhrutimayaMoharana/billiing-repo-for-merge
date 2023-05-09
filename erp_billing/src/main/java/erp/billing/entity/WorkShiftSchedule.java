package erp.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work_shift_schedule")
public class WorkShiftSchedule {

	private Integer id;

	private Double shiftHours;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	private Date updatedOn;

	private Integer updatedBy;

	public WorkShiftSchedule() {
		super();
	}

	public WorkShiftSchedule(Integer id) {
		super();
		this.id = id;
	}

	public WorkShiftSchedule(Integer id, Double shiftHours, Boolean isActive, Date createdOn, Integer createdBy,
			Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.shiftHours = shiftHours;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "shift_hours")
	public Double getShiftHours() {
		return shiftHours;
	}

	public void setShiftHours(Double shiftHours) {
		this.shiftHours = shiftHours;
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
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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
