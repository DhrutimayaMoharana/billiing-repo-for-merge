package erp.boq_mgmt.dto.response;

import java.util.Set;

public class RfiChecklistItemBoqsFetchResponse {

	private Integer id;

	private String name;

	private String description;

	private Set<Long> boqItemIds;

	private Integer stateId;

	private String stateName;

	private Boolean isEditable;

	private Boolean isDeletable;

	private Boolean isFinalState;

	public RfiChecklistItemBoqsFetchResponse() {
		super();
	}

	public RfiChecklistItemBoqsFetchResponse(Integer id, String name, String description, Set<Long> boqItemIds,
			Integer stateId, String stateName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.boqItemIds = boqItemIds;
		this.stateId = stateId;
		this.stateName = stateName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Long> getBoqItemIds() {
		return boqItemIds;
	}

	public void setBoqItemIds(Set<Long> boqItemIds) {
		this.boqItemIds = boqItemIds;
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

}
