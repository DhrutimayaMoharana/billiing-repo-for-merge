package erp.boq_mgmt.dto.response;

public class WorkLayerFetchFinalSuccessListResponse {

	private Integer id;

	private String code;

	private String name;

	private String description;

	public WorkLayerFetchFinalSuccessListResponse() {
		super();
	}

	public WorkLayerFetchFinalSuccessListResponse(Integer id, String code, String name, String description) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
