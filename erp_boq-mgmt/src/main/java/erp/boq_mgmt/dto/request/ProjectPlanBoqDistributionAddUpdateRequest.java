package erp.boq_mgmt.dto.request;

public class ProjectPlanBoqDistributionAddUpdateRequest {

	private Integer year;

	private Integer month;

	private Double quantityDistributed;

	public ProjectPlanBoqDistributionAddUpdateRequest() {
		super();
	}

	public ProjectPlanBoqDistributionAddUpdateRequest(Integer year, Integer month, Double quantityDistributed) {
		super();
		this.year = year;
		this.month = month;
		this.quantityDistributed = quantityDistributed;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getQuantityDistributed() {
		return quantityDistributed;
	}

	public void setQuantityDistributed(Double quantityDistributed) {
		this.quantityDistributed = quantityDistributed;
	}

}
