package erp.billing.dto.request;

import java.util.List;

import erp.billing.dto.UserDetail;

public class ClientInvoicePdfRequest {

	private List<Long> ClientInvoiceId;

	private Long siteId;

	private UserDetail userDetail;

	public ClientInvoicePdfRequest() {
		super();
	}

	public ClientInvoicePdfRequest(List<Long> clientInvoiceId, Long siteId, UserDetail userDetail) {
		super();
		ClientInvoiceId = clientInvoiceId;
		this.siteId = siteId;
		this.userDetail = userDetail;
	}

	public List<Long> getClientInvoiceId() {
		return ClientInvoiceId;
	}

	public void setClientInvoiceId(List<Long> clientInvoiceId) {
		ClientInvoiceId = clientInvoiceId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUser() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
