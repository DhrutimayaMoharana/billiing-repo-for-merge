package erp.boq_mgmt.dto;

public class SbqParentChildDTO {

	private Long id;

	private Long sbqId;

	private String name;

	private String importDescription;

	private String vendorDescription;

	private Long unitId;

	private String unitName;

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

	public SbqParentChildDTO() {
		super();
	}

	public SbqParentChildDTO(Long id) {
		super();
		this.id = id;
	}

	public SbqParentChildDTO(Long id, Long sbqId, String name, String importDescription, String vendorDescription,
			Long unitId, String unitName, Boolean isImportable, String code, String standardBookCode, Double rate,
			Double maxRate, Double quantity, Double amount, String remark, String type, String parentType, Integer pid,
			Integer parentId) {
		super();
		this.id = id;
		this.sbqId = sbqId;
		this.name = name;
		this.importDescription = importDescription;
		this.vendorDescription = vendorDescription;
		this.unitId = unitId;
		this.unitName = unitName;
		this.isImportable = isImportable;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.rate = rate;
		this.maxRate = maxRate;
		this.quantity = quantity;
		this.amount = amount;
		this.remark = remark;
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

	public Long getSbqId() {
		return sbqId;
	}

	public void setSbqId(Long sbqId) {
		this.sbqId = sbqId;
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

	public Boolean getIsImportable() {
		return isImportable;
	}

	public void setIsImportable(Boolean isImportable) {
		this.isImportable = isImportable;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

}
