package erp.billing.dto.response;

import java.util.List;

public class ClientIrnResponse {

	private ClientIrnGovtResponse irnResponse;

	private List<String> errorDetails;

	private String infoDetails;

	public ClientIrnResponse() {
		super();
	}

	public ClientIrnResponse(ClientIrnGovtResponse irnResponse, List<String> errorDetails, String infoDetails) {
		super();
		this.irnResponse = irnResponse;
		this.errorDetails = errorDetails;
		this.infoDetails = infoDetails;
	}

	public ClientIrnGovtResponse getIrnResponse() {
		return irnResponse;
	}

	public void setIrnResponse(ClientIrnGovtResponse irnResponse) {
		this.irnResponse = irnResponse;
	}

	public List<String> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(List<String> errorDetails) {
		this.errorDetails = errorDetails;
	}

	public String getInfoDetails() {
		return infoDetails;
	}

	public void setInfoDetails(String infoDetails) {
		this.infoDetails = infoDetails;
	}

}
