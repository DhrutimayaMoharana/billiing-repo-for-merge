package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class AmendWorkorderInvocationUpdateStateRequest {

	private Integer stateId;

	private Long amendWorkorderInvocationId;
	
	private Long siteId;

	private UserDetail userDetail;

	public AmendWorkorderInvocationUpdateStateRequest() {
		super();
	}

	public AmendWorkorderInvocationUpdateStateRequest(Integer stateId, Long amendWorkorderInvocationId, Long siteId,
			UserDetail userDetail) {
		super();
		this.stateId = stateId;
		this.amendWorkorderInvocationId = amendWorkorderInvocationId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Long getAmendWorkorderInvocationId() {
		return amendWorkorderInvocationId;
	}

	public void setAmendWorkorderInvocationId(Long amendWorkorderInvocationId) {
		this.amendWorkorderInvocationId = amendWorkorderInvocationId;
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
