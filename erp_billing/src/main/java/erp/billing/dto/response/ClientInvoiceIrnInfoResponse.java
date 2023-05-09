package erp.billing.dto.response;

import java.util.Date;

public class ClientInvoiceIrnInfoResponse {

	private String ackNo;

	private Date ackDt;

	private String irn;

	private String signedInvoice;

	private String signedQRCode;

	private Date cancelDate;

	private String cancelReason;

	private String cancelRemark;

	public ClientInvoiceIrnInfoResponse() {
		super();
	}

	public ClientInvoiceIrnInfoResponse(String ackNo, Date ackDt, String irn, String signedInvoice, String signedQRCode,
			Date cancelDate, String cancelReason, String cancelRemark) {
		super();
		this.ackNo = ackNo;
		this.ackDt = ackDt;
		this.irn = irn;
		this.signedInvoice = signedInvoice;
		this.signedQRCode = signedQRCode;
		this.cancelDate = cancelDate;
		this.cancelReason = cancelReason;
		this.cancelRemark = cancelRemark;
	}

	public String getAckNo() {
		return ackNo;
	}

	public void setAckNo(String ackNo) {
		this.ackNo = ackNo;
	}

	public Date getAckDt() {
		return ackDt;
	}

	public void setAckDt(Date ackDt) {
		this.ackDt = ackDt;
	}

	public String getIrn() {
		return irn;
	}

	public void setIrn(String irn) {
		this.irn = irn;
	}

	public String getSignedInvoice() {
		return signedInvoice;
	}

	public void setSignedInvoice(String signedInvoice) {
		this.signedInvoice = signedInvoice;
	}

	public String getSignedQRCode() {
		return signedQRCode;
	}

	public void setSignedQRCode(String signedQRCode) {
		this.signedQRCode = signedQRCode;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

}
