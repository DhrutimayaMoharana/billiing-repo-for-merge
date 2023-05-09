package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class RfiCustomWorkItemFetchRequest {

	private String searchField;

	private Integer pageNo;

	private Integer pageSize;

	private UserDetail userDetail;

	public RfiCustomWorkItemFetchRequest() {
		super();
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
