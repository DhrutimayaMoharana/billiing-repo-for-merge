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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table
@Entity(name = "category_subcategory_mapping")
public class CategorySubcategoryMapping implements Serializable {
	

	private static final long serialVersionUID = -9068516849640242615L;

	private Long id;
	
	private CategoryItem category;
	
	private SubcategoryItem subcategory;
	
	private Date modifiedOn;
	
	private Long modifiedBy;
	
	public CategorySubcategoryMapping() {
		super();
	}
	
	public CategorySubcategoryMapping(Long id) {
		super();
		this.id = id;
	}

	public CategorySubcategoryMapping(Long id, CategoryItem category, SubcategoryItem subcategory, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.category = category;
		this.subcategory = subcategory;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id")
	public CategoryItem getCategory() {
		return category;
	}

	public void setCategory(CategoryItem category) {
		this.category = category;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="subcategory_id")
	public SubcategoryItem getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubcategoryItem subcategory) {
		this.subcategory = subcategory;
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


}
