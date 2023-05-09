package erp.billing.entity;

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
@Table(name = "bill_files_mapping")
public class BillFile implements Serializable {

	private static final long serialVersionUID = -5090446934855317017L;

	private Long id;

	private Long billId;

	private DocumentType type;

	@NotFound(action = NotFoundAction.IGNORE)
	private S3File file;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	public BillFile() {
		super();
	}

	public BillFile(Long id) {
		super();
		this.id = id;
	}

	public BillFile(Long id, Long billId, DocumentType type, S3File file, Boolean isActive, Date createdOn,
			Long createdBy) {
		super();
		this.id = id;
		this.billId = billId;
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

	@Column(name = "bill_id")
	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
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