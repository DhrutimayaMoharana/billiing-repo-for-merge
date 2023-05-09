package erp.billing.feignClient.dto;

import java.util.List;

import erp.billing.dto.UserDetail;

public class IrnGenerateRequest {

	private String version;

	private IrnTransactionDetailRequest tranDtls;

	private IrnDocumentDetailRequest docDtls;

	private IrnSellerDetailRequest sellerDtls;

	private IrnBuyerDetailRequest buyerDtls;

	private IrnDispatchDetailRequest dispDtls;

	private IrnShippingDetailRequest shipDtls;

	private List<IrnItemRequest> itemList = null;

	private IrnValueDetailRequest valDtls;

	private IrnPaymentDetailRequest payDtls;

	private IrnReferenceDetailRequest refDtls;

	private List<IrnAdditionalDocumentDetailRequest> addlDocDtls = null;

	private IrnExportDetailRequest expDtls;

	private IrnEwayBillDetailRequest ewbDtls;

	private UserDetail userDetail;

	private Integer siteId;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public IrnTransactionDetailRequest getTranDtls() {
		return tranDtls;
	}

	public void setTranDtls(IrnTransactionDetailRequest tranDtls) {
		this.tranDtls = tranDtls;
	}

	public IrnDocumentDetailRequest getDocDtls() {
		return docDtls;
	}

	public void setDocDtls(IrnDocumentDetailRequest docDtls) {
		this.docDtls = docDtls;
	}

	public IrnSellerDetailRequest getSellerDtls() {
		return sellerDtls;
	}

	public void setSellerDtls(IrnSellerDetailRequest sellerDtls) {
		this.sellerDtls = sellerDtls;
	}

	public IrnBuyerDetailRequest getBuyerDtls() {
		return buyerDtls;
	}

	public void setBuyerDtls(IrnBuyerDetailRequest buyerDtls) {
		this.buyerDtls = buyerDtls;
	}

	public IrnDispatchDetailRequest getDispDtls() {
		return dispDtls;
	}

	public void setDispDtls(IrnDispatchDetailRequest dispDtls) {
		this.dispDtls = dispDtls;
	}

	public IrnShippingDetailRequest getShipDtls() {
		return shipDtls;
	}

	public void setShipDtls(IrnShippingDetailRequest shipDtls) {
		this.shipDtls = shipDtls;
	}

	public List<IrnItemRequest> getItemList() {
		return itemList;
	}

	public void setItemList(List<IrnItemRequest> itemList) {
		this.itemList = itemList;
	}

	public IrnValueDetailRequest getValDtls() {
		return valDtls;
	}

	public void setValDtls(IrnValueDetailRequest valDtls) {
		this.valDtls = valDtls;
	}

	public IrnPaymentDetailRequest getPayDtls() {
		return payDtls;
	}

	public void setPayDtls(IrnPaymentDetailRequest payDtls) {
		this.payDtls = payDtls;
	}

	public IrnReferenceDetailRequest getRefDtls() {
		return refDtls;
	}

	public void setRefDtls(IrnReferenceDetailRequest refDtls) {
		this.refDtls = refDtls;
	}

	public List<IrnAdditionalDocumentDetailRequest> getAddlDocDtls() {
		return addlDocDtls;
	}

	public void setAddlDocDtls(List<IrnAdditionalDocumentDetailRequest> addlDocDtls) {
		this.addlDocDtls = addlDocDtls;
	}

	public IrnExportDetailRequest getExpDtls() {
		return expDtls;
	}

	public void setExpDtls(IrnExportDetailRequest expDtls) {
		this.expDtls = expDtls;
	}

	public IrnEwayBillDetailRequest getEwbDtls() {
		return ewbDtls;
	}

	public void setEwbDtls(IrnEwayBillDetailRequest ewbDtls) {
		this.ewbDtls = ewbDtls;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

}