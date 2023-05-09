package erp.boq_mgmt.dto.response;

public class MaterialFetchResponse {

	private Long id;

	private String name;

	private String specification;

	private Long unitId;

	private String unitName;

	public MaterialFetchResponse() {
		super();
	}

	public MaterialFetchResponse(Long id, String name, String specification, Long unitId, String unitName) {
		super();
		this.id = id;
		this.name = name;
		this.specification = specification;
		this.unitId = unitId;
		this.unitName = unitName;
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

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
