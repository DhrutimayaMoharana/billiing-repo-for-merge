package erp.boq_mgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "structure_boq_qty_mapping")
public class StructureBoqQuantityMapping implements Serializable {

	private static final long serialVersionUID = 5866111817000037218L;

	private Long id;

	private Structure structure;

	private String description;

	private String vendorDescription;

	private Unit unit;

	private BoqItem boq;

	private CategoryItem category;

	private SubcategoryItem subcategory;

	private Long siteId;

	private Double quantity;

	private Double rate;

	private Double maxRate;

	private String remark;

	private FileEntity file;

	private Integer version;

	private Integer quantityVersion;

	private Date modifiedOn;

	private Long modifiedBy;

	private Boolean isActive;

	private Integer companyId;

	public StructureBoqQuantityMapping() {
		super();
	}

	public StructureBoqQuantityMapping(Long id) {
		super();
		this.id = id;
	}

	public StructureBoqQuantityMapping(Long id, Structure structure, String description, String vendorDescription,
			Unit unit, BoqItem boq, CategoryItem category, SubcategoryItem subcategory, Long siteId, Double quantity,
			Double rate, Double maxRate, String remark, FileEntity file, Integer version, Integer quantityVersion,
			Date modifiedOn, Long modifiedBy, Boolean isActive, Integer companyId) {
		super();
		this.id = id;
		this.structure = structure;
		this.description = description;
		this.vendorDescription = vendorDescription;
		this.boq = boq;
		this.unit = unit;
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

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "structure_id")
	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boq_id")
	public BoqItem getBoq() {
		return boq;
	}

	public void setBoq(BoqItem boq) {
		this.boq = boq;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	public CategoryItem getCategory() {
		return category;
	}

	public void setCategory(CategoryItem category) {
		this.category = category;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subcategory_id")
	public SubcategoryItem getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubcategoryItem subcategory) {
		this.subcategory = subcategory;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "modified_on")
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Column(name = "modified_by")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "quantity_version")
	public Integer getQuantityVersion() {
		return quantityVersion;
	}

	public void setQuantityVersion(Integer quantityVersion) {
		this.quantityVersion = quantityVersion;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "file_id")
	public FileEntity getFile() {
		return file;
	}

	public void setFile(FileEntity file) {
		this.file = file;
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_id")
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
