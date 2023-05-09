package erp.workorder.dto.request;

import erp.workorder.dto.UserDetail;

public class WorkorderPayMilestoneAddUpdateRequest {

	private Long id;

	private Long workorderId;

	private String description;

	private Boolean isPercentage;

	private Double value;
	
	private Long siteId;
	
	private UserDetail tokenDetails;

	public WorkorderPayMilestoneAddUpdateRequest() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public UserDetail getTokenDetails() {
		return tokenDetails;
	}

	public void setTokenDetails(UserDetail tokenDetails) {
		this.tokenDetails = tokenDetails;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

}
