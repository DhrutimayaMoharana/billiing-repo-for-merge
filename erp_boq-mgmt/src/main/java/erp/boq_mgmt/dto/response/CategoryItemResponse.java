package erp.boq_mgmt.dto.response;

public class CategoryItemResponse {

	private Long id;

	private String code;

	private String standardBookCode;

	private String name;

	public CategoryItemResponse() {
		super();
	}

	public CategoryItemResponse(Long id, String code, String standardBookCode, String name) {
		super();
		this.id = id;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.name = name;
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

}
