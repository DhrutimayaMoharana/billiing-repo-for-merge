package erp.billing.dto;

import java.util.Date;

public class FileDTO {
	
	private Long id;
	
	private String name;
	
	private String path;
	
	private Date createdOn;
	
	private Long createdBy;
	
	private Integer moduleId;
	
	// extra properties
	
	private String fullFilePath;

	public FileDTO() {
		super();
	}

	public FileDTO(Long id) {
		super();
		this.id = id;
	}

	public FileDTO(Long id, String name, String path, Date createdOn, Long createdBy, Integer moduleId) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.moduleId = moduleId;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getFullFilePath() {
		return fullFilePath;
	}

	public void setFullFilePath(String fullFilePath) {
		this.fullFilePath = fullFilePath;
	}

}
