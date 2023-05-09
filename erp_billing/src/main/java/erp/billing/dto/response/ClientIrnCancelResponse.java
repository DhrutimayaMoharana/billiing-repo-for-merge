package erp.billing.dto.response;

public class ClientIrnCancelResponse {

	private String Irn;

	private String CancelDate;

	public ClientIrnCancelResponse() {
		super();
	}

	public ClientIrnCancelResponse(String irn, String cancelDate) {
		super();
		Irn = irn;
		CancelDate = cancelDate;
	}

	public String getIrn() {
		return Irn;
	}

	public void setIrn(String irn) {
		Irn = irn;
	}

	public String getCancelDate() {
		return CancelDate;
	}

	public void setCancelDate(String cancelDate) {
		CancelDate = cancelDate;
	}

}
