package erp.workorder.dto.response;

import java.util.Date;
import java.util.List;

import erp.workorder.dto.NextStateTransitDTO;

public class WorkorderCloseGetResponse {

	private Long id;

	private Long workorderId;

	private Integer closeTypeId;

	private String closeTypeEnumName;

	private String closeTypeName;

	private Date workorderDatedOn;

	private String workorderNo;

	private Date dated;

	private String remarks;

	private Integer stateId;

	private String stateName;

	private String stateAlias;

	private String modifiedByName;

	private Boolean isInitial;

	private Boolean isEditable;

	private Boolean inFinalState;

	private Boolean isDeletatble;

	private List<NextStateTransitDTO> nextStates;

	public WorkorderCloseGetResponse() {
		super();
	}

	public WorkorderCloseGetResponse(Long id, Long workorderId, Integer closeTypeId, String closeTypeEnumName,
			String closeTypeName, Date workorderDatedOn, String workorderNo, Date dated, String remarks,
			Integer stateId, String stateName, String stateAlias, String modifiedByName, Boolean isEditable,
			Boolean inFinalState, List<NextStateTransitDTO> nextStates) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.closeTypeId = closeTypeId;
		this.closeTypeEnumName = closeTypeEnumName;
		this.closeTypeName = closeTypeName;
		this.workorderDatedOn = workorderDatedOn;
		this.workorderNo = workorderNo;
		this.dated = dated;
		this.remarks = remarks;
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateAlias = stateAlias;
		this.modifiedByName = modifiedByName;
		this.isEditable = isEditable;
		this.inFinalState = inFinalState;
		this.nextStates = nextStates;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Date getWorkorderDatedOn() {
		return workorderDatedOn;
	}

	public void setWorkorderDatedOn(Date workorderDatedOn) {
		this.workorderDatedOn = workorderDatedOn;
	}

	public String getWorkorderNo() {
		return workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

	public Date getDated() {
		return dated;
	}

	public void setDated(Date dated) {
		this.dated = dated;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getStateAlias() {
		return stateAlias;
	}

	public void setStateAlias(String stateAlias) {
		this.stateAlias = stateAlias;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Boolean getInFinalState() {
		return inFinalState;
	}

	public void setInFinalState(Boolean inFinalState) {
		this.inFinalState = inFinalState;
	}

	public List<NextStateTransitDTO> getNextStates() {
		return nextStates;
	}

	public void setNextStates(List<NextStateTransitDTO> nextStates) {
		this.nextStates = nextStates;
	}

	public Boolean getIsInitial() {
		return isInitial;
	}

	public void setIsInitial(Boolean isInitial) {
		this.isInitial = isInitial;
	}

	public Boolean getIsDeletatble() {
		return isDeletatble;
	}

	public void setIsDeletatble(Boolean isDeletatble) {
		this.isDeletatble = isDeletatble;
	}

	public String getCloseTypeName() {
		return closeTypeName;
	}

	public void setCloseTypeName(String closeTypeName) {
		this.closeTypeName = closeTypeName;
	}

	public Integer getCloseTypeId() {
		return closeTypeId;
	}

	public void setCloseTypeId(Integer closeTypeId) {
		this.closeTypeId = closeTypeId;
	}

	public String getCloseTypeEnumName() {
		return closeTypeEnumName;
	}

	public void setCloseTypeEnumName(String closeTypeEnumName) {
		this.closeTypeEnumName = closeTypeEnumName;
	}

}
