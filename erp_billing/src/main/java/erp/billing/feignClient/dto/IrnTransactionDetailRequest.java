package erp.billing.feignClient.dto;

public class IrnTransactionDetailRequest {

	private String taxSch;

	private String supTyp;

	private String regRev;

	private Object ecmGstin;

	private String igstOnIntra;

	public IrnTransactionDetailRequest() {
		super();
	}

	public IrnTransactionDetailRequest(String taxSch, String supTyp, String regRev, Object ecmGstin,
			String igstOnIntra) {
		super();
		this.taxSch = taxSch;
		this.supTyp = supTyp;
		this.regRev = regRev;
		this.ecmGstin = ecmGstin;
		this.igstOnIntra = igstOnIntra;
	}

	public String getTaxSch() {
		return taxSch;
	}

	public void setTaxSch(String taxSch) {
		this.taxSch = taxSch;
	}

	public String getSupTyp() {
		return supTyp;
	}

	public void setSupTyp(String supTyp) {
		this.supTyp = supTyp;
	}

	public String getRegRev() {
		return regRev;
	}

	public void setRegRev(String regRev) {
		this.regRev = regRev;
	}

	public Object getEcmGstin() {
		return ecmGstin;
	}

	public void setEcmGstin(Object ecmGstin) {
		this.ecmGstin = ecmGstin;
	}

	public String getIgstOnIntra() {
		return igstOnIntra;
	}

	public void setIgstOnIntra(String igstOnIntra) {
		this.igstOnIntra = igstOnIntra;
	}

}