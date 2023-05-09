package erp.boq_mgmt.dto;

public class ExcelErrorDTO {
	
	private String error;
	
	private Integer rowNo;

	public ExcelErrorDTO(String error, Integer rowNo) {
		super();
		this.error = error;
		this.rowNo = rowNo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getRowNo() {
		return rowNo;
	}

	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

}
