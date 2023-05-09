package erp.workorder.dto.request;

public class WorkoderHireMachineRequest {

	private Long id;

	private Byte machineType;

	private Long machineId;

	private Long machineCategoryId;

	private Long woHiringMachineWorkItemId;

	private Double amount;

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getWoHiringMachineWorkItemId() {
		return woHiringMachineWorkItemId;
	}

	public void setWoHiringMachineWorkItemId(Long woHiringMachineWorkItemId) {
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
	}

}
