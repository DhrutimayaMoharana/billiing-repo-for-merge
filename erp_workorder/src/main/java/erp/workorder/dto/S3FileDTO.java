package erp.workorder.dto;

import java.util.Date;

public class S3FileDTO {

	private Long id;

	private String name;

	private String modifiedName;

	private String path;

	private String bucket;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Integer moduleId;

	private Long siteId;

	private Integer companyId;

	// extra fields

	private String encodedBase64;

	public S3FileDTO() {
		super();
	}

	public S3FileDTO(Long id, String name, String modifiedName, String path, String bucket, Boolean isActive,
			Date createdOn, Long createdBy, Integer moduleId, Long siteId, Integer companyId) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.isActive = isActive;
		this.bucket = bucket;
		this.createdOn = createdOn;
		this.modifiedName = modifiedName;
		this.createdBy = createdBy;
		this.moduleId = moduleId;
		this.siteId = siteId;
		this.companyId = companyId;
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

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getModifiedName() {
		return modifiedName;
	}

	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getEncodedBase64() {
		return encodedBase64;
	}

	public void setEncodedBase64(String encodedBase64) {
		this.encodedBase64 = encodedBase64;
	}

}
