package erp.billing.dto.response;

public class ClientIrnGovtResponse {

	private Long AckNo;

	private String AckDt;

	private String Irn;

	private String SignedInvoice;

	private String SignedQRCode;

	private String Status;

	private Long EwbNo;

	private String EwbDt;

	private String EwbValidTill;

	private String Remarks;

	public ClientIrnGovtResponse() {
		super();
	}

	public Long getAckNo() {
		return AckNo;
	}

	public void setAckNo(Long AckNo) {
		this.AckNo = AckNo;
	}

	public String getAckDt() {
		return AckDt;
	}

	public void setAckDt(String AckDt) {
		this.AckDt = AckDt;
	}

	public String getIrn() {
		return Irn;
	}

	public void setIrn(String Irn) {
		this.Irn = Irn;
	}

	public String getSignedInvoice() {
		return SignedInvoice;
	}

	public void setSignedInvoice(String SignedInvoice) {
		this.SignedInvoice = SignedInvoice;
	}

	public String getSignedQRCode() {
		return SignedQRCode;
	}

	public void setSignedQRCode(String SignedQRCode) {
		this.SignedQRCode = SignedQRCode;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	public Long getEwbNo() {
		return EwbNo;
	}

	public void setEwbNo(Long EwbNo) {
		this.EwbNo = EwbNo;
	}

	public String getEwbDt() {
		return EwbDt;
	}

	public void setEwbDt(String EwbDt) {
		this.EwbDt = EwbDt;
	}

	public String getEwbValidTill() {
		return EwbValidTill;
	}

	public void setEwbValidTill(String EwbValidTill) {
		this.EwbValidTill = EwbValidTill;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	}

}
