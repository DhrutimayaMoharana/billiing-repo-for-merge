package erp.boq_mgmt.dto.response;

public class RfiCustomWorkItemFetchListResponse {

	private Long id;

	private String name;

	private String code;

	private String description;

	private IdNameDTO unit;

	private Integer stateId;

	private String stateName;

	private Boolean isEditable;

	private Boolean isDeletable;

	private Boolean isFinalState;

	public RfiCustomWorkItemFetchListResponse() {
		super();
	}

	public RfiCustomWorkItemFetchListResponse(Long id, String name, String code, String description, IdNameDTO unit,
			Integer stateId, String stateName) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.unit = unit;
		this.stateId = stateId;
		this.stateName = stateName;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Boolean getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(Boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

	public Boolean getIsFinalState() {
		return isFinalState;
	}

	public void setIsFinalState(Boolean isFinalState) {
		this.isFinalState = isFinalState;
	}

	public IdNameDTO getUnit() {
		return unit;
	}

	public void setUnit(IdNameDTO unit) {
		this.unit = unit;
	}

}
