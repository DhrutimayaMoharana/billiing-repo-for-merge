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

@Entity
@Table(name = "rfi_main_files")
public class RfiMainFile implements Serializable {

	private static final long serialVersionUID = -7064260917429056506L;

	private Long id;

	private Long rfiMainId;

	private Long fileId;

	private Boolean isActive;

	private Date updatedOn;

	private Integer updatedBy;

	private S3File file;

	public RfiMainFile() {
		super();
	}

	public RfiMainFile(Long id, Long rfiMainId, Long fileId, Boolean isActive, Date updatedOn, Integer updatedBy) {
		super();
		this.id = id;
		this.rfiMainId = rfiMainId;
		this.fileId = fileId;
		this.isActive = isActive;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
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

	@Column(name = "rfi_main_id")
	public Long getRfiMainId() {
		return rfiMainId;
	}

	public void setRfiMainId(Long rfiMainId) {
		this.rfiMainId = rfiMainId;
	}

	@Column(name = "s3_file_id")
	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "updated_on")
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	@OneToOne
	@JoinColumn(name = "s3_file_id", insertable = false, updatable = false)
	public S3File getFile() {
		return file;
	}

	public void setFile(S3File file) {
		this.file = file;
	}

}
