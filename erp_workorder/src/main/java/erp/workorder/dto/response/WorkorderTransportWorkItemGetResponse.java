package erp.workorder.dto.response;

public class WorkorderTransportWorkItemGetResponse {

	private Long transportWorkItemId;

	private String description;

	private BoqItemResponse boq;

	private Double quantity;

	private Double rate;

	private Double amount;

	private Integer unitId;

	private String unitName;

	private String remark;

	public WorkorderTransportWorkItemGetResponse(Long transportWorkItemId, String description, BoqItemResponse boq,
			Double quantity, Double rate, Double amount, Integer unitId, String unitName, String remark) {
		super();
		this.transportWorkItemId = transportWorkItemId;
		this.description = description;
		this.boq = boq;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
		this.unitId = unitId;
		this.unitName = unitName;
		this.remark = remark;
	}

	public Long getTransportWorkItemId() {
		return transportWorkItemId;
	}

	public void setTransportWorkItemId(Long transportWorkItemId) {
		this.transportWorkItemId = transportWorkItemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BoqItemResponse getBoq() {
		return boq;
	}

	public void setBoq(BoqItemResponse boq) {
		this.boq = boq;
	}
}
