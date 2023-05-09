package erp.billing.dto;

import java.util.Date;

public class BoqItemDTO {

	private Long id;

	private String code;

	private String standardBookCode;

	private String name;

	private String vendorDescription;

	private UnitDTO unit;

	private CategoryItemDTO category;

	private SubcategoryItemDTO subcategory;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

	// Extra Properties
	private Long siteId;

	public BoqItemDTO() {
		super();
	}

	public BoqItemDTO(Long id, String code, String standardBookCode, String name, String vendorDescription,
			UnitDTO unit, CategoryItemDTO category, SubcategoryItemDTO subcategory, Boolean isActive, Date createdOn,
			Long createdBy, Date modifiedOn, Long modifiedBy, Integer companyId) {
		super();
		this.id = id;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.setVendorDescription(vendorDescription);
		this.name = name;
		this.unit = unit;
		this.category = category;
		this.subcategory = subcategory;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UnitDTO getUnit() {
		return unit;
	}

	public void setUnit(UnitDTO unit) {
		this.unit = unit;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
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

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

}
