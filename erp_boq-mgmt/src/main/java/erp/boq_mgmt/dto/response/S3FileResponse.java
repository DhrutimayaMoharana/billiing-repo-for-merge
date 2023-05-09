package erp.boq_mgmt.dto.response;

public class S3FileResponse {

	private Long id;

	private String name;

	private String modifiedName;

	private String path;

	public S3FileResponse() {
		super();
	}

	public S3FileResponse(Long id, String name, String modifiedName, String path) {
		super();
		this.id = id;
		this.name = name;
		this.modifiedName = modifiedName;
		this.path = path;
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

	public String getModifiedName() {
		return modifiedName;
	}

	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
