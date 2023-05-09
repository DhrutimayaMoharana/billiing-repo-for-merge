package erp.billing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import erp.billing.enums.IrnCancelType;

@Entity
@Table(name = "client_invoice_irn_info")
public class ClientInvoiceIrnInfo {

	private Long id;

	private Long clientInvoiceId;

	private String ackNo;

	private Date ackDate;

	private String irn;

	private String signedInvoice;

	private String signedQrCode;

	private Date cancelDate;

	private IrnCancelType cancelReason;

	private String cancelRemark;

	private Boolean isActive;

	private Date createdOn;

	private Integer createdBy;

	private Date updatedOn;

	private Integer updatedBy;

	public ClientInvoiceIrnInfo() {
		super();
	}

	public ClientInvoiceIrnInfo(Long id, Long clientInvoiceId, String ackNo, Date ackDate, String irn,
			String signedInvoice, String signedQrCode, Date cancelDate, IrnCancelType cancelReason, String cancelRemark,
			Boolean isActive, Date createdOn, Integer createdBy, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.clientInvoiceId = clientInvoiceId;
		this.ackNo = ackNo;
		this.ackDate = ackDate;
		this.irn = irn;
		this.signedInvoice = signedInvoice;
		this.signedQrCode = signedQrCode;
		this.cancelDate = cancelDate;
		this.cancelReason = cancelReason;
		this.cancelRemark = cancelRemark;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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

	@Column(name = "ack_no")
	public String getAckNo() {
		return ackNo;
	}

	public void setAckNo(String ackNo) {
		this.ackNo = ackNo;
	}

	@Column(name = "ack_date")
	public Date getAckDate() {
		return ackDate;
	}

	public void setAckDate(Date ackDate) {
		this.ackDate = ackDate;
	}

	@Column(name = "irn")
	public String getIrn() {
		return irn;
	}

	public void setIrn(String irn) {
		this.irn = irn;
	}

	@Column(name = "signed_invoice")
	public String getSignedInvoice() {
		return signedInvoice;
	}

	public void setSignedInvoice(String signedInvoice) {
		this.signedInvoice = signedInvoice;
	}

	@Column(name = "signed_qr_code")
	public String getSignedQrCode() {
		return signedQrCode;
	}

	public void setSignedQrCode(String signedQrCode) {
		this.signedQrCode = signedQrCode;
	}

	@Column(name = "cancel_date")
	public Date getCancelDate() {
		return cancelDate;
	}

	@Column(name = "cancel_reason")
	public IrnCancelType getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(IrnCancelType cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Column(name = "cancel_remark")
	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
