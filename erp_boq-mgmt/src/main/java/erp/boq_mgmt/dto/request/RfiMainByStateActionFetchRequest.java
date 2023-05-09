package erp.boq_mgmt.dto.request;

import java.util.Set;

import erp.boq_mgmt.dto.UserDetail;
import erp.boq_mgmt.enums.RfiWorkType;

public class RfiMainByStateActionFetchRequest {

	private String searchField;

	private Set<Integer> stateActionIds;

	private Boolean includeParents;

	private RfiWorkType workType;

	private Integer siteId;

	private UserDetail userDetail;

	public RfiMainByStateActionFetchRequest() {
		super();
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public Set<Integer> getStateActionIds() {
		return stateActionIds;
	}

	public void setStateActionIds(Set<Integer> stateActionIds) {
		this.stateActionIds = stateActionIds;
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

	public RfiWorkType getWorkType() {
		return workType;
	}

	public void setWorkType(RfiWorkType workType) {
		this.workType = workType;
	}

	public Boolean getIncludeParents() {
		return includeParents;
	}

	public void setIncludeParents(Boolean includeParents) {
		this.includeParents = includeParents;
	}

}
