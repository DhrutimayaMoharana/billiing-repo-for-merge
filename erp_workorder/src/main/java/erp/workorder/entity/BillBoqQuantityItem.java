package erp.workorder.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill_boq_quantity_item")
public class BillBoqQuantityItem implements Serializable {

	private static final long serialVersionUID = -4626037564259452705L;

	private Long id;

	private Long billBoqItemId;

	private BillBoqItem billBoqItem;

	private Double quantity;

	private Double rate;

	private Long siteId;

	private Long workorderId;

	private Boolean isActive;

	public BillBoqQuantityItem() {
		super();
	}

	public BillBoqQuantityItem(Long id) {
		super();
		this.id = id;
	}

	public BillBoqQuantityItem(Long id, Long billBoqItemId, Double quantity, Double rate, Long siteId, Long workorderId,
			Boolean isActive) {
		super();
		this.id = id;
		this.billBoqItemId = billBoqItemId;
		this.quantity = quantity;
		this.rate = rate;
		this.siteId = siteId;
		this.workorderId = workorderId;
		this.isActive = isActive;
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

	@OneToOne
	@JoinColumn(name = "bill_boq_id", insertable = false, updatable = false)
	public BillBoqItem getBillBoqItem() {
		return billBoqItem;
	}

	public void setBillBoqItem(BillBoqItem billBoqItem) {
		this.billBoqItem = billBoqItem;
	}

}
