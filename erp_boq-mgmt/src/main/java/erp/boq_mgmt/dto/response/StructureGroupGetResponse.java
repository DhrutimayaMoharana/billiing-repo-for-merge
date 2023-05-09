package erp.boq_mgmt.dto.response;

public class StructureGroupGetResponse {

	private Integer id;

	private String name;

	private String description;

	private Long structureTypeId;

	private String structureTypeName;

	public StructureGroupGetResponse() {
		super();
	}

	public StructureGroupGetResponse(Integer id, String name, String description, Long structureTypeId,
			String structureTypeName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.structureTypeId = structureTypeId;
		this.structureTypeName = structureTypeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
