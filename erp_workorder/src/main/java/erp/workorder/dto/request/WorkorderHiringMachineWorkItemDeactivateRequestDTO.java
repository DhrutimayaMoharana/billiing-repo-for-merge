package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderHiringMachineWorkItemDeactivateRequestDTO {

	private Long hmWorkItemId;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderHiringMachineWorkItemDeactivateRequestDTO() {
		super();
	}

	public WorkorderHiringMachineWorkItemDeactivateRequestDTO(Long hmWorkItemId, Long siteId, UserDetail userDetail) {
		super();
		this.hmWorkItemId = hmWorkItemId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getHmWorkItemId() {
		return hmWorkItemId;
	}

	public void setHmWorkItemId(Long hmWorkItemId) {
		this.hmWorkItemId = hmWorkItemId;
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
