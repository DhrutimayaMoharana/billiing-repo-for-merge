package erp.boq_mgmt.dto.response;

import java.util.List;

public class BoqCostDefinitionLabourFetchResponseObj {

	private Double totalAmount;

	private List<BoqCostDefinitionLabourResponse> labourList;

	public BoqCostDefinitionLabourFetchResponseObj() {
		super();
	}

	public BoqCostDefinitionLabourFetchResponseObj(Double totalAmount, List<BoqCostDefinitionLabourResponse> labourList) {
		super();
		this.totalAmount = totalAmount;
		this.labourList = labourList;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<BoqCostDefinitionLabourResponse> getLabourList() {
		return labourList;
	}

	public void setLabourList(List<BoqCostDefinitionLabourResponse> labourList) {
		this.labourList = labourList;
	}

}
