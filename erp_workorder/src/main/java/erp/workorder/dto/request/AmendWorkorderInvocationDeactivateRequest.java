package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class AmendWorkorderInvocationDeactivateRequest {
	
	private Long amendWorkorderInvocationId;
	
	private Long siteId;
	
	private UserDetail userDetail;

	public AmendWorkorderInvocationDeactivateRequest() {
		super();
	}

	public AmendWorkorderInvocationDeactivateRequest(Long amendWorkorderInvocationId, Long siteId,
			UserDetail userDetail) {
		super();
		this.amendWorkorderInvocationId = amendWorkorderInvocationId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getAmendWorkorderInvocationId() {
		return amendWorkorderInvocationId;
	}

	public void setAmendWorkorderInvocationId(Long amendWorkorderInvocationId) {
		this.amendWorkorderInvocationId = amendWorkorderInvocationId;
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
