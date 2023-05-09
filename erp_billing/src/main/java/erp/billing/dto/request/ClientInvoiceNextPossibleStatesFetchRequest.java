package erp.billing.dto.request;

import erp.billing.dto.UserDetail;

public class ClientInvoiceNextPossibleStatesFetchRequest {

	private Long clientInvoiceId;

	private Long siteId;

	private UserDetail user;

	public ClientInvoiceNextPossibleStatesFetchRequest() {
		super();
	}

	public Long getClientInvoiceId() {
		return clientInvoiceId;
	}

	public void setClientInvoiceId(Long clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public UserDetail getUser() {
		return user;
	}

	public void setUser(UserDetail user) {
		this.user = user;
	}

}
