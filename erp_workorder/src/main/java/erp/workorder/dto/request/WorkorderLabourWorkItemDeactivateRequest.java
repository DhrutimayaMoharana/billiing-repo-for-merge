package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderLabourWorkItemDeactivateRequest {

	private Long labourWorkItemId;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderLabourWorkItemDeactivateRequest() {
		super();
	}

	public WorkorderLabourWorkItemDeactivateRequest(Long labourWorkItemId, Long siteId, UserDetail userDetail) {
		super();
		this.labourWorkItemId = labourWorkItemId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getLabourWorkItemId() {
		return labourWorkItemId;
	}

	public void setLabourWorkItemId(Long labourWorkItemId) {
		this.labourWorkItemId = labourWorkItemId;
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
