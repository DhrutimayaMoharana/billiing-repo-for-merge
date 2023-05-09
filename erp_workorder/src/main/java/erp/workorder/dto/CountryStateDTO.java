package erp.workorder.dto;

public class CountryStateDTO {
	
	private Long id;
	
	private String name;

	public CountryStateDTO() {
		super();
	}

	public CountryStateDTO(Long id) {
		super();
		this.id = id;
	}

	public CountryStateDTO(Long id, String name) {
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
