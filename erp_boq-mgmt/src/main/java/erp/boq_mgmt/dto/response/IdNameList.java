package erp.boq_mgmt.dto.response;

public class IdNameList {

	private Long id;

	private String name;

	private Object list;

	public IdNameList() {
		super();
	}

	public IdNameList(Long id, String name, Object list) {
		super();
		this.id = id;
		this.name = name;
		this.list = list;
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

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}

}
