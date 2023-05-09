package erp.billing.dto;

import java.util.List;

public class CustomResponse {

	private Integer status;

	private Object data;

	private String message;

	private List<String> errorList;

	public CustomResponse() {
		super();
	}

	public CustomResponse(Integer status, Object data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public CustomResponse(Integer status, Object data, String message, List<String> errorList) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
		this.errorList = errorList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

}
