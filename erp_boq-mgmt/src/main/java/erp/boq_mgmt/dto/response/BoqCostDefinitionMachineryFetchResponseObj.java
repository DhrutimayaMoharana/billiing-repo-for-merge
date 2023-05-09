package erp.boq_mgmt.dto.response;

import java.util.List;

public class BoqCostDefinitionMachineryFetchResponseObj {

	private Double totalAmount;

	private List<BoqCostDefinitionMachineryResponse> machineryList;

	public BoqCostDefinitionMachineryFetchResponseObj() {
		super();
	}

	public BoqCostDefinitionMachineryFetchResponseObj(Double totalAmount, List<BoqCostDefinitionMachineryResponse> machineryList) {
		super();
		this.totalAmount = totalAmount;
		this.machineryList = machineryList;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<BoqCostDefinitionMachineryResponse> getMachineryList() {
		return machineryList;
	}

	public void setMachineryList(List<BoqCostDefinitionMachineryResponse> machineryList) {
		this.machineryList = machineryList;
	}

}
