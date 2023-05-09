package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "wo_consultant_work_items")
public class WorkorderConsultantWorkItemMapping implements Serializable {

	private static final long serialVersionUID = 3214813669922967435L;

	private Long id;

	private Long workorderConsultantWorkId;

	private String categoryDescription;

	private String description;

	private ChainageTraverse chainage;

	private Double quantity;

	private Double rate;

	private Unit unit;

	private String remark;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private WorkorderConsultantWork workorderConsultantWork;

	public WorkorderConsultantWorkItemMapping() {
		super();
	}

	public WorkorderConsultantWorkItemMapping(Long id) {
		super();
		this.id = id;
	}

	public WorkorderConsultantWorkItemMapping(Long id, Long workorderConsultantWorkId, String categoryDescription,
			String description, ChainageTraverse chainage, Double quantity, Double rate, Unit unit, String remark,
			Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.workorderConsultantWorkId = workorderConsultantWorkId;
		this.categoryDescription = categoryDescription;
		this.description = description;
		this.chainage = chainage;
		this.quantity = quantity;
		this.rate = rate;
		this.unit = unit;
		this.remark = remark;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
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

	@Column(name = "wo_consultant_work_id")
	public Long getWorkorderConsultantWorkId() {
		return workorderConsultantWorkId;
	}

	public void setWorkorderConsultantWorkId(Long workorderConsultantWorkId) {
		this.workorderConsultantWorkId = workorderConsultantWorkId;
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

	@OneToOne
	@JoinColumn(name = "unit_id")
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "chainage_id")
	public ChainageTraverse getChainage() {
		return chainage;
	}

	public void setChainage(ChainageTraverse chainage) {
		this.chainage = chainage;
	}

	@OneToOne
	@JoinColumn(name = "wo_consultant_work_id", insertable = false, updatable = false)
	public WorkorderConsultantWork getWorkorderConsultantWork() {
		return workorderConsultantWork;
	}

	public void setWorkorderConsultantWork(WorkorderConsultantWork workorderConsultantWork) {
		this.workorderConsultantWork = workorderConsultantWork;
	}

	@Column(name = "category_description")
	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}
