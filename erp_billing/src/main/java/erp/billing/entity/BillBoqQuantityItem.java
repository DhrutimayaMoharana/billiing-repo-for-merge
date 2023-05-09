package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill_boq_quantity_item")
public class BillBoqQuantityItem implements Serializable {

	private static final long serialVersionUID = -4626037564259452705L;

	private Long id;

	private Long billBoqItemId;

	private BillBoqItem billBoqItem;

	private Chainage fromChainage;

	private Chainage toChainage;

	private Double quantity;

	private Double rate;

	private Long siteId;

	private Long workorderId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	public BillBoqQuantityItem() {
		super();
	}

	public BillBoqQuantityItem(Long id) {
		super();
		this.id = id;
	}

	public BillBoqQuantityItem(Long id, Long billBoqItemId, Chainage fromChainage, Chainage toChainage, Double quantity,
			Double rate, Long siteId, Long workorderId, Boolean isActive, Date createdOn, Long createdBy,
			Date modifiedOn, Long modifiedBy) {
		super();
		this.id = id;
		this.billBoqItemId = billBoqItemId;
		this.fromChainage = fromChainage;
		this.toChainage = toChainage;
		this.quantity = quantity;
		this.rate = rate;
		this.siteId = siteId;
		this.workorderId = workorderId;
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

	@Column(name = "bill_boq_id")
	public Long getBillBoqItemId() {
		return billBoqItemId;
	}

	public void setBillBoqItemId(Long billBoqItemId) {
		this.billBoqItemId = billBoqItemId;
	}

	@ManyToOne
	@JoinColumn(name = "bill_boq_id", insertable = false, updatable = false)
	public BillBoqItem getBillBoqItem() {
		return billBoqItem;
	}

	public void setBillBoqItem(BillBoqItem billBoqItem) {
		this.billBoqItem = billBoqItem;
	}

	@OneToOne
	@JoinColumn(name = "from_chainage_id")
	public Chainage getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(Chainage fromChainage) {
		this.fromChainage = fromChainage;
	}

	@OneToOne
	@JoinColumn(name = "to_chainage_id")
	public Chainage getToChainage() {
		return toChainage;
	}

	public void setToChainage(Chainage toChainage) {
		this.toChainage = toChainage;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
