package erp.boq_mgmt.dto;

public class CategoryBoqParentChildDTO {

	private Long id;

	private String name;

	private String vendorDescription;

	private Long unitId;

	private String unitName;

	private String code;

	private String standardBookCode;

	private String type;

	private String parentType;

	private Integer pid;

	private Integer parentId;

	public CategoryBoqParentChildDTO() {
		super();
	}

	public CategoryBoqParentChildDTO(Long id) {
		super();
		this.id = id;
	}

	public CategoryBoqParentChildDTO(Long id, String name, String vendorDescription, Long unitId, String unitName,
			String code, String standardBookCode, String type, String parentType, Integer pid, Integer parentId) {
		super();
		this.id = id;
		this.name = name;
		this.vendorDescription = vendorDescription;
		this.unitId = unitId;
		this.unitName = unitName;
		this.code = code;
		this.standardBookCode = standardBookCode;
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

}
