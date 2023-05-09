package erp.workorder.dto.request;

import java.util.List;

import erp.workorder.dto.UserDetail;

public class WorkorderConsultantWorkAddUpdateRequestDTO {

	private Long workorderId;

	private Long consultantWorkId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderConsultantWorkItemAddUpdateRequest> consultantWorkItems;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderConsultantWorkAddUpdateRequestDTO() {
		super();
	}

	public WorkorderConsultantWorkAddUpdateRequestDTO(Long workorderId, Long consultantWorkId, String workScope,
			String annexureNote, List<WorkorderConsultantWorkItemAddUpdateRequest> consultantWorkItems, Long siteId,
			UserDetail userDetail) {
		super();
		this.workorderId = workorderId;
		this.consultantWorkId = consultantWorkId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.consultantWorkItems = consultantWorkItems;
		this.siteId = siteId;
		this.userDetail = userDetail;
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

	public Long getConsultantWorkId() {
		return consultantWorkId;
	}

	public void setConsultantWorkId(Long consultantWorkId) {
		this.consultantWorkId = consultantWorkId;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public String getWorkScope() {
		return workScope;
	}

	public void setWorkScope(String workScope) {
		this.workScope = workScope;
	}

	public String getAnnexureNote() {
		return annexureNote;
	}

	public void setAnnexureNote(String annexureNote) {
		this.annexureNote = annexureNote;
	}

	public List<WorkorderConsultantWorkItemAddUpdateRequest> getConsultantWorkItems() {
		return consultantWorkItems;
	}

	public void setConsultantWorkItems(List<WorkorderConsultantWorkItemAddUpdateRequest> consultantWorkItems) {
		this.consultantWorkItems = consultantWorkItems;
	}

}
