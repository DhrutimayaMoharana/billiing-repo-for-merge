package erp.boq_mgmt.dto.response;

public class BoqCostDefinitionMaterialResponse {

	private Long id;

	private Long boqCostDefinitionId;

	private Integer materialId;

	private String materialName;

	private Integer unitId;

	private String unitName;

	private Double quantity;

	private Double rate;

	private Double amount;

	public BoqCostDefinitionMaterialResponse() {
		super();
	}

	public BoqCostDefinitionMaterialResponse(Long id, Long boqCostDefinitionId, Integer materialId, String materialName,
			Integer unitId, String unitName, Double quantity, Double rate, Double amount) {
		super();
		this.id = id;
		this.boqCostDefinitionId = boqCostDefinitionId;
		this.materialId = materialId;
		this.materialName = materialName;
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

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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
