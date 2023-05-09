package erp.billing.dto.response;

import java.util.List;

public class StructureTypeBillBoqResponse {

	private Long structureTypeId;

	private String structureTypeName;

	private List<BillItemResponseDTO> items;

	public StructureTypeBillBoqResponse() {
		super();
	}

	public StructureTypeBillBoqResponse(Long structureTypeId, String structureTypeName,
			List<BillItemResponseDTO> items) {
		super();
		this.structureTypeId = structureTypeId;
		this.structureTypeName = structureTypeName;
		this.items = items;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public String getStructureTypeName() {
		return structureTypeName;
	}

	public void setStructureTypeName(String structureTypeName) {
		this.structureTypeName = structureTypeName;
	}

	public List<BillItemResponseDTO> getItems() {
		return items;
	}

	public void setItems(List<BillItemResponseDTO> items) {
		this.items = items;
	}

}
