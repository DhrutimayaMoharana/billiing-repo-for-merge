package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category_item")
public class CategoryItem implements Serializable {

	private static final long serialVersionUID = 7000461202932093131L;

	private Long id;

	private String code;

	private String standardBookCode;

	private String name;

	private Boolean ofStructure;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private Integer companyId;

	public CategoryItem() {
		super();
	}

	public CategoryItem(Long id) {
		super();
		this.id = id;
	}

	public CategoryItem(Long id, String code, String stndardBookCode, String name, Boolean ofStructure,
			Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn, Long modifiedBy, Integer companyId) {
		super();
		this.id = id;
		this.code = code;
		this.standardBookCode = stndardBookCode;
		this.name = name;
		this.ofStructure = ofStructure;
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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "standard_book_code")
	public String getStandardBookCode() {
		return standardBookCode;
	}

	public void setStandardBookCode(String standardBookCode) {
		this.standardBookCode = standardBookCode;
	}

	@Column(name = "of_structure")
	public Boolean getOfStructure() {
		return ofStructure;
	}

	public void setOfStructure(Boolean ofStructure) {
		this.ofStructure = ofStructure;
	}

}
