package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bill_boq_quantity_item_transac")
public class BillBoqQuantityItemTransac implements Serializable {

	private static final long serialVersionUID = 6466738128367925082L;

	private Long id;

	private Long billId;

	private Long boqId;

	private Long fromChainageId;

	private Long toChainageId;

	private Double quantity;

	private Double rate;

	private Long siteId;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public BillBoqQuantityItemTransac() {
		super();
	}

	public BillBoqQuantityItemTransac(Long id, Long billId, Long boqId, Long fromChainageId, Long toChainageId,
			Double quantity, Double rate, Long siteId, Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.billId = billId;
		this.boqId = boqId;
		this.fromChainageId = fromChainageId;
		this.toChainageId = toChainageId;
		this.quantity = quantity;
		this.rate = rate;
		this.siteId = siteId;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
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

	@Column(name = "bill_id")
	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	@Column(name = "boq_id")
	public Long getBoqId() {
		return boqId;
	}

	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}

	@Column(name = "from_chainage_id")
	public Long getFromChainageId() {
		return fromChainageId;
	}

	public void setFromChainageId(Long fromChainageId) {
		this.fromChainageId = fromChainageId;
	}

	@Column(name = "to_chainage_id")
	public Long getToChainageId() {
		return toChainageId;
	}

	public void setToChainageId(Long toChainageId) {
		this.toChainageId = toChainageId;
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

	@Column(name = "rate")
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
