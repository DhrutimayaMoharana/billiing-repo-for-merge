package erp.billing.dto.response;

public class WorkorderLabourDepartmentFetchResponse {

	private Integer id;

	private String name;

	public WorkorderLabourDepartmentFetchResponse() {
		super();
	}

	public WorkorderLabourDepartmentFetchResponse(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
