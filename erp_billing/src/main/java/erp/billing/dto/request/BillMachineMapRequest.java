package erp.billing.dto.request;

import java.util.Date;

public class BillMachineMapRequest {

	private Long id;

	private Byte machineType;

	private Long machineId;

	private Long machineCategoryId;

	private Long woHiringMachineWorkItemId;

	private Date fromDate;

	private Date toDate;

	public BillMachineMapRequest() {
		super();
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

	public Long getMachineCategoryId() {
		return machineCategoryId;
	}

	public void setMachineCategoryId(Long machineCategoryId) {
		this.machineCategoryId = machineCategoryId;
	}

	public Long getWoHiringMachineWorkItemId() {
		return woHiringMachineWorkItemId;
	}

	public void setWoHiringMachineWorkItemId(Long woHiringMachineWorkItemId) {
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
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

}
