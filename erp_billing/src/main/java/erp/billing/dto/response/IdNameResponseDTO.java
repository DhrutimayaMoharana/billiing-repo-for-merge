package erp.billing.dto.response;

public class IdNameResponseDTO {
	
	private Long id;
	
	private String name;

	public IdNameResponseDTO() {
		super();
	}

	public IdNameResponseDTO(Long id, String name) {
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
