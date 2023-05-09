package erp.workorder.dto;

public class RenderTypeWiseBoqWork {
	
	private Object data;
	
	private Double amount;

	public RenderTypeWiseBoqWork() {
		super();
	}

	public RenderTypeWiseBoqWork(Object data, Double amount) {
		super();
		this.data = data;
		this.amount = amount;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
