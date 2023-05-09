package erp.workorder.dto;

import java.util.Date;

public class StructureBoqQuantityMappingDTO {

	private Long id;

	private StructureDTO structure;

	private String description;

	private BoqItemDTO boq;

	private CategoryItemDTO category;

	private SubcategoryItemDTO subcategory;

	private Long siteId;

	private Double quantity;

	private Double rate;

	private Double maxRate;

	private String remark;

	private FileDTO file;

	private Integer version;

	private Integer quantityVersion;

	private Date modifiedOn;

	private Long modifiedBy;

	private Boolean isActive;

	private Integer companyId;

	public StructureBoqQuantityMappingDTO() {
		super();
	}

	public StructureBoqQuantityMappingDTO(Long id) {
		super();
		this.id = id;
	}

	public StructureBoqQuantityMappingDTO(Long id, StructureDTO structure, String description, BoqItemDTO boq,
			CategoryItemDTO category, SubcategoryItemDTO subcategory, Long siteId, Double quantity, Double rate,
			Double maxRate, String remark, FileDTO file, Integer version, Integer quantityVersion, Date modifiedOn,
			Long modifiedBy, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.structure = structure;
		this.description = description;
		this.boq = boq;
		this.category = category;
		this.subcategory = subcategory;
		this.siteId = siteId;
		this.quantity = quantity;
		this.rate = rate;
		this.maxRate = maxRate;
		this.remark = remark;
		this.file = file;
		this.version = version;
		this.quantityVersion = quantityVersion;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StructureDTO getStructure() {
		return structure;
	}

	public void setStructure(StructureDTO structure) {
		this.structure = structure;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public FileDTO getFile() {
		return file;
	}

	public void setFile(FileDTO file) {
		this.file = file;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getQuantityVersion() {
		return quantityVersion;
	}

	public void setQuantityVersion(Integer quantityVersion) {
		this.quantityVersion = quantityVersion;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

}
