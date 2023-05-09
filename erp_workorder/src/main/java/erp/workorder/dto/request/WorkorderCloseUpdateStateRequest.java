package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderCloseUpdateStateRequest {

	private Integer stateId;

	private Long workorderCloseId;
	
	private Long siteId;

	private UserDetail userDetail;

	public WorkorderCloseUpdateStateRequest() {
		super();
	}

	public WorkorderCloseUpdateStateRequest(Integer stateId, Long workorderCloseId, Long siteId,
			UserDetail userDetail) {
		super();
		this.stateId = stateId;
		this.workorderCloseId = workorderCloseId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Long getWorkorderCloseId() {
		return workorderCloseId;
	}

	public void setWorkorderCloseId(Long workorderCloseId) {
		this.workorderCloseId = workorderCloseId;
	}

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

}
