package erp.workorder.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "workorder_bill_info_machine_mapping")
public class WorkorderBillInfoMachineMapping {

	private Long id;

	private Integer workorderBillInfoId;

	private Byte machineType;

	private Long machineId;

	private Long woHiringMachineWorkItemId;

	private Double amount;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Equipment equipment;

	public WorkorderBillInfoMachineMapping() {
		super();
	}

	public WorkorderBillInfoMachineMapping(Long id, Integer workorderBillInfoId, Byte machineType, Long machineId,
			Long woHiringMachineWorkItemId, Double amount, Boolean isActive, Date createdOn, Long createdBy,
			Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderBillInfoId = workorderBillInfoId;
		this.machineType = machineType;
		this.machineId = machineId;
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
		this.amount = amount;
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

	@Column(name = "workorder_bill_info_id")
	public Integer getWorkorderBillInfoId() {
		return workorderBillInfoId;
	}

	public void setWorkorderBillInfoId(Integer workorderBillInfoId) {
		this.workorderBillInfoId = workorderBillInfoId;
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

	@Column(name = "wo_hiring_machine_work_item_id")
	public Long getWoHiringMachineWorkItemId() {
		return woHiringMachineWorkItemId;
	}

	public void setWoHiringMachineWorkItemId(Long woHiringMachineWorkItemId) {
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
	}

	@Column(name = "amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	@OneToOne
	@JoinColumn(name = "machine_id", updatable = false, insertable = false)
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

}
