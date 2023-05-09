package erp.billing.dto;

public class UnitTypeDTO {
	
	private Long id;

	private String name;

	public UnitTypeDTO() {
		super();
	}

	public UnitTypeDTO(Long id) {
		super();
		this.id = id;
	}

	public UnitTypeDTO(Long id, String name) {
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
