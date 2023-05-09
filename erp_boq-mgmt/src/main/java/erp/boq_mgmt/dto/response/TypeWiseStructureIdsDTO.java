package erp.boq_mgmt.dto.response;

import java.util.Set;

public class TypeWiseStructureIdsDTO {

	private Long id;

	private String name;

	private Set<Long> structureIds;

	public TypeWiseStructureIdsDTO() {
		super();
	}

	public TypeWiseStructureIdsDTO(Long id) {
		super();
		this.id = id;
	}

	public TypeWiseStructureIdsDTO(Long id, String name, Set<Long> structureIds) {
		super();
		this.id = id;
		this.name = name;
		this.structureIds = structureIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Long> getStructureIds() {
		return structureIds;
	}

	public void setStructureIds(Set<Long> structureIds) {
		this.structureIds = structureIds;
	}

}
