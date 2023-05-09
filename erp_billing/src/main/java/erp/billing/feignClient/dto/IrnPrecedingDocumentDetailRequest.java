package erp.billing.feignClient.dto;

public class IrnPrecedingDocumentDetailRequest {

	private String invNo;

	private String invDt;

	private String othRefNo;

	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	public String getInvDt() {
		return invDt;
	}

	public void setInvDt(String invDt) {
		this.invDt = invDt;
	}

	public String getOthRefNo() {
		return othRefNo;
	}

	public void setOthRefNo(String othRefNo) {
		this.othRefNo = othRefNo;
	}

}