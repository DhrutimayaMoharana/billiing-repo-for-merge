package erp.workorder.dto.response;

public class WorkorderConsultantWorkItemGetResponse {

	private Long consultantWorkItemId;

	private String categoryDescription;

	private String description;

	private Long chainageId;

	private String chainageName;

	private Double quantity;

	private Double rate;

	private Double amount;

	private Long unitId;

	private String unitName;

	private String remark;

	public WorkorderConsultantWorkItemGetResponse() {
		super();
	}

	public WorkorderConsultantWorkItemGetResponse(Long consultantWorkItemId, String categoryDescription,
			String description, Long chainageId, String chainageName, Double quantity, Double rate, Double amount,
			Long unitId, String unitName, String remark) {
		super();
		this.consultantWorkItemId = consultantWorkItemId;
		this.categoryDescription = categoryDescription;
		this.description = description;
		this.chainageId = chainageId;
		this.chainageName = chainageName;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
		this.unitId = unitId;
		this.unitName = unitName;
		this.remark = remark;
	}

	public Long getConsultantWorkItemId() {
		return consultantWorkItemId;
	}

	public void setConsultantWorkItemId(Long consultantWorkItemId) {
		this.consultantWorkItemId = consultantWorkItemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getChainageId() {
		return chainageId;
	}

	public void setChainageId(Long chainageId) {
		this.chainageId = chainageId;
	}

	public String getChainageName() {
		return chainageName;
	}

	public void setChainageName(String chainageName) {
		this.chainageName = chainageName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}
