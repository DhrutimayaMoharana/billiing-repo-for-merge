package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderConsultantWorkItemDeactivateRequestDTO {

	private Long consultantWorkItemId;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderConsultantWorkItemDeactivateRequestDTO() {
		super();
	}

	public WorkorderConsultantWorkItemDeactivateRequestDTO(Long consultantWorkItemId, Long siteId, UserDetail userDetail) {
		super();
		this.consultantWorkItemId = consultantWorkItemId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getConsultantWorkItemId() {
		return consultantWorkItemId;
	}

	public void setConsultantWorkItemId(Long consultantWorkItemId) {
		this.consultantWorkItemId = consultantWorkItemId;
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
