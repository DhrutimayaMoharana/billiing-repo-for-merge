package erp.workorder.dto;

public class ChainageBoqParentChildDTO {

	private Long id;

	private Long chainageBoqId;

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

	private Integer wotypeId;

	private String woTypeName;

	public ChainageBoqParentChildDTO() {
		super();
	}

	public ChainageBoqParentChildDTO(Long id) {
		super();
		this.id = id;
	}

//	public ChainageBoqParentChildDTO(Long id, Long chainageBoqId, String name, String importDescription, Long unitId,
//			String unitName, String code, String standardBookCode, Double clientRate, Double thresholdRate,
//			Double availableQuantity, String type, String parentType, Integer pid, Integer parentId) {
//		super();
//		this.id = id;
//		this.chainageBoqId = chainageBoqId;
//		this.name = name;
//		this.importDescription = importDescription;
//		this.unitId = unitId;
//		this.unitName = unitName;
//		this.code = code;
//		this.standardBookCode = standardBookCode;
//		this.clientRate = clientRate;
//		this.thresholdRate = thresholdRate;
//		this.availableQuantity = availableQuantity;
//		this.type = type;
//		this.parentType = parentType;
//		this.pid = pid;
//		this.parentId = parentId;
//	}
	public ChainageBoqParentChildDTO(Long id, Long chainageBoqId, String name, String importDescription, Long unitId,
			String unitName, String code, String standardBookCode, Double clientRate, Double thresholdRate,
			Double availableQuantity, String type, String parentType, Integer pid, Integer parentId, Integer wotypeId,
			String woTypeName) {
		super();
		this.id = id;
		this.chainageBoqId = chainageBoqId;
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
		this.wotypeId = wotypeId;
		this.woTypeName = woTypeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChainageBoqId() {
		return chainageBoqId;
	}

	public void setChainageBoqId(Long chainageBoqId) {
		this.chainageBoqId = chainageBoqId;
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

	public Integer getWotypeId() {
		return wotypeId;
	}

	public void setWotypeId(Integer wotypeId) {
		this.wotypeId = wotypeId;
	}

	public String getWoTypeName() {
		return woTypeName;
	}

	public void setWoTypeName(String woTypeName) {
		this.woTypeName = woTypeName;
	}

}
