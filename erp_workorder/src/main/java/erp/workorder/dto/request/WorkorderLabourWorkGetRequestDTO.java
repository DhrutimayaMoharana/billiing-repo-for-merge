package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderLabourWorkGetRequestDTO {

	private Long workorderId;

	private UserDetail userDetail;

	private Long siteId;

	public WorkorderLabourWorkGetRequestDTO() {
		super();
	}

	public WorkorderLabourWorkGetRequestDTO(Long workorderId, UserDetail userDetail, Long siteId) {
		super();
		this.workorderId = workorderId;
		this.userDetail = userDetail;
		this.siteId = siteId;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
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
