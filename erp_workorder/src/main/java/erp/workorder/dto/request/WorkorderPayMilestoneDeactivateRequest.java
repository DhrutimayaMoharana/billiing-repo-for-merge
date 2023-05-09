package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderPayMilestoneDeactivateRequest {

	private Long workorderPayMilestoneId;

	private Long siteId;

	private UserDetail tokenDetails;

	public WorkorderPayMilestoneDeactivateRequest() {
		super();
	}

	public Long getWorkorderPayMilestoneId() {
		return workorderPayMilestoneId;
	}

	public void setWorkorderPayMilestoneId(Long workorderPayMilestoneId) {
		this.workorderPayMilestoneId = workorderPayMilestoneId;
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
