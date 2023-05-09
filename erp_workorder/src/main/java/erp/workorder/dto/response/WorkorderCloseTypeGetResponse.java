package erp.workorder.dto.response;

public class WorkorderCloseTypeGetResponse {

	private Integer id;

	private String enumName;

	private String name;

	public WorkorderCloseTypeGetResponse() {
		super();
	}

	public WorkorderCloseTypeGetResponse(Integer id, String enumName, String name) {
		super();
		this.id = id;
		this.enumName = enumName;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
