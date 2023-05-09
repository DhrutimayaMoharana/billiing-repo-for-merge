package erp.boq_mgmt.dto;

public class HighwayBoqItemsRenderDTO {
	
	private Long id;
	
	private String code;
	
	private String name;

	private String importDescription;
	
	private String unitName;
	
	private Double rate;
	
	private Double maxRate;
	
	private Double quantity;
	
	private Double amount;

	public HighwayBoqItemsRenderDTO() {
		super();
	}

	public HighwayBoqItemsRenderDTO(Long id, String code, String name, String importDescription,
			String unitName, Double rate, Double maxRate, Double quantity, Double amount) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.importDescription = importDescription;
		this.unitName = unitName;
		this.rate = rate;
		this.maxRate = maxRate;
		this.quantity = quantity;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImportDescription() {
		return importDescription;
	}

	public void setImportDescription(String importDescription) {
		this.importDescription = importDescription;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}
}
