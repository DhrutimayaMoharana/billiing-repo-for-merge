package erp.workorder.dto.response;

import java.util.List;

public class WorkorderConsultantWorkCategoryWiseItemGetResponse {

	private String categoryDescription;

	private List<WorkorderConsultantWorkItemGetResponse> consultantWorkItems;

	public WorkorderConsultantWorkCategoryWiseItemGetResponse() {
		super();
	}

	public WorkorderConsultantWorkCategoryWiseItemGetResponse(String categoryDescription,
			List<WorkorderConsultantWorkItemGetResponse> consultantWorkItems) {
		super();
		this.categoryDescription = categoryDescription;
		this.consultantWorkItems = consultantWorkItems;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public List<WorkorderConsultantWorkItemGetResponse> getConsultantWorkItems() {
		return consultantWorkItems;
	}

	public void setConsultantWorkItems(List<WorkorderConsultantWorkItemGetResponse> consultantWorkItems) {
		this.consultantWorkItems = consultantWorkItems;
	}

}
