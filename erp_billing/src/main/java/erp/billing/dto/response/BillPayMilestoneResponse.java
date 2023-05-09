package erp.billing.dto.response;

public class BillPayMilestoneResponse {

	private Long id;

	private Long woMilestoneId;

	private String description;

	private Boolean isPercentage;

	private Double value;

	private Double amount;

	private Boolean currentlyUtilised;

	public BillPayMilestoneResponse() {
		super();
	}

	public BillPayMilestoneResponse(Long id, Long woMilestoneId, String description, Boolean isPercentage, Double value,
			Double amount, Boolean currentlyUtilised) {
		super();
		this.id = id;
		this.woMilestoneId = woMilestoneId;
		this.description = description;
		this.isPercentage = isPercentage;
		this.value = value;
		this.amount = amount;
		this.currentlyUtilised = currentlyUtilised;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWoMilestoneId() {
		return woMilestoneId;
	}

	public void setWoMilestoneId(Long woMilestoneId) {
		this.woMilestoneId = woMilestoneId;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getCurrentlyUtilised() {
		return currentlyUtilised;
	}

	public void setCurrentlyUtilised(Boolean currentlyUtilised) {
		this.currentlyUtilised = currentlyUtilised;
	}

}
