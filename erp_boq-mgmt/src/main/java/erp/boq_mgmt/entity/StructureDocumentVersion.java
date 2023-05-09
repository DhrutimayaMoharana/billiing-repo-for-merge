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
@Table(name = "structure_document_version")
public class StructureDocumentVersion implements Serializable {

	private static final long serialVersionUID = 6654508502510135288L;

	private Long id;

	private Long structureDocumentId;

	private Long structureId;

	private DocumentType type;

	private DocumentSubType subtype;

	private String reference;

	private StructureDocumentStatus status;

	private Date date;

	private String remark;

	private EngineState state;

	private Integer version;

	private Boolean isActive;

	private Date createdOn;

	private Long createdBy;

	private Long siteId;

	public StructureDocumentVersion() {
		super();
	}

	public StructureDocumentVersion(Long id) {
		super();
		this.id = id;
	}

	public StructureDocumentVersion(Long id, Long structureDocumentId, Long structureId, DocumentType type,
			DocumentSubType subtype, String reference, StructureDocumentStatus status, Date date, String remark,
			EngineState state, Integer version, Boolean isActive, Date createdOn, Long createdBy, Long siteId) {
		super();
		this.id = id;
		this.setStructureDocumentId(structureDocumentId);
		this.structureId = structureId;
		this.type = type;
		this.subtype = subtype;
		this.reference = reference;
		this.status = status;
		this.date = date;
		this.remark = remark;
		this.state = state;
		this.version = version;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.siteId = siteId;
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

	@Column(name = "structure_id")
	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "doc_type_id")
	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "doc_subtype_id")
	public DocumentSubType getSubtype() {
		return subtype;
	}

	public void setSubtype(DocumentSubType subtype) {
		this.subtype = subtype;
	}

	@Column(name = "reference")
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "structure_doc_status_id")
	public StructureDocumentStatus getStatus() {
		return status;
	}

	public void setStatus(StructureDocumentStatus status) {
		this.status = status;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne
	@JoinColumn(name = "state_id")
	public EngineState getState() {
		return state;
	}

	public void setState(EngineState state) {
		this.state = state;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "structure_doc_id")
	public Long getStructureDocumentId() {
		return structureDocumentId;
	}

	public void setStructureDocumentId(Long structureDocumentId) {
		this.structureDocumentId = structureDocumentId;
	}

}
