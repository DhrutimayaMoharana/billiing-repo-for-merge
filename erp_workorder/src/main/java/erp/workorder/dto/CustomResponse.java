package erp.workorder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class CustomResponse {

	private Integer status;

	private Object data;

	private String message;

	private Boolean isTncMapped;

	public CustomResponse() {
		super();
	}

	public CustomResponse(Integer status, Object data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public CustomResponse(Integer status, Object data, String message, Boolean isTncMapped) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
		this.isTncMapped = isTncMapped;
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

	public Boolean getIsTncMapped() {
		return isTncMapped;
	}

	public void setIsTncMapped(Boolean isTncMapped) {
		this.isTncMapped = isTncMapped;
	}

}
