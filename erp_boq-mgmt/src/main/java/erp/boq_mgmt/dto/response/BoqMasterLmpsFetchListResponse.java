package erp.boq_mgmt.dto.response;

import java.util.Date;

public class BoqMasterLmpsFetchListResponse {

	private Long id;

	private Long boqId;

	private String boqCode;

	private String boqName;

	private Double expectedOutputValue;

	private Integer unitId;

	private String unitName;

	private Integer stateId;

	private String stateName;

	private Date lastUpdatedOn;

	private String lastUpdatedBy;

	private Boolean isEditable;

	private Boolean isDeletable;

	private Boolean isFinalState;

	public BoqMasterLmpsFetchListResponse() {
		super();
	}

	public BoqMasterLmpsFetchListResponse(Long id, Long boqId, String boqCode, String boqName,
			Double expectedOutputValue, Integer unitId, String unitName, Integer stateId, String stateName,
			Date lastUpdatedOn, String lastUpdatedBy) {
		super();
		this.id = id;
		this.boqId = boqId;
		this.boqCode = boqCode;
		this.boqName = boqName;
		this.expectedOutputValue = expectedOutputValue;
		this.unitId = unitId;
		this.unitName = unitName;
		this.stateId = stateId;
		this.stateName = stateName;
		this.lastUpdatedOn = lastUpdatedOn;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	public String getBoqCode() {
		return boqCode;
	}

	public void setBoqCode(String boqCode) {
		this.boqCode = boqCode;
	}

	public String getBoqName() {
		return boqName;
	}

	public void setBoqName(String boqName) {
		this.boqName = boqName;
	}

	public Double getExpectedOutputValue() {
		return expectedOutputValue;
	}

	public void setExpectedOutputValue(Double expectedOutputValue) {
		this.expectedOutputValue = expectedOutputValue;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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
