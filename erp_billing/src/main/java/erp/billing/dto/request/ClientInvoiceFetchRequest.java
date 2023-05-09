package erp.billing.dto.request;

import erp.billing.dto.UserDetail;

public class ClientInvoiceFetchRequest {

	private String search;

	private Integer siteId;

	private Integer stateId;

	private Integer pageNo;

	private Integer pageSize;

	private Boolean sortByInvoiceDateInAsc;

	private UserDetail user;

	public ClientInvoiceFetchRequest() {
		super();
	}

	public ClientInvoiceFetchRequest(String search, Integer siteId, Integer stateId, Integer pageNo, Integer pageSize,
			Boolean sortByInvoiceDateInAsc, UserDetail user) {
		super();
		this.search = search;
		this.siteId = siteId;
		this.stateId = stateId;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortByInvoiceDateInAsc = sortByInvoiceDateInAsc;
		this.user = user;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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

	public Boolean getSortByInvoiceDateInAsc() {
		return sortByInvoiceDateInAsc;
	}

	public void setSortByInvoiceDateInAsc(Boolean sortByInvoiceDateInAsc) {
		this.sortByInvoiceDateInAsc = sortByInvoiceDateInAsc;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

}
