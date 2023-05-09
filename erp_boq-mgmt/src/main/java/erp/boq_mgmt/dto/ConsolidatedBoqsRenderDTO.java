package erp.boq_mgmt.dto;

public class ConsolidatedBoqsRenderDTO {

	private Object data;

	private Double totalAmount;

	public ConsolidatedBoqsRenderDTO() {
		super();
	}

	public ConsolidatedBoqsRenderDTO(Object data, Double totalAmount) {
		super();
		this.data = data;
		this.totalAmount = totalAmount;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
