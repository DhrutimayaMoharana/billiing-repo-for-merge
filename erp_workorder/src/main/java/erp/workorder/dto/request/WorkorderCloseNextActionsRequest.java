package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderCloseNextActionsRequest {

	private Long workorderCloseId;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderCloseNextActionsRequest() {
		super();
	}

	public WorkorderCloseNextActionsRequest(Long workorderCloseId, Long siteId, UserDetail userDetail) {
		super();
		this.workorderCloseId = workorderCloseId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getWorkorderCloseId() {
		return workorderCloseId;
	}

	public void setWorkorderCloseId(Long workorderCloseId) {
		this.workorderCloseId = workorderCloseId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
