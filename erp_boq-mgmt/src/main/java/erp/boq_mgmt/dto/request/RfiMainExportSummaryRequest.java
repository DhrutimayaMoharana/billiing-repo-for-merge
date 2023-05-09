package erp.boq_mgmt.dto.request;

import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.RfiWorkType;

public class RfiMainExportSummaryRequest {

	private String searchField;

	private RfiWorkType workType;

	private Integer siteId;

	private UserDetail userDetail;

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
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
