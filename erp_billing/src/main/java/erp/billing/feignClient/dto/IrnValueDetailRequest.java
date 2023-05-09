package erp.billing.feignClient.dto;

public class IrnValueDetailRequest {

	private Double assVal;

	private Double cgstVal;

	private Double sgstVal;

	private Double igstVal;

	private Double cesVal;

	private Double stCesVal;

	private Double discount;

	private Double othChrg;

	private Double rndOffAmt;

	private Double totInvVal;

	private Double totInvValFc;

	public IrnValueDetailRequest() {
		super();
	}

	public IrnValueDetailRequest(Double assVal, Double cgstVal, Double sgstVal, Double igstVal, Double cesVal,
			Double stCesVal, Double discount, Double othChrg, Double rndOffAmt, Double totInvVal, Double totInvValFc) {
		super();
		this.assVal = assVal;
		this.cgstVal = cgstVal;
		this.sgstVal = sgstVal;
		this.igstVal = igstVal;
		this.cesVal = cesVal;
		this.stCesVal = stCesVal;
		this.discount = discount;
		this.othChrg = othChrg;
		this.rndOffAmt = rndOffAmt;
		this.totInvVal = totInvVal;
		this.totInvValFc = totInvValFc;
	}

	public Double getAssVal() {
		return assVal;
	}

	public void setAssVal(Double assVal) {
		this.assVal = assVal;
	}

	public Double getCgstVal() {
		return cgstVal;
	}

	public void setCgstVal(Double cgstVal) {
		this.cgstVal = cgstVal;
	}

	public Double getSgstVal() {
		return sgstVal;
	}

	public void setSgstVal(Double sgstVal) {
		this.sgstVal = sgstVal;
	}

	public Double getIgstVal() {
		return igstVal;
	}

	public void setIgstVal(Double igstVal) {
		this.igstVal = igstVal;
	}

	public Double getCesVal() {
		return cesVal;
	}

	public void setCesVal(Double cesVal) {
		this.cesVal = cesVal;
	}

	public Double getStCesVal() {
		return stCesVal;
	}

	public void setStCesVal(Double stCesVal) {
		this.stCesVal = stCesVal;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getOthChrg() {
		return othChrg;
	}

	public void setOthChrg(Double othChrg) {
		this.othChrg = othChrg;
	}

	public Double getRndOffAmt() {
		return rndOffAmt;
	}

	public void setRndOffAmt(Double rndOffAmt) {
		this.rndOffAmt = rndOffAmt;
	}

	public Double getTotInvVal() {
		return totInvVal;
	}

	public void setTotInvVal(Double totInvVal) {
		this.totInvVal = totInvVal;
	}

	public Double getTotInvValFc() {
		return totInvValFc;
	}

	public void setTotInvValFc(Double totInvValFc) {
		this.totInvValFc = totInvValFc;
	}

}