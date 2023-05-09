package erp.boq_mgmt.dto;

public class ConsolidatedBoqParentChildDTO {

	private Long id;

	private String name;

	private String importDescription;

	private Long unitId;

	private String unitName;

	private String unitTypeName;

	private String code;

	private String standardBookCode;

	private Double rate;

	private Double maxRate;

	private Double quantity;

	private Double amount;

	private String type;

	private String parentType;

	private Integer pid;

	private Integer parentId;

	public ConsolidatedBoqParentChildDTO() {
		super();
	}

	public ConsolidatedBoqParentChildDTO(Long id, String name, String importDescription, Long unitId, String unitName,
			String unitTypeName, String code, String standardBookCode, Double rate, Double maxRate, Double quantity,
			Double amount, String type, String parentType, Integer pid, Integer parentId) {
		super();
		this.id = id;
		this.name = name;
		this.importDescription = importDescription;
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitTypeName = unitTypeName;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.rate = rate;
		this.maxRate = maxRate;
		this.quantity = quantity;
		this.amount = amount;
		this.type = type;
		this.parentType = parentType;
		this.pid = pid;
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
