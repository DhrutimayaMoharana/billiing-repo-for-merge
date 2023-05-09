package erp.billing.dto.request;

import erp.billing.dto.UserDetail;
import erp.billing.enums.IrnCancelType;

public class ClientIrnCancelRequest {

	private Long clientInvoiceId;

	private IrnCancelType cancelReason;

	private String cancelRemark;

	private Integer siteId;

	private UserDetail userDetail;

	public ClientIrnCancelRequest() {
		super();
	}

	public Long getClientInvoiceId() {
		return clientInvoiceId;
	}

	public void setClientInvoiceId(Long clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}

	public IrnCancelType getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(IrnCancelType cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
