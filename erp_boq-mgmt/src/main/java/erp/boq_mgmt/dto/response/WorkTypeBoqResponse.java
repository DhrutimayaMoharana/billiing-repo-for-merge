package erp.boq_mgmt.dto.response;

public class WorkTypeBoqResponse {

	private Long boqId;

	private String boqSdbCode;

	private String boqCode;

	private String boqDescription;

	private String categoryDescription;

	private String subcategoryDescription;

	private Long unitId;

	private String unitName;

	public WorkTypeBoqResponse() {
		super();
	}

	public WorkTypeBoqResponse(Long boqId, String boqSdbCode, String boqCode, String boqDescription,
			String categoryDescription, String subcategoryDescription, Long unitId, String unitName) {
		super();
		this.boqId = boqId;
		this.boqSdbCode = boqSdbCode;
		this.boqCode = boqCode;
		this.boqDescription = boqDescription;
		this.categoryDescription = categoryDescription;
		this.subcategoryDescription = subcategoryDescription;
		this.unitId = unitId;
		this.unitName = unitName;
	}

	public String getBoqSdbCode() {
		return boqSdbCode;
	}

	public void setBoqSdbCode(String boqSdbCode) {
		this.boqSdbCode = boqSdbCode;
	}

	public String getBoqCode() {
		return boqCode;
	}

	public void setBoqCode(String boqCode) {
		this.boqCode = boqCode;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	public String getBoqDescription() {
		return boqDescription;
	}

	public void setBoqDescription(String boqDescription) {
		this.boqDescription = boqDescription;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getSubcategoryDescription() {
		return subcategoryDescription;
	}

	public void setSubcategoryDescription(String subcategoryDescription) {
		this.subcategoryDescription = subcategoryDescription;
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

}
