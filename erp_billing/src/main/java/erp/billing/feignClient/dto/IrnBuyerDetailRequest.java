package erp.billing.feignClient.dto;

public class IrnBuyerDetailRequest {

	private String gstin;

	private String lglNm;

	private String trdNm;

	private String pos;

	private String addr1;

	private String addr2;

	private String loc;

	private String pin;

	private String stcd;

	private String ph;

	private String em;

	public String getGstin() {
		return gstin;
	}

	public IrnBuyerDetailRequest(String gstin, String lglNm, String trdNm, String pos, String addr1, String addr2,
			String loc, String pin, String stcd, String ph, String em) {
		super();
		this.gstin = gstin;
		this.lglNm = lglNm;
		this.trdNm = trdNm;
		this.pos = pos;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.loc = loc;
		this.pin = pin;
		this.stcd = stcd;
		this.ph = ph;
		this.em = em;
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

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
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

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getEm() {
		return em;
	}

	public void setEm(String em) {
		this.em = em;
	}

}