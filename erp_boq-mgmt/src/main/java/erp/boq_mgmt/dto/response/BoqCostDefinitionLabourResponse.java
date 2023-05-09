package erp.boq_mgmt.dto.response;

public class BoqCostDefinitionLabourResponse {

	private Long id;

	private Long boqCostDefinitionId;

	private Integer labourTypeId;

	private String labourTypeName;

	private Integer unitId;

	private String unitName;

	private Double quantity;

	private Double rate;

	private Double amount;

	public BoqCostDefinitionLabourResponse() {
		super();
	}

	public BoqCostDefinitionLabourResponse(Long id, Long boqCostDefinitionId, Integer labourTypeId,
			String labourTypeName, Integer unitId, String unitName, Double quantity, Double rate, Double amount) {
		super();
		this.id = id;
		this.boqCostDefinitionId = boqCostDefinitionId;
		this.labourTypeId = labourTypeId;
		this.labourTypeName = labourTypeName;
		this.unitId = unitId;
		this.unitName = unitName;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoqCostDefinitionId() {
		return boqCostDefinitionId;
	}

	public void setBoqCostDefinitionId(Long boqCostDefinitionId) {
		this.boqCostDefinitionId = boqCostDefinitionId;
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

}
