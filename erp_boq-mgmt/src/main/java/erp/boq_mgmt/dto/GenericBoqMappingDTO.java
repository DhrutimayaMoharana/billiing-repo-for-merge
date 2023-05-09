package erp.boq_mgmt.dto;

import java.util.Date;

public class GenericBoqMappingDTO {

	private Long id;

	private Integer workorderTypeId;

	private String workorderTypeName;

	private BoqItemDTO boq;

	private String description;

	private String vendorDescription;

	private CategoryItemDTO category;

	private SubcategoryItemDTO subcategory;

	private Double quantity;

	private Double rate;

	private Double maxRate;

	private String remark;

	private Long siteId;

	private FileDTO file;

	private Integer version;

	private Integer quantityVersion;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

	public GenericBoqMappingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GenericBoqMappingDTO(Long id, Integer workorderTypeId, String workorderTypeName, BoqItemDTO boq,
			String description, String vendorDescription, CategoryItemDTO category, SubcategoryItemDTO subcategory,
			Double quantity, Double rate, Double maxRate, String remark, Long siteId, FileDTO file, Integer version,
			Integer quantityVersion, Boolean isActive, Date modifiedOn, Long modifiedBy, Integer companyId) {
		super();
		this.id = id;
		this.workorderTypeId = workorderTypeId;
		this.workorderTypeName = workorderTypeName;
		this.boq = boq;
		this.description = description;
		this.vendorDescription = vendorDescription;
		this.category = category;
		this.subcategory = subcategory;
		this.quantity = quantity;
		this.rate = rate;
		this.maxRate = maxRate;
		this.remark = remark;
		this.siteId = siteId;
		this.file = file;
		this.version = version;
		this.quantityVersion = quantityVersion;
		this.isActive = isActive;
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

	public Integer getWorkorderTypeId() {
		return workorderTypeId;
	}

	public void setWorkorderTypeId(Integer workorderTypeId) {
		this.workorderTypeId = workorderTypeId;
	}

	public String getWorkorderTypeName() {
		return workorderTypeName;
	}

	public void setWorkorderTypeName(String workorderTypeName) {
		this.workorderTypeName = workorderTypeName;
	}

	public BoqItemDTO getBoq() {
		return boq;
	}

	public void setBoq(BoqItemDTO boq) {
		this.boq = boq;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

}
