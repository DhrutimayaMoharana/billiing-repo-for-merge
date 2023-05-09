package erp.workorder.dto.response;

public class HiringMachineDurationTypeGetResponse {

	private Short id;

	private String name;

	public HiringMachineDurationTypeGetResponse() {
		super();
	}

	public HiringMachineDurationTypeGetResponse(Short id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
