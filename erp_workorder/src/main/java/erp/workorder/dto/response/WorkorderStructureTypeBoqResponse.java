package erp.workorder.dto.response;

public class WorkorderStructureTypeBoqResponse {

	private Long structureTypeId;

	private String structureTypeName;

	private Long structureId;

	private String structureName;

	private Object boqs;

	private Double amount;

	public WorkorderStructureTypeBoqResponse() {
		super();
	}

	public WorkorderStructureTypeBoqResponse(Long structureTypeId, String structureTypeName, Long structureId,
			String structureName, Object boqs) {
		super();
		this.structureTypeId = structureTypeId;
		this.structureTypeName = structureTypeName;
		this.structureName = structureName;
		this.structureId = structureId;
		this.boqs = boqs;
	}

	public Long getStructureTypeId() {
		return structureTypeId;
	}

	public void setStructureTypeId(Long structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	public Object getBoqs() {
		return boqs;
	}

	public void setBoqs(Object boqs) {
		this.boqs = boqs;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStructureTypeName() {
		return structureTypeName;
	}

	public void setStructureTypeName(String structureTypeName) {
		this.structureTypeName = structureTypeName;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

}
