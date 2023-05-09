package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderLabourWorkItemAddUpdateRequest {

	private Long labourWorkId;

	private Long labourWorkItemId;

	private Integer labourTypeId;

	private Integer labourCount;

	private String description;

	private Double rate;

	private Short unitId;

	private Long fullDurationThreshold;

	private Long halfDurationThreshold;

	private String remark;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderLabourWorkItemAddUpdateRequest() {
		super();
	}

	public WorkorderLabourWorkItemAddUpdateRequest(Long labourWorkId, Long labourWorkItemId, Integer labourTypeId,
			Integer labourCount, String description, Double rate, Short unitId, Long fullDurationThreshold,
			Long halfDurationThreshold, String remark, Long siteId, UserDetail userDetail) {
		super();
		this.labourWorkId = labourWorkId;
		this.labourWorkItemId = labourWorkItemId;
		this.labourTypeId = labourTypeId;
		this.labourCount = labourCount;
		this.description = description;
		this.rate = rate;
		this.unitId = unitId;
		this.fullDurationThreshold = fullDurationThreshold;
		this.halfDurationThreshold = halfDurationThreshold;
		this.remark = remark;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getLabourWorkId() {
		return labourWorkId;
	}

	public void setLabourWorkId(Long labourWorkId) {
		this.labourWorkId = labourWorkId;
	}

	public Long getLabourWorkItemId() {
		return labourWorkItemId;
	}

	public void setLabourWorkItemId(Long labourWorkItemId) {
		this.labourWorkItemId = labourWorkItemId;
	}

	public Integer getLabourTypeId() {
		return labourTypeId;
	}

	public void setLabourTypeId(Integer labourTypeId) {
		this.labourTypeId = labourTypeId;
	}

	public Integer getLabourCount() {
		return labourCount;
	}

	public void setLabourCount(Integer labourCount) {
		this.labourCount = labourCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Short getUnitId() {
		return unitId;
	}

	public void setUnitId(Short unitId) {
		this.unitId = unitId;
	}

	public Long getFullDurationThreshold() {
		return fullDurationThreshold;
	}

	public void setFullDurationThreshold(Long fullDurationThreshold) {
		this.fullDurationThreshold = fullDurationThreshold;
	}

	public Long getHalfDurationThreshold() {
		return halfDurationThreshold;
	}

	public void setHalfDurationThreshold(Long halfDurationThreshold) {
		this.halfDurationThreshold = halfDurationThreshold;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
