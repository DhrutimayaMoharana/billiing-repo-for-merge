package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wo_files_mapping")
public class WorkorderFile implements Serializable {

	private static final long serialVersionUID = -1908850615017766432L;

	private Long id;

	private Long workorderId;

	private DocumentType type;

	private S3File file;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public WorkorderFile() {
		super();
	}

	public WorkorderFile(Long id) {
		super();
		this.id = id;
	}

	public WorkorderFile(Long id, Long workorderId, DocumentType type, S3File file, Boolean isActive, Date createdOn,
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "workorder_id")
	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	@OneToOne
	@JoinColumn(name = "file_type_id")
	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	@OneToOne
	@JoinColumn(name = "file_id")
	public S3File getFile() {
		return file;
	}

	public void setFile(S3File file) {
		this.file = file;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_on")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}
