package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderPayMilestoneGetRequest {

	private Long workorderId;

	private Long siteId;

	private UserDetail tokenDetails;

	public WorkorderPayMilestoneGetRequest() {
		super();
	}

	public WorkorderPayMilestoneGetRequest(Long workorderId, Long siteId, UserDetail tokenDetails) {
		super();
		this.workorderId = workorderId;
		this.siteId = siteId;
		this.tokenDetails = tokenDetails;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getTokenDetails() {
		return tokenDetails;
	}

	public void setTokenDetails(UserDetail tokenDetails) {
		this.tokenDetails = tokenDetails;
	}

}
