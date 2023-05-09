package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class UndefinedCostDefinitionBoqsFetchRequest {

	private String searchField;

	private Integer siteId;

	private UserDetail userDetail;

	public UndefinedCostDefinitionBoqsFetchRequest() {
		super();
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
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
