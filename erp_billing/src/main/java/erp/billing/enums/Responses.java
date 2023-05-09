package erp.billing.enums;

public enum Responses {
	
	UNAUTHORIZED(401),
	SUCCESS(200),
	BAD_REQUEST(400),
	INVALID_CREDENTIALS(401),
	FORBIDDEN(403),
	PERMISSION_DENIED(401),
	PROBLEM_OCCURRED(500),
	SERVICE_DOWN(403),
	NOT_FOUND(404);
	
	private int code;
	
	Responses(Integer code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

}
