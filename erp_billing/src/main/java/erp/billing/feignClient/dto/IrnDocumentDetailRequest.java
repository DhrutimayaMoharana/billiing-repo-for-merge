package erp.billing.feignClient.dto;

public class IrnDocumentDetailRequest {

	private String typ;

	private String no;

	private String dt;

	public IrnDocumentDetailRequest() {
		super();
	}

	public IrnDocumentDetailRequest(String typ, String no, String dt) {
		super();
		this.typ = typ;
		this.no = no;
		this.dt = dt;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

}