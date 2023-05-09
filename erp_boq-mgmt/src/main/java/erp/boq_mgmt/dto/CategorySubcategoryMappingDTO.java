package erp.boq_mgmt.dto;

import java.util.Date;


public class CategorySubcategoryMappingDTO {
	
	private Long id;
	
	private SubcategoryItemDTO subcategory;
	
	private Date modifiedOn;
	
	private Long modifiedBy;

	public CategorySubcategoryMappingDTO() {
		super();
	}

	public CategorySubcategoryMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public CategorySubcategoryMappingDTO(Long id, SubcategoryItemDTO subcategory, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.subcategory = subcategory;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubcategoryItemDTO getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubcategoryItemDTO subcategory) {
		this.subcategory = subcategory;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
