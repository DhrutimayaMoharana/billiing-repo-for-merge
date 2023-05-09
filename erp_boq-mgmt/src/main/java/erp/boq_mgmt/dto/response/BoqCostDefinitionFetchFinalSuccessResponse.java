package erp.boq_mgmt.dto.response;

public class BoqCostDefinitionFetchFinalSuccessResponse {

	private Long id;

	private Long boqId;

	private Double expectedOutputValue;

	private Boolean foamworkIncluded;

	private Integer foamworkSiteVariableId;

	private Boolean overheadIncluded;

	private Integer overheadSiteVariableId;

	private Boolean contractorProfitIncluded;

	private Integer contractorProfitSiteVariableId;

	private Double extraExpenseAmount;

	private String extraExpenseRemark;

	private Integer stateId;

	private String stateName;

	private Boolean isEditable;

	private Boolean isDeletable;

	private Boolean isFinalState;

	public BoqCostDefinitionFetchFinalSuccessResponse() {
		super();
	}

	public BoqCostDefinitionFetchFinalSuccessResponse(Long id, Long boqId, Double expectedOutputValue, Boolean foamworkIncluded,
			Integer foamworkSiteVariableId, Boolean overheadIncluded, Integer overheadSiteVariableId,
			Boolean contractorProfitIncluded, Integer contractorProfitSiteVariableId, Double extraExpenseAmount,
			String extraExpenseRemark, Integer stateId, String stateName) {
		super();
		this.id = id;
		this.boqId = boqId;
		this.expectedOutputValue = expectedOutputValue;
		this.foamworkIncluded = foamworkIncluded;
		this.foamworkSiteVariableId = foamworkSiteVariableId;
		this.overheadIncluded = overheadIncluded;
		this.overheadSiteVariableId = overheadSiteVariableId;
		this.contractorProfitIncluded = contractorProfitIncluded;
		this.contractorProfitSiteVariableId = contractorProfitSiteVariableId;
		this.extraExpenseAmount = extraExpenseAmount;
		this.extraExpenseRemark = extraExpenseRemark;
		this.stateId = stateId;
		this.stateName = stateName;
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

	public Double getExpectedOutputValue() {
		return expectedOutputValue;
	}

	public void setExpectedOutputValue(Double expectedOutputValue) {
		this.expectedOutputValue = expectedOutputValue;
	}

	public Boolean getFoamworkIncluded() {
		return foamworkIncluded;
	}

	public void setFoamworkIncluded(Boolean foamworkIncluded) {
		this.foamworkIncluded = foamworkIncluded;
	}

	public Integer getFoamworkSiteVariableId() {
		return foamworkSiteVariableId;
	}

	public void setFoamworkSiteVariableId(Integer foamworkSiteVariableId) {
		this.foamworkSiteVariableId = foamworkSiteVariableId;
	}

	public Boolean getOverheadIncluded() {
		return overheadIncluded;
	}

	public void setOverheadIncluded(Boolean overheadIncluded) {
		this.overheadIncluded = overheadIncluded;
	}

	public Integer getOverheadSiteVariableId() {
		return overheadSiteVariableId;
	}

	public void setOverheadSiteVariableId(Integer overheadSiteVariableId) {
		this.overheadSiteVariableId = overheadSiteVariableId;
	}

	public Boolean getContractorProfitIncluded() {
		return contractorProfitIncluded;
	}

	public void setContractorProfitIncluded(Boolean contractorProfitIncluded) {
		this.contractorProfitIncluded = contractorProfitIncluded;
	}

	public Integer getContractorProfitSiteVariableId() {
		return contractorProfitSiteVariableId;
	}

	public void setContractorProfitSiteVariableId(Integer contractorProfitSiteVariableId) {
		this.contractorProfitSiteVariableId = contractorProfitSiteVariableId;
	}

	public Double getExtraExpenseAmount() {
		return extraExpenseAmount;
	}

	public void setExtraExpenseAmount(Double extraExpenseAmount) {
		this.extraExpenseAmount = extraExpenseAmount;
	}

	public String getExtraExpenseRemark() {
		return extraExpenseRemark;
	}

	public void setExtraExpenseRemark(String extraExpenseRemark) {
		this.extraExpenseRemark = extraExpenseRemark;
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
