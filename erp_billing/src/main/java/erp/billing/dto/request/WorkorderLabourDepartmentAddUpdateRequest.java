package erp.billing.dto.request;

public class WorkorderLabourDepartmentAddUpdateRequest {

	private Integer id;

	private String name;

	private Integer companyId;

	private Integer userId;

	public WorkorderLabourDepartmentAddUpdateRequest() {
		super();
	}

	public WorkorderLabourDepartmentAddUpdateRequest(Integer id, String name, Integer companyId, Integer userId) {
		super();
		this.id = id;
		this.name = name;
		this.companyId = companyId;
		this.userId = userId;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
