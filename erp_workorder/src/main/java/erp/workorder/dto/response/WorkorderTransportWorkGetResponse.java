package erp.workorder.dto.response;

import java.util.List;

public class WorkorderTransportWorkGetResponse {

	private Long transportWorkId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderTransportWorkItemGetResponse> items;

	private Double amount;

	public WorkorderTransportWorkGetResponse(Long transportWorkId, String workScope, String annexureNote,
			List<WorkorderTransportWorkItemGetResponse> items, Double amount) {
		super();
		this.transportWorkId = transportWorkId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.items = items;
		this.amount = amount;
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

	public List<WorkorderTransportWorkItemGetResponse> getItems() {
		return items;
	}

	public void setItems(List<WorkorderTransportWorkItemGetResponse> items) {
		this.items = items;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
