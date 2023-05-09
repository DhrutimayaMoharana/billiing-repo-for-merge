package erp.workorder.dto.response;

public class MachineryAttendanceStatusResponse {

	private Integer id;

	private String name;

	public MachineryAttendanceStatusResponse(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public MachineryAttendanceStatusResponse() {
		super();
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

}
