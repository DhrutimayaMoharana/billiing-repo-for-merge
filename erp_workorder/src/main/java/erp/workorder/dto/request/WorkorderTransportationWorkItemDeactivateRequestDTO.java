package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderTransportationWorkItemDeactivateRequestDTO {

	private Long transportWorkItemId;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderTransportationWorkItemDeactivateRequestDTO() {
		super();
	}

	public WorkorderTransportationWorkItemDeactivateRequestDTO(Long transportWorkItemId, Long siteId,
			UserDetail userDetail) {
		super();
		this.transportWorkItemId = transportWorkItemId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getTransportWorkItemId() {
		return transportWorkItemId;
	}

	public void setTransportWorkItemId(Long transportWorkItemId) {
		this.transportWorkItemId = transportWorkItemId;
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
