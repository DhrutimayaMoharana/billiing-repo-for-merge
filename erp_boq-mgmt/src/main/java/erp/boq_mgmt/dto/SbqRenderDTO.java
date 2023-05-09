package erp.boq_mgmt.dto;

public class SbqRenderDTO {
	
	private Object data;
	
	private StructureDTO structure;
	
	private Double totalAmount;

	public SbqRenderDTO() {
		super();
	}

	public SbqRenderDTO(Object data, StructureDTO structure, Double totalAmount) {
		super();
		this.data = data;
		this.structure = structure;
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

	public StructureDTO getStructure() {
		return structure;
	}

	public void setStructure(StructureDTO structure) {
		this.structure = structure;
	}

}
