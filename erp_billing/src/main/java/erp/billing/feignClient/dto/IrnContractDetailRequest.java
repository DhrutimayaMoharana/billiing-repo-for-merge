package erp.billing.feignClient.dto;

public class IrnContractDetailRequest {

	private String recAdvRefr;

	private String recAdvDt;

	private String tendRefr;

	private String contrRefr;

	private String extRefr;

	private String projRefr;

	private String pORefr;

	private String pORefDt;

	public String getRecAdvRefr() {
		return recAdvRefr;
	}

	public void setRecAdvRefr(String recAdvRefr) {
		this.recAdvRefr = recAdvRefr;
	}

	public String getRecAdvDt() {
		return recAdvDt;
	}

	public void setRecAdvDt(String recAdvDt) {
		this.recAdvDt = recAdvDt;
	}

	public String getTendRefr() {
		return tendRefr;
	}

	public void setTendRefr(String tendRefr) {
		this.tendRefr = tendRefr;
	}

	public String getContrRefr() {
		return contrRefr;
	}

	public void setContrRefr(String contrRefr) {
		this.contrRefr = contrRefr;
	}

	public String getExtRefr() {
		return extRefr;
	}

	public void setExtRefr(String extRefr) {
		this.extRefr = extRefr;
	}

	public String getProjRefr() {
		return projRefr;
	}

	public void setProjRefr(String projRefr) {
		this.projRefr = projRefr;
	}

	public String getPORefr() {
		return pORefr;
	}

	public void setPORefr(String pORefr) {
		this.pORefr = pORefr;
	}

	public String getPORefDt() {
		return pORefDt;
	}

	public void setPORefDt(String pORefDt) {
		this.pORefDt = pORefDt;
	}

}