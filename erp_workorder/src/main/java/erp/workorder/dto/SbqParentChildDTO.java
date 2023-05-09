package erp.workorder.dto;

public class SbqParentChildDTO {

	private Long id;
	
	private Long sbqId;
	
	private String name;
	
	private String importDescription;
	
	private Long unitId;
	
	private String unitName;
	
	private String code;
	
	private String standardBookCode;
	
	private Double clientRate;
	
	private Double thresholdRate;
	
	private Double availableQuantity;

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

	public SbqParentChildDTO(Long id, Long sbqId, String name, String importDescription, Long unitId, String unitName,
			String code, String standardBookCode, Double clientRate, Double thresholdRate, Double availableQuantity,
			String type, String parentType, Integer pid, Integer parentId) {
		super();
		this.id = id;
		this.sbqId = sbqId;
		this.name = name;
		this.importDescription = importDescription;
		this.unitId = unitId;
		this.unitName = unitName;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.clientRate = clientRate;
		this.thresholdRate = thresholdRate;
		this.availableQuantity = availableQuantity;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Double getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Double availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	
}
