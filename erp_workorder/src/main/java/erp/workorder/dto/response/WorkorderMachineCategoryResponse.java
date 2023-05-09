package erp.workorder.dto.response;

import java.util.List;

public class WorkorderMachineCategoryResponse {

	private Long hmworkId;

	private Long woHiringMachineWorkItemId;

	private Long categoryId;

	private String categoryName;

	private Integer machineCount;

	private List<WorkorderMachineMapResponse> machines;

	public WorkorderMachineCategoryResponse() {
		super();
	}

	public WorkorderMachineCategoryResponse(Long hmworkId, Long woHiringMachineWorkItemId, Long categoryId,
			String categoryName, Integer machineCount) {
		super();
		this.hmworkId = hmworkId;
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.machineCount = machineCount;
	}

	public Long getHmworkId() {
		return hmworkId;
	}

	public void setHmworkId(Long hmworkId) {
		this.hmworkId = hmworkId;
	}

	public Long getWoHiringMachineWorkItemId() {
		return woHiringMachineWorkItemId;
	}

	public void setWoHiringMachineWorkItemId(Long woHiringMachineWorkItemId) {
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getMachineCount() {
		return machineCount;
	}

	public void setMachineCount(Integer machineCount) {
		this.machineCount = machineCount;
	}

	public List<WorkorderMachineMapResponse> getMachines() {
		return machines;
	}

	public void setMachines(List<WorkorderMachineMapResponse> machines) {
		this.machines = machines;
	}

}
