package erp.billing.feignClient.dto;

public class IrnShippingDetailRequest {

	private String gstin;
	private String lglNm;
	private String trdNm;
	private String addr1;
	private String addr2;
	private String loc;
	private Integer pin;
	private String stcd;

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getLglNm() {
		return lglNm;
	}

	public void setLglNm(String lglNm) {
		this.lglNm = lglNm;
	}

	public String getTrdNm() {
		return trdNm;
	}

	public void setTrdNm(String trdNm) {
		this.trdNm = trdNm;
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

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}

	public String getStcd() {
		return stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

}