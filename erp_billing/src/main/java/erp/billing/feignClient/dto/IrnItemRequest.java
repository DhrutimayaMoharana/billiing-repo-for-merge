package erp.billing.feignClient.dto;

import java.util.List;

public class IrnItemRequest {

	private String slNo;

	private String prdDesc;

	private String isServc;

	private String hsnCd;

	private String barcde;

	private Double qty;

	private Integer freeQty;

	private String unit;

	private Double unitPrice;

	private Double totAmt;

	private Double discount;

	private Double preTaxVal;

	private Double assAmt;

	private Double gstRt;

	private Double igstAmt;

	private Double cgstAmt;

	private Double sgstAmt;

	private Double cesRt;

	private Double cesAmt;

	private Double cesNonAdvlAmt;

	private Double stateCesRt;

	private Double stateCesAmt;

	private Double stateCesNonAdvlAmt;

	private Double othChrg;

	private Double totItemVal;

	private String ordLineRef;

	private String orgCntry;

	private String prdSlNo;

	private IrnBatchDetailRequest bchDtls;

	private List<IrnAttributeDetailRequest> attribDtls = null;

	public IrnItemRequest() {
		super();
	}

	public IrnItemRequest(String slNo, String prdDesc, String isServc, String hsnCd, String barcde, Double qty,
			Integer freeQty, String unit, Double unitPrice, Double totAmt, Double discount, Double preTaxVal,
			Double assAmt, Double gstRt, Double igstAmt, Double cgstAmt, Double sgstAmt, Double cesRt, Double cesAmt,
			Double cesNonAdvlAmt, Double stateCesRt, Double stateCesAmt, Double stateCesNonAdvlAmt, Double othChrg,
			Double totItemVal, String ordLineRef, String orgCntry, String prdSlNo, IrnBatchDetailRequest bchDtls,
			List<IrnAttributeDetailRequest> attribDtls) {
		super();
		this.slNo = slNo;
		this.prdDesc = prdDesc;
		this.isServc = isServc;
		this.hsnCd = hsnCd;
		this.barcde = barcde;
		this.qty = qty;
		this.freeQty = freeQty;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.totAmt = totAmt;
		this.discount = discount;
		this.preTaxVal = preTaxVal;
		this.assAmt = assAmt;
		this.gstRt = gstRt;
		this.igstAmt = igstAmt;
		this.cgstAmt = cgstAmt;
		this.sgstAmt = sgstAmt;
		this.cesRt = cesRt;
		this.cesAmt = cesAmt;
		this.cesNonAdvlAmt = cesNonAdvlAmt;
		this.stateCesRt = stateCesRt;
		this.stateCesAmt = stateCesAmt;
		this.stateCesNonAdvlAmt = stateCesNonAdvlAmt;
		this.othChrg = othChrg;
		this.totItemVal = totItemVal;
		this.ordLineRef = ordLineRef;
		this.orgCntry = orgCntry;
		this.prdSlNo = prdSlNo;
		this.bchDtls = bchDtls;
		this.attribDtls = attribDtls;
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getPrdDesc() {
		return prdDesc;
	}

	public void setPrdDesc(String prdDesc) {
		this.prdDesc = prdDesc;
	}

	public String getIsServc() {
		return isServc;
	}

	public void setIsServc(String isServc) {
		this.isServc = isServc;
	}

	public String getHsnCd() {
		return hsnCd;
	}

	public void setHsnCd(String hsnCd) {
		this.hsnCd = hsnCd;
	}

	public String getBarcde() {
		return barcde;
	}

	public void setBarcde(String barcde) {
		this.barcde = barcde;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Integer getFreeQty() {
		return freeQty;
	}

	public void setFreeQty(Integer freeQty) {
		this.freeQty = freeQty;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(Double totAmt) {
		this.totAmt = totAmt;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getPreTaxVal() {
		return preTaxVal;
	}

	public void setPreTaxVal(Double preTaxVal) {
		this.preTaxVal = preTaxVal;
	}

	public Double getAssAmt() {
		return assAmt;
	}

	public void setAssAmt(Double assAmt) {
		this.assAmt = assAmt;
	}

	public Double getGstRt() {
		return gstRt;
	}

	public void setGstRt(Double gstRt) {
		this.gstRt = gstRt;
	}

	public Double getIgstAmt() {
		return igstAmt;
	}

	public void setIgstAmt(Double igstAmt) {
		this.igstAmt = igstAmt;
	}

	public Double getCgstAmt() {
		return cgstAmt;
	}

	public void setCgstAmt(Double cgstAmt) {
		this.cgstAmt = cgstAmt;
	}

	public Double getSgstAmt() {
		return sgstAmt;
	}

	public void setSgstAmt(Double sgstAmt) {
		this.sgstAmt = sgstAmt;
	}

	public Double getCesRt() {
		return cesRt;
	}

	public void setCesRt(Double cesRt) {
		this.cesRt = cesRt;
	}

	public Double getCesAmt() {
		return cesAmt;
	}

	public void setCesAmt(Double cesAmt) {
		this.cesAmt = cesAmt;
	}

	public Double getCesNonAdvlAmt() {
		return cesNonAdvlAmt;
	}

	public void setCesNonAdvlAmt(Double cesNonAdvlAmt) {
		this.cesNonAdvlAmt = cesNonAdvlAmt;
	}

	public Double getStateCesRt() {
		return stateCesRt;
	}

	public void setStateCesRt(Double stateCesRt) {
		this.stateCesRt = stateCesRt;
	}

	public Double getStateCesAmt() {
		return stateCesAmt;
	}

	public void setStateCesAmt(Double stateCesAmt) {
		this.stateCesAmt = stateCesAmt;
	}

	public Double getStateCesNonAdvlAmt() {
		return stateCesNonAdvlAmt;
	}

	public void setStateCesNonAdvlAmt(Double stateCesNonAdvlAmt) {
		this.stateCesNonAdvlAmt = stateCesNonAdvlAmt;
	}

	public Double getOthChrg() {
		return othChrg;
	}

	public void setOthChrg(Double othChrg) {
		this.othChrg = othChrg;
	}

	public Double getTotItemVal() {
		return totItemVal;
	}

	public void setTotItemVal(Double totItemVal) {
		this.totItemVal = totItemVal;
	}

	public String getOrdLineRef() {
		return ordLineRef;
	}

	public void setOrdLineRef(String ordLineRef) {
		this.ordLineRef = ordLineRef;
	}

	public String getOrgCntry() {
		return orgCntry;
	}

	public void setOrgCntry(String orgCntry) {
		this.orgCntry = orgCntry;
	}

	public String getPrdSlNo() {
		return prdSlNo;
	}

	public void setPrdSlNo(String prdSlNo) {
		this.prdSlNo = prdSlNo;
	}

	public IrnBatchDetailRequest getBchDtls() {
		return bchDtls;
	}

	public void setBchDtls(IrnBatchDetailRequest bchDtls) {
		this.bchDtls = bchDtls;
	}

	public List<IrnAttributeDetailRequest> getAttribDtls() {
		return attribDtls;
	}

	public void setAttribDtls(List<IrnAttributeDetailRequest> attribDtls) {
		this.attribDtls = attribDtls;
	}

}