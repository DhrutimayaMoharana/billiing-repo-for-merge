package erp.billing.dto.request;

import erp.billing.dto.UserDetail;

public class ClientInvoiceExportExcelRequest {

	private String search;

	private Integer siteId;

	private Integer stateId;

	private Boolean sortByInvoiceDateInAsc;

	private UserDetail user;

	public ClientInvoiceExportExcelRequest() {
		super();
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
