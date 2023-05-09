package erp.workorder.dto.response;

import java.util.List;

public class WorkorderConsultantWorkGetResponse {

	private Long consultantWorkId;

	private String workScope;

	private String annexureNote;

	private List<WorkorderConsultantWorkCategoryWiseItemGetResponse> categoryWiseWorkItems;

	private Double amount;

	public WorkorderConsultantWorkGetResponse() {
		super();
	}

	public WorkorderConsultantWorkGetResponse(Long consultantWorkId, String workScope, String annexureNote,
			List<WorkorderConsultantWorkCategoryWiseItemGetResponse> categoryWiseWorkItems, Double amount) {
		super();
		this.consultantWorkId = consultantWorkId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.setCategoryWiseWorkItems(categoryWiseWorkItems);
		this.amount = amount;
	}

	public Long getConsultantWorkId() {
		return consultantWorkId;
	}

	public void setConsultantWorkId(Long consultantWorkId) {
		this.consultantWorkId = consultantWorkId;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public List<WorkorderConsultantWorkCategoryWiseItemGetResponse> getCategoryWiseWorkItems() {
		return categoryWiseWorkItems;
	}

	public void setCategoryWiseWorkItems(List<WorkorderConsultantWorkCategoryWiseItemGetResponse> categoryWiseWorkItems) {
		this.categoryWiseWorkItems = categoryWiseWorkItems;
	}

}
