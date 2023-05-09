package erp.workorder.dto;

import java.util.List;

public class WorkorderTypesBoqsTncsResponseDTO {

	private Long typeId;

	private String typeName;

	private List<WoTncDTO> tncs;

	private Integer tncsCount;

	public WorkorderTypesBoqsTncsResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkorderTypesBoqsTncsResponseDTO(Long typeId, String typeName, List<WoTncDTO> tncs, Integer tncsCount) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
		this.tncs = tncs;
		this.tncsCount = tncsCount;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<WoTncDTO> getTncs() {
		return tncs;
	}

	public void setTncs(List<WoTncDTO> tncs) {
		this.tncs = tncs;
	}

	public Integer getTncsCount() {
		return tncsCount;
	}

	public void setTncsCount(Integer tncsCount) {
		this.tncsCount = tncsCount;
	}

}
