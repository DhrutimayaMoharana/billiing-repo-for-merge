package erp.workorder.dto.response;

import java.util.List;

public class WorkorderLabourWorkGetResponse {

	private Long labourWorkId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderLabourWorkItemGetResponse> items;

	private Double amount;

	public WorkorderLabourWorkGetResponse() {
		super();
	}

	public WorkorderLabourWorkGetResponse(Long labourWorkId, String workScope, String annexureNote,
			List<WorkorderLabourWorkItemGetResponse> items, Double amount) {
		super();
		this.labourWorkId = labourWorkId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.items = items;
		this.amount = amount;
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

	public List<WorkorderLabourWorkItemGetResponse> getItems() {
		return items;
	}

	public void setItems(List<WorkorderLabourWorkItemGetResponse> items) {
		this.items = items;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
