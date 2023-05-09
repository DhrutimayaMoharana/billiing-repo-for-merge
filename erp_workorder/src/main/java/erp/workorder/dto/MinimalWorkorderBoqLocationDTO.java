package erp.workorder.dto;

public class MinimalWorkorderBoqLocationDTO {

	private Long id;

	private MinimalObjectDTO fromChainage;

	private MinimalObjectDTO toChainage;

	private MinimalObjectDTO structureType;

	public MinimalWorkorderBoqLocationDTO() {
		super();
	}

	public MinimalWorkorderBoqLocationDTO(Long id, MinimalObjectDTO fromChainage, MinimalObjectDTO toChainage,
			MinimalObjectDTO structureType) {
		super();
		this.id = id;
		this.fromChainage = fromChainage;
		this.toChainage = toChainage;
		this.structureType = structureType;
	}

	public MinimalObjectDTO getFromChainage() {
		return fromChainage;
	}

	public void setFromChainage(MinimalObjectDTO fromChainage) {
		this.fromChainage = fromChainage;
	}

	public MinimalObjectDTO getToChainage() {
		return toChainage;
	}

	public void setToChainage(MinimalObjectDTO toChainage) {
		this.toChainage = toChainage;
	}

	public MinimalObjectDTO getStructureType() {
		return structureType;
	}

	public void setStructureType(MinimalObjectDTO structureType) {
		this.structureType = structureType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
