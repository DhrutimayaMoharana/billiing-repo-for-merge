package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wo_hiring_machine_work")
public class WorkorderHiringMachineWork implements Serializable {

	private static final long serialVersionUID = 495710087039525836L;

	private Long id;

	private Long workorderId;

	private String workScope;

	private String annexureNote;

	private Double dieselRate;

	private Boolean isActive;

	private Long siteId;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	public WorkorderHiringMachineWork() {
		super();
	}

	public WorkorderHiringMachineWork(Long id) {
		super();
		this.id = id;
	}

	public WorkorderHiringMachineWork(Long id, Long workorderId, String workScope, String annexureNote,
			Double dieselRate, Boolean isActive, Long siteId, Date createdOn, Long createdBy, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.dieselRate = dieselRate;
		this.isActive = isActive;
		this.siteId = siteId;
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

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	@Column(name = "work_scope")
	public String getWorkScope() {
		return workScope;
	}

	public void setWorkScope(String workScope) {
		this.workScope = workScope;
	}

	@Column(name = "annexure_note")
	public String getAnnexureNote() {
		return annexureNote;
	}

	public void setAnnexureNote(String annexureNote) {
		this.annexureNote = annexureNote;
	}

	@Column(name = "diesel_rate")
	public Double getDieselRate() {
		return dieselRate;
	}

	public void setDieselRate(Double dieselRate) {
		this.dieselRate = dieselRate;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

}
