package erp.workorder.dto;


public class PaginationDTO {
	
	Integer totalLength;
	
	Object data;

	public PaginationDTO() {
		super();
	}

	public PaginationDTO(Integer totalLength, Object data) {
		super();
		this.totalLength = totalLength;
		this.data = data;
	}

	public Integer getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}