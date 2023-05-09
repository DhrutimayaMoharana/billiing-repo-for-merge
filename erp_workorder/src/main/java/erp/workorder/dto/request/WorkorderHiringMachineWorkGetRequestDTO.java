package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderHiringMachineWorkGetRequestDTO {
	
	private Long workorderId;

	private UserDetail userDetail;

	private Long siteId;

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

}
