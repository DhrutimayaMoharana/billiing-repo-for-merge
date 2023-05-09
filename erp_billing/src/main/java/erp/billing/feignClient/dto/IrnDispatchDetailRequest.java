package erp.billing.feignClient.dto;

public class IrnDispatchDetailRequest {

	private String nm;

	private String addr1;

	private String addr2;

	private String loc;

	private String pin;

	private String stcd;

	public String getNm() {
		return nm;
	}

	public IrnDispatchDetailRequest(String nm, String addr1, String addr2, String loc, String pin, String stcd) {
		super();
		this.nm = nm;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.loc = loc;
		this.pin = pin;
		this.stcd = stcd;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getStcd() {
		return stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

}