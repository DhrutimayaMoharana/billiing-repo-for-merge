package erp.workorder.dto.request;

import java.util.List;

import erp.workorder.dto.UserDetail;

public class WorkorderHiringMachineWorkAddUpdateRequestDTO {

	private Long workorderId;

	private Long hmWorkId;

	private String workScope;

	private String annexureNote;

	private Double dieselRate;

	private List<WorkorderHiringMachineWorkItemAddUpdateRequest> hmWorkItems;

	private Long siteId;

	private UserDetail userDetail;

	public WorkorderHiringMachineWorkAddUpdateRequestDTO() {
		super();
	}

	public WorkorderHiringMachineWorkAddUpdateRequestDTO(Long workorderId, Long hmWorkId, String workScope,
			String annexureNote, Double dieselRate, List<WorkorderHiringMachineWorkItemAddUpdateRequest> hmWorkItems) {
		super();
		this.workorderId = workorderId;
		this.hmWorkId = hmWorkId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.dieselRate = dieselRate;
		this.hmWorkItems = hmWorkItems;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Long getHmWorkId() {
		return hmWorkId;
	}

	public void setHmWorkId(Long hmWorkId) {
		this.hmWorkId = hmWorkId;
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

	public Double getDieselRate() {
		return dieselRate;
	}

	public void setDieselRate(Double dieselRate) {
		this.dieselRate = dieselRate;
	}

	public List<WorkorderHiringMachineWorkItemAddUpdateRequest> getHmWorkItems() {
		return hmWorkItems;
	}

	public void setHmWorkItems(List<WorkorderHiringMachineWorkItemAddUpdateRequest> hmWorkItems) {
		this.hmWorkItems = hmWorkItems;
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
