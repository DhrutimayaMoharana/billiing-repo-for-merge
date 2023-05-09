package erp.boq_mgmt.dto.response;

public class UnitResponseDTO {

	private Integer id;

	private String name;

	private String unitTypeName;

	private Integer govtUnitId;

	private String govtUnitName;

	public UnitResponseDTO() {
		super();
	}

	public UnitResponseDTO(Integer id, String name, String unitTypeName, Integer govtUnitId, String govtUnitName) {
		super();
		this.id = id;
		this.name = name;
		this.unitTypeName = unitTypeName;
		this.govtUnitId = govtUnitId;
		this.govtUnitName = govtUnitName;
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

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Integer getGovtUnitId() {
		return govtUnitId;
	}

	public void setGovtUnitId(Integer govtUnitId) {
		this.govtUnitId = govtUnitId;
	}

	public String getGovtUnitName() {
		return govtUnitName;
	}

	public void setGovtUnitName(String govtUnitName) {
		this.govtUnitName = govtUnitName;
	}

}
