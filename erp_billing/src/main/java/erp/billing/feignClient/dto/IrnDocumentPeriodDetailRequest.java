package erp.billing.feignClient.dto;

public class IrnDocumentPeriodDetailRequest {

	private String invStDt;

	private String invEndDt;

	public String getInvStDt() {
		return invStDt;
	}

	public void setInvStDt(String invStDt) {
		this.invStDt = invStDt;
	}

	public String getInvEndDt() {
		return invEndDt;
	}

	public void setInvEndDt(String invEndDt) {
		this.invEndDt = invEndDt;
	}

}