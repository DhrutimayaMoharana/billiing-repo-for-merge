package erp.boq_mgmt.dto.response;

public class UndefinedCostDefinitionBoqItemResponse {

	private Long id;

	private String code;

	private String standardBookCode;

	private String name;

	private String categoryCode;

	private String categoryStandardBookCode;

	private String categoryName;

	private Integer unitId;

	private String unitName;

	public UndefinedCostDefinitionBoqItemResponse() {
		super();
	}

	public UndefinedCostDefinitionBoqItemResponse(Long id, String code, String standardBookCode, String name,
			String categoryCode, String categoryStandardBookCode, String categoryName, Integer unitId,
			String unitName) {
		super();
		this.id = id;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.name = name;
		this.categoryCode = categoryCode;
		this.categoryStandardBookCode = categoryStandardBookCode;
		this.categoryName = categoryName;
		this.unitId = unitId;
		this.unitName = unitName;
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

	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryStandardBookCode() {
		return categoryStandardBookCode;
	}

	public void setCategoryStandardBookCode(String categoryStandardBookCode) {
		this.categoryStandardBookCode = categoryStandardBookCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

}
