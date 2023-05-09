package erp.boq_mgmt.dto.request;

import java.util.List;

import erp.boq_mgmt.dto.UserDetail;

public class BoqCostDefinitionAddRequest {

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

	private String stateTransitionRemark;

	private Integer siteId;

	private List<BoqCostDefinitionLabourAddUpdateRequest> labourList;

	private List<BoqCostDefinitionMaterialAddUpdateRequest> materialList;

	private List<BoqCostDefinitionMachineryAddUpdateRequest> machineryList;

	private UserDetail userDetail;

	public BoqCostDefinitionAddRequest() {
		super();
	}

	public BoqCostDefinitionAddRequest(Long id, Long boqId, Double expectedOutputValue, Boolean foamworkIncluded,
			Integer foamworkSiteVariableId, Boolean overheadIncluded, Integer overheadSiteVariableId,
			Boolean contractorProfitIncluded, Integer contractorProfitSiteVariableId, Double extraExpenseAmount,
			String extraExpenseRemark, Integer stateId, String stateTransitionRemark, Integer siteId,
			UserDetail userDetail) {
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
		this.stateTransitionRemark = stateTransitionRemark;
		this.siteId = siteId;
		this.userDetail = userDetail;
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

	public String getStateTransitionRemark() {
		return stateTransitionRemark;
	}

	public void setStateTransitionRemark(String stateTransitionRemark) {
		this.stateTransitionRemark = stateTransitionRemark;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public List<BoqCostDefinitionLabourAddUpdateRequest> getLabourList() {
		return labourList;
	}

	public void setLabourList(List<BoqCostDefinitionLabourAddUpdateRequest> labourList) {
		this.labourList = labourList;
	}

	public List<BoqCostDefinitionMaterialAddUpdateRequest> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<BoqCostDefinitionMaterialAddUpdateRequest> materialList) {
		this.materialList = materialList;
	}

	public List<BoqCostDefinitionMachineryAddUpdateRequest> getMachineryList() {
		return machineryList;
	}

	public void setMachineryList(List<BoqCostDefinitionMachineryAddUpdateRequest> machineryList) {
		this.machineryList = machineryList;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
