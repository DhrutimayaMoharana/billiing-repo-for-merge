package erp.workorder.dto.response;

public class WorkorderLabourWorkItemGetResponse {

	private Long labourWorkItemId;

	private Integer labourTypeId;

	private String labourTypeName;

	private String description;

	private Integer labourCount;

	private Double rate;

	private Double amount;

	private Short unitId;

	private String unitName;

	private Long fullDurationThreshold;

	private Long halfDurationThreshold;

	private String remark;

	public WorkorderLabourWorkItemGetResponse() {
		super();
	}

	public WorkorderLabourWorkItemGetResponse(Long labourWorkItemId, Integer labourTypeId, String labourTypeName,
			String description, Integer labourCount, Double rate, Double amount, Short unitId, String unitName,
			Long fullDurationThreshold, Long halfDurationThreshold, String remark) {
		super();
		this.labourWorkItemId = labourWorkItemId;
		this.labourTypeId = labourTypeId;
		this.labourTypeName = labourTypeName;
		this.description = description;
		this.labourCount = labourCount;
		this.rate = rate;
		this.amount = amount;
		this.unitId = unitId;
		this.unitName = unitName;
		this.fullDurationThreshold = fullDurationThreshold;
		this.halfDurationThreshold = halfDurationThreshold;
		this.remark = remark;
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

	public String getLabourTypeName() {
		return labourTypeName;
	}

	public void setLabourTypeName(String labourTypeName) {
		this.labourTypeName = labourTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLabourCount() {
		return labourCount;
	}

	public void setLabourCount(Integer labourCount) {
		this.labourCount = labourCount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Short getUnitId() {
		return unitId;
	}

	public void setUnitId(Short unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

}
