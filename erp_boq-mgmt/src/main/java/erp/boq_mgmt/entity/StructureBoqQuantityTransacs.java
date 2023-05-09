package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "structure_boq_qty_transacs")
public class StructureBoqQuantityTransacs implements Serializable {

	private static final long serialVersionUID = 6335476960048156183L;

	private Long id;

	private Long structureId;

	private String description;

	private String vendorDescription;

	private Long unitId;

	private Long boqId;

	private Long categoryId;

	private Long subcategoryId;

	private Long siteId;

	private Double quantity;

	private Double rate;

	private Double maxRate;

	private String remark;

	private Long fileId;

	private Integer version;

	private Integer quantityVersion;

	private Date createdOn;

	private Long createdBy;

	private Boolean isActive;

	private Integer companyId;

	public StructureBoqQuantityTransacs() {
		super();
	}

	public StructureBoqQuantityTransacs(Long id) {
		super();
		this.id = id;
	}

	public StructureBoqQuantityTransacs(Long id, Long structureId, Long unitId, String description,
			String vendorDescription, Long boqId, Long categoryId, Long subcategoryId, Long siteId, Double quantity,
			Double rate, Double maxRate, String remark, Long fileId, Integer version, Integer quantityVersion,
			Date createdOn, Long createdBy, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.structureId = structureId;
		this.vendorDescription = vendorDescription;
		this.description = description;
		this.boqId = boqId;
		this.categoryId = categoryId;
		this.subcategoryId = subcategoryId;
		this.unitId = unitId;
		this.siteId = siteId;
		this.quantity = quantity;
		this.rate = rate;
		this.maxRate = maxRate;
		this.remark = remark;
		this.setFileId(fileId);
		this.version = version;
		this.quantityVersion = quantityVersion;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.isActive = isActive;
		this.companyId = companyId;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "structure_id")
	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@Column(name = "category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "subcategory_id")
	public Long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(Long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "quantity_version")
	public Integer getQuantityVersion() {
		return quantityVersion;
	}

	public void setQuantityVersion(Integer quantityVersion) {
		this.quantityVersion = quantityVersion;
	}

	@Column(name = "file_id")
	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "max_rate")
	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

	@Column(name = "vendor_description")
	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	@Column(name = "unit_id")
	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

}
