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
@Table(name = "bill_deduction")
public class BillDeduction implements Serializable {

	private static final long serialVersionUID = 608262629476844473L;

	private Integer id;

	private String name;

	private Boolean isActive;

	private Integer companyId;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	public BillDeduction() {
		super();
	}

	public BillDeduction(Integer id) {
		super();
		this.id = id;
	}

	public BillDeduction(Integer id, String name, Boolean isActive, Integer companyId, Date createdOn, Long createdBy,
			Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.companyId = companyId;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.setModifiedOn(modifiedOn);
		this.setModifiedBy(modifiedBy);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	@Column(name = "company_id")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

}
