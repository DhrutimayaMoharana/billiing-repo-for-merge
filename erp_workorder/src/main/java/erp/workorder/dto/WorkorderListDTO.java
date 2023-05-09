package erp.workorder.dto;

import java.util.Date;
import java.util.List;

import erp.workorder.enums.WorkorderCloseType;

public class WorkorderListDTO {

	private Long id;

	private String uniqueNo;

	private String referenceWorkorderNo;

	private ContractorDTO contractor;

	private Long typeId;

	private String typeName;

	private Integer stateId;

	private String stateName;

	private String stateAlias;

	private WorkorderCloseType closeType;

	private List<NextStateTransitDTO> nextStates;

	private Boolean inFinalState;

	private Double amount;

	private Double pendingAmount;

	private Double issuedAmount;

	private Date modifiedOn;

	private String modifiedByName;

	private Boolean isEditable;

	private Boolean disableBillGeneration;

	private Integer billCount;

	private Integer equipmentCount;

	private String systemGeneratedWorkInfo;

	public WorkorderListDTO() {
		super();
	}

	public WorkorderListDTO(Long id, String uniqueNo, String referenceWorkorderNo, ContractorDTO contractor,
			Long typeId, String typeName, Integer stateId, String stateName, String stateAlias,
			WorkorderCloseType closeType, List<NextStateTransitDTO> nextStates, Boolean inFinalState, Double amount,
			Double pendingAmount, Double issuedAmount, Date modifiedOn, Integer equipmentCount, String modifiedByName) {
		super();
		this.id = id;
		this.uniqueNo = uniqueNo;
		this.contractor = contractor;
		this.typeId = typeId;
		this.referenceWorkorderNo = referenceWorkorderNo;
		this.typeName = typeName;
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateAlias = stateAlias;
		this.nextStates = nextStates;
		this.closeType = closeType;
		this.inFinalState = inFinalState;
		this.amount = amount;
		this.pendingAmount = pendingAmount;
		this.issuedAmount = issuedAmount;
		this.modifiedOn = modifiedOn;
		this.modifiedByName = modifiedByName;
		this.billCount = 0;
		this.equipmentCount = equipmentCount;
	}

	public Integer getEquipmentCount() {
		return equipmentCount;
	}

	public void setEquipmentCount(Integer equipmentCount) {
		this.equipmentCount = equipmentCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueNo() {
		return uniqueNo;
	}

	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}

	public ContractorDTO getContractor() {
		return contractor;
	}

	public void setContractor(ContractorDTO contractor) {
		this.contractor = contractor;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStateAlias() {
		return stateAlias;
	}

	public void setStateAlias(String stateAlias) {
		this.stateAlias = stateAlias;
	}

	public Boolean getInFinalState() {
		return inFinalState;
	}

	public void setInFinalState(Boolean inFinalState) {
		this.inFinalState = inFinalState;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public List<NextStateTransitDTO> getNextStates() {
		return nextStates;
	}

	public void setNextStates(List<NextStateTransitDTO> nextStates) {
		this.nextStates = nextStates;
	}

	public Double getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(Double pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public Double getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(Double issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Integer getBillCount() {
		return billCount;
	}

	public void setBillCount(Integer billCount) {
		this.billCount = billCount;
	}

	public String getReferenceWorkorderNo() {
		return referenceWorkorderNo;
	}

	public void setReferenceWorkorderNo(String referenceWorkorderNo) {
		this.referenceWorkorderNo = referenceWorkorderNo;
	}

	public String getSystemGeneratedWorkInfo() {
		return systemGeneratedWorkInfo;
	}

	public void setSystemGeneratedWorkInfo(String systemGeneratedWorkInfo) {
		this.systemGeneratedWorkInfo = systemGeneratedWorkInfo;
	}

	public WorkorderCloseType getCloseType() {
		return closeType;
	}

	public void setCloseType(WorkorderCloseType closeType) {
		this.closeType = closeType;
	}

	public Boolean getDisableBillGeneration() {
		return disableBillGeneration;
	}

	public void setDisableBillGeneration(Boolean disableBillGeneration) {
		this.disableBillGeneration = disableBillGeneration;
	}

}
