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
@Table(name = "category_subcategory_transacs")
public class CategorySubcategoryTransacs implements Serializable{
	

	private static final long serialVersionUID = -8536708879691276087L;

	private Long id;
	
	private Long categoryId;
	
	private Long subcategoryId;
	
	private Date createdOn;
	
	private Long createdBy;

	public CategorySubcategoryTransacs() {
		super();
	}

	public CategorySubcategoryTransacs(Long id) {
		super();
		this.id = id;
	}

	public CategorySubcategoryTransacs(Long id, Long categoryId, Long subcategoryId, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.subcategoryId = subcategoryId;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	
}
