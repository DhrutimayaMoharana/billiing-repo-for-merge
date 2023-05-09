package erp.billing.entity;

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
@Table(name = "boq_item")
public class BoqItem implements Serializable {

	private static final long serialVersionUID = 5902072003974503336L;

	private Long id;

	private String code;

	private String standardBookCode;

	private String name;

	private Unit unit;

	private CategoryItem category;

	private SubcategoryItem subcategory;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

	public BoqItem() {
		super();
	}

	public BoqItem(Long id) {
		super();
		this.id = id;
	}

	public BoqItem(Long id, String code, String standardBookCode, String name, Unit unit, CategoryItem category,
			SubcategoryItem subcategory, Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn,
			Long modifiedBy, Integer companyId) {
		super();
		this.id = id;
		this.category = category;
		this.subcategory = subcategory;
		this.code = code;
		this.standardBookCode = standardBookCode;
		this.name = name;
		this.unit = unit;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.companyId = companyId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_id")
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
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

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "standard_book_code")
	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	@OneToOne
	@JoinColumn(name = "category_id")
	public CategoryItem getCategory() {
		return category;
	}

	public void setCategory(CategoryItem category) {
		this.category = category;
	}

	@OneToOne
	@JoinColumn(name = "subcategory_id")
	public SubcategoryItem getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubcategoryItem subcategory) {
		this.subcategory = subcategory;
	}

}
