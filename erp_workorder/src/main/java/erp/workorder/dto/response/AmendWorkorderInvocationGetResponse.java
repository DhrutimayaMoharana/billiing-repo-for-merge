package erp.workorder.dto.response;

import java.util.Date;
import java.util.List;

import erp.workorder.dto.NextStateTransitDTO;

public class AmendWorkorderInvocationGetResponse {

	private Long id;

	private Long workorderId;

	private Date workorderDatedOn;

	private String workorderNo;

	private Date dated;

	private String subject;

	private Integer stateId;

	private String stateName;

	private String stateAlias;

	private String modifiedByName;

	private Boolean isInitial;

	private Boolean isEditable;

	private Boolean inFinalState;

	private Boolean isDeletatble;

	private List<NextStateTransitDTO> nextStates;

	public AmendWorkorderInvocationGetResponse() {
		super();
	}

	public AmendWorkorderInvocationGetResponse(Long id, Long workorderId, Date workorderDatedOn, String workorderNo,
			Date dated, String subject, Integer stateId, String stateName, String stateAlias, String modifiedByName,
			Boolean isEditable, Boolean inFinalState, List<NextStateTransitDTO> nextStates) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.workorderDatedOn = workorderDatedOn;
		this.workorderNo = workorderNo;
		this.dated = dated;
		this.subject = subject;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

}
