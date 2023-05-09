package erp.boq_mgmt.dto.response;

public class BoqCostDefinitionFetchByIdResponse {

	private Long id;

	private Long boqId;

	private String standardBoqCode;

	private String boqCode;

	private String boqName;

	private String categoryName;

	private Integer unitId;

	private String unitName;

	private Double expectedOutputValue;

	private Boolean foamworkIncluded;

	private Integer foamworkSiteVariableId;

	private Double foamworkSiteVariableValue;

	private Boolean overheadIncluded;

	private Integer overheadSiteVariableId;

	private Double overheadSiteVariableValue;

	private Boolean contractorProfitIncluded;

	private Integer contractorProfitSiteVariableId;

	private Double contractorProfitSiteVariableValue;

	private Double extraExpenseAmount;

	private String extraExpenseRemark;

	private Integer stateId;

	private String stateName;

	private Boolean isEditable;

	private Boolean isDeletable;

	private Boolean isFinalState;

	public BoqCostDefinitionFetchByIdResponse() {
		super();
	}

	public BoqCostDefinitionFetchByIdResponse(Long id, Long boqId, String standardBoqCode, String boqCode,
			String boqName, String categoryName, Integer unitId, String unitName, Double expectedOutputValue,
			Boolean foamworkIncluded, Integer foamworkSiteVariableId, Double foamworkSiteVariableValue,
			Boolean overheadIncluded, Integer overheadSiteVariableId, Double overheadSiteVariableValue,
			Boolean contractorProfitIncluded, Integer contractorProfitSiteVariableId,
			Double contractorProfitSiteVariableValue, Double extraExpenseAmount, String extraExpenseRemark,
			Integer stateId, String stateName) {
		super();
		this.id = id;
		this.boqId = boqId;
		this.standardBoqCode = standardBoqCode;
		this.boqCode = boqCode;
		this.boqName = boqName;
		this.categoryName = categoryName;
		this.unitId = unitId;
		this.unitName = unitName;
		this.expectedOutputValue = expectedOutputValue;
		this.foamworkIncluded = foamworkIncluded;
		this.foamworkSiteVariableId = foamworkSiteVariableId;
		this.foamworkSiteVariableValue = foamworkSiteVariableValue;
		this.overheadIncluded = overheadIncluded;
		this.overheadSiteVariableId = overheadSiteVariableId;
		this.overheadSiteVariableValue = overheadSiteVariableValue;
		this.contractorProfitIncluded = contractorProfitIncluded;
		this.contractorProfitSiteVariableId = contractorProfitSiteVariableId;
		this.contractorProfitSiteVariableValue = contractorProfitSiteVariableValue;
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

	public String getStandardBoqCode() {
		return standardBoqCode;
	}

	public void setStandardBoqCode(String standardBoqCode) {
		this.standardBoqCode = standardBoqCode;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public Double getFoamworkSiteVariableValue() {
		return foamworkSiteVariableValue;
	}

	public void setFoamworkSiteVariableValue(Double foamworkSiteVariableValue) {
		this.foamworkSiteVariableValue = foamworkSiteVariableValue;
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

	public Double getOverheadSiteVariableValue() {
		return overheadSiteVariableValue;
	}

	public void setOverheadSiteVariableValue(Double overheadSiteVariableValue) {
		this.overheadSiteVariableValue = overheadSiteVariableValue;
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

	public Double getContractorProfitSiteVariableValue() {
		return contractorProfitSiteVariableValue;
	}

	public void setContractorProfitSiteVariableValue(Double contractorProfitSiteVariableValue) {
		this.contractorProfitSiteVariableValue = contractorProfitSiteVariableValue;
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
