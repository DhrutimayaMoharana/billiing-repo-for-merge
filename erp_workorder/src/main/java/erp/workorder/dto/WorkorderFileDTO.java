package erp.workorder.dto;

import java.util.Date;

public class WorkorderFileDTO {

	private Long id;

	private Long workorderId;

	private DocumentTypeDTO type;

	private S3FileDTO file;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WorkorderFileDTO() {
		super();
	}

	public WorkorderFileDTO(Long id) {
		super();
		this.id = id;
	}

	public WorkorderFileDTO(Long id, Long workorderId, DocumentTypeDTO type, S3FileDTO file, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.workorderId = workorderId;
		this.type = type;
		this.file = file;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public DocumentTypeDTO getType() {
		return type;
	}

	public void setType(DocumentTypeDTO type) {
		this.type = type;
	}

	public S3FileDTO getFile() {
		return file;
	}

	public void setFile(S3FileDTO file) {
		this.file = file;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

}
