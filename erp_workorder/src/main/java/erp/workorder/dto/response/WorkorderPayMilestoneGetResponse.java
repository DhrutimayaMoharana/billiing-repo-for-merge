package erp.workorder.dto.response;

public class WorkorderPayMilestoneGetResponse {

	private Long id;

	private String description;

	private Boolean isPercentage;

	private Double value;

	public WorkorderPayMilestoneGetResponse() {
		super();
	}

	public WorkorderPayMilestoneGetResponse(Long id, String description, Boolean isPercentage, Double value) {
		super();
		this.id = id;
		this.description = description;
		this.isPercentage = isPercentage;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
