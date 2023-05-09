package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class BoqMasterLmpsAddUpdateRequest {

	private Long id;

	private Long boqId;

	private Double expectedOutputValue;

	private Boolean foamworkIncluded;

	private Double foamworkPercentValue;

	private Boolean overheadIncluded;

	private Double overheadPercentValue;

	private Boolean contractorProfitIncluded;

	private Double contractorProfitPercentValue;

	private Double extraExpenseAmount;

	private String extraExpenseRemark;

	private Integer stateId;

	private String stateTransitionRemark;

	private UserDetail userDetail;

	public BoqMasterLmpsAddUpdateRequest() {
		super();
	}

	public BoqMasterLmpsAddUpdateRequest(Long id, Long boqId, Double expectedOutputValue, Boolean foamworkIncluded,
			Double foamworkPercentValue, Boolean overheadIncluded, Double overheadPercentValue,
			Boolean contractorProfitIncluded, Double contractorProfitPercentValue, Double extraExpenseAmount,
			String extraExpenseRemark, Integer stateId, String stateTransitionRemark, UserDetail userDetail) {
		super();
		this.id = id;
		this.boqId = boqId;
		this.expectedOutputValue = expectedOutputValue;
		this.foamworkIncluded = foamworkIncluded;
		this.foamworkPercentValue = foamworkPercentValue;
		this.overheadIncluded = overheadIncluded;
		this.overheadPercentValue = overheadPercentValue;
		this.contractorProfitIncluded = contractorProfitIncluded;
		this.contractorProfitPercentValue = contractorProfitPercentValue;
		this.extraExpenseAmount = extraExpenseAmount;
		this.extraExpenseRemark = extraExpenseRemark;
		this.stateId = stateId;
		this.stateTransitionRemark = stateTransitionRemark;
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

	public Double getFoamworkPercentValue() {
		return foamworkPercentValue;
	}

	public void setFoamworkPercentValue(Double foamworkPercentValue) {
		this.foamworkPercentValue = foamworkPercentValue;
	}

	public Boolean getOverheadIncluded() {
		return overheadIncluded;
	}

	public void setOverheadIncluded(Boolean overheadIncluded) {
		this.overheadIncluded = overheadIncluded;
	}

	public Double getOverheadPercentValue() {
		return overheadPercentValue;
	}

	public void setOverheadPercentValue(Double overheadPercentValue) {
		this.overheadPercentValue = overheadPercentValue;
	}

	public Boolean getContractorProfitIncluded() {
		return contractorProfitIncluded;
	}

	public void setContractorProfitIncluded(Boolean contractorProfitIncluded) {
		this.contractorProfitIncluded = contractorProfitIncluded;
	}

	public Double getContractorProfitPercentValue() {
		return contractorProfitPercentValue;
	}

	public void setContractorProfitPercentValue(Double contractorProfitPercentValue) {
		this.contractorProfitPercentValue = contractorProfitPercentValue;
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

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
