package erp.boq_mgmt.dto;

public class CustomResponse {
	
	private Integer status;
	
	private Object data;
	
	private String message;
	
	public CustomResponse() {
		super();
	}

	public CustomResponse(Integer status, Object data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
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
	
	
}
