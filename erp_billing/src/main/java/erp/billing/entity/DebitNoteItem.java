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
@Table(name = "contractor_employee_debit_note_items")
public class DebitNoteItem implements Serializable {

	private static final long serialVersionUID = -6314121247881350560L;

	private Long id;

	private Long workorderId;

	private DebitNote debitNote;

	private Material material;

	private Integer departmentId;

	private Date fromDate;

	private Date toDate;

	private Double quantity;

	private Double rate;

	private Double amount;

	private Double handlingCharge;

	private Double amountAfterHandlingCharge;

	private Double gst;

	private Double gstAmount;

	private Double finalAmount;

	private Boolean isActive;

	private DebitNoteTax handlingChargePercentage;

	public DebitNoteItem() {
		super();
	}

	public DebitNoteItem(Long id, Long workorderId, DebitNote debitNote, Material material, Integer departmentId,
			Date fromDate, Date toDate, Double quantity, Double rate, Double amount, Double handlingCharge,
			Double amountAfterHandlingCharge, Double gst, Double gstAmount, Double finalAmount, Boolean isActive) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.debitNote = debitNote;
		this.material = material;
		this.departmentId = departmentId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
		this.handlingCharge = handlingCharge;
		this.amountAfterHandlingCharge = amountAfterHandlingCharge;
		this.gst = gst;
		this.gstAmount = gstAmount;
		this.finalAmount = finalAmount;
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

	@ManyToOne
	@JoinColumn(name = "contractor_employee_debit_note_id")
	public DebitNote getDebitNote() {
		return debitNote;
	}

	public void setDebitNote(DebitNote debitNote) {
		this.debitNote = debitNote;
	}

	@OneToOne
	@JoinColumn(name = "material_id")
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@Column(name = "department_id")
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "from_date")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name = "to_date")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	@Column(name = "amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "handling_charge")
	public Double getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(Double handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	@Column(name = "after_handling_charge")
	public Double getAmountAfterHandlingCharge() {
		return amountAfterHandlingCharge;
	}

	public void setAmountAfterHandlingCharge(Double amountAfterHandlingCharge) {
		this.amountAfterHandlingCharge = amountAfterHandlingCharge;
	}

	@Column(name = "gst")
	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	@Column(name = "gst_amount")
	public Double getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(Double gstAmount) {
		this.gstAmount = gstAmount;
	}

	@Column(name = "final_amount")
	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "work_order_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	@OneToOne
	@JoinColumn(name = "handling_charge_percentage_id")
	public DebitNoteTax getHandlingChargePercentage() {
		return handlingChargePercentage;
	}

	public void setHandlingChargePercentage(DebitNoteTax handlingChargePercentage) {
		this.handlingChargePercentage = handlingChargePercentage;
	}

}
