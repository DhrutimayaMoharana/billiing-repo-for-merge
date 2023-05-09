package erp.workorder.dto;

public class MinimalObjectDTO {

	private Long id;

	private String name;

	private Long structureId;

	private String structureName;

	private Object data;

	private Integer count;

	private Integer sequenceNo;

	private Integer typeId;

	public MinimalObjectDTO() {
		super();
	}

	public MinimalObjectDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.sequenceNo = 0;
		this.count = 0;
	}

	public MinimalObjectDTO(Long id, String name, Long structureId, String structureName, Object data) {
		super();
		this.id = id;
		this.name = name;
		this.structureId = structureId;
		this.structureName = structureName;
		this.data = data;
		this.sequenceNo = 0;
		this.count = 0;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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
