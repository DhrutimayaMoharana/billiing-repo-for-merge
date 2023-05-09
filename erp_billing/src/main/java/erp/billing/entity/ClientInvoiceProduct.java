package erp.billing.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "client_invoice_products")
public class ClientInvoiceProduct implements Serializable {

	private static final long serialVersionUID = -4927112005098746232L;

	private Long id;

	private Long clientInvoiceId;

	private GstManagement gstManagement;

	private Double amount;

	private Boolean isIgst;

	private Double applicableCgstPercentage;

	private Double applicableCgstAmount;

	private Double applicableSgstPercentage;

	private Double applicableSgstAmount;

	private String remark;

	private Unit unit;

	private Double quantity;

	private Boolean isActive;

	private Date updatedOn;

	private Long updatedBy;

	public ClientInvoiceProduct() {
		super();
	}

	public ClientInvoiceProduct(Long id, Long clientInvoiceId, GstManagement gstManagement, Double amount,
			Boolean isIgst, Double applicableCgstPercentage, Double applicableCgstAmount,
			Double applicableSgstPercentage, Double applicableSgstAmount, String remark, Unit unit, Double quantity,
			Boolean isActive, Date updatedOn, Long updatedBy) {
		super();
		this.id = id;
		this.clientInvoiceId = clientInvoiceId;
		this.gstManagement = gstManagement;
		this.amount = amount;
		this.isIgst = isIgst;
		this.applicableCgstPercentage = applicableCgstPercentage;
		this.applicableCgstAmount = applicableCgstAmount;
		this.applicableSgstPercentage = applicableSgstPercentage;
		this.applicableSgstAmount = applicableSgstAmount;
		this.remark = remark;
		this.unit = unit;
		this.quantity = quantity;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "client_invoice_id")
	public Long getClientInvoiceId() {
		return clientInvoiceId;
	}

	public void setClientInvoiceId(Long clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gst_management_id")
	public GstManagement getGstManagement() {
		return gstManagement;
	}

	public void setGstManagement(GstManagement gstManagement) {
		this.gstManagement = gstManagement;
	}

	@Column(name = "amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "is_igst")
	public Boolean getIsIgst() {
		return isIgst;
	}

	public void setIsIgst(Boolean isIgst) {
		this.isIgst = isIgst;
	}

	@Column(name = "applicable_cgst_percentage")
	public Double getApplicableCgstPercentage() {
		return applicableCgstPercentage;
	}

	public void setApplicableCgstPercentage(Double applicableCgstPercentage) {
		this.applicableCgstPercentage = applicableCgstPercentage;
	}

	@Column(name = "applicable_cgst_amount")
	public Double getApplicableCgstAmount() {
		return applicableCgstAmount;
	}

	public void setApplicableCgstAmount(Double applicableCgstAmount) {
		this.applicableCgstAmount = applicableCgstAmount;
	}

	@Column(name = "applicable_sgst_percentage")
	public Double getApplicableSgstPercentage() {
		return applicableSgstPercentage;
	}

	public void setApplicableSgstPercentage(Double applicableSgstPercentage) {
		this.applicableSgstPercentage = applicableSgstPercentage;
	}

	@Column(name = "applicable_sgst_amount")
	public Double getApplicableSgstAmount() {
		return applicableSgstAmount;
	}

	public void setApplicableSgstAmount(Double applicableSgstAmount) {
		this.applicableSgstAmount = applicableSgstAmount;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_id")
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Column(name = "quantity")
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

}
