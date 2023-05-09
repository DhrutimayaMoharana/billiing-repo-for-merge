package erp.boq_mgmt.dto;

import java.util.List;

public class HighwayBoqsCategoryRenderDTO {
	
	private Long categoryId;
	
	private String categoryName;
	
	private Double subTotal;
	
	private List<HighwayBoqsSubcategoryRenderDTO> subcategories;
	
	private List<HighwayBoqItemsRenderDTO> boqs;

	public HighwayBoqsCategoryRenderDTO() {
		super();
	}

	public HighwayBoqsCategoryRenderDTO(Long categoryId, String categoryName, Double subTotal,
			List<HighwayBoqsSubcategoryRenderDTO> subcategories, List<HighwayBoqItemsRenderDTO> boqs) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.subTotal = subTotal;
		this.subcategories = subcategories;
		this.boqs = boqs;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public List<HighwayBoqsSubcategoryRenderDTO> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<HighwayBoqsSubcategoryRenderDTO> subcategories) {
		this.subcategories = subcategories;
	}

	public List<HighwayBoqItemsRenderDTO> getBoqs() {
		return boqs;
	}

	public void setBoqs(List<HighwayBoqItemsRenderDTO> boqs) {
		this.boqs = boqs;
	}
	
	

}
