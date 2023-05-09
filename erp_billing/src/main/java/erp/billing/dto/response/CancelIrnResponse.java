package erp.billing.dto.response;

import java.util.List;

public class CancelIrnResponse {

	private ClientIrnCancelResponse cancelIrnResponse;

	private List<String> errorDetails;

	public CancelIrnResponse() {
		super();
	}

	public CancelIrnResponse(ClientIrnCancelResponse cancelIrnResponse, List<String> errorDetails) {
		super();
		this.cancelIrnResponse = cancelIrnResponse;
		this.errorDetails = errorDetails;
	}

	public ClientIrnCancelResponse getCancelIrnResponse() {
		return cancelIrnResponse;
	}

	public void setCancelIrnResponse(ClientIrnCancelResponse cancelIrnResponse) {
		this.cancelIrnResponse = cancelIrnResponse;
	}

	public List<String> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(List<String> errorDetails) {
		this.errorDetails = errorDetails;
	}

}
