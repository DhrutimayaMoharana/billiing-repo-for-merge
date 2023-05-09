package erp.billing.entity;

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

@Entity
@Table(name = "bill_pay_milestones")
public class BillPayMilestones implements Serializable {

	private static final long serialVersionUID = 2621150929815420600L;

	private Long id;

	private Long billId;

	private Long workorderMilestoneId;

	private Boolean isActive;

	private Date modifiedOn;

	private Long modifiedBy;

	private Bill bill;

	public BillPayMilestones() {
		super();
	}

	public BillPayMilestones(Long id, Long billId, Long workorderMilestoneId, Boolean isActive, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.billId = billId;
		this.workorderMilestoneId = workorderMilestoneId;
		this.isActive = isActive;
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

	@Column(name = "bill_id")
	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	@Column(name = "wo_pay_milestone_id")
	public Long getWorkorderMilestoneId() {
		return workorderMilestoneId;
	}

	public void setWorkorderMilestoneId(Long workorderMilestoneId) {
		this.workorderMilestoneId = workorderMilestoneId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	@OneToOne
	@JoinColumn(name = "bill_id", insertable = false, updatable = false)
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

}
