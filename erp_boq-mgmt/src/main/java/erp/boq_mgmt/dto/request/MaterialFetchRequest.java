package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class MaterialFetchRequest {

	private String searchField;

	private Long siteId;

	private UserDetail userDetail;

	public MaterialFetchRequest() {
		super();
	}

	public String getSearchField() {
		return searchField;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
