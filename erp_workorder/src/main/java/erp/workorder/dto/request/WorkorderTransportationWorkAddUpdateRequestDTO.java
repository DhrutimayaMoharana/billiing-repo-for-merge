package erp.workorder.dto.request;

import java.util.List;

import erp.workorder.dto.UserDetail;

public class WorkorderTransportationWorkAddUpdateRequestDTO {

	private Long workorderId;

	private Long transportWorkId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderTransportationWorkItemAddUpdateRequest> transportWorkItems;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderTransportationWorkAddUpdateRequestDTO() {
		super();
	}

	public WorkorderTransportationWorkAddUpdateRequestDTO(Long workorderId, Long transportWorkId, String workScope,
			String annexureNote, List<WorkorderTransportationWorkItemAddUpdateRequest> transportWorkItems, Long siteId,
			UserDetail userDetail) {
		super();
		this.workorderId = workorderId;
		this.transportWorkId = transportWorkId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.transportWorkItems = transportWorkItems;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Long getTransportWorkId() {
		return transportWorkId;
	}

	public void setTransportWorkId(Long transportWorkId) {
		this.transportWorkId = transportWorkId;
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

	public List<WorkorderTransportationWorkItemAddUpdateRequest> getTransportWorkItems() {
		return transportWorkItems;
	}

	public void setTransportWorkItems(List<WorkorderTransportationWorkItemAddUpdateRequest> transportWorkItems) {
		this.transportWorkItems = transportWorkItems;
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
