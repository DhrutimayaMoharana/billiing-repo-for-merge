package erp.boq_mgmt.dto.response;

public class RfiCustomWorkItemFetchFinalSuccessListResponse {

	private Long id;

	private String name;

	private String code;

	private String description;

	public RfiCustomWorkItemFetchFinalSuccessListResponse() {
		super();
	}

	public RfiCustomWorkItemFetchFinalSuccessListResponse(Long id, String name, String code, String description) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
