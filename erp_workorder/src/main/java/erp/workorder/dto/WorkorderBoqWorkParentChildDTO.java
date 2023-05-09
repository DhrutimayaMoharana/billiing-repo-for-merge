package erp.workorder.dto;

public class WorkorderBoqWorkParentChildDTO {

	private Long id;

	private Long boqWorkId;

	private String name;

	private String importDescription;

	private String vendorDescription;

	private Long unitId;

	private String unitName;

	private String code;

	private String standardBookCode;

	private Double clientRate;

	private Double thresholdRate;

	private Double allocatedRate;

	private Double availableQuantity;

	private Double allocatedQuantity;

	private Double allocatedAmount;

	private Boolean alreadySaved;

	private String type;

	private String parentType;

	private Integer pid;

	private Integer parentId;

	private String remark;

	private Boolean isBoqEditable;

	private Integer sortingIndex;

	public WorkorderBoqWorkParentChildDTO() {
		super();
	}

	public WorkorderBoqWorkParentChildDTO(Long id, Long boqWorkId, String name, String importDescription,
			String vendorDescription, Long unitId, String unitName, String code, String standardBookCode,
			Double clientRate, Double thresholdRate, Double allocatedRate, Double availableQuantity,
			Double allocatedQuantity, Double allocatedAmount, Boolean alreadySaved, String type, String parentType,
			Integer pid, Integer parentId, String remark, Boolean isBoqEditable, Integer sortingIndex) {
		super();
		this.id = id;
		this.boqWorkId = boqWorkId;
		this.name = name;
		this.importDescription = importDescription;
		this.vendorDescription = vendorDescription;
		this.setUnitId(unitId);
		this.unitName = unitName;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.clientRate = clientRate;
		this.thresholdRate = thresholdRate;
		this.allocatedRate = allocatedRate;
		this.availableQuantity = availableQuantity;
		this.allocatedQuantity = allocatedQuantity;
		this.allocatedAmount = allocatedAmount;
		this.alreadySaved = alreadySaved;
		this.type = type;
		this.parentType = parentType;
		this.pid = pid;
		this.parentId = parentId;
		this.remark = remark;
		this.isBoqEditable = isBoqEditable;
		this.sortingIndex = sortingIndex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoqWorkId() {
		return boqWorkId;
	}

	public void setBoqWorkId(Long boqWorkId) {
		this.boqWorkId = boqWorkId;
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

	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	public Double getClientRate() {
		return clientRate;
	}

	public void setClientRate(Double clientRate) {
		this.clientRate = clientRate;
	}

	public Double getThresholdRate() {
		return thresholdRate;
	}

	public void setThresholdRate(Double thresholdRate) {
		this.thresholdRate = thresholdRate;
	}

	public Double getAllocatedRate() {
		return allocatedRate;
	}

	public void setAllocatedRate(Double allocatedRate) {
		this.allocatedRate = allocatedRate;
	}

	public Double getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Double availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Double getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(Double allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}

	public Double getAllocatedAmount() {
		return allocatedAmount;
	}

	public void setAllocatedAmount(Double allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	public Boolean getAlreadySaved() {
		return alreadySaved;
	}

	public void setAlreadySaved(Boolean alreadySaved) {
		this.alreadySaved = alreadySaved;
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

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsBoqEditable() {
		return isBoqEditable;
	}

	public void setIsBoqEditable(Boolean isBoqEditable) {
		this.isBoqEditable = isBoqEditable;
	}

	public Integer getSortingIndex() {
		return sortingIndex;
	}

	public void setSortingIndex(Integer sortingIndex) {
		this.sortingIndex = sortingIndex;
	}

}
