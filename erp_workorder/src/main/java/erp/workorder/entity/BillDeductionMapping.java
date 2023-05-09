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

@Entity
@Table(name = "bill_deduction_mapping")
public class BillDeductionMapping implements Serializable {

	private static final long serialVersionUID = -19319829869808499L;

	private Long id;

	private Long billId;

	private Integer workorderBillInfoId;

	private Integer billDeductionId;

	private Boolean isPercentage;

	private Double value;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Date modifiedOn;

	private Long modifiedBy;

	private BillDeduction billDeduction;

	public BillDeductionMapping() {
		super();
	}

	public BillDeductionMapping(Long id) {
		super();
		this.id = id;
	}

	public BillDeductionMapping(Long id, Long billId, Integer workorderBillInfoId, Integer billDeductionId,
			Boolean isPercentage, Double value, Boolean isActive, Date createdOn, Long createdBy, Date modifiedOn,
			Long modifiedBy) {
		super();
		this.id = id;
		this.billId = billId;
		this.workorderBillInfoId = workorderBillInfoId;
		this.billDeductionId = billDeductionId;
		this.isPercentage = isPercentage;
		this.value = value;
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

	@Column(name = "bill_id")
	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	@Column(name = "workorder_bill_info_id")
	public Integer getWorkorderBillInfoId() {
		return workorderBillInfoId;
	}

	public void setWorkorderBillInfoId(Integer workorderBillInfoId) {
		this.workorderBillInfoId = workorderBillInfoId;
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
	@JoinColumn(name = "bill_deduction_id", insertable = false, updatable = false)
	public BillDeduction getBillDeduction() {
		return billDeduction;
	}

	public void setBillDeduction(BillDeduction billDeduction) {
		this.billDeduction = billDeduction;
	}

}
