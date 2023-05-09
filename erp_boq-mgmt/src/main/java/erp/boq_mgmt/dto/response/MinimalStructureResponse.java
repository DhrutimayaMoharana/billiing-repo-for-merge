package erp.boq_mgmt.dto.response;

public class MinimalStructureResponse {

	private Long id;

	private String name;

	private IdNameDTO type;

	public MinimalStructureResponse(Long id, String name, IdNameDTO type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public MinimalStructureResponse() {
		super();
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

	public IdNameDTO getType() {
		return type;
	}

	public void setType(IdNameDTO type) {
		this.type = type;
	}

}
