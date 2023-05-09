package erp.boq_mgmt.dto;

public class TypeStructureParentChildRenderDTO {

	private Long id;

	private String name;

	private String code;

	private Integer groupId;

	private String groupName;

	private Long chainageId;

	private String chainageName;

	private Integer boqCount;

	private Integer structureCount;

	private Integer pid;

	private Integer parentId;

	public TypeStructureParentChildRenderDTO() {
		super();
	}

	public TypeStructureParentChildRenderDTO(Long id, String name, String code, Integer groupId, String groupName,
			Long chainageId, String chainageName, Integer boqCount, Integer structureCount, Integer pid,
			Integer parentId) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.groupId = groupId;
		this.groupName = groupName;
		this.chainageId = chainageId;
		this.chainageName = chainageName;
		this.boqCount = boqCount;
		this.structureCount = structureCount;
		this.pid = pid;
		this.parentId = parentId;
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

	public String getChainageName() {
		return chainageName;
	}

	public void setChainageName(String chainageName) {
		this.chainageName = chainageName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getChainageId() {
		return chainageId;
	}

	public void setChainageId(Long chainageId) {
		this.chainageId = chainageId;
	}

	public Integer getBoqCount() {
		return boqCount;
	}

	public void setBoqCount(Integer boqCount) {
		this.boqCount = boqCount;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getStructureCount() {
		return structureCount;
	}

	public void setStructureCount(Integer structureCount) {
		this.structureCount = structureCount;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
