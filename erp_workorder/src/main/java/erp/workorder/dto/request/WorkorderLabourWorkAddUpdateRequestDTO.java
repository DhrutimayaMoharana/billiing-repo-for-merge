package erp.workorder.dto.request;

import java.util.List;

import erp.workorder.dto.UserDetail;

public class WorkorderLabourWorkAddUpdateRequestDTO {

	private Long workorderId;

	private Long labourWorkId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderLabourWorkItemAddUpdateRequest> labourWorkItems;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderLabourWorkAddUpdateRequestDTO() {
		super();
	}

	public WorkorderLabourWorkAddUpdateRequestDTO(Long workorderId, Long labourWorkId, String workScope,
			String annexureNote, List<WorkorderLabourWorkItemAddUpdateRequest> labourWorkItems, Long siteId,
			UserDetail userDetail) {
		super();
		this.workorderId = workorderId;
		this.labourWorkId = labourWorkId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.labourWorkItems = labourWorkItems;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Long getLabourWorkId() {
		return labourWorkId;
	}

	public void setLabourWorkId(Long labourWorkId) {
		this.labourWorkId = labourWorkId;
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

	public List<WorkorderLabourWorkItemAddUpdateRequest> getLabourWorkItems() {
		return labourWorkItems;
	}

	public void setLabourWorkItems(List<WorkorderLabourWorkItemAddUpdateRequest> labourWorkItems) {
		this.labourWorkItems = labourWorkItems;
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
