package erp.billing.dto.request;

public class WorkorderLabourFetchRequest {

	private String search;

	private Integer departmentId;

	private Integer typeId;

	private Integer siteId;

	public WorkorderLabourFetchRequest() {
		super();
	}

	public WorkorderLabourFetchRequest(String search, Integer departmentId, Integer typeId, Integer siteId) {
		super();
		this.search = search;
		this.departmentId = departmentId;
		this.typeId = typeId;
		this.siteId = siteId;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

}
