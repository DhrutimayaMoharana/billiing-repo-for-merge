package erp.workorder.dto.response;

import java.util.List;

public class WorkorderHiringMachineWorkGetResponse {

	private Long hmWorkId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderHiringMachineWorkItemGetResponse> hmWorkItems;

	private Double dieselRate;

	private Double amount;

	public WorkorderHiringMachineWorkGetResponse() {
		super();
	}

	public WorkorderHiringMachineWorkGetResponse(Long hmWorkId, String workScope, String annexureNote,
			List<WorkorderHiringMachineWorkItemGetResponse> hmWorkItems, Double dieselRate, Double amount) {
		super();
		this.hmWorkId = hmWorkId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.hmWorkItems = hmWorkItems;
		this.dieselRate = dieselRate;
		this.amount = amount;
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

	public Long getHmWorkId() {
		return hmWorkId;
	}

	public void setHmWorkId(Long hmWorkId) {
		this.hmWorkId = hmWorkId;
	}

	public List<WorkorderHiringMachineWorkItemGetResponse> getHmWorkItems() {
		return hmWorkItems;
	}

	public void setHmWorkItems(List<WorkorderHiringMachineWorkItemGetResponse> hmWorkItems) {
		this.hmWorkItems = hmWorkItems;
	}

	public Double getDieselRate() {
		return dieselRate;
	}

	public void setDieselRate(Double dieselRate) {
		this.dieselRate = dieselRate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
