package erp.boq_mgmt.dto.response;

import java.util.List;

public class BoqCostDefinitionMaterialFetchResponseObj {

	private Double totalAmount;

	private List<BoqCostDefinitionMaterialResponse> materialList;

	public BoqCostDefinitionMaterialFetchResponseObj() {
		super();
	}

	public BoqCostDefinitionMaterialFetchResponseObj(Double totalAmount, List<BoqCostDefinitionMaterialResponse> materialList) {
		super();
		this.totalAmount = totalAmount;
		this.materialList = materialList;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<BoqCostDefinitionMaterialResponse> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<BoqCostDefinitionMaterialResponse> materialList) {
		this.materialList = materialList;
	}

}
