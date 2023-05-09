package erp.boq_mgmt.dto.response;

public class IdNameDTO {

	private Long id;

	private String name;

	public IdNameDTO() {
		super();
	}

	public IdNameDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

}
