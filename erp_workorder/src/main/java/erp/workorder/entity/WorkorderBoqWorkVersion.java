package erp.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wo_boq_work_versions")
public class WorkorderBoqWorkVersion implements Serializable {

	private static final long serialVersionUID = -625210563204729143L;

	private Long id;

	private Long workorderVersionId;

	private String workScope;

	private String annexureNote;

	private Boolean isActive;

	private Integer version;

	private Long siteId;

	private Date createdOn;

	private Long createdBy;

	public WorkorderBoqWorkVersion() {
		super();
	}

	public WorkorderBoqWorkVersion(Long id, Long workorderVersionId, String workScope, String annexureNote,
			Boolean isActive, Integer version, Long siteId, Date createdOn, Long createdBy) {
		super();
		this.id = id;
		this.workorderVersionId = workorderVersionId;
		this.workScope = workScope;
		this.annexureNote = annexureNote;
		this.isActive = isActive;
		this.version = version;
		this.siteId = siteId;
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

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "work_scope")
	public String getWorkScope() {
		return workScope;
	}

	public void setWorkScope(String workScope) {
		this.workScope = workScope;
	}

	@Column(name = "site_id")
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	@Column(name = "annexure_note")
	public String getAnnexureNote() {
		return annexureNote;
	}

	public void setAnnexureNote(String annexureNote) {
		this.annexureNote = annexureNote;
	}

	@Column(name = "wo_version_id")
	public Long getWorkorderVersionId() {
		return workorderVersionId;
	}

	public void setWorkorderVersionId(Long workorderVersionId) {
		this.workorderVersionId = workorderVersionId;
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
