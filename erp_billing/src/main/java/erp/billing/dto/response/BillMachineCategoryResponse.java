package erp.billing.dto.response;

import java.util.List;

import erp.billing.enums.MachineryRunningMode;

public class BillMachineCategoryResponse {

	private Long id;

	private String name;

	private Integer count;

	private List<BillMachineMapResponse> machines;

	private Long woHiringMachineWorkItemId;

	private String woHiringMachineWorkItemDescription;

	private Boolean isMultiFuel;

	private Long primaryReadingUnitId;

	private String primaryReadingUnitName;

	private Long secondaryReadingUnitId;

	private String secondaryReadingUnitName;

	private MachineryRunningMode runningMode;

	public BillMachineCategoryResponse() {
		super();
	}

	public BillMachineCategoryResponse(Long id, String name, Integer count, List<BillMachineMapResponse> machines,
			Long woHiringMachineWorkItemId, String woHiringMachineWorkItemDescription, Boolean isMultiFuel,
			Long primaryReadingUnitId, String primaryReadingUnitName, Long secondaryReadingUnitId,
			String secondaryReadingUnitName, MachineryRunningMode runningMode) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
		this.machines = machines;
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
		this.woHiringMachineWorkItemDescription = woHiringMachineWorkItemDescription;
		this.isMultiFuel = isMultiFuel;
		this.primaryReadingUnitId = primaryReadingUnitId;
		this.primaryReadingUnitName = primaryReadingUnitName;
		this.secondaryReadingUnitId = secondaryReadingUnitId;
		this.secondaryReadingUnitName = secondaryReadingUnitName;
		this.runningMode = runningMode;
	}

	public BillMachineCategoryResponse(Long id, String name, Integer count, List<BillMachineMapResponse> machines,
			Long woHiringMachineWorkItemId, String woHiringMachineWorkItemDescription, Boolean isMultiFuel,
			Long primaryReadingUnitId, String primaryReadingUnitName, Long secondaryReadingUnitId,
			String secondaryReadingUnitName) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
		this.machines = machines;
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
		this.woHiringMachineWorkItemDescription = woHiringMachineWorkItemDescription;
		this.isMultiFuel = isMultiFuel;
		this.primaryReadingUnitId = primaryReadingUnitId;
		this.primaryReadingUnitName = primaryReadingUnitName;
		this.secondaryReadingUnitId = secondaryReadingUnitId;
		this.secondaryReadingUnitName = secondaryReadingUnitName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<BillMachineMapResponse> getMachines() {
		return machines;
	}

	public void setMachines(List<BillMachineMapResponse> machines) {
		this.machines = machines;
	}

	public Boolean getIsMultiFuel() {
		return isMultiFuel;
	}

	public void setIsMultiFuel(Boolean isMultiFuel) {
		this.isMultiFuel = isMultiFuel;
	}

	public Long getPrimaryReadingUnitId() {
		return primaryReadingUnitId;
	}

	public void setPrimaryReadingUnitId(Long primaryReadingUnitId) {
		this.primaryReadingUnitId = primaryReadingUnitId;
	}

	public String getPrimaryReadingUnitName() {
		return primaryReadingUnitName;
	}

	public void setPrimaryReadingUnitName(String primaryReadingUnitName) {
		this.primaryReadingUnitName = primaryReadingUnitName;
	}

	public Long getSecondaryReadingUnitId() {
		return secondaryReadingUnitId;
	}

	public void setSecondaryReadingUnitId(Long secondaryReadingUnitId) {
		this.secondaryReadingUnitId = secondaryReadingUnitId;
	}

	public String getSecondaryReadingUnitName() {
		return secondaryReadingUnitName;
	}

	public void setSecondaryReadingUnitName(String secondaryReadingUnitName) {
		this.secondaryReadingUnitName = secondaryReadingUnitName;
	}

	public Long getWoHiringMachineWorkItemId() {
		return woHiringMachineWorkItemId;
	}

	public void setWoHiringMachineWorkItemId(Long woHiringMachineWorkItemId) {
		this.woHiringMachineWorkItemId = woHiringMachineWorkItemId;
	}

	public String getWoHiringMachineWorkItemDescription() {
		return woHiringMachineWorkItemDescription;
	}

	public void setWoHiringMachineWorkItemDescription(String woHiringMachineWorkItemDescription) {
		this.woHiringMachineWorkItemDescription = woHiringMachineWorkItemDescription;
	}

	public MachineryRunningMode getRunningMode() {
		return runningMode;
	}

	public void setRunningMode(MachineryRunningMode runningMode) {
		this.runningMode = runningMode;
	}

}
