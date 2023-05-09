package erp.workorder.dto.response;

public class BoqItemResponse {

	private Long boqId;

	private String boqCode;

	private String boqSdbCode;

	private String boqDescription;

	private String categoryDescription;

	private String subcategoryDescription;

	public BoqItemResponse() {
		super();
	}

	public BoqItemResponse(Long boqId, String boqCode, String boqSdbCode, String boqDescription,
			String categoryDescription, String subcategoryDescription) {
		super();
		this.boqId = boqId;
		this.boqCode = boqCode;
		this.boqSdbCode = boqSdbCode;
		this.boqDescription = boqDescription;
		this.categoryDescription = categoryDescription;
		this.subcategoryDescription = subcategoryDescription;
	}

	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
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

	public String getBoqDescription() {
		return boqDescription;
	}

	public void setBoqDescription(String boqDescription) {
		this.boqDescription = boqDescription;
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

}
