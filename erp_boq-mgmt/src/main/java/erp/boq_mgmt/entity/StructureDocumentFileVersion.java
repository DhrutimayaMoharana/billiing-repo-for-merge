package erp.boq_mgmt.entity;

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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "structure_doc_files_version")
public class StructureDocumentFileVersion implements Serializable {

	private static final long serialVersionUID = 4012800899486812879L;

	private Long id;

	private StructureDocumentVersion documentVersion;

	private S3File file;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public StructureDocumentFileVersion() {
		super();
	}

	public StructureDocumentFileVersion(Long id) {
		super();
		this.id = id;
	}

	public StructureDocumentFileVersion(Long id, StructureDocumentVersion documentVersion, S3File file,
			Boolean isActive, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.documentVersion = documentVersion;
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

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "structure_doc_version_id")
	public StructureDocumentVersion getDocumentVersion() {
		return documentVersion;
	}

	public void setDocumentVersion(StructureDocumentVersion documentVersion) {
		this.documentVersion = documentVersion;
	}

	@NotFound(action = NotFoundAction.IGNORE)
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
