package erp.boq_mgmt.dto;

public class GenericBoqParentChildDTO {

	private Long id;

	private Long genericBoqId;

	private String name;

	private String importDescription;

	private String vendorDescription;

	private Long unitId;

	private String unitName;

	private String unitTypeName;

	private Boolean isImportable;

	private String code;

	private String standardBookCode;

	private Double rate;

	private Double maxRate;

	private Double quantity;

	private Double amount;

	private String remark;

	private String type;

	private String parentType;

	private Integer pid;

	private Integer parentId;

	public GenericBoqParentChildDTO() {
		super();
	}

	public GenericBoqParentChildDTO(Long id) {
		super();
		this.id = id;
	}

	public GenericBoqParentChildDTO(Long id, Long genericBoqId, String name, String importDescription,
			String vendorDescription, Long unitId, String unitName, String unitTypeName, Boolean isImportable,
			String code, String standardBookCode, Double rate, Double maxRate, Double quantity, Double amount,
			String remark, String type, String parentType, Integer pid, Integer parentId) {
		super();
		this.id = id;
		this.genericBoqId = genericBoqId;
		this.name = name;
		this.vendorDescription = vendorDescription;
		this.isImportable = isImportable;
		this.unitTypeName = unitTypeName;
		this.importDescription = importDescription;
		this.unitId = unitId;
		this.unitName = unitName;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.rate = rate;
		this.maxRate = maxRate;
		this.quantity = quantity;
		this.amount = amount;
		this.remark = remark;
		this.type = type;
		this.parentType = parentType;
		this.setPid(pid);
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Long getGenericBoqId() {
		return genericBoqId;
	}

	public void setGenericBoqId(Long genericBoqId) {
		this.genericBoqId = genericBoqId;
	}

	public Boolean getIsImportable() {
		return isImportable;
	}

	public void setIsImportable(Boolean isImportable) {
		this.isImportable = isImportable;
	}

	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

}
