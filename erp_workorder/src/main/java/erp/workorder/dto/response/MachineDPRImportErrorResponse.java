package erp.workorder.dto.response;

import java.util.Set;

public class MachineDPRImportErrorResponse {

	private String message;

	private Set<Integer> error;

	public MachineDPRImportErrorResponse() {
		super();
	}

	public MachineDPRImportErrorResponse(String message, Set<Integer> error) {
		super();
		this.message = message;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<Integer> getError() {
		return error;
	}

	public void setError(Set<Integer> error) {
		this.error = error;
	}

}
