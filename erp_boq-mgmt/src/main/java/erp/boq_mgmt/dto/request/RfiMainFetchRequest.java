package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.RfiWorkType;

public class RfiMainFetchRequest {

	private String searchField;

	private Integer pageNo;

	private Integer pageSize;

	private Integer stateId;

	private RfiWorkType workType;

	private Integer siteId;

	private UserDetail userDetail;

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

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public RfiWorkType getWorkType() {
		return workType;
	}

	public void setWorkType(RfiWorkType workType) {
		this.workType = workType;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
