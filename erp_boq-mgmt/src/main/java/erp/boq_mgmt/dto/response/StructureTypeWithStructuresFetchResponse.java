package erp.boq_mgmt.dto.response;

import java.util.List;

public class StructureTypeWithStructuresFetchResponse {

	private Long id;

	private String structureTypeName;

	private List<IdNameDTO> structureList;

	public StructureTypeWithStructuresFetchResponse(Long id, String structureTypeName, List<IdNameDTO> structureList) {
		super();
		this.id = id;
		this.structureTypeName = structureTypeName;
		this.structureList = structureList;
	}

	public StructureTypeWithStructuresFetchResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStructureTypeName() {
		return structureTypeName;
	}

	public void setStructureTypeName(String structureTypeName) {
		this.structureTypeName = structureTypeName;
	}

	public List<IdNameDTO> getStructureList() {
		return structureList;
	}

	public void setStructureList(List<IdNameDTO> structureList) {
		this.structureList = structureList;
	}

}
