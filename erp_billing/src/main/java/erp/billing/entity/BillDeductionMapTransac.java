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
@Table(name = "bill_deduction_map_transac")
public class BillDeductionMapTransac implements Serializable {

	private static final long serialVersionUID = -5589954825215842387L;

	private Long id;

	private Long billId;

	private Integer billDeductionId;

	private Boolean isPercentage;

	private Double value;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public BillDeductionMapTransac() {
		super();
	}

	public BillDeductionMapTransac(Long id) {
		super();
		this.id = id;
	}

	public BillDeductionMapTransac(Long id, Long billId, Integer billDeductionId, Boolean isPercentage, Double value,
			Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.billId = billId;
		this.billDeductionId = billDeductionId;
		this.isPercentage = isPercentage;
		this.value = value;
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

	@Column(name = "bill_deduction_id")
	public Integer getBillDeductionId() {
		return billDeductionId;
	}

	public void setBillDeductionId(Integer billDeductionId) {
		this.billDeductionId = billDeductionId;
	}

	@Column(name = "is_percentage")
	public Boolean getIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	@Column(name = "value")
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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

}
