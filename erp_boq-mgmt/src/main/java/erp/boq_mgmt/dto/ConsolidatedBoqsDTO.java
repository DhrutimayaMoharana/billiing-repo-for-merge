package erp.boq_mgmt.dto;

public class ConsolidatedBoqsDTO {

	private BoqItemDTO boq;

	private CategoryItemDTO category;

	private SubcategoryItemDTO subcategory;

	private Double quantity;

	private Double rate;

	private Double maxRate;

	public ConsolidatedBoqsDTO() {
		super();
	}

	public ConsolidatedBoqsDTO(BoqItemDTO boq, CategoryItemDTO category, SubcategoryItemDTO subcategory,
			Double quantity, Double rate, Double maxRate) {
		super();
		this.boq = boq;
		this.category = category;
		this.subcategory = subcategory;
		this.quantity = quantity;
		this.rate = rate;
		this.maxRate = maxRate;
	}

	public BoqItemDTO getBoq() {
		return boq;
	}

	public void setBoq(BoqItemDTO boq) {
		this.boq = boq;
	}

	public CategoryItemDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryItemDTO category) {
		this.category = category;
	}

	public SubcategoryItemDTO getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubcategoryItemDTO subcategory) {
		this.subcategory = subcategory;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

}
