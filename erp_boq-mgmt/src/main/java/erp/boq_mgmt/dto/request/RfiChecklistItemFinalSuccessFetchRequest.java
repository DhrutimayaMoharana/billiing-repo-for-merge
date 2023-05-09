package erp.boq_mgmt.dto.request;

import java.util.Set;

import erp.boq_mgmt.dto.UserDetail;

public class RfiChecklistItemFinalSuccessFetchRequest {

	private String searchField;

	private Set<Long> boqIds;

	private Integer pageNo;

	private Integer pageSize;

	private UserDetail userDetail;

	public RfiChecklistItemFinalSuccessFetchRequest() {
		super();
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public Set<Long> getBoqIds() {
		return boqIds;
	}

	public void setBoqIds(Set<Long> boqIds) {
		this.boqIds = boqIds;
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
