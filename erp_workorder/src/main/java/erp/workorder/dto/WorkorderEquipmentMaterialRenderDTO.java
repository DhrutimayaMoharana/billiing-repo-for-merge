package erp.workorder.dto;

public class WorkorderEquipmentMaterialRenderDTO {
	
	private Object materialConfig;
	
	private Object issuedEquipments;

	public WorkorderEquipmentMaterialRenderDTO(Object materialConfig, Object issuedEquipments) {
		super();
		this.materialConfig = materialConfig;
		this.issuedEquipments = issuedEquipments;
	}

	public Object getMaterialConfig() {
		return materialConfig;
	}

	public void setMaterialConfig(Object materialConfig) {
		this.materialConfig = materialConfig;
	}

	public Object getIssuedEquipments() {
		return issuedEquipments;
	}

	public void setIssuedEquipments(Object issuedEquipments) {
		this.issuedEquipments = issuedEquipments;
	}
	
	

}
