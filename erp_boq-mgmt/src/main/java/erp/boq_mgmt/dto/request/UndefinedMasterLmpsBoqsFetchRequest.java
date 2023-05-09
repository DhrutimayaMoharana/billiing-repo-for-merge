package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;

public class UndefinedMasterLmpsBoqsFetchRequest {

	private String searchField;

	private UserDetail userDetail;

	public UndefinedMasterLmpsBoqsFetchRequest() {
		super();
	}

	public String getSearchField() {
		return searchField;
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
