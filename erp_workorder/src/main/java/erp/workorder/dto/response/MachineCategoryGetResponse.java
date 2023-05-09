package erp.workorder.dto.response;

public class MachineCategoryGetResponse {

	private Long id;

	private String name;

	private Object machines;

	private Boolean isMultiFuel;

	private Long primaryReadingUnitId;

	private String primaryReadingUnitName;

	private Long secondaryReadingUnitId;

	private String secondaryReadingUnitName;

	public MachineCategoryGetResponse() {
		super();
	}

	public MachineCategoryGetResponse(Long id, String name, Object machines, Boolean isMultiFuel,
			Long primaryReadingUnitId, String primaryReadingUnitName, Long secondaryReadingUnitId,
			String secondaryReadingUnitName) {
		super();
		this.id = id;
		this.name = name;
		this.machines = machines;
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

	public Object getMachines() {
		return machines;
	}

	public void setMachines(Object machines) {
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

}
