package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class MachineDprDeactivateRequest {

	private Long dprId;

	private Long siteId;

	private UserDetail userDetail;

	public MachineDprDeactivateRequest() {
		super();
	}

	public Long getDprId() {
		return dprId;
	}

	public void setDprId(Long dprId) {
		this.dprId = dprId;
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
