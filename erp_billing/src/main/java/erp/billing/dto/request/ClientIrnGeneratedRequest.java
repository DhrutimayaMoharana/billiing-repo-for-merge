package erp.billing.dto.request;

import erp.billing.dto.UserDetail;

public class ClientIrnGeneratedRequest {

	private Long clientInvoiceId;

	private UserDetail userDetail;

	public Long getClientInvoiceId() {
		return clientInvoiceId;
	}

	public void setClientInvoiceId(Long clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
